package ch.Elodin.RealmQuill.mapper;
import ch.Elodin.RealmQuill.dto.QuestNpcDto;
import ch.Elodin.RealmQuill.model.QuestNpc;
public class QuestNpcMapper {
    public static QuestNpcDto toDto(QuestNpc questNpc) {
        QuestNpcDto dto = new QuestNpcDto();
        dto.setQuestNpcId(questNpc.getQuestNpcId());
        dto.setNpcId(questNpc.getNpc().getNpc_ID());
        String fn = questNpc.getNpc().getFirstname() != null ? questNpc.getNpc().getFirstname().getFirstname() : "";
        String ln = questNpc.getNpc().getLastname() != null ? questNpc.getNpc().getLastname().getLastname() : "";
        dto.setNpcName((fn + " " + ln).trim());
        dto.setQuestId(questNpc.getQuest().getQuestID());
        dto.setQuestName(questNpc.getQuest().getQuestName());
        dto.setFunction(questNpc.getFunction());
        return dto;
    }
}