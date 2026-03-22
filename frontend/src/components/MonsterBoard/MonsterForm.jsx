import { useState, useEffect } from 'react';
import { createMonster, getMonsterBooks } from "@/service/monsterAPI.js";

function MonsterForm() {

    const [form, setForm] = useState({
        name: '',
        challengeLvl: '',
        schlagwort: '',
        book: '',
        page1: '',
        page2: '',
        page3: '',
        shiftable: 0,
        favorit: 0,
        description: ''
    });

    const [books, setBooks] = useState([]);

    useEffect(() => {
        getMonsterBooks()
            .then(setBooks)
            .catch(err => console.error("Fehler beim Laden der Bücher:", err));
    }, []);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;

        let val = value;

        if (type === 'checkbox') {
            val = checked ? 1 : 0;
        }

        setForm(prev => ({
            ...prev,
            [name]: val
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const payload = {
                ...form,
                challengeLvl: form.challengeLvl ? parseFloat(form.challengeLvl) : null,
                page1: form.page1 ? parseInt(form.page1) : null,
                page2: form.page2 ? parseInt(form.page2) : null,
                page3: form.page3 ? parseInt(form.page3) : null,
            };

            await createMonster(payload);

            alert('Monster erfolgreich erstellt!');

            setForm({
                name: '',
                challengeLvl: '',
                schlagwort: '',
                book: '',
                page1: '',
                page2: '',
                page3: '',
                shiftable: 0,
                favorit: 0,
                description: ''
            });

        } catch (err) {
            console.error(err);
            alert('Fehler beim Erstellen.');
        }
    };

    return (
        <form onSubmit={handleSubmit} autoComplete="off" className="monster-form">

            <label>
                Name:
                <input name="name" value={form.name} onChange={handleChange} />
            </label>

            <br />

            <label>
                Herausforderungsgrad:
                <input type="number" step="0.1" name="challengeLvl" value={form.challengeLvl} onChange={handleChange} />
            </label>

            <br />

            <label>
                Schlagwort:
                <input name="schlagwort" value={form.schlagwort} onChange={handleChange} />
            </label>

            <br />

            <label>
                Buch:
                <select name="book" value={form.book} onChange={handleChange}>
                    <option value="">-- Quelle wählen --</option>
                    {books.map((b) => (
                        <option key={b} value={b}>{b}</option>
                    ))}
                </select>
            </label>

            <br />

            <label>
                Seite 1:
                <input type="number" name="page1" value={form.page1} onChange={handleChange} />
            </label>

            <br />

            <label>
                Seite 2:
                <input type="number" name="page2" value={form.page2} onChange={handleChange} />
            </label>

            <br />

            <label>
                Seite 3:
                <input type="number" name="page3" value={form.page3} onChange={handleChange} />
            </label>

            <br />

            <label>
                Shiftable:
                <input
                    type="checkbox"
                    name="shiftable"
                    checked={form.shiftable === 1}
                    onChange={handleChange}
                />
            </label>

            <br />

            <label>
                Favorit:
                <input
                    type="checkbox"
                    name="favorit"
                    checked={form.favorit === 1}
                    onChange={handleChange}
                />
            </label>

            <br />

            <label>
                Beschreibung:
                <br />
                <textarea
                    name="description"
                    value={form.description}
                    onChange={handleChange}
                />
            </label>

            <br />

            <button type="submit">Erstellen</button>
        </form>
    );
}

export default MonsterForm;