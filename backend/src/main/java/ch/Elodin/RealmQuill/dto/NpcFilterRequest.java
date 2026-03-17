package ch.Elodin.RealmQuill.dto;


import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.npcinfo.NpcClass;
import ch.Elodin.RealmQuill.model.npcinfo.Race;
import ch.Elodin.RealmQuill.model.npcinfo.Subclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NpcFilterRequest {
    private String search;
    private String race;
    private String npcClass;
    private String subclass;
    private String clan;
    private String clanPosition;
    private Boolean hasPicture;
    private Boolean hasShop;
    private String symbol;
    private Integer locationId;
    // Getter/Setter




}



