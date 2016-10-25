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
    public List<List<Integer>> levelOrder(TreeNode root) {
    	Queue<TreeNode> q = new LinkedList<>();
    	List<List<Integer>> result = new LinkedList<>();   //要这么定义！

    	if(root == null) return result;      //老是少打等号！！！
    	q.offer(root);
    	while(!q.isEmpty()){
    		int size = q.size();
    		List<Integer> templist = new LinkedList<>();
    		for(int i = 0; i < size; i++){
    			TreeNode temp = q.poll();
    			templist.add(temp.val);
    			if(temp.left != null) q.offer(temp.left);
    			if(temp.right != null) q.offer(temp.right);
    		}
    		result.add(templist);
    	}
    	return result;
    }
}

/*Using DFS
public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        levelHelper(res, root, 0);
        return res;
    }

    public void levelHelper(List<List<Integer>> res, TreeNode root, int height) {
        if (root == null) return;
        if (height >= res.size()) {
            res.add(new LinkedList<Integer>());
        }
        res.get(height).add(root.val);
        levelHelper(res, root.left, height+1);
        levelHelper(res, root.right, height+1);
    }
}
*/
