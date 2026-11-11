package dataStructures;

import java.io.Serial;

import dataStructures.exceptions.*;

/**
 * Sequence Linked List
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 */
abstract class SequenceLinkedList<E> extends LinkedList<E> implements List<E> {
    @Serial 
    private static final long serialVersionUID = 1L;

    public SequenceLinkedList() {
        super();
    }

    public E getFirst() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return head.getElement();
    }

    public E getLast() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return tail.getElement();
    }

    public E get(int position) {
        if (position < 0 || position >= currentSize)
            throw new InvalidPositionException();
        if (position == 0)
            return getFirst();
        if (position == currentSize - 1)
            return getLast();
        return getNode(position).getElement();
    }

    LinkedNode<E> getNode(int position) {
        LinkedNode<E> node = head;
        for (int i = 0; i < position; i++)
            node = node.getNext();
        return node;
    }

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

    public abstract void addFirst(E element);

    public abstract void addLast(E element);

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

    abstract void addMiddle(int position, E element);

    pairNode<E> getNodes(int position) {
        LinkedNode<E> prevNode = this.getNode(position - 1);
        return new pairNode<>(prevNode, prevNode.getNext());
    }

    public E removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        return super.removeFirstNode();
    }

    public E removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        if (size() == 1)
            return removeFirst();
        pairNode<E> pair = this.getNodes(size() - 1);
        return super.removeLastNode(pair);
    }

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

    E removeMiddle(int position) {
        return removeMiddleNode(position).node().getElement();
    }

    pairNode<E> removeMiddleNode(int position) {
        pairNode<E> pair = getNodes(position);
        super.removeMiddleNode(pair);
        return pair;
    }

    @Override
    void addElem(E element) {
        addLast(element);
    }
}
