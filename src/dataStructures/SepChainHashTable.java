package dataStructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

/**
 * Separate Chaining Hash Table implementation.
 *
 * @author AED Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 *
 * Space complexity: O(n), where n is the capacity of the hash table.
 */
public class SepChainHashTable<K,V> extends HashTable<K,V> {
    @Serial 
    private static final long serialVersionUID = 1L;

    static final float IDEAL_LOAD_FACTOR = 0.75f;
    static final float MAX_LOAD_FACTOR = 0.9f;
    private static final int NUMCOLLISION = 3;

    private transient Set<Entry<K,V>>[] table;

    /**
     * Constructs an empty SepChainHashTable with the default capacity.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public SepChainHashTable( ){
        this(DEFAULT_CAPACITY);
    }
    
    /**
     * Constructs an empty SepChainHashTable with the specified initial capacity.
     *
     * @param capacity initial expected number of entries
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n), where n is the allocated table array size
     */
    public SepChainHashTable( int capacity ){
        super();
        table = createTable(capacity);
    }

    /**
     * Allocates and initializes an array of empty bucket sets for the given capacity.
     *
     * @param capacity target capacity before load factor adjustment
     * @return initialized array of Set buckets
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n), where n is the allocated table array size
     */
    @SuppressWarnings("unchecked")
    private Set<Entry<K,V>>[] createTable(int capacity){
        Set<Entry<K,V>>[] aux;
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
        aux = (Set<Entry<K,V>>[]) new Set<?> [arraySize];
        for ( int i = 0; i < arraySize; i++ )
            aux[i] = new SetLinkedList<>();
        return aux;
    }

    /**
     * Resizes the hash table to approximately double its current capacity and reinserts all entries.
     *
     * Time complexity:
     * - Best case: O(n) when entries are evenly distributed, where n is the number of entries in the table
     * - Worst case: O(n^2) in case of hash collisions, where n is the number of entries in the table
     */
    private void rehash() {
        Set<Entry<K,V>>[] oldTable = table;
        table = createTable(oldTable.length * 2);
        currentSize = 0;
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null && !oldTable[i].isEmpty()) {
                Iterator<Entry<K,V>> it = oldTable[i].iterator();
                while (it.hasNext()) {
                    Entry<K,V> e = it.next();
                    put(e.key(), e.value());
                }
            }
        }
    }

    /**
     * Computes a non-negative bucket index for the specified key.
     *
     * @param key the key to hash
     * @return bucket index in the range [0, table.length - 1]
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    int hash( K key ){
        return (key.hashCode() & 0x7FFFFFFF) % table.length;
    }

    /**
     * If there is an entry in the map whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the map whose key is the specified key,
     * or null if the map does not have an entry with that key
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) in case of hash collisions, where n is the number of entries in the table
     */
    @Override
    public V get(K key) {
        if (key == null) return null;
        int idx = hash(key);
        Entry<K,V> found = table[idx].get(new Entry<>(key, null));
        return found != null ? found.value() : null;
    }

    /**
     * If there is an entry in the map whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the map does not have an entry with that key
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) when rehashing or hash collisions occur, where n is the number of entries in the table
     */
    @Override
    public V put(K key, V value) {
        if (isFull())
            rehash();

        int idx = hash(key);
        Entry<K,V> newEntry = new Entry<>(key, value);
        Entry<K,V> oldEntry = table[idx].replace(newEntry);

        if (oldEntry != null) {
            return oldEntry.value();
        }

        table[idx].put(newEntry);
        currentSize++;
        return null;
    }

    /**
     * If there is an entry in the map whose key is the specified key,
     * removes it from the map and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the map does not have an entry with that key
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) in case of hash collisions, where n is the number of entries in the table
     */
    @Override
    public V remove(K key) {
        if (key == null) return null;
        int idx = hash(key);
        Entry<K,V> removed = table[idx].remove(new Entry<>(key, null));
        if (removed != null) {
            currentSize--;
            return removed.value();
        }
        return null;
    }

    /**
     * Returns an iterator of the entries in the map.
     *
     * @return iterator of the entries in the map
     *
     * Time complexity:
     * - Best case: O(1) when the first bucket is non-empty
     * - Worst case: O(n) when the table is empty or all entries are in the last bucket, where n is the table capacity
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new SepChainHTIterator<K, V>(table);
    }

    /**
     * Hook method for serialization (entries are serialized by the superclass).
     *
     * @param oos the ObjectOutputStream
     * @throws IOException if an I/O error occurs
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void writeData(ObjectOutputStream oos) throws IOException {
    }

    /**
     * Reinitializes the underlying bucket array before entries are restored by the superclass.
     *
     * @param ios the ObjectInputStream
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if a class cannot be found
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void readData(ObjectInputStream ios) throws IOException, ClassNotFoundException {
        table = createTable(DEFAULT_CAPACITY);
        currentSize = 0;
    }
}
