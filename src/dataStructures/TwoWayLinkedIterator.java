package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Two Way Iterator for DLList 
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 */
class TwoWayLinkedIterator<E> extends LinkedIterator<E> implements TwoWayIterator<E> {

    private final LinkedNode<E> lastNode;
    private LinkedNode<E> prevToReturn;

    public TwoWayLinkedIterator(LinkedNode<E> first, LinkedNode<E> last) {
        super(first);
        this.prevToReturn = null;
        this.lastNode = last;
    }

    @Override
    public boolean hasPrevious() {
        return prevToReturn != null;
    }

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

    @Override
    public void fullForward() {
        prevToReturn = lastNode;
        nextToReturn = null;
    }

    @Override
    public void rewind() {
        super.rewind();
        prevToReturn = null;
    }

    @Override
    public E next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        prevToReturn = nextToReturn;
        return super.next();
    }
}
