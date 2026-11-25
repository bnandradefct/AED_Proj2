package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

public class ArrayClass<E> implements TwoWayList<E> {

    private static final int SIZE = 50;

    /**
     * Array of elements E.
     */
    private E[] elems;

    /**
     * Number of elements on an array.
     */
    private int counter;

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public ArrayClass() {
        elems = (E[]) new Object[SIZE];
        counter = 0;
    }

    /**
     *
     * @param dimension
     */
    @SuppressWarnings("unchecked")
    public ArrayClass(int dimension) {
        elems = (E[]) new Object[dimension];
        counter = 0;
    }
    //TODO: Left as an exercise.

    @Override
    public TwoWayIterator<E> twoWayiterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public E getFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elems[0];
    }

    @Override
    public E getLast() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elems[counter - 1];
    }

    @Override
    public E get(int position) throws InvalidPositionException {
        if (position < 0 || position >= counter) {
            throw new InvalidPositionException();
        }
        return elems[position];
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < counter; i++) {
            if (elems[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addFirst(E element) {
        for (int i = counter; i > 0; i--) {
            elems[i] = elems[i - 1];
        }
        elems[0] = element;
        counter++;
    }

    @Override
    public void addLast(E element) {
        elems[counter++] = element;
    }

    @Override
    public void add(int position, E element) throws InvalidPositionException {
        if (position < 0 || position > counter) {
            throw new InvalidPositionException();
        }

        for (int i = counter; i > position; i--) {
            elems[i] = elems[i - 1];
        }
        elems[position] = element;
        counter++;
    }

    @Override
    public E removeFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E removed = elems[0];
        for (int i = 0; i < counter - 1; i++) {
            elems[i] = elems[i + 1];
        }
        elems[--counter] = null;
        return removed;
    }

    @Override
    public E removeLast() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E removed = elems[--counter];
        elems[counter] = null;
        return removed;
    }

    @Override
    public E remove(int position) throws InvalidPositionException {
        if (position < 0 || position >= counter) {
            throw new InvalidPositionException();
        }
        E removed = elems[position];
        for (int i = position; i < counter - 1; i++) {
            elems[i] = elems[i + 1];
        }
        elems[--counter] = null;
        return removed;
    }
}
