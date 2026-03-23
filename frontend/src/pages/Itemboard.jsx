// Itemboard.jsx

import Navbar          from '../components/navbar';
import ItemForm        from '../components/items/ItemForm';
import ResourceFilter  from '../components/items/ResourceFilter';
import ItemList        from '../components/items/ItemList';
import ItemDetail      from '../components/items/itemDetail';
import BoardLayout     from "@/components/BoardTemplates/BoardLayout.jsx";

import { useEffect, useState, useCallback } from 'react';
import { getItems }                          from '../service/itemAPI';

// =========================
// ADAPTER - ausserhalb der Komponente
// damit React bei jedem Render dieselbe
// Referenz sieht → kein Remount
// =========================
function ItemListAdapter({ data, onSelect, onDoubleClick }) {
  return (
      <ItemList
          items={data}
          onSelect={onSelect}
          onItemDoubleClick={onDoubleClick}
      />
  );
}

function ItemDetailAdapter({ data }) {
  return (
      <ItemDetail
          item={data}
      />
  );
}

// ResourceFilter erwartet onFilterChange statt onChange →
// Adapter übersetzt den Prop-Namen
function ResourceFilterAdapter({ onFilterChange }) {
  return (
      <ResourceFilter
          onFilterChange={onFilterChange}
      />
  );
}

// =========================
// HAUPTKOMPONENTE
// =========================
export default function Itemboard() {

  const [allItems,      setAllItems]      = useState([]);
  const [filteredItems, setFilteredItems] = useState([]);
  const [selectedItem,  setSelectedItem]  = useState(null);

  // =========================
  // DATEN LADEN
  // =========================
  useEffect(() => {
    getItems()
        .then(data => {
          if (Array.isArray(data)) {
            setAllItems(data);
            setFilteredItems(data);
          } else {
            console.error("Ungültige Datenstruktur:", data);
          }
        })
        .catch(console.error);
  }, []);

  // =========================
  // FILTER + SORT
  // =========================
  const handleFilterChange = useCallback(({ buch, typ, seltenheit, sort }) => {
    let result = [...allItems];

    if (buch)      result = result.filter(item => item?.buch === buch);
    if (typ)       result = result.filter(item => item?.typ  === typ)
        .sort((a, b) => a.name.localeCompare(b.name));
    if (seltenheit) result = result.filter(item => item?.seltenheit === seltenheit);

    switch (sort) {
      case "price-asc":  result.sort((a, b) => a.price - b.price);               break;
      case "price-desc": result.sort((a, b) => b.price - a.price);               break;
      case "alpha-asc":  result.sort((a, b) => a.name.localeCompare(b.name));    break;
      case "alpha-desc": result.sort((a, b) => b.name.localeCompare(a.name));    break;
    }

    setFilteredItems(result);
  }, [allItems]);

  // =========================
  // SELECT / DESELECT
  // =========================
  const handleSelect   = useCallback((item) => setSelectedItem(item), []);
  const handleDeselect = useCallback(()      => setSelectedItem(null), []);

  // =========================
  // RENDER
  // =========================
  return (
      <>
        <Navbar />

        <BoardLayout
            FormComponent={ItemForm}
            FilterComponent={ResourceFilterAdapter}
            ListComponent={ItemListAdapter}
            DetailComponent={ItemDetailAdapter}

            // ResourceFilterAdapter leitet onFilterChange
            // an ResourceFilter weiter
            filterProps={{
              onFilterChange: handleFilterChange
            }}

            data={filteredItems}
            selected={selectedItem}
            onSelect={handleSelect}
            onDoubleClick={handleDeselect}
        />
      </>
  );
}