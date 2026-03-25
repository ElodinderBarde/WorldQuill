// MonsterDetail.jsx

export default function MonsterDetail({ monster, onOpenPdf }) {

    if (!monster) {
        return (
            <div className="board-detail">
                Wähle ein Monster, um Details anzuzeigen.
            </div>
        );
    }

    const pages = [monster.page1, monster.page2, monster.page3]
        .filter(Boolean)
        .join(", ");

    return (
        <div className="board-detail">

            <h1>{monster.name}</h1>

            <p><strong>Schlagwort:</strong>          {monster.keyword   ?? "-"}</p>
            <p><strong>Herausforderungsgrad:</strong> {monster.challengeRating ?? "-"}</p>
            <p><strong>Quelle:</strong>               {monster.sourceBook      ?? "-"}</p>

            <p>
                <strong>Quellenseiten:</strong>{" "}{pages || "-"}
                {" "}
                <button onClick={() => onOpenPdf?.(monster)}>
                    Seiten anzeigen
                </button>
            </p>

            <p>
                <strong>Shiftable:</strong>{" "}
                {monster.isShiftable === 1 ? "Ja" : "Nein"}
            </p>

            <p>
                {monster.description
                    ? monster.description
                    : <em>Keine Beschreibung vorhanden.</em>}
            </p>

        </div>
    );
}