/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {

        // String result = "";
        StringBuilder resultB = new StringBuilder();
        Queue<TreeNode> q = new LinkedList<>();
        //经过试验，可以把q.offer(null);

        if(root == null) return resultB.toString();
        boolean allNullFlag = false;

        q.offer(root);
        while(!q.isEmpty() && !allNullFlag){
            allNullFlag = true;
            int level = q.size();
            for(int i = 0; i < level; i++){
                TreeNode temp = q.poll();
                if(temp == null){
                    // result += "null ";
                    resultB.append("null "); //StringBuilder的append会比String的+快
                    q.offer(null);
                    q.offer(null);
                }
                else{
                    // result += temp.val + " ";
                    resultB.append(temp.val+" ");
                    if(temp.left != null || temp.right != null) allNullFlag = false;
                    q.offer(temp.left);
                    q.offer(temp.right);

                }
            }
        }


        return resultB.toString().trim();
    }

    public TreeNode build(int i, String[] value){
        if(!value[i].equals("null")){
            TreeNode temp = new TreeNode(Integer.parseInt(value[i]));
            temp.left = 2*i+1<value.length?build(2*i+1,value):null;
            temp.right = 2*i+2<value.length?build(2*i+2,value):null;
            return temp;
        }

        return null;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {

        if(data.length() == 0) return null;

        String[] value = data.split(" ");
        // Map<Integer, TreeNode> hm = new HashMap<>(); //HashMap好像太慢了
        // TreeNode[] map = new TreeNode[value.length]; //MLE

        //还是太慢、、、
        // for(int i = value.length-1; i >= 0; i--){
        //     if(!value[i].equals("null")){
        //         TreeNode temp = new TreeNode(Integer.parseInt(value[i]));
        //         temp.left = 2*i+1<value.length?map[2*i+1]:null;
        //         temp.right = 2*i+2<value.length?map[2*i+2]:null;
        //         map[i] = temp;
        //         // temp.left = hm.get(2*i+1);
        //         // temp.right = hm.get(2*i+2);
        //         // hm.put(i,temp);
        //     }
        // }

        // System.out.println(data);
        return build(0,value);

    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));



//在设计的时候还是有问题，并不需要把树用null补全也能用BFS deserialize




//Using BFS
Here I use typical BFS method to handle a binary tree. I use string n to represent null values. The string of the binary tree in the example will be "1 2 3 n n 4 5 n n n n ".

When deserialize the string, I assign left and right child for each not-null node, and add the not-null children to the queue, waiting to be handled later.

public class Codec {
    public String serialize(TreeNode root) {
        if (root == null) return "";
        Queue<TreeNode> q = new LinkedList<>();
        StringBuilder res = new StringBuilder();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) {
                res.append("n ");
                continue;
            }
            res.append(node.val + " ");
            q.add(node.left);
            q.add(node.right);
        }
        return res.toString();
    }

    public TreeNode deserialize(String data) {
        if (data == "") return null;
        Queue<TreeNode> q = new LinkedList<>();
        String[] values = data.split(" ");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        q.add(root);
        for (int i = 1; i < values.length; i++) {
            TreeNode parent = q.poll();
            if (!values[i].equals("n")) {
                TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                parent.left = left;
                q.add(left);
            }
            if (!values[++i].equals("n")) {
                TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                parent.right = right;
                q.add(right);
            }
        }
        return root;
    }
}




//Using DFS
The idea is simple: print the tree in pre-order traversal and use "X" to denote null node and split node with ",". We can use a StringBuilder for building the string on the fly. For deserializing, we use a Queue to store the pre-order traversal and since we have "X" as null node, we know exactly how to where to end building subtress.

public class Codec {
    private static final String spliter = ",";
    private static final String NN = "X";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        buildString(root, sb);
        return sb.toString();
    }

    private void buildString(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append(NN).append(spliter);
        } else {
            sb.append(node.val).append(spliter);
            buildString(node.left, sb);
            buildString(node.right,sb);
        }
    }
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Deque<String> nodes = new LinkedList<>();
        nodes.addAll(Arrays.asList(data.split(spliter)));
        return buildTree(nodes);
    }

    private TreeNode buildTree(Deque<String> nodes) {
        String val = nodes.remove();
        if (val.equals(NN)) return null;
        else {
            TreeNode node = new TreeNode(Integer.valueOf(val));
            node.left = buildTree(nodes);
            node.right = buildTree(nodes);
            return node;
        }
    }
}





//Using Json
public class Codec {
    public String serialize(TreeNode root) {
        return root==null?"n":"[v:"+root.val+
            ",l:"+serialize(root.left)+",r:"+serialize(root.right)+"]";
    }
    public TreeNode deserialize(String data) {
        Deque<String> stack = new LinkedList<String>();
        Deque<TreeNode> nodeStack = new LinkedList<TreeNode>();
        StringBuilder sb = new StringBuilder();
        for (char c: data.toCharArray()){
            if (c == '[' || c == ',' || c == ']'){
                stack.push(sb.toString());
                sb.setLength(0);
                stack.push(String.valueOf(c));
                if (c == ']'){
                    TreeNode t = new TreeNode(0);
                    String str = null;
                    while (!(str = stack.pop()).equals("["))
                        if (str.startsWith("v:"))
                            t.val = Integer.parseInt(str.substring(2));
                        else if (str.equals("l:"))
                            t.left = nodeStack.pop();
                        else if (str.equals("r:"))
                            t.right = nodeStack.pop();
                    nodeStack.push(t);
                }
            }
            else
                sb.append(c);
        }
        return nodeStack.isEmpty()?null:nodeStack.pop();
    }
}





//Three ways
This problem can solved in 3 different ways

(1) Iterative DFS

public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb=new StringBuilder();
        TreeNode x=root;
        Deque<TreeNode> stack=new LinkedList<>();
        while (x!=null || !stack.isEmpty()) {
            if (x!=null) {
                sb.append(String.valueOf(x.val));
                sb.append(' ');
                stack.push(x);
                x=x.left;
            }
            else {
                sb.append("null ");
                x=stack.pop();
                x=x.right;
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.length()==0) return null;
        String[] node=data.split(" ");
        int n=node.length;
        Deque<TreeNode> stack=new LinkedList<>();
        TreeNode root=new TreeNode(Integer.valueOf(node[0]));
        TreeNode x=root;
        stack.push(x);

        int i=1;
        while (i<n) {
            while (i<n && !node[i].equals("null")) {
                x.left=new TreeNode(Integer.valueOf(node[i++]));
                x=x.left;
                stack.push(x);
            }
            while (i<n && node[i].equals("null")) {
                x=stack.pop();
                i++;
            }
            if (i<n) {
                x.right=new TreeNode(Integer.valueOf(node[i++]));
                x=x.right;
                stack.push(x);
            }
        }
        return root;
    }
}
(2) recursive DFS

public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb=new StringBuilder();
        dfs(root,sb);
        return sb.toString();
    }
    private void dfs(TreeNode x, StringBuilder sb) {
        if (x==null) {
            sb.append("null ");
            return;
        }
        sb.append(String.valueOf(x.val));
        sb.append(' ');
        dfs(x.left,sb);
        dfs(x.right,sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] node=data.split(" ");
        int[] d=new int[1];
        return dfs(node,d);
    }
    private TreeNode dfs(String[] node, int[] d) {
        if (node[d[0]].equals("null")) {
            d[0]++;
            return null;
        }
        TreeNode x=new TreeNode(Integer.valueOf(node[d[0]]));
        d[0]++;
        x.left=dfs(node,d);
        x.right=dfs(node,d);
        return x;
    }
}
(3) BFS

public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root==null) return "";
        Queue<TreeNode> qu=new LinkedList<>();
        StringBuilder sb=new StringBuilder();
        qu.offer(root);
        sb.append(String.valueOf(root.val));
        sb.append(' ');
        while (!qu.isEmpty()) {
            TreeNode x=qu.poll();
            if (x.left==null) sb.append("null ");
            else {
                qu.offer(x.left);
                sb.append(String.valueOf(x.left.val));
                sb.append(' ');
            }
            if (x.right==null) sb.append("null ");
            else {
                qu.offer(x.right);
                sb.append(String.valueOf(x.right.val));
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.length()==0) return null;
        String[] node=data.split(" ");
        Queue<TreeNode> qu=new LinkedList<>();
        TreeNode root=new TreeNode(Integer.valueOf(node[0]));
        qu.offer(root);
        int i=1;
        while (!qu.isEmpty()) {
            Queue<TreeNode> nextQu=new LinkedList<>();
            while (!qu.isEmpty()) {
                TreeNode x=qu.poll();
                if (node[i].equals("null")) x.left=null;
                else {
                    x.left=new TreeNode(Integer.valueOf(node[i]));
                    nextQu.offer(x.left);
                }
                i++;
                if (node[i].equals("null")) x.right=null;
                else {
                    x.right=new TreeNode(Integer.valueOf(node[i]));
                    nextQu.offer(x.right);
                }
                i++;
            }
            qu=nextQu;
        }
        return root;
    }
}
