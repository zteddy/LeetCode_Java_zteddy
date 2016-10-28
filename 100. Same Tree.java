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
	//boolean result = true;
	public boolean inOrder(TreeNode a, TreeNode b){
		if(a == null && b == null){
			return true;
		}
		if(a == null || b == null){
			return false;
		}
		if(a.val == b.val){
			boolean r1 = inOrder(a.left,b.left);
			boolean r2 = inOrder(a.right,b.right);
			return r1 & r2;
		}
		return false;
	}
    public boolean isSameTree(TreeNode p, TreeNode q) {
    	return inOrder(p,q);

    }
}

/*More concise
public class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == q) return true;
        if ((p == null && q != null) || (p != null && q == null) || (p.val != q.val)) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
*/

