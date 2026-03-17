export default function ItemList({ items, onSelect, onItemDoubleClick }) {
  return (

      <table className="item-table">
        <thead className="item-table-header">
        <tr>
          <th>Bezeichunung</th>
          <th>Typ</th>
          <th>Seltenheit</th>
          <th>Wert</th>
        </tr>
        </thead>

    <tbody className="item-list" style  ={{color:"#fff"}}>
      {(items || []).map((item, i) => (
        <tr key={item?.itemId ?? i} onClick={() => onSelect(item)}
        onDoubleClick={() => onItemDoubleClick(item)}
          style={{cursor: 'pointer'}}
        >
            <td><strong>{item.name}</strong></td>
          <td>{item.typ ?? "-"}</td>
          <td>{item.seltenheit ?? "-"}</td>
          <td>{item.price ?? "-"}</td>
        </tr>
      ))}
    </tbody>

      </table>
  );
}
