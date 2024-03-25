package org.example.entities;

import java.time.LocalDate;

public record Order(String id, LocalDate orderDate, LocalDate shipDate) {
    public enum ShipMode {
        /**
         * standart.
         */
        STANDART_CLASS,
        /**
         * first.
         */
        FIRST_CLASS,
        /**
         * second.
         */
        SECOND_CLASS
    }
}
