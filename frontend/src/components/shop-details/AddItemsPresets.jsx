// src/components/shop/AddItemsPreset.jsx

import React, { useState, useEffect } from "react";
import presets from "./ItemPresets/ItemPresets.js";

export default function AddItemsPreset({ onClose, shopId }) {
    const [allItems, setAllItems] = useState([]);
    const [selectedPreset, setSelectedPreset] = useState("");

    useEffect(() => {
        const fetchItems = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/items`);
                const data = await response.json();
                setAllItems(data);
            } catch (error) {
                console.error("Fehler beim Laden der Items:", error);
            }
        };
        fetchItems();
    }, []);

    const handlePresetSelect = async () => {
        if (!selectedPreset) {
            alert("Bitte wähle ein Preset aus.");
            return;
        }

        const preset = presets[selectedPreset]; // ← Dies fehlte in deinem Code
        const selectedItemsMap = {};

        preset.items.forEach(setting => {
            const filtered = allItems.filter(item => {
                const typMatch = setting.typ ? item.item_type?.toLowerCase() === setting.item_type.toLowerCase() : true;
                const rarityMatch = setting.rarity ? item.rarity?.toLowerCase() === setting.rarity.toLowerCase() : true;
                return typMatch && rarityMatch;
            });

            for (let i = 0; i < setting.quantity; i++) {
                if (filtered.length > 0) {
                    const randomItem = filtered[Math.floor(Math.random() * filtered.length)];
                    const itemId = randomItem.id;
                    if (selectedItemsMap[itemId]) {
                        selectedItemsMap[itemId] += 1;
                    } else {
                        selectedItemsMap[itemId] = 1;
                    }
                }
            }
        });

        const itemsToAdd = Object.entries(selectedItemsMap).map(([itemId, qty]) => ({
            shopId: shopId,
            itemId: parseInt(itemId),
            quantity: qty
        }));

        if (itemsToAdd.length === 0) {
            alert("Keine passenden Items gefunden.");
            return;
        }

        try {
            await fetch(`http://localhost:8081/api/shopItems/addItems`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(itemsToAdd)
            });
            onClose();
        } catch (error) {
            console.error("Fehler beim Hinzufügen von Preset-Items:", error);
            alert("Fehler beim Hinzufügen.");
        }
    };

    return (
        <div style={overlayStyle}>
            <div style={modalStyle}>
                <button onClick={onClose} style={closeButtonStyle}>✖</button>
                <h3>⚙️ Preset Items hinzufügen</h3>

                <select value={selectedPreset} onChange={e => setSelectedPreset(e.target.value)} style={{ width: "100%", padding: "0.5rem", marginBottom: "1rem" }}>
                    <option value="">Preset wählen...</option>
                    {Object.entries(presets).map(([key, preset]) => (
                        <option key={key} value={key}>{preset.label}</option>
                    ))}
                </select>

                <button onClick={handlePresetSelect} style={presetButtonStyle}>
                    Preset Items hinzufügen
                </button>
            </div>
        </div>
    );
}

// Stile unverändert
const overlayStyle = {
    position: "fixed",
    top: 0,
    left: 0,
    width: "100vw",
    height: "100vh",
    backgroundColor: "rgba(0,0,0,0.7)",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    zIndex: 9999
};

const modalStyle = {
    backgroundColor: "#1e1e1e",
    padding: "2rem",
    borderRadius: "8px",
    width: "90%",
    maxWidth: "400px",
    position: "relative"
};

const closeButtonStyle = {
    position: "absolute",
    top: "1rem",
    right: "1rem",
    background: "transparent",
    color: "white",
    border: "none",
    fontSize: "1.5rem",
    cursor: "pointer"
};

const presetButtonStyle = {
    width: "100%",
    padding: "0.75rem",
    backgroundColor: "#333",
    color: "white",
    border: "none",
    cursor: "pointer"
};
