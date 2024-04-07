package org.example.ui;

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
    private TableView<Row> table = buildMainTable();

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        final VBox mainVbox = new VBox();
        mainVbox.setSpacing(5);
        mainVbox.setPadding(new Insets(10, 0, 0, 10));
        mainVbox.getChildren().addAll(table);

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

        orderCol.getColumns().setAll(orderIdCol, orderDateCol,
                orderShipDateCol, orderShipModeCol);

        StackPane orderText = createHeaderContainer("Order");
        orderText.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println("Order header clicked!");
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
            table.getColumns().stream().skip(1).forEach(UI::expandColumn);
        });
        MenuItem collapseAllMenuItem = new MenuItem("Collapse all");
        collapseAllMenuItem.setOnAction(event -> {
            table.getColumns().stream().skip(1).forEach(UI::collapseColumn);
        });
        viewMenu.getItems().addAll(expandMenuItem, collapseMenuItem, expandAllMenuItem, collapseAllMenuItem);

        // Add menus to the menu bar
        menuBar.getMenus().addAll(fileMenu, viewMenu);
        return menuBar;
    }

    public static String getColumnTitle(final TableColumn<Row, ?> column) {
        // column Row ID has no graphic, hence this if check is needed
        // to avoid NullPointerException
        StackPane content = (StackPane) column.getGraphic();
        Text columnTitle = (Text) content.getChildren().get(0);
        return columnTitle.getText();
    }

    private static void expandColumn(final TableColumn<Row, ?> column) {
        if (column.getColumns().get(1).isVisible()) {
            return;
        }
        column.getColumns().stream().forEach(subColumn -> {
            subColumn.setVisible(true);
            // System.out.println(getColumnTitle(subColumn));
        });
    }

    private static void collapseColumn(final TableColumn<Row, ?> column) {
        if (!column.getColumns().get(1).isVisible()) {
            return;
        }
        column.getColumns().stream().skip(1).forEach(subColumn -> {
            subColumn.setVisible(false);
            // System.out.println(getColumnTitle(subColumn));
        });
    }

    private static void toggleColumnExpansion(final TableColumn<Row, ?> column) {
        // check if second sub column is visible, assuming only
        // only first sub-column will be shown when collapsed
        if (column.getColumns().get(1).isVisible()) {
            collapseColumn(column);
        } else {
            expandColumn(column);
        }
    }

}
