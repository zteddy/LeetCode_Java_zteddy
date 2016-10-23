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
    public int minDepth(TreeNode root) {
    	int min = 1;
    	TreeNode temp;
    	Queue<TreeNode> q = new LinkedList<>();
    	q.offer(root);
    	if(root == null) return 0;                //总是写错==和=
    	while(!q.isEmpty()){
    		int size = q.size();
    		for(int i = 0; i < size; i++){
    			temp = q.poll();
    			if(temp.left == null && temp.right == null) return min;
    			if(temp.left != null) q.offer(temp.left);
    			if(temp.right != null) q.offer(temp.right);
    		}
    		min++;
    	}
    	return min;
    }
}

/*Using recursive
public class Solution {
 public int minDepth(TreeNode root) {
     if(root == null) return 0;
     int left = minDepth(root.left);
     int right = minDepth(root.right);
     return (left == 0 || right == 0) ? left + right + 1: Math.min(left,right) + 1;

 }
}
*/

/*Using recursive2
public int minDepth(TreeNode root) {
    if (root == null)
        return 0;
    if (root.left != null && root.right != null)
        return Math.min(minDepth(root.left), minDepth(root.right))+1;
    else
        return Math.max(minDepth(root.left), minDepth(root.right))+1;
}
*/

