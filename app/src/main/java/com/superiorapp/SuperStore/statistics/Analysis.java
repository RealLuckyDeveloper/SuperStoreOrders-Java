package com.superiorapp.SuperStore.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.superiorapp.SuperStore.entities.Customer;
import com.superiorapp.SuperStore.entities.Row;

public final class Analysis {
    private Analysis() {
    }

    /**
     * calculates average money spent per order.
     *
     * @param data List of Rows with all data
     * @return average oney spent per order
     */
    public static double averageSalesPerOrder(final List<Row> data) {
        Map<String, Double> orderSales = data.stream()
                .collect(Collectors.groupingBy(row -> row.getOrder().getId(),
                        Collectors.summingDouble(row -> row.getData().getSales())));
        return orderSales.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    /**
     * finds the best customer.
     * best customer meaning customer who spent most money on the orders
     *
     * @param data List of Rows with all data
     * @return best customer and amount of money they spent
     */
    public static Map<Customer, Double> findBestCustomer(final List<Row> data) {
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

    /**
     * Calculates the number of customers by a specified attribute from the provided
     * data.
     *
     * @param data      List of Rows with all data
     * @param attribute the attribute by which to categorize customers (currently
     *                  only "state" or "segment" is supported)
     * @return a map containing the count of customers for each distinct attribute
     *         value
     * @throws IllegalArgumentException if the given attribute is not supported
     */
    public static Map<String, Integer> calculateNumberOfCustomersByAttribute(final List<Row> data,
            final String attribute) {
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
                    throw new IllegalArgumentException(
                            "Given incorrect attribute for function "
                                    + "calculateNumberOfCustomersByAttribute(List<Row> data, String attribute): "
                                    + attribute);
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

    /**
     * calculates total sales per year.
     *
     * @param data List of Rows with all data
     * @return Map with stats ready to be displayed
     */
    public static Map<Integer, Double> calculateTotalSalesPerYear(final List<Row> data) {
        Map<Integer, Double> totalSalesPerYear = new HashMap<>();
        data.forEach(row -> {
            int year = row.getOrder().getOrderDate().getYear();
            double sales = row.getData().getSales();
            totalSalesPerYear.merge(year, sales, Double::sum);
        });
        return totalSalesPerYear;
    }

    /**
     * calculates total sales per region.
     *
     * @param data List of Rows with all data
     * @return Map with stats ready to be displayed
     */
    public static Map<String, Double> calculateTotalSalesPerRegion(final List<Row> data) {
        Map<String, Double> totalSalesPerRegion = new HashMap<>();
        data.forEach(row -> {
            String region = row.getCustomer().getRegion();
            double sales = row.getData().getSales();
            totalSalesPerRegion.merge(region, sales, Double::sum);
        });
        return totalSalesPerRegion;
    }
}
