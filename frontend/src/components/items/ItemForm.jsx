import { useState, useEffect } from 'react';

import {createItem, getItemRarities, getItemResources, getItemTypes} from '../../service/itemAPI';


export default function ItemForm() {
  const [item, setItem] = useState({
    name: '',
    price: 0,
    typ: '', // Start leer, wird über Dropdown gesetzt
    seltenheit: '',
    buch: '',
    seite1: null,
    seite2: null,
    seite3: null,
    einstimmung: 'N', // Start mit Ja
    beschreibung: ''
  });

  const [resources, setResources] = useState([]);
    const [types, setTypes] = useState([]);
    const [rarities, setRarities] = useState([]);
    useEffect(() => {

        const fetchTypes = async () => {
            try {
                const data = await getItemTypes();
                setTypes(data);
            } catch (err) {
                console.error("Fehler beim Laden der Typen:", err);
            }
        };

        const fetchRarities = async () => {
            try {
                const data = await getItemRarities();
                setRarities(data);
            } catch (err) {
                console.error("Fehler beim Laden der Rarities:", err);
            }
        };

        fetchTypes();
        fetchRarities();

    }, []);
  useEffect(() => {
    const fetchResources = async () => {
      try {
        const data = await getItemResources();
        setResources(data);
      } catch (error) {
        console.error("Fehler beim Laden der Ressourcen:", error);
      }
    };
    fetchResources();
  }, []);



  const handleChange = (e) => {
    const { name, value } = e.target;
    setItem((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
        await createItem(item);
        alert('Item erfolgreich erstellt!');
        setItem({ ...item, name: '', beschreibung: '' }); // Reset
    } catch (err) {
      console.error(err);
      alert('Fehler beim Erstellen.');
    }
  };
    const safeSortedTypes = () => {
        return types
            .filter((t) => t != null)
            .slice()
            .sort((a, b) => String(a).localeCompare(String(b)));
    };
  return (
    <form onSubmit={handleSubmit} autoComplete="off" className="item-form">
      <label>
        Name:
        <input name="name" value={item.name} onChange={handleChange} />
      </label>
      <br />
      <label>
        Preis:
        <input type="number" name="price" value={item.price} onChange={handleChange} />
      </label>
      <br />
      <label>
        Seltenheit:
      <input type="text" name="seltenheit" value={item.rarity} onChange={handleChange} />
      </label>
      <br />
      <label>
        Typ:
          <select name="typ" value={item.typ} onChange={handleChange}>
              <option value="">Alle</option>

              {safeSortedTypes().map((type) => (
                  <option key={type} value={type}>
                      {type}
                  </option>
              ))}
          </select>
          <select name="rarity" value={item.rarity} onChange={handleChange}>
              <option value="">Alle</option>

              {rarities.map((rarity) => (
                  <option key={rarity} value={rarity}>
                      {rarity}
                  </option>
              ))}

          </select>
      </label>
      <br />
<label>
  Buch:
  <select name="buch" value={item.buch} onChange={handleChange}>
    <option value="">-- Quelle wählen --</option>
    {resources.map((r, index) => (
      <option key={index} value={r}>
        {r}
      </option>
    ))}
  </select>
</label>
<br />

      <label>
        Buchseite:
        <input type="number" name="seite1" value={item.seite1 || ''} onChange={handleChange} />
      </label>
      <br /><br />
      <fieldset>
        <legend>Einstimmung erforderlich:</legend>
        <label>
          <input
            type="radio"
            name="einstimmung"
            value="Y"
            checked={item.einstimmung === 'Y'}
            onChange={handleChange}
          />
          Ja
        </label>
        <label>
          <input
            type="radio"
            name="einstimmung"
            value="N"
            checked={item.einstimmung === 'N'}
            onChange={handleChange}
          />
          Nein
        </label>
      </fieldset>
      <br />
      <label className='beschreibungTB'>
        Beschreibung:
        <br/>
        <textarea
  className="beschreibungTB"
  name="beschreibung"
  value={item.beschreibung}
  onChange={handleChange}
/>

      </label>
      <br /><br />
      <button type="submit">Erstellen</button>
    </form>
  );
}
