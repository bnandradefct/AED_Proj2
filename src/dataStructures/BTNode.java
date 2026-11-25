package dataStructures;

import java.io.Serializable;

/**
 * Binary Tree Node
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 * Space complexity: O(1)
 */
class BTNode<E> implements InfoNode<E>, Serializable {
    private static final long serialVersionUID = 1L;

    // Element stored in the node.
    private E element;
    // (Pointer to) the father.
    private InfoNode<E> parent;
    // (Pointer to) the left child.
    private InfoNode<E> leftChild;
    // (Pointer to) the right child.
    private InfoNode<E> rightChild;

    /**
     * Constructor
     * @param elem element stored in the node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    BTNode(E elem) {
        this(elem, null, null, null);
    }

    /**
     * Constructor
     * @param elem element stored in the node
     * @param parent parent node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    BTNode(E elem, BTNode<E> parent) {
        this(elem, parent, null, null);
    }

    /**
     * Constructor
     * @param elem element stored in the node
     * @param parent parent node
     * @param leftChild left child node
     * @param rightChild right child node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    BTNode(E elem, BTNode<E> parent, BTNode<E> leftChild, BTNode<E> rightChild) {
        element = elem;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Returns the element of the node.
     * @return element stored in the node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public E getElement() {
        return element;
    }

    /**
     * Returns the left child of the node.
     * @return left child node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    InfoNode<E> getLeftChild() {
        return leftChild;
    }

    /**
     * Returns the right child of the node.
     * @return right child node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    InfoNode<E> getRightChild() {
        return rightChild;
    }

    /**
     * Returns the parent of the node.
     * @return parent node
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    InfoNode<E> getParent() {
        return parent;
    }

    /**
     * Updates the element stored in the node.
     * @param elem new element
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public void setElement(E elem) {
        element = elem;
    }

    /**
     * Updates the left child reference.
     * @param node new left child
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void setLeftChild(InfoNode<E> node) {
        leftChild = node;
    }

    /**
     * Updates the right child reference.
     * @param node new right child
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void setRightChild(InfoNode<E> node) {
        rightChild = node;
    }

    /**
     * Updates the parent reference.
     * @param node new parent
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    void setParent(InfoNode<E> node) {
        parent = node;
    }

    /**
     * Returns the height of the subtree rooted at this node.
     * @return height of the node (0 for a leaf)
     *
     * Time complexity:
     * - Best case: O(1) when the node is a leaf
     * - Worst case: O(n), where n is the number of nodes in the subtree
     */
    int getHeight() {
        int leftH = (leftChild == null) ? -1 : ((BTNode<E>) leftChild).getHeight();
        int rightH = (rightChild == null) ? -1 : ((BTNode<E>) rightChild).getHeight();
        return 1 + Math.max(leftH, rightH);
    }

    /**
     * Returns the leftmost descendant node in the subtree rooted at this node.
     * @return leftmost BTNode
     *
     * Time complexity:
     * - Best case: O(1) when this node has no left child
     * - Worst case: O(h), where h is the height of the subtree
     */
    BTNode<E> furtherLeftElement() {
        BTNode<E> curr = this;
        while (curr.getLeftChild() != null) {
            curr = (BTNode<E>) curr.getLeftChild();
        }
        return curr;
    }

    /**
     * Returns the rightmost descendant node in the subtree rooted at this node.
     * @return rightmost BTNode
     *
     * Time complexity:
     * - Best case: O(1) when this node has no right child
     * - Worst case: O(h), where h is the height of the subtree
     */
    BTNode<E> furtherRightElement() {
        BTNode<E> curr = this;
        while (curr.getRightChild() != null) {
            curr = (BTNode<E>) curr.getRightChild();
        }
        return curr;
    }
}
