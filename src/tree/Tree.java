package tree;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 二叉树
 * Created by tony on 16/8/25.
 */
public class Tree<T> {

    Node<T> root;   //根节点

    public Tree() {
    }

    public Tree(Node<T> root) {
        this.root = root;
    }

    /**
     * 判断树是否为空
     * @return
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 获取根节点
     * @return
     */
    public Node<T> getRoot() {
        return root;
    }

    /**
     * 清空二叉树
     */
    public void clear(){
        root=null;
    }

    /**
     * 先序遍历
     * @param root
     */
    public void preorder(Node<T> root) {
        if(root != null) {
            System.out.println(root.value);
            preorder(root.left);
            preorder(root.right);
        }
    }

    /**
     * 递归实现中序遍历
     * @param root
     */
    public void inorder(Node<T> root) {
        if (root != null) {
            inorder(root.left);
            System.out.println(root.value);
            inorder(root.right);
        }
    }

    /**
     * 递归实现后序遍历
     * @param root
     */
    public void postorder(Node<T> root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.println(root.value);
        }
    }

    /**
     * 深度优先遍历
     * 非递归实现前序遍历
     * @param root
     */
    public void iterativePreorder(Node<T> root) {
        Stack<Node> stack = new Stack<Node>();
        if (root != null) {
            stack.push(root);
            while (!stack.empty()) {
                root = stack.pop();
                System.out.println(root.value);
                if (root.right != null)
                    stack.push(root.right);
                if (root.left != null)
                    stack.push(root.left);
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
                if (root.right != null)
                    stack.push(root.right);// 当前节点右子入栈
                stack.push(root);// 当前节点入栈
                root = root.left;
            }
            root = stack.pop();
            while (!stack.empty() && root.right == null) {
                System.out.println(root.value);
                root = stack.pop();
            }
            System.out.println(root.value);
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
            for (; root.left != null; root = root.left)
                stack.push(root);
            // 当前节点无右子或右子已经输出
            while (root != null && (root.right == null || root.right == q)) {
                System.out.println(root.value);
                q = root;// 记录上一个已输出节点
                if (stack.empty())
                    return;
                root = stack.pop();
            }
            // 处理右子
            stack.push(root);
            root = root.right;
        }
    }

    /**
     * 取得二叉树的深度.
     *
     * @return the depth
     */
    public int getDepth() {

        return Node.getDepth(root);
    }

    /**
     * 获取二叉树的节点数
     *
     * @return
     */
    public int counter() {

        return Node.counter(root);
    }

    /**
     * 广度优先算法
     * 从上层到下层遍历二叉树
     * @param node
     */
    public void traverseByLevelFromTop(Node<T> node) {
        if (node == null) {
            return;
        }

        // 用队列实现二叉树的层次遍历
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.addLast(node);
        int inCount = 1;
        int outCount = 0;
        Node curNode = null;
        while (!queue.isEmpty()) {

            curNode = queue.pollFirst();
            System.out.print(curNode.value + " ");
            outCount++;

            if (curNode.left != null) {
                queue.addLast(curNode.left);
            }

            if (curNode.right != null) {
                queue.addLast(curNode.right);
            }

            //用inCount记录某层有多少个元素，outCount记录当前输出了多少个元素；当inCount==outCount时，就说明某层元素已经完全输出，此时应该换行(outCount清零)
            if (outCount == inCount) {
                System.out.println();
                // 当第K层元素全部出队（并已将各自左右孩子入队）后，队列里面刚好存放了第K+1层的所有元素，不多不少，所以有：inCount = queue.size();
                inCount = queue.size();
                outCount = 0;
            }
        }
    }

    public void printTree() {
        printSubtree(root);
    }

    public void printSubtree(Node node) {
        if (node.right != null) {
            printTree(node.right, true, "");
        }
        printNodeValue(node);
        if (node.left != null) {
            printTree(node.left, false, "");
        }
    }

    private void printNodeValue(Node node) {
        if (node.value == null) {
            System.out.print("<null>");
        } else {
            System.out.print(node.value.toString());
        }
        System.out.println();
    }

    private void printTree(Node node, boolean isRight, String indent) {
        if (node.right != null) {
            printTree(node.right, true, indent + (isRight ? "        " : " |      "));
        }
        System.out.print(indent);
        if (isRight) {
            System.out.print(" /");
        } else {
            System.out.print(" \\");
        }
        System.out.print("----- ");
        printNodeValue(node);
        if (node.left != null) {
            printTree(node.left, false, indent + (isRight ? " |      " : "        "));
        }
    }

    public static void main(String[] args) {
        Node<String> k = new Node<String>("K");
        Node<String> j = new Node<String>("J");
        Node<String> i = new Node<String>("I");
        Node<String> h = new Node<String>("H");
        Node<String> g = new Node<String>("G");
        Node<String> f = new Node<String>("F");
        Node<String> d = new Node<String>("D");
        Node<String> e = new Node<String>("E");
        Node<String> b = new Node<String>("B");
        Node<String> c = new Node<String>("C");
        Node<String> root = new Node<String>("A",b,c);
        c.left = d;
        c.right = f;
        d.right = e;
        d.left = g;
        f.right = h;
        f.left = i;
        h.right = j;
        b.left = k;
        Tree<String> tree = new Tree<String>(root);
//        tree.preorder(tree.root);
//        System.out.println(tree.getDepth());
//        tree.traverseByLevelFromTop(tree.root);
        tree.printTree();
    }
}
