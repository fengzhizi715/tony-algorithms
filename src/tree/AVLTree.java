package tree;

/**
 * 平衡二叉树（Balanced Binary Tree）又被称为AVL树（有别于AVL算法），
 * 且具有以下性质：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
 * Created by tony on 16/9/10.
 */
public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    //在avl树中插入数据，重复数据复略
    public void insertT(T x) {
        root = insert(x, root);
    }

    private Node<T> insert(T x, Node<T> t) {
        if (t == null)
            return new Node<T>(x, null, null, null);

        int compareResult = x.compareTo(t.value);

        if (compareResult < 0) {
            t.left = insert(x, t.left);//将x插入左子树中
            if (!isBalance(t))//打破平衡wo
                if (x.compareTo(t.left.value) < 0)//LL型（左左型）
                    t = rotateWithLeftChild(t);
                else   //LR型（左右型）
                    t = doubleWithLeftChild(t);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right);//将x插入右子树中
            if (!isBalance(t))//打破平衡
                if (x.compareTo(t.right.value) > 0)//RR型（右右型）
                    t = rotateWithRightChild(t);
                else                           //RL型
                    t = doubleWithRightChild(t);
        } else
            ;  // 重复数据，什么也不做

        return t;
    }

    //带左子树旋转,适用于LL型
    private Node<T> rotateWithLeftChild(Node<T> k2) {
        Node<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    //带右子树旋转，适用于RR型
    private Node<T> rotateWithRightChild(Node<T> k1) {
        Node<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    //双旋转，适用于LR型
    private Node<T> doubleWithLeftChild(Node<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    //双旋转,适用于RL型
    private Node<T> doubleWithRightChild(Node<T> k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    public boolean isBalance(Node node) {
        if (node == null)
            return true;
        int dis = Node.getDepth(node.left) - Node.getDepth(node.right);
        return dis > 1 || dis < -1 ? false : isBalance(node.left) && isBalance(node.right);
    }

    public static void main(String[] args) {
        AVLTree<Integer> t = new AVLTree<Integer>();
        int NUMS = 200;
        int GAP = 17;
        for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
            t.insertT(i);
        t.printTree();
    }
}
