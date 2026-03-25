// MonsterForm.jsx

import { useState, useEffect } from 'react';
import { createMonster, getMonsterBooks } from "@/service/monsterAPI.js";

const EMPTY_FORM = {
    name:         '',
    challengeLvl: '',
    schlagwort:   '',
    book:         '',
    page1:        '',
    page2:        '',
    page3:        '',
    shiftable:    0,
    favorit:      0,
    description:  ''
};

export default function MonsterForm({ onCreated }) {

    const [form,     setForm]     = useState(EMPTY_FORM);
    const [books,    setBooks]    = useState([]);
    const [feedback, setFeedback] = useState(null);
    const [loading,  setLoading]  = useState(false);

    useEffect(() => {
        getMonsterBooks()
            .then(setBooks)
            .catch(err => console.error("Fehler beim Laden der Bücher:", err));
    }, []);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setForm(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? (checked ? 1 : 0) : value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setFeedback(null);

        try {
            await createMonster({
                ...form,
                challengeLvl: form.challengeLvl ? parseFloat(form.challengeLvl) : null,
                page1:        form.page1        ? parseInt(form.page1)          : null,
                page2:        form.page2        ? parseInt(form.page2)          : null,
                page3:        form.page3        ? parseInt(form.page3)          : null,
            });

            setForm(EMPTY_FORM);
            setFeedback({ type: "success", text: "Monster erfolgreich erstellt." });
            onCreated?.();

        } catch (err) {
            console.error(err);
            setFeedback({ type: "error", text: "Fehler beim Erstellen." });
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} autoComplete="off" className="board-form">

            <label>Name</label>
            <input name="name" value={form.name} onChange={handleChange} />

            <label>Herausforderungsgrad</label>
            <input
                type="number"
                step="0.1"
                name="challengeLvl"
                value={form.challengeLvl}
                onChange={handleChange}
            />

            <label>Schlagwort</label>
            <input name="schlagwort" value={form.schlagwort} onChange={handleChange} />

            <label>Buch</label>
            <select name="book" value={form.book} onChange={handleChange}>
                <option value="">-- Quelle wählen --</option>
                {books.map(b => (
                    <option key={b} value={b}>{b}</option>
                ))}
            </select>

            <label>Seite 1</label>
            <input type="number" name="page1" value={form.page1} onChange={handleChange} />

            <label>Seite 2</label>
            <input type="number" name="page2" value={form.page2} onChange={handleChange} />

            <label>Seite 3</label>
            <input type="number" name="page3" value={form.page3} onChange={handleChange} />

            <label>
                Shiftable
                <input
                    type="checkbox"
                    name="shiftable"
                    checked={form.shiftable === 1}
                    onChange={handleChange}
                    style={{ width: "auto", marginLeft: "8px" }}
                />
            </label>

            <label>
                Favorit
                <input
                    type="checkbox"
                    name="favorit"
                    checked={form.favorit === 1}
                    onChange={handleChange}
                    style={{ width: "auto", marginLeft: "8px" }}
                />
            </label>

            <label>Beschreibung</label>
            <textarea
                name="description"
                value={form.description}
                onChange={handleChange}
                rows={4}
            />

            {feedback && (
                <p style={{ color: feedback.type === "success" ? "#6fcf97" : "#eb5757" }}>
                    {feedback.text}
                </p>
            )}

            <button type="submit" disabled={loading}>
                {loading ? "Wird erstellt..." : "Erstellen"}
            </button>

        </form>
    );
}