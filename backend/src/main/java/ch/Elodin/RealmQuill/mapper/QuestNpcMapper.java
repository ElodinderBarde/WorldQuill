package ch.Elodin.RealmQuill.mapper;

import ch.Elodin.RealmQuill.dto.QuestNpcDto;
import ch.Elodin.RealmQuill.model.npcinfo.Firstname;
import ch.Elodin.RealmQuill.model.npcinfo.Lastname;
import ch.Elodin.RealmQuill.model.QuestNpc;

public class QuestNpcMapper {
    public static QuestNpcDto toDto(QuestNpc questNpc) {
        QuestNpcDto dto = new QuestNpcDto();
        dto.setQuestNpcId(questNpc.getQuestNpcId());
        dto.setNpcId(questNpc.getNpc().getNpc_ID());

        String firstname = questNpc.getNpc().getFirstname() != null
                ? questNpc.getNpc().getFirstname().getFirstname()
                : "";
        String lastname = questNpc.getNpc().getLastname() != null
                ? questNpc.getNpc().getLastname().getLastname()
                : "";
        dto.setNpcName(STR."\{firstname} \{lastname}".trim());



        dto.setQuestId(questNpc.getQuest().getQuestID());
        dto.setQuestName(questNpc.getQuest().getMonsterName());
        dto.setFunction(questNpc.getFunction());
        return dto;
    }
}



