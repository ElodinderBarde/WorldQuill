package ch.Elodin.RealmQuill.dto;
import ch.Elodin.RealmQuill.model.world.Campaign;
import ch.Elodin.RealmQuill.model.world.Location;
import lombok.*;

import javax.net.ssl.SSLSession;

@Getter @Setter @NoArgsConstructor
public class QuestCreateDTO {
    private String questname;
    private String description;
    private String group;
    private String priceItem;
    private int priceGold;
    private boolean isActive;
    private String status;
    private int questlocationId;
    private String questLocation;
    private Integer previousQuestId;
    private Integer campaignId;
    private String notes;
    private Campaign campaign;


}
