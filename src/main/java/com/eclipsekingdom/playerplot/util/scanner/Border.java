package com.eclipsekingdom.playerplot.util.scanner;

import com.eclipsekingdom.playerplot.sys.Version;

public class Border {

    public static IBorder select() {
        switch (Version.getCurrent()) {
            case v1_16_R2:
                return new Border_v1_16_R2();
            case v1_16_R1:
                return new Border_v1_16_R1();
            case v1_15_R1:
                return new Border_v1_15_R1();
            case v1_14_R1:
                return new Border_v1_14_R1();
            case v1_13_R2:
                return new Border_v1_13_R2();
            case v1_13_R1:
                return new Border_v1_13_R1();
            case v1_12_R1:
                return new Border_v1_12_R1();
            case v1_11_R1:
                return new Border_v1_11_R1();
            case v1_10_R1:
                return new Border_v1_10_R1();
            case v1_9_R2:
                return new Border_v1_9_R2();
            case v1_9_R1:
                return new Border_v1_9_R1();
            case v1_8_R3:
                return new Border_v1_8_R3();
            case v1_8_R2:
                return new Border_v1_8_R2();
            default:
                return new Border_Unknown();
        }
    }


}
