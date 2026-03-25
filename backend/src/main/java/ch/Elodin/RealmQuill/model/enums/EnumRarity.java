package ch.Elodin.RealmQuill.model.enums;

import lombok.Getter;

@Getter
public enum EnumRarity {
    COMMON("common", "Gewöhnlich"),
    UNCOMMON("uncommon", "Ungewöhnlich"),
    RARE("rare", "Selten"),
    VERY_RARE("very_rare", "Sehr selten"),
    LEGENDARY("legendary", "Legendär");

    private final String key;
    private final String label;

    EnumRarity(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public static String toLabel(String key) {
        for (EnumRarity r : values()) {
            if (r.key.equals(key)) return r.label;
        }
        return key; // Fallback: Originalwert
    }
}
