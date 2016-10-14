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

    public Node<T> deleteT(T x) {

        //找到该节点
        Node<T> cur = search(x);
        if (cur != null) {
            return remove(root, x);
        }

        return null;
    }

    /**
     * remove: 删除avl树中的节点
     * @param node    要删除的节点所在的根节点
     * @param value   要删除的value
     * @return
     * AVLNode  返回类型
     */
    private Node<T> remove(Node<T> node, T value) {

        int compareResult = value.compareTo(node.value);
        if (compareResult < 0) {        // 待删除的节点在"node的左子树"中
            node.left = remove(node.left, value);
            // 删除节点后，若AVL树失去平衡，则进行相应的调节。
            if (!isBalance(node)) {  // 此时, 右子树的深度 > 左子树的深度
                Node<T> r =  node.right;
                if (Node.getDepth(r.left) > Node.getDepth(r.right))
                    node = doubleWithRightChild(node);
                else
                    node = rotateWithRightChild(node);
            }
        } else if (compareResult > 0) {    // 待删除的节点在"node的右子树"中
            node.right = remove(node.right, value);
            // 删除节点后，若AVL树失去平衡，则进行相应的调节。
            if (!isBalance(node)) { // 此时, 左子树的深度 > 右子树的深度
                Node<T> l =  node.left;
                if (Node.getDepth(l.right) > Node.getDepth(l.left))
                    node = doubleWithLeftChild(node);
                else
                    node = rotateWithLeftChild(node);
            }
        } else {    // node是对应要删除的节点。
            // node的左右孩子都非空
            if (node.hasLeftChild() && node.hasRightChild()) {
                if (Node.getDepth(node.left) > Node.getDepth(node.right)) {
                    // 如果node的左子树比右子树高；
                    // 则(01)找出node的左子树中的最大节点
                    //   (02)将该最大节点的值赋值给node。
                    //   (03)删除该最大节点。
                    // 这类似于用"node的左子树中最大节点"做"node"的替身；
                    // 采用这种方式的好处是：删除"node的左子树中最大节点"之后，AVL树仍然是平衡的。
                    T max = max(node.left);
                    node.value = max;
                    node.left = remove(node.left, max);
                } else {
                    // 如果tree的左子树不比右子树高(即它们相等，或右子树比左子树高1)
                    // 则(01)找出node的右子树中的最小节点
                    //   (02)将该最小节点的值赋值给node。
                    //   (03)删除该最小节点。
                    // 这类似于用"node的右子树中最小节点"做"node"的替身；
                    // 采用这种方式的好处是：删除"node的右子树中最小节点"之后，AVL树仍然是平衡的。
                    T min = min(node.right);
                    node.value = min;
                    node.right = remove(node.right, min);
                }
            } else {
                Node<T> tmp = node;
                node = (node.left!=null) ? node.left : node.right;
                tmp = null;
            }
        }

        return node;
    }

    /**
     * 带左子树旋转,适用于LL型
     * 插入或删除一个节点后，根节点的左子树的左子树还有非空子节点，导致"根的左子树的高度"比"根的右子树的高度"大2，导致AVL树失去了平衡。
     * @param k2
     * @return
     */
    private Node<T> rotateWithLeftChild(Node<T> k2) {
        Node<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    /**
     * 带右子树旋转，适用于RR型
     * 插入或删除一个节点后，根节点的右子树的右子树还有非空子节点，导致"根的右子树的高度"比"根的左子树的高度"大2，导致AVL树失去了平衡。
     * @param k1
     * @return
     */
    private Node<T> rotateWithRightChild(Node<T> k1) {
        Node<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    /**
     * 双旋转，适用于LR型
     * 插入或删除一个节点后，根节点的左子树的右子树还有非空子节点，导致"根的左子树的高度"比"根的右子树的高度"大2，导致AVL树失去了平衡。
     * @param k3
     * @return
     */
    private Node<T> doubleWithLeftChild(Node<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     * 双旋转,适用于RL型
     * 插入或删除一个节点后，根节点的右子树的左子树还有非空子节点，导致"根的右子树的高度"比"根的左子树的高度"大2，导致AVL树失去了平衡。
     * @param k1
     * @return
     */
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
//        t.printTree();

        t.deleteT(195);
        t.printTree();
        System.out.println(t.isBalance(t.root));
    }
}
