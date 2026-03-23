// monster.jsx

import Navbar                from '../components/navbar';
import MonsterResourceFilter from '@/components/MonsterBoard/MonsterResourceFilter.jsx';
import MonsterList           from "@/components/MonsterBoard/MonsterList.jsx";
import MonsterForm           from "@/components/MonsterBoard/MonsterForm.jsx";
import MonsterDetail         from "@/components/MonsterBoard/MonsterDetail.jsx";
import PdfModal              from "@/components/pdfViewer/pdfModal.jsx";
import PdfViewer             from "@/components/pdfViewer/pdfViewer.jsx";
import BoardLayout           from "@/components/BoardTemplates/BoardLayout.jsx";

import { useEffect, useState, useCallback } from 'react';
import { getMonsters }                      from '../service/monsterAPI.js';

// =========================
// ADAPTER - ausserhalb der Komponente definiert
// damit React bei jedem Render dieselbe Referenz
// sieht und kein Remount ausgelöst wird
// =========================
function MonsterListAdapter({ data, onSelect, onDoubleClick }) {
    return (
        <MonsterList
            monsters={data}
            onSelect={onSelect}
            onItemDoubleClick={onDoubleClick}
        />
    );
}

// onOpenPdf kann nicht direkt übergeben werden da
// BoardLayout die Props von DetailComponent nicht kennt.
// Lösung: detailProps wird genutzt um onOpenPdf durchzureichen
function MonsterDetailAdapter({ data, onOpenPdf }) {
    return (
        <MonsterDetail
            monster={data}
            onOpenPdf={onOpenPdf}
        />
    );
}

// =========================
// HAUPTKOMPONENTE
// =========================
export default function Monster() {

    const [allMonsters,      setAllMonsters]      = useState([]);
    const [filteredMonsters, setFilteredMonsters] = useState([]);
    const [selectedMonster,  setSelectedMonster]  = useState(null);
    const [showPdf,          setShowPdf]          = useState(false);
    const [pdfMonster,       setPdfMonster]       = useState(null);

    // =========================
    // PDF
    // =========================
    const openPdf = useCallback((monster) => {
        if (!monster) return;
        setPdfMonster(monster);
        setShowPdf(true);
    }, []);

    const closePdf = useCallback(() => {
        setShowPdf(false);
        setPdfMonster(null);
    }, []);

    const pdfPages = [
        pdfMonster?.page1,
        pdfMonster?.page2,
        pdfMonster?.page3,
    ].filter(p => p != null);

    // =========================
    // DATEN LADEN
    // =========================
    useEffect(() => {
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

    // =========================
    // FILTER + SORT
    // =========================
    const handleFilterChange = useCallback(({
                                                buch,
                                                herausforderungsgrad,
                                                schlagwort,
                                                sort
                                            }) => {
        let result = [...allMonsters];

        if (buch)                 result = result.filter(m => m?.book === buch);
        if (herausforderungsgrad) result = result.filter(m => m?.challengeLvl == herausforderungsgrad);
        if (schlagwort)           result = result.filter(m => m?.schlagwort?.includes(schlagwort));

        switch (sort) {
            case "alpha-asc":  result.sort((a, b) => a.name.localeCompare(b.name));    break;
            case "alpha-desc": result.sort((a, b) => b.name.localeCompare(a.name));    break;
            case "hg-asc":     result.sort((a, b) => a.challengeLvl - b.challengeLvl); break;
            case "hg-desc":    result.sort((a, b) => b.challengeLvl - a.challengeLvl); break;
            case "favoriten":  result.sort((a, b) => b.favorit - a.favorit);           break;
            case "shiftable":  result.sort((a, b) => b.shiftable - a.shiftable);       break;
        }

        setFilteredMonsters(result);
    }, [allMonsters]);

    // =========================
    // SELECT / DESELECT
    // =========================
    const handleSelect   = useCallback((monster) => setSelectedMonster(monster), []);
    const handleDeselect = useCallback(()         => setSelectedMonster(null),   []);

    // =========================
    // RENDER
    // =========================
    return (
        <>
            <Navbar />

            <PdfModal isOpen={showPdf} onClose={closePdf}>
                {pdfMonster && (
                    <PdfViewer
                        file={pdfMonster.book}
                        pages={pdfPages}
                    />
                )}
            </PdfModal>

            <BoardLayout
                FormComponent={MonsterForm}
                FilterComponent={MonsterResourceFilter}
                ListComponent={MonsterListAdapter}
                DetailComponent={MonsterDetailAdapter}

                filterProps={{
                    onFilterChange: handleFilterChange
                }}
                detailProps={{
                    onOpenPdf: openPdf  // via detailProps durchgereicht
                }}

                data={filteredMonsters}
                selected={selectedMonster}
                onSelect={handleSelect}
                onDoubleClick={handleDeselect}
            />
        </>
    );
}