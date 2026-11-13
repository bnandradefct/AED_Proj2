package dataStructures;

import java.io.Serializable;

/**
 * Double List Node Implementation.
 *
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(1)
 */
class DoublyListNode<E> extends SinglyListNode<E> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Pointer to the previous node.
     */
    private LinkedNode<E> previous;

    /**
     * Constructs a DoublyListNode with the specified element, previous node, and next node.
     *
     * @param theElement the element to be contained in the node
     * @param thePrevious the previous node
     * @param theNext the next node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public DoublyListNode(E theElement, DoublyListNode<E> thePrevious,
                          DoublyListNode<E> theNext) {
        super(theElement, theNext);
        this.previous = thePrevious;
    }

    /**
     * Constructs a DoublyListNode with the specified element and null neighbors.
     *
     * @param theElement the element to be contained in the node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public DoublyListNode(E theElement) {
        super(theElement, null);
        this.previous = null;
    }

    /**
     * Returns the previous node.
     *
     * @return the previous node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public LinkedNode<E> getPrevious() {
        return previous;
    }

    /**
     * Replaces the current previous node reference.
     *
     * @param newPrevious node to replace the current previous node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public void setPrevious(LinkedNode<E> newPrevious) {
        this.previous = newPrevious;
    }
}
