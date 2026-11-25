package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Breadth-first (level-order) Binary Tree iterator.
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(w), where w is the maximum width of the tree (O(n) worst case).
 */
class BreadthIterator<E> implements Iterator<E> {

    private final BTNode<E> root;
    private final List<BTNode<E>> queue;

    /**
     * Constructs a BreadthIterator rooted at the specified node.
     * @param root root node of the tree
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public BreadthIterator(BTNode<E> root) {
        this.root = root;
        this.queue = new DoublyLinkedList<>();
        rewind();
    }

    /**
     * Returns true if there are more nodes to visit.
     * @return true if the queue is not empty
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    /**
     * Returns the next element in level-order traversal.
     * @return next element
     * @throws NoSuchElementException if no more elements exist
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();
        BTNode<E> curr = queue.removeFirst();
        if (curr.getLeftChild() != null)
            queue.addLast((BTNode<E>) curr.getLeftChild());
        if (curr.getRightChild() != null)
            queue.addLast((BTNode<E>) curr.getRightChild());
        return curr.getElement();
    }

    /**
     * Restarts the level-order traversal from the root.
     *
     * Time complexity:
     * - Best case: O(1) when the queue is empty
     * - Worst case: O(n), where n is the number of remaining elements in the queue
     */
    @Override
    public void rewind() {
        while (!queue.isEmpty()) {
            queue.removeFirst();
        }
        if (root != null) {
            queue.addLast(root);
        }
    }
}
