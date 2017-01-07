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
	public TreeNode flattenTree(TreeNode t){
		if(t == null) return null;
		if(t.left == null && t.right == null) return t;
		if(t.left != null && t.right == null){
			t.right = t.left;
			t.left = null;
			TreeNode rr = flattenTree(t.right);
			return rr;
		}
		if(t.left == null && t.right !=  null){
			TreeNode rr = flattenTree(t.right);
			return rr;
		}
		if(t.left != null && t.right != null){
			TreeNode lr = flattenTree(t.left);
			TreeNode templ = t.left;
			TreeNode tempr = t.right;
			t.left = null;
			t.right = templ;
			lr.right = tempr;
			TreeNode rr = flattenTree(tempr);
			return rr;
		}
		return null;
	}
    public void flatten(TreeNode root) {
    	TreeNode temp = flattenTree(root);
    }
}

/*Using preorder
I am basically storing the last visited pre-order traversal node in a static "lastVisited" TreeNode, and re-assigning its children.
Can my algorithm be improved so that we don't need that static variable, and all is handled by the recursive algorithm.

private static TreeNode lastVisited = null;

public static void flattenHelper(TreeNode root) {
    if(root == null)
        return;

    TreeNode savedRight = root.right;
    if(lastVisited != null) {
        lastVisited.left = null;
        lastVisited.right = root;
    }
    lastVisited = root;

    flattenHelper(root.left);
    flattenHelper(savedRight);
}
*/

/*Using postorder
private TreeNode prev = null;

public void flatten(TreeNode root) {
    if (root == null)
        return;
    flatten(root.right);
    flatten(root.left);
    root.right = prev;
    root.left = null;
    prev = root;
}
*/

/*Iterative
class Solution {
public:
    void flatten(TreeNode *root) {
		TreeNode*now = root;
		while (now)
		{
			if(now->left)
			{
                //Find current node's prenode that links to current node's right subtree
				TreeNode* pre = now->left;
				while(pre->right)
				{
					pre = pre->right;
				}
				pre->right = now->right;
                //Use current node's left subtree to replace its right subtree(original right
                //subtree is already linked by current node's prenode
				now->right = now->left;
				now->left = NULL;
			}
			now = now->right;
		}
    }
};
*/

/*Using stack DFS
It is DFS so u need a stack. Dont forget to set the left child to null, or u'll get TLE. (tricky!)

public void flatten(TreeNode root) {
    if (root == null) return;
    Stack<TreeNode> stk = new Stack<TreeNode>();
    stk.push(root);
    while (!stk.isEmpty()){
        TreeNode curr = stk.pop();
        if (curr.right!=null)
             stk.push(curr.right);
        if (curr.left!=null)
             stk.push(curr.left);
        if (!stk.isEmpty())
             curr.right = stk.peek();
        curr.left = null;  // dont forget this!!
    }
}

The wikipedia link mentioned also says "More broadly, in-place means that the algorithm does not use extra space for manipulating the input but may require a small though nonconstant extra space for its operation. Usually, this space is O(log n), though sometimes anything in o(n) is allowed."

I think that means using the stack is still in place because the stack will use O(log n) space.
*/
