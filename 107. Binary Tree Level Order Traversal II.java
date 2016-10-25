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
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
    	Stack<List<Integer>> s = new Stack<>();
    	Queue<TreeNode> q = new LinkedList<>();
    	List<List<Integer>> result = new LinkedList<>();

    	if(root == null) return result;
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
    		s.add(templist);
    	}
    	while(!s.empty()){
    		result.add(s.pop());
    	}
    	return result;
    }
}

/*DFS solution
public class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> wrapList = new LinkedList<List<Integer>>();
        levelMaker(wrapList, root, 0);
        return wrapList;
    }

    public void levelMaker(List<List<Integer>> list, TreeNode root, int level) {
        if(root == null) return;
        if(level >= list.size()) {
            list.add(0, new LinkedList<Integer>());
        }
        levelMaker(list, root.left, level+1);
        levelMaker(list, root.right, level+1);
        list.get(list.size()-level-1).add(root.val);
    }
}
*/
