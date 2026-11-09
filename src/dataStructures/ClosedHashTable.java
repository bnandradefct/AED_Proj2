package dataStructures;
/**
 * Closed Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class ClosedHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.5f;
    static final float MAX_LOAD_FACTOR =0.8f;
    static final int NOT_FOUND=-1;

    // removed cell
    static final Entry<?,?> REMOVED_CELL = new Entry<>(null,null);

    // The array of entries.
    private transient Entry<K,V>[] table;
    

    /**
     * Constructors
     */

    public ClosedHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    public ClosedHashTable( int capacity ){
        super();
        table=createTable(capacity);
    }

    @SuppressWarnings("unchecked")
    private Entry<K,V>[] createTable(int capacity) {
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
        // Compiler gives a warning.
        Entry<K,V>[] aux = (Entry<K,V>[]) new Entry<?,?>[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            aux[i] = null;
        return aux;
    }

    //Methods for handling collisions. Only one of them should be used!
    // Returns the hash value of the specified key.
    int hash( K key, int i ){
        return Math.abs( key.hashCode() + i) % table.length;
    }
    /**
     * Linear Proving
     * @param key to search
     * @return the index of the table, where is the entry with the specified key, or null
     */
    int searchLinearProving(K key) {
        //TODO: Left as an exercise.
        return NOT_FOUND; // all cells were covered, the element was not found
    }

    
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        //TODO: Left as an exercise.
        return null;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with the key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        if (isFull())
            rehash();
        //TODO: Left as an exercise.
        return null;
    }

    
    private void rehash() {
        //TODO: Left as an exercise.
        
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with the key,
     * or null if the dictionary does not an entry with that key
     */
    @Override
    public V remove(K key) {
        //TODO: Left as an exercise.
        return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new FilterIterator<>(
                new ArrayIterator<>(table,table.length),elem-> elem!=null && elem!= REMOVED_CELL);
    }

    void writeData(ObjectOutputStream oos)
            throws IOException {
        //TODO: Left as an exercise.
    }

    void readData(ObjectInputStream ios)
            throws IOException, ClassNotFoundException {
        //TODO: Left as an exercise.
    }
}
