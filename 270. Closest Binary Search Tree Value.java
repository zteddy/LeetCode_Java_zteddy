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
	public int inderOrderWalke(TreeNode n, int closest, double target){
		if(n != null){
			if(Math.abs(n.val-target) < Math.abs(closest -target))
				closest = n.val;
			int closest1 = inderOrderWalke(n.left, closest, target);
			int closest2 = inderOrderWalke(n.right, closest, target);
			if(Math.abs(closest1-target) < Math.abs(closest -target))
				closest = closest1;
			if(Math.abs(closest2-target) < Math.abs(closest -target))
				closest = closest2;

		}
		return closest;
	}


    public int closestValue(TreeNode root, double target) {
    	TreeNode now = root;
    	while(now != null){
    		if(target > now.val){
    			if(now.right != null &&  target >= now.right.val )
    				now = now.right;
    			else if(now.right != null && target < now.right.val)
    				return inderOrderWalke(now.right, now.val, target);
    			else if(now.right == null) return now.val;
    		}
    		if(target < now.val){
    			if(now.left != null &&  target <= now.left.val )
    				now = now.left;
    			else if(now.left != null && target > now.left.val)
    				return inderOrderWalke(now.left, now.val, target);
    			else if(now.left == null) return now.val;
    		}
    		else return now.val;
    	}
    	return 0;
    }
}

//I did it too complex.

/*More simple solution
public int closestValue(TreeNode root, double target) {
    int closestVal = root.val;
    while(root != null){
        //update closestVal if the current value is closer to target
        closestVal = (Math.abs(target - root.val) < Math.abs(target - closestVal))? root.val : closestVal;
        if(closestVal == target){   //already find the best result
            return closestVal;
        }
        root = (root.val > target)? root.left: root.right;   //binary search
    }
    return closestVal;
}
*/
