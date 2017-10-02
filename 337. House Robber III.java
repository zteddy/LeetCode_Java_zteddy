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
    public List<Integer> recursive(TreeNode t){
        List<Integer> temp = new ArrayList<>();
        temp.add(0);
        temp.add(0);
        if(t == null) return temp;
        // if(t.left == null && t.right == null){
        //     temp = new ArrayList<>();
        //     //first block then unblock
        //     temp.add(val);
        //     tamp.add(0);
        //     return temp;
        // }
        temp = new ArrayList<>();
        List<Integer> llist = recursive(t.left);
        List<Integer> rlist = recursive(t.right);
        int block = llist.get(1) + rlist.get(1) + t.val;
        int unblock = Math.max(llist.get(0),llist.get(1)) + Math.max(rlist.get(0),rlist.get(1));
        temp.add(block);
        temp.add(unblock);
        return temp;

    }

    public int rob(TreeNode root) {
        List<Integer> resultList = recursive(root);
        return Math.max(resultList.get(0),resultList.get(1));
    }
}




/*More concise
Step I -- Think naively

At first glance, the problem exhibits the feature of "optimal substructure": if we want to rob maximum amount of money from current binary tree (rooted at root), we surely hope that we can do the same to its left and right subtrees.

So going along this line, let's define the function rob(root) which will return the maximum amount of money that we can rob for the binary tree rooted at root; the key now is to construct the solution to the original problem from solutions to its subproblems, i.e., how to get rob(root) from rob(root.left), rob(root.right), ... etc.

Apparently the analyses above suggest a recursive solution. And for recursion, it's always worthwhile figuring out the following two properties:

Termination condition: when do we know the answer to rob(root) without any calculation? Of course when the tree is empty -- we've got nothing to rob so the amount of money is zero.

Recurrence relation: i.e., how to get rob(root) from rob(root.left), rob(root.right), ... etc. From the point of view of the tree root, there are only two scenarios at the end: root is robbed or is not. If it is, due to the constraint that "we cannot rob any two directly-linked houses", the next level of subtrees that are available would be the four "grandchild-subtrees" (root.left.left, root.left.right, root.right.left, root.right.right). However if root is not robbed, the next level of available subtrees would just be the two "child-subtrees" (root.left, root.right). We only need to choose the scenario which yields the larger amount of money.

Here is the program for the ideas above:

public int rob(TreeNode root) {
    if (root == null) return 0;

    int val = 0;

    if (root.left != null) {
        val += rob(root.left.left) + rob(root.left.right);
    }

    if (root.right != null) {
        val += rob(root.right.left) + rob(root.right.right);
    }

    return Math.max(val + root.val, rob(root.left) + rob(root.right));
}
However the solution runs very slowly (1186 ms) and barely got accepted (the time complexity turns out to be exponential, see my comments below).

Step II -- Think one step further

In step I, we only considered the aspect of "optimal substructure", but think little about the possibilities of overlapping of the subproblems. For example, to obtain rob(root), we need rob(root.left), rob(root.right), rob(root.left.left), rob(root.left.right), rob(root.right.left), rob(root.right.right); but to get rob(root.left), we also need rob(root.left.left), rob(root.left.right), similarly for rob(root.right). The naive solution above computed these subproblems repeatedly, which resulted in bad time performance. Now if you recall the two conditions for dynamic programming: "optimal substructure" + "overlapping of subproblems", we actually have a DP problem. A naive way to implement DP here is to use a hash map to record the results for visited subtrees.

And here is the improved solution:

public int rob(TreeNode root) {
    return robSub(root, new HashMap<>());
}

private int robSub(TreeNode root, Map<TreeNode, Integer> map) {
    if (root == null) return 0;
    if (map.containsKey(root)) return map.get(root);

    int val = 0;

    if (root.left != null) {
        val += robSub(root.left.left, map) + robSub(root.left.right, map);
    }

    if (root.right != null) {
        val += robSub(root.right.left, map) + robSub(root.right.right, map);
    }

    val = Math.max(val + root.val, robSub(root.left, map) + robSub(root.right, map));
    map.put(root, val);

    return val;
}
The runtime is sharply reduced to 9 ms, at the expense of O(n) space cost (n is the total number of nodes; stack cost for recursion is not counted).

Step III -- Think one step back

In step I, we defined our problem as rob(root), which will yield the maximum amount of money that can be robbed of the binary tree rooted at root. This leads to the DP problem summarized in step II.

Now let's take one step back and ask why we have overlapping subproblems. If you trace all the way back to the beginning, you'll find the answer lies in the way how we have defined rob(root). As I mentioned, for each tree root, there are two scenarios: it is robbed or is not. rob(root) does not distinguish between these two cases, so "information is lost as the recursion goes deeper and deeper", which results in repeated subproblems.

If we were able to maintain the information about the two scenarios for each tree root, let's see how it plays out. Redefine rob(root) as a new function which will return an array of two elements, the first element of which denotes the maximum amount of money that can be robbed if root is not robbed, while the second element signifies the maximum amount of money robbed if it is robbed.

Let's relate rob(root) to rob(root.left) and rob(root.right)..., etc. For the 1st element of rob(root), we only need to sum up the larger elements of rob(root.left) and rob(root.right), respectively, since root is not robbed and we are free to rob its left and right subtrees. For the 2nd element of rob(root), however, we only need to add up the 1st elements of rob(root.left) and rob(root.right), respectively, plus the value robbed from root itself, since in this case it's guaranteed that we cannot rob the nodes of root.left and root.right.

As you can see, by keeping track of the information of both scenarios, we decoupled the subproblems and the solution essentially boiled down to a greedy one. Here is the program:

public int rob(TreeNode root) {
    int[] res = robSub(root);
    return Math.max(res[0], res[1]);
}

private int[] robSub(TreeNode root) {
    if (root == null) return new int[2];

    int[] left = robSub(root.left);
    int[] right = robSub(root.right);
    int[] res = new int[2];

    res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
    res[1] = root.val + left[0] + right[0];

    return res;
}
*/




/*Simple recursive O(n)?
public class Solution {
    public int rob(TreeNode root) {
        if (root == null) return 0;
        return Math.max(robInclude(root), robExclude(root));
    }

    public int robInclude(TreeNode node) {
        if(node == null) return 0;
        return robExclude(node.left) + robExclude(node.right) + node.val;
    }

    public int robExclude(TreeNode node) {
        if(node == null) return 0;
        return rob(node.left) + rob(node.right);
    }
}
*/



/*Using Point
public class Solution {
    public int rob(TreeNode root) {
        return robHelp(root).x;
    }
    public Point robHelp(TreeNode root) {
        if (root == null) {
            return new Point(0, 0);
        }
        Point leftPoint = robHelp(root.left);
        Point rightPoint = robHelp(root.right);

        return new Point(Math.max(root.val + leftPoint.y + rightPoint.y, leftPoint.x + rightPoint.x), leftPoint.x + rightPoint.x);
    }
}
*/



/*Using HashMap
The naive solution is straightforward, just traverse the tree and in each node, we either take it or not. If we take it, we cannot take its children, if not take, we can take either or both of the children. This will cause TLE due to extra calculation. Since this is a house robber problem, DP is the first come to mind for optimization. There are two ways to work with this problem. The top-down is the most intuitive one for me, as follows. Used two map, hasRoot and noRoot, since we need to keep track of the result for either rob the house or not.

The second approach is bottom-up. It is not very intuitive for a tree. But one can think about post-order traversal. We traverse the left and the right child and return some necessary result, and then process the root. First, what do we need the child to return? So from the first solution, we can see that we either rob a house or not, so the child needs to return two values, rob or no rob for the root. Here we can just use an int array to keep the two values. // traverse the tree int[] left = helper(curr.left); int[] right = helper(curr.right); Here left[0] represents robbing root node, left[1] not robbing.

Then we do stuff for the root node. We need an int array again to save its result. We either rob root, and take the left[1] and right[1] and add root value to it, or we don't rob root, and take the largest one for left and right. In the end, we just return res.

Here are two solutions, hope it helps!

Top-down approach:

Map<TreeNode, Integer> hasRoot = new HashMap<>();
Map<TreeNode, Integer> noRoot = new HashMap<>();
public int rob(TreeNode root) {
    if (root == null) {
        return 0;
    }
    int max = Math.max(helper(root, true), helper(root, false));
    return max;
}

private int helper(TreeNode root, boolean canrob) {
    if (root == null) {
        return 0;
    }
    int res = 0;
    if (canrob) {
        // check the hasRoot map for previous calculated
        if(hasRoot.containsKey(root)) {
            return hasRoot.get(root);
        }
        res = Math.max(helper(root.left, false) + helper(root.right, false) + root.val, helper(root.left, true) + helper(root.right, true));
        hasRoot.put(root, res);
    } else {
        // check the noRoot map
        if(noRoot.containsKey(root)) {
            return noRoot.get(root);
        }
        res = helper(root.left, true) + helper(root.right, true);
        noRoot.put(root, res);
    }
    return res;
}
Bottom-up:

// bottom-up solution
public int rob(TreeNode root) {
    int[] num = helper(root);
    // nums[0] includes root, nums[1] excludes root
    return Math.max(num[0], num[1]);
}
private int[] helper(TreeNode curr) {
    if (curr == null) {
        return new int[2];
    }
    // traverse the tree
    int[] left = helper(curr.left);
    int[] right = helper(curr.right);

    // do stuff

    int[] res = new int[2];
    // case 1:  add root value, so exclude both left and right
    res[0] = left[1] + right[1] + curr.val;
    // case 2: exclued root value, get max from both left child and right child
    res[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

    // done stuff

    return res;
}
*/
