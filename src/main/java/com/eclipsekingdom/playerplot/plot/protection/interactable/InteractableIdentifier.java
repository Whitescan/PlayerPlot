package com.eclipsekingdom.playerplot.plot.protection.interactable;

import com.eclipsekingdom.playerplot.sys.Version;

public class InteractableIdentifier {

    public static IInteractableIdentifier select() {
        switch (Version.current) {
            case V1_15:
                return new InteractableIdentifier_V1_14();
            case V1_14:
                return new InteractableIdentifier_V1_14();
            case V1_13:
                return new InteractableIdentifier_V1_13();
            case V1_12:
                return new InteractableIdentifier_V1_12();
            case V1_11:
                return new InteractableIdentifier_V1_11();
            default:
                return new InteractableIdentifier_Unknown();
        }
    }

}
