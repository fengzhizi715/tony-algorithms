package tree;

/**
 * 二叉树的节点
 * Created by tony on 16/9/12.
 */
public class Node<T> {

    T value;        //节点的值

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

    /**
     * 是否有孩子节点
     * @return
     */
    public boolean hasSubNode() {

        return hasLeftChild() || hasRightChild();
    }

    /**
     * 是否有左孩子
     * @return
     */
    public boolean hasLeftChild() {

        return left != null;
    }

    /**
     * 是否有右孩子
     * @return
     */
    public boolean hasRightChild() {

        return right != null;
    }

    public T getValue() {
        return value;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public static <T> int counter(Node<T> node) {

        return node == null ? 0 : counter(node.left) + counter(node.right) + 1;
    }

    /**
     * 取得节点的深度.
     *
     * @param node
     * @return the depth
     */
    public static <T> int getDepth(Node<T> node) {

        if (node == null) {
            return 0;
        }

        int leftDepth = getDepth(node.left);
        int rightDepth = getDepth(node.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
