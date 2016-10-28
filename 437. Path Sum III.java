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
	public int fromThisSum(TreeNode n, int sum){
		int count = 0;
		if(n == null) return 0;
		sum = sum - n.val;
		if(sum == 0) count++;
		return count+fromThisSum(n.right,sum)+fromThisSum(n.left,sum);
	}
    public int pathSum(TreeNode root, int sum) {
    	Queue<TreeNode> q = new LinkedList<>();
    	int count = 0;
    	if(root == null) return count;
    	q.offer(root);
    	while(!q.isEmpty()){
    		TreeNode temp = q.poll();
    		count += fromThisSum(temp,sum);
    		if(temp.left != null) q.offer(temp.left);
    		if(temp.right != null) q.offer(temp.right);
    	}
    	return count;
    }
}

/*More concise
public class Solution {
    public int pathSum(TreeNode root, int sum) {
        int count = findPathWithRoot(root, sum);
        if (root != null) count += pathSum(root.left, sum) + pathSum(root.right, sum); //add case when root is excluded.
        return count;
    }

    private int findPathWithRoot(TreeNode root, int sum) { //Must contain root.val when calculating sum.
        if (root == null) return 0;
        int count = 0;
        if (root.val == sum) count = 1;

        return count + findPathWithRoot(root.left, sum - root.val) + findPathWithRoot(root.right, sum - root.val);
    }
}
*/

/*Using Backtracking
public int pathSum(TreeNode root, int sum) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);  //Default sum = 0 has one count
    return backtrack(root, 0, sum, map);
}
//BackTrack one pass
public int backtrack(TreeNode root, int sum, int target, Map<Integer, Integer> map){
    if(root == null)
        return 0;
    sum += root.val;
    int res = map.getOrDefault(sum - target, 0);    //See if there is a subarray sum equals to target
    map.put(sum, map.getOrDefault(sum, 0)+1);
    //Extend to left and right child
    res += backtrack(root.left, sum, target, map) + backtrack(root.right, sum, target, map);
    map.put(sum, map.get(sum)-1);   //Remove the current node so it wont affect other path
    return res;
}
*/

/*Using Hashmap

for each parent node in the tree, we have 2 choices:
1. include it in the path to reach sum.
2. not include it in the path to reach sum.

for each child node in the tree, we have 2 choices:
1. take what your parent left you.
2. start from yourself to form the path.

one little thing to be careful:
every node in the tree can only try to be the start point once.

for example, When we try to start with node 1, node 3, as a child, could choose to start by itself.
             Later when we try to start with 2, node 3, still as a child,
             could choose to start by itself again, but we don't want to add the count to result again.
     1
      \
       2
        \
         3


public class Solution {
    int target;
    Set<TreeNode> visited;
    public int pathSum(TreeNode root, int sum) {
        target = sum;
        visited = new HashSet<TreeNode>();  // to store the nodes that have already tried to start path by themselves.
        return pathSumHelper(root, sum, false);
    }

    public int pathSumHelper(TreeNode root, int sum, boolean hasParent) {
        if(root == null) return 0;
        //the hasParent flag is used to handle the case when parent path sum is 0.
        //in this case we still want to explore the current node.
        if(sum == target && visited.contains(root) && !hasParent) return 0;
        if(sum == target && !hasParent) visited.add(root);
        int count = (root.val == sum)?1:0;
        count += pathSumHelper(root.left, sum - root.val, true);
        count += pathSumHelper(root.right, sum - root.val, true);
        count += pathSumHelper(root.left, target , false);
        count += pathSumHelper(root.right, target, false);
        return count;
    }
}
*/
