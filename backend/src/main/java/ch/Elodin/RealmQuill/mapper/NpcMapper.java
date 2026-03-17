package ch.Elodin.RealmQuill.mapper;

import ch.Elodin.RealmQuill.dto.NpcReadDTO;
import ch.Elodin.RealmQuill.dto.NpcWriteDTO;
import ch.Elodin.RealmQuill.dto.StatsDTO;
import ch.Elodin.RealmQuill.model.enums.EnumSymbol;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.npcinfo.Level;
import ch.Elodin.RealmQuill.model.npcinfo.Stats;
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
import ch.Elodin.RealmQuill.repository.npcinfo.SubclassRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.TalkingStyleRepository;
import ch.Elodin.RealmQuill.repository.npcinfo.TrousersRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopRelationsRepository;
import ch.Elodin.RealmQuill.repository.world.ClanRepository;
import org.springframework.cglib.core.internal.Function;

public class NpcMapper {
    private static String safe(Object o, java.util.function.Function<Object, String> ex) {
        return (o == null) ? null : ex.apply(o);
    }

    private static String getShopRelationText(ShopRelations r) {
        if (r == null) return "Nicht Zugeordnet";

        String role;
        if (r.getShopEmployeeRole() != null) {
            role = "Mitarbeiter: " + r.getShopEmployeeRole().getPosition();
        } else if (r.getShopCustomerRole() != null) {
            role = "Kunde: " + r.getShopCustomerRole().getPosition();
        } else {
            role = "unbekannt";
        }

        if (r.getShop() != null) {
            role += " bei " + r.getShop().getName() +
                    " in " + r.getShop().getLocation();
        }

        return role;
    }

    // ------------------------------------------------------------
    //  VollstÃ¤ndiges NPC-DTO
    // ------------------------------------------------------------


    private static <T> String safeString(T value, Function<T, String> extractor) {
        return value == null ? null : extractor.apply(value);
    }

    private static <T> Integer safeInt(T value, Function<T, Integer> extractor) {
        return value == null ? null : extractor.apply(value);
    }
    private static <T> String safe(T o, Function<T, String> ex) {
        return (o == null) ? null : ex.apply(o);
    }


    public static NpcReadDTO toReadDTO(Npc npc, Stats stats) {
        NpcReadDTO dto = new NpcReadDTO();

        dto.setNpcId(npc.getNpc_ID());
        dto.setFirstname(safe((Object) npc.getFirstname(), (java.util.function.Function<Object, String>) f -> npc.getFirstname().getFirstname()));
        dto.setLastname(safe((Object) npc.getLastname(), (java.util.function.Function<Object, String>) f -> npc.getLastname().getLastname()));
        dto.setRace(safe((Object) npc.getRace(), (java.util.function.Function<Object, String>) r -> npc.getRace().getRacename()));
        dto.setGender(safe((Object) npc.getGender(), (java.util.function.Function<Object, String>) g -> npc.getGender().getGendername()));
        dto.setNpcClass(safe((Object) npc.getNpcClass(), (java.util.function.Function<Object, String>) c -> npc.getNpcClass().getClassname()));
        dto.setSubclass(safe((Object) npc.getSubclass(), (java.util.function.Function<Object, String>) s -> npc.getSubclass().getSubclassname()));
        dto.setClan(safe((Object) npc.getClan(), (java.util.function.Function<Object, String>) c -> npc.getClan().getClan()));

        dto.setClanPosition(npc.getClan_position());
        dto.setAge(npc.getNpc_age());
        dto.setLevel(safeInt(npc.getLevel(), Level::getLvl));

        dto.setHaircolor(safe((Object) npc.getHaircolor(), (java.util.function.Function<Object, String>) h -> npc.getHaircolor().getName()));
        dto.setHairstyle(safe((Object) npc.getHairstyle(), (java.util.function.Function<Object, String>) h -> npc.getHairstyle().getName()));
        dto.setBeardstyle(safe((Object) npc.getBeardstyle(), (java.util.function.Function<Object, String>) b -> npc.getBeardstyle().getName()));
        dto.setTalkingStyle(safe((Object) npc.getTalkingstyle(), (java.util.function.Function<Object, String>) t -> npc.getTalkingstyle().getDescription()));
        dto.setJackets(safe((Object) npc.getJackets(), (java.util.function.Function<Object, String>) j -> npc.getJackets().getName()));
        dto.setTrousers(safe((Object) npc.getTrousers(), (java.util.function.Function<Object, String>) t -> npc.getTrousers().getName()));
        dto.setKleidungsQuali(safe((Object) npc.getKleidungQuali(), (java.util.function.Function<Object, String>) q -> npc.getKleidungQuali().getDescription()));
        dto.setJewellery(safe((Object) npc.getJewellery(), (java.util.function.Function<Object, String>) j -> npc.getJewellery().getName()));
        dto.setArmor(npc.getArmor_ID() != null? npc.getArmor_ID().getArmor(): null);
        dto.setBackground(safe((Object) npc.getBackground(), (java.util.function.Function<Object, String>) b -> npc.getBackground().getName()));
        dto.setPersonality(safe((Object) npc.getPersonality(), (java.util.function.Function<Object, String>) p -> npc.getPersonality().getDescription()));
        dto.setFlaw(safe((Object) npc.getFlaw(), (java.util.function.Function<Object, String>) f -> npc.getFlaw().getName()));
        dto.setLikes(safe((Object) npc.getLikes(), (java.util.function.Function<Object, String>) l -> npc.getLikes().getDescription()));
        dto.setDislikes(safe((Object) npc.getDislikes(), (java.util.function.Function<Object, String>) d -> npc.getDislikes().getDescription()));
        dto.setIdeals(safe((Object) npc.getIdeals(), (java.util.function.Function<Object, String>) i -> npc.getIdeals().getDescription()));
        dto.setOtherDescription(safe((Object) npc.getOtherDescription(), (java.util.function.Function<Object, String>) o -> npc.getOtherDescription().getText()));
        dto.setBetonung(safe((Object) npc.getBetonung(), (java.util.function.Function<Object, String>) b -> npc.getBetonung().getBetonung()));

        dto.setShopRelation(getShopRelationText(npc.getShop_relations_ID()));

        // Stats
        if (stats != null) {
            StatsDTO statsDTO = new StatsDTO();
            statsDTO.setStrength(stats.getStrength());
            statsDTO.setDexterity(stats.getDexterity());
            statsDTO.setConstitution(stats.getConstitution());
            statsDTO.setIntelligence(stats.getIntelligence());
            statsDTO.setWisdom(stats.getWisdom());
            statsDTO.setCharisma(stats.getCharisma());
            dto.setStats(statsDTO);
        }else {
            dto.setStats(null);
        }



        dto.setFamily(safe((Object) npc.getNpc_family_ID(), (java.util.function.Function<Object, String>) f -> npc.getNpc_family_ID().getFamilienname()));
        dto.setPictureUrl(safe((Object) npc.getPicture(), (java.util.function.Function<Object, String>) p -> npc.getPicture().getPicture()));
        dto.setNotes(npc.getNotes());

        dto.setSymbol(npc.getSymbol() != null ? EnumSymbol.valueOf(npc.getSymbol().name()) : null);

        return dto;
    }

    // ------------------------------------------------------------
    //  Short-DTO fÃ¼r Listen
    // ------------------------------------------------------------

    public static NpcReadDTO toReadDTOShort(Npc npc) {
        NpcReadDTO dto = new NpcReadDTO();

        dto.setNpcId(npc.getNpc_ID());
        dto.setFirstname(safe((Object) npc.getFirstname(), (java.util.function.Function<Object, String>) f -> npc.getFirstname().getFirstname()));
        dto.setLastname(safe((Object) npc.getLastname(), (java.util.function.Function<Object, String>) f -> npc.getLastname().getLastname()));
        dto.setRace(safe((Object) npc.getRace(), (java.util.function.Function<Object, String>) r -> npc.getRace().getRacename()));
        dto.setGender(safe((Object) npc.getGender(), (java.util.function.Function<Object, String>) g -> npc.getGender().getGendername()));
        dto.setAge(npc.getNpc_age());
        dto.setShopRelation(getShopRelationText(npc.getShop_relations_ID()));

        return dto;
    }

    // ------------------------------------------------------------
    //  Entity-Update aus WriteDTO
    // ------------------------------------------------------------

    public static void updateEntityFromDto(
            Npc npc,
            NpcWriteDTO dto,
            FirstnameRepository firstnameRepository,
            LastnameRepository lastnameRepository,
            GenderRepository genderRepository,
            RaceRepository raceRepository,
            LevelRepository levelRepository,
            PersonalityRepository personalityRepository,
            OtherDescriptionRepository otherDescriptionRepository,
            LikesRepository likesRepository,
            DislikesRepository dislikesRepository,
            IdealsRepository idealsRepository,
            KleidungQualiRepository kleidungQualiRepository,
            JacketsRepository jacketsRepository,
            TrousersRepository trousersRepository,
            HairstyleRepository hairstyleRepository,
            HaircolorRepository haircolorRepository,
            BeardstyleRepository beardstyleRepository,
            ArmorRepository armorRepository,
            NpcClassRepository npcClassRepository,
            SubclassRepository subclassRepository,
            BetonungRepository betonungRepository,
            FlawRepository flawRepository,
            JewelleryRepository jewelleryRepository,
            BackgroundRepository backgroundRepository,
            TalkingStyleRepository talkingStyleRepository,
            PictureRepository pictureRepository,
            ClanRepository clanRepository,
            ShopRelationsRepository shopRelationsRepository
    ) {

        npc.setFirstname(firstnameRepository.findById(dto.getFirstnameId()).orElseThrow());
        npc.setLastname(lastnameRepository.findById(dto.getLastnameId()).orElseThrow());
        npc.setNpc_age(dto.getNpc_age());
        npc.setGender(genderRepository.findById(dto.getGenderId()).orElseThrow());
        npc.setRace(raceRepository.findById(dto.getRaceId()).orElseThrow());
        npc.setLevel(levelRepository.findById(dto.getLevelId()).orElseThrow());
        npc.setPersonality(personalityRepository.findById(dto.getPersonalityId()).orElseThrow());
        npc.setOtherDescription(otherDescriptionRepository.findById(dto.getOtherDescriptionId()).orElseThrow());
        npc.setLikes(likesRepository.findById(dto.getLikesId()).orElseThrow());
        npc.setDislikes(dislikesRepository.findById(dto.getDislikesId()).orElseThrow());
        npc.setIdeals(idealsRepository.findById(dto.getIdealsId()).orElseThrow());
        npc.setKleidungQuali(kleidungQualiRepository.findById(dto.getKleidungQualiId()).orElseThrow());
        npc.setJackets(jacketsRepository.findById(dto.getJacketsId()).orElseThrow());
        npc.setTrousers(trousersRepository.findById(dto.getTrousersId()).orElseThrow());
        npc.setHairstyle(hairstyleRepository.findById(dto.getHairstyleId()).orElseThrow());
        npc.setHaircolor(haircolorRepository.findById(dto.getHaircolorId()).orElseThrow());
        npc.setBeardstyle(beardstyleRepository.findById(dto.getBeardstyleId()).orElseThrow());
        npc.setArmor_ID(armorRepository.findById(dto.getArmorId()).orElseThrow());
        npc.setNpcClass(npcClassRepository.findById(dto.getClassId()).orElse(null));
        npc.setSubclass(subclassRepository.findById(dto.getSubclassId()).orElse(null));
        npc.setBetonung(betonungRepository.findById(dto.getBetonungId()).orElse(null));
        npc.setFlaw(flawRepository.findById(dto.getFlawId()).orElse(null));
        npc.setJewellery(jewelleryRepository.findById(dto.getJewelleryId()).orElse(null));
        npc.setBackground(backgroundRepository.findById(dto.getBackgroundId()).orElse(null));
        npc.setTalkingstyle(talkingStyleRepository.findById(dto.getTalkingstyleId()).orElse(null));
        npc.setPicture(pictureRepository.findById(dto.getPictureId()).orElse(null));
        npc.setClan(clanRepository.findById(dto.getClanId()).orElse(null));
        npc.setShop_relations_ID(shopRelationsRepository.findById(dto.getShopRelationsId()).orElse(null));
        npc.setClan_position(dto.getClan_position());
        npc.setSymbol(dto.getSymbol());
        npc.setNotes(dto.getNotes());
    }
}



