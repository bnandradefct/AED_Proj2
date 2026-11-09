package dataStructures;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
 abstract class HashTable<K,V> implements Map<K,V>{

     // Default size of the hash table.
     static final int DEFAULT_CAPACITY = 50;
     // Resize Factor
     static final int FACTOR = 2;

     // Number of entries in the hash table.
     transient int currentSize;

     // Number of entries to reach MAX_LOAD_FACTOR.
     transient int maxSize;

     public HashTable() {
         currentSize=0;
     }

     // Protected Static Methods

     // Returns a prime number that is not less than the
     // specified number; or zero if all such primes are greater
     // than Integer.MAX VALUE.
     protected static int nextPrime( int number ){
         //TODO: Left as exercise
        
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

     // Returns true iff the hash table needs to do rehashing.
     protected boolean isFull( ){
         return currentSize == maxSize;
     }
     /**
      * Returns true iff the map contains no entries.
      *
      * @return true if the map is empty
      */
     public boolean isEmpty() {
         return currentSize==0;
     }

     /**
      * Returns the number of entries in the map.
      *
      * @return number of elements in the map
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
     public abstract V get(K key) ;

     /**
      * If there is an entry in the map whose key is the specified key,
      * replaces its value by the specified value and returns the old value;
      * otherwise, inserts the entry (key, value) and returns null.
      *
      * @param key   with which the specified value is to be associated
      * @param value to be associated with the specified key
      * @return previous value associated with the key,
      * or null if the map does not have an entry with that key
      */
     public abstract V put(K key, V value);

     /**
      * If there is an entry in the map whose key is the specified key,
      * removes it from the map and returns its value;
      * otherwise, returns null.
      *
      * @param key whose entry is to be removed from the map
      * @return previous value associated with the key,
      * or null if the map does not an entry with that key
      */
     public abstract V remove(K key);

     /**
      * Returns an iterator of the entries in the map.
      *
      * @return iterator of the entries in the map
      */
     public abstract Iterator<Entry<K, V>> iterator();

 // MANUAL SERIALIZATION
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // write the normal attributes
        writeData(oos);
        oos.writeInt(maxSize);
        oos.writeInt(currentSize);       // write the size
        Iterator<Entry<K, V>> it = iterator();
        while (it.hasNext())
            oos.writeObject(it.next()); // write each element
    }

    // MANUAL DESERIALIZATION
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();      // read the normal attributes
        readData(ois);
        maxSize=ois.readInt();
        int size = ois.readInt();  // read the size
        for (int i = 0; i < size; i++) { // add each element on the list
            @SuppressWarnings("unchecked")
            Entry<K, V> element = (Entry<K, V>) ois.readObject();
            put(element.key(),element.value());
        }
    }
    void writeData(ObjectOutputStream out)
            throws IOException{
    }

    void readData(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
    }

     
 }
