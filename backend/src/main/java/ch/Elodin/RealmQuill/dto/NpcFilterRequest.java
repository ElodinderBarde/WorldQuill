package ch.Elodin.RealmQuill.dto;
import lombok.*;

@Getter @Setter @NoArgsConstructor
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
    private Integer campaignId;
}
