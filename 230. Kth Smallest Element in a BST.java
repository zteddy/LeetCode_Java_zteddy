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
    int result;
    public int inorder(TreeNode t, int now, int k){
        if(t != null){
            now = inorder(t.left, now, k);
            now++;
            if(now == k) result = t.val;
            return inorder(t.right, now, k);
        }
        return now;
    }

    public int kthSmallest(TreeNode root, int k) {


        inorder(root, 1, k);

        return result;

    }
}




/*Three ways
Binary Search (dfs): most preferable?

  public int kthSmallest(TreeNode root, int k) {
        int count = countNodes(root.left);
        if (k <= count) {
            return kthSmallest(root.left, k);
        } else if (k > count + 1) {
            return kthSmallest(root.right, k-1-count); // 1 is counted as current node
        }

        return root.val;
    }

    public int countNodes(TreeNode n) {
        if (n == null) return 0;

        return 1 + countNodes(n.left) + countNodes(n.right);
    }
DFS in-order recursive:

    // better keep these two variables in a wrapper class
    private static int number = 0;
    private static int count = 0;

    public int kthSmallest(TreeNode root, int k) {
        count = k;
        helper(root);
        return number;
    }

    public void helper(TreeNode n) {
        if (n.left != null) helper(n.left);
        count--;
        if (count == 0) {
            number = n.val;
            return;
        }
        if (n.right != null) helper(n.right);
    }
DFS in-order iterative:

  public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> st = new Stack<>();

        while (root != null) {
            st.push(root);
            root = root.left;
        }

        while (k != 0) {
            TreeNode n = st.pop();
            k--;
            if (k == 0) return n.val;
            TreeNode right = n.right;
            while (right != null) {
                st.push(right);
                right = right.left;
            }
        }

        return -1; // never hit if k is valid
  }
2 yrs later...
Appreciated everyone reviewing my answers and leaving insightful comments here through the last two years, I've never got chance to reply to all of them, and unfortunately, I no longer write in JAVA and this might be the excuse I won't go back editing my stupid codes any more lol. Below is my Python answer that I just picked up lately, it's more fun and hopefully, easier to understand by its simple structure.

note: requirement has been changed a bit since last time I visited that the counting could be looked up frequently and BST itself could be altered (inserted/deleted) by multiple times, so that's the main reason that I stored them in an array.

class Solution(object):
    def kthSmallest(self, root, k):
        """
        :type root: TreeNode
        :type k: int
        :rtype: int
        """
        count = []
        self.helper(root, count)
        return count[k-1]

    def helper(self, node, count):
        if not node:
            return

        self.helper(node.left, count)
        count.append(node.val)
        self.helper(node.right, count)
Thanks again!
*/




/*Concise and clean
In order traverse for BST gives the natural order of numbers. No need to use array.

Recursive:

int count = 0;
int result = Integer.MIN_VALUE;

public int kthSmallest(TreeNode root, int k) {
    traverse(root, k);
    return result;
}

public void traverse(TreeNode root, int k) {
    if(root == null) return;
    traverse(root.left, k);
    count ++;
    if(count == k) result = root.val;
    traverse(root.right, k);
}
Iterative:

 public int kthSmallest(TreeNode root, int k) {
     Stack<TreeNode> stack = new Stack<TreeNode>();
     TreeNode p = root;
     int count = 0;

     while(!stack.isEmpty() || p != null) {
         if(p != null) {
             stack.push(p);  // Just like recursion
             p = p.left;

         } else {
            TreeNode node = stack.pop();
            if(++count == k) return node.val;
            p = node.right;
         }
     }

     return Integer.MIN_VALUE;
 }
 */




/*Using search
public static int ans = 0;
public int kthSmallest(TreeNode root, int k) {
    helper(root, k);
    return ans;
}

public int helper(TreeNode root, int k) {
    if (root == null) {
        return 0;
    }
    int leftCount = helper(root.left, k);
    int rightCount = helper(root.right, k - leftCount - 1);
    if (k == leftCount + 1) {
        ans = root.val;
    }
    return leftCount + rightCount + 1;
}
We count the number of nodes of left sub tree and right sub tree recursively. Suppose the Kth smallest element is in the right sub tree, then we need to update k as k - leftCount - 1 (leftCount + 1 is the number of nodes of left sub tree plus the root node). Only when k equals leftCount + 1, we find the target.
*/
