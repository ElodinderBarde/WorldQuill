package ch.Elodin.RealmQuill.service.notes;

import ch.Elodin.RealmQuill.model.AppUser;
import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.shop.Shop;
import ch.Elodin.RealmQuill.model.world.Campaign;
import ch.Elodin.RealmQuill.model.world.City;
import ch.Elodin.RealmQuill.model.world.Village;
import ch.Elodin.RealmQuill.model.notes.WorldNotesCategory;
import ch.Elodin.RealmQuill.model.notes.WorldNotesFolder;
import ch.Elodin.RealmQuill.model.notes.WorldNotesNote;
import ch.Elodin.RealmQuill.repository.Login.AppUserRepository;
import ch.Elodin.RealmQuill.repository.notes.WorldNotesCategoryRepository;
import ch.Elodin.RealmQuill.repository.notes.WorldNotesFolderRepository;
import ch.Elodin.RealmQuill.repository.notes.WorldNotesNoteRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopRepository;
import ch.Elodin.RealmQuill.repository.world.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotesSyncService {

    private final WorldNotesFolderRepository folderRepo;
    private final WorldNotesNoteRepository noteRepo;
    private final WorldNotesCategoryRepository categoryRepo;
    private final AppUserRepository userRepo;
    private final CityRepository cityRepo;
    private final VillageRepository villageRepo;
    private final ShopRepository shopRepo;
    private final NpcRepository npcRepo;
    private final ClanRepository clanRepo;
    private final CampaignRepository campaignRepo;

    // ── HELPER ────────────────────────────────────────────────────────────────
    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        if ("anonymousUser".equals(username)) {
            username = "Elodin"; // temporär
        }
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User nicht gefunden"));
    }

    @Transactional
    public void syncAllCampaignsForCurrentUser() {
        AppUser user = getCurrentUser();
        WorldNotesCategory category = getOrCreateCategory(user);
        WorldNotesFolder root = getOrCreateFolder("Dein Hauptordner", user, category, null);

        List<Campaign> campaigns = campaignRepo.findByUserId(user.getId().intValue());
        for (Campaign campaign : campaigns) {
            syncCampaignStructure(campaign, user, root, category);
        }
    }

    public WorldNotesFolder getOrCreateFolder(String name, AppUser user,
                                              WorldNotesCategory category, WorldNotesFolder parent) {
        return folderRepo.findByNameAndUserAndParentFolder(name, user, parent).orElseGet(() -> {
            WorldNotesFolder f = new WorldNotesFolder();
            f.setName(name);
            f.setUser(user);
            f.setCategory(category);
            f.setParentFolder(parent);
            return folderRepo.save(f);
        });
    }

    public void syncNote(String title, String content, WorldNotesFolder folder,
                         AppUser user, WorldNotesCategory category) {
        noteRepo.findByTitleAndUser(title, user).ifPresentOrElse(
                existing -> {
                    if (existing.getFolder() == null) {
                        existing.setFolder(folder);
                        noteRepo.save(existing);
                    }
                },
                () -> {
                    WorldNotesNote n = new WorldNotesNote();
                    n.setTitle(title);
                    n.setContent(content != null ? content : "");
                    n.setFolder(folder);
                    n.setUser(user);
                    n.setCategory(category);
                    noteRepo.save(n);
                }
        );
    }

    public WorldNotesCategory getOrCreateCategory(AppUser user) {
        return categoryRepo.findByNameAndUser("Allgemein", user).orElseGet(() -> {
            WorldNotesCategory c = new WorldNotesCategory();
            c.setName("Allgemein");
            c.setUser(user);
            return categoryRepo.save(c);
        });
    }

    // ── KAMPAGNE-GRUNDSTRUKTUR ────────────────────────────────────────────────

    /**
     * Erstellt die Grundstruktur für eine neue Kampagne unter dem Root-Ordner.
     * Wird sowohl vom DataInitializer als auch vom CampaignController aufgerufen.
     */
    @Transactional
    public WorldNotesFolder syncCampaignStructure(Campaign campaign, AppUser user,
                                                  WorldNotesFolder root,
                                                  WorldNotesCategory category) {
        String name = campaign.getCampaignName();

        WorldNotesFolder kampagneFolder = getOrCreateFolder(name, user, category, root);

        // Grundstruktur
        getOrCreateFolder("Weltnotizen", user, category, kampagneFolder);
        WorldNotesFolder npcFolder = getOrCreateFolder("NPC", user, category, kampagneFolder);
        WorldNotesFolder ortschaften = getOrCreateFolder("Ortschaften", user, category, kampagneFolder);
        getOrCreateFolder("Clans", user, category, kampagneFolder);

        // Ortschaften-Unterstruktur
        WorldNotesFolder staedte = getOrCreateFolder("Städte", user, category, ortschaften);
        WorldNotesFolder dörfer = getOrCreateFolder("Dörfer", user, category, ortschaften);
        getOrCreateFolder("Sonstige", user, category, ortschaften);

        return kampagneFolder;
    }

    // ── CITY SYNC ────────────────────────────────────────────────────────────

    @Transactional
    public void syncCity(City city, AppUser user, WorldNotesCategory category,
                         WorldNotesFolder staedteFolder) {
        WorldNotesFolder cityFolder = getOrCreateFolder(city.getCityName(), user, category, staedteFolder);

        if (city.getNotes() != null && !city.getNotes().isBlank()) {
            syncNote(city.getCityName(), city.getNotes(), cityFolder, user, category);
        }

        WorldNotesFolder gebäude = getOrCreateFolder("Gebäude", user, category, cityFolder);
        getOrCreateFolder("Quests", user, category, cityFolder);

        List<Shop> shops = shopRepo.findByLocationId(city.getId());
        Map<String, WorldNotesFolder> typeFolders = new HashMap<>();

        for (Shop shop : shops) {
            String typeName = shop.getShopType() != null ? shop.getShopType().getName() : "Sonstige";
            WorldNotesFolder typeFolder = typeFolders.computeIfAbsent(typeName,
                    n -> getOrCreateFolder(n, user, category, gebäude));
            WorldNotesFolder shopFolder = getOrCreateFolder(shop.getName(), user, category, typeFolder);
            if (shop.getNotes() != null && !shop.getNotes().isBlank()) {
                syncNote(shop.getName(), shop.getNotes(), shopFolder, user, category);
            }
        }
    }

    // ── VILLAGE SYNC ─────────────────────────────────────────────────────────

    @Transactional
    public void syncVillage(Village village, AppUser user, WorldNotesCategory category,
                            WorldNotesFolder dörferFolder) {
        WorldNotesFolder villageFolder = getOrCreateFolder(village.getName(), user, category, dörferFolder);

        if (village.getNotes() != null && !village.getNotes().isBlank()) {
            syncNote(village.getName(), village.getNotes(), villageFolder, user, category);
        }

        WorldNotesFolder gebäude = getOrCreateFolder("Gebäude", user, category, villageFolder);
        getOrCreateFolder("Quests", user, category, villageFolder);

        List<Shop> shops = shopRepo.findByLocationId(village.getId());
        Map<String, WorldNotesFolder> typeFolders = new HashMap<>();

        for (Shop shop : shops) {
            String typeName = shop.getShopType() != null ? shop.getShopType().getName() : "Sonstige";
            WorldNotesFolder typeFolder = typeFolders.computeIfAbsent(typeName,
                    n -> getOrCreateFolder(n, user, category, gebäude));
            WorldNotesFolder shopFolder = getOrCreateFolder(shop.getName(), user, category, typeFolder);
            if (shop.getNotes() != null && !shop.getNotes().isBlank()) {
                syncNote(shop.getName(), shop.getNotes(), shopFolder, user, category);
            }
        }
    }

    // ── FULL SYNC (für DataInitializer) ──────────────────────────────────────

    @Transactional
    public void fullSync(AppUser user) {
        WorldNotesCategory category = getOrCreateCategory(user);
        WorldNotesFolder root = getOrCreateFolder("Dein Hauptordner", user, category, null);

        // Alle Kampagnen des Users
        for (Campaign campaign : campaignRepo.findByUserId(user.getId().intValue())) {
            WorldNotesFolder kampagneFolder = syncCampaignStructure(campaign, user, root, category);

            WorldNotesFolder ortschaften = getOrCreateFolder("Ortschaften", user, category, kampagneFolder);
            WorldNotesFolder staedte = getOrCreateFolder("Städte", user, category, ortschaften);
            WorldNotesFolder dörfer = getOrCreateFolder("Dörfer", user, category, ortschaften);
            WorldNotesFolder npcFolder = getOrCreateFolder("NPC", user, category, kampagneFolder);
            WorldNotesFolder clanFolder = getOrCreateFolder("Clans", user, category, kampagneFolder);

            for (City city : cityRepo.findAll()) {
                syncCity(city, user, category, staedte);
            }
            for (Village village : villageRepo.findAll()) {
                syncVillage(village, user, category, dörfer);
            }
            for (Npc npc : npcRepo.findAll()) {
                if (npc.getNotes() == null || npc.getNotes().isBlank()) continue;
                String npcName = npc.getFirstname().getFirstname() + " " + npc.getLastname().getLastname();
                WorldNotesFolder npcNoteFolder = getOrCreateFolder(npcName, user, category, npcFolder);
                syncNote(npcName, npc.getNotes(), npcNoteFolder, user, category);
            }
            for (Clan clan : clanRepo.findAll()) {
                WorldNotesFolder thisClanFolder = getOrCreateFolder(clan.getClan(), user, category, clanFolder);
                if (clan.getClanNotes() != null && !clan.getClanNotes().isBlank()) {
                    syncNote(clan.getClan(), clan.getClanNotes(), thisClanFolder, user, category);
                }
                WorldNotesFolder mitgliederFolder = getOrCreateFolder("Mitglieder", user, category, thisClanFolder);
                for (Npc npc : npcRepo.findByClanId(clan.getId())) {
                    String npcName = npc.getFirstname().getFirstname() + " " + npc.getLastname().getLastname();
                    WorldNotesFolder npcInClanFolder = getOrCreateFolder(npcName, user, category, mitgliederFolder);
                    String noteTitle = npcName + " (Clan: " + clan.getClan() + ")";
                    boolean hasNote = npc.getNotes() != null && !npc.getNotes().isBlank();
                    syncNote(noteTitle,
                            hasNote ? npc.getNotes() : "Mitglied von " + clan.getClan() + ", als " + npc.getClan_position(),
                            npcInClanFolder, user, category);
                }
            }
        }
    }
}