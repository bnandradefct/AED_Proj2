package dataStructures;

import dataStructures.exceptions.EmptyQueueException;
import dataStructures.exceptions.FullQueueException;

public class QueueInCircularArray<E> implements Queue<E>{

    // Default capacity of the queue.
    static final int DEFAULT_CAPACITY = 1000;

    // Memory of the queue: a circular array.
    private E[] array;

    // Index of the element at the front of the queue.
    private int front;

    // Number of elements in the queue.
    private int currentSize;

    @SuppressWarnings("unchecked")
    public QueueInCircularArray( int capacity ) {
        // Compiler gives a warning.
        array = (E[]) new Object[capacity];
        front = 0;
        currentSize = 0;
    }

    public QueueInCircularArray( ) {
        this(DEFAULT_CAPACITY);
    }

    //TODO: Left as an exercise.
}
