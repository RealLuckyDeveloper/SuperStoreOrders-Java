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
import com.superiorapp.SuperStore.statistics.Analysis;
import com.superiorapp.SuperStore.ui.util.MapEntry;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class AnalysisWindow {
    private final ObservableList<Row> beans;
    private static final int DEFAULT_SPACING = 10;
    static final DecimalFormat DF = new DecimalFormat("#.##");

    /**
     * public constructor.
     * initializes data needed to make analysis
     * @param data
     */
    public AnalysisWindow(final List<Row> data) {
        this.beans = FXCollections.observableList(data);
    }

    /**
     * builds and shows the Analysis window.
     */
    public void showWindow() {
        final int numOfRows = 4;
        final int maxTableWidth = 300;
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Analysis Window");

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        BorderPane root = new BorderPane();

        final VBox centralVbox = new VBox();
        centralVbox.setSpacing(DEFAULT_SPACING);
        centralVbox.setPadding(new Insets(DEFAULT_SPACING, 0, 0, DEFAULT_SPACING));
        centralVbox.setAlignment(Pos.CENTER);

        final Font boldFont = Font.font((new Label()).getFont().getFamily(), FontWeight.BOLD,
                (new Label()).getFont().getSize());

        HBox averageSalesPerOrderContainer = new HBox(DEFAULT_SPACING);
        averageSalesPerOrderContainer.setAlignment(Pos.CENTER);

        Label averageSalesPerOrderText = new Label("Average sales per order: ");
        Label averageSalesPerOrderValue = new Label(DF.format(Analysis.averageSalesPerOrder(beans)));
        averageSalesPerOrderValue.setFont(boldFont);
        averageSalesPerOrderContainer.getChildren().setAll(averageSalesPerOrderText, averageSalesPerOrderValue);

        Label bestCustomerText = new Label();
        Map<Customer, Double> bestCustomer = Analysis.findBestCustomer(beans);
        bestCustomerText
                .setText("Highest amount of sales for single customer is: "
                        + DF.format(bestCustomer.values().iterator().next())
                        + " which belongs to Customer:");
        TableView<Customer> bestCustomerTable = UI.buildCustomerTable(bestCustomer.keySet().iterator().next());
        bestCustomerText.setFont(boldFont);

        Label numberOfCustomersPerStateText = new Label("Number of customers per state: ");
        Map<String, Integer> numberOfCustomersPerStateMap = Analysis.calculateNumberOfCustomersByAttribute(beans,
                "state");

        TableView<MapEntry> numberOfCustomersPerStateTable = mapToTable(numberOfCustomersPerStateMap, "State");
        numberOfCustomersPerStateTable.setMaxWidth(maxTableWidth);

        Label numberOfCustomersPerSegmentText = new Label("Number of customers per segment: ");
        Map<String, Integer> numberOfCustomersPerSegmentMap = Analysis.calculateNumberOfCustomersByAttribute(beans,
                "segment");
        TableView<MapEntry> numberOfCustomersPerSegmentTable = mapToTable(numberOfCustomersPerSegmentMap, "Segment");
        setNumOfRows(numberOfCustomersPerSegmentTable, numOfRows);

        Label totalSalesPerYearText = new Label("Total sales per year: ");
        Map<Integer, Double> totalSalesPerYearMap = Analysis.calculateTotalSalesPerYear(beans);
        TableView<MapEntry> totalSalesPerYearTable = mapToTable(totalSalesPerYearMap, "Year");
        setNumOfRows(totalSalesPerYearTable, numOfRows);

        Label totalSalesPerRegionText = new Label("Total sales per region: ");
        Map<String, Double> totalSalesPerRegionMap = Analysis.calculateTotalSalesPerRegion(beans);
        TableView<MapEntry> totalSalesPerRegionTable = mapToTable(totalSalesPerRegionMap, "Region");
        setNumOfRows(totalSalesPerRegionTable, numOfRows);

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

    private static TableView<MapEntry> mapToTable(final Map<?, ?> data, final String attribute) {
        TableView<MapEntry> tableView = new TableView<>();
        TableColumn<MapEntry, Object> keyColumn = new TableColumn<>(attribute);
        TableColumn<MapEntry, Object> valueColumn = new TableColumn<>("Value");

        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableView.getColumns().addAll(keyColumn, valueColumn);

        ObservableList<MapEntry> listData = FXCollections.observableArrayList();
        data.forEach((key, value) -> listData.add(new MapEntry(key, DF.format(value))));

        tableView.setItems(listData);

        return tableView;
    }

    private TableView<MapEntry> setNumOfRows(final TableView<MapEntry> table, final int number) {
        final int tableCellSize = 25;
        final int maxWidth = 300;
        table.setFixedCellSize(tableCellSize);
        table.setPrefHeight(tableCellSize * (number + 1));
        table.setMaxWidth(maxWidth);
        return table;
    }
}
