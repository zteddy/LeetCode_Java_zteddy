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
	/*Binary tree!! Not Binary search tree!!
	public TreeNode insert(TreeNode r, int x){
		if(r == null){
			TreeNode temp = new TreeNode(x);
			return temp;
		}
		if(x < r.val) r.left = insert(r.left, x);
		else if(x > r.val) r.right = insert(r.right, x);
		return r;
	}
	*/

	int[] p;
	int[] i;
	public TreeNode constructTree(int istart, int iend, int pstart){
		if(istart == iend) return null;
		else if(istart - iend == 1){
			TreeNode temp = new TreeNode(p[pstart]);
			return temp;
		}
		else{
			int rootVal = p[pstart];
			TreeNode temp = new TreeNode(rootVal);
			int index = istart;
			for(int j = 0; j < p.length; j++){
				if(i[j] == rootVal)
					index = j;
			}
			temp.left = constructTree(istart, index, pstart+1);
			temp.right = constructTree(index+1, iend, pstart+1+(index-istart));
			return temp;
		}
	}

    public TreeNode buildTree(int[] preorder, int[] inorder) {
    	/*Binary tree!! Not Binary search tree!!
    	if(preorder.length == 0) return null;
    	TreeNode root = new TreeNode(preorder[0]);
    	for(int i = 1; i < preorder.length; i++){
    		insert(root, preorder[i]);
    	}
    	return root;
    	*/
    	p = preorder;
    	i = inorder;
    	return constructTree(0, p.length, 0);

    }
}

/*Using hashmap to speedup
Nice solution! One improvement: remember to use HashMap to cache the inorder[] position. This can reduce your solution from 20ms to 5ms.

Here is the my Java solution:

public TreeNode buildTree(int[] preorder, int[] inorder) {
    Map<Integer, Integer> inMap = new HashMap<Integer, Integer>();

    for(int i = 0; i < inorder.length; i++) {
        inMap.put(inorder[i], i);
    }

    TreeNode root = buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inMap);
    return root;
}

public TreeNode buildTree(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd, Map<Integer, Integer> inMap) {
    if(preStart > preEnd || inStart > inEnd) return null;

    TreeNode root = new TreeNode(preorder[preStart]);
    int inRoot = inMap.get(root.val);
    int numsLeft = inRoot - inStart;

    root.left = buildTree(preorder, preStart + 1, preStart + numsLeft, inorder, inStart, inRoot - 1, inMap);
    root.right = buildTree(preorder, preStart + numsLeft + 1, preEnd, inorder, inRoot + 1, inEnd, inMap);

    return root;
}
*/

/*Using Arrays.copyOfRange()
public TreeNode buildTree(int[] preorder, int[] inorder) {
	if(preorder==null || inorder==null || inorder.length==0 || preorder.length==0) return null;
	TreeNode root = new TreeNode(preorder[0]);
	if(preorder.length==1) return root;
	int breakindex = -1;
	for(int i=0;i<inorder.length;i++) { if(inorder[i]==preorder[0]) { breakindex=i; break;} }
	int[] subleftpre  = Arrays.copyOfRange(preorder,1,breakindex+1);
	int[] subleftin   = Arrays.copyOfRange(inorder,0,breakindex);
	int[] subrightpre = Arrays.copyOfRange(preorder,breakindex+1,preorder.length);
	int[] subrightin  = Arrays.copyOfRange(inorder,breakindex+1,inorder.length);
	root.left  = buildTree(subleftpre,subleftin);
	root.right = buildTree(subrightpre,subrightin);
	return root;
}
*/

/*Iterative version
I din't find iterative solutions discussed in the old Discuss. So, I thought, I will add my solution in here.

The idea is as follows:

Keep pushing the nodes from the preorder into a stack (and keep making the tree by adding nodes to the left of the previous node) until the top of the stack matches the inorder.

At this point, pop the top of the stack until the top does not equal inorder (keep a flag to note that you have made a pop).

Repeat 1 and 2 until preorder is empty. The key point is that whenever the flag is set, insert a node to the right and reset the flag.

class Solution {
public:
    TreeNode *buildTree(vector<int> &preorder, vector<int> &inorder) {

        if(preorder.size()==0)
            return NULL;

        stack<int> s;
        stack<TreeNode *> st;
        TreeNode *t,*r,*root;
        int i,j,f;

        f=i=j=0;
        s.push(preorder[i]);

        root = new TreeNode(preorder[i]);
        st.push(root);
        t = root;
        i++;

        while(i<preorder.size())
        {
            if(!st.empty() && st.top()->val==inorder[j])
            {
                t = st.top();
                st.pop();
                s.pop();
                f = 1;
                j++;
            }
            else
            {
                if(f==0)
                {
                    s.push(preorder[i]);
                    t -> left = new TreeNode(preorder[i]);
                    t = t -> left;
                    st.push(t);
                    i++;
                }
                else
                {
                    f = 0;
                    s.push(preorder[i]);
                    t -> right = new TreeNode(preorder[i]);
                    t = t -> right;
                    st.push(t);
                    i++;
                }
            }
        }

        return root;
    }
};
*/
