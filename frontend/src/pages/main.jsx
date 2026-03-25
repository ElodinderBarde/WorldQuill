import {useEffect, useState, useRef} from 'react';
import './CSS/main.css';
import '../components/npc-detail-page/NpcPicture.css'

import Flag from '../components/main-Page/blocks/item-details-block/Flag.jsx';
import { GridStack } from 'gridstack';
import Navbar from '../components/navbar';
import CityNameSelector from "../components/main-Page/blocks/city-name-block/CityNameSelector.jsx";
import {CommandMenu01} from "../components/main-Page/blocks/city-name-block/modal/locationSelectorModal.jsx";
import SelectList from "../components/main-Page/elements/SelectList/SelectList.jsx";
import {getShops} from "../service/shopsAPI.js";
import {getEmployees, getCustomers} from "../service/ShopCustomerEmployeeAPI.js";
import { useNavigate } from "react-router-dom";
import ShopInventorySelect from "../components/main-Page/blocks/invventory-block/ShopInventorySelect.jsx";
import NpcStats from '../components/main-Page/blocks/npc-stats-block/NpcStats.jsx';
import NpcPicture from "@/components/npc-detail-page/npcPicture.jsx";
import ActiveQuestPanel from "@/components/main-Page/blocks/quest-info-block/QuestInfo.jsx";
import QuestNotes from "@/components/main-Page/blocks/quest-notes-block/QuestNotes.jsx";

function loadFromStorage(key) {
    const raw = localStorage.getItem(key);
    if (!raw) return null;
    try {
        return JSON.parse(raw);
    } catch (err) {
        console.warn(`Failed to parse localStorage key ${key}:`, raw, err);
        try { localStorage.removeItem(key); } catch (e) {}
        return null;
    }
}

function saveToStorage(key, value) {
    try {
        if (value === null || typeof value === 'undefined') {
            localStorage.removeItem(key);
        } else {
            localStorage.setItem(key, JSON.stringify(value));
        }
    } catch (err) {
        console.warn(`Failed to write localStorage key ${key}:`, err);
    }
}

export default function Main() {
    const navigate = useNavigate();

    const [isCityModalOpen, setCityModalOpen] = useState(false);
    const [viewMode, setViewMode] = useState("EMPLOYEE");

    const [selectedLocation, setSelectedLocation] = useState(() => loadFromStorage("selectedLocation"));
    const [shopType, setShopType] = useState(() => loadFromStorage("selectedShopType"));
    const [selectedShopId, setSelectedShopId] = useState(() => loadFromStorage("selectedShopId"));
    const [selectedNpcId, setSelectedNpcId] = useState(() => loadFromStorage("selectedNpcId"));

    const [shops, setShops] = useState([]);
    const [availableShopTypes, setAvailableShopTypes] = useState([]);
    const [filteredShops, setFilteredShops] = useState([]);
    const [employees, setEmployees] = useState([]);
    const [customers, setCustomers] = useState([]);
    const [selectedShopItem, setSelectedShopItem] = useState(null);
    const [, setShopItemsVersion] = useState(0);
    const [activeQuestId, setActiveQuestId] = useState(null);

    const selectedShop = selectedShopId != null
        ? shops.find(s => s.id === selectedShopId) ?? null
        : null;

    const selectedLocationRef = useRef(selectedLocation);
    const shopTypeRef = useRef(shopType);
    const selectedShopIdRef = useRef(selectedShopId);
    const selectedNpcRef = useRef(selectedNpcId);

    useEffect(() => { selectedLocationRef.current = selectedLocation; }, [selectedLocation]);
    useEffect(() => { shopTypeRef.current = shopType; }, [shopType]);
    useEffect(() => { selectedShopIdRef.current = selectedShopId; }, [selectedShopId]);
    useEffect(() => { selectedNpcRef.current = selectedNpcId; }, [selectedNpcId]);

    useEffect(() => {
        const persist = () => {
            saveToStorage("selectedLocation", selectedLocationRef.current);
            saveToStorage("selectedShopType", shopTypeRef.current);
            saveToStorage("selectedShopId", selectedShopIdRef.current);
            saveToStorage("selectedNpc", selectedNpcRef.current);
        };
        window.addEventListener("beforeunload", persist);
        document.addEventListener("visibilitychange", () => {
            if (document.visibilityState === "hidden") persist();
        });
        return () => persist();
    }, []);

    useEffect(() => {
        if (!selectedLocation) { setShopType(null); setSelectedShopId(null); setSelectedNpcId(null); }
    }, [selectedLocation]);

    useEffect(() => {
        if (!shopType) { setSelectedShopId(null); setSelectedNpcId(null); }
    }, [shopType]);

    useEffect(() => {
        if (!selectedShopId) setSelectedNpcId(null);
    }, [selectedShopId]);

    useEffect(() => { setSelectedShopItem(null); }, [selectedShopId]);

    useEffect(() => { getShops().then(setShops); }, []);

    useEffect(() => {
        if (!selectedLocation) { setAvailableShopTypes([]); return; }
        setAvailableShopTypes(deriveShopTypesForLocation(shops, selectedLocation.locationId));
    }, [selectedLocation, shops]);

    useEffect(() => {
        if (!selectedLocation || !shopType) { setFilteredShops([]); return; }
        setFilteredShops(
            shops.filter(s =>
                Number(s.locationId) === Number(selectedLocation.locationId) &&
                Number(s.shopTypeId) === Number(shopType.id)
            )
        );
    }, [shops, selectedLocation, shopType]);

    useEffect(() => {
        if (!selectedShop) { setEmployees([]); setCustomers([]); return; }
        Promise.all([getEmployees(selectedShop.id), getCustomers(selectedShop.id)])
            .then(([emp, cust]) => { setEmployees(emp); setCustomers(cust); });
    }, [selectedShop]);

    function handleNpcDoubleClick(npc) { navigate(`/npc/${npc.npcId}`); }
    function handleShopDoubleClick(shop) { setSelectedShopId(shop.id); navigate(`/shops/${shop.id}`); }
    function toggleViewMode() { setViewMode(prev => prev === "EMPLOYEE" ? "CUSTOMER" : "EMPLOYEE"); }

    useEffect(() => {
        GridStack.init({ column: 100, cellHeight: 100, margin: 4, disableResize: true, disableDrag: true });
    }, []);

    function sortByNameAsc(a, b) {
        return a.name.localeCompare(b.name, 'de', { sensitivity: 'base' });
    }

    function deriveShopTypesForLocation(shops, locationId) {
        const map = new Map();
        shops
            .filter(shop => Number(shop.locationId) === Number(locationId))
            .forEach(shop => {
                if (!map.has(shop.shopTypeId)) {
                    map.set(shop.shopTypeId, {
                        id: shop.shopTypeId,
                        name: shop.shopTypeName, // war: shop.shopType (Objekt statt String!)
                    });
                }
            });
        return Array.from(map.values());
    }

    function decrementItemQuantity(shopItem) {
        if (!shopItem || shopItem.quantity <= 0) return;
        const newQuantity = shopItem.quantity - 1;
        fetch(`http://localhost:8081/api/shop-items/${shopItem.shopItemId}/quantity`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ quantity: newQuantity }),
        }).then(() => {
            setShopItemsVersion(v => v + 1);
            if (newQuantity <= 0) {
                setSelectedShopItem(null);
            } else {
                setSelectedShopItem({ ...shopItem, quantity: newQuantity });
            }
        });
    }

    return (
        <>
            <Navbar />
            <main style={{ padding: '1rem' }}>
                <div className="grid-stack">
                    <CommandMenu01
                        open={isCityModalOpen}
                        setOpen={setCityModalOpen}
                        onSelectLocation={setSelectedLocation}
                    />

                    <div className="grid-stack-item" gs-x="80" gs-y="10" gs-w="20" gs-h="4">
                        <div className="grid-stack-item-content">
                            <NpcPicture npc={selectedNpcId} />
                        </div>
                    </div>

                    <div className="grid-stack-item" gs-x="60" gs-y="10" gs-w="20" gs-h="7">
                        <div className="grid-stack-item-content npc-detail">
                            <Flag symbol={selectedNpcId?.symbol} size={80} />
                            <div className="npc-header">
                                <h1 className="npc-name">
                                    {selectedNpcId?.firstname} {selectedNpcId?.lastname}
                                </h1>
                            </div>
                            <div className="npc-clan">
                                {selectedNpcId?.clan}
                                <div className="npc-clanposition">{selectedNpcId?.clanPosition}</div>
                            </div>
                            <div className="npc-meta">
                                Betonung: {selectedNpcId?.betonung}<br />
                                Spracheigenheiten: {selectedNpcId?.talkingStyle}
                            </div>

                            {/* tbody hinzugefügt – war React hydration warning */}
                            <table className="npc-table">
                                <tbody>
                                <tr><th>Volk</th><td>{selectedNpcId?.race}</td></tr>
                                <tr><th>Geschlecht</th><td>{selectedNpcId?.gender}</td></tr>
                                <tr><th>Alter</th><td>{selectedNpcId?.age}</td></tr>
                                <tr><th>Hintergrund</th><td>{selectedNpcId?.background}</td></tr>
                                </tbody>
                            </table>

                            {selectedNpcId?.shopRelation}

                            <h2 className="npc-section-title"><u>Persönlichkeit:</u></h2>
                            <div className="npc-text-block">
                                <div>{selectedNpcId?.personality}</div>
                                <div>{selectedNpcId?.otherDescription}</div>
                                <div>{selectedNpcId?.likes}</div>
                                <div>{selectedNpcId?.dislikes}</div>
                                <div>{selectedNpcId?.flaw}</div>
                                <div>{selectedNpcId?.ideals}</div>
                            </div>

                            <h2 className="npc-section-title"><u>Optik:</u></h2>
                            <table className="npc-table">
                                <tbody>
                                <tr><th>Kleidung</th><td>{selectedNpcId?.kleidungsQuali}</td></tr>
                                <tr><th>Oberteil</th><td>{selectedNpcId?.jackets}</td></tr>
                                <tr><th>Hose</th><td>{selectedNpcId?.trousers}</td></tr>
                                <tr><th>Haarstil</th><td>{selectedNpcId?.hairstyle}</td></tr>
                                <tr><th>Haarfarbe</th><td>{selectedNpcId?.haircolor}</td></tr>
                                <tr><th>Bartstil</th><td>{selectedNpcId?.beardstyle}</td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div className="grid-stack-item" gs-x="80" gs-y="0" gs-w="40" gs-h="1">
                        <div className="grid-stack-item-content">
                            <NpcStats npc={selectedNpcId} />
                        </div>
                    </div>

                    <div className="grid-stack-item" gs-x="80" gs-y="50" gs-w="20" gs-h="3">
                        <div className="grid-stack-item-content">Party</div>
                    </div>

                    <div className="grid-stack-item" gs-x="35" gs-y="40" gs-w="25" gs-h="2">
                        <div className="grid-stack-item-content">
                            <button style={{width: "100%"}} type="button" onClick={toggleViewMode}>
                                {viewMode === "EMPLOYEE" ? "Mitarbeiter" : "Kunden"}
                            </button>
                            <br/>
                            <div className="flex-1 overflow-y-auto" style={{ width: "100%", minWidth: 250 }}>
                                <SelectList
                                    items={viewMode === "EMPLOYEE" ? employees : customers}
                                    activeId={selectedNpcId ? selectedNpcId.npcId : null}
                                    onSelect={setSelectedNpcId}
                                    onDoubleClick={handleNpcDoubleClick}
                                    getId={(p) => p.npcId}
                                    getLabel={(p) => (
                                        <span>
                                            {p.firstname} {p.lastname}
                                            <br/>
                                            <small>{p.shopRelation}</small>
                                        </span>
                                    )}
                                />
                            </div>
                        </div>
                    </div>

                    <div className="grid-stack-item" gs-x="25" gs-y="40" gs-w="10" gs-h="4">
                        <div className="grid-stack-item-content">
                            <SelectList
                                items={filteredShops.sort(sortByNameAsc)}
                                activeId={selectedShop ? selectedShop.id : null}
                                onSelect={(shop) => setSelectedShopId(shop.id)}
                                onDoubleClick={handleShopDoubleClick}
                                getId={(s) => s.id}
                                getLabel={(s) => s.name}
                            />
                        </div>
                    </div>

                    <div className="grid-stack-item" gs-x="15" gs-y="40" gs-w="10" gs-h="4">
                        <div className="grid-stack-item-content">
                            <SelectList
                                items={availableShopTypes.sort(sortByNameAsc)}
                                activeId={shopType ? shopType.id : null}
                                onSelect={setShopType}
                                getId={(t) => t.id}
                                getLabel={(t) => t.name}
                            />
                        </div>
                    </div>

                    <div className="grid-stack-item" gs-x="35" gs-y="70" gs-w="25" gs-h="2">
                        <div className="grid-stack-item-content" style={{ display: "flex", flexDirection: "column", padding: "0.75rem", gap: "0.5rem" }}>
                            {!selectedShopItem ? (
                                <span style={{ color: "#777" }}>Kein Item ausgewählt</span>
                            ) : (
                                <>
                                    <strong>{selectedShopItem.item.name}</strong>
                                    <div style={{ flex: 1, overflowY: "auto", fontSize: "0.9rem" }}>
                                        {selectedShopItem.item.description ?? "Keine Beschreibung vorhanden."}
                                    </div>
                                    <button type="button" onClick={() => decrementItemQuantity(selectedShopItem)}>
                                        −1 Menge
                                    </button>
                                </>
                            )}
                        </div>
                    </div>

                    <div className="grid-stack-item" gs-x="35" gs-y="42" gs-w="25" gs-h="2">
                        <div className="grid-stack-item-content" style={{ display: "flex", alignItems: "left", boxSizing: "border-box" }}>
                            <div style={{ flex: 1, overflowY: "auto", width: "100%", minWidth: 250 }}>
                                <ShopInventorySelect
                                    shopId={selectedShop?.id}
                                    onSelectItem={(item) => {
                                        setSelectedShopItem(item);
                                        console.log("Ausgewähltes Item:", item);
                                    }}
                                />
                            </div>
                        </div>
                    </div>

                    <div className="grid-stack-item" gs-x="35" gs-y="80" gs-w="25" gs-h="2">
                        <div className="grid-stack-item-content">Karte</div>
                    </div>

                    <div className="grid-stack-item" gs-x="15" gs-y="80" gs-w="20" gs-h="4">
                        <div className="grid-stack-item-content">
                            <QuestNotes activeQuestId={activeQuestId} />
                        </div>
                    </div>

                    <div className="grid-stack-item" gs-x="0" gs-y="100" gs-w="15" gs-h="4">
                        <div className="grid-stack-item-content">
                            <ActiveQuestPanel
                                activeQuestId={activeQuestId}
                                onSelectQuest={setActiveQuestId}
                            />
                        </div>
                    </div>

                    <div className="grid-stack-item" gs-x="0" gs-y="0" gs-w="15" gs-h="1">
                        <div className="grid-stack-item-content">
                            <CityNameSelector selectedLocation={selectedLocation} onOpen={() => setCityModalOpen(true)} />
                        </div>
                    </div>

                    <div className="grid-stack-item" gs-x="0" gs-y="1" gs-w="15" gs-h="3">
                        <div className="grid-stack-item-content">Ruf</div>
                    </div>
                </div>
            </main>
        </>
    );
}
