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
	public int height(TreeNode n){
		if(n.left == null && n.right == null) return 1;
		int l = 0;
		int r = 0;

		if(n.left != null) l = height(n.left);
		if(n.right != null) r = height(n.right);

		if(l == -1 || r == -1|| Math.abs(l-r) > 1) return -1;

		return Math.max(l,r)+1;
	}
    public boolean isBalanced(TreeNode root) {
    	if(root == null) return true;
    	return height(root) != -1;
    }
}

//TODO None
