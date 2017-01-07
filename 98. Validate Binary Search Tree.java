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
    public boolean isValidBST(TreeNode root) {
    	if(root == null) return true;
    	boolean result = false;
    	if(root.right == null && root.left == null) //只要访问类中变量一定要先考虑null
    		result = true;
    	else if(root.right == null){
    		TreeNode leftmax = root.left;
    		while(leftmax.right != null) leftmax = leftmax.right;
    		int leftmaxval = leftmax.val;
    		if(leftmaxval < root.val)
    			result = true;
    	}
    	else if(root.left == null){
    		TreeNode rightmin = root.right;
    		while(rightmin.left != null) rightmin = rightmin.left;
    		int rightminval = rightmin.val;
    		if(rightminval > root.val)
    			result = true;
    	}     //这里的括号嵌套一开始有问题
    	else{
    		TreeNode leftmax = root.left;
    		while(leftmax.right != null) leftmax = leftmax.right;
    		int leftmaxval = leftmax.val;
    		TreeNode rightmin = root.right;
    		while(rightmin.left != null) rightmin = rightmin.left;
    		int rightminval = rightmin.val;
    		if(leftmaxval < root.val && rightminval > root.val)
    			result = true;
    	}
    	return result && isValidBST(root.right) && isValidBST(root.left);
    }
}

/*Another recursive
public class Solution {
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode root, long minVal, long maxVal) {
        if (root == null) return true;
        if (root.val >= maxVal || root.val <= minVal) return false;
        return isValidBST(root.left, minVal, root.val) && isValidBST(root.right, root.val, maxVal);
    }
}
Basically what I am doing is recursively iterating over the tree while defining interval <minVal, maxVal> for each node which value must fit in.

We can use Object Integer and null pointer to avoid the corner cases (when node has value Integer.MIN_VALUE or Integer.MAX_VALUE ).

private boolean help(TreeNode p, Integer low, Integer high) {
    if (p == null) return true;
    return (low == null || p.val > low) && (high == null || p.val < high) && help(p.left, low, p.val) && help(p.right, p.val, high);
}

public boolean isValidBST(TreeNode root) {
    return help(root, null, null);
}
*/

/*Using iterative inorder
public boolean isValidBST(TreeNode root) {
   if (root == null) return true;
   Stack<TreeNode> stack = new Stack<>();
   TreeNode pre = null;
   while (root != null || !stack.isEmpty()) {
      while (root != null) {
         stack.push(root);
         root = root.left;
      }
      root = stack.pop();
      if(pre != null && root.val <= pre.val) return false;
      pre = root;
      root = root.right;
   }
   return true;
}
*/
