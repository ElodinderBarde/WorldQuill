export default function ItemDetail({ item }) {
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
        <h1 style={{ fontSize: "2rem" }}>{item.name}</h1>
          <br/>
        <p><strong>Typ:</strong> {item.itemType}</p>
        <p><strong>Preis:</strong> {item.price}g</p>
        <p><strong>Seltenheit:</strong> {item.rarity}</p>
        <p>{item.description? item.description : <em>Keine Beschreibung vorhanden. </em>}</p>

</div>
    );
  }
