package dataStructures;
import dataStructures.Map.Entry;


/**
 * Advanced Binary Search Tree
 * @author AED Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 *
 * Space complexity: O(n), where n is the number of entries in the tree.
 */
abstract class AdvancedBSTree<K extends Comparable<K>, V> extends BSTree<K, V> {

    /**
     * Constructor with default comparator.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public AdvancedBSTree() {
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
    public AdvancedBSTree(Comparator<K> compare) {
        super(compare);
    }

    /**
     * Performs a single left rotation rooted at z node.
     * Node y was a right child of z before the rotation,
     * then z becomes the left child of y after the rotation.
     * @param z root of the rotation
     * @pre: z has a right child
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void rotateLeft(BTNode<Entry<K, V>> z) {
        BTNode<Entry<K, V>> y = (BTNode<Entry<K, V>>) z.getRightChild();
        BTNode<Entry<K, V>> t2 = (BTNode<Entry<K, V>>) y.getLeftChild();
        BTNode<Entry<K, V>> parent = (BTNode<Entry<K, V>>) z.getParent();

        y.setParent(parent);
        if (parent == null) {
            root = y;
        } else if (parent.getLeftChild() == z) {
            parent.setLeftChild(y);
        } else {
            parent.setRightChild(y);
        }

        y.setLeftChild(z);
        z.setParent(y);

        z.setRightChild(t2);
        if (t2 != null) {
            t2.setParent(z);
        }
    }

    /**
     * Performs a single right rotation rooted at z node.
     * Node y was a left child of z before the rotation,
     * then z becomes the right child of y after the rotation.
     * @param z root of the rotation
     * @pre: z has a left child
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void rotateRight(BTNode<Entry<K, V>> z) {
        BTNode<Entry<K, V>> y = (BTNode<Entry<K, V>>) z.getLeftChild();
        BTNode<Entry<K, V>> t3 = (BTNode<Entry<K, V>>) y.getRightChild();
        BTNode<Entry<K, V>> parent = (BTNode<Entry<K, V>>) z.getParent();

        y.setParent(parent);
        if (parent == null) {
            root = y;
        } else if (parent.getLeftChild() == z) {
            parent.setLeftChild(y);
        } else {
            parent.setRightChild(y);
        }

        y.setRightChild(z);
        z.setParent(y);

        z.setLeftChild(t3);
        if (t3 != null) {
            t3.setParent(z);
        }
    }

    /**
     * Performs a tri-node restructuring (a single or double rotation).
     * @param x grandchild node triggering the restructuring
     * @return the new root of the restructured subtree
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    BTNode<Entry<K, V>> restructure(BTNode<Entry<K, V>> x) {
        BTNode<Entry<K, V>> y = (BTNode<Entry<K, V>>) x.getParent();
        BTNode<Entry<K, V>> z = (BTNode<Entry<K, V>>) y.getParent();

        if (y == z.getLeftChild()) {
            if (x == y.getLeftChild()) {
                // Left-Left case: single right rotation at z
                rotateRight(z);
                return y;
            } else {
                // Left-Right case: double rotation (left at y, right at z)
                rotateLeft(y);
                rotateRight(z);
                return x;
            }
        } else {
            if (x == y.getRightChild()) {
                // Right-Right case: single left rotation at z
                rotateLeft(z);
                return y;
            } else {
                // Right-Left case: double rotation (right at y, left at z)
                rotateRight(y);
                rotateLeft(z);
                return x;
            }
        }
    }
}
