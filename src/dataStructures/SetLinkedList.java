package dataStructures;

public class SetLinkedList<E> extends LinkedList<E> implements Set<E> {

    public SetLinkedList() {
        super();
    }

    @Override
    void addElem(E element) {
        put(element);
    }

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

    @Override
    public boolean contains(E element) {
        if (isEmpty())
            return false;
        return get(element) != null;
    }

    @Override
    public boolean put(E element) {
        if (contains(element)) {
            return false;
        }
        // Cast explícito de (SinglyListNode<E>) head para permitir inferência de tipos
        LinkedNode<E> newNode = new SinglyListNode<>(element, (SinglyListNode<E>) head);
        if (isEmpty()) {
            tail = newNode;
        }
        head = newNode;
        currentSize++;
        invariant();
        return true;
    }

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

    private void invariant() {
        assert allDif();
    }

    private boolean allDif() {
        for (LinkedNode<E> n1 = head; n1 != null; n1 = n1.getNext()) {
            for (LinkedNode<E> n2 = n1.getNext(); n2 != null; n2 = n2.getNext()) {
                if (n1.getElement().equals(n2.getElement())) {
                    return false;
                }
            }
        }
        return true;
    }
}
