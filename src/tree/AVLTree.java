package tree;

/**
 * 平衡二叉树（Balanced Binary Tree）又被称为AVL树（有别于AVL算法），
 * 且具有以下性质：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
 * Created by tony on 16/9/10.
 */
public class AVLTree<T extends Comparable<T>> extends SelfBalancingBinarySearchTree {

//    public Node<T> insertAVLNode(T t) {
//
//        Node newNode = super.insert(t);
//
//        if (newNode == null) return null;
//
//        rebalance((newNode);
//        return newNode;
//    }
//
//    private void rebalance(Node<T> node) {
//        while (node != null) {
//
//            Node parent = node.parent;
//
//            int leftHeight = (node.left == null) ? -1 : ((Node) node.left).height;
//            int rightHeight = (node.right == null) ? -1 : ((Node) node.right).height;
//            int nodeBalance = rightHeight - leftHeight;
//            // rebalance (-2 means left subtree outgrow, 2 means right subtree)
//            if (nodeBalance == 2) {
//                if (node.right.right != null) {
//                    node = (Node)avlRotateLeft(node);
//                    break;
//                } else {
//                    node = (Node)doubleRotateRightLeft(node);
//                    break;
//                }
//            } else if (nodeBalance == -2) {
//                if (node.left.left != null) {
//                    node = (Node)avlRotateRight(node);
//                    break;
//                } else {
//                    node = (Node)doubleRotateLeftRight(node);
//                    break;
//                }
//            } else {
//                updateHeight(node);
//            }
//
//            node = parent;
//        }
//    }
//
//    /**
//     * Rotates to left side.
//     */
//    private Node avlRotateLeft(Node node) {
//        Node temp = super.rotateLeft(node);
//
//        updateHeight((Node)temp.left);
//        updateHeight((Node)temp);
//        return temp;
//    }
//
//    /**
//     * Rotates to right side.
//     */
//    private Node avlRotateRight(Node node) {
//        Node temp = super.rotateRight(node);
//
//        updateHeight((Node)temp.right);
//        updateHeight((Node)temp);
//        return temp;
//    }
//
//    /**
//     * Take right child and rotate it to the right side first and then rotate
//     * node to the left side.
//     */
//    protected Node doubleRotateRightLeft(Node node) {
//        node.right = avlRotateRight(node.right);
//        return avlRotateLeft(node);
//    }
//
//    /**
//     * Take right child and rotate it to the right side first and then rotate
//     * node to the left side.
//     */
//    protected Node doubleRotateLeftRight(Node node) {
//        node.left = avlRotateLeft(node.left);
//        return avlRotateRight(node);
//    }
//
//    /**
//     * Updates height and balance of the node.
//     *
//     * @param node Node for which height and balance must be updated.
//     */
//    private static final void updateHeight(Node node) {
//        int leftHeight = (node.left == null) ? -1 : ((Node) node.left).height;
//        int rightHeight = (node.right == null) ? -1 : ((Node) node.right).height;
//        node.height = 1 + Math.max(leftHeight, rightHeight);
//    }
//
//    /**
//     * 判断是否平衡二叉树
//     * @param node
//     * @return
//     */
//    public boolean isBalance(Node<T> node) {
//        if (node == null)
//            return true;
//        int dis = Node.getDepth(node.left) - Node.getDepth(node.right);
//
//        return Math.abs(dis) <= 1 ? isBalance(node.left) && isBalance(node.right) : false;
//    }
//
//    public static void main(String[] args) {
//
//        AVLTree avlTree = new AVLTree();
//
//        avlTree.insertAVLNode(10);
//        avlTree.insertAVLNode(13);
//        avlTree.insertAVLNode(6);
//        avlTree.insertAVLNode(5);
//        avlTree.insertAVLNode(12);
//        avlTree.insertAVLNode(100);
//        avlTree.insertAVLNode(7);
//        avlTree.insertAVLNode(10);
//        avlTree.insertAVLNode(79);
//        avlTree.insertAVLNode(8);
//        avlTree.insertAVLNode(11);
//        avlTree.insertAVLNode(110);
//        avlTree.insertAVLNode(14);
//        avlTree.printTree();
//    }
}
