package ch.Elodin.RealmQuill.mapper;

import ch.Elodin.RealmQuill.dto.ClanDTO;
import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.world.Location;

public interface ClanMapper {



    public static ClanDTO toClanDTO(Clan clan) {

        ClanDTO dto = new ClanDTO();

        dto.setId(clan.getId());
        dto.setClan(clan.getClan());
        dto.setMitglieder(clan.getMitglieder());
        dto.setClanNotes(clan.getClanNotes());
        dto.getClanNotes(clan.getClanNotes());
        dto.setFamilienclan(
                clan.getFamilienclan() != null ? clan.getFamilienclan().toString() : null
        );

        Location loc = clan.getLocation();

        if (loc != null) {
            dto.setLocationId(loc.getId());

            if (loc.getCityID() != null) {
                dto.setCityName(loc.getCityID().getCity_name());
            }

            if (loc.getVillageID() != null) {
                dto.setVillageName(loc.getVillageID().getName());
            }
        }

        return dto;
    }




}



