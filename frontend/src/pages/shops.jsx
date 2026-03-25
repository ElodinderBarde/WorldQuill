import { useEffect, useRef, useState } from "react";
import { GridStack } from "gridstack";
import "gridstack/dist/gridstack.min.css";
import "../components/shops/shops.css";

import Navbar from "../components/Navbar";
import ShopFilter from "../components/shops/ShopFilter";
import ShopList from "../components/shops/ShopList";
import ShopCreate from "../components/shops/ShopCreate";
import ShopNotes from "../components/shops/ShopNotes";

import { getShops } from "../service/shopsAPI";

export default function ShopPage() {
    const gridRef = useRef(null);
    const gridInstance = useRef(null);
    const notesWidgetProps = {
        "gs-x": "0",
        "gs-y": "4",
        "gs-w": "6",
        "gs-h": "4"
    };
    const [shops, setShops] = useState([]);
    const [selectedCity, setSelectedCity] = useState("");
    const [selectedType, setSelectedType] = useState(null);
    const [selectedShop, setSelectedShop] = useState(null);

    useEffect(() => {
        const grid = GridStack.init({
            column: 12,
            margin: 5,
            cellHeight: 100,
            staticGrid: true,
            disableResize: true,
            disableDrag: true
        }, gridRef.current);

        gridInstance.current = grid;

        getShops()
            .then(setShops)
            .catch((err) => console.error("Fehler beim Laden der Shops:", err));

        // Korrigiertes Positionieren sobald GridStack bereit ist
        grid.on('added', () => {
            const notesNode = grid.engine.nodes.find(node =>
                node.el?.querySelector('.shop-notes-marker')
            );
            if (notesNode) {
                grid.update(notesNode.el, { x: 0, y: 4 });
            }
        });

        return () => grid.destroy(false);
    }, []);

    const handleShopClick = (shop) => {
        setSelectedShop(shop);
    };

    const handleSaveNotes = (newNote) => {
        if (!selectedShop || !selectedShop.id) return;

        fetch(`http://localhost:8081/api/Shop/${selectedShop.id}/notes`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(newNote),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Fehler beim Speichern der Notizen");
                }
                return response.json();
            })
            .then((data) => {
                console.log("Notizen erfolgreich gespeichert:", data);
                return getShops();
            })
            .then(setShops)

    .catch((error) => {
                console.error(error);
            });
    };


    return (
        <>
            <Navbar />
            <div className="grid-stack" ref={gridRef}>
                <ShopFilter
                    selectedCity={selectedCity}
                    setSelectedCity={setSelectedCity}
                    selectedType={selectedType}
                    setSelectedType={setSelectedType}
                />

                <ShopList
                    shops={shops}
                    selectedCity={selectedCity}
                    selectedType={selectedType}
                    onShopClick={handleShopClick}
                />

                <ShopCreate />

                <div className="grid-stack-item" {...notesWidgetProps}>
                    <div className="grid-stack-item-content ">
                        {selectedShop ? (
                            <ShopNotes
                                selectedShop={selectedShop}
                                onSave={handleSaveNotes}
                            />
                        ) : (
                            <div style={{
                                padding: "1rem",
                                fontSize: "1.1rem",
                                textAlign: "center",
                                width: "100%",
                                color: "#ccc"
                            }}>
                                Wähle einen Shop, um Notizen zu bearbeiten.
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </>
    );
}
