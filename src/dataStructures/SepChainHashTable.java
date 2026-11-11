package dataStructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

/**
 * SepChain Hash Table
 * @author AED Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class SepChainHashTable<K,V> extends HashTable<K,V> {
    @Serial 
    private static final long serialVersionUID = 1L;

    //Load factors
    static final float IDEAL_LOAD_FACTOR = 0.75f;
    static final float MAX_LOAD_FACTOR = 0.9f;
    private static final int NUMCOLLISION = 3;

    // The array of set.
    private transient Set<Entry<K,V>>[] table;

    public SepChainHashTable( ){
        this(DEFAULT_CAPACITY);
    }
    
    public SepChainHashTable( int capacity ){
        super();
        table = createTable(capacity);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Set<Entry<K,V>>[] createTable(int capacity){
        Set<Entry<K,V>>[] aux;
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
        aux = (Set<Entry<K,V>>[]) new Set[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            aux[i] = new SetLinkedList<>();
        return aux;
    }

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

    // Returns the hash value of the specified key.
    int hash( K key ){
        return Math.abs( key.hashCode() ) % table.length;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     */
    @Override
    public V get(K key) {
        if (key == null) return null;
        int idx = hash(key);
        Entry<K,V> found = table[idx].get(new Entry<>(key, null));
        return found != null ? found.value() : null;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
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
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
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
     * Returns an iterator of the entries in the dictionary.
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new SepChainHTIterator<K, V>(table);
    }

    void writeData(ObjectOutputStream oos) throws IOException {
        // A serialização das entradas já é feita na superclasse HashTable
    }

    void readData(ObjectInputStream ios) throws IOException, ClassNotFoundException {
        // A tabela é reconstruída automaticamente através dos puts na superclasse HashTable
        table = createTable(DEFAULT_CAPACITY);
        currentSize = 0;
    }
}
