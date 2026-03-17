export default function ItemDetail({ item, onItemDoubleClick }) {
    if (!item) {
        return (
            <div className="item-detail placeholder">
                Wähle ein Item, um Details anzuzeigen.
            </div>
        );
    }
    return (
      <div className="item-detail"

      >
        <h1>{item.name}</h1>
        <p><strong>Typ:</strong> {item.typ}</p>
        <p><strong>Preis:</strong> {item.price}g</p>
        <p><strong>Seltenheit:</strong> {item.seltenheit}</p>
        <p>{item.beschreibung ? item.beschreibung : <em>Keine Beschreibung vorhanden. </em>}</p>

</div>
    );
  }
