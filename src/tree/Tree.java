package tree;

import java.util.Stack;

/**
 * Created by tony on 16/8/25.
 */
public class Tree<T> {

    private Node<T> root;   //根节点

    public Tree(Node<T> root) {
        this.root = root;
    }

    /**
     * 二叉树的节点
     * @param <T>
     */
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

    /**
     * 先序遍历
     * @param root
     */
    public void preorder(Node<T> root) {
        if(root != null) {
            System.out.println(root.element);
            preorder(root.lchild);
            preorder(root.rchild);
        }
    }

    /**
     * 递归实现中序遍历
     * @param root
     */
    public void inorder(Node<T> root) {
        if (root != null) {
            inorder(root.lchild);
            System.out.println(root.element);
            inorder(root.rchild);
        }
    }

    /**
     * 递归实现后序遍历
     * @param root
     */
    public void postorder(Node<T> root) {
        if (root != null) {
            postorder(root.lchild);
            postorder(root.rchild);
            System.out.println(root.element);
        }
    }

    /**
     * 非递归实现前序遍历
     * @param root
     */
    public void iterativePreorder(Node<T> root) {
        Stack<Node> stack = new Stack<Node>();
        if (root != null) {
            stack.push(root);
            while (!stack.empty()) {
                root = stack.pop();
                System.out.println(root.element);
                if (root.rchild != null)
                    stack.push(root.rchild);
                if (root.lchild != null)
                    stack.push(root.lchild);
            }
        }
    }

    /**
     * 非递归实现中序遍历
     * @param root
     */
    public void iterativeInorder(Node<T> root) {
        Stack<Node> stack = new Stack<Node>();
        while (root != null) {
            while (root != null) {
                if (root.rchild != null)
                    stack.push(root.rchild);// 当前节点右子入栈
                stack.push(root);// 当前节点入栈
                root = root.lchild;
            }
            root = stack.pop();
            while (!stack.empty() && root.rchild == null) {
                System.out.println(root.element);
                root = stack.pop();
            }
            System.out.println(root.element);
            if (!stack.empty())
                root = stack.pop();
            else
                root = null;
        }
    }

    /**
     * 非递归实现后序遍历
     * @param root
     */
    public void iterativePostorder(Node<T> root) {
        Node q = root;
        Stack<Node> stack = new Stack<Node>();
        while (root != null) {
            // 左子树入栈
            for (; root.lchild != null; root = root.lchild)
                stack.push(root);
            // 当前节点无右子或右子已经输出
            while (root != null && (root.rchild == null || root.rchild == q)) {
                System.out.println(root.element);
                q = root;// 记录上一个已输出节点
                if (stack.empty())
                    return;
                root = stack.pop();
            }
            // 处理右子
            stack.push(root);
            root = root.rchild;
        }
    }

    /**
     * 取得二叉树的深度.
     *
     * @param root
     * @return the depth
     */
    public int getDepth(Node<T> root) {

        if (root == null) {
            return 0;
        }

        int leftDepth = getDepth(root.lchild);
        int rightDepth = getDepth(root.rchild);

        return leftDepth > rightDepth ? leftDepth + 1 : rightDepth + 1;
    }

    /*main方法用于测试*/
    public static void main(String[] args) {
        Node<String> f = new Node<String>("F");
        Node<String> d = new Node<String>("D",f,null);
        Node<String> e = new Node<String>("E");
        Node<String> lchild = new Node<String>("B", null, d);
        Node<String> rchild = new Node<String>("C", e, null);
        Node<String> root = new Node<String>("A", lchild, rchild);

        Tree<String> tree = new Tree<String>(root);
//        tree.preorder(tree.root);
        System.out.println(tree.getDepth(tree.root));
    }
}
