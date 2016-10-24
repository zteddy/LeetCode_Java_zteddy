/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
	int sum = 0;
	public void preorder(TreeNode n, int x){   //use x to mark whether it is a left leaf: 0 for root, 1 for left leaf, 2 for right leaf
		if(n != null){
			if(n.left == null && n.right == null && x == 1) sum += n.val;
			if(n.left != null) preorder(n.left, 1);
			if(n.right != null) preorder(n.right, 2);
 		}
	}
    public int sumOfLeftLeaves(TreeNode root) {
    	preorder(root, 0);
    	return sum;
    }
}

//TODO None
