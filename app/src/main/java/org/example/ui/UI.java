package org.example.ui;

import java.util.List;

import org.example.entities.Customer;
import org.example.entities.FinancialData;
import org.example.entities.Product;
import org.example.entities.Row;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * class representing User Interface.
 */
public class UI extends Application {
    private static List<Row> beans;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        // Text text = new Text(beans.get(0).getCustomer().toString());
        StackPane root = new StackPane();
        // root.getChildren().add(text);

        final VBox mainVbox = new VBox();
        mainVbox.setSpacing(5);
        mainVbox.setPadding(new Insets(10, 0, 0, 10));
        mainVbox.getChildren().addAll(buildMainTable());

        root.getChildren().add(mainVbox);
        // set window size to take all screen space
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * custom launcher.
     * launchs UI and sets data from csv file
     * @param data {@code List<Row>} data parsed from csv
     */
    public static void passDataAndLaunch(final List<Row> data) {
        beans = data;
        launch();
    }

    /**
     * builds main table and returns it.
     * @return Tableview main table
     */
    public TableView<Row> buildMainTable() {
        final ObservableList<Row> tableData = FXCollections.observableArrayList(beans);

        TableView<Row> table = new TableView<Row>(tableData);
        table.setEditable(false);

        TableColumn<Row, Integer> rowIdCol = new TableColumn<>("Row ID");
        rowIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                cellData.getValue()
                        .getRowId())
                .asObject());
        // ----------------------- order column --------------------------------
        TableColumn<Row, Customer> orderCol = new TableColumn<>("Order");

        TableColumn<Row, String> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getOrder()
                        .getId()));

        TableColumn<Row, String> orderDateCol = new TableColumn<>("Order Date");
        orderDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getOrder()
                        .getOrderDate()
                        .toString()));

        TableColumn<Row, String> orderShipDateCol = new TableColumn<>("Ship Date");
        orderShipDateCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue()
                                .getOrder()
                                .getShipDate()
                                .toString()));

        TableColumn<Row, String> orderShipModeCol = new TableColumn<>("Ship Mode");
        orderShipModeCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue()
                                .getOrder()
                                .getShipMode()));

        orderCol.getColumns().addAll(orderIdCol, orderDateCol,
                orderShipDateCol, orderShipModeCol);

        // ==================order column end===================================
        // ------------------customer column start------------------------------

        TableColumn<Row, Customer> customerCol = new TableColumn<>("Customer");

        TableColumn<Row, String> customerIdCol = new TableColumn<>("Customer ID");
        customerIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getCustomer()
                        .getId()));

        TableColumn<Row, String> customerNameCol = new TableColumn<>("Customer Name");
        customerNameCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue()
                                .getCustomer()
                                .getName()));

        TableColumn<Row, String> segmentCol = new TableColumn<>("Segment");
        segmentCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getCustomer()
                        .getSegment()));

        TableColumn<Row, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getCustomer()
                        .getCountry()));

        TableColumn<Row, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getCustomer()
                        .getCity()));

        TableColumn<Row, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getCustomer()
                        .getState()));

        TableColumn<Row, Integer> postalCodeCol = new TableColumn<>("Postal Code");
        postalCodeCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                cellData.getValue()
                        .getCustomer()
                        .getPostalCode())
                .asObject());

        TableColumn<Row, String> regionCol = new TableColumn<>("Region");
        regionCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getCustomer()
                        .getRegion()));

        customerCol.getColumns().addAll(customerIdCol, customerNameCol,
                segmentCol, countryCol,
                cityCol, stateCol,
                postalCodeCol, regionCol);

        // ================customer column end==================================
        // -------------------product column start------------------------------

        TableColumn<Row, Product> productCol = new TableColumn<>("Product");

        TableColumn<Row, String> productIdCol = new TableColumn<>("Product ID");
        productIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getProduct()
                        .getId()));

        TableColumn<Row, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getProduct()
                        .getCategory()));

        TableColumn<Row, String> subcategoryCol = new TableColumn<>("Sub-Category");
        subcategoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getProduct()
                        .getSubcategory()));

        TableColumn<Row, String> productNameCol = new TableColumn<>("Product Name");
        productNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getProduct()
                        .getName()));

        productCol.getColumns().addAll(productIdCol, categoryCol,
                subcategoryCol, productNameCol);

        // ==========================product column end=========================
        // ------------------financial data column start------------------------

        TableColumn<Row, FinancialData> financialDataCol = new TableColumn<>("Financial Data");

        TableColumn<Row, Double> salesCol = new TableColumn<>("Sales");
        salesCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(
                cellData.getValue()
                        .getData()
                        .getSales())
                .asObject());

        TableColumn<Row, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                cellData.getValue()
                        .getData()
                        .getQuantity())
                .asObject());

        TableColumn<Row, Double> discountCol = new TableColumn<>("Discount");
        discountCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(
                cellData.getValue()
                        .getData()
                        .getDiscount())
                .asObject());

        TableColumn<Row, Double> profitCol = new TableColumn<>("Profit");
        profitCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(
                cellData.getValue()
                        .getData()
                        .getProfit())
                .asObject());

        financialDataCol.getColumns().addAll(salesCol, quantityCol,
                discountCol, profitCol);

        // =================financial data column end===========================

        table.getColumns().addAll(rowIdCol, orderCol, customerCol,
                productCol, financialDataCol);
        table.setItems(tableData);
        return table;
    }

}
