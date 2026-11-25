package dataStructures;

/**
 * Binary Tree
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(n), where n is the number of nodes in the tree.
 */
abstract class BTree<E> extends LinkedTree<E> {

    /**
     * Constructor of an empty binary tree.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public BTree() {
        super();
    }

    /**
     * Returns the height of the tree.
     * @return height of the tree, or -1 if empty
     *
     * Time complexity:
     * - Best case: O(1) when the tree is empty or overridden with cached height
     * - Worst case: O(n), where n is the number of nodes in the tree
     */
    public int getHeight() {
        if (isEmpty())
            return -1;
        return ((BTNode<E>) root).getHeight();
    }

    /**
     * Returns the leftmost node of the tree.
     * @return leftmost BTNode, or null if empty
     *
     * Time complexity:
     * - Best case: O(1) when the tree is empty or the root has no left child
     * - Worst case: O(h), where h is the height of the tree
     */
    BTNode<E> furtherLeftElement() {
        if (isEmpty())
            return null;
        return ((BTNode<E>) root).furtherLeftElement();
    }

    /**
     * Returns the rightmost node of the tree.
     * @return rightmost BTNode, or null if empty
     *
     * Time complexity:
     * - Best case: O(1) when the tree is empty or the root has no right child
     * - Worst case: O(h), where h is the height of the tree
     */
    BTNode<E> furtherRightElement() {
        if (isEmpty())
            return null;
        return ((BTNode<E>) root).furtherRightElement();
    }
}
