/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<Integer> rightSideView(TreeNode root) {

        // List<List<Integer>> layer = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        List<Integer> result = new ArrayList<>();

        if(root == null) return result;

        q.offer(root);
        while(!q.isEmpty()){
            int levelSize = q.size();
            for(int i = 0; i < levelSize; i++){
                TreeNode temp = q.poll();
                if(i == levelSize-1) result.add(temp.val);
                if(temp.left != null) q.offer(temp.left);
                if(temp.right != null) q.offer(temp.right);
            }
        }

        return result;

    }
}




/*Using recursive
The core idea of this algorithm:

1. Each depth of the tree only select one node.
2. View depth is current size of result list.

View depth is current size of result list.
Here is the code:

public class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        rightView(root, result, 0);
        return result;
    }

    public void rightView(TreeNode curr, List<Integer> result, int currDepth){
        if(curr == null){
            return;
        }
        if(currDepth == result.size()){
            result.add(curr.val);
        }

        rightView(curr.right, result, currDepth + 1);
        rightView(curr.left, result, currDepth + 1);

    }
}
*/
