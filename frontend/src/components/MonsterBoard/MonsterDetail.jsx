export default function MonsterDetail({ monster }) {
    if (!monster) {
        return (
            <div className="monster-detail placeholder">
                Wähle ein Monster, um Details anzuzeigen.
            </div>
        );
    }

    return (
        <div className="monster-detail">




            <h1 style={{ fontSize: "25px", textDecoration: "underline" }}>{monster?.name}</h1>
<br/>
            <p><strong>Schlagwort:</strong> {monster?.schlagwort ?? "-"}</p>

            <p><strong>Herausforderungsgrad:</strong> {monster?.challengeLvl ?? "-"}</p>

            <p><strong>Quelle:</strong> {monster?.book ?? "-"}</p>

            <p>
                <strong>Quellenseiten:</strong>{" "}
                {[monster?.page1, monster?.page2, monster?.page3]
                    .filter(Boolean)
                    .join(", ") || "-"}
                <button onClick={() => openPdf(monster)}>
                    Seiten anzeigen
                </button>
            </p>

            <p>
                <strong>Shiftable:</strong>{" "}
                {monster?.shiftable === 1 ? "Ja" : "Nein"}
            </p>

            <p>
                {monster?.description
                    ? monster.description
                    : <em>Keine Beschreibung vorhanden.</em>}
            </p>
        </div>
    );
}