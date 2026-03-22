import Navbar from '../components/navbar';
import "gridstack/dist/gridstack.min.css";
import '../components/items/layout/itemboard.css';
import MonsterResourceFilter from '@/components/MonsterBoard/MonsterResourceFilter.jsx';
import {useEffect, useRef, useState, useCallback} from 'react';
import { getMonsters } from '../service/monsterAPI.js';
import { GridStack } from "gridstack";
import MonsterList from "@/components/MonsterBoard/MonsterList.jsx";
import MonsterForm from "@/components/MonsterBoard/MonsterForm.jsx";
import MonsterDetail from "@/components/MonsterBoard/MonsterDetail.jsx";
import PdfModal from "@/components/pdfViewer/pdfModal.jsx";
import PdfViewer from "@/components/pdfViewer/pdfViewer.jsx";

export default function Monster() {
    const [allMonsters, setAllMonsters] = useState([]);
    const [filteredMonsters, setFilteredMonsters] = useState([]);
    const [selectedMonster, setSelectedMonster] = useState(null);
    const [showPdf, setShowPdf] = useState(false);
    const [pdfMonster, setPdfMonster] = useState(null);

    const listRef = useRef(null);
    const detailRef = useRef(null);
    const gridRef = useRef(null);
    const grid = useRef(null);

    const openPdf = (monster) => {
        if (!monster) return;
        setPdfMonster(monster);
        setShowPdf(true);
    };

    const closePdf = () => {
        setShowPdf(false);
        setPdfMonster(null);
    };

    useEffect(() => {
        grid.current = GridStack.init({
            column: 12,
            cellHeight: 100,
            margin: 5,
            staticGrid: true,
            disableResize: true,
            disableDrag: true
        }, gridRef.current);

        getMonsters()
            .then(data => {
                if (Array.isArray(data)) {
                    setAllMonsters(data);
                    setFilteredMonsters(data);
                } else {
                    console.error("Ungültige Datenstruktur:", data);
                }
            })
            .catch(console.error);
    }, []);

    const hideMonsterDetails = () => {
        setSelectedMonster(null);
    };

    const handleFilterChange = useCallback(({ buch, herausforderungsgrad, schlagwort, sort }) => {
        let filtered = [...allMonsters];

        if (buch) {
            filtered = filtered.filter(m => m?.book === buch);
        }

        if (herausforderungsgrad) {
            filtered = filtered.filter(m => m?.challengeLvl == herausforderungsgrad);
        }

        if (schlagwort) {
            filtered = filtered.filter(m => m?.schlagwort?.includes(schlagwort));
        }

        // =========================
        // SORTIERUNG
        // =========================
        switch (sort) {
            case "alpha-asc":
                filtered.sort((a, b) => a.name.localeCompare(b.name));
                break;

            case "alpha-desc":
                filtered.sort((a, b) => b.name.localeCompare(a.name));
                break;

            case "hg-asc":
                filtered.sort((a, b) => a.challengeLvl - b.challengeLvl);
                break;

            case "hg-desc":
                filtered.sort((a, b) => b.challengeLvl - a.challengeLvl);
                break;

            case "favoriten":
                filtered.sort((a, b) => b.favorit - a.favorit);
                break;

            case "shiftable":
                filtered.sort((a, b) => b.shiftable - a.shiftable);
                break;
        }

        setFilteredMonsters(filtered);

    }, [allMonsters]);

    useEffect(() => {
        if (!grid.current) return;
        const listEl = listRef.current;
        const detailEl = detailRef.current;
        if (!listEl || !detailEl) return;
        if (selectedMonster) {
            grid.current.update(listEl, {
                w: 5
            });
            grid.current.update(detailEl, {
                w: 4,
                x: 8
            });
        } else {
            grid.current.update(listEl, {
                w: 8
            });

            grid.current.update(detailEl, {
                w: 0,
                x: 12
            });
        }
    }, [selectedMonster]);

    const pdfPages = [
        pdfMonster?.page1, pdfMonster?.page2, pdfMonster?.page3
    ].filter(p => p != null);

    return (
        <>
            <Navbar />
            {/* PDF MODAL */}
            <PdfModal isOpen={showPdf} onClose={closePdf}>
                {pdfMonster && (
                    <PdfViewer file={pdfMonster.book}
                               pages={pdfPages} /> )}
            </PdfModal>
            <div className="grid-stack" ref={gridRef}>
                <div className="grid-stack-item" gs-x="0" gs-y="0" gs-w="3" gs-h="5">
                    <div className="grid-stack-item-content">
                        <MonsterForm/>
                    </div>
                </div>
                <div className="grid-stack-item" gs-x="0" gs-y="5" gs-w="3" gs-h="3">
                    <div className="grid-stack-item-content">
                        <MonsterResourceFilter onFilterChange={handleFilterChange}/>
                    </div>
                </div>

                <div
                    ref={listRef}
                    className="grid-stack-item itemlist-panel"
                    gs-x="3" gs-y="0" gs-w="9" gs-h="8"
                >
                    <div className="grid-stack-item-content">
                        <MonsterList monsters={filteredMonsters} onSelect={setSelectedMonster}
                                     onItemDoubleClick={hideMonsterDetails}/>
                    </div>
                </div>

                <div
                    ref={detailRef}
                    className={`grid-stack-item detail-panel ${selectedMonster ? "active" : ""}`}
                    gs-x="12"
                    gs-y="0"
                    gs-w="0"
                    gs-h="8"
                >
                    <div className="grid-stack-item-content">
                        <MonsterDetail monster={selectedMonster}
                                       onOpenPdf={openPdf}
                        />
                    </div>

                </div>
            </div>
        </>
    );
}
