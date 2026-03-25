package ch.Elodin.RealmQuill.dto;
import lombok.*;

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
    private Integer previousQuestId;
    private String notes;
}
