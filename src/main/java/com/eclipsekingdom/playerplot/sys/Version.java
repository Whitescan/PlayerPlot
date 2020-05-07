package com.eclipsekingdom.playerplot.sys;

import org.bukkit.Bukkit;

public enum Version {

    V1_15(115),
    V1_14(114),
    V1_13(113),
    V1_12(112),
    V1_11(111),
    V1_10(110),
    V1_9(109),
    V1_8(108),
    UNKNOWN(0),
    ;

    public static Version current = getVersion();

    private static Version getVersion() {
        String versionString = Bukkit.getVersion();
        if (versionString.contains("1.15")) {
            return V1_15;
        } else if (versionString.contains("1.14")) {
            return V1_14;
        } else if (versionString.contains("1.13")) {
            return V1_13;
        } else if (versionString.contains("1.12")) {
            return V1_12;
        } else if (versionString.contains("1.11")) {
            return V1_11;
        } else if (versionString.contains("1.10")) {
            return V1_10;
        } else if (versionString.contains("1.9")) {
            return V1_9;
        } else if (versionString.contains("1.8")) {
            return V1_8;
        } else {
            return UNKNOWN;
        }
    }

    public final int value;

    Version(int value) {
        this.value = value;
    }

    public boolean isAutoCompleteSupported() {
        return value >= 109;
    }

    public boolean isModelDataSupported() {
        return value >= 113;
    }

    public boolean hasPageTurnSound() {
        return value >= 114;
    }

    public boolean canPlayBorderSound() {
        return value >= 113;
    }

    public boolean isNormalItemConsume() {
        return value >= 111;
    }

    public boolean hasOffhand() {
        return value >= 109;
    }

    public boolean isLegacy() {
        return value <= 112;
    }

    public boolean hasUIToast() {
        return value >= 112;
    }

    public boolean isLegacyTitle() {
        return value <= 110;
    }


}
