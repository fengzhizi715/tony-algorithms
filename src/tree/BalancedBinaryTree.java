package tree;

/**
 * 平衡二叉树（Balanced Binary Tree）又被称为AVL树（有别于AVL算法），
 * 且具有以下性质：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
 * Created by tony on 16/9/10.
 */
public class BalancedBinaryTree extends BinarySearchTree {

    /**
     * 判断是否平衡二叉树
     * @param node
     * @return
     */
    public boolean isBalance(Node node){
        if (node==null)
            return true;
        int dis = getDepth(node.left) - getDepth(node.right);

        if (dis>1 || dis<-1 )
            return false;
        else
            return isBalance(node.left) && isBalance(node.right);
    }
}
