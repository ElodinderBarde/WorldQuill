import { useEffect, useState } from "react";
import "./ClanMitgliederList.css";

export default function ClanMitgliederList({ clan }) {
    const [mitglieder, setMitglieder] = useState([]);
    const [modalNpc, setModalNpc] = useState(null); // Für Modal
    const [editedNotes, setEditedNotes] = useState("");

    useEffect(() => {
        if (!clan) return;

        fetch(`/api/npcs/byClan/${clan.id}`)
            .then(res => res.json())
            .then(data => setMitglieder(data));
    }, [clan]);

    function shorten(text, max = 40) {
        if (!text) return "Keine Notizen";
        return text.length > max ? text.substring(0, max) + " ..." : text;
    }

    async function saveNotes(npcId) {
        const response = await fetch(`/api/npcs/${npcId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ notes: editedNotes })
        });

        if (response.ok) {
            // Liste aktualisieren
            const updated = mitglieder.map(m =>
                m.npcId === npcId ? { ...m, notes: editedNotes } : m
            );
            setMitglieder(updated);
            setModalNpc(null);
        } else {
            alert("Fehler beim Speichern!");
        }
    }
    function handleCancel() {
        if (editedNotes !== (modalNpc?.notes ?? "")) {
            const confirmExit = window.confirm(
                "Änderungen verwerfen?\nDiese können nicht rückgängig gemacht werden!"
            );
            if (!confirmExit) return;
        }

        setModalNpc(null); // Modal schließen
    }

    function handleSave() {
        if (editedNotes !== (modalNpc?.notes ?? "")) {
            const confirmExit = window.confirm(
                "Änderungen speichern?\nDiese können nicht rückgängig gemacht werden!"
            );
            if (!confirmExit) return;
        }

        saveNotes(modalNpc.npcId);
    }

    return (
        <div className="grid-stack-item" gs-w="10" gs-h="6" gs-x="2" gs-y="2">
            <div className="grid-stack-item-content mitglieder-container">

                <h4>Clanmitglieder</h4>

                {mitglieder.length === 0 && (
                    <p style={{ opacity: 0.6 }}>Keine Mitglieder gefunden.</p>
                )}

                {mitglieder.length > 0 && (
                    <table className="npc-table"

                           style={{ zIndex: 0 }}
                    >
                        <thead>
                        <tr>
                            <th></th>
                            <th>Name</th>
                            <th>Level</th>
                            <th>Position</th>
                            <th>Volk</th>
                            <th>Arbeit / Aufenthaltsort</th>
                            <th>Notizen</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        {mitglieder.map(npc => (
                            <tr key={npc.npcId}>
                                <td><button className="btn-small">
                                    ✏
                                </button></td>
                                <td>{npc.firstname} {npc.lastname}</td>
                                <td>{npc.level ?? "?"}</td>
                                <td>{npc.clanPosition ?? "Mitglied"}</td>
                                <td>{npc.race ?? "Unbekannt"}</td>
                                <td>{npc.shopRelation ?? "Unbekannt"}</td>
                                <td>{shorten(npc.notes)}</td>
                                <td>
                                    <button
                                        className="btn-small"
                                        onClick={() => {
                                            setModalNpc(npc);
                                            setEditedNotes(npc.notes ?? "");
                                        }}
                                    >
                                        ✏ Notizen
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}

                {/* --- Modal für NPC Notes --- */}
                {modalNpc && (
                    <div className="npc-modal-backdrop">
                        <div className="npc-modal">
                            <h3>Notizen bearbeiten</h3>
                            <p><b>{modalNpc.firstname} {modalNpc.lastname}</b></p>

                            <textarea
                                value={editedNotes}
                                onChange={e => setEditedNotes(e.target.value)}
                                rows={8}
                                style={{ width: "100%", height: "80%" }}
                            />

                            <div className="modal-buttons">
                                <button onClick={handleSave} className="btn-save">
                                    Speichern
                                </button>

                                <button onClick={handleCancel} className="btn-cancel">
                                    Abbrechen
                                </button>
                            </div>


                            </div>
                    </div>
                )}

            </div>
        </div>
    );
}
