import Navbar from '../components/navbar';
import "gridstack/dist/gridstack.min.css";
import '../components/items/layout/itemboard.css';
import ItemForm from '../components/items/ItemForm';
import ResourceFilter from '../components/items/ResourceFilter';
import ItemList from '../components/items/ItemList';
import ItemDetail from '../components/items/itemDetail';
import {useEffect, useRef, useState, useCallback} from 'react';
import { getItems } from '../service/itemAPI';
import { GridStack } from "gridstack";


export default function Itemboard() {
  const [allItems, setAllItems] = useState([]);
  const [filteredItems, setFilteredItems] = useState([]);
  const [selectedItem, setSelectedItem] = useState(null);

  const listRef = useRef(null);
  const detailRef = useRef(null);
  const gridRef = useRef(null);
  const grid = useRef(null);

  useEffect(() => {
    grid.current = GridStack.init({
      column: 12,
      cellHeight: 100,
      margin: 5,
      staticGrid: true,
      disableResize: true,
      disableDrag: true
    }, gridRef.current);

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


  const hideItemDetails = () => {
    setSelectedItem(null);
  }

  const handleFilterChange = useCallback(({buch, typ, seltenheit, sort}) => {
    let filtered = [...allItems];

    if (buch) filtered = filtered.filter(item => item?.buch === buch);
    if (typ) {
      filtered = filtered
          .filter(item => item?.typ === typ)
          .sort((a, b) => a.name.localeCompare(b.name));
    }
    if (seltenheit) filtered = filtered.filter(item => item?.seltenheit === seltenheit);

    switch (sort) {
      case 'price-asc':
        filtered.sort((a, b) => a.price - b.price);
        break;
      case 'price-desc':
        filtered.sort((a, b) => b.price - a.price);
        break;
      case 'alpha-asc':
        filtered.sort((a, b) => a.name.localeCompare(b.name));
        break;
      case 'alpha-desc':
        filtered.sort((a, b) => b.name.localeCompare(a.name));
        break;
    }

    setFilteredItems(filtered);




  }, [allItems]);
  useEffect(() => {
    if (!grid.current) return;
    const listEl = listRef.current;
    const detailEl = detailRef.current;
    if (!listEl || !detailEl) return;
    if (selectedItem) {
      grid.current.update(listEl, {
        w: 5
      });
      grid.current.update(detailEl, {
        w: 4,
        x: 8
      });
    } else {
      grid.current.update(listEl, {
        w: 8
        });

      grid.current.update(detailEl, {
        w: 0,
        x: 12
      });
    }
  }, [selectedItem]);

  return (
      <>
        <Navbar/>
        <div className="grid-stack" ref={gridRef}>
          <div className="grid-stack-item" gs-x="0" gs-y="0" gs-w="3" gs-h="5">
            <div className="grid-stack-item-content">
              <ItemForm />
            </div>
          </div>
          <div className="grid-stack-item" gs-x="0" gs-y="5" gs-w="3" gs-h="3">
            <div className="grid-stack-item-content">
              <ResourceFilter onFilterChange={handleFilterChange}/>
            </div>
          </div>

          <div
              ref={listRef}
               className="grid-stack-item itemlist-panel"
               gs-x="3" gs-y="0" gs-w="9" gs-h="8"
          >
            <div className="grid-stack-item-content">
              <ItemList items={filteredItems} onSelect={setSelectedItem} onItemDoubleClick={hideItemDetails} />
            </div>
          </div>

          <div
              ref={detailRef}
              className={`grid-stack-item detail-panel ${selectedItem ? "active" : ""}`}
              gs-x="12"
              gs-y="0"
              gs-w="0"
              gs-h="8"
          >
            <div className="grid-stack-item-content">
              <ItemDetail item={selectedItem}/>
            </div>
            </div>
        </div>

      </>
  );
}