import Navbar from "../../navbar.jsx";

import BoardLayout from "@/components/BoardTemplates/BoardLayout.jsx";
import BoardForm from "@/components/BoardTemplates/BoardForm.jsx";
import BoardFilter from "@/components/BoardTemplates/BoardFilter.jsx";
import BoardTable from "@/components/BoardTemplates/BoardTable.jsx";
import BoardDetail from "@/components/BoardTemplates/BoardDetail.jsx";

import { useState, useCallback } from "react";

export default function TestBoard() {

    // =========================
    // MOCK DATA
    // =========================
    const [data, setData] = useState([
        { id: 1,  name: "Goblin",              hg: 1,  type: "Humanoid"  },
        { id: 2,  name: "Kobold",              hg: 1,  type: "Humanoid"  },
        { id: 3,  name: "Ork",                 hg: 2,  type: "Humanoid"  },
        { id: 4,  name: "Troll",               hg: 5,  type: "Riese"     },
        { id: 5,  name: "Oger",                hg: 4,  type: "Riese"     },
        { id: 6,  name: "Zombie",              hg: 2,  type: "Untot"     },
        { id: 7,  name: "Skelett",             hg: 1,  type: "Untot"     },
        { id: 8,  name: "Ghul",                hg: 2,  type: "Untot"     },
        { id: 9,  name: "Vampir",              hg: 13, type: "Untot"     },
        { id: 10, name: "Lich",                hg: 21, type: "Untot"     },
        { id: 11, name: "Roter Drache",        hg: 17, type: "Drache"    },
        { id: 12, name: "Blauer Drache",       hg: 16, type: "Drache"    },
        { id: 13, name: "Grüner Drache",       hg: 15, type: "Drache"    },
        { id: 14, name: "Weißer Drache",       hg: 14, type: "Drache"    },
        { id: 15, name: "Schwarzer Drache",    hg: 16, type: "Drache"    },
        { id: 16, name: "Drakeling",           hg: 3,  type: "Drache"    },
        { id: 17, name: "Wyvern",              hg: 6,  type: "Drache"    },
        { id: 18, name: "Basilisk",            hg: 8,  type: "Monströs"  },
        { id: 19, name: "Medusa",              hg: 6,  type: "Monströs"  },
        { id: 20, name: "Minotaurus",          hg: 5,  type: "Monströs"  },
        { id: 21, name: "Mantikor",            hg: 3,  type: "Monströs"  },
        { id: 22, name: "Greif",               hg: 2,  type: "Monströs"  },
        { id: 23, name: "Hydra",               hg: 8,  type: "Monströs"  },
        { id: 24, name: "Chimära",             hg: 8,  type: "Monströs"  },
        { id: 25, name: "Zyklop",              hg: 6,  type: "Riese"     },
        { id: 26, name: "Feuerriese",          hg: 9,  type: "Riese"     },
        { id: 27, name: "Eisriese",            hg: 9,  type: "Riese"     },
        { id: 28, name: "Steinriese",          hg: 7,  type: "Riese"     },
        { id: 29, name: "Wolkeriese",          hg: 9,  type: "Riese"     },
        { id: 30, name: "Hügelriese",          hg: 5,  type: "Riese"     },
        { id: 31, name: "Werwolf",             hg: 3,  type: "Gestaltwandler" },
        { id: 32, name: "Werebär",             hg: 5,  type: "Gestaltwandler" },
        { id: 33, name: "Weretiger",           hg: 4,  type: "Gestaltwandler" },
        { id: 34, name: "Imp",                 hg: 1,  type: "Teufel"    },
        { id: 35, name: "Sukubus",             hg: 4,  type: "Teufel"    },
        { id: 36, name: "Bearded Devil",       hg: 3,  type: "Teufel"    },
        { id: 37, name: "Horned Devil",        hg: 11, type: "Teufel"    },
        { id: 38, name: "Pit Fiend",           hg: 20, type: "Teufel"    },
        { id: 39, name: "Quasit",              hg: 1,  type: "Dämon"     },
        { id: 40, name: "Dretch",              hg: 1,  type: "Dämon"     },
        { id: 41, name: "Vrock",               hg: 6,  type: "Dämon"     },
        { id: 42, name: "Hezrou",              hg: 8,  type: "Dämon"     },
        { id: 43, name: "Glabrezu",            hg: 9,  type: "Dämon"     },
        { id: 44, name: "Marilith",            hg: 16, type: "Dämon"     },
        { id: 45, name: "Balor",               hg: 19, type: "Dämon"     },
        { id: 46, name: "Gelatinöser Würfel",  hg: 2,  type: "Ooze"      },
        { id: 47, name: "Schwarzer Pudding",   hg: 4,  type: "Ooze"      },
        { id: 48, name: "Grauer Schleim",      hg: 1,  type: "Ooze"      },
        { id: 49, name: "Ochre Jelly",         hg: 2,  type: "Ooze"      },
        { id: 50, name: "Banshee",             hg: 13, type: "Untot"     },
        { id: 51, name: "Todesritter",         hg: 17, type: "Untot"     },
        { id: 52, name: "Spektrum",            hg: 1,  type: "Untot"     },
        { id: 53, name: "Wraith",              hg: 5,  type: "Untot"     },
        { id: 54, name: "Mumie",               hg: 3,  type: "Untot"     },
        { id: 55, name: "Mumienherr",          hg: 15, type: "Untot"     },
    ]);

    const [filtered,  setFiltered]  = useState(data);
    const [selected,  setSelected]  = useState(null);

    // =========================
    // FILTER CONFIG
    // =========================
    const filterConfig = [
        { key: "type", label: "Typ", loader: async () => ["Humanoid", "Drache", "Untot"] },
        { key: "hg",   label: "HG",  loader: async () => [1, 2, 10] }
    ];

    // =========================
    // FORM CONFIG
    // =========================
    const formConfig = [
        { key: "name", label: "Name" },
        { key: "hg",   label: "HG",  type: "number" },
        { key: "type", label: "Typ", type: "select", loader: async () => ["Humanoid", "Drache", "Untot"] }
    ];

    // =========================
    // TABLE CONFIG
    // =========================
    const columns = [
        { key: "name", label: "Name" },
        { key: "hg",   label: "HG"   },
        { key: "type", label: "Typ"  }
    ];

    // =========================
    // DETAIL CONFIG
    // =========================
    const detailFields = [
        { key: "name", label: "Name"                 },
        { key: "hg",   label: "Herausforderungsgrad" },
        { key: "type", label: "Typ"                  }
    ];

    // =========================
    // FILTER LOGIC
    // useCallback verhindert, dass BoardFilter bei
    // jedem Render eine neue Funktion bekommt
    // =========================
    const handleFilter = useCallback((filters) => {
        setData(prev => {
            let result = [...prev];
            if (filters.type) result = result.filter(r => r.type === filters.type);
            if (filters.hg)   result = result.filter(r => String(r.hg) === String(filters.hg));
            setFiltered(result);
            return prev; // data selbst bleibt unverändert
        });
    }, []);

    // =========================
    // CREATE
    // =========================
    const handleCreate = useCallback((newItem) => {
        const item = {
            id:  Date.now(),
            ...newItem,
            hg:  Number(newItem.hg)
        };

        setData(prev => {
            const updated = [...prev, item];
            setFiltered(updated);
            return updated;
        });
    }, []);

    const handleSelect       = useCallback((row) => setSelected(row),  []);
    const handleDoubleClick  = useCallback(()    => setSelected(null), []);
    const handleReload       = useCallback(()    => {},                []);

    // =========================
    // RENDER
    // Komponenten werden als stabile Elemente
    // übergeben, nicht als inline-Arrow-Functions,
    // damit GridStack-Portals nicht bei jedem
    // Render neu gemountet werden
    // =========================
// TestBoard.jsx

    return (
        <>
            <Navbar />

            <BoardLayout
                // Komponenten-REFERENZEN (keine JSX-Elemente, kein <.../>)
                FormComponent={BoardForm}
                FilterComponent={BoardFilter}
                ListComponent={BoardTable}
                DetailComponent={BoardDetail}

                // Props für die jeweiligen Komponenten
                formProps={{
                    config:   formConfig,
                    onSubmit: handleCreate
                }}
                filterProps={{
                    config:   filterConfig,
                    onChange: handleFilter
                }}
                listProps={{
                    columns
                }}
                detailProps={{
                    fields: detailFields
                }}

                // Daten & State
                data={filtered}
                selected={selected}
                onSelect={handleSelect}
                onDoubleClick={handleDoubleClick}
                onReload={handleReload}
            />
        </>
    );
}