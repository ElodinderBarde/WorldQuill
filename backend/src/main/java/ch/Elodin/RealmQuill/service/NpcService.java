package ch.Elodin.RealmQuill.service;

import ch.Elodin.RealmQuill.dto.NpcFilterRequest;
import ch.Elodin.RealmQuill.dto.NpcReadDTO;
import ch.Elodin.RealmQuill.dto.NpcWriteDTO;
import ch.Elodin.RealmQuill.mapper.NpcMapper;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.npcinfo.Firstname;
import ch.Elodin.RealmQuill.model.npcinfo.Gender;
import ch.Elodin.RealmQuill.model.npcinfo.Lastname;
import ch.Elodin.RealmQuill.model.npcinfo.Level;
import ch.Elodin.RealmQuill.model.npcinfo.Race;
import ch.Elodin.RealmQuill.model.npcinfo.Stats;
import ch.Elodin.RealmQuill.model.shop.Shop;
import ch.Elodin.RealmQuill.model.shop.ShopCustomer;
import ch.Elodin.RealmQuill.model.shop.ShopEmployee;
import ch.Elodin.RealmQuill.model.shop.ShopRelations;
import ch.Elodin.RealmQuill.repository.npcinfo.ArmorRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.BackgroundRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.BeardstyleRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.BetonungRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.DislikesRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.FirstnameRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.FlawRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.GenderRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.HaircolorRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.HairstyleRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.IdealsRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.JacketsRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.JewelleryRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.KleidungQualiRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.LastnameRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.LevelRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.LikesRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.NpcClassRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.OtherDescriptionRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.PersonalityRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.PictureRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.RaceRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.StatsRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.SubclassRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.TalkingStyleRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.TrousersRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopCustomerRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopEmployeeRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopRelationsRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopRepository;
import ch.Elodin.RealmQuill.repository.world.ClanRepository;
import ch.Elodin.RealmQuill.repository.world.NpcRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ch.Elodin.RealmQuill.service.SaveService.resolveAndSet;

@SuppressWarnings("ALL")
@Service
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

    public NpcService(NpcClassRepository npcClassRepository,NpcRepository npcRepository, StatsRepository statsRepository,
                      FirstnameRepository firstnameRepository, RaceRepository raceRepository, LevelRepository levelRepository,
                      LastnameRepository lastnameRepository, GenderRepository genderRepository, PersonalityRepository personalityRepository,
                      OtherDescriptionRepository otherDescriptionRepository, LikesRepository likesRepository, DislikesRepository dislikesRepository,
                      IdealsRepository idealsRepository, KleidungQualiRepository kleidungQualiRepository, JacketsRepository jacketsRepository,
                      TrousersRepository trousersRepository, HairstyleRepository hairstyleRepository, HaircolorRepository haircolorRepository,
                      BeardstyleRepository beardstyleRepository, ArmorRepository armorRepository, SubclassRepository subclassRepository,
                      BetonungRepository betonungRepository,FlawRepository flawRepository,BackgroundRepository backgroundRepository,
                      TalkingStyleRepository talkingStyleRepository,PictureRepository pictureRepository,ClanRepository clanRepository,
                       JewelleryRepository  jewelleryRepository) {
        this.npcRepository = npcRepository;
        this.statsRepository = statsRepository;
        this.firstnameRepository = firstnameRepository;
        this.raceRepository = raceRepository;
        this.levelRepository = levelRepository;
        this.lastnameRepository = lastnameRepository;
        this.genderRepository = genderRepository;
        this.personalityRepository = personalityRepository;
        this.otherDescriptionRepository = otherDescriptionRepository;
        this.likesRepository = likesRepository;
        this.dislikesRepository = dislikesRepository;
        this.idealsRepository = idealsRepository;
        this.kleidungQualiRepository = kleidungQualiRepository;
        this.jacketsRepository = jacketsRepository;
        this.trousersRepository = trousersRepository;
        this.hairstyleRepository = hairstyleRepository;
        this.haircolorRepository = haircolorRepository;
        this.beardstyleRepository = beardstyleRepository;
        this.armorRepository = armorRepository;
        this.subclassRepository = subclassRepository;
        this.npcClassRepository = npcClassRepository;
        this.betonungRepository = betonungRepository;
        this.flawRepository = flawRepository;
        this.backgroundRepository = backgroundRepository;
        this.jewelleryRepository = jewelleryRepository;
        this.talkingStyleRepository = talkingStyleRepository;
        this.pictureRepository = pictureRepository;
        this.clanRepository = clanRepository;
    }

    @Autowired
    private ShopRelationsRepository shopRelationsRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopEmployeeRepository shopEmployeeRepository;

    @Autowired
    private ShopCustomerRepository shopCustomerRepository;


    public List<NpcReadDTO> getAllNpcs() {
        return npcRepository.findAll().stream()
                .map(npc -> {
                    Stats stats = statsRepository.findByNpc(npc).orElse(null);
                    return NpcMapper.toReadDTO(npc, stats);
                })
                .collect(Collectors.toList());
    }


    public List<NpcReadDTO> getFilteredNpcs(NpcFilterRequest filter) {
        return npcRepository.findAll().stream()
                .filter(npc -> filter.getSearch() == null || filter.getSearch().isEmpty() ||
                        (npc.getFirstname() != null && npc.getFirstname().getFirstname().toLowerCase().contains(filter.getSearch().toLowerCase())) ||
                        (npc.getLastname() != null && npc.getLastname().getLastname().toLowerCase().contains(filter.getSearch().toLowerCase())))
                .filter(npc -> filter.getRace() == null || filter.getRace().isEmpty() ||
                        (npc.getRace() != null && npc.getRace().getRacename().equalsIgnoreCase(filter.getRace())))
                .filter(npc -> {
                    if ("__NONE__".equals(filter.getNpcClass())) {
                        return npc.getNpcClass() == null;
                    }
                    return filter.getNpcClass() == null || filter.getNpcClass().isEmpty()
                            || (npc.getNpcClass() != null && npc.getNpcClass().getClassname().equalsIgnoreCase(filter.getNpcClass()));
                })

                .filter(npc -> filter.getSubclass() == null || filter.getSubclass().isEmpty() ||
                        (npc.getSubclass() != null && npc.getSubclass().getSubclassname().equalsIgnoreCase(filter.getSubclass())))
                .filter(npc -> filter.getClan() == null || filter.getClan().isEmpty() ||
                        (npc.getClan() != null && npc.getClan().getClan().equalsIgnoreCase(filter.getClan())))
                .filter(npc -> filter.getClanPosition() == null || filter.getClanPosition().isEmpty() ||
                        (npc.getClan_position() != null && npc.getClan_position().equalsIgnoreCase(filter.getClanPosition())))
                .filter(npc -> filter.getHasPicture() == null ||
                        (filter.getHasPicture() && npc.getPicture() != null) ||
                        (!filter.getHasPicture() && npc.getPicture() == null))
                .filter(npc -> filter.getHasShop() == null ||
                        (filter.getHasShop() && npc.getShop_relations_ID() != null) ||
                        (!filter.getHasShop() && npc.getShop_relations_ID() == null))
                .filter(npc -> filter.getSymbol() == null || filter.getSymbol().isEmpty() ||
                        (npc.getSymbol() != null && npc.getSymbol().toString().equalsIgnoreCase(filter.getSymbol())))



                .filter(npc -> filter.getLocationId() == null ||
                (npc.getShop_relations_ID() != null &&
                        npc.getShop_relations_ID().getShop() != null &&
                        npc.getShop_relations_ID().getShop().getLocation() != null &&
                        npc.getShop_relations_ID().getShop().getLocation().getId() == filter.getLocationId()))

                .map(npc -> {
                    Stats stats = statsRepository.findByNpc(npc).orElse(null);
                    return NpcMapper.toReadDTO(npc, stats);
                })


                .collect(Collectors.toList());
    }

    public NpcReadDTO getNpcById(int id) {
        Npc npc = npcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(STR."NPC nicht gefunden mit ID: \{id}"));

        Stats stats = statsRepository.findByNpc(npc)
                .orElseGet(() -> statsRepository.findByNpc(npcRepository.findById(1).orElse(null)).orElse(null));

        return NpcMapper.toReadDTO(npc, stats);
    }

    public <ShopRelation> void createNpc(NpcWriteDTO dto) {
        Npc npc = new Npc();

        // Pflichtfeld: Race
        Race race = raceRepository.findById(dto.getRaceId())
                .orElseThrow(() -> new IllegalArgumentException(STR."Rasse mit ID \{dto.getRaceId()} nicht gefunden."));

        // Pflichtfeld: Gender
        Gender gender = genderRepository.findById(dto.getGenderId())
                .orElseThrow(() -> new IllegalArgumentException(STR."Geschlecht mit ID \{dto.getGenderId()} nicht gefunden."));

        // Firstname validieren
        if (dto.getFirstnameId() == null) {
            throw new IllegalArgumentException("Vorname darf nicht null sein.");
        }
        Firstname fname = firstnameRepository.findById(dto.getFirstnameId())
                .orElseThrow(() -> new IllegalArgumentException(STR."Vorname mit ID \{dto.getFirstnameId()} nicht gefunden."));

        if (!fname.getRace().equals(race)) {
            throw new IllegalArgumentException("Vorname gehÃ¶rt nicht zur angegebenen Rasse.");
        }
        if (!fname.getGender().equals(gender)) {
            throw new IllegalArgumentException("Vorname passt nicht zum angegebenen Geschlecht.");
        }

        // Lastname validieren
        if (dto.getLastnameId() == null) {
            throw new IllegalArgumentException("Nachname darf nicht null sein.");
        }
        Lastname lname = lastnameRepository.findById(dto.getLastnameId())
                .orElseThrow(() -> new IllegalArgumentException(STR."Nachname mit ID \{dto.getLastnameId()} nicht gefunden."));

        if (!lname.getRace().equals(race)) {
            throw new IllegalArgumentException("Nachname gehÃ¶rt nicht zur angegebenen Rasse.");
        }

        // Level validieren
        Level level = levelRepository.findById(dto.getLevelId())
                .orElseThrow(() -> new IllegalArgumentException(STR."Level mit ID \{dto.getLevelId()} nicht gefunden."));

        // Zuordnung zur Entity
        npc.setFirstname(fname);
        npc.setLastname(lname);
        npc.setRace(race);
        npc.setGender(gender);
        npc.setLevel(level);
        npc.setNotes(dto.getNotes());
        npc.setNpc_age(dto.getNpc_age());
        npc.setSymbol(dto.getSymbol());


// Verwendung in createNpc:
        resolveAndSet(dto.getPersonalityId(), personalityRepository::findById, npc::setPersonality);
        resolveAndSet(dto.getOtherDescriptionId(), otherDescriptionRepository::findById, npc::setOtherDescription);
        resolveAndSet(dto.getLikesId(), likesRepository::findById, npc::setLikes);
        resolveAndSet(dto.getDislikesId(), dislikesRepository::findById, npc::setDislikes);
        resolveAndSet(dto.getIdealsId(), idealsRepository::findById, npc::setIdeals);
        resolveAndSet(dto.getKleidungQualiId(), kleidungQualiRepository::findById, npc::setKleidungQuali);
        resolveAndSet(dto.getJacketsId(), jacketsRepository::findById, npc::setJackets);
        resolveAndSet(dto.getTrousersId(), trousersRepository::findById, npc::setTrousers);
        resolveAndSet(dto.getHairstyleId(), hairstyleRepository::findById, npc::setHairstyle);
        resolveAndSet(dto.getHaircolorId(), haircolorRepository::findById, npc::setHaircolor);
        resolveAndSet(dto.getBeardstyleId(), beardstyleRepository::findById, npc::setBeardstyle);
        resolveAndSet(dto.getShopRelationsId(), shopRelationsRepository::findById, npc::setShop_relations_ID);
        //resolveAndSet(dto.getClanId(), ClanRepository::findById, npc::setClan);
        //resolveAndSet(dto.getClan_position(), clanRepository::findByClan, npc::setClan_position);
        resolveAndSet(dto.getBetonungId(), betonungRepository::findById, npc::setBetonung);
        resolveAndSet(dto.getClassId(), npcClassRepository::findById, npc::setNpcClass);
        resolveAndSet(dto.getSubclassId(), subclassRepository::findById, npc::setSubclass);
        resolveAndSet(dto.getJewelleryId(), jewelleryRepository::findById, npc::setJewellery);
        resolveAndSet(dto.getPictureId(), pictureRepository::findById, npc::setPicture);
        resolveAndSet(dto.getFlawId(), flawRepository::findById, npc::setFlaw);
        resolveAndSet(dto.getTalkingstyleId(), talkingStyleRepository::findById, npc::setTalkingstyle);
        resolveAndSet(dto.getBackgroundId(), backgroundRepository::findById, npc::setBackground);
        resolveAndSet(dto.getArmorId(), armorRepository::findById, npc::setArmor_ID);






        npcRepository.save(npc);
        Npc savedNpc = npcRepository.save(npc);

        if (dto.getStrength() != null || dto.getDexterity() != null || dto.getConstitution() != null ||
                dto.getIntelligence() != null || dto.getWisdom() != null || dto.getCharisma() != null) {

            Stats stats = getStats(dto, savedNpc);

            statsRepository.save(stats);
        }

        if (dto.getShopId() != null || dto.getShopEmployeeRole() != null || dto.getShopCustomerRole() != null) {
            ShopRelations relation = getShopRelation(dto);
            relation = shopRelationsRepository.save(relation);
            npc.setShop_relations_ID(relation);
        }

        npcRepository.save(npc);


    }

    private ShopRelations getShopRelation(NpcWriteDTO dto) {
        ShopRelations relation = new ShopRelations();

        if (dto.getShopId() != null) {
            Shop shop = shopRepository.findById(dto.getShopId())
                    .orElseThrow(() -> new IllegalArgumentException(STR."Shop mit ID \{dto.getShopId()} nicht gefunden"));
            relation.setShop(shop);
        }

        if (dto.getShopEmployeeRole() != null) {
            ShopEmployee employee = shopEmployeeRepository.findById(dto.getShopEmployeeRole())
                    .orElseThrow(() -> new IllegalArgumentException(STR."ShopEmployee mit ID \{dto.getShopEmployeeRole()} nicht gefunden"));
            relation.setShopEmployeeRole(employee);
        }

        if (dto.getShopCustomerRole() != null) {
            ShopCustomer customer = shopCustomerRepository.findById(dto.getShopCustomerRole())
                    .orElseThrow(() -> new IllegalArgumentException(STR."ShopCustomer mit ID \{dto.getShopCustomerRole()} nicht gefunden"));
            relation.setShopCustomerRole(customer);
        }

        return relation;
    }





    private static Stats getStats(NpcWriteDTO dto, Npc savedNpc) {
        Stats stats = new Stats();

        stats.setNpc(savedNpc); // Verbindung herstellen
        stats.setStrength(dto.getStrength() != null ? dto.getStrength() : 0);
        stats.setDexterity(dto.getDexterity() != null ? dto.getDexterity() : 0);
        stats.setConstitution(dto.getConstitution() != null ? dto.getConstitution() : 0);
        stats.setIntelligence(dto.getIntelligence() != null ? dto.getIntelligence() : 0);
        stats.setWisdom(dto.getWisdom() != null ? dto.getWisdom() : 0);
        stats.setCharisma(dto.getCharisma() != null ? dto.getCharisma() : 0);
        return stats;
    }

    public void updateNpc(int id, NpcWriteDTO dto) {
        Npc npc = npcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(STR."NPC nicht gefunden mit ID: \{id}"));

        NpcMapper.updateEntityFromDto(npc, dto,
                firstnameRepository, lastnameRepository, genderRepository, raceRepository, levelRepository,
                personalityRepository, otherDescriptionRepository, likesRepository, dislikesRepository,
                idealsRepository, kleidungQualiRepository, jacketsRepository, trousersRepository,
                hairstyleRepository, haircolorRepository, beardstyleRepository, armorRepository, npcClassRepository, subclassRepository,
                betonungRepository,flawRepository,jewelleryRepository, backgroundRepository, talkingStyleRepository, pictureRepository,
                clanRepository, shopRelationsRepository
        );

        npcRepository.save(npc);
    }
    public Firstname getOrCreateFirstname(String name, Race race, Gender gender) {
        return firstnameRepository.findByFirstnameAndRaceAndGender(name, race, gender)
                .orElseGet(() -> {
                    Firstname newEntry = new Firstname();
                    newEntry.setFirstname(name);
                    newEntry.setRace(race);
                    newEntry.setGender(gender);
                    return firstnameRepository.save(newEntry);
                });
    }

    public Lastname getOrCreateLastname(String name, Race race) {
        return lastnameRepository.findByLastnameAndRace(name, race)
                .orElseGet(() -> {
                    Lastname newEntry = new Lastname();
                    newEntry.setLastname(name);
                    newEntry.setRace(race);
                    return lastnameRepository.save(newEntry);
                });
    }


    public List<NpcReadDTO> getNpcsByClanId(int clanId) {
        return npcRepository.findByClanId(clanId).stream()
                .map(npc -> {
                    Stats stats = statsRepository.findByNpc(npc).orElse(null);
                    return NpcMapper.toReadDTO(npc, stats);
                })
                .collect(Collectors.toList());
    }
}



