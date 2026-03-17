import {useNavigate} from "react-router-dom";

export default function NpcList({ npcs, onNpcClick, selectedNpc }) {
    const navigate = useNavigate();

    if (!Array.isArray(npcs)) return <div>Fehler: Keine gültigen Daten</div>;



    const handleDoubleClick = (npcId) => {
        navigate(`/npc/${npcId}`);
    };

    return (
        <div className="npc-list">
            <h3>NPC-Liste</h3>

            <div className="table-scroll">
                <table className="npc-table">
                    <thead>
                    <tr>
                        <th>Vorname</th>
                        <th>Nachname</th>
                        <th>Volk</th>
                        <th>Alter</th>
                        <th>Klasse / Subklasse</th>
                        <th>Level</th>
                        <th>Clan</th>
                        <th>Clan-Position</th>
                        <th>Standort</th>
                        <th>Bild</th>
                        <th>Symbol</th>
                    </tr>
                    </thead>

                    <tbody>
                    {npcs.map((npc) => (
                        <tr
                            key={npc.npcId}
                            onClick={() => onNpcClick(npc)}
                            onDoubleClick={() => handleDoubleClick(npc.npcId)}
                            style={{
                                cursor: "pointer",
                                backgroundColor:
                                    selectedNpc?.npcId === npc.npcId
                                        ? "gray"
                                        : "transparent"
                            }}
                        >
                            <td>{npc.firstname ?? "-"}</td>
                            <td>{npc.lastname ?? "-"}</td>
                            <td>{npc.race ?? "-"}</td>
                            <td>{npc.age ?? "-"}</td>
                            <td>{npc.npcClass ?? "-"} / {npc.subclass ?? "-"}</td>
                            <td>{npc.level ?? "-"}</td>
                            <td>{npc.clan ?? "-"}</td>
                            <td>{npc.clanPosition ?? "-"}</td>
                            <td>{npc.shopRelation ?? "-"}</td>
                            <td style={{ textAlign: "center" }}>
                                {npc.pictureUrl ? "✅" : "❌"}
                            </td>
                            <td>{npc.symbol ?? "🧍‍♂️"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
