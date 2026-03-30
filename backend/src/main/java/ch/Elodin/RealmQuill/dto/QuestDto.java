package ch.Elodin.RealmQuill.dto;
import ch.Elodin.RealmQuill.model.enums.EnumQuest;
import lombok.*;

@Getter @Setter @NoArgsConstructor
public class QuestDto {
    private int questID;
    private String questName;
    private String description;
    private EnumQuest status;
    private String group;
    private Integer priceGold;
    private String priceItem;
    private String locationName;
    private String notes;
    private boolean active;
    private int previousQuestId;
    private int campaignId;
}
