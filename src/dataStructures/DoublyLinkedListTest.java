package dataStructures.testDataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {

    private DoublyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new DoublyLinkedList<>();
    }

    @SafeVarargs
    private <E> void assertDoublyListInvariants(DoublyLinkedList<E> target, E... expected) {
        assertEquals(expected.length, target.size());
        assertEquals(expected.length == 0, target.isEmpty());

        if (expected.length == 0) {
            assertThrows(NoSuchElementException.class, target::getFirst);
            assertThrows(NoSuchElementException.class, target::getLast);
        } else {
            assertEquals(expected[0], target.getFirst());
            assertEquals(expected[expected.length - 1], target.getLast());
        }

        // Verificar ligação para a frente (next)
        TwoWayIterator<E> it = target.twoWayiterator();
        for (E item : expected) {
            assertTrue(it.hasNext());
            assertEquals(item, it.next());
        }
        assertFalse(it.hasNext());

        // Verificar ligação para trás (previous)
        it.fullForward();
        for (int i = expected.length - 1; i >= 0; i--) {
            assertTrue(it.hasPrevious());
            assertEquals(expected[i], it.previous());
        }
        assertFalse(it.hasPrevious());
    }

    @Test
    void testEmptyListInvariants() {
        assertDoublyListInvariants(list);
        assertThrows(NoSuchElementException.class, list::removeFirst);
        assertThrows(NoSuchElementException.class, list::removeLast);
        assertThrows(InvalidPositionException.class, () -> list.get(0));
    }

    @Test
    void testInsertionsMaintainForwardAndBackwardLinks() {
        list.addLast(20);
        assertDoublyListInvariants(list, 20);

        list.addFirst(10);
        assertDoublyListInvariants(list, 10, 20);

        list.addLast(40);
        assertDoublyListInvariants(list, 10, 20, 40);

        list.add(2, 30);
        assertDoublyListInvariants(list, 10, 20, 30, 40);

        list.add(0, 5);
        assertDoublyListInvariants(list, 5, 10, 20, 30, 40);

        list.add(5, 50);
        assertDoublyListInvariants(list, 5, 10, 20, 30, 40, 50);
    }

    @Test
    void testRemovalsMaintainForwardAndBackwardLinks() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);

        assertEquals(3, list.remove(2));
        assertDoublyListInvariants(list, 1, 2, 4, 5);

        assertEquals(1, list.removeFirst());
        assertDoublyListInvariants(list, 2, 4, 5);

        assertEquals(5, list.removeLast());
        assertDoublyListInvariants(list, 2, 4);

        assertEquals(2, list.removeFirst());
        assertDoublyListInvariants(list, 4);

        assertEquals(4, list.removeLast());
        assertDoublyListInvariants(list);
    }

    @Test
    void testTwoWayIteratorNavigation() {
        list.addLast(100);
        list.addLast(200);
        list.addLast(300);

        TwoWayIterator<Integer> it = list.twoWayiterator();
        assertFalse(it.hasPrevious());
        assertThrows(NoSuchElementException.class, it::previous);

        assertEquals(100, it.next());
        assertEquals(200, it.next());
        assertTrue(it.hasPrevious());
        assertEquals(200, it.previous());
        assertEquals(100, it.previous());
        assertFalse(it.hasPrevious());

        it.fullForward();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
        assertEquals(300, it.previous());

        it.rewind();
        assertFalse(it.hasPrevious());
        assertEquals(100, it.next());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testSerialization() throws IOException, ClassNotFoundException {
        for (int i = 0; i < 200; i++) {
            list.addLast(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(list);
        }

        DoublyLinkedList<Integer> restored;
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            restored = (DoublyLinkedList<Integer>) ois.readObject();
        }

        assertEquals(200, restored.size());
        assertEquals(0, restored.getFirst());
        assertEquals(199, restored.getLast());
    }
}
