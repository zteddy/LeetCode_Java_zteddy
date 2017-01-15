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
	public TreeNode findMin(TreeNode t){
		if(t.left == null) return null;
		while(t.left.left != null){
			t = t.left;
		}
		return t;
	}

    public TreeNode deleteNode(TreeNode root, int key) {
    	TreeNode result;
    	if(root == null) return null;
    	result = root;
    	while(result != null && result.val != key){
    		if(key > result.val) result = result.right;
    		else result = result.left;
    	}
    	if(result == null) return root;
    	if(result.left == null && result.right == null) result = null; //引用机制紊乱
    	else if(result.left != null && result.right != null){
			TreeNode temp = findMin(result.right);
			if(temp == null){
				result.val = result.right.val;
				result.right = null;
			}
			else{
				result.val = temp.left.val;
				temp.left = null;    //注意引用机制
			}

		}
		else{
			result = (result.left == null? result.right : result.left);
		}

		if(root.val == key) return result;
		else return root;

    }
}

//WA引用机制紊乱


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
	public TreeNode findMin(TreeNode t){
		if(t == null) return null;
		while(t.left != null){
			t = t.left;
		}
		return t;
	}
    public TreeNode deleteNode(TreeNode root, int key) {
    	TreeNode temp;
    	if(root == null) return null;
    	else if(key < root.val) root.left = deleteNode(root.left, key);
    	else if(key > root.val) root.right = deleteNode(root.right, key);
    	else{
    		if(root.left != null && root.right != null){
    			temp = findMin(root.right);
    			root.val = temp.val;
    			root.right = deleteNode(root.right, root.val);
    		}
    		else{
    			temp = root;
    			if(root.left == null) root = root.right;
    			else if(root.right == null) root = root.left;
    		}
    	}
    	return root;
    }
}

//AC

/*Using iterative
    private TreeNode deleteRootNode(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root.right;
        }
        if (root.right == null) {
            return root.left;
        }
        TreeNode next = root.right;
        TreeNode pre = null;
        for(; next.left != null; pre = next, next = next.left);
        next.left = root.left;
        if(root.right != next) {
            pre.left = next.right;
            next.right = root.right;
        }
        return next;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode cur = root;
        TreeNode pre = null;
        while(cur != null && cur.val != key) {
            pre = cur;
            if (key < cur.val) {
                cur = cur.left;
            } else if (key > cur.val) {
                cur = cur.right;
            }
        }
        if (pre == null) {
            return deleteRootNode(cur);
        }
        if (pre.left == cur) {
            pre.left = deleteRootNode(cur);
        } else {
            pre.right = deleteRootNode(cur);
        }
        return root;
    }
Find the node to be removed and its parent using binary search, and then use deleteRootNode to delete the root node of the subtree and return the new root node. This idea is taken from https://discuss.leetcode.com/topic/67309/an-easy-understanding-o-h-time-o-1-space-java-solution.

I'd also like to share my thinkings of the other solutions I've seen.

There are many solutions that got high votes using recursive approach, including the ones from the Princeton's Algorithm and Data Structure book. Don't you notice that recursive approach always takes extra space? Why not consider the iterative approach first?
Some solutions swap the values instead of swapping the nodes. In reality, the value of a node could be more complicated than just a single integer, so copying the contents might take much more time than just copying the reference.
As for the case when both children of the node to be deleted are not null, I transplant the successor to replace the node to be deleted, which is a bit harder to implement than just transplant the left subtree of the node to the left child of its successor. The former way is used in many text books too. Why? My guess is that transplanting the successor can keep the height of the tree almost unchanged, while transplanting the whole left subtree could increase the height and thus making the tree more unbalanced.
*/

/*Using iterative2
If the node is found, delete the node.
We need a function deleteRoot to delete the root from a BST.

If root==null, then return null
If root.right==null, then return root.left
If root.right!=null, the the new root of the BST is root.right; And what we should do is to put root.left into this new BST. As all nodes in root.left is smaller than the new tree, we just need to find the left-most node.
public class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root==null || root.val==key) return deleteRoot(root);
        TreeNode p=root;

        while (true) { // search the node
            if (key>p.val) {
                if (p.right==null || p.right.val==key) {
                    p.right=deleteRoot(p.right);
                    break;
                }
                p=p.right;
            }
            else {
                if (p.left==null || p.left.val==key) {
                    p.left=deleteRoot(p.left);
                    break;
                }
                p=p.left;
            }
        }
        return root;
    }

    private TreeNode deleteRoot(TreeNode root) {
        if (root==null) return null;
        if (root.right==null) return root.left;
        TreeNode x=root.right; // root.right should be the new root
        while (x.left!=null) x=x.left; // find the left-most node
        x.left=root.left;
        return root.right;
    }
}
*/
