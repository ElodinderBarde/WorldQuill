import React, { useState, useEffect } from "react";
import AddItems from "./AddItems.jsx";
import AddItemsPreset from "./AddItemsPresets.jsx";
export default function ShopItems({ shopId }) {
    const [shopItems, setShopItems] = useState([]);
    const [expandedIndex, setExpandedIndex] = useState(null);
    const [loading, setLoading] = useState(true);
    const [showAddItems, setShowAddItems] = useState(false);
    const [selectedShopItemId, setSelectedShopItemId] = useState(null);
    const [showPreset, setShowPreset] = useState(false);


    const fetchShopItems = async () => {
        if (!shopId || shopId === "undefined") {
            setShopItems([]);
            setLoading(false);
            return;
        }

        try {
            setLoading(true);
            const response = await fetch(`http://localhost:8081/api/shopItems/shop/${shopId}`);
            if (!response.ok) {
                if (response.status === 204) {
                    setShopItems([]);
                    return;
                }
                throw new Error(`Fehler beim Laden: ${response.status}`);
            }
            const text = await response.text();
            const data = text ? JSON.parse(text) : [];
            setShopItems(data);
        } catch (error) {
            console.error("Fehler beim Laden der ShopItems:", error);
            setShopItems([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (!shopId || shopId === "undefined") return;
        fetchShopItems();
    }, [shopId]);



    const toggleExpand = (shopItemId) => {
        setExpandedIndex(shopItemId === expandedIndex ? null : shopItemId);
        setSelectedShopItemId(shopItemId === selectedShopItemId ? null : shopItemId);
    };


    const updateQuantityInDatabase = async (shopItemId, newQuantity) => {
        try {
            const response = await fetch(`http://localhost:8081/api/shopItems/${shopItemId}/quantity`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ quantity: newQuantity }), // <-- hier!
            });

            if (!response.ok) {
                throw new Error(`Fehler beim Speichern: ${response.status}`);
            }
            console.log(`Menge für Item ${shopItemId} erfolgreich auf ${newQuantity} gesetzt.`);
        } catch (error) {
            console.error("Fehler beim Speichern der Menge:", error);
        }
    };


    const increaseQuantity = () => {
        if (selectedShopItemId !== null) {
            const updatedItems = [...shopItems];
            const item = updatedItems.find(item => item.shopItemId === selectedShopItemId);
            if (!item) return;

            const currentQuantity = item.menge ?? item.quantity ?? 0;
            const newQuantity = currentQuantity + 1;
            item.menge = newQuantity;
            setShopItems(updatedItems);
            updateQuantityInDatabase(item.shopItemId, newQuantity);
        }
    };

    const decreaseQuantity = () => {
        if (selectedShopItemId !== null) {
            const updatedItems = [...shopItems];
            const item = updatedItems.find(item => item.shopItemId === selectedShopItemId);
            if (!item) return;

            const currentQuantity = item.menge ?? item.quantity ?? 0;
            const newQuantity = currentQuantity > 0 ? currentQuantity - 1 : 0;
            item.menge = newQuantity;
            setShopItems(updatedItems);
            updateQuantityInDatabase(item.shopItemId, newQuantity);
        }

    };


    useEffect(() => {
        if (!shopId || shopId === "undefined") return;
        fetchShopItems();
    }, [shopId]);

    const openFormsElement = () => setShowAddItems(true);
    const closeFormsElement = () => {
        setShowAddItems(false);
        fetchShopItems();
    };

    if (loading) {
        return <p>Lade Items...</p>;
    }

    const filteredShopItems = shopItems.filter(item => (item.menge ?? item.quantity ?? 0) > 0);

    return (
        <div style={{ display: "flex", flexDirection: "column", height: "100%", width: "100%", justifyContent: "space-between" }}>
            <div style={{ overflowY: "auto", flexGrow: 1 }}>
                {filteredShopItems.length === 0 ? (
                    <p>Das Geschäft ist zur Zeit ausverkauft.</p>
                ) : (
                    <table style={{ width: "100%", borderCollapse: "collapse" }}>
                        <thead>
                        <tr style={{ backgroundColor: "#333", color: "#fff" }}>
                            <th style={thStyle}>Name</th>
                            <th style={thStyle}>Preis</th>
                            <th style={thStyle}>Typ</th>
                            <th style={thStyle}>Seltenheit</th>
                            <th style={thStyle}>Menge</th>
                        </tr>
                        </thead>
                        <tbody>
                        {filteredShopItems.map((item, index) => (
                            <React.Fragment key={item.shopItemId ?? index}>
                                <tr
                                    onClick={() => toggleExpand(item.shopItemId)}
                                    style={{
                                        cursor: "pointer",
                                        backgroundColor: index % 2 === 0 ? "#222" : "#111",
                                        outline: selectedShopItemId === item.shopItemId ? "2px solid #888" : "none",
                                    }}
                                >
                                    <td style={tdStyle}>{item.item?.name || "Unbekannt"}</td>
                                    <td style={tdStyle}>{item.item?.price ?? "?"}g</td>
                                    <td style={tdStyle}>{item.item?.typ || "-"}</td>
                                    <td style={tdStyle}>{item.item?.seltenheit || "-"}</td>
                                    <td style={tdStyle}>{item.menge ?? item.quantity ?? "-"}</td>
                                </tr>
                                {expandedIndex === item.shopItemId && (
                                    <tr>
                                        <td colSpan="5" style={{ ...tdStyle, backgroundColor: "#1a1a1a" }}>
                                            <strong>Beschreibung:</strong> {item.item?.beschreibung || "Keine Beschreibung vorhanden."}
                                        </td>
                                    </tr>
                                )}
                            </React.Fragment>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>

            <div style={{ display: "flex", justifyContent: "flex-end", gap: "0.5rem", marginTop: "0.5rem" }}>
                <button className="form-button" onClick={increaseQuantity} disabled={selectedShopItemId === null}>
                    + Element
                </button>
                <button className="form-button" onClick={decreaseQuantity} disabled={selectedShopItemId === null}>
                    - Element
                </button>
            </div>


            <div>
                <button className="form-button" onClick={() => setShowPreset(true)}>
                    Preset verwenden
                </button>

            </div>
            {showPreset && <AddItemsPreset onClose={() => { setShowPreset(false); fetchShopItems(); }} shopId={shopId} />}


            <div>
                <button className="form-button" onClick={openFormsElement}>
                    Item hinzufügen
                </button>
            </div>

            {showAddItems && <AddItems onClose={closeFormsElement} shopId={shopId} />}
        </div>
    );
}

const thStyle = {
    padding: "0.5rem",
    textAlign: "left",
    borderBottom: "1px solid #555",
};

const tdStyle = {
    padding: "0.5rem",
    borderBottom: "1px solid #333",
};
