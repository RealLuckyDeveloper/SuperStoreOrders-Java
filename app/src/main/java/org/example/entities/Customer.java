package org.example.entities;

public record Customer(String id, String name, String segment, String country,
                     String city, String state, int postalCode, Region region) {
    public enum Region {
        /**
         * east.
         */
        EAST,
        /**
         * west.
         */
        WEST,
        /**
         * south.
         */
        SOUTH,
        /** north. */
        NORTH,
        /**
         * central.
         */
        CENTRAL
    }
}

