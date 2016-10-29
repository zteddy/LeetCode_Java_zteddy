public class Solution {
	HashMap<Integer, UndirectedGraphNode> h = new HashMap<>();

	public class UndirectedGraphNode {
	     int label;
	     List<UndirectedGraphNode> neighbors;
	     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
	}

	public void constructGraph(int[][] prerequisites){
		for(int i = 0; i < prerequisites.length; i++){
			int then = prerequisites[i][0];
			int first = prerequisites[i][1];
			UndirectedGraphNode f;
			UndirectedGraphNode t;
			if(h.containsKey(first)) f = h.get(first);
			else{
					f = new UndirectedGraphNode(first);
					h.put(first,f);
				}
			if(h.containsKey(then)) t = h.get(then);
			else{
					t = new UndirectedGraphNode(then);
					h.put(then,t);
				}
			f.neighbors.add(t);
			//t.neighbors.add(f);

		}
	}

	public boolean acycle(UndirectedGraphNode n, HashMap visited){
		boolean result = true;
		if(visited.containsKey(n.label))
			return false;
		visited.put(n.label,n.label);
		h.remove(n);
		for(UndirectedGraphNode i:n.neighbors) {
			result = result && acycle(i,visited);
		}
		return result;
	}

    public boolean canFinish(int numCourses, int[][] prerequisites) {
    	boolean result = true;
    	if(prerequisites.length == 0) return result;
    	constructGraph(prerequisites);
    	Iterator iter = h.entrySet().iterator();
		while (iter.hasNext()) {
			HashMap<Integer, Integer> visited = new HashMap<>();
			Map.Entry entry = (Map.Entry) iter.next();
			Object value = entry.getValue();
			result = result && acycle((UndirectedGraphNode)value, visited);
    	}
    	return result;
    }
}
