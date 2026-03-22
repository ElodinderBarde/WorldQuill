import { useEffect, useState } from "react";

export default function BoardForm({ config = [], onSubmit }) {

    const [form, setForm] = useState({});
    const [options, setOptions] = useState({});

    // =========================
    // LOAD OPTIONS
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
                        console.error("Form loader failed:", f.key, e);
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

    const handleChange = (key, value) => {
        setForm(prev => ({ ...prev, [key]: value }));
    };

    const submit = (e) => {
        e.preventDefault();
        onSubmit?.(form);
    };

    return (
        <form onSubmit={submit} className="board-form">

            {config.map(f => (
                <div key={f.key}>
                    <label>{f.label}</label>

                    {f.type === "select" ? (
                        <select
                            value={form[f.key] || ""}
                            onChange={(e) => handleChange(f.key, e.target.value)}
                        >
                            <option value="">-- wählen --</option>
                            {(options[f.key] || []).map(o => (
                                <option key={o} value={o}>{o}</option>
                            ))}
                        </select>
                    ) : (
                        <input
                            type={f.type || "text"}
                            value={form[f.key] || ""}
                            onChange={(e) => handleChange(f.key, e.target.value)}
                        />
                    )}
                </div>
            ))}

            <button type="submit">Erstellen</button>
        </form>
    );
}