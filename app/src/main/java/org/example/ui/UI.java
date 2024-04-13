package org.example.ui;

import java.util.List;

import org.example.entities.Customer;
import org.example.entities.FinancialData;
import org.example.entities.Order;
import org.example.entities.Product;
import org.example.entities.Row;
import org.example.ui.util.ColumnUtils;

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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static org.example.ui.util.ColumnUtils.*;

/**
 * class representing User Interface.
 */
public class UI extends Application {
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
        BorderPane root = new BorderPane();

        final VBox centralVbox = new VBox();
        centralVbox.setSpacing(5);
        centralVbox.setPadding(new Insets(10, 0, 0, 10));
        centralVbox.getChildren().addAll(buildSearchBar(), table);

        root.setCenter(centralVbox);
        root.setTop(buildMenuBar());
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
        table.setOnMouseClicked(mouseEvent -> {
            table.getSelectionModel().getSelectedCells().forEach(cell -> {
                if (cell != null && cell.getTableColumn().getText().equals("Customer Name")) {
                    System.out.println("correct column");
                    
                    Customer customer = table.getSelectionModel().getSelectedItem().getCustomer();
                    try {
                        buildOrdersMadeByCustomerWindow(customer).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });

        TableColumn<Row, Integer> rowIdCol = new TableColumn<>("Row ID");
        rowIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                cellData.getValue()
                        .getRowId())
                .asObject());
        // ----------------------- order column --------------------------------
        TableColumn<Row, Order> orderCol = new TableColumn<>();

        TableColumn<Row, String> orderIdCol = new TableColumn<>("Order ID");

        orderIdCol.setCellFactory(column -> {
            return new TableCell<Row, String>() {
                @Override
                protected void updateItem(String orderId, boolean empty) {
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

        // ==================order column end===================================
        // ------------------customer column start------------------------------

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

        // ================customer column end==================================
        // -------------------product column start------------------------------

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

        // ==========================product column end=========================
        // ------------------financial data column start------------------------

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
                protected void updateItem(Double profit, boolean empty) {
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

        // =================financial data column end===========================

        table.getColumns().addAll(rowIdCol, orderCol, customerCol,
                productCol, financialDataCol);

        return table;
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
     * 
     * @return menubar
     */
    private MenuBar buildMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem openMenuItem = new MenuItem("Preferences");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(event -> {
            Platform.exit();
        });

        fileMenu.getItems().addAll(openMenuItem, exitMenuItem);

        // View menu
        Menu viewMenu = new Menu("View");
        Menu expandMenuItem = new Menu("Expand");
        Menu collapseMenuItem = new Menu("Collapse");

        table.getColumns().stream().skip(1).forEach(column -> {
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
            table.getColumns().stream().skip(1).forEach(ColumnUtils::expandColumn);
        });
        MenuItem collapseAllMenuItem = new MenuItem("Collapse all");
        collapseAllMenuItem.setOnAction(event -> {
            table.getColumns().stream().skip(1).forEach(ColumnUtils::collapseColumn);
        });
        viewMenu.getItems().addAll(expandMenuItem, collapseMenuItem, expandAllMenuItem, collapseAllMenuItem);

        // Add menus to the menu bar
        menuBar.getMenus().addAll(fileMenu, viewMenu);
        return menuBar;
    }

    private VBox buildSearchBar() {
        FilteredList<Row> filteredData = new FilteredList<>(beans, p -> true); // Initially display all data

        VBox searchBarContainer = new VBox();
        searchBarContainer.setSpacing(5);

        Label searchLabel = new Label("Search Customer:");

        TextField searchField = new TextField();
        searchField.setPrefWidth(200);
        searchField.setPromptText("Search...");

        HBox searchBox = new HBox(10);
        searchBox.getChildren().addAll(searchLabel, searchField);
        searchBox.setAlignment(Pos.CENTER);

        // HBox.setHgrow(searchField, Priority.ALWAYS);

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

    private Stage buildOrdersMadeByCustomerWindow(Customer customer) throws Exception {
        Stage primaryStage = new Stage();

        TableView<Row> table = buildMainTable();
        table.getColumns().get(2).setVisible(false);
        FilteredList<Row> tableData = new FilteredList<Row>(beans, row -> row.getCustomer().getId().equals(customer.getId()));
        table.setItems(FXCollections.observableList(tableData));

        String summaryLabel = "Summary of all orders made by customer " + customer.getName();

        final VBox centralVbox = new VBox();
        centralVbox.setSpacing(5);
        centralVbox.setPadding(new Insets(10, 0, 0, 10));
        centralVbox.getChildren().addAll(new Label(summaryLabel), table);

        BorderPane root = new BorderPane();
        root.setCenter(centralVbox);
        root.setTop(buildMenuBar());

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setTitle("Summary of orders");
        primaryStage.setScene(new Scene(root));
        return primaryStage;
    }

}
