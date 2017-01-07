/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

public class BSTIterator {
	Stack<TreeNode> stk = new Stack<>();
	TreeNode cur;

    public BSTIterator(TreeNode root) {
    	if(root != null){
    		stk.push(root);
    		while(root.left != null){
    			stk.push(root.left);
    			root = root.left;
    		}
    	}

    	cur = root;
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
    	return !stk.empty();
    }

    /** @return the next smallest number */
    public int next() {
    	TreeNode cur = stk.pop();
    	int result = cur.val;
    	if(cur.right != null){
    		cur = cur.right;
    		stk.push(cur);
    		while(cur.left != null){
    			stk.push(cur.left);
    			cur = cur.left;
    		}
    	}
    	return result;
    }
}

/**
 * Your BSTIterator will be called like this:
 * BSTIterator i = new BSTIterator(root);
 * while (i.hasNext()) v[f()] = i.next();
 */



/*Using Morris Inorder Tree Traversal
First of all, even with most optimized space and time complexity, I have to say this may be not the best solution, since it changes the tree structure a little bit during constructor period.

#Construct Period
The idea is use in-order Morris Tree Traversal (check out [1][2] if you are not familiar with it, otherwise the bellow explanation to you is nonsense) to construct a threaded binary tree in construct function. (This is O(n) time, but we don't care much about it.) Then set a pointer (we call it "curr") to the smallest TreeNode, which is easy to do, just find the left-most child from root.

#hasNext()
For hasNext() function, simple return "curr != null", which is by definition of threaded binary tree.

#next()
For next() function, it is a little bit tricky. We call the right child of "curr" as "next". If "next" is not a normal right child of "curr", which means the right child relationship is constructed during the threaded binary tree construction period, then the next TreeNode we should iterate is indeed "next". However, if "next" is a normal right child of "curr", then the next TreeNode we should iterate is actually the left-most child of "next".

So the problem reduces to how to make clear the situation. Well, it is no hard. If "next" is null, then we've done, simply set "curr" to null. If "next" has no left child, or "next"'s left child is strictly larger than "curr", that means it is a normal right child of "curr", so we should set "curr" to left-most child of "next". Otherwise, we set "curr" to "next", and break the right child relationship between "curr" and "next", to recover the original tree structure.

#Complexity analysis
The space complexity is straightforwardly O(1). The time complexity needs some more explanation. Since the only part that is not O(1) is when we search the left-most child of "next". However, for all the children along this left path (say, there are N children), we do once search left-most and (N-1) times simply go to right child. So the amortized time complexity is still O(1).

#Code:

public class BSTIterator {

	private TreeNode curr;
    public BSTIterator(TreeNode root) {
		TreeNode prev;
		//Do a morris in-order traversal, to construct a threaded binary tree
		curr = root;
		while(curr != null){
			if(curr.left == null){
				curr = curr.right;
			}
			else{
				prev = curr.left;
				while(prev.right != null && prev.right != curr)
					prev = prev.right;

				if(prev.right == null){
					prev.right = curr;
					curr = curr.left;
				}
				else{
					curr = curr.right;
				}
			}
		}

		//get the left-most child of root, i.e. the smallest TreeNode
		curr = root;
		while(curr != null && curr.left != null)
			curr = curr.left;
    }

    //@return whether we have a next smallest number
    public boolean hasNext() {
		return curr != null;
    }

    // @return the next smallest number
    public int next() {

		//copy the value we need to return
		int result = curr.val;

		TreeNode next = curr.right;
		if(next == null)
			curr = next;
		//the right child relationship is a normal one, find left-most
		//child of "next"
		else if(next.left == null || next.left.val > curr.val){
			curr = next;
			while(curr.left != null)
				curr = curr.left;
		}
		//the right child relationship is made when we
		//construct the threaded binary tree
		else{
			curr.right = null;//we recover the original tree structure
			curr = next;
		}

		return result;
    }
}
#Reference

For those who are not familiar with Morris Tree Traversal, these two paragraphs are good references.

[1]https://en.wikipedia.org/wiki/Threaded_binary_tree

[2]http://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion-and-without-stack/




Hey, just saw your post. I got similar idea but slightly improved the time cost from O(1) amortized time to O(1) in worst case.

Check it here.
https://leetcode.com/discuss/63980/o-1-time-o-1-space-in-worst-case-challenge-me-please
TreeNode nodeIterator;
public BSTIterator(TreeNode root) {
    TreeNode node = root;
    if (root == null) return;
    while (node.left != null) {
        node = node.left;
    }
    nodeIterator = node;
    restructure(root);
}

//@return whether we have a next smallest number
public boolean hasNext() {
    return nodeIterator != null;
}

//@return the next smallest number
public int next() {
    int next = nodeIterator.val;
    nodeIterator = nodeIterator.right;
    return next;
}

private void restructure(TreeNode node) {
    TreeNode pre = null;
    TreeNode temp = null;
    while(node!=null){
        if(node.left!=null){
            // connect threading for node
            temp = node.left;
            while(temp.right!=null && temp.right != node)
                temp = temp.right;
            // the threading already exists
            if(temp.right != null){
                // add right pointer from 'pre' node to current node
                pre.right = node;
                pre = node;
                node = node.right;
            }else{
                // construct the threading
                temp.right = node;
                node = node.left;
            }
        }else{
            // add right pointer from 'pre' node to current node
            if (pre != null) pre.right = node;
            pre = node;
            node = node.right;
        }
    }
}
Explanation:
Saw a lot of solutions here trying to get O(1) search time and O(h) space cost. Some of them are actually using O(n) space where n is the number of nodes, which is much greater than h. Some of them are cost average O(1) or amortized O(1) to search but worst case costs O(n), which makes it still O(n).

The solution I posted above is inspired by Morris Tree Traversal. In Morris Tree Traversal, we create fake 'right' pointer to point to the ancestors. And set them back to null later. What if we just keep those fake pointers? We get a sorted linked list! Where you call 'right' to go 'next'.

However, only keeping those fake 'right' points is not enough. In the Morris Tree Traversal, some times we have to go to the most left node to get the next node. How should we handle this case? We just need to set the right pointer of previously node points to the current node. Done.
*/
