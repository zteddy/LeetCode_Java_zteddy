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

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

    }
}

//WA

//Genius recursive solution
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
	if(root.val > p.val && root.val > q.val) return lowestCommonAncestor(root.left, p, q);
	else if(root.val < p.val && root.val < q.val) return lowestCommonAncestor(root.right, p, q);
	else return root;
}


//Iterative version
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    while ((root.val - (long)p.val) * (root.val - (long)q.val)) > 0)
    //注意相减有可能会溢出
        root = p.val < root.val ? root.left : root.right;
    return root;
}


//One-line Java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    return (root.val - p.val) * (root.val - q.val) < 1 ? root :
           lowestCommonAncestor(p.val < root.val ? root.left : root.right, p, q);
}

