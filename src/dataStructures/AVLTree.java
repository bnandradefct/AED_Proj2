package dataStructures;
import dataStructures.Map.Entry;


/**
 * AVL Tree Sorted Map
 * @author AED Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 *
 * Space complexity: O(n), where n is the number of entries in the tree.
 */
public class AVLTree<K extends Comparable<K>, V> extends AdvancedBSTree<K, V> {

    /**
     * Constructor with default comparator.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public AVLTree() {
        super();
    }

    /**
     * Constructor with custom comparator.
     * @param compare custom key comparator
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public AVLTree(Comparator<K> compare) {
        super(compare);
    }

    /**
     * If an entry with this key exists, updates the node with the new value
     * and returns the old value of the entry;
     * otherwise, inserts a new AVLNode, rebalances from the insertion position,
     * and returns null.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return previous value associated with key, or null if none existed
     *
     * Time complexity:
     * - Best case: O(1) when the tree is empty or the key matches the root
     * - Worst case: O(log n), where n is the number of entries in the tree
     */
    @Override
    public V put(K key, V value) {
        AVLNode<Entry<K, V>> parent = null;
        AVLNode<Entry<K, V>> curr = (AVLNode<Entry<K, V>>) root;
        int cmp = 0;

        while (curr != null) {
            parent = curr;
            cmp = comparator.compare(key, curr.getElement().key());
            if (cmp == 0) {
                V oldVal = curr.getElement().value();
                curr.setElement(new Entry<>(key, value));
                return oldVal;
            } else if (cmp < 0) {
                curr = (AVLNode<Entry<K, V>>) curr.getLeftChild();
            } else {
                curr = (AVLNode<Entry<K, V>>) curr.getRightChild();
            }
        }

        AVLNode<Entry<K, V>> newNode = new AVLNode<>(new Entry<>(key, value));
        linkChild(parent, newNode, cmp < 0);
        if (parent != null) {
            rebalance(parent);
        }
        invariant();
        return null;
    }

    /**
     * If no entry exists with this key, returns null;
     * otherwise, removes the node with this key,
     * rebalances from the removal position, and returns its value.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key, or null if not found
     *
     * Time complexity:
     * - Best case: O(1) when the tree is empty
     * - Worst case: O(log n), where n is the number of entries in the tree
     */
    @Override
    public V remove(K key) {
        AVLNode<Entry<K, V>> z = (AVLNode<Entry<K, V>>) findNode(key);
        if (z == null) return null;

        V oldValue = z.getElement().value();
        AVLNode<Entry<K, V>> actionPos;

        if (z.getLeftChild() == null) {
            actionPos = (AVLNode<Entry<K, V>>) z.getParent();
            transplant(z, (BTNode<Entry<K, V>>) z.getRightChild());
        } else if (z.getRightChild() == null) {
            actionPos = (AVLNode<Entry<K, V>>) z.getParent();
            transplant(z, (BTNode<Entry<K, V>>) z.getLeftChild());
        } else {
            AVLNode<Entry<K, V>> y = (AVLNode<Entry<K, V>>) ((BTNode<Entry<K, V>>) z.getRightChild()).furtherLeftElement();
            if (y.getParent() != z) {
                actionPos = (AVLNode<Entry<K, V>>) y.getParent();
                transplant(y, (BTNode<Entry<K, V>>) y.getRightChild());
                y.setRightChild(z.getRightChild());
                ((BTNode<Entry<K, V>>) y.getRightChild()).setParent(y);
            } else {
                actionPos = y;
            }
            transplant(z, y);
            y.setLeftChild(z.getLeftChild());
            ((BTNode<Entry<K, V>>) y.getLeftChild()).setParent(y);
        }

        currentSize--;
        if (actionPos != null) {
            rebalance(actionPos);
        }
        invariant();
        return oldValue;
    }

    /**
     * Traverses the path from zPos to the root. For each node encountered,
     * recomputes its height and performs a trinode restructuring if unbalanced.
     *
     * @param zPos starting node for bottom-up height update and rebalancing
     *
     * Time complexity:
     * - Best case: O(1) when zPos is the root and balanced
     * - Worst case: O(log n), where n is the number of entries in the tree
     */
    void rebalance(AVLNode<Entry<K, V>> zPos) {
        AVLNode<Entry<K, V>> curr = zPos;
        while (curr != null) {
            curr.updateHeight();
            if (Math.abs(curr.getBalance()) > 1) {
                AVLNode<Entry<K, V>> y = curr.tallerChild();
                AVLNode<Entry<K, V>> x = y.tallerChild();
                curr = (AVLNode<Entry<K, V>>) restructure(x);
                if (curr.getLeftChild() != null) {
                    ((AVLNode<Entry<K, V>>) curr.getLeftChild()).updateHeight();
                }
                if (curr.getRightChild() != null) {
                    ((AVLNode<Entry<K, V>>) curr.getRightChild()).updateHeight();
                }
                curr.updateHeight();
            }
            curr = (AVLNode<Entry<K, V>>) curr.getParent();
        }
    }

    /**
     * Asserts that the AVL height-balance invariant holds across the entire tree.
     *
     * Time complexity:
     * - Best case: O(1) when assertions are disabled or tree has <= 1 node
     * - Worst case: O(n), where n is the number of nodes in the tree
     */
    private void invariant() {
        assert isAVL() : " AVL tree";
    }

    /**
     * Checks whether the tree satisfies the AVL balance invariant.
     * @return true if balanced, false otherwise
     *
     * Time complexity:
     * - Best case: O(1) when the tree has <= 1 node
     * - Worst case: O(n), where n is the number of nodes in the tree
     */
    private boolean isAVL() {
        if (isEmpty() || size() == 1)
            return true;
        return isAVL((AVLNode<Entry<K, V>>) root);
    }

    /**
     * Recursively validates balance factor and cached height at each node.
     * @param node subtree root
     * @return true if subtree rooted at node is a valid AVL tree
     *
     * Time complexity:
     * - Best case: O(1) when node is null or unbalanced at the root
     * - Worst case: O(n), where n is the number of nodes in the subtree
     */
    private boolean isAVL(AVLNode<Entry<K, V>> node) {
        if (node == null) {
            return true;
        }
        int leftH = (node.getLeftChild() == null) ? -1 : ((AVLNode<Entry<K, V>>) node.getLeftChild()).getHeight();
        int rightH = (node.getRightChild() == null) ? -1 : ((AVLNode<Entry<K, V>>) node.getRightChild()).getHeight();

        if (Math.abs(leftH - rightH) > 1 || node.getHeight() != 1 + Math.max(leftH, rightH)) {
            return false;
        }
        return isAVL((AVLNode<Entry<K, V>>) node.getLeftChild())
            && isAVL((AVLNode<Entry<K, V>>) node.getRightChild());
    }
}
