package dataStructures;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

/**
 * Abstract Hash Table implementation.
 *
 * @author AED Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 *
 * Space complexity: O(n), where n is the capacity of the hash table.
 */
abstract class HashTable<K,V> implements Map<K,V>{
    @Serial 
    private static final long serialVersionUID = 1L;

     static final int DEFAULT_CAPACITY = 50;
     static final int FACTOR = 2;

     transient int currentSize;

     transient int maxSize;

     /**
      * Constructs an empty HashTable.
      *
      * Time complexity:
      * - Best case: O(1)
      * - Worst case: O(1)
      */
     public HashTable() {
         currentSize=0;
     }

     /**
      * Returns a prime number that is not less than the specified number,
      * or zero if all available primes are smaller than the specified number.
      *
      * @param number minimum value for the prime
      * @return a prime greater than or equal to number, or 0
      *
      * Time complexity:
      * - Best case: O(1)
      * - Worst case: O(1) (iterates over a fixed-size table of primes)
      */
     protected static int nextPrime( int number ){
         for (int prime : PRIMES) {
             if (prime >= number) {
                 return prime;
             }
         }
         return 0;
     }

     protected static final int[]
             PRIMES={11, 17, 19, 31, 47, 73,
             113, 181, 277, 421, 643, 967,
             1451, 2179, 3299, 4951, 7433,
             11173, 16763, 25163, 37747, 56671, 85009,
             127529, 191299, 287003, 430517, 645787, 968689,
             1453043, 2179571, 3269377, 4904077, 7356119,
             11034223, 16551361, 24827059, 37240597, 55860923, 83791441,
             125687173, 188530777, 282796177, 424194271, 636291413, 954437161,
             1431655751, 2147483647};

     /**
      * Returns true iff the hash table needs to do rehashing.
      *
      * @return true if currentSize == maxSize, false otherwise
      *
      * Time complexity:
      * - Best case: O(1)
      * - Worst case: O(1)
      */
     protected boolean isFull( ){
         return currentSize == maxSize;
     }

     /**
      * Returns true iff the map contains no entries.
      *
      * @return true if the map is empty
      *
      * Time complexity:
      * - Best case: O(1)
      * - Worst case: O(1)
      */
     public boolean isEmpty() {
         return currentSize==0;
     }

     /**
      * Returns the number of entries in the map.
      *
      * @return number of elements in the map
      *
      * Time complexity:
      * - Best case: O(1)
      * - Worst case: O(1)
      */
     public int size() {
         return currentSize;
     }

     /**
      * If there is an entry in the map whose key is the specified key,
      * returns its value; otherwise, returns null.
      *
      * @param key whose associated value is to be returned
      * @return value of entry in the map whose key is the specified key,
      * or null if the map does not have an entry with that key
      */
     public abstract V get(K key);

     /**
      * If there is an entry in the map whose key is the specified key,
      * replaces its value by the specified value and returns the old value;
      * otherwise, inserts the entry (key, value) and returns null.
      *
      * @param key with which the specified value is to be associated
      * @param value to be associated with the specified key
      * @return previous value associated with key,
      * or null if the map does not have an entry with that key
      */
     public abstract V put(K key, V value);

     /**
      * If there is an entry in the map whose key is the specified key,
      * removes it from the map and returns its value;
      * otherwise, returns null.
      *
      * @param key whose entry is to be removed from the map
      * @return previous value associated with key,
      * or null if the map does not have an entry with that key
      */
     public abstract V remove(K key);

     /**
      * Returns an iterator of the entries in the map.
      *
      * @return iterator of the entries in the map
      */
     public abstract Iterator<Entry<K, V>> iterator();

    /**
     * Serializes the hash table entries iteratively.
     *
     * @param oos the ObjectOutputStream to write to
     * @throws IOException if an I/O error occurs
     *
     * Time complexity:
     * - Best case: O(n) when the table is empty, where n is the table capacity
     * - Worst case: O(n + m), where n is the number of entries and m is the table capacity
     */
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        writeData(oos);
        oos.writeInt(maxSize);
        oos.writeInt(currentSize);
        Iterator<Entry<K, V>> it = iterator();
        while (it.hasNext())
            oos.writeObject(it.next());
    }

    /**
     * Deserializes and reconstructs the hash table iteratively.
     *
     * @param ois the ObjectInputStream to read from
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     *
     * Time complexity:
     * - Best case: O(1) when the serialized table is empty
     * - Worst case: O(n^2) in case of hash collisions (O(n) expected), where n is the number of entries
     */
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        readData(ois);
        maxSize=ois.readInt();
        int size = ois.readInt();
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            Entry<K, V> element = (Entry<K, V>) ois.readObject();
            put(element.key(),element.value());
        }
    }

    /**
     * Hook method for subclasses to write additional fields during serialization.
     *
     * @param out the ObjectOutputStream
     * @throws IOException if an I/O error occurs
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void writeData(ObjectOutputStream out) throws IOException {
    }

    /**
     * Hook method for subclasses to read or initialize additional fields during deserialization.
     *
     * @param in the ObjectInputStream
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if a class cannot be found
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void readData(ObjectInputStream in) throws IOException, ClassNotFoundException {
    }
}
