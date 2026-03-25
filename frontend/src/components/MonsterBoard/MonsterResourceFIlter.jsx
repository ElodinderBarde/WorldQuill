// MonsterResourceFilter.jsx

import { useEffect, useState, useRef } from 'react';
import {
    getMonsterBooks,
    getMonsterHerausforderungsgrade,
    getMonsterSchlagworte
} from "@/service/monsterAPI.js";

export default function MonsterResourceFilter({ onFilterChange }) {

    const [filters, setFilters] = useState({
        source_book:                 '',
        challenge_rating: '',
        keyword:           '',
        sort:                 ''
    });

    const [sourceBook,         setSourceBook]         = useState([]);
    const [challengeRating, setChallengeRating] = useState([]);
    const [keyword,   setKeyword]   = useState([]);

    const isMounted = useRef(false);

    useEffect(() => {
        getMonsterBooks()
            .then(setSourceBook)
            .catch(err => console.error("Fehler Bücher:", err));

        getMonsterHerausforderungsgrade()
            .then(setChallengeRating)
            .catch(err => console.error("Fehler HG:", err));

        getMonsterSchlagworte()
            .then(setKeyword)
            .catch(err => console.error("Fehler Schlagworte:", err));
    }, []);

    useEffect(() => {
        if (!isMounted.current) {
            isMounted.current = true;
            return;
        }
        onFilterChange?.(filters);
    }, [filters, onFilterChange]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFilters(prev => ({ ...prev, [name]: value }));
    };

    const uniqueSorted = (arr) =>
        [...new Set(arr || [])]
            .filter(v => v != null)
            .sort((a, b) =>
                !isNaN(a) && !isNaN(b)
                    ? Number(a) - Number(b)
                    : String(a).localeCompare(String(b), undefined, { sensitivity: "base" })
            );

    return (
        <div className="board-filter">
            <h4>Filter</h4>

            <label>Buch</label>
            <select name="source_book" value={filters.source_book} onChange={handleChange}>
                <option value="">Alle</option>
                {uniqueSorted(sourceBook).map(b => (
                    <option key={b} value={b}>{b}</option>
                ))}
            </select>

            <label>Herausforderungsgrad</label>
            <select name="challenge_rating" value={filters.challenge_rating} onChange={handleChange}>
                <option value="">Alle</option>
                {uniqueSorted(challengeRating).map(hg => (
                    <option key={hg} value={hg}>{hg}</option>
                ))}
            </select>

            <label>Schlagwort</label>
            <select name="keyword" value={filters.keyword} onChange={handleChange}>
                <option value="">Alle</option>
                {uniqueSorted(keyword).map(s => (
                    <option key={s} value={s}>{s}</option>
                ))}
            </select>

            <label>Sortierung</label>
            <select name="sort" value={filters.sort} onChange={handleChange}>
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