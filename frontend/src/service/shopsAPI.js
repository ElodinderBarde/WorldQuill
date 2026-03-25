// src/api/shopApi.js

const BASE_URL = 'http://localhost:8081/';
const API_URL = `${BASE_URL}api/shops`;

// Neuen Shop erstellen
export async function createShop(shop) {
  const response = await fetch(API_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(shop),
  });

  if (!response.ok) throw new Error('Fehler beim Erstellen des Geschäfts');
  return await response.json();
}

// Shop-Beschreibung laden (falls du das brauchst)
export async function getShopDescription() {
  const res = await fetch(`${API_URL}/description`);
  if (!res.ok) throw new Error('Fehler beim Laden der Beschreibung');
  return await res.json();
}

//filter nach Stadt
export async function getCities() {
    const res = await fetch("http://localhost:8081/api/City");
    if (!res.ok) throw new Error("Fehler beim Laden der Städte");
    return await res.json();
  }





function mapShop(shop) {
    return {
        id:           shop.shopId  ?? shop.id          ?? null,
        name:         shop.name,
        notes:        shop.notes   ?? null,
        shopTypeId:   shop.shopType?.id   ?? shop.shopTypeId   ?? null,
        shopTypeName: shop.shopType?.name ?? shop.shopTypeName ?? null,
        locationId:   shop.location?.id   ?? shop.locationId   ?? null,
        cityName:     shop.location?.city?.cityName ?? shop.cityName ?? null,
        villageName:  shop.location?.village?.name  ?? shop.villageName ?? null,
        campaignId:   shop.campaign?.id ?? shop.campaignId ?? null,
    };
}

export const getShops = () =>
    fetch(`${API_URL}`)
        .then(res => res.json())
        .then(data => Array.isArray(data) ? data.map(mapShop) : []);

export const getShopTypes = () =>
    fetch(`${BASE_URL}api/ShopType`)
        .then(res => res.json())
        .then(data => Array.isArray(data) ? data : []);
