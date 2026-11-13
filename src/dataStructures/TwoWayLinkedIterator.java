package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Two-Way Iterator for Doubly Linked List.
 *
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(1)
 */
class TwoWayLinkedIterator<E> extends LinkedIterator<E> implements TwoWayIterator<E> {

    private final LinkedNode<E> lastNode;
    private LinkedNode<E> prevToReturn;

    /**
     * Constructs a TwoWayLinkedIterator bounded by the first and last nodes of the list.
     *
     * @param first the head node of the list
     * @param last the tail node of the list
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public TwoWayLinkedIterator(LinkedNode<E> first, LinkedNode<E> last) {
        super(first);
        this.prevToReturn = null;
        this.lastNode = last;
    }

    /**
     * Returns true if previous would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements in the reverse direction
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public boolean hasPrevious() {
        return prevToReturn != null;
    }

    /**
     * Returns the previous element in the iteration.
     *
     * @return previous element in the iteration
     * @throws NoSuchElementException if call is made without verifying pre-condition
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public E previous() throws NoSuchElementException {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        E element = prevToReturn.getElement();
        nextToReturn = prevToReturn;
        prevToReturn = ((DoublyListNode<E>) prevToReturn).getPrevious();
        return element;
    }

    /**
     * Restarts the iteration in the reverse direction.
     * After fullForward, if iteration is not empty,
     * previous will return the last element.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public void fullForward() {
        prevToReturn = lastNode;
        nextToReturn = null;
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public void rewind() {
        super.rewind();
        prevToReturn = null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if call is made without verifying pre-condition
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public E next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        prevToReturn = nextToReturn;
        return super.next();
    }
}
