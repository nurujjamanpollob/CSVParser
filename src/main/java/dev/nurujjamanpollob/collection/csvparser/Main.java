package dev.nurujjamanpollob.collection.csvparser;

import dev.nurujjamanpollob.collection.csvparser.iterables.CSVToIterableObject;
import dev.nurujjamanpollob.collection.csvparser.exception.CSVIterableObjectError;

import java.io.File;

public class Main {
    public static void main(String[] args) throws CSVIterableObjectError {

        String csvFile = "samplefile/simple.csv";

         CSVToIterableObject csvToIterableObject = new CSVToIterableObject(new File(csvFile));

            // Print a specific value from a specific row, here we are printing requirement from the value second row
            System.out.println(csvToIterableObject.getValue(3, 1));

            // Print All names from the value set
            for (String name : csvToIterableObject.getAllValuesByKey("name")) {
                System.out.println(name);
            }
    }
}