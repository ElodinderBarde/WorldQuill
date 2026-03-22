export default function BoardTable({
                                       data = [],
                                       columns = [],
                                       onSelect,
                                       onDoubleClick,
                                       getRowKey = (row, index) => row?.id ?? index
                                   }) {
    return (
        <div className="board-table-container">
            <table className="board-table">

                {/* =========================
                   HEADER
                ========================= */}
                <thead>
                <tr>
                    {columns.map((col) => (
                        <th key={col.key}>
                            {col.label}
                        </th>
                    ))}
                </tr>
                </thead>

                {/* =========================
                   BODY
                ========================= */}
                <tbody>
                {data.length === 0 ? (
                    <tr>
                        <td colSpan={columns.length} style={{ textAlign: "center", opacity: 0.6 }}>
                            Keine Daten vorhanden
                        </td>
                    </tr>
                ) : (
                    data.map((row, index) => (
                        <tr
                            key={getRowKey(row, index)}
                            onClick={() => onSelect?.(row)}
                            onDoubleClick={() => onDoubleClick?.(row)}
                            style={{ cursor: "pointer" }}
                            className="board-row"
                        >
                            {columns.map((col) => (
                                <td key={col.key}>
                                    {col.render
                                        ? col.render(row)
                                        : row?.[col.key] ?? "-"}
                                </td>
                            ))}
                        </tr>
                    ))
                )}
                </tbody>

            </table>
        </div>
    );
}