const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081';
const API_URL = `${BASE_URL}/api/monsters`;

// =========================
// GENERIC FETCH WRAPPER
// =========================
async function apiFetch(url, options = {}) {
    const response = await fetch(url, {
        headers: {
            'Content-Type': 'application/json',
            ...(options.headers || {})
        },
        ...options
    });

    if (!response.ok) {
        let message = `HTTP ${response.status}`;
        try {
            const error = await response.json();
            message = error.message || message;
        } catch (_) {}

        throw new Error(message);
    }

    // bei 204 No Content kein JSON parsen
    if (response.status === 204) return null;

    return response.json();
}

// =========================
// MONSTERS
// =========================
export function getMonsters() {
    return apiFetch(API_URL);
}

export function createMonster(monster) {
    return apiFetch(API_URL, {
        method: 'POST',
        body: JSON.stringify(monster),
    });
}

export function setMonsterFavorit(monsterId, favorit) {
    return apiFetch(`${API_URL}/${monsterId}/favorit`, {
        method: 'PUT',
        body: JSON.stringify({ favorit: favorit ? 1 : 0 }),
    });
}

// =========================
// META DATA
// =========================
export function getMonsterBooks() {
    return apiFetch(`${API_URL}/books`);
}

export function getMonsterHerausforderungsgrade() {
    return apiFetch(`${API_URL}/challenge-ratings`);
}

export function getMonsterSchlagworte() {
    return apiFetch(`${API_URL}/keywords`);
};