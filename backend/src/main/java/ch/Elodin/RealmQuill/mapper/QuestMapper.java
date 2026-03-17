package ch.Elodin.RealmQuill.mapper;

import ch.Elodin.RealmQuill.dto.QuestCreateDTO;
import ch.Elodin.RealmQuill.dto.QuestDto;
import ch.Elodin.RealmQuill.model.enums.EnumQuest;
import ch.Elodin.RealmQuill.model.Quest;
import ch.Elodin.RealmQuill.model.world.Location;

public class QuestMapper {

    public static QuestDto toDto(Quest quest) {
        QuestDto dto = new QuestDto();

        dto.setQuestID(quest.getQuestID());
        dto.setQuestName(quest.getMonsterName());
        dto.setDescription(quest.getDescription());
        dto.setStatus(quest.getStatus());
        dto.setGroup(quest.getGroup());
        dto.setPrice_gold(quest.getPrice_gold());
        dto.setPrice_item(quest.getPrice_item());
        dto.setNotes(quest.getNotes());
        dto.setActive(quest.isActive());

        dto.setPreviousQuestId(

                quest.getPreviousQuest() != null ? quest.getPreviousQuest().getQuestID() : 0
        );

        Location loc = quest.getQuestlocation();
        if (loc != null) {
            if (loc.getCityID() != null) {
                dto.setLocationName(loc.getCityID().getCity_name());
            } else if (loc.getVillageID() != null) {
                dto.setLocationName(loc.getVillageID().getName());
            }
        }

        return dto;
    }


    public static Quest toEntity(QuestCreateDTO dto) {
        Quest quest = new Quest();
        quest.setMonsterName(dto.getQuestname());
        quest.setDescription(dto.getDescription());
        quest.setGroup(dto.getGroup());
        quest.setPrice_gold(dto.getPrice_gold());
        quest.setPrice_item(dto.getPrice_item());
        quest.setActive(dto.is_active); // Direktzugriff auf public field
        quest.setStatus(EnumQuest.fromString(dto.getStatus()));
        quest.setNotes(dto.getNotes());
        return quest;
    }
}



