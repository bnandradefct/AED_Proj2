package dataStructures;

import java.io.Serial;

import dataStructures.exceptions.*;

/**
 * Abstract Sequence Linked List implementation.
 *
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(n), where n is the number of elements in the list.
 */
abstract class SequenceLinkedList<E> extends LinkedList<E> implements List<E> {
    @Serial 
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an empty SequenceLinkedList.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public SequenceLinkedList() {
        super();
    }

    /**
     * Returns the first element of the list.
     *
     * @return first element in the list
     * @throws NoSuchElementException if size() == 0
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public E getFirst() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     *
     * @return last element in the list
     * @throws NoSuchElementException if size() == 0
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public E getLast() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return tail.getElement();
    }

    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size() - 1.
     * If the specified position is 0, it corresponds to getFirst.
     * If the specified position is size() - 1, get corresponds to getLast.
     *
     * @param position position of an element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     *
     * Time complexity:
     * - Best case: O(1) when position == 0 or position == size() - 1
     * - Worst case: O(n) when accessing an interior position, where n is the number of elements in the list
     */
    public E get(int position) {
        if (position < 0 || position >= currentSize)
            throw new InvalidPositionException();
        if (position == 0)
            return getFirst();
        if (position == currentSize - 1)
            return getLast();
        return getNode(position).getElement();
    }

    /**
     * Returns the node at the specified 0-based position.
     *
     * @param position index of the node
     * @return the LinkedNode at position
     *
     * Time complexity:
     * - Best case: O(1) when position == 0
     * - Worst case: O(n), where n is the number of elements in the list
     */
    LinkedNode<E> getNode(int position) {
        LinkedNode<E> node = head;
        for (int i = 0; i < position; i++)
            node = node.getNext();
        return node;
    }

    /**
     * Returns the position of the specified element in the list
     * if the list contains the element. Otherwise, returns -1.
     *
     * @param element element to be searched in the list
     * @return position of the element in the list (or -1)
     *
     * Time complexity:
     * - Best case: O(1) when the element is at the head of the list
     * - Worst case: O(n) when the element is at the tail or not present, where n is the number of elements in the list
     */
    public int indexOf(E element) {
        LinkedNode<E> node = head;
        int index = 0;
        while (node != null) {
            if (node.getElement().equals(element)) {
                return index;
            }
            node = node.getNext();
            index++;
        }
        return -1;
    }

    /**
     * Inserts the specified element at the first position in the list.
     *
     * @param element to be inserted
     */
    public abstract void addFirst(E element);

    /**
     * Inserts the specified element at the last position in the list.
     *
     * @param element to be inserted
     */
    public abstract void addLast(E element);

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     *
     * @param position position where to insert the element
     * @param element element to be inserted
     * @throws InvalidPositionException if position is not valid in the list
     *
     * Time complexity:
     * - Best case: O(1) when position == 0 or position == size()
     * - Worst case: O(n) when inserting at an interior position, where n is the number of elements in the list
     */
    public void add(int position, E element) {
        if (position < 0 || position > currentSize)
            throw new InvalidPositionException();
        if (position == 0)
            addFirst(element);
        else if (position == currentSize)
            addLast(element);
        else
            addMiddle(position, element);
    }

    /**
     * Inserts the specified element at a strictly interior position in the list.
     *
     * @param position middle position for insertion
     * @param element element to be inserted
     */
    abstract void addMiddle(int position, E element);

    /**
     * Returns a pair containing the node before position and the node at position.
     *
     * @param position target position (position >= 1)
     * @return pairNode of (prevNode, targetNode)
     *
     * Time complexity:
     * - Best case: O(1) when position == 1
     * - Worst case: O(n), where n is the number of elements in the list
     */
    pairNode<E> getNodes(int position) {
        LinkedNode<E> prevNode = this.getNode(position - 1);
        return new pairNode<>(prevNode, prevNode.getNext());
    }

    /**
     * Removes and returns the element at the first position in the list.
     *
     * @return element removed from the first position of the list
     * @throws NoSuchElementException if size() == 0
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public E removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        return super.removeFirstNode();
    }

    /**
     * Removes and returns the element at the last position in the list.
     *
     * @return element removed from the last position of the list
     * @throws NoSuchElementException if size() == 0
     *
     * Time complexity:
     * - Best case: O(1) when size() <= 1
     * - Worst case: O(n) when size() > 1, where n is the number of elements in the list
     */
    public E removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        if (size() == 1)
            return removeFirst();
        pairNode<E> pair = this.getNodes(size() - 1);
        return super.removeLastNode(pair);
    }

    /**
     * Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size() - 1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size() - 1, remove corresponds to removeLast.
     *
     * @param position position of the element to be removed
     * @return element removed at position
     * @throws InvalidPositionException if position is not valid in the list
     *
     * Time complexity:
     * - Best case: O(1) when position == 0
     * - Worst case: O(n) when position > 0, where n is the number of elements in the list
     */
    @Override
    public E remove(int position) {
        if (position < 0 || position >= currentSize)
            throw new InvalidPositionException();
        if (position == 0)
            return removeFirst();
        if (position == currentSize - 1)
            return removeLast();
        return removeMiddle(position);
    }

    /**
     * Removes and returns the element at a strictly interior position in the list.
     *
     * @param position middle position to remove
     * @return element removed at position
     *
     * Time complexity:
     * - Best case: O(1) when position == 1
     * - Worst case: O(n), where n is the number of elements in the list
     */
    E removeMiddle(int position) {
        return removeMiddleNode(position).node().getElement();
    }

    /**
     * Unlinks and returns the pair of nodes around a strictly interior position.
     *
     * @param position middle position to remove
     * @return pairNode containing the previous and removed nodes
     *
     * Time complexity:
     * - Best case: O(1) when position == 1
     * - Worst case: O(n), where n is the number of elements in the list
     */
    pairNode<E> removeMiddleNode(int position) {
        pairNode<E> pair = getNodes(position);
        super.removeMiddleNode(pair);
        return pair;
    }

    /**
     * Appends an element to the end of the sequence during deserialization.
     *
     * @param element element to add
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    void addElem(E element) {
        addLast(element);
    }
}
