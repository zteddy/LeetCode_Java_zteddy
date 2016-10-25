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

		//if(l == -1 || r == -1|| Math.abs(l-r) > 1) return -1;

		return Math.max(l,r)+1;
	}

    public int maxDepth(TreeNode root) {
    	if(root == null) return 0;
    	return height(root);


    }
}

/* More concise
public class Solution {
    public int maxDepth(TreeNode root) {
        return root==null? 0 : Math.max(maxDepth(root.left), maxDepth(root.right))+1;
    }
}
*/

/*Iterative version
public int maxDepth(TreeNode root) {
    if(root == null) {
        return 0;
    }

    Stack<TreeNode> stack = new Stack<>();
    Stack<Integer> value = new Stack<>();
    stack.push(root);
    value.push(1);
    int max = 0;
    while(!stack.isEmpty()) {
        TreeNode node = stack.pop();
        int temp = value.pop();
        max = Math.max(temp, max);
        if(node.left != null) {
            stack.push(node.left);
            value.push(temp+1);
        }
        if(node.right != null) {
            stack.push(node.right);
            value.push(temp+1);
        }
    }
    return max;
}
*/
