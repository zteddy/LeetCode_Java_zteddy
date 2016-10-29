/**
 * Definition for undirected graph.
 * class UndirectedGraphNode {
 *     int label;
 *     List<UndirectedGraphNode> neighbors;
 *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
 * };
 */
public class Solution {
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
    	if(node == null) return null;
    	Queue<UndirectedGraphNode> q1 = new LinkedList<>();
    	//Queue<UndirectedGraphNode> q2 = new LinkedList<>();
    	HashMap<Integer,UndirectedGraphNode> visited = new HashMap();
    	HashMap<Integer,UndirectedGraphNode> mapping = new HashMap();

    	//UndirectedGraphNode temp = new UndirectedGraphNode(node.label);
    	q1.offer(node);
    	UndirectedGraphNode temp;
    	UndirectedGraphNode tempn;
    	UndirectedGraphNode clone;
    	List<UndirectedGraphNode> tlist;
    	//q2.offer(temp);

    	while(!q1.isEmpty()){
    		temp = q1.poll();
    		if(visited.containsKey(temp.label)) continue;
    		if(mapping.containsKey(temp.label))
    			clone = mapping.get(temp.label);
    		else{
    			clone = new UndirectedGraphNode(temp.label);
    			mapping.put(temp.label, clone);
    		}
    		tlist = temp.neighbors;
    		for (UndirectedGraphNode n: tlist) {
    			q1.offer(n);
    			if(mapping.containsKey(n.label))
    				tempn = mapping.get(n.label);
    			else{
    				tempn = new UndirectedGraphNode(n.label);
    				mapping.put(n.label,tempn);

    			}
    			clone.neighbors.add(tempn);
    		}
    		visited.put(temp.label,temp);
    	}
    	return mapping.get(node.label);
    }
}

/*More concise
public class Solution {
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) return null;

        UndirectedGraphNode newNode = new UndirectedGraphNode(node.label); //new node for return
        HashMap<Integer, UndirectedGraphNode> map = new HashMap(); //store visited nodes

        map.put(newNode.label, newNode); //add first node to HashMap

        LinkedList<UndirectedGraphNode> queue = new LinkedList(); //to store **original** nodes need to be visited
        queue.add(node); //add first **original** node to queue

        while (!queue.isEmpty()) { //if more nodes need to be visited
            UndirectedGraphNode n = queue.pop(); //search first node in the queue
            for (UndirectedGraphNode neighbor : n.neighbors) {
                if (!map.containsKey(neighbor.label)) { //add to map and queue if this node hasn't been searched before
                    map.put(neighbor.label, new UndirectedGraphNode(neighbor.label));
                    queue.add(neighbor);
                }
                map.get(n.label).neighbors.add(map.get(neighbor.label)); //add neighbor to new created nodes
            }
        }

        return newNode;
    }
}
*/

/*Using recursive dfs
public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
    HashMap<Integer,UndirectedGraphNode> map = new HashMap<Integer,UndirectedGraphNode>();
    return dfs(node,map);
}
private UndirectedGraphNode dfs(UndirectedGraphNode node, HashMap<Integer,UndirectedGraphNode> map) {
    if (node == null) return null;
    if (map.containsKey(node.label)) {
        return map.get(node.label);
    } else {
        UndirectedGraphNode clone = new UndirectedGraphNode(node.label);
        map.put(node.label,clone);
        for (int i = 0; i < node.neighbors.size(); i++) {
            clone.neighbors.add(dfs(node.neighbors.get(i), map));
        }
        return clone;
    }
}
*/
