export default function MonsterList({ monsters, onSelect, onItemDoubleClick }) {
    return (
        <table className="item-table">
            <thead className="item-table-header">
            <tr>
                <th>Name</th>
                <th>Herausforderungsgrad</th>
                <th>Schlagworte</th>
                <th>Shiftable</th>
            </tr>
            </thead>

            <tbody className="item-list" style={{ color: "#fff" }}>
            {(monsters || []).map((monster) => (
                <tr
                    key={monster.id}
                    onClick={() => onSelect?.(monster)}
                    onDoubleClick={() => onItemDoubleClick?.(monster)}
                    style={{ cursor: 'pointer' }}
                    tabIndex={0}
                    onKeyDown={(e) => e.key === "Enter" && onSelect?.(monster)}
                >
                    <td><strong>{monster?.name ?? "-"}</strong></td>
                    <td>{monster?.challengeLvl ?? "-"}</td>
                    <td>{monster?.schlagwort ?? "-"}</td>
                    <td>{monster?.shiftable === 1 ? "Ja" : "Nein"}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}