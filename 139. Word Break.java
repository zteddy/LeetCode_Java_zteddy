public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
    	int num = wordDict.size();
    	int possibility = s.length()-1;
    	boolean[] DP = new boolean[possibility][num];
    	for(int i = 0; i < num; i++){
    		for(int j = 0; j < possibility; j++){

    		}
    	}

    }
}


//Using DP
public class Solution {
    public boolean wordBreak(String s, Set<String> dict) {

        boolean[] f = new boolean[s.length() + 1];
        f[0] = true;

        /* First DP
        for(int i = 1; i <= s.length(); i++){
            for(String str: dict){
                if(str.length() <= i){
                    if(f[i - str.length()]){
                        if(s.substring(i-str.length(), i).equals(str)){
                            f[i] = true;
                            break;
                        }
                    }
                }
            }
        }*/

        //Second DP
        for(int i=1; i <= s.length(); i++){
            for(int j=0; j < i; j++){
                if(f[j] && dict.contains(s.substring(j, i))){
                    f[i] = true;
                    break;
                }
            }
        }

        return f[s.length()];
    }
}



//Using BFS
People have posted elegant solutions using DP. The solution I post below using BFS is no better than those. Just to share some new thoughts.

We can use a graph to represent the possible solutions. The vertices of the graph are simply the positions of the first characters of the words and each edge actually represents a word. For example, the input string is "nightmare", there are two ways to break it, "night mare" and "nightmare". The graph would be

0-->5-->9

|__ __ _^

The question is simply to check if there is a path from 0 to 9. The most efficient way is traversing the graph using BFS with the help of a queue and a hash set. The hash set is used to keep track of the visited nodes to avoid repeating the same work.

For this problem, the time complexity is O(n^2) and space complexity is O(n), the same with DP. This idea can be used to solve the problem word break II. We can simple construct the graph using BFS, save it into a map and then find all the paths using DFS.

public boolean wordBreak(String s, Set<String> dict) {
    if (dict.contains(s)) return true;
    Queue<Integer> queue = new LinkedList<Integer>();
    queue.offer(0);
    // use a set to record checked index to avoid repeated work.
    // This is the key to reduce the running time to O(N^2).
    Set<Integer> visited = new HashSet<Integer>();
    visited.add(0);
    while (!queue.isEmpty()) {
        int curIdx = queue.poll();
        for (int i = curIdx+1; i <= s.length(); i++) {
            if (visited.contains(i)) continue;
            if (dict.contains(s.substring(curIdx, i))) {
                if (i == s.length()) return true;
                queue.offer(i);
                visited.add(i);
            }
        }
    }
    return false;
}



//Using DFS
I write this method by what I learned from @mahdy in his post Decode Ways

Use a set to record all position that cannot find a match in dict. That cuts down the run time of DFS to O(n^2)

public class Solution {
    public boolean wordBreak(String s, Set<String> dict) {
        // DFS
        Set<Integer> set = new HashSet<Integer>();
        return dfs(s, 0, dict, set);
    }

    private boolean dfs(String s, int index, Set<String> dict, Set<Integer> set){
        // base case
        if(index == s.length()) return true;
        // check memory
        if(set.contains(index)) return false;
        // recursion
        for(int i = index+1;i <= s.length();i++){
            String t = s.substring(index, i);
            if(dict.contains(t))
                if(dfs(s, i, dict, set))
                    return true;
                else
                    set.add(i);
        }
        set.add(index);
        return false;
    }
}



//Approach #2 Recursion with memorization [Accepted]
In the previous approach we can see that many subproblems were redundant, i.e we were calling the recursive function multiple times for a particular string. To avoid this we can use memorization method, where an array memomemo is used to store the result of the subproblems. Now, when the function is called again for a particular string, value will be fetched and returned using the memomemo array, if its value has been already evaluated.

With memorization many redundant subproblems are avoided and recursion tree is pruned and thus it reduces the time complexity by a large factor.

Java

public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        int[] memo = new int[s.length() + 1];
        Arrays.fill(memo, -1);
        return word_Break(s, new HashSet(wordDict), 0, memo) == 1;
    }
    public int word_Break(String s, Set<String> wordDict, int start, int[] memo) {
        if (memo[start] >= 0) {
            return memo[start];
        }
        int res = 0;
        if (start == s.length()) {
            res = 1;
        }
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordDict.contains(s.substring(start, end))) {
                res |= word_Break(s, wordDict, end, memo);
            }
        }
        memo[start] = res;
        return res;
    }
}



//Approach #3 Using Breadth-First-Search [Accepted]
Another approach is to use Breadth-First-Search. Visualize the string as a tree where each node represents the prefix upto index endend. Two nodes are connected only if the substring between the indices linked with those nodes is also a valid string which is present in the dictionary. In order to form such a tree, we start with the first character of the given string (say ss) which acts as the root of the tree being formed and find every possible substring starting with that character which is a part of the dictionary. Further, the ending index (say ii) of every such substring is pushed at the back of a queue which will be used for Breadth First Search. Now, we pop an element out from the front of the queue and perform the same process considering the string s(i+1,end)s(i+1,end) to be the original string and the popped node as the root of the tree this time. This process is continued, for all the nodes appended in the queue during the course of the process. If we are able to obtain the last element of the given string as a node (leaf) of the tree, this implies that the given string can be partitioned into substrings which are all a part of the given dictionary.

public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordDictSet=new HashSet(wordDict);
        Queue<Integer> queue = new LinkedList<>();
        int[] visited = new int[s.length()];
        queue.add(0);
        while (!queue.isEmpty()) {
            int start = queue.remove();
            if (visited[start] == 0) {
                for (int end = start + 1; end <= s.length(); end++) {
                    if (wordDictSet.contains(s.substring(start, end))) {
                        queue.add(end);
                        if (end == s.length()) {
                            return true;
                        }
                    }
                }
                visited[start] = 1;
            }
        }
        return false;
    }
}



//Approach #4 Using Dynamic Programming [Accepted]:

public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordDictSet=new HashSet(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
