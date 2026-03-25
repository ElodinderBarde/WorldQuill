

export const RARITY = {
    COMMON:    "Gewöhnlich",
    UNCOMMON:  "Ungewöhnlich",
    RARE:      "Selten",
    VERY_RARE: "Sehr selten",
    LEGENDARY: "Legendär",
};

const presets = {
    taverne: {
        label: "Taverne",
        items: [
            { item_type: "Lebensmittel",        rarity: RARITY.COMMON,   quantity: 10 },
            { item_type: "Lebensmittel",        rarity: RARITY.UNCOMMON, quantity: 10 },
            { item_type: "Other",               rarity: RARITY.COMMON,   quantity: 2  },
            { item_type: "Wundersamer Gegenstand", rarity: null,          quantity: 1  },
        ]
    },
    cityshopbig: {
        label: "Shop Stadt gross",
        items: [
            { item_type: "Wundersamer Gegenstand", rarity: null,            quantity: 5 },
            { item_type: "Wundersamer Gegenstand", rarity: null,            quantity: 3 },
            { item_type: "Wundersamer Gegenstand", rarity: RARITY.COMMON,   quantity: 10 },
            { item_type: "Tränke",                 rarity: null,            quantity: 5 },
            { item_type: "Edelsteine",             rarity: null,            quantity: 3 },
            { item_type: "Gift",                   rarity: null,            quantity: 2 },
            { item_type: "Ring",                   rarity: null,            quantity: 2 },
        ]
    },
    cityshopsmall: {
        label: "Shop Stadt klein",
        items: [
            { item_type: "Wundersamer Gegenstand", rarity: RARITY.RARE,     quantity: 1  },
            { item_type: "Wundersamer Gegenstand", rarity: RARITY.UNCOMMON, quantity: 3  },
            { item_type: "Wundersamer Gegenstand", rarity: RARITY.COMMON,   quantity: 10 },
            { item_type: "Tränke",                 rarity: null,            quantity: 5  },
            { item_type: "Edelsteine",             rarity: null,            quantity: 1  },
        ]
    },
    alchemist: {
        label: "Alchemist",
        items: [
            { item_type: "Trank",   rarity: RARITY.RARE,     quantity: 2  },
            { item_type: "Trank",   rarity: RARITY.UNCOMMON, quantity: 5  },
            { item_type: "Trank",   rarity: RARITY.COMMON,   quantity: 10 },
            { item_type: "Zutaten", rarity: null,            quantity: 15 },
        ]
    },
    waffenladen: {
        label: "Waffenladen",
        items: [
            { item_type: "Waffe",   rarity: null, quantity: 1 },
            { item_type: "Waffe",   rarity: null, quantity: 3 },
            { item_type: "Waffe",   rarity: null, quantity: 5 },
            { item_type: "Rüstung", rarity: null, quantity: 5 },
        ]
    },
    schmied: {
        label: "Schmied",
        items: [
            { item_type: "Rüstung",  rarity: RARITY.RARE,     quantity: 1  },
            { item_type: "Rüstung",  rarity: RARITY.UNCOMMON, quantity: 2  },
            { item_type: "Werkzeug", rarity: null,            quantity: 5  },
            { item_type: "Rohstoff", rarity: null,            quantity: 10 },
            { item_type: "Waffe",    rarity: null,            quantity: 8  },
        ]
    },
    circus: {
        label: "Zirkus",
        items: [
            { item_type: "Tiere",          rarity: RARITY.COMMON, quantity: 10 },
            { item_type: "Sattelzeug",     rarity: null,          quantity: 5  },
            { item_type: "Transportmittel",rarity: null,          quantity: 5  },
        ]
    },
    magierladen: {
        label: "Magierladen",
        items: [
            { item_type: "Zauberstab",        rarity: RARITY.RARE,     quantity: 1  },
            { item_type: "Zauberstab",        rarity: RARITY.UNCOMMON, quantity: 2  },
            { item_type: "Schriftrolle",      rarity: RARITY.RARE,     quantity: 2  },
            { item_type: "Schriftrolle",      rarity: RARITY.UNCOMMON, quantity: 5  },
            { item_type: "Schriftrolle",      rarity: RARITY.COMMON,   quantity: 10 },
            { item_type: "Wundersamer Gegenstand", rarity: RARITY.UNCOMMON, quantity: 3 },
            { item_type: "Zutaten",           rarity: null,            quantity: 8  },
        ]
    },
    tempel: {
        label: "Tempel / Heiler",
        items: [
            { item_type: "Trank",          rarity: RARITY.UNCOMMON, quantity: 3  },
            { item_type: "Trank",          rarity: RARITY.COMMON,   quantity: 8  },
            { item_type: "Schriftrolle",   rarity: RARITY.COMMON,   quantity: 5  },
            { item_type: "Heiliges Symbol",rarity: RARITY.UNCOMMON, quantity: 2  },
            { item_type: "Zutaten",        rarity: null,            quantity: 10 },
        ]
    },
    hafen: {
        label: "Hafenladen",
        items: [
            { item_type: "Werkzeug",       rarity: null,            quantity: 8  },
            { item_type: "Rohstoff",       rarity: null,            quantity: 15 },
            { item_type: "Transportmittel",rarity: null,            quantity: 2  },
            { item_type: "Waffe",          rarity: null,            quantity: 4  },
            { item_type: "Lebensmittel",   rarity: null,            quantity: 20 },
            { item_type: "Karte",          rarity: RARITY.UNCOMMON, quantity: 3  },
        ]
    },
    diebesgilde: {
        label: "Diebesgilde",
        items: [
            { item_type: "Gift",                  rarity: RARITY.RARE,     quantity: 1 },
            { item_type: "Gift",                  rarity: RARITY.UNCOMMON, quantity: 3 },
            { item_type: "Werkzeug",              rarity: RARITY.UNCOMMON, quantity: 4 },
            { item_type: "Wundersamer Gegenstand",rarity: RARITY.UNCOMMON, quantity: 2 },
            { item_type: "Edelsteine",            rarity: null,            quantity: 5 },
        ]
    },
    bibliothek: {
        label: "Bibliothek / Skriptorium",
        items: [
            { item_type: "Schriftrolle", rarity: RARITY.RARE,     quantity: 1  },
            { item_type: "Schriftrolle", rarity: RARITY.UNCOMMON, quantity: 4  },
            { item_type: "Schriftrolle", rarity: RARITY.COMMON,   quantity: 10 },
            { item_type: "Karte",        rarity: RARITY.RARE,     quantity: 1  },
            { item_type: "Karte",        rarity: RARITY.UNCOMMON, quantity: 3  },
        ]
    },
    stall: {
        label: "Stall / Tierhändler",
        items: [
            { item_type: "Tiere",       rarity: RARITY.UNCOMMON, quantity: 2  },
            { item_type: "Tiere",       rarity: RARITY.COMMON,   quantity: 5  },
            { item_type: "Sattelzeug",  rarity: null,            quantity: 6  },
            { item_type: "Lebensmittel",rarity: null,            quantity: 10 },
            { item_type: "Werkzeug",    rarity: null,            quantity: 3  },
        ]
    },
    schwarzmarkt: {
        label: "Schwarzmarkt",
        items: [
            { item_type: "Wundersamer Gegenstand", rarity: RARITY.VERY_RARE, quantity: 1 },
            { item_type: "Wundersamer Gegenstand", rarity: RARITY.RARE,      quantity: 2 },
            { item_type: "Gift",                   rarity: RARITY.RARE,      quantity: 2 },
            { item_type: "Edelsteine",             rarity: RARITY.RARE,      quantity: 3 },
            { item_type: "Schriftrolle",           rarity: RARITY.RARE,      quantity: 2 },
            { item_type: "Ring",                   rarity: RARITY.RARE,      quantity: 1 },
        ]
    },
};

export default presets;


