const API_URL = 'http://localhost:8081/api/items';

export async function getItems() {
  const response = await fetch(API_URL);
  if (!response.ok) throw new Error('Fehler beim Laden der Items');
  return await response.json();
}

export async function createItem(item) {
  const response = await fetch(API_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(item),
  });

  if (!response.ok) throw new Error('Fehler beim Erstellen des Items');
  return await response.json();
}


export const getItemResources = async () => {
  const res = await fetch('http://localhost:8081/api/items/resources');
  if (!res.ok) throw new Error('Fehler beim Laden der Ressourcen');
  return await res.json();
};
export const getItemTypes = async () => {
  const response = await fetch("http://localhost:8081/api/items/types");
  if (!response.ok) {
    throw new Error("Fehler beim Laden der Itemtypen");
  }
  return response.json();
};
export const getItemRarities = async () => {
  const response = await fetch("http://localhost:8081/api/items/rarities");
  if (!response.ok) {
    throw new Error("Fehler beim Laden der Seltenheiten");
  }
  return response.json();
};