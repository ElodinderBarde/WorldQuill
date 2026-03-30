// QuestFilter.jsx
import { useEffect, useState } from "react";

export default function QuestFilter({ onFilterChange }) {
    const [filters, setFilters] = useState({
        name: "",
        questreihe: false,
        gruppe: "",
        ort: "",
        status: ""
    });
    const [questNames, setQuestNames] = useState([]);
    const [locations, setLocations] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8081/api/quest")
            .then((res) => res.json())
            .then((data) => {
                const getLocation = (q) =>
                    q.locationName || q.questlocation?.cityID?.city_name || q.questlocation?.villageID?.village_name || "";

                const names = Array.from(
                    new Set(
                        data
                            .map((q) => q.questName || q.questname || q.monsterName)
                            .filter((name) => typeof name === "string" && name.trim() !== "")
                    )
                ).sort((a, b) => a.localeCompare(b));

                const uniqueLocations = Array.from(
                    new Set(
                        data
                            .map((q) => getLocation(q))
                            .filter((location) => typeof location === "string" && location.trim() !== "")
                    )
                ).sort((a, b) => a.localeCompare(b));

                setQuestNames(names);
                setLocations(uniqueLocations);
            })
            .catch(() => {
                setQuestNames([]);
                setLocations([]);
            });
    }, []);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        const updated = {
            ...filters,
            [name]: type === "checkbox" ? checked : value
        };
        setFilters(updated);
        onFilterChange(updated);
    };

    return (
        <div className="quest-filter-container">
            <h4> Questfilter</h4>

            <div className="form-group-inline">
                <label>Name:</label>
                <select name="name" value={filters.name} onChange={handleChange}>
                    <option value="">Alle</option>
                    {questNames.map((questName) => (
                        <option key={questName} value={questName}>
                            {questName}
                        </option>
                    ))}
                </select>
            </div>

            <div className="form-group-inline">
                <label>Reihe:</label>
                <input type="checkbox" name="questreihe" checked={filters.questreihe} onChange={handleChange} />
            </div>

            <div className="form-group-inline">
                <label>Gruppe:</label>
                <input type="text" name="gruppe" value={filters.gruppe} onChange={handleChange} />
            </div>

            <br/>
            <div className="form-group-inline">
                <label>Ort:</label>
                <select name="ort" value={filters.ort} onChange={handleChange}>
                    <option value="">Alle</option>
                    {locations.map((location) => (
                        <option key={location} value={location}>
                            {location}
                        </option>
                    ))}
                </select>
            </div>

            <div className="form-group-inline">
                <label>Status:</label>
                <select name="status" value={filters.status} onChange={handleChange}>
                    <option value="">Alle</option>
                    <option value="offen">Offen</option>
                    <option value="abgeschlossen">Abgeschlossen</option>
                </select>
            </div>
        </div>
    );
}
