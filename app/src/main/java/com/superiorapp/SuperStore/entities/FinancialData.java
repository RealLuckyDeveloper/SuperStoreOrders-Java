package com.superiorapp.SuperStore.entities;

import com.superiorapp.SuperStore.util.DoubleConverter;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialData {
    @CsvBindByName(column = "Sales")
    @CsvCustomBindByName(converter = DoubleConverter.class)
    private double sales;

    @CsvBindByName(column = "Quantity")
    private int quantity;

    @CsvBindByName(column = "Discount")
    @CsvCustomBindByName(converter = DoubleConverter.class)
    private double discount;

    @CsvBindByName(column = "Profit")
    @CsvCustomBindByName(converter = DoubleConverter.class)
    private double profit;

}
