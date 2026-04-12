import { useEffect, useState } from "react";
import { getActiveQuests, updateQuestNotes } from "@/service/questAPI";
import "./QuestNotes.css";


export default function QuestNotes({ activeQuestId }) {
    const [quests, setQuests] = useState([]);
    const [notes, setNotes] = useState("");
    const [currentQuest, setCurrentQuest] = useState(null);

    useEffect(() => {
        getActiveQuests().then(setQuests);
    }, []);

    // 👉 Reagiert IMMER auf aktive Quest
    useEffect(() => {
        const quest = quests.find(q => q.questID === activeQuestId);
        setCurrentQuest(quest || null);
        setNotes(quest?.notes || "");
    }, [activeQuestId, quests]);

    if (!currentQuest) {
        return <div>Keine aktive Quest ausgewählt</div>;
    }

    const handleSave = async () => {
        await updateQuestNotes(currentQuest.questID, notes);

        setQuests(prev =>
            prev.map(q =>
                q.questID === currentQuest.questID
                    ? { ...q, notes }
                    : q
            )
        );
    };

    return (
        <div className="quest-notes-panel">
            <textarea
                value={notes}
                onChange={e => setNotes(e.target.value)}
                className="quest-notes-textarea"
            />

            <button onClick={handleSave}>
                Speichern
            </button>
        </div>
    );
}
