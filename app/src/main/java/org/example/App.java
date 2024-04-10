/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.example;

import java.util.List;
import java.util.stream.Collectors;

import java.io.FileReader;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;

import org.example.entities.Row;
import org.example.ui.UI;

public final class App {

    /**
     * Private constructor.
     */
    private App() {

    }

    /**
     * main class of the application.
     * 
     * @param args args.
     */
    public static void main(final String[] args) throws Exception {
        List<Row> beans = null;
        List<String> returnIDs = null;
        try {
            beans = new CsvToBeanBuilder<Row>(new FileReader("temp.csv"))
                    .withSeparator(';')
                    .withType(Row.class)
                    .build()
                    .parse();

            CSVReader reader = new CSVReaderBuilder(new FileReader("SuperStoreReturns.csv")).build();
            returnIDs = reader.readAll()
                    .stream()
                    .skip(1) //discard column titles and remove "Yes;" at the start
                    .map(array -> array[0].substring(4))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (beans == null) {
            throw new Exception("main file data from csv is null!!!");
        }
        if (returnIDs == null) {
            throw new Exception("return file data from csv is null!!!");
        }

        UI.passDataAndLaunch(beans, returnIDs);
    }
}
