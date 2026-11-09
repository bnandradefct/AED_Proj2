package dataStructures;
import java.io.Serializable;

/**
 * map Abstract Data Type 
 * Includes description of general methods to be implemented by dictionaries.
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value 
 */

public interface Map<K,V> extends Serializable {

    record Entry<K,V>(K key,V value) implements Serializable {
        @Override
        public boolean equals(Object obj) {
            if (this==obj) return true;
            if (!(obj instanceof Entry))
                return false;
            //noinspection unchecked
            K key=((Entry<K, V>)obj).key();
            return key().equals(key);
        }
    }
    /**
     * Returns true iff the map contains no entries.
     * @return true if the map is empty
     */
    boolean isEmpty( );                                           

    /**
     * Returns the number of entries in the map.
     * @return number of elements in the map
     */
    int size( );                                                  

    /**
     * If there is an entry in the map whose key is the specified key,
     * returns its value; otherwise, returns null.
     * @param key whose associated value is to be returned
     * @return value of entry in the map whose key is the specified key, 
     * or null if the map does not have an entry with that key
     */
    V get( K key );

    /**
     * If there is an entry in the map whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * @param key with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key, 
     * or null if the map does not have an entry with that key
     */
    V put( K key, V value );

    /**
     * If there is an entry in the map whose key is the specified key,
     * removes it from the map and returns its value;
     * otherwise, returns null.
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key, 
     * or null if the map does not an entry with that key
     */
    V remove( K key );                                

    /**
     * Returns an iterator of the entries in the map.
     * @return  iterator of the entries in the map
     */
    Iterator<Entry<K,V>> iterator( );

    /**
      * Returns an iterator of the values in the map.
      *
      * @return iterator of the values in the map
      */

     default Iterator<V> values() {
         Iterator<Entry<K, V>> it = iterator();

         return new Iterator<>() {
             public boolean hasNext() {
                 return it.hasNext();
             }
             public V next() {
                 return it.next().value();
             }
             public void rewind() {
                 it.rewind();
             }
         };
     }

     /**
      * Returns an iterator of the keys in the map.
      *
      * @return iterator of the keys in the map
      */
     
     default Iterator<K> keys() {
         Iterator<Entry<K, V>> it = iterator();

         return new Iterator<>() {
             public boolean hasNext() {
                 return it.hasNext();
             }
             public K next() {
                 return it.next().key();
             }
             public void rewind() {
                it.rewind();
             }
         };
     }

} 


