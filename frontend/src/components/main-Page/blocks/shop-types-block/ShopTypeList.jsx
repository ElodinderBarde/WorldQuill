import { useEffect, useState } from "react";
import SelectList from "@/components/main-Page/elements/SelectList/SelectList";
import { getShopTypes } from "@/service/shopsAPI.js";

export default function ShopTypeList({ locationId, onSelect }) {
    const [shopTypes, setShopTypes] = useState([]);
    const [activeTypeId, setActiveTypeId] = useState(null);

    useEffect(() => {
        async function load() {
            const data = await getShopTypes();
            setShopTypes(data);
        }
        load();
    }, []);

    function handleSelect(type) {
        setActiveTypeId(type.shopTypeId);
        onSelect(type);
    }

    return (
        <SelectList
            items={shopTypes}
            activeId={activeTypeId}
            onSelect={handleSelect}
            getId={(t) => t.shopTypeId}
            getLabel={(t) => t.name}
        />
    );
}
