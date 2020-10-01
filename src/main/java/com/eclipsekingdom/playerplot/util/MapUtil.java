package com.eclipsekingdom.playerplot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapUtil {

    public static void addItemToList(Map map, Object key, Object item) {
        try {
            if (map.containsKey(key)) {
                List list = (List) map.get(key);
                while (list.contains(item)) {
                    list.remove(item);
                }
                list.add(item);
            } else {
                List list = new ArrayList<>();
                list.add(item);
                map.put(key, list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void removeItemFromList(Map map, Object key, Object item) {
        try {
            if (map.containsKey(key)) {
                List list = (List) map.get(key);
                while (list.contains(item)) {
                    list.remove(item);
                }
                if (list.size() < 1) {
                    map.remove(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
