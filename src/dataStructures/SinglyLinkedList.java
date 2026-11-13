package dataStructures;

import java.io.Serial;

/**
 * Implementation of Singly Linked List.
 *
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(n), where n is the number of elements in the list.
 */
public class SinglyLinkedList<E> extends SequenceLinkedList<E> {
    @Serial 
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an empty SinglyLinkedList.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public SinglyLinkedList() {
        super();
    }

    /**
     * Inserts the specified element at the first position in the list.
     *
     * @param element to be inserted
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public void addFirst(E element) {
        LinkedNode<E> newNode = new SinglyListNode<>(element, (SinglyListNode<E>) head);
        if (isEmpty()) {
            tail = newNode;
        }
        head = newNode;
        currentSize++;
    }

    /**
     * Inserts the specified element at the last position in the list.
     *
     * @param element to be inserted
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public void addLast(E element) {
        LinkedNode<E> newNode = new SinglyListNode<>(element, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            ((SinglyListNode<E>) tail).setNext(newNode);
        }
        tail = newNode;
        currentSize++;
    }

    /**
     * Inserts the specified element at a strictly interior position in the list.
     *
     * @param position middle position for insertion
     * @param element element to be inserted
     *
     * Time complexity:
     * - Best case: O(1) when position == 1
     * - Worst case: O(n) when position == currentSize - 1, where n is the number of elements in the list
     */
    @Override
    void addMiddle(int position, E element) {
        LinkedNode<E> prevNode = getNode(position - 1);
        LinkedNode<E> newNode = new SinglyListNode<>(element, (SinglyListNode<E>) prevNode.getNext());
        ((SinglyListNode<E>) prevNode).setNext(newNode);
        currentSize++;
    }
}
