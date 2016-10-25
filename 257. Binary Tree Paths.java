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
	List<String> result = new LinkedList<>();
	public void DFS(TreeNode n, String currentpath){

		if(n.left == null && n.right == null){
			currentpath += n.val;
			result.add(currentpath);
		}
		currentpath += n.val+"->";
		if(n.left != null) DFS(n.left, currentpath);
		if(n.right != null) DFS(n.right, currentpath);
	}
    public List<String> binaryTreePaths(TreeNode root) {
    	if(root == null) return result;
    	String a = new String();
    	DFS(root, a);
    	return result;
    }
}

/*More concise
public List<String> binaryTreePaths(TreeNode root) {
    List<String> answer = new ArrayList<String>();
    if (root != null) searchBT(root, "", answer);
    return answer;
}
private void searchBT(TreeNode root, String path, List<String> answer) {
    if (root.left == null && root.right == null) answer.add(path + root.val);
    if (root.left != null) searchBT(root.left, path + root.val + "->", answer);
    if (root.right != null) searchBT(root.right, path + root.val + "->", answer);
}
*/
