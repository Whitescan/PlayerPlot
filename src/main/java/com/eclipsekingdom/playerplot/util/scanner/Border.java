package com.eclipsekingdom.playerplot.util.scanner;

import com.eclipsekingdom.playerplot.sys.Version;

public class Border {

    public static IBorder select() {
        switch (Version.current) {
            case V1_15:
                return new Border_V1_15();
            case V1_14:
                return new Border_V1_14();
            case V1_13:
                return new Border_V1_13();
            case V1_12:
                return new Border_V1_12();
            case V1_11:
                return new Border_V1_11();
            case V1_10:
                return new Border_V1_10();
            case V1_9:
                return new Border_V1_9();
            case V1_8:
                return new Border_V1_8();
            default:
                return new Border_Unknown();
        }
    }


}
