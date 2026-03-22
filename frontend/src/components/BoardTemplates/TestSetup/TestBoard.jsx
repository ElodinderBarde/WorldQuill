import Navbar from "../../Navbar.jsx";

import BoardLayout from "@/components/BoardTemplates/BoardLayout.jsx";
import BoardForm from "@/components/BoardTemplates/BoardForm.jsx";
import BoardFilter from "@/components/BoardTemplates/BoardFilter.jsx";
import BoardTable from "@/components/BoardTemplates/BoardTable.jsx";
import BoardDetail from "@/components/BoardTemplates/BoardDetail.jsx";

import { useState } from "react";

export default function TestBoard() {

    // =========================
    // MOCK DATA
    // =========================
    const [data, setData] = useState([
        { id: 1, name: "Goblin", hg: 1, type: "Humanoid" },
        { id: 2, name: "Drache", hg: 10, type: "Drache" },
        { id: 3, name: "Zombie", hg: 2, type: "Untot" }
    ]);

    const [filtered, setFiltered] = useState(data);
    const [selected, setSelected] = useState(null);

    // =========================
    // FILTER CONFIG
    // =========================
    const filterConfig = [
        {
            key: "type",
            label: "Typ",
            loader: async () => ["Humanoid", "Drache", "Untot"]
        },
        {
            key: "hg",
            label: "HG",
            loader: async () => [1, 2, 10]
        }
    ];

    // =========================
    // FORM CONFIG
    // =========================
    const formConfig = [
        { key: "name", label: "Name" },
        { key: "hg", label: "HG", type: "number" },
        {
            key: "type",
            label: "Typ",
            type: "select",
            loader: async () => ["Humanoid", "Drache", "Untot"]
        }
    ];

    // =========================
    // TABLE CONFIG
    // =========================
    const columns = [
        { key: "name", label: "Name" },
        { key: "hg", label: "HG" },
        { key: "type", label: "Typ" }
    ];

    // =========================
    // DETAIL CONFIG
    // =========================
    const detailFields = [
        { key: "name", label: "Name" },
        { key: "hg", label: "Herausforderungsgrad" },
        { key: "type", label: "Typ" }
    ];

    // =========================
    // FILTER LOGIC
    // =========================
    const handleFilter = (filters) => {
        let result = [...data];

        if (filters.type) {
            result = result.filter(r => r.type === filters.type);
        }

        if (filters.hg) {
            result = result.filter(r => String(r.hg) === String(filters.hg));
        }

        setFiltered(result);
    };

    // =========================
    // CREATE
    // =========================
    const handleCreate = (newItem) => {
        const id = Date.now();

        const item = {
            id,
            ...newItem,
            hg: Number(newItem.hg)
        };

        const updated = [...data, item];

        setData(updated);
        setFiltered(updated);
    };

    return (
        <>
            <Navbar />

            <BoardLayout
                FormComponent={(props) => (
                    <BoardForm {...props} config={formConfig} onSubmit={handleCreate} />
                )}

                FilterComponent={(props) => (
                    <BoardFilter {...props} config={filterConfig} onChange={handleFilter} />
                )}

                ListComponent={(props) => (
                    <BoardTable {...props} columns={columns} />
                )}

                DetailComponent={(props) => (
                    <BoardDetail {...props} fields={detailFields} />
                )}

                data={filtered}
                selected={selected}
                onSelect={setSelected}
                onDoubleClick={() => setSelected(null)}
                onFilterChange={handleFilter}
                onReload={() => {}}
            />
        </>
    );
}