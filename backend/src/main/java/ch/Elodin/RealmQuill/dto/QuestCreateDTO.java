package ch.Elodin.RealmQuill.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestCreateDTO {
    public String questname;
    public String description;
    public String group;
    public String price_item;
    public int price_gold;
    public boolean is_active;
    public String status;
    public int questlocationId;
    public Integer previousQuestId;
    public String notes;
}


