package dev.nurujjamanpollob.collection.csvparser.iterables;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import dev.nurujjamanpollob.collection.csvparser.exception.CSVIterableObjectError;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author nurujjamanpollob
 * @version 1.0.0
 * @since 1.0.0
 * @apiNote Class that represents a CSV file as an iterable object, divided into rows.
 * The first row represents the key-set of the CSV file.
 */
@SuppressWarnings({"unused"})
public final class CSVToIterableObject {

    private final List<String[]> keyValueRows = new ArrayList<>();
    private String[] keySet;

    /**
     * Constructor that initializes the new instance of {@link CSVToIterableObject} from a {@link File} object.
     */
    public CSVToIterableObject(File file) throws CSVIterableObjectError {

        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            List<String[]> r = reader.readAll();

            for (int i = 0; i < r.size(); i++) {
                putRow(i, r.get(i));
            }
        } catch (IOException | CsvException e) {
            throw new CSVIterableObjectError(e.getMessage());
        }
    }

    /**
     * Constructor that initializes the new instance of {@link CSVToIterableObject} from a {@link FileReader} object.
     */
    public CSVToIterableObject(FileReader fileReader) throws CSVIterableObjectError {

        try (CSVReader reader = new CSVReader(fileReader)) {
            List<String[]> r = reader.readAll();
            for (int i = 0; i < r.size(); i++) {
                putRow(i, r.get(i));
            }
        } catch (IOException | CsvException e) {
            throw new CSVIterableObjectError(e.getMessage());
        }
    }

    /**
     * Constructor that initializes the new instance of {@link CSVToIterableObject} from a {@link CSVReader} object.
     */
    public CSVToIterableObject(CSVReader reader) throws CSVIterableObjectError {

        try {
            List<String[]> r = reader.readAll();
            for (int i = 0; i < r.size(); i++) {
                putRow(i, r.get(i));
            }
        } catch (IOException | CsvException e) {
            throw new CSVIterableObjectError(e.getMessage());
        }
    }

    /**
     * Constructor that initializes the new instance of {@link CSVToIterableObject} from a {@link String} object. where String is a path to a CSV file.
     */
    public CSVToIterableObject(String path) throws CSVIterableObjectError {

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            List<String[]> r = reader.readAll();
            for (int i = 0; i < r.size(); i++) {
                putRow(i, r.get(i));
            }
        } catch (IOException | CsvException e) {
            throw new CSVIterableObjectError(e.getMessage());
        }
    }

    /**
     * Constructor that initializes the new instance of {@link CSVToIterableObject} from a {@link String} object. where String is the content of a CSV file.
     * @param content CSV file content as a String.
     * @param isPath a boolean value that indicates whether the content is a path to a CSV file or the content of a CSV file.
     */
    public CSVToIterableObject(String content, boolean isPath) throws CSVIterableObjectError {

        if (isPath) {
            try (CSVReader reader = new CSVReader(new FileReader(content))) {
                List<String[]> r = reader.readAll();
                for (int i = 0; i < r.size(); i++) {
                    putRow(i, r.get(i));
                }
            } catch (IOException | CsvException e) {
                throw new CSVIterableObjectError(e.getMessage());
            }
        } else {
            try (CSVReader reader = new CSVReader(new StringReader(content))) {
                List<String[]> r = reader.readAll();
                for (int i = 0; i < r.size(); i++) {
                    putRow(i, r.get(i));
                }
            } catch (IOException | CsvException e) {
                throw new CSVIterableObjectError(e.getMessage());
            }
        }
    }

    /**
     * Empty constructor that initializes the new instance of {@link CSVToIterableObject}.
     */
    public CSVToIterableObject() {}

    /**
     * Method to put a row of the CSV file into the {@link CSVToIterableObject}
     */
    private void putRow(int iterationIndex, String[] row) throws CSVIterableObjectError {

        if(iterationIndex == 0) {
            keySet = row;
        } else {
            if (row.length != keySet.length) {
                throw new CSVIterableObjectError("Row length does not match key-set length");
            }
            keyValueRows.add(row);
        }
    }

    /**
     * Method to get the key-set of the CSV file
     */
    public String[] getKeySet() {
        return keySet;
    }

    /**
     * method to get the all values row from the CSV file
     */
    public List<String[]> getAllValues() {
        return keyValueRows;
    }

    /**
     * @apiNote Method return the whole CSV file as a Key-Value pair, including key-set
     *
     * if you need to individually access the key-set and values, use {@link #getKeySet()} and {@link #getAllValues()}
     *
     * @return a list of key-value pairs, first row is the key-set, other rows are the values
     */
    public List<String[]> getAll() {
        List<String[]> all = new ArrayList<>();
        all.add(keySet);
        all.addAll(keyValueRows);
        return all;
    }

    /**
     * @apiNote Method to get a key from a given Index
     * @param index the index of the key
     * @throws CSVIterableObjectError if the index is out of bound
     */
    public String getKey(int index) throws CSVIterableObjectError {
        if (index >= keySet.length) {
            throw new CSVIterableObjectError("The index is out of bound, the index is " + index + " and the key-set length is " + keySet.length);
        }
        return keySet[index];
    }

    /**
     * @apiNote Method to get value by a given key and row index
     * @param key the key of name to look for
     * @param rowIndex the index of the row
     * @return the value of the key in the row
     */
    public String getValue(String key, int rowIndex) throws CSVIterableObjectError {

        // check if the passed row index is out of bound
        if (rowIndex >= keyValueRows.size()) {
            throw new CSVIterableObjectError("The index is out of bound, the index is " + rowIndex + " and the row length is " + keyValueRows.size());
        }
        return keyValueRows.get(rowIndex)[getKeyIndex(key)];
    }

    /**
     * @apiNote Method to get the index of a given key
     * @param key the key of name to look for
     * @return the index of the key in the key-set
     * @throws CSVIterableObjectError if the key is not found in the key-set
     */
    public int getKeyIndex(String key) throws CSVIterableObjectError {
        for (int i = 0; i < keySet.length; i++) {
            if (keySet[i].equals(key)) {
                return i;
            }
        }
        throw new CSVIterableObjectError("The key " + key + " is not found in the key-set");

    }

    /**
     * Method to get value by a given key and row index
     * @param keyIndex the key index
     * @param rowIndex the row index
     * @return the value of the key in the row
     * @throws CSVIterableObjectError if the key index is not found or the value index is out of bound
     */
    public String getValue(int keyIndex, int rowIndex) throws CSVIterableObjectError {
        // check if the passed row index is out of bound
        if (rowIndex >= keyValueRows.size() || keyIndex >= keySet.length) {
            throw new CSVIterableObjectError("The index is out of bound, the index is " + rowIndex + " and the row length is " + keyValueRows.size());
        }
        return keyValueRows.get(rowIndex)[keyIndex];
    }

    /**
     * Method to get all values by a given key
     * @param key the key of name to look for
     * @return a list of values of the key
     * @throws CSVIterableObjectError if the key is not found in the key-set
     */
    public List<String> getAllValuesByKey(String key) throws CSVIterableObjectError {
        // First get the index of the key
        int keyIndex = getKeyIndex(key);
        // Then get all values by the key index
        List<String> values = new ArrayList<>();
        for (String[] row : keyValueRows) {
            values.add(row[keyIndex]);
        }

        return values;
    }

    /**
     * Method to get all value associated with a key, by its index number
     * @param keyIndex the key index number
     * @throws CSVIterableObjectError if the keyIndex is out of bound
     */
    public List<String> getAllValuesByKey(int keyIndex) throws CSVIterableObjectError {

        // check for key index
        if(keyIndex >= keySet.length){

            throw new CSVIterableObjectError("The key index is out of bound. The index is " + keyIndex + " and the key-set length is " + keySet.length);
        }

        // Then get all values by the key index
        List<String> values = new ArrayList<>();
        for (String[] row : keyValueRows) {
            values.add(row[keyIndex]);
        }

        return values;
    }

    /**
     * Method to get ket-set length
     * @return the length of the key-set
     */
    public int getKeySetLength() {
        return keySet.length;
    }

    /**
     * Method to get column length
     * @return the length of the key-set, as the all rows are the same length
     */
    public int getColumnLength() {
        return keySet.length;
    }

    /**
     * method to get row length
     * @return the length of the values row
     */
    public int getRowLength() {
        return keyValueRows.size();
    }

    /**
     * Method to get all row length, including the key-set row
     * @return the length of the all rows
     */
    public int getAllRowLength() {
        return keyValueRows.size() + 1;
    }

    /**
     * Method to check If a specific key is present in the key-set
     * @param key the key of name to look for
     * @return true if the key is present in the key-set, false otherwise
     */
    public boolean isKeyPresent(String key) {
        for (String k : keySet) {
            if (k.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check If a specific set of keys is present in the key-set
     * @param keys the set of keys to look for
     * @return true if all keys are present in the key-set, false otherwise
     */
    public boolean isKeysPresent(String[] keys) {
        for (String k : keys) {
            if (!isKeyPresent(k)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public String toString() {
        return "CSVToIterableObject{" +
                "keyValueRows=" + keyValueRows +
                ", keySet=" + Arrays.toString(keySet) +
                '}';
    }


}
