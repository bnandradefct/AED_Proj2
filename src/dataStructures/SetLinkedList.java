package dataStructures;

import java.io.Serial;

/**
 * Singly Linked List implementation of the Set ADT.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(n), where n is the number of elements in the set.
 */
public class SetLinkedList<E> extends LinkedList<E> implements Set<E> {
    @Serial 
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an empty SetLinkedList.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public SetLinkedList() {
        super();
    }

    /**
     * Inserts an element into the set during deserialization.
     *
     * @param element element to add
     *
     * Time complexity:
     * - Best case: O(1) when the set is empty or the element matches the head
     * - Worst case: O(n), where n is the number of elements in the set
     */
    @Override
    void addElem(E element) {
        put(element);
    }

    /**
     * If there is an element in the set equal to a specified element,
     * returns the element; otherwise, returns null.
     *
     * @param element element to search for
     * @return an element in the set equal to the specified element,
     * or null otherwise
     *
     * Time complexity:
     * - Best case: O(1) when element is null, the set is empty, or element is at the head
     * - Worst case: O(n) when element is at the tail or not present, where n is the number of elements in the set
     */
    @Override
    public E get(E element) {
        if (element == null) return null;
        LinkedNode<E> curr = head;
        while (curr != null) {
            if (element.equals(curr.getElement())) {
                return curr.getElement();
            }
            curr = curr.getNext();
        }
        return null;
    }

    /**
     * If there is an element in the set equal to a specified element,
     * returns true; otherwise, returns false.
     *
     * @param element element to check for membership
     * @return true if the element is in the set, false otherwise
     *
     * Time complexity:
     * - Best case: O(1) when the set is empty or element is at the head
     * - Worst case: O(n), where n is the number of elements in the set
     */
    @Override
    public boolean contains(E element) {
        if (isEmpty())
            return false;
        return get(element) != null;
    }

    /**
     * Inserts the element if there is not an element in the set equal to the specified element
     * and returns true; otherwise returns false.
     *
     * @param element to be added
     * @return true if the element is inserted, otherwise false
     *
     * Time complexity:
     * - Best case: O(1) when the set is empty or the element already exists at the head
     * - Worst case: O(n), where n is the number of elements in the set
     */
    @Override
    public boolean put(E element) {
        if (contains(element)) {
            return false;
        }
        SinglyListNode<E> newNode = new SinglyListNode<>(element, (SinglyListNode<E>) head);
        if (isEmpty()) {
            tail = newNode;
        }
        head = newNode;
        currentSize++;
        return true;
    }

    /**
     * If there is an element in the set equal to the specified element,
     * replaces the element and returns the old element; otherwise returns null.
     *
     * @param element to be added
     * @return old element, otherwise null
     *
     * Time complexity:
     * - Best case: O(1) when element is null, the set is empty, or element is at the head
     * - Worst case: O(n), where n is the number of elements in the set
     */
    @Override
    public E replace(E element) {
        if (element == null) return null;
        LinkedNode<E> curr = head;
        while (curr != null) {
            if (element.equals(curr.getElement())) {
                E old = curr.getElement();
                ((SinglyListNode<E>) curr).setElement(element);
                return old;
            }
            curr = curr.getNext();
        }
        return null;
    }

    /**
     * If there is an element in the set equal to the specified element,
     * removes it from the set and returns the element; otherwise, returns null.
     *
     * @param element to be removed
     * @return element removed, or null if the set does not contain an equal element
     *
     * Time complexity:
     * - Best case: O(1) when the set is empty, element is null, or element is at the head
     * - Worst case: O(n) when element is at the tail or not present, where n is the number of elements in the set
     */
    @Override
    public E remove(E element) {
        if (isEmpty() || element == null) {
            return null;
        }

        if (element.equals(head.getElement())) {
            return super.removeFirstNode();
        }

        LinkedNode<E> prev = head;
        LinkedNode<E> curr = head.getNext();

        while (curr != null) {
            if (element.equals(curr.getElement())) {
                pairNode<E> pair = new pairNode<>(prev, curr);
                if (curr == tail) {
                    return super.removeLastNode(pair);
                } else {
                    super.removeMiddleNode(pair);
                    return curr.getElement();
                }
            }
            prev = curr;
            curr = curr.getNext();
        }
        return null;
    }
}
