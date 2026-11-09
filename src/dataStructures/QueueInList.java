package dataStructures;

import dataStructures.exceptions.EmptyQueueException;

public class QueueInList<E> implements Queue<E> {

    // Memory of the queue: a list.
    private final List<E> list;

    public QueueInList( ){
        list = new SinglyLinkedList<>();
    }

    //TODO: Left as an exercise.
}
