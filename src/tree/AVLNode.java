package tree;

/**
 * Created by tony on 16/9/15.
 */
public class AVLNode<T> extends Node<T> {

    T value;        //节点的值
    int nodeBalance; //height of the node.

    AVLNode<T> parent; //父节点
    AVLNode<T> left;   //左孩子节点
    AVLNode<T> right;  //右孩子节点

    public AVLNode(T value) {
        super();
        this.value = value;
    }
}
