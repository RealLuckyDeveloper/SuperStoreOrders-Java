package org.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.entities.Customer;
import org.example.entities.Row;

public class Analysis {
    private Analysis() {
    }
    public static double averageSalesPerOrder(List<Row> data) {
        HashMap<String, Double> listOfOrders = new HashMap<String, Double>();
        data.stream().forEach(row -> {
            String orderId = row.getOrder().getId();
            double salesAmount = row.getData().getSales();
            // checks if hashmap already has this order id in it
            if (listOfOrders.get(orderId) == null) {
                listOfOrders.put(orderId, salesAmount);
            } else {
                /* if this orderId was already in the table, add the sales value
                in the current row to the total sales from the specific order.
                This is needed for the cases where same order is in multiple
                rows and contains different products and sales amount.
                For more information refer to the original data
                in the csv file */
                double totalSalesAmount = listOfOrders.get(orderId) + salesAmount;
                listOfOrders.put(orderId, totalSalesAmount);
            }
        });
        double total = 0.0;
        for (double value : listOfOrders.values()) {
            total += value;
        }
        return total / listOfOrders.size();
    }

    public static Map<Customer, Double> findBestCustomer(List<Row> data) {
        Map<Customer, Double> customerTotalSales = new HashMap<>();

        // Calculate total sales for each customer
        for (Row row : data) {
            Customer customer = row.getCustomer();
            double sales = row.getData().getSales();
            customerTotalSales.merge(customer, sales, Double::sum);
        }

        // Find the customer with the highest total sales
        Customer bestCustomer = customerTotalSales.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (bestCustomer != null) {
            double bestCustomerSales = customerTotalSales.get(bestCustomer);
            return Map.of(bestCustomer, bestCustomerSales); // Return the best customer and their total sales
        }

        return new HashMap<>(); // Return an empty map if no best customer found (unlikely with real data)
    }

    public static Map<String, Integer> calculateNumberOfCustomersByAttribute(List<Row> data, String attribute) {
        Map<Customer, String> customerAndAttribute = new HashMap<>();
        
        // Populate customerAndAttribute with customer-attribute mappings
        data.forEach(row -> {
            Customer customer = row.getCustomer();
            String attributeValue = null;
            
            switch (attribute) {
                case "state":
                    attributeValue = customer.getState();
                    break;
                case "segment":
                    attributeValue = customer.getSegment();
                    break;
                
                default:
                    throw new RuntimeException("Given incorrect attribute for function calculateNumberOfCustomersByAttribute(List<Row> data, String attribute): " + attribute);
            }
            
            if (attributeValue != null) {
                customerAndAttribute.put(customer, attributeValue);
            }
        });
        
        // Count occurrences of each attribute value
        Map<String, Integer> attributeCounts = new HashMap<>();
        customerAndAttribute.values().forEach(value -> {
            attributeCounts.merge(value, 1, Integer::sum);
        });
        
        return attributeCounts;
    }

    public static Map<Integer, Double> calculateTotalSalesPerYear(List<Row> data) {
        Map<Integer, Double> totalSalesPerYear = new HashMap<>();
        data.forEach(row -> {
            int year = row.getOrder().getOrderDate().getYear();
            double sales = row.getData().getSales();
            totalSalesPerYear.merge(year, sales, Double::sum);
        });
        return totalSalesPerYear;
    }

    public static Map<String, Double> calculateTotalSalesPerRegion(List<Row> data) {
        Map<String, Double> totalSalesPerYear = new HashMap<>();
        data.forEach(row -> {
            String region = row.getCustomer().getRegion();
            double sales = row.getData().getSales();
            totalSalesPerYear.merge(region, sales, Double::sum);
        });
        return totalSalesPerYear;
    }
}
