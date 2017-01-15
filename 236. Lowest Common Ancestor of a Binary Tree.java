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
	LinkedList<TreeNode> a = new LinkedList<>();
	LinkedList<TreeNode> b = new LinkedList<>();
	boolean x = true;
	public void preorder(TreeNode root, TreeNode t, LinkedList l){
		if(x && root !=null){
			l.addLast(root);
			if(root == t) x = false; //看清题目，比的是Node而不是val
			preorder(root.left,t,l);
			preorder(root.right,t,l);
			if(x) l.removeLast();
		}
	}
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    	x = true;
    	preorder(root,p,a);
    	x = true;
    	preorder(root,q,b);
    	TreeNode temp1 = new TreeNode(100);
    	TreeNode temp2 = root;
    	TreeNode result = root;
    	while(a.size() != 0 && b.size() != 0){
    		temp1 = a.getFirst();
    		temp2 = b.getFirst();
    		if(temp1 == temp2){
    			a.removeFirst();
    			b.removeFirst();
    			result = temp1;
    		}
    		else{
    			break;
    		}
    	}
    	return result;
    }
}



/*Using recursive
public class Solution {
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
	    if(root == null) return null;
	    if(root == p) return p;
	    if(root == q) return q;
	    TreeNode left = lowestCommonAncestor(root.left, p, q);
	    TreeNode right = lowestCommonAncestor(root.right, p, q);
	    if(left == null && right == null) return null;
	    if(left != null && right == null) return left;
	    if(left == null && right != null) return right;
	    return root;
	}
}
*/

/*Using stack
public class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        parent.put(root, null);
        stack.push(root);

        while (!parent.containsKey(p) || !parent.containsKey(q)) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                parent.put(node.left, node);
                stack.push(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                stack.push(node.right);
            }
        }
        Set<TreeNode> ancestors = new HashSet<>();
        while (p != null) {
            ancestors.add(p);
            p = parent.get(p);
        }
        while (!ancestors.contains(q))
            q = parent.get(q);
        return q;
    }
}
To find the lowest common ancestor, we need to find where is p and q and a way to track their ancestors. A parent pointer for each node found is good for the job. After we found both p and q, we create a set of p's ancestors. Then we travel through q's ancestors, the first one appears in p's is our answer.
*/

/*Using stack2
public class Solution
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		Stack<TreeNode> pStack = new Stack<TreeNode>();
		Stack<TreeNode> qStack = new Stack<TreeNode>();
		TreeNode target = null;
		if (findPath(root, p, pStack) && findPath(root, q, qStack)) {
	 		while (!pStack.isEmpty()) {
	 			TreeNode pNode = pStack.pop();
				if (qStack.contains(pNode))
					target = pNode;
			}
		}
		return target;
	}

	private boolean findPath(TreeNode root, TreeNode node, Stack<TreeNode> stack) {
		if (root == null)
			return false;
		if (root == node) {
			stack.push(root);
			return true;
		} else {
			if (findPath(root.left, node, stack) ||  findPath(root.right, node, stack)) {
			    stack.push(root);
				return true;
			}
		}
		return false;
	}
}
*/
