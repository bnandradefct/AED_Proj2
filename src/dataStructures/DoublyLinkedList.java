package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Doubly Linked List
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 */
public class DoublyLinkedList<E> extends SequenceLinkedList<E> implements TwoWayList<E> {

    /**
     * Constructor of an empty double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public DoublyLinkedList() {
        super();
    }

    /**
     * Returns a two-way iterator of the elements in the list.
     *
     * @return Two-Way Iterator of the elements in the list
     */
    public TwoWayIterator<E> twoWayiterator() {
        return new TwoWayLinkedIterator<>(head, tail);
    }

    /**
     * Inserts the element at the first position in the list.
     * @param element - Element to be inserted
     */
    @Override
    public void addFirst(E element) {
        DoublyListNode<E> newNode = new DoublyListNode<>(element, null, (DoublyListNode<E>) head);
        if (isEmpty()) {
            tail = newNode;
        } else {
            ((DoublyListNode<E>) head).setPrevious(newNode);
        }
        head = newNode;
        currentSize++;
    }

    /**
     * Inserts the element at the last position in the list.
     * @param element - Element to be inserted
     */
    @Override
    public void addLast(E element) {
        DoublyListNode<E> newNode = new DoublyListNode<>(element, (DoublyListNode<E>) tail, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        currentSize++;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Pre-condition: position ranges from 1 to currentSize-1.
     * @param position - middle position for insertion of element
     * @param element - element to be inserted at middle position
     */
    @Override
    void addMiddle(int position, E element) {
        LinkedNode<E> nextNode = getNode(position);
        LinkedNode<E> prevNode = ((DoublyListNode<E>) nextNode).getPrevious();

        DoublyListNode<E> newNode = new DoublyListNode<>(element, (DoublyListNode<E>) prevNode, (DoublyListNode<E>) nextNode);
        prevNode.setNext(newNode);
        ((DoublyListNode<E>) nextNode).setPrevious(newNode);
        currentSize++;
    }

    /**
     * Removes and returns the element at the first position in the list.
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E element = head.getElement();
        head = head.getNext();
        currentSize--;
        if (isEmpty()) {
            tail = null;
        } else {
            ((DoublyListNode<E>) head).setPrevious(null);
        }
        return element;
    }
}
