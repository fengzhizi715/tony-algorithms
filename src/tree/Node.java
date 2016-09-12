package tree;

/**
 * 二叉树的节点
 * Created by tony on 16/9/12.
 */
public class Node<T> {

    T value;  //节点的值

    Node<T> parent; //父节点
    Node<T> left;   //左孩子节点
    Node<T> right;  //右孩子节点

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node<T> left, Node<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public Node(T value, Node<T> parent, Node<T> left, Node<T> right) {
        this.value = value;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public boolean hasSubNode() {

        return left!=null || right!=null;
    }

    public String toString() {
        String leftkey = (left == null ? "" : String
                .valueOf(left.value));
        String rightkey = (right == null ? "" : String
                .valueOf(right.value));
        return "(" + leftkey + " , " + value + " , " + rightkey + ")";
    }
}
