package dataStructures;

/**
 * AVL Tree Node
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(1)
 */
class AVLNode<E> extends BTNode<E> {
    private static final long serialVersionUID = 1L;

    // Height of the node
    int height;

    /**
     * Constructs an AVLNode with height 0.
     * @param elem element stored in the node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public AVLNode(E elem) {
        super(elem);
        height = 0;
    }

    /**
     * Returns the cached height of this AVL node.
     * @return node height
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    int getHeight() {
        return height;
    }

    /**
     * Recomputes the height of this node from its children's heights.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void updateHeight() {
        int leftH = (getLeftChild() == null) ? -1 : ((AVLNode<E>) getLeftChild()).getHeight();
        int rightH = (getRightChild() == null) ? -1 : ((AVLNode<E>) getRightChild()).getHeight();
        height = 1 + Math.max(leftH, rightH);
    }

    /**
     * Returns the balance factor of this node (leftHeight - rightHeight).
     * @return balance factor
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    int getBalance() {
        int leftH = (getLeftChild() == null) ? -1 : ((AVLNode<E>) getLeftChild()).getHeight();
        int rightH = (getRightChild() == null) ? -1 : ((AVLNode<E>) getRightChild()).getHeight();
        return leftH - rightH;
    }

    /**
     * Returns the taller child of this node (breaking ties in favor of the same side).
     * @return taller AVLNode child
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    AVLNode<E> tallerChild() {
        int leftH = (getLeftChild() == null) ? -1 : ((AVLNode<E>) getLeftChild()).getHeight();
        int rightH = (getRightChild() == null) ? -1 : ((AVLNode<E>) getRightChild()).getHeight();
        if (leftH > rightH) {
            return (AVLNode<E>) getLeftChild();
        } else if (rightH > leftH) {
            return (AVLNode<E>) getRightChild();
        } else {
            if (getParent() != null && this == ((BTNode<E>)getParent()).getLeftChild()) {
                return (AVLNode<E>) getLeftChild();
            }
            return (AVLNode<E>) getRightChild();
        }
    }
}
