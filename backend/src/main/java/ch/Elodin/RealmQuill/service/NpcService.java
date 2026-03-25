// NpcService.java
package ch.Elodin.RealmQuill.service;

import ch.Elodin.RealmQuill.dto.NpcFilterRequest;
import ch.Elodin.RealmQuill.dto.NpcReadDTO;
import ch.Elodin.RealmQuill.dto.NpcWriteDTO;
import ch.Elodin.RealmQuill.mapper.NpcMapper;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.npcinfo.*;
import ch.Elodin.RealmQuill.model.shop.*;
import ch.Elodin.RealmQuill.repository.npcinfo.*;
import ch.Elodin.RealmQuill.repository.shop.*;
import ch.Elodin.RealmQuill.repository.world.ClanRepository;
import ch.Elodin.RealmQuill.repository.world.NpcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ch.Elodin.RealmQuill.service.SaveService.resolveAndSet;

@Service
@RequiredArgsConstructor
public class NpcService {

    private final NpcRepository npcRepository;
    private final StatsRepository statsRepository;
    private final FirstnameRepository firstnameRepository;
    private final RaceRepository raceRepository;
    private final LevelRepository levelRepository;
    private final LastnameRepository lastnameRepository;
    private final GenderRepository genderRepository;
    private final PersonalityRepository personalityRepository;
    private final OtherDescriptionRepository otherDescriptionRepository;
    private final LikesRepository likesRepository;
    private final DislikesRepository dislikesRepository;
    private final IdealsRepository idealsRepository;
    private final KleidungQualiRepository kleidungQualiRepository;
    private final JacketsRepository jacketsRepository;
    private final TrousersRepository trousersRepository;
    private final HairstyleRepository hairstyleRepository;
    private final HaircolorRepository haircolorRepository;
    private final BeardstyleRepository beardstyleRepository;
    private final ArmorRepository armorRepository;
    private final NpcClassRepository npcClassRepository;
    private final SubclassRepository subclassRepository;
    private final BetonungRepository betonungRepository;
    private final FlawRepository flawRepository;
    private final BackgroundRepository backgroundRepository;
    private final JewelleryRepository jewelleryRepository;
    private final TalkingStyleRepository talkingStyleRepository;
    private final PictureRepository pictureRepository;
    private final ClanRepository clanRepository;
    private final ShopRelationsRepository shopRelationsRepository;
    private final ShopRepository shopRepository;
    private final ShopEmployeeRepository shopEmployeeRepository;
    private final ShopCustomerRepository shopCustomerRepository;

    // ── Read ─────────────────────────────────────────────────

    public List<NpcReadDTO> getAllNpcs() {
        return npcRepository.findAll().stream()
                .map(npc -> NpcMapper.toReadDTO(npc, statsRepository.findByNpc(npc).orElse(null)))
                .toList();
    }

    public List<NpcReadDTO> getFilteredNpcs(NpcFilterRequest filter) {
        return npcRepository.findAll().stream()
                .filter(npc -> matchesSearch(npc, filter.getSearch()))
                .filter(npc -> matchesString(
                        npc.getRace() != null ? npc.getRace().getRacename() : null,
                        filter.getRace()))
                .filter(npc -> matchesClass(npc, filter.getNpcClass()))
                .filter(npc -> matchesString(
                        npc.getSubclass() != null ? npc.getSubclass().getSubclassname() : null,
                        filter.getSubclass()))
                .filter(npc -> matchesString(
                        npc.getClan() != null ? npc.getClan().getClan() : null,
                        filter.getClan()))
                .filter(npc -> matchesString(npc.getClan_position(), filter.getClanPosition()))
                .filter(npc -> filter.getHasPicture() == null ||
                        (filter.getHasPicture() == (npc.getPicture() != null)))
                .filter(npc -> filter.getHasShop() == null ||
                        (filter.getHasShop() == (npc.getShop_relations_ID() != null)))
                .filter(npc -> filter.getSymbol() == null || filter.getSymbol().isEmpty() ||
                        (npc.getSymbol() != null &&
                                npc.getSymbol().toString().equalsIgnoreCase(filter.getSymbol())))
                .filter(npc -> filter.getLocationId() == null ||
                        (npc.getShop_relations_ID() != null &&
                                npc.getShop_relations_ID().getShop() != null &&
                                npc.getShop_relations_ID().getShop().getLocation() != null &&
                                npc.getShop_relations_ID().getShop().getLocation().getId()
                                        == filter.getLocationId()))
                .filter(npc -> filter.getCampaignId() == null ||
                        (npc.getCampaign() != null &&
                                npc.getCampaign().getId() == filter.getCampaignId()))
                .map(npc -> NpcMapper.toReadDTO(npc, statsRepository.findByNpc(npc).orElse(null)))
                .toList();
    }

    public NpcReadDTO getNpcById(int id) {
        Npc npc = npcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NPC nicht gefunden: " + id));
        Stats stats = statsRepository.findByNpc(npc).orElse(null);
        return NpcMapper.toReadDTO(npc, stats);
    }

    public List<NpcReadDTO> getNpcsByClanId(int clanId) {
        return npcRepository.findByClanId(clanId).stream()
                .map(npc -> NpcMapper.toReadDTO(npc, statsRepository.findByNpc(npc).orElse(null)))
                .toList();
    }

    public List<NpcReadDTO> getNpcsByCampaignId(int campaignId) {
        return npcRepository.findByCampaignId(campaignId).stream()
                .map(npc -> NpcMapper.toReadDTO(npc, statsRepository.findByNpc(npc).orElse(null)))
                .toList();
    }

    // ── Create ───────────────────────────────────────────────

    @Transactional
    public void createNpc(NpcWriteDTO dto) {
        Npc npc = new Npc();

        // Pflichtfelder
        Race race = raceRepository.findById(dto.getRaceId())
                .orElseThrow(() -> new IllegalArgumentException("Rasse nicht gefunden: " + dto.getRaceId()));
        Gender gender = genderRepository.findById(dto.getGenderId())
                .orElseThrow(() -> new IllegalArgumentException("Geschlecht nicht gefunden: " + dto.getGenderId()));

        if (dto.getFirstnameId() == null) throw new IllegalArgumentException("Vorname darf nicht null sein.");
        Firstname fname = firstnameRepository.findById(dto.getFirstnameId())
                .orElseThrow(() -> new IllegalArgumentException("Vorname nicht gefunden: " + dto.getFirstnameId()));
        if (!fname.getRace().equals(race))
            throw new IllegalArgumentException("Vorname gehört nicht zur angegebenen Rasse.");
        if (!fname.getGender().equals(gender))
            throw new IllegalArgumentException("Vorname passt nicht zum angegebenen Geschlecht.");

        if (dto.getLastnameId() == null) throw new IllegalArgumentException("Nachname darf nicht null sein.");
        Lastname lname = lastnameRepository.findById(dto.getLastnameId())
                .orElseThrow(() -> new IllegalArgumentException("Nachname nicht gefunden: " + dto.getLastnameId()));
        if (!lname.getRace().equals(race))
            throw new IllegalArgumentException("Nachname gehört nicht zur angegebenen Rasse.");

        Level level = levelRepository.findById(dto.getLevelId())
                .orElseThrow(() -> new IllegalArgumentException("Level nicht gefunden: " + dto.getLevelId()));

        npc.setFirstname(fname);
        npc.setLastname(lname);
        npc.setRace(race);
        npc.setGender(gender);
        npc.setLevel(level);
        npc.setNotes(dto.getNotes());
        npc.setNpc_age(dto.getNpc_age());
        npc.setSymbol(dto.getSymbol());

        // Optional-Felder
        resolveAndSet(dto.getPersonalityId(),       personalityRepository::findById,       npc::setPersonality);
        resolveAndSet(dto.getOtherDescriptionId(),  otherDescriptionRepository::findById,  npc::setOtherDescription);
        resolveAndSet(dto.getLikesId(),             likesRepository::findById,             npc::setLikes);
        resolveAndSet(dto.getDislikesId(),          dislikesRepository::findById,          npc::setDislikes);
        resolveAndSet(dto.getIdealsId(),            idealsRepository::findById,            npc::setIdeals);
        resolveAndSet(dto.getKleidungQualiId(),     kleidungQualiRepository::findById,     npc::setKleidungQuali);
        resolveAndSet(dto.getJacketsId(),           jacketsRepository::findById,           npc::setJackets);
        resolveAndSet(dto.getTrousersId(),          trousersRepository::findById,          npc::setTrousers);
        resolveAndSet(dto.getHairstyleId(),         hairstyleRepository::findById,         npc::setHairstyle);
        resolveAndSet(dto.getHaircolorId(),         haircolorRepository::findById,         npc::setHaircolor);
        resolveAndSet(dto.getBeardstyleId(),        beardstyleRepository::findById,        npc::setBeardstyle);
        resolveAndSet(dto.getBetonungId(),          betonungRepository::findById,          npc::setBetonung);
        resolveAndSet(dto.getClassId(),             npcClassRepository::findById,          npc::setNpcClass);
        resolveAndSet(dto.getSubclassId(),          subclassRepository::findById,          npc::setSubclass);
        resolveAndSet(dto.getJewelleryId(),         jewelleryRepository::findById,         npc::setJewellery);
        resolveAndSet(dto.getPictureId(),           pictureRepository::findById,           npc::setPicture);
        resolveAndSet(dto.getFlawId(),              flawRepository::findById,              npc::setFlaw);
        resolveAndSet(dto.getTalkingstyleId(),      talkingStyleRepository::findById,      npc::setTalkingstyle);
        resolveAndSet(dto.getBackgroundId(),        backgroundRepository::findById,        npc::setBackground);
        resolveAndSet(dto.getArmorId(),             armorRepository::findById,             npc::setArmor_ID);
        resolveAndSet(dto.getShopRelationsId(),     shopRelationsRepository::findById,     npc::setShop_relations_ID);

        Npc savedNpc = npcRepository.save(npc);

        if (dto.getStrength() != null || dto.getDexterity() != null ||
                dto.getConstitution() != null || dto.getIntelligence() != null ||
                dto.getWisdom() != null || dto.getCharisma() != null) {
            statsRepository.save(buildStats(dto, savedNpc));
        }

        if (dto.getShopId() != null || dto.getShopEmployeeRole() != null ||
                dto.getShopCustomerRole() != null) {
            ShopRelations relation = shopRelationsRepository.save(buildShopRelation(dto));
            savedNpc.setShop_relations_ID(relation);
            npcRepository.save(savedNpc);
        }
    }

    // ── Update ───────────────────────────────────────────────

    @Transactional
    public void updateNpc(int id, NpcWriteDTO dto) {
        Npc npc = npcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NPC nicht gefunden: " + id));

        NpcMapper.updateEntityFromDto(npc, dto,
                firstnameRepository, lastnameRepository, genderRepository, raceRepository,
                levelRepository, personalityRepository, otherDescriptionRepository,
                likesRepository, dislikesRepository, idealsRepository, kleidungQualiRepository,
                jacketsRepository, trousersRepository, hairstyleRepository, haircolorRepository,
                beardstyleRepository, armorRepository, npcClassRepository, subclassRepository,
                betonungRepository, flawRepository, jewelleryRepository, backgroundRepository,
                talkingStyleRepository, pictureRepository, clanRepository, shopRelationsRepository
        );
        npcRepository.save(npc);
    }

    public Firstname getOrCreateFirstname(String name, Race race, Gender gender) {
        return firstnameRepository.findByFirstnameAndRaceAndGender(name, race, gender)
                .orElseGet(() -> {
                    Firstname f = new Firstname();
                    f.setFirstname(name);
                    f.setRace(race);
                    f.setGender(gender);
                    return firstnameRepository.save(f);
                });
    }

    public Lastname getOrCreateLastname(String name, Race race) {
        return lastnameRepository.findByLastnameAndRace(name, race)
                .orElseGet(() -> {
                    Lastname l = new Lastname();
                    l.setLastname(name);
                    l.setRace(race);
                    return lastnameRepository.save(l);
                });
    }

    private static boolean matchesSearch(Npc npc, String search) {
        if (search == null || search.isEmpty()) return true;
        String s = search.toLowerCase();
        return (npc.getFirstname() != null && npc.getFirstname().getFirstname().toLowerCase().contains(s))
                || (npc.getLastname() != null && npc.getLastname().getLastname().toLowerCase().contains(s));
    }

    private static boolean matchesString(String value, String filter) {
        return filter == null || filter.isEmpty() ||
                (value != null && value.equalsIgnoreCase(filter));
    }

    private static boolean matchesClass(Npc npc, String filterClass) {
        if ("__NONE__".equals(filterClass)) return npc.getNpcClass() == null;
        return filterClass == null || filterClass.isEmpty() ||
                (npc.getNpcClass() != null && npc.getNpcClass().getClassname().equalsIgnoreCase(filterClass));
    }

    private static Stats buildStats(NpcWriteDTO dto, Npc npc) {
        Stats s = new Stats();
        s.setNpc(npc);
        s.setStrength(dto.getStrength() != null ? dto.getStrength() : 0);
        s.setDexterity(dto.getDexterity() != null ? dto.getDexterity() : 0);
        s.setConstitution(dto.getConstitution() != null ? dto.getConstitution() : 0);
        s.setIntelligence(dto.getIntelligence() != null ? dto.getIntelligence() : 0);
        s.setWisdom(dto.getWisdom() != null ? dto.getWisdom() : 0);
        s.setCharisma(dto.getCharisma() != null ? dto.getCharisma() : 0);
        return s;
    }

    private ShopRelations buildShopRelation(NpcWriteDTO dto) {
        ShopRelations relation = new ShopRelations();
        if (dto.getShopId() != null)
            relation.setShop(shopRepository.findById(dto.getShopId())
                    .orElseThrow(() -> new IllegalArgumentException("Shop nicht gefunden: " + dto.getShopId())));
        if (dto.getShopEmployeeRole() != null)
            relation.setShopEmployeeRole(shopEmployeeRepository.findById(dto.getShopEmployeeRole())
                    .orElseThrow(() -> new IllegalArgumentException("ShopEmployee nicht gefunden: " + dto.getShopEmployeeRole())));
        if (dto.getShopCustomerRole() != null)
            relation.setShopCustomerRole(shopCustomerRepository.findById(dto.getShopCustomerRole())
                    .orElseThrow(() -> new IllegalArgumentException("ShopCustomer nicht gefunden: " + dto.getShopCustomerRole())));
        return relation;
    }
}
