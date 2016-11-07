/*WA
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
*/

public class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
    	int indegree[] = new int[numCourses];
    	for(int i = 0; i < prerequisites.length; i++){
    		indegree[prerequisites[i][0]]++;
    	}
    	Queue<Integer> q = new LinkedList<>(); 
    	for(int i = 0; i < numCourses; i++){
    		if(indegree[i] == 0){
    			q.offer(i);
    		}
    	}

    	while(!q.isEmpty()){
    		int temp = q.poll();
	    	for(int i = 0; i < prerequisites.length; i++){
    			if(prerequisites[i][1] == temp){
    				indegree[prerequisites[i][0]]--;
    				if(indegree[prerequisites[i][0]] == 0){
    					q.offer(prerequisites[i][0]);
    				}
    			}
    		}
    	}

    	boolean result = true;
    	for(int i = 0; i < numCourses; i++){
    		if(indegree[i] != 0) result = false;
    	}

    	return result;
    }
}

/*BFS & DFS
According to my code test, BFS is much faster than DFS. 
From my perspective DFS searches more branches. 
EX: 1->3->4 //1->5->3 the first branch we need search 3's children, in second we still need to do so.

BFS:

public class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList[] graph = new ArrayList[numCourses];
        int[] degree = new int[numCourses];
        Queue queue = new LinkedList();
        int count=0;
        
        for(int i=0;i<numCourses;i++)
            graph[i] = new ArrayList();
            
        for(int i=0; i<prerequisites.length;i++){
            degree[prerequisites[i][1]]++;
            graph[prerequisites[i][0]].add(prerequisites[i][1]);
        }
        for(int i=0; i<degree.length;i++){
            if(degree[i] == 0){
                queue.add(i);
                count++;
            }
        }
        
        while(queue.size() != 0){
            int course = (int)queue.poll();
            for(int i=0; i<graph[course].size();i++){
                int pointer = (int)graph[course].get(i);
                degree[pointer]--;
                if(degree[pointer] == 0){
                    queue.add(pointer);
                    count++;
                }
            }
        }
        if(count == numCourses)
            return true;
        else    
            return false;
    }
}

DFS with detailed comments:

public class Solution {

	public boolean canFinish(int numCourses, int[][] prerequisites) {
	    if(numCourses == 0 || prerequisites == null || prerequisites.length == 0) return true; //??
	    
	    // create the array lists to represent the courses
	    List<List<Integer>> courses = new ArrayList<List<Integer>>(numCourses);
	    for(int i=0; i<numCourses; i++) {
	        courses.add(new ArrayList<Integer>());
	    }
	    
	    // create the dependency graph
	    for(int i=0; i<prerequisites.length; i++) {
	        courses.get(prerequisites[i][1]).add(prerequisites[i][0]);
	    }

	    int[] visited = new int[numCourses]; 
	    
	    // dfs visit each course
	    for(int i=0; i<numCourses; i++) {
	           if (!dfs(i,courses, visited)) return false; 
	    }
	    
	    return true;
	}

	private boolean dfs(int course, List<List<Integer>> courses, int[] visited) {
	    
	    visited[course] = 1; // mark it being visited
	    
	    List<Integer> eligibleCourses = courses.get(course); // get its children
	    
	    // dfs its children
	    for(int i=0; i<eligibleCourses.size(); i++) {
	        int eligibleCourse = eligibleCourses.get(i).intValue();
	        
	        if(visited[eligibleCourse] == 1) return false; // has been visited while visiting its children - cycle !!!!
	        if(visited[eligibleCourse]  == 0) { // not visited
	           if (!dfs(eligibleCourse,courses, visited)) return false; 
	        }

	    }
	    
	    visited[course] = 2; // mark it done visiting
	    return true;
	    
	}
}
*/

/*Another DFS
DFS Solution:

The basic idea is using DFS to check if the current node was already included in the traveling path. 
In this case, we need to convert graph presentation from edge list to adjacency list first, and then check if there's cycle existing in any subset. 
Because tree is a connected graph, we can start from any node.
The graph is possibly not connected, so need to check every node.
To do memorization and pruning, a HashMap is used to save the previous results for the specific node.

HashMap<Integer, Boolean>memo = new HashMap<Integer, Boolean>();//Memorization HashMap for DFS pruning 
public boolean canFinish(int n, int[][] edges) {		 
    if (edges.length != 0) { 
        HashMap<Integer, HashSet<Integer>> neighbors = new HashMap<Integer, HashSet<Integer>>(); // Neighbors of each node
        HashSet<Integer>curPath = new HashSet<Integer>(); // Nodes on the current path
        for (int i = 0; i < edges.length; i++) {// Convert graph presentation from edge list to adjacency list 
            if (!neighbors.containsKey(edges[i][1])) 
                neighbors.put(edges[i][1], new HashSet<Integer>());            
            neighbors.get(edges[i][1]).add(edges[i][0]);
        }
        
        for (int a[] : edges) // The graph is possibly not connected, so need to check every node.
	        if (hasCycle(neighbors, a[0], -1, curPath))// Use DFS method to check if there's cycle in any curPath
	            return false;
    } 
    return true;
}     

boolean hasCycle(HashMap<Integer, HashSet<Integer>> neighbors, int kid, int parent, HashSet<Integer>curPath) {
	if (memo.containsKey(kid)) return memo.get(kid);
    if (curPath.contains(kid)) return true;// The current node is already in the set of the current path	    
    if (!neighbors.containsKey(kid)) return false;	   
    
    curPath.add(kid);
    for (Integer neighbor : neighbors.get(kid)) {
    	boolean hasCycle = hasCycle(neighbors, neighbor, kid, curPath);// DFS
    	memo.put(kid, hasCycle);	        	
        if (hasCycle) return true;
    }
    curPath.remove(kid);	    
    return false;
}
*/