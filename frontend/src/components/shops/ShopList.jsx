import {useNavigate} from "react-router-dom";


export default function ShopList({ shops = [], selectedCity, selectedType, onShopClick }) {
  const navigate = useNavigate();
  const normalizedSelectedCity = selectedCity === null || selectedCity === undefined ? "" : String(selectedCity);
  const normalizedSelectedType = selectedType === null || selectedType === undefined ? "" : String(selectedType);

  const filteredShops = shops.filter((shop) => {
    const shopCityId = shop?.locationId ?? shop?.cityId ?? shop?.location?.id;
    const shopTypeId = shop?.shopTypeId ?? shop?.typeId ?? shop?.shopType?.id;

    const matchesCity = !normalizedSelectedCity || String(shopCityId) === normalizedSelectedCity;
    const matchesType = !normalizedSelectedType || String(shopTypeId) === normalizedSelectedType;

    return matchesCity && matchesType;
  });
  const handleDoubleClick = (shopId) => {
    navigate(`/shops/${shopId}`);
  };

  const getShopId = (shop) => shop?.shopId ?? shop?.id;
  const listWidgetProps = { "gs-x": "6", "gs-y": "0", "gs-w": "6", "gs-h": "8" };

  return (
    <div className="grid-stack-item" {...listWidgetProps}>
      <div className="grid-stack-item-content" style={{ flexDirection: "column", alignItems: "flex-start", overflowY: "auto" }}>
        <ul style={{ paddingLeft: "1rem" }}>
          {filteredShops.map((shop, index) => {
              const resolvedShopId = getShopId(shop);
              const shopTypeName = shop?.shopTypeName ?? shop?.typeName;
              const cityName = shop?.cityName ?? shop?.city;
              return (
              <li key={resolvedShopId ?? `shop-${index}`}
                  onClick={() => onShopClick(shop)}
                  onDoubleClick={() => resolvedShopId && handleDoubleClick(resolvedShopId)}>
              <strong>{shop.name}</strong>
                {shopTypeName && ` (${shopTypeName})`}
                {cityName && ` - ${cityName}`}

              </li>
          )})}
        </ul>
      </div>
    </div>
  );
}
