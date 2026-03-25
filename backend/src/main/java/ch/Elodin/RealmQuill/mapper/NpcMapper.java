package ch.Elodin.RealmQuill.mapper;

import ch.Elodin.RealmQuill.dto.NpcReadDTO;
import ch.Elodin.RealmQuill.dto.NpcWriteDTO;
import ch.Elodin.RealmQuill.dto.StatsDTO;
import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.enums.EnumSymbol;
import ch.Elodin.RealmQuill.model.npcinfo.Stats;
import ch.Elodin.RealmQuill.model.shop.ShopRelations;
import ch.Elodin.RealmQuill.repository.npcinfo.*;
import ch.Elodin.RealmQuill.repository.shop.ShopRelationsRepository;
import ch.Elodin.RealmQuill.repository.world.ClanRepository;

import java.util.function.Function;

public class NpcMapper {

    // ── Hilfsmethoden ─────────────────────────────────────────

    private static <T> String safe(T o, Function<T, String> ex) {
        return (o == null) ? null : ex.apply(o);
    }

    private static <T> Integer safeInt(T value, Function<T, Integer> extractor) {
        return value == null ? null : extractor.apply(value);
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
            role += " bei " + r.getShop().getName() + " in " + r.getShop().getLocation();
        }
        return role;
    }

    // ── Vollständiges Read-DTO ────────────────────────────────

    public static NpcReadDTO toReadDTO(Npc npc, Stats stats) {
        NpcReadDTO dto = new NpcReadDTO();

        dto.setNpcId(npc.getNpc_ID());
        dto.setFirstname(safe(npc.getFirstname(),  f -> f.getFirstname()));
        dto.setLastname(safe(npc.getLastname(),    l -> l.getLastname()));
        dto.setRace(safe(npc.getRace(),            r -> r.getRacename()));
        dto.setGender(safe(npc.getGender(),        g -> g.getGendername()));
        dto.setNpcClass(safe(npc.getNpcClass(),    c -> c.getClassname()));
        dto.setSubclass(safe(npc.getSubclass(),    s -> s.getSubclassname()));
        dto.setClan(safe(npc.getClan(),            c -> c.getClan()));
        dto.setClanPosition(npc.getClan_position());
        dto.setAge(npc.getNpc_age());
        dto.setLevel(safeInt(npc.getLevel(),       l -> l.getLvl()));
        dto.setHaircolor(safe(npc.getHaircolor(),  h -> h.getName()));
        dto.setHairstyle(safe(npc.getHairstyle(),  h -> h.getName()));
        dto.setBeardstyle(safe(npc.getBeardstyle(),b -> b.getName()));
        dto.setTalkingStyle(safe(npc.getTalkingstyle(), t -> t.getDescription()));
        dto.setJackets(safe(npc.getJackets(),      j -> j.getName()));
        dto.setTrousers(safe(npc.getTrousers(),    t -> t.getName()));
        dto.setKleidungsQuali(safe(npc.getKleidungQuali(), q -> q.getDescription()));
        dto.setJewellery(safe(npc.getJewellery(),  j -> j.getName()));
        // Armor: acValue statt alter getArmor()-Stub-Methode
        dto.setArmor(npc.getArmor_ID() != null ? npc.getArmor_ID().getAcValue() : null);
        dto.setBackground(safe(npc.getBackground(),   b -> b.getName()));
        dto.setPersonality(safe(npc.getPersonality(), p -> p.getDescription()));
        dto.setFlaw(safe(npc.getFlaw(),               f -> f.getName()));
        dto.setLikes(safe(npc.getLikes(),             l -> l.getDescription()));
        dto.setDislikes(safe(npc.getDislikes(),       d -> d.getDescription()));
        dto.setIdeals(safe(npc.getIdeals(),           i -> i.getDescription()));
        dto.setOtherDescription(safe(npc.getOtherDescription(), o -> o.getText()));
        dto.setBetonung(safe(npc.getBetonung(),       b -> b.getBetonung()));
        dto.setShopRelation(getShopRelationText(npc.getShop_relations_ID()));
        // Family: familyName statt alter getFamilienname()-Methode
        dto.setFamily(safe(npc.getNpc_family_ID(),    f -> f.getFamilyName()));
        dto.setPictureUrl(safe(npc.getPicture(),      p -> p.getPicture()));
        dto.setNotes(npc.getNotes());
        dto.setSymbol(npc.getSymbol() != null ? EnumSymbol.valueOf(npc.getSymbol().name()) : null);
        // Neue Felder nach DB-Migration
        dto.setCampaignId(npc.getCampaign() != null ? npc.getCampaign().getId() : null);
        dto.setWorldnoteId(npc.getWorldnoteId());

        if (stats != null) {
            StatsDTO statsDTO = new StatsDTO(
                    stats.getStrength(), stats.getDexterity(), stats.getConstitution(),
                    stats.getIntelligence(), stats.getWisdom(), stats.getCharisma()
            );
            dto.setStats(statsDTO);
        }

        return dto;
    }

    // ── Short-DTO für Listen ──────────────────────────────────

    public static NpcReadDTO toReadDTOShort(Npc npc) {
        NpcReadDTO dto = new NpcReadDTO();
        dto.setNpcId(npc.getNpc_ID());
        dto.setFirstname(safe(npc.getFirstname(), f -> f.getFirstname()));
        dto.setLastname(safe(npc.getLastname(),   l -> l.getLastname()));
        dto.setRace(safe(npc.getRace(),           r -> r.getRacename()));
        dto.setGender(safe(npc.getGender(),       g -> g.getGendername()));
        dto.setAge(npc.getNpc_age());
        dto.setShopRelation(getShopRelationText(npc.getShop_relations_ID()));
        return dto;
    }

    // ── Entity-Update aus WriteDTO ────────────────────────────

    public static void updateEntityFromDto(
            Npc npc, NpcWriteDTO dto,
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
        npc.setNotes(dto.getNotes());
        npc.setSymbol(dto.getSymbol());
        npc.setClan_position(dto.getClan_position());

        // Optional-Felder (null-safe)
        npc.setPersonality(personalityRepository.findById(dto.getPersonalityId()).orElse(null));
        npc.setOtherDescription(otherDescriptionRepository.findById(dto.getOtherDescriptionId()).orElse(null));
        npc.setLikes(likesRepository.findById(dto.getLikesId()).orElse(null));
        npc.setDislikes(dislikesRepository.findById(dto.getDislikesId()).orElse(null));
        npc.setIdeals(idealsRepository.findById(dto.getIdealsId()).orElse(null));
        npc.setKleidungQuali(kleidungQualiRepository.findById(dto.getKleidungQualiId()).orElse(null));
        npc.setJackets(jacketsRepository.findById(dto.getJacketsId()).orElse(null));
        npc.setTrousers(trousersRepository.findById(dto.getTrousersId()).orElse(null));
        npc.setHairstyle(hairstyleRepository.findById(dto.getHairstyleId()).orElse(null));
        npc.setHaircolor(haircolorRepository.findById(dto.getHaircolorId()).orElse(null));
        npc.setBeardstyle(beardstyleRepository.findById(dto.getBeardstyleId()).orElse(null));
        npc.setArmor_ID(armorRepository.findById(dto.getArmorId()).orElse(null));
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
    }
}
