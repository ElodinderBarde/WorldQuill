import { useEffect, useState } from 'react';
import { getItemRarities, getItemResources, getItemTypes } from '../../service/itemAPI';

export default function ResourceFilter({ onFilterChange }) {
    const [filters, setFilters] = useState({
        buch: '',
        typ: '',
        seltenheit: '',
        sort: '',
    });
    const [resources, setResources] = useState([]);
    const [types, setTypes] = useState([]);
    const [rarities, setRarities] = useState([]);

    useEffect(() => {
        getItemResources()
            .then(setResources)
            .catch((err) => console.error("Fehler beim Laden der Ressourcen:", err));
    }, []);

    useEffect(() => {
        const fetchTypesAndRarities = async () => {
            try {
                const [typesData, raritiesData] = await Promise.all([
                    getItemTypes(),
                    getItemRarities(),
                ]);
                setTypes(Array.isArray(typesData) ? typesData : []);
                setRarities(Array.isArray(raritiesData) ? raritiesData : []);
            } catch (err) {
                console.error("Fehler beim Laden der Typen und Raritäten:", err);
            }
        };
        fetchTypesAndRarities();
    }, []);

    useEffect(() => {
        if (typeof onFilterChange === 'function') {
            onFilterChange(filters);
        }
    }, [filters, onFilterChange]);

    const handleFilterChange = (e) => {
        const { name, value } = e.target;
        setFilters((prev) => ({ ...prev, [name]: value }));
    };

    const safeSortedTypes = () => {
        return types
            .filter((t) => t != null)
            .slice()
            .sort((a, b) => String(a).localeCompare(String(b)));
    };

    return (
        <div className="resource-filter">
            <h4>Filter</h4>
            <label>Buch:</label>
            <select name="buch" value={filters.buch} onChange={handleFilterChange}>
                <option value="">Alle</option>
                {resources.map((r, i) => (
                    <option key={i} value={r}>
                        {r}
                    </option>
                ))}
            </select>
            <br />
            <label>Typ:</label>
            <select name="typ" value={filters.typ} onChange={handleFilterChange}>
                <option value="">Alle</option>
                {safeSortedTypes().map((type) => (
                    <option key={type} value={type}>
                        {type}
                    </option>
                ))}
            </select>
            <br />
            <label>Seltenheit:</label>
            <select name="seltenheit" value={filters.seltenheit} onChange={handleFilterChange}>
                <option value="">Alle</option>
                {rarities.map((rarity) => (
                    <option key={rarity} value={rarity}>
                        {rarity}
                    </option>
                ))}
            </select>
            <br />
            <label>Sortierung:</label>
            <select name="sort" value={filters.sort} onChange={handleFilterChange}>
                <option value="">None</option>
                <option value="price-asc">Price Ascending</option>
                <option value="price-desc">Price Descending</option>
                <option value="alpha-asc">A–Z</option>
                <option value="alpha-desc">Z–A</option>
            </select>
        </div>
    );
}
