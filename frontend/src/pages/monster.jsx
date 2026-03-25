// monster.jsx

import Navbar                from '../components/navbar';
import MonsterForm           from "@/components/MonsterBoard/MonsterForm.jsx";
import MonsterResourceFilter from '@/components/MonsterBoard/MonsterResourceFIlter.jsx';
import MonsterList           from "@/components/MonsterBoard/MonsterList.jsx";
import MonsterDetail         from "@/components/MonsterBoard/MonsterDetail.jsx";
import PdfModal              from "@/components/pdfViewer/pdfModal.jsx";
import PdfViewer             from "@/components/pdfViewer/pdfViewer.jsx";
import BoardLayout           from "@/components/BoardTemplates/BoardLayout.jsx";

import { useEffect, useState, useCallback } from 'react';
import { getMonsters }                      from '../service/monsterAPI.js';

// =========================
// ADAPTER
// ausserhalb der Komponente →
// stabile Referenz, kein Remount
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
    const [loading,          setLoading]          = useState(true);
    const [error,            setError]            = useState(null);
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
        setLoading(true);
        setError(null);

        getMonsters()
            .then(data => {
                if (Array.isArray(data)) {
                    setAllMonsters(data);
                    setFilteredMonsters(data);
                } else {
                    setError("Ungültige Datenstruktur vom Server.");
                }
            })
            .catch(() => setError("Monster konnten nicht geladen werden."))
            .finally(() => setLoading(false));
    }, []);

    // =========================
    // FILTER + SORT
    // =========================
// monster.jsx - handleFilterChange

    const handleFilterChange = useCallback(({
                                                source_book,
                                                challenge_rating,
                                                keyword,
                                                sort
                                            }) => {
        let result = [...allMonsters];

        if (source_book)
            result = result.filter(m => m.sourceBook === source_book);

        if (challenge_rating)
            result = result.filter(m =>
                String(m.challengeRating) === String(challenge_rating)
            );

        if (keyword)
            result = result.filter(m => m.keyword?.includes(keyword));

        switch (sort) {
            case "alpha-asc":  result.sort((a, b) => a.name.localeCompare(b.name));    break;
            case "alpha-desc": result.sort((a, b) => b.name.localeCompare(a.name));    break;
            case "hg-asc":     result.sort((a, b) => a.challengeRating - b.challengeRating); break;
            case "hg-desc":    result.sort((a, b) => b.challengeRating - a.challengeRating); break;
            case "favoriten":  result.sort((a, b) => b.favorit   - a.favorit);         break;
            case "shiftable":  result.sort((a, b) => b.shiftable - a.shiftable);       break;
        }

        setFilteredMonsters(result);

        // Selektion zurücksetzen wenn das selektierte
        // Monster nicht mehr in der gefilterten Liste ist
        setSelectedMonster(prev =>
            prev && result.some(m => m.id === prev.id) ? prev : null
        );

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
                    onOpenPdf: openPdf
                }}

                data={filteredMonsters}
                selected={selectedMonster}
                onSelect={handleSelect}
                onDoubleClick={handleDeselect}

                loading={loading}
                error={error}
            />
        </>
    );
}