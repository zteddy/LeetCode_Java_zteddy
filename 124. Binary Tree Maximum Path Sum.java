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
	int max = Integer.MIN_VALUE;
	public int maxSinglePath(TreeNode root, HashMap<TreeNode, Integer> DP) {
		if(DP.containsKey(root)) return DP.get(root);
		if(root == null) return 0;
		if(root.left == null && root.right == null) return root.val;
		int temptemp = Math.max(maxSinglePath(root.left, DP), maxSinglePath(root.right, DP));
		int temp = temptemp > 0? temptemp+root.val : root.val;
		DP.put(root, temp);
		return temp;
	}
	public void dfs(TreeNode root, HashMap<TreeNode, Integer> DP){
		if(root == null) return ;
		dfs(root.left, DP);
		dfs(root.right, DP);
		int temp = root.val;
		if(maxSinglePath(root.left, DP) >= 0) temp +=  maxSinglePath(root.left, DP);
		if(maxSinglePath(root.right, DP) >= 0) temp +=  maxSinglePath(root.right, DP);
		if(temp > max) max = temp;
	}

    public int maxPathSum(TreeNode root) {
    	HashMap<TreeNode, Integer> DP = new HashMap<>();
    	dfs(root, DP);
    	return max;
    }
}


/*More concise
Here's my ideas:

A path from start to end, goes up on the tree for 0 or more steps, then goes down for 0 or more steps. Once it goes down, it can't go up. Each path has a highest node, which is also the lowest common ancestor of all other nodes on the path.
A recursive method maxPathDown(TreeNode node) (1) computes the maximum path sum with highest node is the input node, update maximum if necessary (2) returns the maximum sum of the path that can be extended to input node's parent.
Code:

public class Solution {
    int maxValue;

    public int maxPathSum(TreeNode root) {
        maxValue = Integer.MIN_VALUE;
        maxPathDown(root);
        return maxValue;
    }

    private int maxPathDown(TreeNode node) {
        if (node == null) return 0;
        int left = Math.max(0, maxPathDown(node.left));
        int right = Math.max(0, maxPathDown(node.right));
        maxValue = Math.max(maxValue, left + right + node.val);
        return Math.max(left, right) + node.val;
    }
}
*/
