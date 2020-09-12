package com.eclipsekingdom.playerplot.sys;

import org.bukkit.Bukkit;

public enum Version {

    v1_16_R2(116.2),
    v1_16_R1(116.1),
    v1_15_R1(115.1),
    v1_14_R1(114.1),
    v1_13_R2(113.2),
    v1_13_R1(113.1),
    v1_12_R1(112.1),
    v1_11_R1(111.1),
    v1_10_R1(110.1),
    v1_9_R2(109.2),
    v1_9_R1(109.1),
    v1_8_R3(108.3),
    v1_8_R2(108.2),
    v1_8_R1(108.1),
    UNKNOWN(0),
    ;

    private static Version current = getVersion();
    private static String name = current.n;
    private static double value = current.v;

    private static Version getVersion() {
        String v = Bukkit.getServer().getClass().getPackage().getName();
        v = v.substring(v.lastIndexOf('.') + 1);
        if (v.contains("v1_16_R2")) {
            return v1_16_R2;
        } else if (v.contains("v1_16_R1")) {
            return v1_16_R1;
        } else if (v.contains("v1_15_R1")) {
            return v1_15_R1;
        } else if (v.contains("v1_14_R1")) {
            return v1_14_R1;
        } else if (v.contains("v1_13_R2")) {
            return v1_13_R2;
        } else if (v.contains("v1_13_R1")) {
            return v1_13_R1;
        } else if (v.contains("v1_12_R1")) {
            return v1_12_R1;
        } else if (v.contains("v1_11_R1")) {
            return v1_11_R1;
        } else if (v.contains("v1_10_R1")) {
            return v1_10_R1;
        } else if (v.contains("v1_9_R2")) {
            return v1_9_R2;
        } else if (v.contains("v1_9_R1")) {
            return v1_9_R1;
        } else if (v.contains("v1_8_R3")) {
            return v1_8_R3;
        } else if (v.contains("v1_8_R2")) {
            return v1_8_R2;
        } else if (v.contains("v1_8_R1")) {
            return v1_8_R1;
        } else {
            return UNKNOWN;
        }
    }

    private String n;
    private double v;

    Version(double v) {
        this.n = this.toString();
        this.v = v;
    }

    public static Version getCurrent() {
        return current;
    }

    public static String getName() {
        return name;
    }

    public static double getValue() {
        return value;
    }

    public static boolean hasAutoComplete() {
        return value >= 109;
    }

    public static boolean hasModelData() {
        return value >= 114;
    }

    public static boolean isNormalItemConsume() {
        return value >= 111;
    }

    public static boolean hasOffhand() {
        return value >= 109;
    }

    public static boolean hasBossBar() {
        return value >= 113;
    }

    public static boolean hasDustOption() {
        return value >= 113;
    }

    public static boolean hasMultiPassenger() {
        return value >= 111;
    }

    public static boolean isLegacy() {
        return value <= 112;
    }

}
