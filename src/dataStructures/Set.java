package dataStructures;

import java.io.Serializable;

/**
 *  Set Abstract Data Type
 * Includes description of general methods to be implemented by sets.
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public interface Set<E> extends Serializable {
    /**
     * Returns true iff the set contains no elements.
     * @return true if the set is empty
     */
    boolean isEmpty( );

    /**
     * Returns the number of elements in the set.
     * @return number of elements in the set
     */
    int size( );

    /**
     * If there is an element in the set equals to a specified element,
     * returns the element; otherwise, returns null.
     * @param element
     * @return an element in the set equals to a specified element,
     * or null otherwise
     */
    E get( E element );

    /**
     * If there is an element in the set equals to a specified element,
     * returns true; otherwise, returns false.
     * @param element
     * @return
     */
    boolean contains( E element );

    /**
     * Inserts the element,
     * if there is not an element in the set equals to the specified element and return true.
     * otherwise returns false
     * @param element to be added
     * @return true if insert the element, otherwise false
     */
    boolean put( E element );

    /**
     * If there is an element in the set equals to the specified element,
     * replace the element and return the old element.
     * otherwise returns null
     * @param element to be added
     * @return old element, otherwise null
     */
    E replace( E element );
    /**
     * If there is an element in the set equals to the specified element,
     * removes it from the set and returns the element;
     * otherwise, returns null.
     * @param element to be removed
     * @return element removed,
     * or null if the set does not an element equals
     */
    E remove( E element );

    /**
     * Returns an iterator of the elements in the set.
     * @return
     */
    Iterator<E> iterator( );

}
