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

    public TreeNode invertTree(TreeNode root) {

    	Queue<TreeNode> q = new LinkedList<>();
    	TreeNode current;
    	TreeNode temp;
    	if(root == null) return root;
    	q.offer(root);

    	while(!q.isEmpty()){
    		int size = q.size();
    		for(int i = 0; i < size; i++){
    			current = q.poll();
    			temp = current.left;
    			current.left = current.right;
    			current.right = temp;
    			if(current.left != null) q.offer(current.left);
    			if(current.right != null) q.offer(current.right);
    		}
    	}
    	return root;
    }
}

/*Recursive version
public TreeNode invertTree(TreeNode root) {
    if (root == null) {
        return null;
    }
    TreeNode right = invertTree(root.right);
    TreeNode left = invertTree(root.left);
    root.left = right;
    root.right = left;
    return root;
}
*/
