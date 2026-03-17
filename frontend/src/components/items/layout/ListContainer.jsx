export default function ListContainer() {
    return (
        <div className="ItemsList" style={{
            backgroundColor: '#383c40',
            borderRadius: '10px',

        }}>
            <div className="table-scroll">
                <table className="item-table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Typ</th>
                        <th>Seltenheit</th>
                        <th>Wert</th>
                        <th>Gewicht</th>
                    </tr>
                    </thead>
                    <tbody>



                    </tbody>
                </table>
            </div>
        </div>
    );
}
