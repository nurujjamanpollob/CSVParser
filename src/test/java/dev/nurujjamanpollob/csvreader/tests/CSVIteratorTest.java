package dev.nurujjamanpollob.csvreader.tests;

import dev.nurujjamanpollob.collection.csvparser.iterables.CSVToIterableObject;
import dev.nurujjamanpollob.collection.csvparser.exception.CSVIterableObjectError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * Class to test {@link dev.nurujjamanpollob.collection.csvparser.iterables.CSVToIterableObject} class.
 */
public class CSVIteratorTest {


    // Simple CSV Text
    private final String csvText = "name, mail, phone\n" +
            "Nurujjaman Pollob, nur@mail.com, +880123456789\n" +
            "John Doe, john@mail.com , +880123456789\n";

    // Replace with CSV Absolute Path
    private final File simpleCSVDir = new File("/Volumes/Projects/CSVParser/samplefile/simple.csv");


    /**
     * Method to create a new instance of {@link CSVToIterableObject} from a {@link File} object.
     */
    @Test
    public void testCSVToIterableObjectFromFile() throws CSVIterableObjectError {

        CSVToIterableObject csvToIterableObject = new CSVToIterableObject(simpleCSVDir);

        // Assert that the key-set of the CSV file is as expected, the csv file has three elements in the key-set
        Assertions.assertEquals(4, csvToIterableObject.getKeySet().length);
    }

    /**
     * Method to create a new instance of {@link CSVToIterableObject} from a String object.
     */
    @Test
    public void testCSVToIterableObjectFromString() throws CSVIterableObjectError {

        CSVToIterableObject csvToIterableObject = new CSVToIterableObject(csvText, false);

        // Assert that the key-set of the CSV file is as expected, the csv file has three elements in the key-set
        Assertions.assertEquals(3, csvToIterableObject.getKeySet().length);
    }


    /**
     * Method to test the {@link CSVToIterableObject#getValue(int, int)} method.
     */
    @Test
    public void testGetValueByIndexNumberAtASpecificIndex() throws CSVIterableObjectError {

        CSVToIterableObject csvToIterableObject = new CSVToIterableObject(simpleCSVDir);
        // Assert that the value of the CSV file is as expected, the csv file has three elements in the key-set
        Assertions.assertEquals("Nurujjaman Pollob", csvToIterableObject.getValue(0, 0));
    }

    /**
     * Method to test the {@link CSVToIterableObject#getValue(int, int)} method.
     */
    @Test
    public void testGetValueByKeyNameAtASpecificIndex() throws CSVIterableObjectError {

        CSVToIterableObject csvToIterableObject = new CSVToIterableObject(csvText, false);
        // Assert that the value of the CSV file is as expected, the csv file has three elements in the key-set
        Assertions.assertEquals("Nurujjaman Pollob", csvToIterableObject.getValue("name", 0));
    }

    /**
     * Method to test the {@link CSVToIterableObject#getAllValuesByKey(String)} method.
     */
    @Test
    public void testGetAllValuesByKeyName() throws CSVIterableObjectError {

        CSVToIterableObject csvToIterableObject = new CSVToIterableObject(simpleCSVDir);
        // Assert that the value of the CSV file is as expected, the csv file has three elements in the key-set
        Assertions.assertEquals(2, csvToIterableObject.getAllValuesByKey("name").size());
    }

    /**
     * Method to test the {@link CSVToIterableObject#getAllValuesByKey(int)} method.
     */
    @Test
    public void testGetAllValuesByKeyIndex() throws CSVIterableObjectError {

        CSVToIterableObject csvToIterableObject = new CSVToIterableObject(simpleCSVDir);
        // Assert that the value of the CSV file is as expected, the csv file has three elements in the key-set
        Assertions.assertEquals(2, csvToIterableObject.getAllValuesByKey(0).size());
    }


    /**
     * Method to get a value from a specific row and column.
     */
    @Test
    public void testGetValueFromASpecificRowAndColumn() throws CSVIterableObjectError {

        CSVToIterableObject csvToIterableObject = new CSVToIterableObject(simpleCSVDir);
        // Assert that the value of the CSV file is as expected, the csv file has three elements in the key-set
        Assertions.assertEquals("Need a iOS App", csvToIterableObject.getValue("requirement", 1));
    }

    /**
     * Method to trying access a value row index, which is out of bound, and it should throw {@link CSVIterableObjectError} exception.
     */
    @Test
    public void testGetValueFromRowAndColumnOutOfBound() throws CSVIterableObjectError {

        CSVToIterableObject csvToIterableObject = new CSVToIterableObject(simpleCSVDir);
      CSVIterableObjectError csvIterableObjectError =  Assertions.assertThrows(CSVIterableObjectError.class, () -> {
            csvToIterableObject.getValue("requirement", 3);
        });

        // Assert  the exception message
        Assertions.assertTrue(csvIterableObjectError.getMessage().contains("The index is out of bound"));
    }

    /**
     * Method to try to access a value row index, where the key index is out of bound, and it should throw {@link CSVIterableObjectError} exception.
     */
    @Test
    public void testGetValueFromRowAndColumnOutOfBoundKey() throws CSVIterableObjectError {

        CSVToIterableObject csvToIterableObject = new CSVToIterableObject(simpleCSVDir);
        CSVIterableObjectError csvIterableObjectError =  Assertions.assertThrows(CSVIterableObjectError.class, () -> {
            csvToIterableObject.getValue(4, 0);
        });

        // Assert  the exception message
        Assertions.assertTrue(csvIterableObjectError.getMessage().contains("The index is out of bound"));
    }


    /**
     * Method to get a key index by its name.
     */
    @Test
    public void testGetKeyIndexByName() throws CSVIterableObjectError {

        CSVToIterableObject csvToIterableObject = new CSVToIterableObject(simpleCSVDir);
        // Assert that the value of the CSV file is as expected, the csv file has three elements in the key-set
        Assertions.assertEquals(0, csvToIterableObject.getKeyIndex("name"));
    }

    /**
     * Method to get a key by its index, which is out of bound, and it should throw {@link CSVIterableObjectError} exception.
     */
    @Test
    public void testGetKeyIndexByNameOutOfBound() throws CSVIterableObjectError {

        CSVToIterableObject csvToIterableObject = new CSVToIterableObject(simpleCSVDir);
        CSVIterableObjectError csvIterableObjectError =  Assertions.assertThrows(CSVIterableObjectError.class, () -> {
            csvToIterableObject.getKey(4);
        });

        // Assert  the exception message
        Assertions.assertTrue(csvIterableObjectError.getMessage().contains("The index is out of bound"));
    }

    /**
     * Method to test put a value row, this is big then the key - set size, and it should throw {@link CSVIterableObjectError} exception.
     */
    @Test
    public void testPutValueRowBiggerThenKeySetSize(){

        StringBuilder csv = new StringBuilder();
        csv.append(csvText);
        // Add a new row to the CSV text
        csv.append("Name" + "mail@mail" + "+1-123-456-7890" + "Need a iOS App" + "\n");
        try {
            CSVToIterableObject  csvToIterableObject = new CSVToIterableObject(csv.toString(), false);
            // If this call stack success, it must an error
            Assertions.fail();
        } catch (CSVIterableObjectError csvIterableObjectError) {
            // Assert  the exception message
            Assertions.assertTrue(csvIterableObjectError.getMessage().contains("Row length does not match key-set length"));
        }


    }


}