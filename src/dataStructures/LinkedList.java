package dataStructures;

import java.io.*;

/**
 * Linked List
 * Includes description of general methods to be implemented by linked lists.
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(n), where n is the number of elements in the list.
 */
abstract class LinkedList<E> implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    /**
     *  Node at the head of the list.
     */
    transient LinkedNode<E> head;
    /**
     * Node at the tail of the list.
     */
    transient LinkedNode<E> tail;
    /**
     * Number of elements in the list.
     */
    transient int currentSize;
    /**
     * Constructor of an empty singly linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public LinkedList(){
        head=null;
        tail=null;
        currentSize=0;
    }
    /**
     * Returns true iff the list contains no elements.
     * @return true if the list is empty
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }
    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public int size() {
        return currentSize;
    }

    /**
     * Returns an iterator of the elements in the list (in a proper sequence).
     * @return Iterator of the elements in the list
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public Iterator<E> iterator() {
        return new LinkedIterator<>(head);
    }

    /**
     * Insert a node on the head of list
     * @param newNode
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void addFirstNode(LinkedNode<E> newNode){
        //TODO: Left as an exercise.
        newNode.setNext(head);
        head = newNode;
        if (isEmpty()) {
            tail = newNode;
        }
        currentSize++;
    }
    /**
     * Insert a node on the tail of list
     * @param newNode
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void addLastNode(LinkedNode<E> newNode){
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        currentSize++;
    }
    

    record pairNode<E>(LinkedNode<E> prev, LinkedNode<E> node){}

    /**
     * Insert node (newNode) between pair.previous() and pair.node()
     * @pre: pair.previous()!=null && pair.node()!=null
     * @param newNode
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void addMiddleNode(pairNode<E> pair,LinkedNode<E> newNode){
        newNode.setNext(pair.node());
        pair.prev().setNext(newNode);
        currentSize++;
    }
    /**
     * Removes the first node in the list.
     * @pre: !isEmpty()
     * @return
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    E removeFirstNode(){
        E element = head.getElement();
        head = head.getNext();
        currentSize--;
        if (isEmpty()) {
            tail = null;
        }
        return element;
    }

    /**
     * remove the last node (pair.node()) of the list
     * @return
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    E removeLastNode(pairNode<E> pair){
        E element = pair.node().getElement();
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            pair.prev().setNext(null);
            tail = pair.prev();
        }
        currentSize--;
        return element;
    }
    /**
     * remove the node pair.node()
     @pre: pair.previous()!=null && pair.node()!=null
     * @param pair
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void removeMiddleNode(pairNode<E> pair) {
        pair.prev().setNext(pair.node().getNext());
        currentSize--;
    }

    /**
     *
     * @param element
     * @return pair with the previous node and the element node, Or null if no element
     * 
     * Time complexity:
     * - Best case: O(1) — when the list is empty or the element is at the head.
     * - Worst case: O(n) — when the element is at the tail or does not exist in the list.
     */
    pairNode<E>  nodeOf(E element){
  
        LinkedNode<E> prev = null;
        LinkedNode<E> curr = head;

        while (curr != null) {
            if ((element == null && curr.getElement() == null) ||
                    (element != null && element.equals(curr.getElement()))) {
                return new pairNode<>(prev, curr);
            }
            prev = curr;
            curr = curr.getNext();
        }
        return null;
    }

    /**
     * Returns the first node of the list.
     * @return the head node
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    LinkedNode<E> getFirstNode(){

        return head;
    }

    /**
     * Returns the last node of the list.
     * @return the tail node
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    LinkedNode<E> getLastNode(){
        //TODO: Left as an exercise.
        return tail;
    }
     // MANUAL SERIALIZATION
    /**
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    // MANUAL DESERIALIZATION
    /**
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }

    /**
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void writeData(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /**
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void readData(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    abstract void addElem(E element);

}
