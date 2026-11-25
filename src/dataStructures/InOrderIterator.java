package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * In-order Binary Tree iterator
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(1)
 */
public class InOrderIterator<E> implements Iterator<E> {

    /**
     * Node with the current element
     */
    private BTNode<E> next;
    /**
     * Root Node
     */
    private final BTNode<E> root;

    /**
     * Constructs an InOrderIterator starting at the smallest element in the subtree rooted at root.
     * @param root root node of the tree
     *
     * Time complexity:
     * - Best case: O(1) when root is null or has no left child
     * - Worst case: O(h), where h is the height of the tree
     */
    public InOrderIterator(BTNode<E> root) {
        this.root = root;
        rewind();
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public boolean hasNext() {
        return next != null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if call is made without verifying pre-condition
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(h), where h is the height of the tree (O(1) amortized per step)
     */
    @Override
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();
        E elem = next.getElement();
        advance();
        return elem;
    }

    /**
     * Advances the iterator to the in-order successor of the current node.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(h), where h is the height of the tree
     */
    private void advance() {
        if (next.getRightChild() != null) {
            next = ((BTNode<E>) next.getRightChild()).furtherLeftElement();
        } else {
            BTNode<E> parent = (BTNode<E>) next.getParent();
            while (parent != null && next == parent.getRightChild()) {
                next = parent;
                parent = (BTNode<E>) parent.getParent();
            }
            next = parent;
        }
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     *
     * Time complexity:
     * - Best case: O(1) when root is null or has no left child
     * - Worst case: O(h), where h is the height of the tree
     */
    public void rewind() {
        if (root == null)
            next = null;
        else
            next = root.furtherLeftElement();
    }
}
