package ch.Elodin.RealmQuill.mapper;
import ch.Elodin.RealmQuill.dto.QuestCreateDTO;
import ch.Elodin.RealmQuill.dto.QuestDto;
import ch.Elodin.RealmQuill.model.Quest;
import ch.Elodin.RealmQuill.model.enums.EnumQuest;
import ch.Elodin.RealmQuill.model.world.Location;
public class QuestMapper {
    public static QuestDto toDto(Quest quest) {
        QuestDto dto = new QuestDto();
        dto.setQuestID(quest.getQuestID());
        dto.setQuestName(quest.getQuestName());
        dto.setDescription(quest.getDescription());
        dto.setStatus(quest.getStatus());
        dto.setGroup(quest.getGroup());
        dto.setPriceGold(quest.getPrice_gold());
        dto.setPriceItem(quest.getPrice_item());
        dto.setNotes(quest.getNotes());
        dto.setActive(quest.isActive());
        dto.setPreviousQuestId(quest.getPreviousQuest() != null ? quest.getPreviousQuest().getQuestID() : 0);
        Location loc = quest.getQuestlocation();
        dto.setCampaignId(quest.getCampaign().getId());
        if (loc != null) {
            if (loc.getCity() != null) dto.setLocationName(loc.getCity().getCityName());
            else if (loc.getVillage() != null) dto.setLocationName(loc.getVillage().getName());
        }
        return dto;
    }
    public static Quest toEntity(QuestCreateDTO dto) {
        Quest quest = new Quest();
        quest.setQuestName(dto.getQuestname());
        quest.setDescription(dto.getDescription());
        quest.setGroup(dto.getGroup());
        quest.setPrice_gold(dto.getPriceGold());
        quest.setPrice_item(dto.getPriceItem());
        quest.setActive(dto.isActive());
        quest.setStatus(EnumQuest.fromString(dto.getStatus()));
        quest.setNotes(dto.getNotes());
        quest.setCampaign(dto.getCampaign());
        return quest;
    }

}