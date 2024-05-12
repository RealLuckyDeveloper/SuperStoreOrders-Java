package com.superiorapp.SuperStore.ui;

import java.util.List;

import com.superiorapp.SuperStore.entities.Customer;
import com.superiorapp.SuperStore.entities.FinancialData;
import com.superiorapp.SuperStore.entities.Order;
import com.superiorapp.SuperStore.entities.Product;
import com.superiorapp.SuperStore.entities.Row;
import com.superiorapp.SuperStore.ui.util.ColumnUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static com.superiorapp.SuperStore.ui.util.ColumnUtils.collapseColumn;
import static com.superiorapp.SuperStore.ui.util.ColumnUtils.expandColumn;
import static com.superiorapp.SuperStore.ui.util.ColumnUtils.getColumnTitle;
import static com.superiorapp.SuperStore.ui.util.ColumnUtils.toggleColumnExpansion;

/**
 * class representing User Interface.
 */
public class UI extends Application {
    private static final Logger LOG = LogManager.getLogger(UI.class);

    private static ObservableList<Row> beans;
    // for search function
    // private static ObservableList<Row> filteredList;
    private static List<String> returnedOrderIDs;
    private TableView<Row> table = buildMainTable();

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        final int topBottomPadding = 10;
        final int centralVboxSpacing = 5;
        BorderPane root = new BorderPane();

        final VBox centralVbox = new VBox();
        centralVbox.setSpacing(centralVboxSpacing);
        centralVbox.setPadding(new Insets(topBottomPadding, 0, 0, topBottomPadding));
        VBox.setVgrow(table, Priority.ALWAYS);
        centralVbox.getChildren().addAll(buildSearchBar(), table);

        root.setCenter(centralVbox);
        root.setTop(buildMenuBar(table));
         //set Stage boundaries to visible bounds of the main screen
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
     * launchs UI and sets data from csv files
     *
     * @param data           {@code List<Row>} data parsed from csv
     * @param returnedOrders list of returned orders
     */
    public static void passDataAndLaunch(final List<Row> data, final List<String> returnedOrders) {
        beans = FXCollections.observableList(data);
        // filteredList = beans;
        returnedOrderIDs = returnedOrders;
        launch();
    }

    /**
     * builds main table and returns it.
     *
     * @return Tableview main table
     */
    @SuppressWarnings("unchecked")
    private TableView<Row> buildMainTable() {

        TableView<Row> table = new TableView<Row>(beans);
        table.setEditable(false);
        table.setMaxHeight(Double.MAX_VALUE);
        table.setOnMouseClicked(mouseEvent -> {
            table.getSelectionModel().getSelectedCells().forEach(cell -> {
                if (cell != null && cell.getTableColumn() != null
                        && cell.getTableColumn().getText().equals("Customer Name")) {
                    Customer customer = table.getSelectionModel().getSelectedItem().getCustomer();
                    try {
                        buildOrdersMadeByCustomerWindow(customer).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    LOG.info("TablePosition is null!");
                }
            });
        });

        TableColumn<Row, Integer> rowIdCol = new TableColumn<>("Row ID");
        rowIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                cellData.getValue()
                        .getRowId())
                .asObject());

        table.getColumns().addAll(rowIdCol, buildOrderColumn(), buildCustomerColumn(),
                buildProductColumn(), buildFinancialDataColumn());

        return table;
    }

    private TableColumn<Row, Order> buildOrderColumn() {
        TableColumn<Row, Order> orderCol = new TableColumn<>();

        TableColumn<Row, String> orderIdCol = new TableColumn<>("Order ID");

        orderIdCol.setCellFactory(column -> {
            return new TableCell<Row, String>() {
                @Override
                protected void updateItem(final String orderId, final boolean empty) {
                    super.updateItem(orderId, empty);

                    if (empty || orderId == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(orderId);

                        if (returnedOrderIDs.contains(orderId)) {
                            setStyle("-fx-background-color: yellow;");
                        } else {
                            setStyle("");
                        }
                    }
                }
            };
        });

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

        orderCol.getColumns().setAll(orderIdCol, orderDateCol,
                orderShipDateCol, orderShipModeCol);

        StackPane orderText = createHeaderContainer("Order");
        orderText.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                toggleColumnExpansion(orderCol);
            }
        });
        orderCol.setGraphic(orderText);

        return orderCol;
    }

    private TableColumn<Row, Customer> buildCustomerColumn() {
        TableColumn<Row, Customer> customerCol = new TableColumn<>();

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

        customerCol.getColumns().setAll(customerIdCol, customerNameCol,
                segmentCol, countryCol, cityCol, stateCol,
                postalCodeCol, regionCol);

        StackPane customerText = createHeaderContainer("Customer");
        customerText.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                toggleColumnExpansion(customerCol);
            }
        });
        customerCol.setGraphic(customerText);
        return customerCol;
    }

    private TableColumn<Row, Product> buildProductColumn() {
        TableColumn<Row, Product> productCol = new TableColumn<>();

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

        StackPane productText = createHeaderContainer("Product");
        productText.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // System.out.println("Product header clicked!");
                toggleColumnExpansion(productCol);
            }
        });
        productCol.setGraphic(productText);
        return productCol;
    }

    private TableColumn<Row, FinancialData> buildFinancialDataColumn() {
        TableColumn<Row, FinancialData> financialDataCol = new TableColumn<>();

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

        profitCol.setCellFactory(column -> {
            return new TableCell<Row, Double>() {
                @Override
                protected void updateItem(final Double profit, final boolean empty) {
                    super.updateItem(profit, empty);
                    if (empty || profit == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(profit.toString());
                        // make background yellow if order is in returned list
                        if (profit > 0) {
                            setStyle("-fx-background-color: lightgreen;");
                        } else if (profit == 0) {
                            setStyle("-fx-background-color: lightyellow;");
                        } else {
                            setStyle("-fx-background-color: lightcoral;");
                        }
                    }
                }
            };
        });

        profitCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(
                cellData.getValue()
                        .getData()
                        .getProfit())
                .asObject());

        financialDataCol.getColumns().setAll(salesCol, quantityCol, discountCol, profitCol);

        StackPane salesText = createHeaderContainer("Financial Data");
        salesText.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                toggleColumnExpansion(financialDataCol);
            }
        });
        financialDataCol.setGraphic(salesText);
        return financialDataCol;
    }

    /**
     * creates clickable stackpane, used on main columns as a graphic.
     *
     * @param text text inside StackPane
     * @return Stackpane with text.
     */
    private StackPane createHeaderContainer(final String text) {
        Text headerText = new Text(text);
        StackPane headerContainer = new StackPane(headerText);
        headerContainer.setStyle("-fx-background-color: lightgray;");
        return headerContainer;
    }

    /**
     * builds the menubar and returns it.
     * @param refTable table which Menu is build based on
     * (mainly needed for expand and collapse features)
     *
     * @return menubar
     */
    private MenuBar buildMenuBar(final TableView<Row> refTable) {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem preferencesMenuItem = new MenuItem("Preferences");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(event -> {
            Platform.exit();
        });

        fileMenu.getItems().addAll(preferencesMenuItem, exitMenuItem);

        // View menu
        Menu viewMenu = new Menu("View");
        Menu expandMenuItem = new Menu("Expand");
        Menu collapseMenuItem = new Menu("Collapse");

        Menu analysisMenu = new Menu("Analysis");
        MenuItem checkAnalysis = new MenuItem("Check analysis");
        checkAnalysis.setOnAction(event -> {
            AnalysisWindow window = new AnalysisWindow(beans);
            window.showWindow();
        });
        analysisMenu.getItems().addAll(checkAnalysis);

        refTable.getColumns().stream().skip(1).forEach(column -> {
            MenuItem expandItem = new MenuItem(getColumnTitle(column));
            expandItem.setOnAction(event -> {
                System.out.println(expandItem.getText() + " was clicked(expand)");
                expandColumn(column);
            });
            MenuItem collapseItem = new MenuItem(getColumnTitle(column));
            collapseItem.setOnAction(event -> {
                System.out.println(collapseItem.getText() + " was clicked(collapse)");
                collapseColumn(column);
            });
            expandMenuItem.getItems().add(expandItem);
            collapseMenuItem.getItems().add(collapseItem);
        });

        MenuItem expandAllMenuItem = new MenuItem("Expand all");
        expandAllMenuItem.setOnAction(event -> {
            refTable.getColumns().stream().skip(1).forEach(ColumnUtils::expandColumn);
        });
        MenuItem collapseAllMenuItem = new MenuItem("Collapse all");
        collapseAllMenuItem.setOnAction(event -> {
            refTable.getColumns().stream().skip(1).forEach(ColumnUtils::collapseColumn);
        });
        viewMenu.getItems().addAll(expandMenuItem, collapseMenuItem, expandAllMenuItem, collapseAllMenuItem);

        // Add menus to the menu bar
        menuBar.getMenus().addAll(fileMenu, viewMenu, analysisMenu);
        return menuBar;
    }

    private VBox buildSearchBar() {
        final int searchBoxSpacing = 10;
        final int searchBoxPrefWidth = 200;
        final int searchContainerSpacing = 5;
        FilteredList<Row> filteredData = new FilteredList<>(beans, p -> true); // Initially display all data

        VBox searchBarContainer = new VBox();
        searchBarContainer.setSpacing(searchContainerSpacing);

        Label searchLabel = new Label("Search Customer:");

        TextField searchField = new TextField();
        searchField.setPrefWidth(searchBoxPrefWidth);
        searchField.setPromptText("Search...");

        HBox searchBox = new HBox(searchBoxSpacing);
        searchBox.getChildren().addAll(searchLabel, searchField);
        searchBox.setAlignment(Pos.CENTER);

        searchBarContainer.getChildren().add(searchBox);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(row -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all rows if filter text is empty
                }

                return row.getCustomer().getName().toLowerCase().contains(newValue.toLowerCase());
            });
        });

        SortedList<Row> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        Label placeholderLabel = new Label("No results matching your query");
        placeholderLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        // Use a StackPane to overlay the table with the placeholder label
        StackPane placeholderPane = new StackPane(placeholderLabel);
        placeholderPane.setStyle("-fx-background-color: white;");

        // Bind visibility of placeholderPane to the emptiness of filteredList
        placeholderPane.visibleProperty().bind(
                Bindings.isEmpty(filteredData));

        table.setPlaceholder(placeholderPane);
        table.setItems(sortedData);

        return searchBarContainer;
    }

    private Stage buildOrdersMadeByCustomerWindow(final Customer customer) throws Exception {
        final int topBottomPadding = 10;
        final int centralVboxSpacing = 5;
        Stage primaryStage = new Stage();

        TableView<Row> table = buildMainTable();
        table.getColumns().remove(2);
        FilteredList<Row> tableData = new FilteredList<Row>(beans,
                row -> row.getCustomer().getId().equals(customer.getId()));
        table.setItems(FXCollections.observableList(tableData));

        final VBox centralVbox = new VBox();
        centralVbox.setSpacing(centralVboxSpacing);
        centralVbox.setPadding(new Insets(topBottomPadding, 0, 0, topBottomPadding));
        centralVbox.getChildren().addAll(new Label("Customer info:"),
                buildCustomerTable(customer),
                new Label("All orders made by this customer:"),
                table);

        BorderPane root = new BorderPane();
        root.setCenter(centralVbox);
        root.setTop(buildMenuBar(table));

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setTitle("Summary of orders");
        primaryStage.setScene(new Scene(root));
        return primaryStage;
    }

    /**
     * builds customer info table.
     * @param customer customer whose info is shown
     * @return table with only 2 rows (column titles and 1 row info) for window that shows Customer Window
     */
    @SuppressWarnings("unchecked")
    public static TableView<Customer> buildCustomerTable(final Customer customer) {
        final int fixedCellSize = 25;
        var tableData = FXCollections.observableList(List.of(customer));
        TableView<Customer> table = new TableView<Customer>(tableData);

        TableColumn<Customer, String> customerIdCol = new TableColumn<>("Customer ID");
        customerIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            customer.getId()));

        TableColumn<Customer, String> customerNameCol = new TableColumn<>("Customer Name");
        customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            customer.getName()));

        TableColumn<Customer, String> segmentCol = new TableColumn<>("Segment");
        segmentCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            customer.getSegment()));

        TableColumn<Customer, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            customer.getCountry()));

        TableColumn<Customer, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            customer.getCity()));

        TableColumn<Customer, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            customer.getState()));

        TableColumn<Customer, String> postalCodeCol = new TableColumn<>("Postal Code");
        postalCodeCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            Integer.toString(customer.getPostalCode())));

        TableColumn<Customer, String> regionCol = new TableColumn<>("Region");
        regionCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            customer.getRegion()));

        table.getColumns().setAll(customerIdCol, customerNameCol,
                segmentCol, countryCol, cityCol, stateCol,
                postalCodeCol, regionCol);
        table.setFixedCellSize(fixedCellSize);
        table.setPrefHeight(fixedCellSize * 2);

        return table;
    }

}
