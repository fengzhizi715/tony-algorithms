package tree;

/**
 * Created by tony on 16/8/25.
 */
public class Tree<T> {

    private Node<T> root;   //根节点

    public Tree(Node<T> root) {
        this.root = root;
    }

    /*二叉树的节点*/
    private static class Node<T> {
        T element;  //节点的值
        Node<T> lchild, rchild; //左右孩子节点

        public Node(T element) {
            this.element = element;
        }

        public Node(T element, Node<T> lchild, Node<T> rchild) {
            this.element = element;
            this.lchild = lchild;
            this.rchild = rchild;
        }
    }

    /*先序遍历*/
    public void preorder(Node<T> root) {
        if(root != null) {
            System.out.println(root.element);
            preorder(root.lchild);
            preorder(root.rchild);
        }
    }

    /** 递归实现中序遍历 */
    public void inorder(Node<T> root) {
        if (root != null) {
            inorder(root.lchild);
            System.out.println(root.element);
            inorder(root.rchild);
        }
    }

    /** 递归实现后序遍历 */
    public  void postorder(Node root) {
        if (root != null) {
            postorder(root.lchild);
            postorder(root.rchild);
            System.out.println(root.element);
        }
    }

    /*main方法用于测试*/
    public static void main(String[] args) {
        Node<String> d = new Node<String>("D");
        Node<String> e = new Node<String>("E");
        Node<String> lchild = new Node<String>("B", null, d);
        Node<String> rchild = new Node<String>("C", e, null);
        Node<String> root = new Node<String>("A", lchild, rchild);

        Tree<String> tree = new Tree<String>(root);
        tree.postorder(tree.root);
    }
}
