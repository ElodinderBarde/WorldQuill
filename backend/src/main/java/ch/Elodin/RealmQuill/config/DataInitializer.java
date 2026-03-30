package ch.Elodin.RealmQuill.config;

import ch.Elodin.RealmQuill.model.AppUser;
import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.enums.RoleEnum;
import ch.Elodin.RealmQuill.model.notes.WorldNotesCategory;
import ch.Elodin.RealmQuill.model.notes.WorldNotesFolder;
import ch.Elodin.RealmQuill.model.notes.WorldNotesNote;
import ch.Elodin.RealmQuill.model.shop.Shop;
import ch.Elodin.RealmQuill.model.world.City;
import ch.Elodin.RealmQuill.model.world.Village;
import ch.Elodin.RealmQuill.repository.Login.AppUserRepository;
import ch.Elodin.RealmQuill.repository.notes.WorldNotesCategoryRepository;
import ch.Elodin.RealmQuill.repository.notes.WorldNotesFolderRepository;
import ch.Elodin.RealmQuill.repository.notes.WorldNotesNoteRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopTypeRepository;
import ch.Elodin.RealmQuill.repository.world.*;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ch.Elodin.RealmQuill.repository.shop.ShopRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepo;
    private final WorldNotesCategoryRepository categoryRepo;
    private final WorldNotesFolderRepository folderRepo;
    private final WorldNotesNoteRepository noteRepo;
    private final PasswordEncoder passwordEncoder;
    private final ShopRepository shopRepo;
    private final CampaignRepository campaignRepo;
    private final NpcRepository npcRepo;
    private final LocationRepository locationRepo;
    private final CityRepository cityRepo;
    private final VillageRepository villageRepo;
    private final ShopTypeRepository shopTypeRepo;
        private final ClanRepository ClanRepo;




    // ── HELPER ────────────────────────────────────────────────────────────────
// Ordner holen oder erstellen
    private WorldNotesFolder getOrCreateFolder(String name, AppUser user,
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

    // Note holen oder erstellen
    private void syncNote(String title, String content, WorldNotesFolder folder,
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


    @Override
    public void run(String... args) {

        // DEFAULT USER ---------------------------------------------------------
        AppUser user = userRepo.findByUsername("Elodin").orElse(null);
        if (user == null) {
            user = new AppUser();
            user.setUsername("Elodin");
            user.setEmail("Elodin@local");
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setRole(RoleEnum.ADMIN);
            userRepo.save(user);
            System.out.println("✔ Default-User 'Elodin' erstellt");
        }

        // DEFAULT CATEGORY -----------------------------------------------------
        WorldNotesCategory category =
                categoryRepo.findByNameAndUser("Allgemein", user).orElse(null);

        if (category == null) {
            category = new WorldNotesCategory();
            category.setName("Allgemein");
            category.setUser(user);
            categoryRepo.save(category);
            System.out.println(" Kategorie 'Allgemein' erstellt");
        }
// ── FOLDER-HIERARCHIE ─────────────────────────────────────────────────────
// Hauptordner (später ausblenden)
        WorldNotesFolder root = getOrCreateFolder("Dein Hauptordner", user, category, null);

// Kampagne
        WorldNotesFolder kampagne = getOrCreateFolder("Kampagne", user, category, root);

// Unter Kampagne
        WorldNotesFolder weltnotizen  = getOrCreateFolder("Weltnotizen",  user, category, kampagne);
        WorldNotesFolder npcFolder    = getOrCreateFolder("NPC",          user, category, kampagne);
        WorldNotesFolder ortschaften  = getOrCreateFolder("Ortschaften",  user, category, kampagne);
        WorldNotesFolder clans        = getOrCreateFolder("Clans",        user, category, kampagne);
// Unter Ortschaften
        WorldNotesFolder staedte  = getOrCreateFolder("Städte",  user, category, ortschaften);
        WorldNotesFolder villages = getOrCreateFolder("Dörfer",  user, category, ortschaften);

// ── CITIES: Ordner + Notes ────────────────────────────────────────────────
        for (City city : cityRepo.findAll()) {
            // Ordner pro Stadt
            WorldNotesFolder cityFolder = getOrCreateFolder(city.getCityName(), user, category, staedte);

            // Note aus city.getNotes() falls vorhanden
            if (city.getNotes() != null && !city.getNotes().isBlank()) {
                syncNote(city.getCityName(), city.getNotes(), cityFolder, user, category);
            }

            // Geschäftstypen unter der Stadt
            List<Shop> cityShops = shopRepo.findByLocationId(city.getId());
            Map<String, WorldNotesFolder> shopTypeFolders = new HashMap<>();

            for (Shop shop : cityShops) {
                String typeName = shop.getShopType() != null
                        ? shop.getShopType().getName() : "Sonstige";

                // Geschäftstyp-Ordner (einmalig pro Stadt)
                AppUser finalUser = user;
                WorldNotesCategory finalCategory = category;
                WorldNotesFolder typeFolder = shopTypeFolders.computeIfAbsent(typeName,
                        n -> getOrCreateFolder(n, finalUser, finalCategory, cityFolder));

                // Geschäft-Ordner
                WorldNotesFolder shopFolder = getOrCreateFolder(shop.getName(), user, category, typeFolder);

                // Note aus shop.getNotes()
                if (shop.getNotes() != null && !shop.getNotes().isBlank()) {
                    syncNote(shop.getName(), shop.getNotes(), shopFolder, user, category);
                }
            }
        }
        // ── CLANS: Ordner + Notes + Mitglieder ───────────────────────────────────
        WorldNotesFolder clanFolder = getOrCreateFolder("Clans", user, category, kampagne);

        for (Clan clan : ClanRepo.findAll()) {

            // Clan-Ordner immer erstellen
            WorldNotesFolder thisClanFolder = getOrCreateFolder(
                    clan.getClan(), user, category, clanFolder);

            // Clan-Note nur wenn vorhanden
            if (clan.getClanNotes() != null && !clan.getClanNotes().isBlank()) {
                syncNote(clan.getClan(), clan.getClan(), thisClanFolder, user, category);
            }

            // Mitglieder-Unterordner immer erstellen
            WorldNotesFolder mitgliederFolder = getOrCreateFolder(
                    "Mitglieder", user, category, thisClanFolder);

            // Alle NPCs dieses Clans
            for (Npc npc : npcRepo.findByClanId(clan.getId())) {
                String npcName = npc.getFirstname().getFirstname() + " "
                        + npc.getLastname().getLastname();

                // Ordner im Mitglieder-Bereich immer erstellen
                WorldNotesFolder npcInClanFolder = getOrCreateFolder(
                        npcName, user, category, mitgliederFolder);

                // Eindeutiger Titel verhindert Kollision mit NPC-Ordner-Note
                String noteTitle = npcName + " (Clan: " + clan.getClan() + ")";
                boolean hasNote = npc.getNotes() != null && !npc.getNotes().isBlank();

                syncNote(
                        noteTitle,
                        hasNote ? npc.getNotes() : "Mitglied von " + clan.getClan() +", als " + npc.getClan_position(),
                        npcInClanFolder, user, category
                );
            }
        }



// ── VILLAGES: Ordner + Notes ──────────────────────────────────────────────
        for (Village village : villageRepo.findAll()) {
            WorldNotesFolder villageFolder = getOrCreateFolder(village.getName(), user, category, villages);

            if (village.getNotes() != null && !village.getNotes().isBlank()) {
                syncNote(village.getName(), village.getNotes(), villageFolder, user, category);
            }

            // Shops in Village (gleiche Logik wie City)
            List<Shop> villageShops = shopRepo.findByLocationId(village.getId());
            Map<String, WorldNotesFolder> shopTypeFolders = new HashMap<>();

            for (Shop shop : villageShops) {
                String typeName = shop.getShopType() != null
                        ? shop.getShopType().getName() : "Sonstige";
                AppUser finalUser1 = user;
                WorldNotesCategory finalCategory1 = category;
                WorldNotesFolder typeFolder = shopTypeFolders.computeIfAbsent(typeName,
                        n -> getOrCreateFolder(n, finalUser1, finalCategory1, villageFolder));
                WorldNotesFolder shopFolder = getOrCreateFolder(shop.getName(), user, category, typeFolder);
                if (shop.getNotes() != null && !shop.getNotes().isBlank()) {
                    syncNote(shop.getName(), shop.getNotes(), shopFolder, user, category);
                }
            }
        }

// ── NPCS: Ordner + Notes ──────────────────────────────────────────────────
        for (Npc npc : npcRepo.findAll()) {
            if (npc.getNotes() == null || npc.getNotes().isBlank()) continue;

            String npcName = npc.getFirstname().getFirstname() + " " + npc.getLastname().getLastname();

            WorldNotesFolder npcNoteFolder = getOrCreateFolder(npcName, user, category, npcFolder);

            if (npc.getNotes() != null && !npc.getNotes().isBlank()) {
                syncNote(npcName, npc.getNotes(), npcNoteFolder, user, category);
            }
        }


        // DEFAULT WELCOME NOTE -------------------------------------------------
        if (noteRepo.count() == 0) {
            WorldNotesNote note = new WorldNotesNote();
            note.setTitle("Willkommen in NotesHandler");
            note.setContent("""
Dies ist deine erste Notiz.

Die Plattform unterstützt **Markdown**.
Viel Spaß beim Schreiben!

# Hello There
## Das ist eine Überschrift
Verwalte deine Notizen einfach mit Drag and Drop, sowie dem Kontextmenü bei Rechtsklick auf einen Ordner oder Notiz.

---
Markdown unterstützt einige verschiedene Funktionen

### Codeblöcke
```cpp
std::cout << "Ich kann hier auch Codeblöcke verwenden";
std::endl;
// ich lasse diesen Code nach 10 zählen
for (int i = 0; i < 10; ++i) {
    std::cout << "\\nIch kann auf " << i << " zählen!";
}
```
---
### Optische bearbeitung

Einen Codeblock hier zu erkennen ist jedoch nicht ganz einfach
aber ich kann auch **Fett** aber auch *kursiv*.

---
Für weitere  Syntaxhilfe → www.markdown.de
""");
            note.setFolder(root);
            note.setUser(user);
            noteRepo.save(note);

            System.out.println("✔ Begrüßungsnotiz erstellt");
        }
    }
}
