package ch.Elodin.RealmQuill.dto;

import ch.Elodin.RealmQuill.model.enums.EnumQuest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class QuestDto {
    private int questID;
    private String questName;
    private String description;
    private EnumQuest status;
    private String group;
    private Integer price_gold;
    private String price_item;
    private String locationName;
    private String notes;
    private boolean active;
    private int previousQuestId;
}



