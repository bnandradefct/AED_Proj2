package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * SepChain Hash Table Iterator
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.1
 * @param <K> Generic Key
 * @param <V> Generic Value
 *
 * Space complexity: O(1)
 */
class SepChainHTIterator<K, V> implements Iterator<Map.Entry<K, V>> {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Hash table bucket array.
     */
    private final Set<Map.Entry<K, V>>[] table;

    /**
     * Index of the current bucket being traversed.
     */
    private int currentBucket;

    /**
     * Iterator for the current bucket, or null if the table is empty or exhausted.
     */
    private Iterator<Map.Entry<K, V>> bucketIterator;

    /**
     * Constructs a SepChainHTIterator for the specified bucket array.
     *
     * @param table array of sets containing the hash table entries
     *
     * Time complexity:
     * - Best case: O(1) when the first bucket contains elements
     * - Worst case: O(n) when the table is empty or only the last bucket has elements, where n is the table capacity
     */
    public SepChainHTIterator(Set<Map.Entry<K, V>>[] table) {
        this.table = table;
        this.rewind();
    }

    /**
     * Advances currentBucket to the next non-null, non-empty bucket and initializes its iterator.
     *
     * Time complexity:
     * - Best case: O(1) when the current bucket is non-empty
     * - Worst case: O(n) when scanning across empty buckets, where n is the table capacity
     */
    private void findNextBucket() {
        while (currentBucket < table.length) {
            if (table[currentBucket] != null && !table[currentBucket].isEmpty()) {
                bucketIterator = table[currentBucket].iterator();
                return;
            }
            currentBucket++;
        }
        bucketIterator = null;
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     *
     * Time complexity:
     * - Best case: O(1) when the current bucket has remaining elements or the iteration has ended
     * - Worst case: O(n) when advancing across empty buckets, where n is the table capacity
     */
    @Override
    public boolean hasNext() {
        if (bucketIterator == null) {
            return false;
        }
        if (bucketIterator.hasNext()){
            return true;
        }
            currentBucket++;
            findNextBucket();
        return bucketIterator != null && bucketIterator.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if call is made without verifying pre-condition
     *
     * Time complexity:
     * - Best case: O(1) when the current bucket has remaining elements
     * - Worst case: O(n) when skipping empty buckets to reach the next entry, where n is the table capacity
     */
    @Override
    public Map.Entry<K, V> next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return bucketIterator.next();
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     *
     * Time complexity:
     * - Best case: O(1) when the first bucket is non-empty
     * - Worst case: O(n) when the table is empty or only the last bucket has elements, where n is the table capacity
     */
    @Override
    public void rewind() {
        currentBucket = 0;
        bucketIterator = null;
        findNextBucket();
    }
}
