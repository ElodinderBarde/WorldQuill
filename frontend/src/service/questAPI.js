const API_BASE_URL = "http://localhost:8081/api/quest";



export async function getActiveQuests() {
    const res = await fetch(`${API_BASE_URL}/active`);
    if (!res.ok) throw new Error("Failed to load active quests");
    return res.json();
}
export async function updateQuestNotes(questId, notes) {
    const res = await fetch(`http://localhost:8081/api/quest/${questId}/notes`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ notes }),
    });

    if (!res.ok) {
        throw new Error("Failed to update quest notes");
    }
}
