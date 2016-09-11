package tree;

/**
 * 二叉查找树、二叉搜索树、二叉排序树,简称BST
 * 1) 若左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 * 2) 若右子树不空，则右子树上所有结点的值均大于它的根结点的值；
 * 3) 左、右子树也分别为BST；
 * 4) 没有键值相等的结点。
 *
 * Created by tony on 16/8/27.
 */
public class BinarySearchTree<T extends Comparable<T>> {

    private Node<T> root; // tree root
    private int count;	  // tree element counts

    /**
     * BST的内部节点类
     */
    public static class Node<E>{

        E value; //元素对象
        Node<E> parent; //父节点
        Node<E> left;   //左孩子节点
        Node<E> right;  //右孩子节点

        public Node(E value, Node<E> parent, Node<E> left, Node<E> right) {
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

    /**
     * 获取根节点
     * @return
     */
    public Node<T> getRoot() {
        return root;
    }

    /**
     * 返回BST的节点数
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * 取得二叉树的深度
     * @param node
     * @return
     */
    public int getDepth(Node node){

        if (node == null) {
            return 0;
        }

        int leftDepth = getDepth(node.left);
        int rightDepth = getDepth(node.right);

        return leftDepth > rightDepth ? leftDepth + 1 : rightDepth + 1;
    }

    /**
     * 插入元素,如果该元素在tree中已经包含则无法插入该树
     * @param t 待插入元素
     * @return 插入成功返回true, 反之返回false
     */
    public boolean insert(T t){
        if (root == null){ //若为空树, 插入的节点为root节点
            root = new Node<T>(t, null, null, null);
            count++;
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
     * @return 对应的节点或null
     */
    public Node<T> search(T t) {
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
     * 移除某元素
     * @param t 待删除元素
     * @return 删除成功返回true, 反之false
     */
    public boolean delete(T t){
        //找到该节点
        Node<T> cur = search(t);
        if (cur != null){
            if (doDelete(cur)){
                cur=null;
                count--;
                return true;
            }
        }
        return false;
    }

    /**
     * 执行删除操作
     * 删除可以分为三种情况:
     * 1.该节点为叶子节点，直接删除
     * 2.该节点有一个孩子，将其孩子接上其父节点
     * 3.该节点有2个孩子，先删除其右子树的最小元素(该元素最多只会有一个孩子)，将这个最小元素去替换要删除的节点
     * @param cur
     * @return
     */
    private boolean doDelete(Node<T> cur) {
        //该节点是否为根
        boolean isRoot = cur == root;

        //1.该节点为叶子节点, 直接将其父节点对应(左或右)孩子置空
        if (cur.left == null && cur.right == null){

            if (isRoot) return true;     //若树只有一个根节点

            if (cur == cur.parent.right) //该节点为父节点的右孩子
                cur.parent.right = null;
            else					     //该节点为父节点的左孩子
                cur.parent.left = null;
            return true;
        } else if(cur.left != null && cur.right != null){

            //2.该节点有2个孩子, 我们先找出一个替换节点(该节点的后继节点，后继节点没有则前驱节点)
            //找到其后继节点
            Node<T> replaceNode = locateNextN(cur);
            if (replaceNode == null) //若没有后继节点则用前驱节点
                replaceNode = locatePrevN(cur);
            doDelete(replaceNode);
            cur.value = replaceNode.value;
            return true;
        } else{ //3.该节点有一个孩子，将其孩子接上其父节点：
            Node<T> needLinkedNode = null;
            if (cur.left == null && cur.right != null){       //该节点有右孩子
                needLinkedNode = cur.right;
            } else if(cur.left != null && cur.right == null){ //该节点有左孩子
                needLinkedNode = cur.left;
            }

            if(isRoot){ //若该节点为根
                root = needLinkedNode;
                return true;
            }

            if (cur == cur.parent.right)  //该节点为父节点右孩子
                cur.parent.right = needLinkedNode;
            else
                cur.parent.left = needLinkedNode;
            return true;
        }
    }

    /**
     * 定位到前驱节点
     * @param cur 当前节点
     * @return 当前节点的前驱节点
     */
    private Node<T> locatePrevN(Node<T> cur){
        if (cur != null){
            //1.如果该节点左子树不会空，则其前驱为其左子树的最大元素
            if (cur.left != null) return maxN(cur.left);
            //2.该节点左子树为空, 则其前驱为：其祖先节点(递归), 且该祖先节点的右孩子也是其祖先节点
            //  通俗的说，一直忘上找找到左拐后那个节点;
            Node<T> p = cur.parent;
            while(p != null && cur == p.left){
                cur = p;
                p = p.parent;
            }
            return p == null ? null: p;
        }
        return null;
    }

    /**
     * 定位到当前节点的后继节点
     * @param cur 当前节点
     * @return 当前节点的后继节点
     */
    private Node<T> locateNextN(Node<T> cur) {
        if (cur == null) return null;
        //1.若其右子树不为空，那么其后继节点就是其右子树的最小元素
        if (cur.right != null) return minN(cur.right);
        //2.若为空，应该为其祖先节点(递归)，且该祖先节点的左孩子也是其祖先节点
        //  通俗的说，一直忘上找，找到右拐后那个节点;
        Node<T> p = cur.parent;
        while (p != null && cur == p.right){
            cur = p;
            p = p.parent;
        }
        return p;
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

    /**
     * 判断BST是否为空
     * @return
     */
    public boolean isEmpty() {
        return root == null;
    }

    public static void main(String[] args) {

        BinarySearchTree bsTree = new BinarySearchTree();

        bsTree.insert(10);
        bsTree.insert(10);
        bsTree.insert(11);
        bsTree.insert(1);
        bsTree.insert(12);
        bsTree.insert(100);
        System.out.println(bsTree.search(100).hasSubNode());
        System.out.println(bsTree.getDepth(bsTree.getRoot()));
//        System.out.println(bsTree.getCount());
//        bsTree.delete(11);
//        System.out.println(bsTree.search(11));
//        System.out.println(bsTree.getCount());
    }
}
