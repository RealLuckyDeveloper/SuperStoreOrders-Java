package com.superiorapp.SuperStore.ui.util;

/**
 * The MapEntry class represents a key-value pair in a map.
 * It encapsulates a key and a corresponding value.
 */
public class MapEntry {
    private Object key;
    private Object value;

    /**
     * Constructs a MapEntry object with the specified key and value.
     *
     * @param key the key of the entry
     * @param value the value of the entry
     */
    public MapEntry(final Object key, final Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key of this entry as a string representation.
     *
     * @return the string representation of the key
     */
    public String getKey() {
        return key.toString();
    }

    /**
     * Returns the value of this entry as a string representation.
     *
     * @return the string representation of the value
     */
    public String getValue() {
        return value.toString();
    }
}
