package org.example.entities;

import org.example.util.DoubleConverter;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
