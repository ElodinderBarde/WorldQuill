package ch.Elodin.RealmQuill.mapper;
import ch.Elodin.RealmQuill.dto.ClanDTO;
import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.world.Location;
public class ClanMapper {
    public static ClanDTO toClanDTO(Clan clan) {
        ClanDTO dto = new ClanDTO();
        dto.setId(clan.getId());
        dto.setClan(clan.getClan());
        dto.setMemberCount(clan.getMemberCount());
        dto.setClanNotes(clan.getClanNotes());
        dto.setIsFamilyClan(clan.getIsFamilyClan() != null ? clan.getIsFamilyClan().toString() : null);
        Location loc = clan.getLocation();
        if (loc != null) {
            dto.setLocationId(loc.getId());
            if (loc.getCity() != null) dto.setCityName(loc.getCity().getCityName());
            if (loc.getVillage() != null) dto.setVillageName(loc.getVillage().getName());
        }
        return dto;
    }
}