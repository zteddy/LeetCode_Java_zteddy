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
	TreeNode w1;
	TreeNode wenhao;
	TreeNode w2;
	TreeNode prev;
	public void recoverFinder(TreeNode root){
		if(root == null) return ;
		recoverFinder(root.left);
		if(prev != null){
			if(prev.val > root.val){
				if(w1 == null){
					w1 = prev;
					wenhao = root;
				}
				else{
					w2 = root;
				}
			}
		}
		prev = root;
		recoverFinder(root.right);
		return ;
	}
    public void recoverTree(TreeNode root) {
    	recoverFinder(root);
    	int temp = w1.val;
    	if(w2 == null){
    		w1.val = wenhao.val;
    		wenhao.val = temp;
    	}
    	else{
    		w1.val = w2.val;
    		w2.val = temp;
    	}
    }
}

//Recursive use O(log(n)) space


/*Using Morris Travel
To understand this, you need to first understand Morris Traversal or Morris Threading Traversal.
It take use of leaf nodes' right/left pointer to achieve O(1) space Traversal on a Binary Tree.
Below is a standard Inorder Morris Traversal, referred from http://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html (a Chinese Blog, while the graphs are great for illustration)

public void morrisTraversal(TreeNode root){
		TreeNode temp = null;
		while(root!=null){
			if(root.left!=null){
				// connect threading for root
				temp = root.left;
				while(temp.right!=null && temp.right != root)
					temp = temp.right;
				// the threading already exists
				if(temp.right!=null){
					temp.right = null;
					System.out.println(root.val);
					root = root.right;
				}else{
					// construct the threading
					temp.right = root;
					root = root.left;
				}
			}else{
				System.out.println(root.val);
				root = root.right;
			}
		}
	}
In the above code, System.out.println(root.val);appear twice, which functions as outputing the Node in ascending order (BST). Since these places are in order, replace them with

    if(pre!=null && pre.val > root.val){
    	if(first==null){first = pre;second = root;}
    	else{second = root;}
  }
pre = root;
each time, the pre node and root are in order as System.out.println(root.val); outputs them in order.

Then, come to how to specify the first wrong node and second wrong node.

When they are not consecutive, the first time we meet pre.val > root.val ensure us the first node is the pre node, since root should be traversal ahead of pre, pre should be at least at small as root. The second time we meet pre.val > root.val ensure us the second node is the root node, since we are now looking for a node to replace with out first node, which is found before.

When they are consecutive, which means the case pre.val > cur.val will appear only once. We need to take case this case without destroy the previous analysis. So the first node will still be pre, and the second will be just set to root. Once we meet this case again, the first node will not be affected.

Below is the updated version on Morris Traversal.

public void recoverTree(TreeNode root) {
    TreeNode pre = null;
    TreeNode first = null, second = null;
    // Morris Traversal
    TreeNode temp = null;
	while(root!=null){
		if(root.left!=null){
			// connect threading for root
			temp = root.left;
			while(temp.right!=null && temp.right != root)
				temp = temp.right;
			// the threading already exists
			if(temp.right!=null){
			    if(pre!=null && pre.val > root.val){
			        if(first==null){first = pre;second = root;}
			        else{second = root;}
			    }
			    pre = root;

				temp.right = null;
				root = root.right;
			}else{
				// construct the threading
				temp.right = root;
				root = root.left;
			}
		}else{
			if(pre!=null && pre.val > root.val){
			    if(first==null){first = pre;second = root;}
			    else{second = root;}
			}
			pre = root;
			root = root.right;
		}
	}
	// swap two node values;
	if(first!= null && second != null){
	    int t = first.val;
	    first.val = second.val;
	    second.val = t;
	}
}
*/

/*Using Morris Travel 2
In-order traversal is really useful in BST. Following in-order traversal, we should have following order: prev.val < curr.val. If not, then we found at least one incorrectly placed node

So the basic idea is to visit the tree with in-order traversal and search for two swapped nodes. Then swap them back.

Now the problem is if we found an incorrect pair where prev.val > curr.val, how do we know which node is the incorrect one? The answer is it depends on whether we have found incorrect node before. So What is that?

Since we get two elements that are swapped by mistake, there must be a smaller TreeNode get a larger value and a larger TreeNode get a smaller value.
Their value are swapped, but the incorrect smaller node is still in smaller tree and incorrect larger node is still in larger tree. So we will visit the incorrect smaller node first, and this node will be detected when we compare its value with next.val, i.e. when it is treated as prev node. The incorrect larger node will be detected when we compare its value with prev.val. We don't know if it is close or not close to incorrect smaller node, so we should continue search BST and update it if we found another incorrect node.

Therefore if it is the first time we found an incorrect pair, the prev node must be the first incorrect node.
If it is not the first time we found an incorrect pair, the curr node must be the second incorrect node, though
we may have corner case that two incorrect nodes are in same pair.

Recursive in-order traversal based on above idea:

public void recoverTree(TreeNode root) {
    //use inorder traversal to detect incorrect node

    inOrder(root);


    int temp = first.val;
    first.val = second.val;
    second.val = temp;
}

TreeNode prev = null;
TreeNode first = null;
TreeNode second = null;

public void inOrder(TreeNode root){
    if(root == null) return;
    //search left tree
    inOrder(root.left);

    //in inorder traversal of BST, prev should always have smaller value than current value
    if(prev != null && prev.val >= root.val){
        //incorrect smaller node is always found as prev node
        if(first == null) first = prev;
      //incorrect larger node is always found as curr(root) node
        second = root;
    }


    //update prev node
    prev = root;

    //search right tree
    inOrder(root.right);
}
iterative in-order traversal based on above idea:

public void recoverTree(TreeNode root) {
    TreeNode first = null;
    TreeNode second = null;

    TreeNode curr = root;
    TreeNode prev = null;

    Stack<TreeNode> stack = new Stack<TreeNode>();

    while(!stack.isEmpty() ||  curr != null){
        if(curr != null){
            //visit curr's left subtree
            stack.push(curr);
            curr = curr.left;
        }else{
            //done left subtree of curr Node
            curr = stack.pop();

            //compare curr.val with prev.val if we have one
            if(prev != null && curr.val <= prev.val){
                //incorrect smaller node is always found as prev node
                if(first == null) first = prev;
                //incorrect larger node is always found as curr node
                second = curr;
            }

            //visit curr's right subtree
            prev = curr;
            curr = curr.right;
        }
    }

    //recover swapped nodes
    int temp = first.val;
    first.val = second.val;
    second.val = temp;
}
Both recursive and iterative will occupy O(n) space in worst case, in which the tree is like a LinkedList

To reduce the space to constant space, we have to use Morris-traversal.

Morris-traversal is similar to recursive/iterative traversal, but we need to modify the tree structure during the
traversal. before we visiting the left tree of a root, we will build a back-edge between rightmost node in left tree and the root. So we can go back to the root node after we are done with the left tree. Then we locate the rightmost node in left subtree again, cut the back-edge, recover the tree structure and start visit right subtree. The detection of two incorrect TreeNodes is similar to iterative/recursive in-order traversal.
We don't use extra data structure here, so the space complexity is reduced to O(1) and the time complexity will be O(n)

Morris-traversal based on above description:

public void recoverTree(TreeNode root) {
	//Morris-traversal

    TreeNode first = null;
    TreeNode second = null;

    TreeNode pred = null; //rightmost node in left tree
    TreeNode prev = null;

    TreeNode curr = root;

    while(curr != null){
        //for each node, we compare it with prev node as we did in in-order-traversal
        if(prev != null && curr.val <= prev.val){
            if(first == null) first = prev;
            second = curr;
        }

        if(curr.left != null){
            //got left tree, then let's locate its rightmost node in left tree
            pred = curr.left;
            //we may have visited the left tree before, and connect the rightmost node with curr node (root node)
            while(pred.right != null && pred.right != curr){
                pred = pred.right;
            }

            if(pred.right == curr){
                //if this left tree has been visited before, then we are done with it
                //cut the connection with currNode and start visit curr's right tree
                pred.right = null;
                prev = curr;
                curr = curr.right;
            }else{
                //if this left tree has not been visited before, then we create a back edge from rightmost node
                // to curr node, so we can return to the start point after done the left tree
                pred.right = curr;
                curr = curr.left;
            }

        }else{
            //no left tree, then just visit its right tree
            prev = curr;
            curr = curr.right;
        }
    }

    int temp = first.val;
    first.val = second.val;
    second.val = temp;
}
*/
