package tree;

/**
 * 二叉树的节点
 * Created by tony on 16/9/12.
 */
public class Node<T> {

    T element;  //节点的值

    Node<T> left, right; //左右孩子节点

    public Node(T element) {
        this.element = element;
    }

    public Node(T element, Node<T> left, Node<T> right) {
        this.element = element;
        this.left = left;
        this.right = right;
    }
}
