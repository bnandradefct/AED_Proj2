package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of an Iterator for Linked List
 * @authors Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(1) — stores only references to the first and current nodes.
 */

class LinkedIterator<E>  implements Iterator<E> {
    /**
     * First node of the list.
     */
    private final LinkedNode<E> first;
    /**
     * Node with the next element in the iteration.
     */
    LinkedNode<E> nextToReturn;

    /**
     * SinglyIterator constructor
     * @param first - Node with the first element of the iteration
     * 
     */
    public LinkedIterator(LinkedNode<E> first) {
        this.first=first;
        nextToReturn=first;
    }

    /**
     * Checks if there are more elements in the iteration.
     * 
     * @return true if there are more elements, false otherwise.
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public boolean hasNext() {
        return nextToReturn!=null;
    }

    /**
     * Returns the next element in the iteration.
     * 
     * @return the next element in the iteration.
     * @throws NoSuchElementException if there are no more elements to return.
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public E next() throws NoSuchElementException {
        if(!hasNext()) throw new NoSuchElementException();
        E element = nextToReturn.getElement();
        nextToReturn = nextToReturn.getNext();
        return element;
    }

    /**
     * Restarts the iteration from the first element of the list.
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public void rewind() {
        nextToReturn=first;
    }

}
