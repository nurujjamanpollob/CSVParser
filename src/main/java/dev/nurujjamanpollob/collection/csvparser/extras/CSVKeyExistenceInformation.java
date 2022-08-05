package dev.nurujjamanpollob.collection.csvparser.extras;

import dev.nurujjamanpollob.collection.csvparser.datastore.CSVToIterableObject;

/**
 * @author nurujjamanpollob
 * @version 1.0.0
 * @since 1.0.0
 * @param keyExists a boolean value that indicates whether the key exists in the {@link CSVToIterableObject}
 * @param key that checked for existence
 * @param keyIndex the index of the key in the {@link CSVToIterableObject}, if it's not found, the value will be -1
 * @apiNote Class that follows data-oriented design pattern to store information about a key is exists in the {@link CSVToIterableObject}
 */
public final record CSVKeyExistenceInformation(boolean keyExists, String key, int keyIndex) {}
