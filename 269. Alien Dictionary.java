public class Solution {
	ArrayList[] graph;
	int[] al = new int[26];
	public void graphConstructor(String[] words){
		graph = new ArrayList[26];
		for(int i = 0; i < 26; i++){
			graph[i] = new ArrayList();
		}
		// StringBuilder[] wb = new StringBuilder[words.length];
		// int maxL = -1;
		// for(int i = 0; i < words.length; i++){
		// 	wb[i] = new StringBuilder(words[i]);
		// 	if(words[i].length > maxL) maxL = words[i];
		// }
		int maxL = -1;
		for(int i = 0; i < words.length; i++){
			if(words[i].length() > maxL) maxL = words[i].length();
		}

		for(int i = 0; i < maxL; i++){
			char prev = '1';
			for(int j = 0; j < words.length; j++){
				if(words[j].length() < i+1){
					prev = '1';
					continue;
				}
				al[words[j].charAt(i)-'a']++;
				if(prev != '1' && prev != words[j].charAt(i)){
				//if(prev != '1'){
					if(!graph[prev-'a'].contains(words[j].charAt(i)-'a')){
						graph[prev-'a'].add(words[j].charAt(i)-'a');
					}
				}
				prev = words[j].charAt(i);
			}
		}
	}
    public String alienOrder(String[] words) {
    	graphConstructor(words);
    	int[] indegree = new int[26];
    	// for(int i = 0; i < 26; i++){
    	// 	if(al[i] == 0) indegree[i] = -1;
    	// }
    	for(int i = 0; i < 26; i++){
    		for(int j = 0; j < graph[i].size(); j++){
    			indegree[(int)graph[i].get(j)]++;
    		}
    	}
    	for(int i = 0; i < 26; i++){
    		if(indegree[i] == 0 && graph[i].size() == 0) indegree[i] = -1;
    	}
    	StringBuilder result = new StringBuilder("");
    	Queue q = new LinkedList();
    	for(int i = 0; i < 26; i++){
    		if(indegree[i] == 0) q.offer(i);
    	}
    	while(!q.isEmpty()){
    		int temp = (int)q.poll();
    		al[temp] = 0;
    		result.append((char)('a'+temp));
    		for(int j = 0; j < graph[temp].size(); j++){
    			indegree[(int)graph[temp].get(j)]--;
    			if(indegree[(int)graph[temp].get(j)] == 0) q.offer((int)graph[temp].get(j));
    		}
    	}

    	for(int i = 0; i < 26; i++){
    		if(indegree[i] > 0) return new String("");
    	}
    	// if(result.toString().length() == 0 && words.length != 0) return words[0];
    	for(int i = 0; i < 26; i++){
    		if(al[i] >0) result.append((char)('a'+i));
    	}
    	return result.toString();
    }
}
//WA
//构建图的方法好像出了问题



//Using correct graph constructor
@Bruce Finally I understand it. I think it is not so obvious for Chinese, so I post my understanding here:

For somebody who is not familiar with dictionary like me.
The reason "wrtkj", "wrt" should return wrong is as following:
Because "wrtkj" appears before than "wrt"(pay attention to "wrt" is same as prefix of "wrtkj"), this is not allowed lexicographically.
"wrtkj"
"wrt"

For dictionary for language like English.

"wrt" must appears before "wrtkj", like this:
"wrt"
"wrtkj"

Means your code should check this scenario.

public String alienOrder(String[] words) {
    Map<Character, Set<Character>> map = new HashMap<Character, Set<Character>>();
    Map<Character, Integer> degree  = new HashMap();
    StringBuilder res = new StringBuilder();
    if(words == null || words.length == 0) return res.toString();

    for(String s: words){
        for(char c: s.toCharArray()){
            degree.put(c,0);
        }
    }

    for(int i=0; i<words.length-1; i++){
        String cur = words[i];
        String next = words[i+1];
        int length = Math.min(cur.length(), next.length());

        for(int j = 0; j < length; j++){
            char c1 = cur.charAt(j);
            char c2 = next.charAt(j);
            if(c1 != c2){
                Set<Character> set = new HashSet();
                if(map.containsKey(c1)) set=map.get(c1);
                if(!set.contains(c2)){
                    set.add(c2);
                    map.put(c1, set);
                    degree.put(c2, degree.get(c2)+1);
                }
                break;
            } else {
                if(j+1 <= cur.length()-1 && j+1 > next.length()-1) return "";
            }
        }
    }

    Queue<Character> q = new LinkedList<Character>();
    for(char c: degree.keySet()){
        if(degree.get(c)==0) q.add(c);
    }
    while(!q.isEmpty()){
        char c = q.remove();
        res.append(c);
        if(map.containsKey(c)){
            for(char c2: map.get(c)){
                degree.put(c2,degree.get(c2)-1);
                if(degree.get(c2)==0) q.add(c2);
            }
        }
    }

    if(res.length() != degree.size()) return "";
    return res.toString();
}

Really nice solution! Let me try to explain the code with example in the problem description:

First, build a degree map for each character in all the words:

w:0
r:0
t:0
f:0
e:0
Then build the hashmap by comparing the adjacent words, the first character that is different between two adjacent words reflect the lexicographical order. For example:

 "wrt",
 "wrf",
    first different character is 3rd letter, so t comes before f

 "wrf",
 "er",
    first different character is 1rd letter, so w comes before e
The characters in set come after the key. x->y means letter x comes before letter y. x -> set: y,z,t,w means x comes before all the letters in the set. The final HashMap "map" looks like.

t -> set: f
w -> set: e
r -> set: t
e -> set: r
and final HashMap "degree" looks like, the number means "how many letters come before the key":

w:0
r:1
t:1
f:1
e:1
Then use Karns aglorithm to do topological sort. This is essentially BFS.
https://en.wikipedia.org/wiki/Topological_sorting



//Using DFS
The key to this problem is:

A topological ordering is possible if and only if the graph has no directed cycles
Lets build a graph and perform a DFS. The following states made things easier.

visited[i] = -1. Not even exist.
visited[i] = 0. Exist. Non-visited.
visited[i] = 1. Visiting.
visited[i] = 2. Visited.
Similarly, there is another simple BFS version.

private final int N = 26;
public String alienOrder(String[] words) {
    boolean[][] adj = new boolean[N][N];
    int[] visited = new int[N];
    buildGraph(words, adj, visited);

    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < N; i++) {
        if(visited[i] == 0) {                 // unvisited
            if(!dfs(adj, visited, sb, i)) return "";
        }
    }
    return sb.reverse().toString();
}

public boolean dfs(boolean[][] adj, int[] visited, StringBuilder sb, int i) {
    visited[i] = 1;                            // 1 = visiting
    for(int j = 0; j < N; j++) {
        if(adj[i][j]) {                        // connected
            if(visited[j] == 1) return false;  // 1 => 1, cycle
            if(visited[j] == 0) {              // 0 = unvisited
                if(!dfs(adj, visited, sb, j)) return false;
            }
        }
    }
    visited[i] = 2;                           // 2 = visited



    //因为是在有向图里找环，如果单纯记visit的话找出的环可能只是无向意义上的环。所以要找“正在用”的node，这样才是有向意义下的环。





    sb.append((char) (i + 'a'));
    return true;
}

public void buildGraph(String[] words, boolean[][] adj, int[] visited) {
    Arrays.fill(visited, -1);                 // -1 = not even existed
    for(int i = 0; i < words.length; i++) {
        for(char c : words[i].toCharArray()) visited[c - 'a'] = 0;
        if(i > 0) {
            String w1 = words[i - 1], w2 = words[i];
            int len = Math.min(w1.length(), w2.length());
            for(int j = 0; j < len; j++) {
                char c1 = w1.charAt(j), c2 = w2.charAt(j);
                if(c1 != c2) {
                    adj[c1 - 'a'][c2 - 'a'] = true;
                    break;
                }
            }
        }
    }
}




//Another BFS
public String alienOrder(String[] words) {
    List<Set<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < 26; i++) {
        adj.add(new HashSet<Integer>());
    }
    int[] degree = new int[26];
    Arrays.fill(degree, -1);

    for (int i = 0; i < words.length; i++) {
        for (char c : words[i].toCharArray()) {
            if (degree[c - 'a'] < 0) {
                degree[c - 'a'] = 0;
            }
        }
        if (i > 0) {
            String w1 = words[i - 1], w2 = words[i];
            int len = Math.min(w1.length(), w2.length());
            for (int j = 0; j < len; j++) {
                int c1 = w1.charAt(j) - 'a', c2 = w2.charAt(j) - 'a';
                if (c1 != c2) {
                    if (!adj.get(c1).contains(c2)) {
                        adj.get(c1).add(c2);
                        degree[c2]++;
                    }
                    break;
                }
                if (j == w2.length() - 1 && w1.length() > w2.length()) { // "abcd" -> "ab"
                    return "";
                }
            }
        }
    }

    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < degree.length; i++) {
        if (degree[i] == 0) {
            q.add(i);
        }
    }

    StringBuilder sb = new StringBuilder();
    while (!q.isEmpty()) {
        int i = q.poll();
        sb.append((char) ('a'  + i));
        for (int j : adj.get(i)) {
            degree[j]--;
            if (degree[j] == 0) {
                q.add(j);
            }
        }
    }

    for (int d : degree) {
        if (d > 0) {
            return "";
        }
    }

    return sb.toString();
}
