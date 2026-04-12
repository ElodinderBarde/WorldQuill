import { useEffect, useState } from "react";
import { getActiveQuests } from "@/service/questAPI";
import "./QuestInfo.css";



export default function ActiveQuestPanel({ activeQuestId, onSelectQuest }) {
    const [quests, setQuests] = useState([]);

    useEffect(() => {
        getActiveQuests().then(qs => {
            setQuests(qs);
            if (!activeQuestId && qs.length > 0) {
                onSelectQuest(qs[0].questID); // Initiale Auswahl
            }
        });
    }, []);

    const quest = quests.find(q => q.questID === activeQuestId);

    if (!quest) {
        return <div>Keine aktive Quest</div>;
    }

    return (
        <div className="quest-panel">
            <div className="quest-switch">
                {quests.map((q, idx) => (
                    <button
                        key={q.questID}
                        className={q.questID === activeQuestId ? "active" : ""}
                        onClick={() => onSelectQuest(q.questID)}
                    >
                        Quest {String.fromCharCode(65 + idx)}
                    </button>
                ))}
            </div>

            <div className="quest-content">
                <p className="quest-title">{quest.QuestName}</p>

                <h3>Beschreibung:</h3>
                <p>{quest.description}</p>

                <div className="quest-meta">
                    <div>Ort: {quest.locationName}</div>
                    <div>Gruppe: {quest.group ?? "–"}</div>
                    <div>Gegenstände: {quest.price_item ?? "–"}</div>
                </div>
            </div>
        </div>
    );
}
