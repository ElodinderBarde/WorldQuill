import { useEffect, useState } from 'react';
import {
    getMonsterBooks,
    getMonsterHerausforderungsgrade,
    getMonsterSchlagworte
} from "@/service/monsterAPI.js";

export default function MonsterResourceFilter({ onFilterChange }) {

    const [filters, setFilters] = useState({
        buch: '',
        herausforderungsgrad: '',
        schlagwort: '',
        sort: ''
    });

    const [books, setBooks] = useState([]);
    const [challengeLvls, setChallengeLvls] = useState([]);
    const [schlagworte, setSchlagworte] = useState([]);

    // =========================
    // LOAD FILTER DATA
    // =========================
    useEffect(() => {
        getMonsterBooks()
            .then(setBooks)
            .catch(err => console.error("Fehler Bücher:", err));

        getMonsterHerausforderungsgrade()
            .then(setChallengeLvls)
            .catch(err => console.error("Fehler HG:", err));

        getMonsterSchlagworte()
            .then(setSchlagworte)
            .catch(err => console.error("Fehler Schlagworte:", err));
    }, []);

    // =========================
    // FILTER TRIGGER
    // =========================
    useEffect(() => {
        onFilterChange?.(filters);
    }, [filters, onFilterChange]);

    const handleFilterChange = (e) => {
        const { name, value } = e.target;
        setFilters(prev => ({
            ...prev,
            [name]: value
        }));
    };

    // =========================
    // HELPER: UNIQUE + SORT
    // =========================
    const uniqueSorted = (arr) => {
        return [...new Set(arr || [])]
            .filter(v => v != null)
            .sort((a, b) => {

                // Zahlen sortieren
                if (!isNaN(a) && !isNaN(b)) {
                    return Number(a) - Number(b);
                }

                // Strings sortieren
                return String(a).localeCompare(String(b), undefined, { sensitivity: 'base' });
            });
    };

    return (
        <div className="monster-filter">
            <h4>Filter</h4>

            {/* BUCH */}
            <label>Buch:</label>
            <select name="buch" value={filters.buch} onChange={handleFilterChange}>
                <option value="">Alle</option>
                {uniqueSorted(books).map((b) => (
                    <option key={b} value={b}>{b}</option>
                ))}
            </select>

            <br />

            {/* HG */}
            <label>Herausforderungsgrad:</label>
            <select
                name="herausforderungsgrad"
                value={filters.herausforderungsgrad}
                onChange={handleFilterChange}
            >
                <option value="">Alle</option>
                {uniqueSorted(challengeLvls).map((hg) => (
                    <option key={hg} value={hg}>{hg}</option>
                ))}
            </select>

            <br />

            {/* SCHLAGWORT */}
            <label>Schlagwort:</label>
            <select
                name="schlagwort"
                value={filters.schlagwort}
                onChange={handleFilterChange}
            >
                <option value="">Alle</option>
                {uniqueSorted(schlagworte).map((s) => (
                    <option key={s} value={s}>{s}</option>
                ))}
            </select>

            <br />

            {/* SORT */}
            <label>Sortierung:</label>
            <select name="sort" value={filters.sort} onChange={handleFilterChange}>
                <option value="">Keine</option>
                <option value="alpha-asc">A–Z</option>
                <option value="alpha-desc">Z–A</option>
                <option value="hg-asc">HG ↑</option>
                <option value="hg-desc">HG ↓</option>
                <option value="favoriten">Favoriten zuerst</option>
                <option value="shiftable">Shiftable zuerst</option>
            </select>
        </div>
    );
}