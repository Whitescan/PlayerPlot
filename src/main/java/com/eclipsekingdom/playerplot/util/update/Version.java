package com.eclipsekingdom.playerplot.util.update;

import java.util.Objects;

public class Version {

    private String name;
    private int[] components;

    public Version(String name) {
        this.name = name;
        String cleanedString = name.replace("[^\\d.]", "");
        String[] parts = cleanedString.split("\\.");
        int length = parts.length;
        if (length == 0) length = 1;
        this.components = new int[length];
        components[0] = 0;
        for (int i = 0; i < parts.length; i++) {
            components[i] = Integer.parseInt(parts[i]);
        }
    }

    public String getName() {
        return name;
    }

    public boolean isOutdated(Version version) {
        int[] newComponents = version.components;
        int[] oldComponents = this.components;
        int newComponentCount = newComponents.length;
        int oldComponentCount = oldComponents.length;
        int componentCount = newComponentCount > oldComponentCount ? newComponentCount : oldComponentCount;
        for (int i = 0; i < componentCount; i++) {
            int newValue = i < newComponentCount ? newComponents[i] : 0;
            int oldValue = i < oldComponentCount ? oldComponents[i] : 0;
            if (newValue > oldValue) {
                return true;
            } else if (oldValue > newValue) {
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version that = (Version) o;
        return Objects.equals(name, that.name);
    }

}
