import { useEffect, useState } from "react";

export default function BoardFilter({ config = [], onChange }) {

    const [filters, setFilters] = useState({});
    const [options, setOptions] = useState({});

    // =========================
    // LOAD OPTIONS (FIXED)
    // =========================
    useEffect(() => {
        let active = true;

        const load = async () => {
            const result = {};

            for (const f of config) {
                if (f.loader) {
                    try {
                        result[f.key] = await f.loader();
                    } catch (e) {
                        console.error("Filter loader failed:", f.key, e);
                        result[f.key] = [];
                    }
                }
            }

            if (active) {
                setOptions(result);
            }
        };

        load();

        return () => { active = false; };
    }, [config]);

    // =========================
    // TRIGGER CHANGE
    // =========================
    useEffect(() => {
        onChange?.(filters);
    }, [filters, onChange]);

    const handleChange = (key, value) => {
        setFilters(prev => ({ ...prev, [key]: value }));
    };

    return (
        <div className="board-filter">
            <h4>Filter</h4>

            {config.map(f => (
                <div key={f.key}>
                    <label>{f.label}</label>

                    <select
                        value={filters[f.key] || ""}
                        onChange={(e) => handleChange(f.key, e.target.value)}
                    >
                        <option value="">Alle</option>

                        {(options[f.key] || []).map(opt => (
                            <option key={opt} value={opt}>{opt}</option>
                        ))}
                    </select>
                </div>
            ))}
        </div>
    );
}