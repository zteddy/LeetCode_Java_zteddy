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
    public List<List<Integer>> verticalOrder(TreeNode root) {

        if(root == null) return new LinkedList<>();

        Queue<TreeNode> bfs = new LinkedList<>();
        Queue<Integer> bfsDegree = new LinkedList<>();
        Map<Integer,List<Integer>> hm = new HashMap<>();


        bfs.offer(root);
        bfsDegree.offer(0);

        int min = 0;
        int max = 0;



        while(!bfs.isEmpty()){
            TreeNode temp = bfs.poll();
            int degreeNow = bfsDegree.poll();

            if(hm.containsKey(degreeNow)){
                hm.get(degreeNow).add(temp.val);
            }
            else{
                List<Integer> t = new LinkedList<>();
                t.add(temp.val);
                hm.put(degreeNow,t);
            }

            if(temp.left != null){
                bfs.offer(temp.left);
                bfsDegree.offer(degreeNow-1);
                if(degreeNow-1 < min) min = degreeNow-1;
            }

            if(temp.right != null){
                bfs.offer(temp.right);
                bfsDegree.offer(degreeNow+1);
                if(degreeNow+1 > max) max = degreeNow+1;
            }

        }


        List<List<Integer>> result = new LinkedList<>();

        for(int i = min; i <= max; i++){
            result.add(hm.get(i));
        }

        return result;

    }
}
