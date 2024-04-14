package org.example;

import org.example.entities.Customer;
import org.example.entities.FinancialData;
import org.example.entities.Order;
import org.example.entities.Product;
import org.example.entities.Row;
import org.junit.jupiter.api.Test;
import org.statistics.Analysis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AnalysisTest {
    // @Test
    // public void testAverageSalesPerOrder_EmptyData() {
    //     List<Row> emptyData = Arrays.asList();
    //     double result = Analysis.averageSalesPerOrder(emptyData);
    //     assertEquals(0.0, result);
    // }

    @Test
    public void testAverageSalesPerOrder_SingleRowUniqueOrder() {
        Order order = new Order("1", LocalDate.now(), LocalDate.now(), "Standard");
        FinancialData data = new FinancialData(100.0, 1, 0.0, 20.0); // Sample FinancialData
        Row row = new Row(1, order, new Customer(), new Product(), data);
        List<Row> dataRows = Arrays.asList(row);
        double result = Analysis.averageSalesPerOrder(dataRows);
        assertEquals(100.0, result);
    }

    @Test
    public void testAverageSalesPerOrder_MultipleRowsSameOrder() {
        Order order = new Order("1", LocalDate.now(), LocalDate.now(), "Standard");
        FinancialData data1 = new FinancialData(50.0, 1, 0.0, 10.0);
        FinancialData data2 = new FinancialData(30.0, 1, 0.0, 5.0);
        Row row1 = new Row(1, order, new Customer(), new Product(), data1);
        Row row2 = new Row(2, order, new Customer(), new Product(), data2);
        List<Row> dataRows = Arrays.asList(row1, row2);
        double result = Analysis.averageSalesPerOrder(dataRows);
        assertEquals(80.0, result);
    }

    @Test
    public void testAverageSalesPerOrder_MixedOrderIDs() {
        Order order1 = new Order("1", LocalDate.now(), LocalDate.now(), "Standard");
        Order order2 = new Order("2", LocalDate.now(), LocalDate.now(), "Express");
        FinancialData data1 = new FinancialData(50.0, 1, 0.0, 10.0);
        FinancialData data2 = new FinancialData(30.0, 1, 0.0, 5.0);
        FinancialData data3 = new FinancialData(20.0, 1, 0.0, 3.0);
        Row row1 = new Row(1, order1, new Customer(), new Product(), data1); // id = 1
        Row row2 = new Row(2, order2, new Customer(), new Product(), data2); // id = 2
        Row row3 = new Row(3, order1, new Customer(), new Product(), data3); // id = 1
        List<Row> dataRows = Arrays.asList(row1, row2, row3);
        double result = Analysis.averageSalesPerOrder(dataRows);
        assertEquals(50.0, result); // ((50 + 20) + 30) / 2 = 50
    }

    // @Test
    // public void testAverageSalesPerOrder_NullChecks() {
    //     Order order1 = new Order("1", LocalDate.now(), LocalDate.now(), "Standard");
    //     Order order2 = null; // Null Order
    //     FinancialData data1 = new FinancialData(50.0, 1, 0.0, 10.0);
    //     FinancialData data2 = null; // Null Data
    //     Row row1 = new Row(1, order1, new Customer(), new Product(), data1);
    //     Row row2 = new Row(2, order2, new Customer(), new Product(), data2);
    //     List<Row> dataRows = Arrays.asList(row1, row2);
    //     double result = Analysis.averageSalesPerOrder(dataRows);
    //     assertEquals(50.0, result); // Only consider non-null rows for calculation
    // }


    @Test
    public void testFindBestCustomer_WithMultipleCustomers_ShouldReturnBestCustomer() {

        Customer customerA = new Customer("A", "John Doe", "SegmentA", "USA", "New York", "NY", 12345, "East");
        Customer customerB = new Customer("B", "Jane Smith", "SegmentB", "Canada", "Toronto", "ON", 67890, "North");

        FinancialData data1 = new FinancialData(100.0, 2, 0.1, 20.0);
        FinancialData data2 = new FinancialData(150.0, 3, 0.2, 30.0);

        Row row1 = new Row(1, null, customerA, null, data1);
        Row row2 = new Row(2, null, customerA, null, data2);
        Row row3 = new Row(3, null, customerB, null, data1);

        List<Row> testData = Arrays.asList(row1, row2, row3);

        // Act
        Map<Customer, Double> result = Analysis.findBestCustomer(testData);

        // Assert
        assertEquals(1, result.size());
        assertEquals(250.0, result.get(customerA)); // Customer A has the highest total sales (100.0 + 150.0)
    }

    @Test
    public void testCalculateNumberOfCustomerByAttribute_UniqueCustomers() {
        // Create sample data for testing
        List<Row> testData = new ArrayList<>();
        testData.add(new Row(1, new Order(), new Customer("1", "John Doe", "Segment1", "USA", "New York", "New York", 10001, "East"), new Product(), new FinancialData()));
        testData.add(new Row(2, new Order(), new Customer("2", "Jane Smith", "Segment2", "USA", "Los Angeles", "California", 90001, "West"), new Product(), new FinancialData()));
        testData.add(new Row(3, new Order(), new Customer("3", "Alice Johnson", "Segment1", "Canada", "Toronto", "Ontario", 12345, "North"), new Product(), new FinancialData()));
        testData.add(new Row(4, new Order(), new Customer("4", "Bob Brown", "Segment3", "USA", "Chicago", "California", 60007, "Midwest"), new Product(), new FinancialData()));
        testData.add(new Row(5, new Order(), new Customer("5", "Joooohn Doooe", "Segment1", "USA", "New York", "New York", 10001, "East"), new Product(), new FinancialData()));
        testData.add(new Row(6, new Order(), new Customer("6", "David Lee", "Segment2", "USA", "Seattle", "Washington", 98101, "West"), new Product(), new FinancialData()));

        // Call the method under test
        Map<String, Integer> result = Analysis.calculateNumberOfCustomersByAttribute(testData, "state");

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(2, result.get("New York").intValue());
        assertEquals(2, result.get("California").intValue());
        assertEquals(1, result.get("Ontario").intValue());
        assertEquals(1, result.get("Washington").intValue());

        Map<String, Integer> result1 = Analysis.calculateNumberOfCustomersByAttribute(testData, "segment");

        assertNotNull(result1);
        assertEquals(3, result1.size());
        for (String name : result1.keySet()) {
            System.out.println(name);
            System.out.println(result1.get(name));
        }
        assertEquals(3, result1.get("Segment1").intValue());
        assertEquals(2, result1.get("Segment2").intValue());
        assertEquals(1, result1.get("Segment3").intValue());
    }

    @Test
    public void testCalculateNumberOfCustomerByAttribute_repeatedCustomers() {
        // Create sample data for testing
        List<Row> testData = new ArrayList<>();
        testData.add(new Row(1, new Order(), new Customer("1", "John Doe", "Segment1", "USA", "New York", "New York", 10001, "East"), new Product(), new FinancialData()));
        testData.add(new Row(2, new Order(), new Customer("2", "Jane Smith", "Segment2", "USA", "Los Angeles", "California", 90001, "West"), new Product(), new FinancialData()));
        testData.add(new Row(3, new Order(), new Customer("3", "Alice Johnson", "Segment1", "Canada", "Toronto", "Ontario", 12345, "North"), new Product(), new FinancialData()));
        testData.add(new Row(4, new Order(), new Customer("4", "Bob Brown", "Segment3", "USA", "Chicago", "California", 60007, "Midwest"), new Product(), new FinancialData()));
        testData.add(new Row(5, new Order(), new Customer("5", "Joooohn Doooe", "Segment1", "USA", "New York", "New York", 10001, "East"), new Product(), new FinancialData()));
        testData.add(new Row(6, new Order(), new Customer("6", "David Lee", "Segment2", "USA", "Seattle", "Washington", 98101, "West"), new Product(), new FinancialData()));
        testData.add(new Row(7, new Order(), new Customer("1", "John Doe", "Segment1", "USA", "New York", "New York", 10001, "East"), new Product(), new FinancialData()));
        testData.add(new Row(8, new Order(), new Customer("2", "Jane Smith", "Segment2", "USA", "Los Angeles", "California", 90001, "West"), new Product(), new FinancialData()));
        testData.add(new Row(9, new Order(), new Customer("3", "Alice Johnson", "Segment1", "Canada", "Toronto", "Ontario", 12345, "North"), new Product(), new FinancialData()));

        // Call the method under test
        Map<String, Integer> result = Analysis.calculateNumberOfCustomersByAttribute(testData, "state");

        assertNotNull(result);
        assertEquals(4, result.size()); // Expecting 3 unique states in the result map
        assertEquals(2, result.get("New York").intValue()); // Expecting 2 customers from "East" state
        assertEquals(2, result.get("California").intValue()); // Expecting 2 customers from "West" state
        assertEquals(1, result.get("Ontario").intValue());
        assertEquals(1, result.get("Washington").intValue());

        Map<String, Integer> result1 = Analysis.calculateNumberOfCustomersByAttribute(testData, "segment");

        assertNotNull(result1);
        assertEquals(3, result1.size());
        assertEquals(3, result1.get("Segment1").intValue());
        assertEquals(2, result1.get("Segment2").intValue());
        assertEquals(1, result1.get("Segment3").intValue());
    }

    @Test
    public void testCalculateTotalSalesPerYear() {
        List<Row> testData = new ArrayList<>();
        // Populate testData with sample rows
        testData.add(createRowForTotalSalesPerYear(1, createDate(2021, 1, 15), 100.0));
        testData.add(createRowForTotalSalesPerYear(2, createDate(2021, 3, 20), 150.0));
        testData.add(createRowForTotalSalesPerYear(3, createDate(2022, 2, 10), 120.0));
        testData.add(createRowForTotalSalesPerYear(4, createDate(2022, 4, 5), 200.0));
        testData.add(createRowForTotalSalesPerYear(5, createDate(2021, 5, 12), 80.0));
        testData.add(createRowForTotalSalesPerYear(6, createDate(2022, 8, 28), 300.0));

        // Call the method under test
        Map<Integer, Double> result = Analysis.calculateTotalSalesPerYear(testData);

        // Assert expected results
        assertNotNull(result);
        assertEquals(2, result.size()); // Expecting data for 2 distinct years

        assertEquals(330.0, result.get(2021), 0.001); // Total sales for year 2021
        assertEquals(620.0, result.get(2022), 0.001); // Total sales for year 2022
        assertFalse(result.containsKey(2020)); // Year with no sales data should not be present
    }

    @Test
    public void testCalculateTotalSalesPerRegion() {
        List<Row> testData = new ArrayList<>();
        // Populate testData with sample rows
        testData.add(new Row(1, new Order(), new Customer("1", "John Doe", "Segment1", "USA", "New York", "New York", 10001, "East"), new Product(), new FinancialData(100, 0, 0, 0)));
        testData.add(new Row(2, new Order(), new Customer("2", "Jane Smith", "Segment2", "USA", "Los Angeles", "California", 90001, "West"), new Product(), new FinancialData(250, 0, 0, 0)));
        testData.add(new Row(3, new Order(), new Customer("3", "Alice Johnson", "Segment1", "Canada", "Toronto", "Ontario", 12345, "South"), new Product(), new FinancialData(30, 0, 0, 0)));
        testData.add(new Row(4, new Order(), new Customer("4", "Bob Brown", "Segment3", "USA", "Chicago", "California", 60007, "Central"), new Product(), new FinancialData(80, 0, 0, 0)));
        testData.add(new Row(5, new Order(), new Customer("5", "Joooohn Doooe", "Segment1", "USA", "New York", "New York", 10001, "East"), new Product(), new FinancialData(150, 0, 0, 0)));
        testData.add(new Row(6, new Order(), new Customer("6", "David Lee", "Segment2", "USA", "Seattle", "Washington", 98101, "West"), new Product(), new FinancialData(100, 0, 0, 0)));
        // Call the method under test
        Map<String, Double> result = Analysis.calculateTotalSalesPerRegion(testData);

        // Assert expected results
        assertNotNull(result);
        assertEquals(4, result.size()); // Expecting data for 4 distinct regions

        assertEquals(250.0, result.get("East"), 0.001); // Total sales for East region
        assertEquals(350.0, result.get("West"), 0.001); // Total sales for West region
        assertEquals(80.0, result.get("Central"), 0.001); // Total sales for Central region
        assertEquals(30.0, result.get("South"), 0.001); // Total sales for South region
        assertFalse(result.containsKey("NoMansLand")); // Region with no sales data should not be present
    }

    // Helper method to create a Row with specified order date and sales amount
    private Row createRowForTotalSalesPerYear(int rowId, LocalDate orderDate, double salesAmount) {
        Order order = new Order("order-1", orderDate, LocalDate.now(), "Standard");
        FinancialData financialData = new FinancialData(salesAmount, 1, 0.0, 0.0);
        return new Row(rowId, order, new Customer(), new Product(), financialData);
    }

    // Helper method to create a LocalDate instance
    private LocalDate createDate(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }
}
