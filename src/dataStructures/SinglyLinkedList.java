package dataStructures;

/**
 * Implementation of Singly Linked List
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 */
public class SinglyLinkedList<E> extends SequenceLinkedList<E> {

    public SinglyLinkedList() {
        super();
    }

    @Override
    public void addFirst(E element) {
        LinkedNode<E> newNode = new SinglyListNode<>(element, (SinglyListNode<E>) head);
        if (isEmpty()) {
            tail = newNode;
        }
        head = newNode;
        currentSize++;
    }

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

    @Override
    void addMiddle(int position, E element) {
        LinkedNode<E> prevNode = getNode(position - 1);
        LinkedNode<E> newNode = new SinglyListNode<>(element, (SinglyListNode<E>) prevNode.getNext());
        ((SinglyListNode<E>) prevNode).setNext(newNode);
        currentSize++;
    }
}
