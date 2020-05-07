package com.eclipsekingdom.playerplot.plot.protection.strip;

import com.eclipsekingdom.playerplot.sys.Version;

public class StripIdentifier {

    public static IStripIdentifier select() {
        if (Version.current.value >= 114) {
            return new StripIdentifier_V1_14();
        } else {
            return new StripIdentifier_Unknown();
        }
    }

}
