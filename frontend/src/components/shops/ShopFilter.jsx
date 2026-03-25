import { useEffect, useState } from "react";
import { getCities, getShopTypes } from "@/service/shopsAPI.js";

export default function ShopFilter({
  selectedCity,
  setSelectedCity,
  selectedType,
  setSelectedType,
}) {
  const [cities, setCities] = useState([]);
  const [types, setTypes] = useState([]);

  useEffect(() => {
    getCities().then(setCities).catch(console.error);

    getShopTypes()
      .then((data) => {
        console.log("ShopTypes geladen:", data);
        setTypes(data);
      })
      .catch(console.error);
  }, []);

  const filterWidgetProps = {
    "gs-x": "1",
    "gs-y": "0",
    "gs-w": "6",
    "gs-h": "1",
  };

  const normalizedSelectedType =
    selectedType === null || selectedType === undefined ? "" : String(selectedType);

  const cityOptions = cities
    .map((city) => ({
      id: city?.id ?? city?.cityId ?? city?.cityID,
      name: city?.city_name ?? city?.cityName ?? city?.name,
    }))
    .filter((city) => city.id !== undefined && city.id !== null && city.name);

  const typeOptions = types
    .map((type) => ({
      id: type?.id ?? type?.shopTypeId ?? type?.typeId,
      name: type?.name ?? type?.shopTypeName ?? type?.typeName,
    }))
    .filter((type) => type.id !== undefined && type.id !== null && type.name);

  return (
    <div className="grid-stack-item" {...filterWidgetProps}>
      <div
        className="grid-stack-item-content"
        style={{ flexDirection: "column", alignItems: "flex-start" }}
      >
        <label>
          Stadt:
          <select value={selectedCity} onChange={(e) => setSelectedCity(e.target.value)}>
            <option value="">Alle Städte</option>
            {cityOptions.map((city) => (
              <option key={city.id} value={String(city.id)}>
                {city.name}
              </option>
            ))}
          </select>
        </label>

        <label style={{ marginTop: "1rem" }}>
          Shoptyp:
          <select
              value={normalizedSelectedType}
              onChange={(e) => {
                const value = e.target.value;
                if (!value) {
                  setSelectedType(null);
                  return;
                }
                const parsed = Number.parseInt(value, 10);
                setSelectedType(Number.isNaN(parsed) ? value : parsed);
              }}
          >
            <option value="">Alle Typen</option>
            {typeOptions.map((type) => (
                    <option key={type.id} value={String(type.id)}>
                      {type.name}
                    </option>
                ))}
          </select>
        </label>

      </div>
    </div>
  );
}