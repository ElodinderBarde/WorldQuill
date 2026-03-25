package ch.Elodin.RealmQuill.dto;
import lombok.*;

@Getter @Setter @NoArgsConstructor
public class ClanDTO {
    private Integer id;
    private String clan;
    private Integer memberCount;
    private String isFamilyClan;
    private Integer locationId;
    private String cityName;
    private String villageName;
    private String clanNotes;
    // Neu nach DB-Migration
    private Integer campaignId;
}
