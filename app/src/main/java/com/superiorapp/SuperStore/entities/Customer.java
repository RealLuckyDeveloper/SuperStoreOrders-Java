package com.superiorapp.SuperStore.entities;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @CsvBindByName(column = "Customer ID")
    private String id;
    @CsvBindByName(column = "Customer Name")
    private String name;
    @CsvBindByName(column = "Segment")
    private String segment;
    @CsvBindByName(column = "Country")
    private String country;
    @CsvBindByName(column = "City")
    private String city;
    @CsvBindByName(column = "State")
    private String state;
    @CsvBindByName(column = "Postal Code")
    private int postalCode;
    @CsvBindByName(column = "Region")
    private String region;

}

