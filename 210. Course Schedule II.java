public class Solution {
	public ArrayList[] graphConstructor(int num, int[][] prerequisites){
		ArrayList<Integer>[] cg = new ArrayList[num];
		for(int i = 0; i < num; i++){
			cg[i] = new ArrayList<Integer>();
		}
		for(int i = 0; i < prerequisites.length; i++){
			cg[prerequisites[i][1]].add(prerequisites[i][0]);
		}
		return cg;
	}
    public int[] findOrder(int numCourses, int[][] prerequisites) {
    	ArrayList<Integer>[] cg; //这里可以不声明<Integer>，但之后就会自动视为object，用的时候需要强制类型转换为int
    	cg = graphConstructor(numCourses, prerequisites);
    	int[] indegree = new int[numCourses];
    	for(int i = 0; i < numCourses; i++){
    		 for(int j = 0; j < cg[i].size(); j++){
    		 	indegree[(int)cg[i].get(j)]++;
    		 }
    	}
    	Queue<Integer> q = new LinkedList<>();
    	//这里也可以不声明<Integer>，但之后就会自动视为object，用的时候需要强制类型转换为int
    	for(int i = 0; i < numCourses; i++){
    		if(indegree[i] == 0) q.offer(i);
    	}

    	int[] result = new int[numCourses];
    	int i = 0;

    	while(!q.isEmpty()){
    		int temp = q.poll();
    		result[i] = temp;
    		i++;
    		for(int j = 0; j < cg[temp].size(); j++){
    			indegree[cg[temp].get(j)]--;
    			if(indegree[cg[temp].get(j)] == 0) q.offer(cg[temp].get(j));
    		}
    	}

    	if(i != numCourses) return new int[0];
    	else return result;
    }
}



/*Using BFS and DFS
This question asks for an order in which prerequisite courses must be taken first. This prerequisite relationship reminds one of directed graphs. Then, the problem reduces to find a topological sort order of the courses, which would be a DAG if it has a valid order.

public int[] findOrder(int numCourses, int[][] prerequisites) {
    int[] incLinkCounts = new int[numCourses];
    List<List<Integer>> adjs = new ArrayList<>(numCourses); //还可以这样inti
    initialiseGraph(incLinkCounts, adjs, prerequisites);
    //return solveByBFS(incLinkCounts, adjs);
    return solveByDFS(adjs);
}
The first step is to transform it into a directed graph. Since it is likely to be sparse,we use adjacency list graph data structure. 1 -> 2 means 1 must be taken before 2.

private void initialiseGraph(int[] incLinkCounts, List<List<Integer>> adjs, int[][] prerequisites){
    int n = incLinkCounts.length;
    while (n-- > 0) adjs.add(new ArrayList<>());
    for (int[] edge : prerequisites) {
        incLinkCounts[edge[0]]++;
        adjs.get(edge[1]).add(edge[0]);
    }
}
How can we obtain a topological sort order of a DAG?

We observe that if a node has incoming edges, it has prerequisites. Therefore, the first few in the order must be those with no prerequisites, i.e. no incoming edges. Any non-empty DAG must have at least one node without incoming links. You can draw a small graph to convince yourself. If we visit these few and remove all edges attached to them, we are left with a smaller DAG, which is the same problem. This will then give our BFS solution.

private int[] solveByBFS(int[] incLinkCounts, List<List<Integer>> adjs){
    int[] order = new int[incLinkCounts.length];
    Queue<Integer> toVisit = new ArrayDeque<>();
    for (int i = 0; i < incLinkCounts.length; i++) {
        if (incLinkCounts[i] == 0) toVisit.offer(i);
    }
    int visited = 0;
    while (!toVisit.isEmpty()) {
        int from = toVisit.poll();
        order[visited++] = from;
        for (int to : adjs.get(from)) {
            incLinkCounts[to]--;
            if (incLinkCounts[to] == 0) toVisit.offer(to);
        }
    }
    return visited == incLinkCounts.length ? order : new int[0];
}
Another way to think about it is the last few in the order must be those which are not prerequisites of other courses. Thinking it recursively means if one node has unvisited child node, you should visit them first before you put this node down in the final order array. This sounds like the post-order of a DFS. Since we are putting nodes down in the reverse order, we should reverse it back to correct ordering or use a stack.

private int[] solveByDFS(List<List<Integer>> adjs) {
    BitSet hasCycle = new BitSet(1);
    BitSet visited = new BitSet(adjs.size());
    BitSet onStack = new BitSet(adjs.size());
    Deque<Integer> order = new ArrayDeque<>();
    for (int i = adjs.size() - 1; i >= 0; i--) {
        if (visited.get(i) == false && hasOrder(i, adjs, visited, onStack, order) == false) return new int[0];
    }
    int[] orderArray = new int[adjs.size()];
    for (int i = 0; !order.isEmpty(); i++) orderArray[i] = order.pop();
    return orderArray;
}

private boolean hasOrder(int from, List<List<Integer>> adjs, BitSet visited, BitSet onStack, Deque<Integer> order) {
    visited.set(from);
    onStack.set(from);
    for (int to : adjs.get(from)) {
        if (visited.get(to) == false) {
            if (hasOrder(to, adjs, visited, onStack, order) == false) return false;
        } else if (onStack.get(to) == true) {
            return false;
        }
    }
    onStack.clear(from);
    order.push(from);
    return true;
}
*/



/*Using DFS postorder
I replaced the two boolean arrays with one int array where I track the state -

0 - denotes not visited
1 - denotes visiting
2 - denotes visited

Here is the modified code:

public int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> adj = new ArrayList<List<Integer>>(numCourses);
    for (int i = 0; i < numCourses; i++) adj.add(i, new ArrayList<>());
    for (int i = 0; i < prerequisites.length; i++) adj.get(prerequisites[i][1]).add(prerequisites[i][0]);
    int[] visited = new int[numCourses];
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < numCourses; i++) {
        if (!topologicalSort(adj, i, stack, visited)) return new int[0];
    }
    int i = 0;
    int[] result = new int[numCourses];
    while (!stack.isEmpty()) {
        result[i++] = stack.pop();
    }
    return result;
}

private boolean topologicalSort(List<List<Integer>> adj, int v, Stack<Integer> stack, int[] visited) {
    if (visited[v] == 2) return true;
    if (visited[v] == 1) return false;
    visited[v] = 1;
    for (Integer u : adj.get(v)) {
        if (!topologicalSort(adj, u, stack, visited)) return false;
    }
    visited[v] = 2;
    stack.push(v);
    return true;
}
*/
