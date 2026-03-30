import { useState, useEffect } from 'react';
import axios from 'axios';

export default function QuestCreate() {
    const [quest, setQuest] = useState({
        questname: '',
        description: '',
        group: '',
        price_item: '',
        price_gold: 0,
        is_active: false,
        status: 'offen',
        questlocationId: '',
        questReihenName: '',
        previousQuestId: null,
        notes: '',
        campaignId: 1
    });

    const [locations, setLocations] = useState([]);
    const [previousQuests, setPreviousQuests] = useState([]);

    useEffect(() => {
        axios.get('/api/locations')
            .then(res => setLocations(res.data))
            .catch(err => console.error(err));

        axios.get('/api/quest')
            .then(res => setPreviousQuests(res.data))
            .catch(err => console.error(err));
    }, []);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setQuest(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value
        }));
    };

    const handleSave = () => {
        axios.post('/api/quest/create', quest)
            .then(() => {
                alert('Quest erfolgreich gespeichert');
                setQuest({
                    questname: '',
                    description: '',
                    group: '',
                    price_item: '',
                    price_gold: 0,
                    is_active: false,
                    status: 'offen',
                    questlocationId: '',
                    questReihenName: '',
                    previousQuestId: null,
                    notes: '',
                    campaignId: 1,
                });
            })
            .catch(err => {
                console.error(err);
                alert('Fehler beim Speichern der Quest. Bitte versuchen Sie es erneut.');
            });
    };

    return (
        <div className="quest-create-container">
            <h3>Neue Quest erstellen:</h3>

            <div className="form-group">
                <label>Questreihenname:</label>
                <input type="text" name="questReihenName" value={quest.questReihenName} onChange={handleChange} />
            </div>
            <div className="form-group">
                <label>Questname:</label>
                <input type="text" name="questname" value={quest.questname} onChange={handleChange} />
            </div>
            <div className="form-group">
                <label>Vorherige Quest:</label>
                <select name="previousQuestId" value={quest.previousQuestId || ''} onChange={handleChange}>
                    <option value="">Bitte wählen...</option>
                    {previousQuests.map(q => (
                        <option key={q.questID} value={q.questID}>{q.questName}</option>
                    ))}
                </select>
            </div>

            <div className="form-group">
                <label>Questgruppe:</label>
                <input type="text" name="group" value={quest.group} onChange={handleChange} />
            </div>

            <div className="form-group">
                <label>Beschreibung:</label>
                <input type="text" name="description" value={quest.description} onChange={handleChange} />
            </div>

            <div className="form-group">
                <label>Ortschaft:</label>
                <select name="questlocationId" value={quest.questlocationId} onChange={handleChange}>
                    <option value="">Bitte wählen...</option>

                    {/* Städte */}
                    {locations
                        .filter(loc => loc.cityName)
                        .map(loc => (
                            <option key={loc.locationId} value={loc.locationId}>
                                {loc.cityName}
                            </option>
                        ))}

                    {/* Trennlinie */}
                    <option disabled>──────────</option>

                    {/* Dörfer */}
                    {locations
                        .filter(loc => loc.villageName)
                        .map(loc => (
                            <option key={loc.locationId} value={loc.locationId}>
                                {loc.villageName}
                            </option>
                        ))}
                </select>

            </div>

            <div className="form-group">
                <label>Status:</label>
                <select name="status" value={quest.status} onChange={handleChange}>
                    <option value="offen">Offen</option>
                    <option value="abgeschlossen">Abgeschlossen</option>
                </select>
            </div>

            <div className="form-group">
                <label>Item:</label>
                <input type="text" name="price_item" value={quest.price_item} onChange={handleChange} />
            </div>

            <div className="form-group">
                <label>Gold:</label>
                <input type="number" name="price_gold" value={quest.price_gold} onChange={handleChange} />
            </div>

            <div className="form-group">
                <label>Aktiv:</label>
                <input type="checkbox" name="is_active" checked={quest.is_active} onChange={handleChange} />
            </div>

            <div className="form-group">
                <label>Notizen:</label>
                <textarea name="notes" rows="6" value={quest.notes} onChange={handleChange} placeholder="Hier könnten Ihre Questnotes stehen ..." />
            </div>

            <button className="save-button" onClick={handleSave}>Speichern</button>
        </div>
    );
}
