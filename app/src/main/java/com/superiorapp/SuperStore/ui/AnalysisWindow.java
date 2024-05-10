package com.superiorapp.SuperStore.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.superiorapp.SuperStore.entities.Customer;
import com.superiorapp.SuperStore.entities.Row;
import com.superiorapp.SuperStore.ui.util.MapEntry;
import com.superiorapp.SuperStore.statistics.Analysis;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class AnalysisWindow {
    private final ObservableList<Row> beans;
    private static final int DEFAULT_SPACING = 10;
    static DecimalFormat df = new DecimalFormat("#.##");

    public AnalysisWindow(List<Row> data) {
        this.beans = FXCollections.observableList(data);
    }

    public void showWindow() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Analysis Window");

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        BorderPane root = new BorderPane();

        final VBox centralVbox = new VBox();
        centralVbox.setSpacing(5);
        centralVbox.setPadding(new Insets(DEFAULT_SPACING, 0, 0, DEFAULT_SPACING));
        centralVbox.setAlignment(Pos.CENTER);

        final Font boldFont = Font.font((new Label()).getFont().getFamily(), FontWeight.BOLD,
                (new Label()).getFont().getSize());

        HBox averageSalesPerOrderContainer = new HBox(DEFAULT_SPACING);
        averageSalesPerOrderContainer.setAlignment(Pos.CENTER);

        Label averageSalesPerOrderText = new Label("Average sales per order: ");
        Label averageSalesPerOrderValue = new Label(df.format(Analysis.averageSalesPerOrder(beans)));
        averageSalesPerOrderValue.setFont(boldFont);
        averageSalesPerOrderContainer.getChildren().setAll(averageSalesPerOrderText, averageSalesPerOrderValue);

        Label bestCustomerText = new Label();
        Map<Customer, Double> bestCustomer = Analysis.findBestCustomer(beans);
        bestCustomerText
                .setText("Highest amount of sales for single customer is: "
                        + df.format(bestCustomer.values().iterator().next())
                        + " which belongs to Customer:");
        TableView<Customer> bestCustomerTable = UI.buildCustomerTable(bestCustomer.keySet().iterator().next());
        bestCustomerText.setFont(boldFont);

        Label numberOfCustomersPerStateText = new Label("Number of customers per state: ");
        Map<String, Integer> numberOfCustomersPerStateMap = Analysis.calculateNumberOfCustomersByAttribute(beans,
                "state");

        TableView<MapEntry> numberOfCustomersPerStateTable = mapToTable(numberOfCustomersPerStateMap, "State");
        numberOfCustomersPerStateTable.setMaxWidth(300); 

        Label numberOfCustomersPerSegmentText = new Label("Number of customers per segment: ");
        Map<String, Integer> numberOfCustomersPerSegmentMap = Analysis.calculateNumberOfCustomersByAttribute(beans,
                "segment");
        TableView<MapEntry> numberOfCustomersPerSegmentTable = mapToTable(numberOfCustomersPerSegmentMap, "Segment");
        setNumOfRows(numberOfCustomersPerSegmentTable, 4);

        Label totalSalesPerYearText = new Label("Total sales per year: ");
        Map<Integer, Double> totalSalesPerYearMap = Analysis.calculateTotalSalesPerYear(beans);
        TableView<MapEntry> totalSalesPerYearTable = mapToTable(totalSalesPerYearMap, "Year");
        setNumOfRows(totalSalesPerYearTable, 4);

        Label totalSalesPerRegionText = new Label("Total sales per region: ");
        Map<String, Double> totalSalesPerRegionMap = Analysis.calculateTotalSalesPerRegion(beans);
        TableView<MapEntry> totalSalesPerRegionTable = mapToTable(totalSalesPerRegionMap, "Region");
        setNumOfRows(totalSalesPerRegionTable, 4);

        centralVbox.getChildren().setAll(averageSalesPerOrderContainer, bestCustomerText,
                bestCustomerTable, numberOfCustomersPerStateText, numberOfCustomersPerStateTable,
                numberOfCustomersPerSegmentText, numberOfCustomersPerSegmentTable,
                totalSalesPerYearText, totalSalesPerYearTable, totalSalesPerRegionText,
                totalSalesPerRegionTable);

        root.setCenter(centralVbox);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);
        primaryStage.setScene(new Scene(scrollPane));
        primaryStage.show();
    }

    private static TableView<MapEntry> mapToTable(Map<?, ?> data, String attribute) {
        TableView<MapEntry> tableView = new TableView<>();
        TableColumn<MapEntry, Object> keyColumn = new TableColumn<>(attribute);
        TableColumn<MapEntry, Object> valueColumn = new TableColumn<>("Value");

        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableView.getColumns().addAll(keyColumn, valueColumn);

        ObservableList<MapEntry> listData = FXCollections.observableArrayList();
        data.forEach((key, value) -> listData.add(new MapEntry(key, df.format(value))));

        tableView.setItems(listData);

        return tableView;
    }

    private TableView<MapEntry> setNumOfRows(TableView<MapEntry> table, int number) {
        table.setFixedCellSize(25);
        table.setPrefHeight(25 * (number + 1));
        table.setMaxWidth(300); 
        return table;
    }
}
