export const getQuestLocation = (quest) => {
    return quest.questlocation?.locationName
        || quest.questlocation?.city?.cityName
        || quest.questlocation?.village?.village_name
        || null;
};

export const getQuestDisplayName = (quest) =>
    quest.questName || quest.questname || "-";