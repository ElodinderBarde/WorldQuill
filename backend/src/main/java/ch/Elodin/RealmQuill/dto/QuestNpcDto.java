package ch.Elodin.RealmQuill.dto;
import lombok.*;

@Getter @Setter @NoArgsConstructor
public class QuestNpcDto {
    private int questNpcId;
    private int npcId;
    private String npcName;
    private int questId;
    private String questName;
    private String function;
}
