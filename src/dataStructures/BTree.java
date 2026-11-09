package dataStructures;
/**
 * Binary Tree
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
abstract class BTree<E> extends LinkedTree<E> {

    public BTree(){
      super();
    }

    /**
     * Returns the height of the tree.
     */
    public int getHeight() {
        if(isEmpty())
            return -1;
        return ((BTNode<E>)root).getHeight();
    }

    /**
     * Return the further left node of the tree
     * @return
     */
    BTNode<E> furtherLeftElement() {
        //TODO: Left as an exercise.
        return null
    };

    /**
     * Return the further right node of the tree
     * @return
     */
    BTNode<E> furtherRightElement() {
        //TODO: Left as an exercise.
        return null;
    }

}
