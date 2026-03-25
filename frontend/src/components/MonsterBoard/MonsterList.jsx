
export default function MonsterList({ monsters, onSelect, onItemDoubleClick }) {

    return (
        <div className="board-table-container">
            <table className="board-table">

                <thead>
                <tr>
                    <th>Name</th>
                    <th>Herausforderungsgrad</th>
                    <th>Schlagworte</th>
                    <th>Shiftable</th>
                </tr>
                </thead>

                <tbody>
                {(monsters || []).map((monster) => (
                    <tr
                        key={monster.id}
                        className="board-row"
                        style={{ cursor: "pointer" }}
                        tabIndex={0}
                        onClick={()    => onSelect?.(monster)}
                        onDoubleClick={() => onItemDoubleClick?.(monster)}
                        onKeyDown={(e) => e.key === "Enter" && onSelect?.(monster)}
                    >
                        <td><strong>{monster.name         ?? "-"}</strong></td>
                        <td>{monster.challengeRating ?? "-"}</td>
                        <td>{monster.keyword   ?? "-"}</td>
                        <td>{monster.isShiftable === 1 ? "Ja" : "Nein"}</td>
                    </tr>
                ))}

                {monsters?.length === 0 && (
                    <tr>
                        <td colSpan={4} style={{ textAlign: "center", opacity: 0.6 }}>
                            Keine Monster vorhanden
                        </td>
                    </tr>
                )}
                </tbody>

            </table>
        </div>
    );
}