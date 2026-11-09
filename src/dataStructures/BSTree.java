package dataStructures;

import dataStructures.exceptions.EmptyMapException;
import dataStructures.Map.Entry;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Binary Search Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTree<K extends Comparable<K>,V> extends BTree<Entry<K,V>> implements SortedMap<K,V> {
    /**
     * Comparator
     */
    private Comparator<K> comparator;
    /**
     * Default comparator
     * @param <K>
     */
    static class DefaultComparator<K extends Comparable<K>> implements Comparator<K> {
        public int compare(K a, K b)  {
            return a.compareTo(b);
        }
    }
    /**
     * Constructors
     */
    public BSTree(){
        super();
        comparator= new DefaultComparator<>();
    }

    public BSTree(Comparator<K> compare){
        super();
        comparator = compare;
    }
    /**
     * Returns the entry with the smallest key in the map.
     *
     * @return
     * @throws EmptyMapException
     */
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the map.
     *
     * @return
     * @throws EmptyMapException
     */
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
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
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    public V put(K key, V value) {
        //TODO: Left as an exercise.
        return null;
    }

    /**
     * If there is an entry in the map whose key is the specified key,
     * removes it from the map and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    public V remove(K key) {
        //TODO: Left as an exercise.
        return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Entry<K,V>>) root);
    }

    //Manual Serialization
    Iterator<Entry<K, V>> iteratorLevel() {
        return new BreadthIterator<>((BTNode<Entry<K,V>>)root);
    }

    void addElem(Entry<K, V> element) {
        put(element.key(),element.value());
    }

    void writeData(ObjectOutputStream oos) throws IOException {
        oos.writeObject(comparator);
    }

    void readData(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        comparator = (Comparator<K>) ois.readObject();
    }

}
