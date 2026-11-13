package dataStructures;
import java.io.Serial;
import java.io.Serializable;

/**
 * Singly List Node Implementation.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(1)
 */
class SinglyListNode<E> implements LinkedNode<E>, Serializable {
    @Serial 
    private static final long serialVersionUID = 1L;

    /**
     * Element stored in the node.
     */
    @SuppressWarnings("serial")
    private E element;

    /**
     * Pointer to the next node.
     */
    private LinkedNode<E> next;

    /**
     * Constructs a SinglyListNode with the specified element and next node.
     *
     * @param theElement the element to be contained in the node
     * @param theNext the next node
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public SinglyListNode( E theElement, SinglyListNode<E> theNext ) {
        element = theElement;
        next = theNext;
    }

    /**
     * Constructs a SinglyListNode with the specified element and null next reference.
     *
     * @param theElement to be contained in the node
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public SinglyListNode( E theElement ) {
        this(theElement, null);
    }

    /**
     * Returns the element contained in the node.
     *
     * @return the element
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public E getElement( ) {
        return element;
    }

    /**
     * Returns the next node.
     *
     * @return the next node
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public LinkedNode<E> getNext( ) {
        return next;
    }

    /**
     * Updates the element stored in the node.
     *
     * @param newElement new element to replace the current element
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public void setElement( E newElement ) {
        element = newElement;
    }

    /**
     * Updates the next node reference.
     *
     * @param newNext node to replace the next node
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public void setNext( LinkedNode<E> newNext ) {
        next = newNext;
    }
}
