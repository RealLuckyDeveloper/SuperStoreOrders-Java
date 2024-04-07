package org.example.ui;

import java.util.ArrayList;
import java.util.List;

import org.example.entities.Customer;
import org.example.entities.FinancialData;
import org.example.entities.Order;
import org.example.entities.Product;
import org.example.entities.Row;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
        BorderPane root = new BorderPane();
        // root.getChildren().add(text);

        final VBox mainVbox = new VBox();
        mainVbox.setSpacing(5);
        mainVbox.setPadding(new Insets(10, 0, 0, 10));
        mainVbox.getChildren().addAll(buildMainTable());

        root.setCenter(mainVbox);
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
     * launchs UI and sets data from csv file
     * 
     * @param data {@code List<Row>} data parsed from csv
     */
    public static void passDataAndLaunch(final List<Row> data) {
        beans = data;
        launch();
    }

    /**
     * builds main table and returns it.
     * 
     * @return Tableview main table
     */
    @SuppressWarnings("unchecked")
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
        TableColumn<Row, Order> orderCol = new TableColumn<>();

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

        final var orderColumns = List.of(orderIdCol, orderDateCol,
                orderShipDateCol, orderShipModeCol);

        orderCol.getColumns().setAll(orderColumns);

        StackPane orderText = createHeaderContainer("Order");
        orderText.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println("Order header clicked!");
                if (orderCol.getColumns().size() != 1) {
                    orderCol.getColumns().setAll(orderColumns.get(0));
                    // orderCol.getColumns().removeAll(new ArrayList<>());
                } else {
                    orderCol.getColumns().setAll(orderColumns);
                }
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

        final var customerColumns = List.of(customerIdCol, customerNameCol,
                segmentCol, countryCol, cityCol, stateCol,
                postalCodeCol, regionCol);

        customerCol.getColumns().setAll(customerColumns);

        StackPane customerText = createHeaderContainer("Customer");
        customerText.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // System.out.println("Customer header clicked!");
                if (customerCol.getColumns().size() != 1) {
                    customerCol.getColumns().setAll(customerColumns.get(0));
                } else {
                    customerCol.getColumns().setAll(customerColumns);
                }
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

        final var productColumns = List.of(productIdCol, categoryCol,
                subcategoryCol, productNameCol);

        productCol.getColumns().addAll(productColumns);

        StackPane productText = createHeaderContainer("Product");
        productText.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // System.out.println("Product header clicked!");
                if (productCol.getColumns().size() != 1) {
                    productCol.getColumns().setAll(productCol.getColumns().get(0));
                } else {
                    productCol.getColumns().setAll(productColumns);
                }
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
        profitCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(
                cellData.getValue()
                        .getData()
                        .getProfit())
                .asObject());

        final var salesList = List.of(salesCol, quantityCol, discountCol, profitCol);
        financialDataCol.getColumns().setAll(salesList);

        StackPane salesText = createHeaderContainer("Financial Data");
        salesText.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println("Order header clicked!");
                if (financialDataCol.getColumns().size() != 1) {
                    financialDataCol.getColumns().removeAll(quantityCol,
                            discountCol, profitCol);
                } else {
                    financialDataCol.getColumns().addAll(quantityCol,
                            discountCol, profitCol);
                }
            }
        });
        financialDataCol.setGraphic(salesText);

        // =================financial data column end===========================

        table.getColumns().addAll(rowIdCol, orderCol, customerCol,
                productCol, financialDataCol);
        table.setItems(tableData);
        return table;
    }

    /**
     * creates clickable stackpane, used on main columns as a graphic.
     * 
     * @param text text inside StackPane
     * @return Stackpane with text.
     */
    private StackPane createHeaderContainer(String text) {
        Text headerText = new Text(text);
        StackPane headerContainer = new StackPane(headerText);
        headerContainer.setStyle("-fx-background-color: lightgray;");
        return headerContainer;
    }

    public MenuBar buildMenuBar() {
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

        getColumnList().stream().forEach(text -> {
            MenuItem expandItem = new MenuItem(text);
            expandItem.setOnAction(event -> {
                System.out.println(expandItem.getText() + " was clicked(expand)");
            });
            MenuItem collapseItem = new MenuItem(text);
            collapseItem.setOnAction(event -> {
                System.out.println(collapseItem.getText() + " was clicked(collapse)");
            });
            expandMenuItem.getItems().add(expandItem);
            collapseMenuItem.getItems().add(collapseItem);
        });

        MenuItem expandAllMenuItem = new MenuItem("Expand all");
        MenuItem collapseAllMenuItem = new MenuItem("Collapse all");
        viewMenu.getItems().addAll(expandMenuItem, collapseMenuItem, expandAllMenuItem, collapseAllMenuItem);

        // Add menus to the menu bar
        menuBar.getMenus().addAll(fileMenu, viewMenu);
        return menuBar;
    }

    public List<String> getColumnList() {
        List<String> columnList = new ArrayList<String>();
        buildMainTable().getColumns().stream().forEach(column -> {
            // column Row ID has no graphic, hence this if check is needed
            // to avoid NullPointerException
            if (column.getGraphic() != null) {
                StackPane content = (StackPane) column.getGraphic();
                Text columnTitle = (Text) content.getChildren().get(0);
                columnList.add(columnTitle.getText());
            }
        });
        return columnList;
    }

}
