package ch.Elodin.RealmQuill.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class QuestNpcDto {
    private int questNpcId;
    private int npcId;
    private String npcName; // optional
    private int questId;
    private String questName; // optional
    private String function;
}


