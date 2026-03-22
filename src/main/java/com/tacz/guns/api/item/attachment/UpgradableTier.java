package com.tacz.guns.api.item.attachment;

public enum UpgradableTier {
    TIER_1(0, 3),
    TIER_2(3, 6),
    TIER_3(6, 9),
    TIER_4(9, 10);

    private final int minLevel;
    private final int maxLevel;

    UpgradableTier(int minLevel, int maxLevel) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public static UpgradableTier getTierByLevel(int level) {
        for (UpgradableTier tier : values()) {
            if (level >= tier.minLevel && level <= tier.maxLevel) {
                return tier;
            }
        }
        throw new IllegalArgumentException("Invalid weapon level: " + level);
    }
}
