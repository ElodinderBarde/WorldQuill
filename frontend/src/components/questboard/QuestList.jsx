import { useEffect, useState } from "react";
import QuestFilter from "./QuestFilter.jsx";

export default function QuestList({ onOpenNote, reloadTrigger, onReload, filters, setFilters }) {
    const [quests, setQuests] = useState([]);
    const [activeQuests, setActiveQuests] = useState([]);



    useEffect(() => {
        fetch("http://localhost:8081/api/quest")
            .then(res => res.json())
            .then(data => setQuests(data));
    }, [reloadTrigger]);

// 2. Gefilterte Quests berechnen (immer bei filter-Änderung)
    const [filteredQuests, setFilteredQuests] = useState([]);

    useEffect(() => {
        const filtered = quests.filter(q => {
            const nameMatch = !filters.name || q.monsterName.toLowerCase().includes(filters.name.toLowerCase());
            const reiheMatch = filters.questreihe ? q.previousQuestId !== null : true;
            const groupMatch = !filters.gruppe || q.group?.toLowerCase().includes(filters.gruppe.toLowerCase());
            const ortMatch = !filters.ort || (
                q.questlocation?.cityID?.city_name?.toLowerCase().includes(filters.ort.toLowerCase()) ||
                q.questlocation?.villageID?.village_name?.toLowerCase().includes(filters.ort.toLowerCase())
            );
            const statusMatch = !filters.status || q.status.toLowerCase() === filters.status;
            return nameMatch && reiheMatch && groupMatch && ortMatch && statusMatch;
        });

        setFilteredQuests(filtered);
    }, [filters, quests]);


    const toggleQuestActive = async (quest) => {
        const newActive = !quest.active;

        await fetch(`http://localhost:8081/api/Quest/${quest.questID}/active`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ active: newActive })
        });

        onReload(); // Questliste neu laden
    };


    const handleComplete = (id) => {
        fetch(`http://localhost:8081/api/Quest/${id}/complete`, {
            method: "PUT",
        })
            .then((res) => {
                if (res.ok) {
                    onReload(); // Trigger zum Neuladen
                } else {
                    alert("Fehler beim Abschließen der Quest.");
                }
            });
    };

    const handleInomplete = (id) => {
        fetch(`http://localhost:8081/api/Quest/${id}/incomplete`, {
            method: "PUT",
        })
            .then((res) => {
                if (res.ok) {
                    onReload(); // Trigger zum Neuladen
                } else {
                    alert("Fehler beim reaktivieren der Quest.");
                }
            });
    };


    return (
        <div className="p-4">
            <table className="w-full border-collapse border border-gray-300">
                <thead>
                <tr className="bg-gray-100">
                    <th>Status</th>
                    <th>Aktiv</th>
                    <th>Questname</th>
                    <th>Reihe</th>
                    <th>Gruppe</th>
                    <th>Gold</th>
                    <th>Item</th>
                    <th>Beschreibung</th>
                    <th>Notizen</th>
                    <th>Ort</th>
                </tr>
                </thead>
                <tbody>
                {filteredQuests.map((quest) => (
                    <tr key={quest.questID}>
                        <td className="text-center">
                            {quest.status.toLowerCase() !== "abgeschlossen" ? (
                                <button
                                    onClick={() => handleComplete(quest.questID)}
                                    className="bg-green-500 text-white px-2 py-1 rounded hover:bg-green-600"
                                >
                                    Abschließen
                                </button>
                            ) : (
                                <p
                                    onDoubleClick={() => handleInomplete(quest.questID)}
                                    className="text-gray-500 cursor-pointer"
                                    title="Doppelklick zum Reaktivieren"
                                >
                                    Abgeschlossen
                                </p>
                            )}
                        </td>
                        <td className="text-center">
                            {quest.status.toLowerCase() !== "abgeschlossen" ? (
                                <input
                                    type="checkbox"
                                    checked={quest.active}
                                    onChange={() => toggleQuestActive(quest)}
                                    disabled={
                                        !quest.active &&
                                        filteredQuests.filter(q => q.active).length >= 2
                                    }
                                />

                            ) : (
                                <span className="text-gray-400">–</span>
                            )}
                        </td>
                        <td>{quest.monsterName}</td>
                        <td>{quest.questname || "-"}</td>
                        <td>{quest.group || "-"}</td>
                        <td>{quest.price_gold}g</td>
                        <td>{quest.price_item || "-"}</td>
                        <td>{quest.description}</td>
                        <td className="text-center">
                            <button
                                className="text-blue-600 underline"
                                onClick={() =>
                                    onOpenNote({ id: quest.questID, text: quest.notes || "" })
                                }
                            >
                                Notizen
                            </button>
                        </td>
                        <td>
                            {quest.questlocation?.cityID?.city_name ||
                                quest.questlocation?.villageID?.village_name ||
                                "-"}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
