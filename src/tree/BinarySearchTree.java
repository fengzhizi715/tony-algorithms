package tree;

/**
 * 二叉查找树、二叉搜索树、二叉排序树,简称BST
 * 1) 若左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 * 2) 若右子树不空，则右子树上所有结点的值均大于它的根结点的值；
 * 3) 左、右子树也分别为BST；
 * 4) 没有键值相等的结点。
 * Created by tony on 16/8/27.
 */
public class BinarySearchTree<T extends Comparable<T>> {

    private Node<T> root; // tree root
    private int count;	// tree element counts

    /**
     * BST的内部节点类
     */
    private static class Node<E>{

        E value; //元素对象
        Node<E> parent; //父节点
        Node<E> left; //左孩子节点
        Node<E> right; //右孩子节点

        public Node(E value, Node<E> parent, Node<E> left, Node<E> right) {
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public String toString() {
            String leftkey = (left == null ? "" : String
                    .valueOf(left.value));
            String rightkey = (right == null ? "" : String
                    .valueOf(right.value));
            return "(" + leftkey + " , " + value + " , " + rightkey + ")";
        }
    }

    /**
     * 插入元素
     * @param t 待插入元素
     * @return 插入成功返回true, 反之返回false
     */
    public boolean insert(T t){
        if (root == null){ //若为空树
            root = new Node<T>(t, null, null, null);
            return true;
        }
        Node<T> newNode = new Node<T>(t, null, null, null);
        Node<T> pointer = root;
        while(true){
            if (newNode.value.compareTo(pointer.value) > 0){
                if (pointer.right == null){ //插入右边
                    newNode.parent = pointer;
                    pointer.right = newNode;
                    count++;
                    return true;
                } else{
                    pointer = pointer.right;
                }
            } else if (newNode.value.compareTo(pointer.value) < 0){
                if (pointer.left == null){ //插入左边
                    newNode.parent = pointer;
                    pointer.left = newNode;
                    count++;
                    return true;
                } else{
                    pointer = pointer.left;
                }
            } else { //相等了,无法插入该树
                return false;
            }
        }
    }

    /**
     * 查找元素
     * @param t 待查找元素
     * @return 对应元素或null
     */
    public T get(T t) {
        Node<T> n = getN(t);
        return n == null? null : n.value;
    }

    /**
     * 查找节点
     * @param t 待查找元素
     * @return 元素对应节点或null
     */
    private Node<T> getN(T t) {
        Node<T> cur = root;
        while (cur != null){
            if (cur.value.compareTo(t) < 0){ //右边子树找
                cur = cur.right;
            } else if(cur.value.compareTo(t) > 0){ //左边子树找
                cur = cur.left;
            } else{ //找到该节点
                break;
            }
        }
        return cur;
    }

    /**
     * 获取某节点为根的树的最小元素
     */
    public T min(Node<T> n){
        Node<T> min = minN(n);
        return min == null ? null : min.value;
    }

    /**
     * 获取某节点为根的树的最小节点
     * @param n 树根节点
     * @return 该子树最小节点
     */
    private Node<T> minN(Node<T> n){
        Node<T> min = n;
        while (min != null && min.left != null){
            min = min.left;
        }
        return min;
    }

    /**
     * 获取某节点为根的树的最大元素
     * @return 最大元素, 没有返回null
     */
    public T max(Node<T> n){
        Node<T> max = maxN(n);
        return max == null ? null : max.value;
    }

    /**
     * 获取某节点为根的树的最大节点
     */
    private Node<T> maxN(Node<T> n){
        Node<T> max = n;
        while (max != null && max.right != null){
            max = max.right;
        }
        return max;
    }

    /**
     * 中序遍历
     */
    public void leftRootRight(){
        printLRR(root);
    }

    /**
     * 中序遍历打印元素
     */
    private void printLRR(Node<T> node) {
        if (node != null){
            printLRR(node.left);
            System.out.println(node.value);
            printLRR(node.right);
        }
    }

    public static void main(String[] args) {

        BinarySearchTree bsTree = new BinarySearchTree();

        bsTree.insert(10);
        bsTree.insert(10);
        bsTree.insert(11);
        bsTree.insert(1);
        bsTree.insert(12);
        bsTree.insert(100);

        bsTree.leftRootRight();
    }
}
