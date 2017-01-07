/**
 * Definition for binary tree with next pointer.
 * public class TreeLinkNode {
 *     int val;
 *     TreeLinkNode left, right, next;
 *     TreeLinkNode(int x) { val = x; }
 * }
 */
public class Solution {
    public void connect(TreeLinkNode root) {
    	if(root == null) return ;  //void也可以return，用于终止
    	if(root.left == null) return ;
    	root.left.next = root.right;
    	if(root.next == null) root.right.next = null;
    	else root.right.next = root.next.left;
    	connect(root.left);
    	connect(root.right);
    }
}
//不确定recursive是不是用constant extra space

/*Iterative
Here is solution from old discuss by skaugust. Thanks to skaugust!

I'm confused why people are trying to use recursive solutions here. The problem states that you can only use constant space. To get to the leaf nodes, a recursive solution needs to be log2(n) calls deep, and each call has a call stack, which takes up memory. This means that a recursive solution isn't constant memory, but O(log(n)) memory. To solve this, you just replace the recursive call with a while loop wrapping all of your logic.

public class Solution {
    public void connect(TreeLinkNode root) {
        TreeLinkNode level_start=root;
        while(level_start!=null){
            TreeLinkNode cur=level_start;
            while(cur!=null){
                if(cur.left!=null) cur.left.next=cur.right;
                if(cur.right!=null && cur.next!=null) cur.right.next=cur.next.left;

                cur=cur.next;
            }
            level_start=level_start.left;
        }
    }
}
*/
