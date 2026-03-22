export default function BoardDetail({ data, fields = [] }) {

    if (!data) {
        return (
            <div className="board-detail">
                Kein Eintrag gewählt
            </div>
        );
    }

    return (
        <div className="board-detail">
            {fields.map(f => (
                <p key={f.key}>
                    <strong>{f.label}:</strong>{" "}
                    {f.render ? f.render(data) : data?.[f.key] ?? "-"}
                </p>
            ))}
        </div>
    );
}