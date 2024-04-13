package org.example.ui.util;

import org.example.entities.Row;

import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ColumnUtils {
    private ColumnUtils() {

    }

    /**
     * returns the title of the column passed as an argument.
     *
     * @param column column to get title of
     * @return title of the column
     */
    public static String getColumnTitle(final TableColumn<Row, ?> column) {
        // column Row ID has no graphic, hence this if check is needed
        // to avoid NullPointerException
        StackPane content = (StackPane) column.getGraphic();
        Text columnTitle = (Text) content.getChildren().get(0);
        return columnTitle.getText();
    }

    /**
     * expands the given column.
     * expands the column if the column was collapsed, otherwise does nothing
     *
     * @param column column to expand
     */
    public static void expandColumn(final TableColumn<Row, ?> column) {
        if (column.getColumns().get(1).isVisible()) {
            return;
        }
        column.getColumns().stream().forEach(subColumn -> {
            subColumn.setVisible(true);
            // System.out.println(getColumnTitle(subColumn));
        });
    }

    /**
     * collapses the column.
     * only leaves first sub column visible and makes all others invisible
     * 
     * @param column
     */
    public static void collapseColumn(final TableColumn<Row, ?> column) {
        if (!column.getColumns().get(1).isVisible()) {
            return;
        }
        column.getColumns().stream().skip(1).forEach(subColumn -> {
            subColumn.setVisible(false);
            // System.out.println(getColumnTitle(subColumn));
        });
    }

    /**
     * expands the column if collapsed and collapses if expanded.
     * 
     * @param column column to toggle
     */
    public static void toggleColumnExpansion(final TableColumn<Row, ?> column) {
        // check if second sub column is visible, assuming only
        // only first sub-column will be shown when collapsed
        if (column.getColumns().get(1).isVisible()) {
            collapseColumn(column);
        } else {
            expandColumn(column);
        }
    }
}
