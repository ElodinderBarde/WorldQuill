import { useEffect, useState } from "react";

export default function QuestList({ onOpenNote, reloadTrigger, onReload, filters }) {
    const [quests, setQuests] = useState([]);



    useEffect(() => {
        fetch("http://localhost:8081/api/quest")
            .then(res => res.json())
            .then(data => setQuests(data));
    }, [reloadTrigger]);

// 2. Gefilterte Quests berechnen (immer bei filter-Änderung)
    const [filteredQuests, setFilteredQuests] = useState([]);

    const getQuestLocationLabel = (quest) =>
        quest.locationName || quest.questlocation?.cityID?.city_name || quest.questlocation?.villageID?.village_name || "";

    const getQuestDisplayName = (quest) =>
        quest.questName || quest.questname || quest.questName || "-";

    useEffect(() => {
        const toLower = (value) => (typeof value === "string" ? value.toLowerCase() : "");

        const filtered = quests.filter(q => {
            const questName = q.questName || q.questname || q.questName|| "";
            const locationLabel = getQuestLocationLabel(q);

            const nameMatch = !filters.name || questName === filters.name;
            const reiheMatch = filters.questreihe ? (q.previousQuestId ?? 0) > 0 : true;
            const groupMatch = !filters.gruppe || toLower(q.group).includes(toLower(filters.gruppe));
            const ortMatch = !filters.ort || locationLabel === filters.ort;
            const statusMatch = !filters.status || toLower(q.status) === toLower(filters.status);
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
                            {(quest.status || "").toLowerCase() !== "abgeschlossen" ? (
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
                            {(quest.status || "").toLowerCase() !== "abgeschlossen" ? (
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
                        <td>{getQuestDisplayName(quest)}</td>
                        <td>{(quest.previousQuestId ?? 0) > 0 ? `#${quest.previousQuestId}` : "-"}</td>
                        <td>{quest.group || "-"}</td>
                        <td>{quest.priceGold ?? quest.price_gold ?? 0}g</td>
                        <td>{quest.priceItem || quest.price_item || "-"}</td>
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
                            {getQuestLocationLabel(quest) || "-"}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
