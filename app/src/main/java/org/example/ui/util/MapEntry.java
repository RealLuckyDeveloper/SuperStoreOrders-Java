package org.example.ui.util;

public class MapEntry {
    private Object key;
    private Object value;

    public MapEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key.toString();
    }

    public String getValue() {
        return value.toString();
    }
}