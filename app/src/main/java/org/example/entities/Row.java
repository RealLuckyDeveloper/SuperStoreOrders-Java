package org.example.entities;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Row {
    //because of the BOM character in the start of the file
    @CsvBindByName(column = "\uFEFFRow ID")
    private int rowId;
    @CsvRecurse
    private Order order;
    @CsvRecurse
    private Customer customer;
    @CsvRecurse
    private Product product;
    @CsvRecurse
    private FinancialData data;
}
