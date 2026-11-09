package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

public class ArrayIterator<T> implements TwoWayIterator<T> {
	private static final int DIFF=2;

	private final T[] elems;
	private final int counter;
	private int currentN;
	private int currentP;
	
	public ArrayIterator(T[] elems, int counter) {
		this.elems = elems;
		this.counter = counter;
		rewind();
	}
	//TODO: Left as an exercise.
}
