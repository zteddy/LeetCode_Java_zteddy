public class Solution {
	Map<String, Integer> indegree;
	Map<String, ArrayList<String>> graph;
	//List<String, ArrayList<String>> graph;
	List<String> result;
	Map<String, Integer> outdegree;
	public void graphConstructor(String[][] tickets){
		graph = new HashMap<>();
		outdegree = new HashMap<>();
		indegree = new HashMap<>();
		ArrayList<String> temp;
		for(int i = 0; i < tickets.length; i++){
			if(!graph.containsKey(tickets[i][0])){
				temp = new ArrayList<>();
				temp.add(tickets[i][1]);
				graph.put(tickets[i][0], temp);
			}
			else{
				graph.get(tickets[i][0]).add(tickets[i][1]);
			}
			if(!outdegree.containsKey(tickets[i][0])){
				outdegree.put(tickets[i][0], 1);
			}
			else{
				outdegree.put(tickets[i][0], outdegree.get(tickets[i][0])+1);
			}
			if(!outdegree.containsKey(tickets[i][1])){
				outdegree.put(tickets[i][1], 0);
			}
			if(!indegree.containsKey(tickets[i][1])){
				indegree.put(tickets[i][1], 1);
			}
			else{
				indegree.put(tickets[i][1], indegree.get(tickets[i][1])+1);
			}
			if(!indegree.containsKey(tickets[i][0])){
				indegree.put(tickets[i][0], 0);
			}
		}
		//return graph;
	}
	public void iDFS(String now){
		result.add(now);
		if(graph.get(now) == null) return ;
		if(graph.get(now).size() == 0) return ;
		ArrayList<String> temp = new ArrayList();
		temp.addAll(graph.get(now));
		Collections.sort(temp);
		if(temp.size() == 1){
			graph.get(now).remove(temp.get(0));
			iDFS(temp.get(0));
		}
		else{
			if(outdegree.get(temp.get(0)) == 0){
				for(int i = 1; i < temp.size(); i++){
					if(indegree.get(temp.get(i)) == 1){
						graph.get(now).remove(temp.get(i));
						iDFS(temp.get(i));
						return ;
					}
				}
				graph.get(now).remove(temp.get(1));
				iDFS(temp.get(1));
			}
			else{
				for(int i = 0; i < temp.size(); i++){
					if(indegree.get(temp.get(i)) == 1){
						graph.get(now).remove(temp.get(i));
						iDFS(temp.get(i));
						return ;
					}
				}
				graph.get(now).remove(temp.get(0));
				iDFS(temp.get(0));
			}
		}
		// for(int i = 0; i < temp.size(); i++){
		// 	if(outdegree.get(temp.get(i)) == 0){
		// 		if(i != temp.size() -1){
		// 			String t = temp.get(i+1);
		// 			temp.set(i+1,temp.get(i));
		// 			temp.set(i,t);
		// 		}
		// 		// for(int j = i; j < temp.size(); j++){
		// 		// 	if(j+1 < temp.size()){
		// 		// 		String t = temp.get(j+1);
		// 		// 		temp.set(j+1,temp.get(j));
		// 		// 		temp.set(j,t);
		// 		// 	}
		// 		// }
		// 	}
		// 	graph.get(now).remove(temp.get(i));
		// 	iDFS(temp.get(i));
		// }
	}

    public List<String> findItinerary(String[][] tickets) {
    	graphConstructor(tickets);
    	for(ArrayList t : graph.values()){
    		Collections.sort(t);
    	}
    	result = new ArrayList<>();
    	//一定要先好好读题！！！！
    	// Queue<String> q = new LinkedList<>();
    	// List<String> lll = new ArrayList<>();
    	// lll.addAll(indegree.keySet());     //这里是精髓
    	// for(String s : lll){
    	// Collections.sort(lll);
    	// 	if(indegree.get(s) == 0) q.offer(s);
    	// }
    	// List<String> result = new ArrayList<>();
    	// while(!q.isEmpty()){
    	// 	String temp = q.poll();
    	// 	result.add(temp);
    	// 	if(!graph.containsKey(temp)) continue;
    	// 	for(String to : graph.get(temp)){
    	// 		indegree.put(to, indegree.get(to)-1);
    	// 		if(indegree.get(to) == 0) q.offer(to);
    	// 	}
    	// }

    	iDFS("JFK");
    	return result;
    }
}

//WA 几次走错方向
//还是思维受限了，这更本就不是一到拓扑排序题，因为有环。。。。
//也不是一个检查是否有欧拉路径的问题，应为题目保证了有
//所以是一个求最小字典序的欧拉路径的问题





//Using dfs (backtracking?)

Explanation

First keep going forward until you get stuck. Thats a good main path already. Remaining tickets form cycles which are found on the way back and get merged into that main path. By writing down the path backwards when retreating from recursion, merging the cycles into the main path is easy - the end part of the path has already been written, the start part of the path hasnt been written yet, so just write down the cycle now and then keep backwards-writing the path.

Example:
JFK A
JFK D
A C
C D
C JFK
D B
D A
B C
From JFK we first visit JFK -> A -> C -> D -> A. There we re stuck, so we write down A as the end of the route and retreat back to D. There we see the unused ticket to B and follow it: D -> B -> C -> JFK -> D. Then we re stuck again, retreat and write down the airports while doing so: Write down D before the already written A, then JFK before the D, etc. When we re back from our cycle at D, the written route is D -> B -> C -> JFK -> D -> A. Then we retreat further along the original path, prepending C, A and finally JFK to the route, ending up with the route JFK -> A -> C -> D -> B -> C -> JFK -> D -> A.

//很难懂，但可以先记住，post-order DFS， 并且从后往前加node，可以构建欧拉路径

Recursive version:
public List<String> findItinerary(String[][] tickets) {
    for (String[] ticket : tickets)
        targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
    visit("JFK");
    return route;
}

Map<String, PriorityQueue<String>> targets = new HashMap<>();
List<String> route = new LinkedList();

void visit(String airport) {
    while(targets.containsKey(airport) && !targets.get(airport).isEmpty())
        visit(targets.get(airport).poll());
    route.add(0, airport);
}


Iterative version:
public List<String> findItinerary(String[][] tickets) {
    Map<String, PriorityQueue<String>> targets = new HashMap<>();
    for (String[] ticket : tickets)
        targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
    List<String> route = new LinkedList();
    Stack<String> stack = new Stack<>();
    stack.push("JFK");
    while (!stack.empty()) {
        while (targets.containsKey(stack.peek()) && !targets.get(stack.peek()).isEmpty())
            stack.push(targets.get(stack.peek()).poll());
        route.add(0, stack.pop());
    }
    return route;
}





//Same as above
All the airports are vertices and tickets are directed edges. Then all these tickets form a directed graph.

The graph must be Eulerian since we know that a Eulerian path exists.

Thus, start from "JFK", we can apply the Hierholzers algorithm to find a Eulerian path in the graph which is a valid reconstruction.

Since the problem asks for lexical order smallest solution, we can put the neighbors in a min-heap. In this way, we always visit the smallest possible neighbor first in our trip.

public class Solution {

    Map<String, PriorityQueue<String>> flights;
    LinkedList<String> path;

    public List<String> findItinerary(String[][] tickets) {
        flights = new HashMap<>();
        path = new LinkedList<>();
        for (String[] ticket : tickets) {
            flights.putIfAbsent(ticket[0], new PriorityQueue<>()); //感觉这个很实用
            flights.get(ticket[0]).add(ticket[1]);
        }
        dfs("JFK");
        return path;
    }

    public void dfs(String departure) {
        PriorityQueue<String> arrivals = flights.get(departure);
        while (arrivals != null && !arrivals.isEmpty())
            dfs(arrivals.poll());
        path.addFirst(departure);
    }
}



//Using iterative
Noticed some folks are using Hierholzers algorithm to find a Eulerian path.

My solution is similar, considering this passenger has to be physically in one place before move to another airport, we are considering using up all tickets and choose lexicographically smaller solution if in tie as two constraints.

Thinking as that passenger, the passenger choose his/her flight greedy as the lexicographical order, once he/she figures out go to an airport without departure with more tickets at hand. the passenger will push current ticket in a stack and look at whether it is possible for him/her to travel to other places from the airport on his/her way.

Please let me know if you have any suggestions.

public List<String> findItinerary(String[][] tickets) {
    List<String> ans = new ArrayList<String>();
    if(tickets == null || tickets.length == 0) return ans;
    Map<String, PriorityQueue<String>> ticketsMap = new HashMap<>();
    for(int i = 0; i < tickets.length; i++) {
        if(!ticketsMap.containsKey(tickets[i][0])) ticketsMap.put(tickets[i][0], new PriorityQueue<String>());
        ticketsMap.get(tickets[i][0]).add(tickets[i][1]);
    }

    String curr = "JFK";
    Stack<String> drawBack = new Stack<String>();
    for(int i = 0; i < tickets.length; i++) {
        while(!ticketsMap.containsKey(curr) || ticketsMap.get(curr).isEmpty()) {
            drawBack.push(curr);
            curr = ans.remove(ans.size()-1);
        }
        ans.add(curr);
        curr = ticketsMap.get(curr).poll();
    }
    ans.add(curr);
    while(!drawBack.isEmpty()) ans.add(drawBack.pop());
    return ans;
}



//Using backtracking
public class Solution {
    private HashMap<String, List<String>> adjList = new HashMap<>();
    private LinkedList<String> route = new LinkedList<>();
    private int numTickets = 0;
    private int numTicketsUsed = 0;

    public List<String> findItinerary(String[][] tickets) {
        if (tickets == null || tickets.length == 0) return route;
        // build graph
        numTickets = tickets.length;
        for (int i = 0; i < tickets.length; ++i) {
            if (!adjList.containsKey(tickets[i][0])) {
                // create a new list
                List<String> list = new ArrayList<>();
                list.add(tickets[i][1]);
                adjList.put(tickets[i][0], list);
            } else {
                // add to existing list
                adjList.get(tickets[i][0]).add(tickets[i][1]);
            }
        }
        // sort vertices in the adjacency list so they appear in lexical order
        for (Map.Entry<String, List<String>> entry : adjList.entrySet()) {
            Collections.sort(entry.getValue());
        }

        // start DFS
        route.add("JFK");
        dfsRoute("JFK");
        return route;
    }

    private void dfsRoute(String v) {
        // base case: vertex v is not in adjacency list
        // v is not a starting point in any itinerary, or we would have stored it
        // thus we have reached end point in our DFS
        if (!adjList.containsKey(v)) return;
        List<String> list = adjList.get(v);
        for (int i = 0; i < list.size(); ++i) {
            String neighbor = list.get(i);
            // remove ticket(route) from graph
            list.remove(i);
            route.add(neighbor);
            numTicketsUsed++;
            dfsRoute(neighbor);
            // we only return when we have used all tickets
            if (numTickets == numTicketsUsed) return;
            // otherwise we need to revert the changes and try other tickets
            list.add(i, neighbor);
            // This line took me a long time to debug
            // we must remove the last airport, since in an itinerary, the same airport can appear many times!!
            route.removeLast();
            numTicketsUsed--;
        }
    }
}



//Using backtracking2
Calculate Euler path. For each point, try to DFS its out-going point. There is chance that a DFS wont get a result. So, we do backtrack. Out-going points should keep ascending order.

public static List<String> findItinerary(String[][] tickets) {
    // construct graph
    HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
    ArrayList<String> al = null;
    for (String[] ticket : tickets) {
        al = graph.get(ticket[0]);
        if (al == null) {
            al = new ArrayList<String>();
            graph.put(ticket[0], al);
        }
        al.add(ticket[1]);
    }
    for (ArrayList<String> curr : graph.values()) {
        Collections.sort(curr);
    }
    ArrayList<String> ans = new ArrayList<>();
    itineraryHelper("JFK", ans, graph, tickets.length + 1);
    return ans;
}

// n is how many stops totally should contain
public static boolean itineraryHelper(String curr, List<String> ans, HashMap<String, ArrayList<String>> graph, int n) {
    ans.add(curr);
    if (ans.size() >= n) {
        return true;
    }
    if (!graph.containsKey(curr) || graph.get(curr).isEmpty()) {
        return false;
    }
    ArrayList<String> arrivals = graph.get(curr);
    for (int i = 0; i < arrivals.size(); i++) { // iterate each arrival point
        String arrival = graph.get(curr).remove(i);
        if (itineraryHelper(arrival, ans, graph, n)) {
            return true;
        }
        ans.remove(ans.size() - 1); // backtrack
        arrivals.add(i, arrival);
    }
    return false;
}
