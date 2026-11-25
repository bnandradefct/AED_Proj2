package dataStructures;

import dataStructures.exceptions.EmptyMapException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Binary Search Tree Sorted Map
 * @author AED Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 *
 * Space complexity: O(n), where n is the number of entries in the tree.
 */
public class BSTree<K extends Comparable<K>, V> extends BTree> implements SortedMap<K, V> {
    /**
     * Comparator
     */
    protected Comparator<K> comparator;

    /**
     * Default comparator
     * @param <K> Generic Key
     */
    static class DefaultComparator<K extends Comparable<K>> implements Comparator<K> {
        private static final long serialVersionUID = 1L;

        /**
         * Compares two keys using their natural ordering.
         * @param a first key
         * @param b second key
         * @return negative, zero, or positive integer
         *
         * Time complexity:
         * - Best case: O(1)
         * - Worst case: O(1)
         */
        public int compare(K a, K b) {
            return a.compareTo(b);
        }
    }

    /**
     * Constructs an empty BSTree using the natural comparator.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public BSTree() {
        super();
        comparator = new DefaultComparator<>();
    }

    /**
     * Constructs an empty BSTree using the specified comparator.
     * @param compare custom key comparator
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public BSTree(Comparator<K> compare) {
        super();
        comparator = compare;
    }

    /**
     * Returns the entry with the smallest key in the map.
     *
     * @return minimum entry
     * @throws EmptyMapException if the map is empty
     *
     * Time complexity:
     * - Best case: O(1) when the tree is empty or the root has no left child
     * - Worst case: O(h), where h is the height of the tree
     */
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the map.
     *
     * @return maximum entry
     * @throws EmptyMapException if the map is empty
     *
     * Time complexity:
     * - Best case: O(1) when the tree is empty or the root has no right child
     * - Worst case: O(h), where h is the height of the tree
     */
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
    }

    /**
     * Finds and returns the node containing the specified key, or null if not found.
     *
     * @param key key to search for
     * @return matching BTNode or null
     *
     * Time complexity:
     * - Best case: O(1) when the key is at the root or tree is empty
     * - Worst case: O(h), where h is the height of the tree
     */
    protected BTNode<Entry<K, V>> findNode(K key) {
        if (key == null) return null;
        BTNode<Entry<K, V>> curr = (BTNode<Entry<K, V>>) root;
        while (curr != null) {
            int cmp = comparator.compare(key, curr.getElement().key());
            if (cmp == 0) {
                return curr;
            } else if (cmp < 0) {
                curr = (BTNode<Entry<K, V>>) curr.getLeftChild();
            } else {
                curr = (BTNode<Entry<K, V>>) curr.getRightChild();
            }
        }
        return null;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     *
     * Time complexity:
     * - Best case: O(1) when the key is at the root or tree is empty
     * - Worst case: O(h), where h is the height of the tree
     */
    public V get(K key) {
        BTNode<Entry<K, V>> node = findNode(key);
        return node != null ? node.getElement().value() : null;
    }

    /**
     * Links a newly created node as a child of parent (or as root if parent is null).
     *
     * @param parent parent node
     * @param newNode new child node
     * @param isLeft true to attach as left child, false for right child
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    protected void linkChild(BTNode<Entry<K, V>> parent, BTNode<Entry<K, V>> newNode, boolean isLeft) {
        newNode.setParent(parent);
        if (parent == null) {
            root = newNode;
        } else if (isLeft) {
            parent.setLeftChild(newNode);
        } else {
            parent.setRightChild(newNode);
        }
        currentSize++;
    }

    /**
     * Replaces subtree rooted at u with subtree rooted at v.
     *
     * @param u node to replace
     * @param v replacement node (may be null)
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    protected void transplant(BTNode<Entry<K, V>> u, BTNode<Entry<K, V>> v) {
        BTNode<Entry<K, V>> parent = (BTNode<Entry<K, V>>) u.getParent();
        if (parent == null) {
            root = v;
        } else if (u == parent.getLeftChild()) {
            parent.setLeftChild(v);
        } else {
            parent.setRightChild(v);
        }
        if (v != null) {
            v.setParent(parent);
        }
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
     *
     * Time complexity:
     * - Best case: O(1) when the tree is empty or key matches the root
     * - Worst case: O(h), where h is the height of the tree
     */
    public V put(K key, V value) {
        BTNode<Entry<K, V>> parent = null;
        BTNode<Entry<K, V>> curr = (BTNode<Entry<K, V>>) root;
        int cmp = 0;

        while (curr != null) {
            parent = curr;
            cmp = comparator.compare(key, curr.getElement().key());
            if (cmp == 0) {
                V oldVal = curr.getElement().value();
                curr.setElement(new Entry<>(key, value));
                return oldVal;
            } else if (cmp < 0) {
                curr = (BTNode<Entry<K, V>>) curr.getLeftChild();
            } else {
                curr = (BTNode<Entry<K, V>>) curr.getRightChild();
            }
        }

        BTNode<Entry<K, V>> newNode = new BTNode<>(new Entry<>(key, value));
        linkChild(parent, newNode, cmp < 0);
        return null;
    }

    /**
     * If there is an entry in the map whose key is the specified key,
     * removes it from the map and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     *
     * Time complexity:
     * - Best case: O(1) when key is at the root with at most one child or tree is empty
     * - Worst case: O(h), where h is the height of the tree
     */
    public V remove(K key) {
        BTNode<Entry<K, V>> z = findNode(key);
        if (z == null) return null;

        V oldValue = z.getElement().value();
        if (z.getLeftChild() == null) {
            transplant(z, (BTNode<Entry<K, V>>) z.getRightChild());
        } else if (z.getRightChild() == null) {
            transplant(z, (BTNode<Entry<K, V>>) z.getLeftChild());
        } else {
            BTNode<Entry<K, V>> y = ((BTNode<Entry<K, V>>) z.getRightChild()).furtherLeftElement();
            if (y.getParent() != z) {
                transplant(y, (BTNode<Entry<K, V>>) y.getRightChild());
                y.setRightChild(z.getRightChild());
                ((BTNode<Entry<K, V>>) y.getRightChild()).setParent(y);
            }
            transplant(z, y);
            y.setLeftChild(z.getLeftChild());
            ((BTNode<Entry<K, V>>) y.getLeftChild()).setParent(y);
        }
        currentSize--;
        return oldValue;
    }

    /**
     * Returns an iterator of the entries in the dictionary in ascending key order.
     *
     * @return iterator of the entries in the dictionary
     *
     * Time complexity:
     * - Best case: O(1) when the tree is empty or root has no left child
     * - Worst case: O(h), where h is the height of the tree
     */
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Entry<K, V>>) root);
    }

    /**
     * Returns a level-order iterator used during serialization.
     *
     * @return level-order iterator
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    Iterator<Entry<K, V>> iteratorLevel() {
        return new BreadthIterator<>((BTNode<Entry<K, V>>) root);
    }

    /**
     * Inserts an entry into the map during deserialization.
     *
     * @param element entry to insert
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(h), where h is the height of the tree
     */
    void addElem(Entry<K, V> element) {
        put(element.key(), element.value());
    }

    /**
     * Serializes the comparator during object output.
     *
     * @param oos ObjectOutputStream
     * @throws IOException if an I/O error occurs
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void writeData(ObjectOutputStream oos) throws IOException {
        oos.writeObject(comparator);
    }

    /**
     * Deserializes the comparator during object input.
     *
     * @param ois ObjectInputStream
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if class cannot be found
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @SuppressWarnings("unchecked")
    void readData(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        comparator = (Comparator<K>) ois.readObject();
    }
}
