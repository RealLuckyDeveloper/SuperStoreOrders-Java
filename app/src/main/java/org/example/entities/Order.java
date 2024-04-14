package org.example.entities;

import java.time.LocalDate;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @CsvBindByName(column = "Order ID")
    private String id;
    @CsvBindByName(column = "Order Date")
    @CsvDate("d.M.yyyy")
    private LocalDate orderDate;
    @CsvBindByName(column = "Ship Date")
    @CsvDate("d.M.yyyy")
    private LocalDate shipDate;
    @CsvBindByName(column = "Ship Mode")
    private String shipMode;

}
