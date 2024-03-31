package org.example.entities;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
    @CsvBindByName(column = "Product ID")
    private String id;
    @CsvBindByName(column = "Category")
    private String category;
    @CsvBindByName(column = "Sub-Category")
    private String subcategory;
    @CsvBindByName(column = "Product Name")
    private String name;

}
