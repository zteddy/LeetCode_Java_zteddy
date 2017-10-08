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

//WA


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






//Summary
Approach #1 Brute Force [Time Limit Exceeded]

Algorithm

The naive approach to solve this problem is to use recursion and backtracking. For finding the solution, we check every possible prefix of that string in the dictionary of words, if it is found in the dictionary, then the recursive function is called for the remaining portion of that string. And, if in some function call it is found that the complete string is in dictionary, then it will return true.

Java

public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        return word_Break(s, new HashSet(wordDict), 0);
    }
    public boolean word_Break(String s, Set<String> wordDict, int start) {
        if (start == s.length()) {
            return true;
        }
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordDict.contains(s.substring(start, end)) && word_Break(s, wordDict, end)) {
                return true;
            }
        }
        return false;
    }
}
Complexity Analysis

Time complexity : O(n^n)O(n
​n
​​ ). Consider the worst case where s=``\text{aaaaaaa}"s=‘‘aaaaaaa" and every prefix of ss is present in the dictionary of words, then the recursion tree can grow upto n^nn
​n
​​ .

Space complexity : O(n)O(n). The depth of the recursion tree can go upto nn.

Approach #2 Recursion with memoization [Accepted]

Algorithm

In the previous approach we can see that many subproblems were redundant, i.e we were calling the recursive function multiple times for a particular string. To avoid this we can use memoization method, where an array memomemo is used to store the result of the subproblems. Now, when the function is called again for a particular string, value will be fetched and returned using the memomemo array, if its value has been already evaluated.

With memoization many redundant subproblems are avoided and recursion tree is pruned and thus it reduces the time complexity by a large factor.

Java

public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        return word_Break(s, new HashSet(wordDict), 0, new Boolean[s.length()]);
    }
    public boolean word_Break(String s, Set<String> wordDict, int start, Boolean[] memo) {
        if (start == s.length()) {
            return true;
        }
        if (memo[start] != null) {
            return memo[start];
        }
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordDict.contains(s.substring(start, end)) && word_Break(s, wordDict, end, memo)) {
                return memo[start] = true;
            }
        }
        return memo[start] = false;
    }
}
Complexity Analysis

Time complexity : O(n^2)O(n
​2
​​ ). Size of recursion tree can go up to n^2n
​2
​​ .

Space complexity : O(n)O(n). The depth of recursion tree can go up to nn.

Approach #3 Using Breadth-First-Search [Accepted]

Algorithm

Another approach is to use Breadth-First-Search. Visualize the string as a tree where each node represents the prefix upto index endend. Two nodes are connected only if the substring between the indices linked with those nodes is also a valid string which is present in the dictionary. In order to form such a tree, we start with the first character of the given string (say ss) which acts as the root of the tree being formed and find every possible substring starting with that character which is a part of the dictionary. Further, the ending index (say ii) of every such substring is pushed at the back of a queue which will be used for Breadth First Search. Now, we pop an element out from the front of the queue and perform the same process considering the string s(i+1,end)s(i+1,end) to be the original string and the popped node as the root of the tree this time. This process is continued, for all the nodes appended in the queue during the course of the process. If we are able to obtain the last element of the given string as a node (leaf) of the tree, this implies that the given string can be partitioned into substrings which are all a part of the given dictionary.

1 / 7
Java

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
Complexity Analysis

Time complexity : O(n^2)O(n
​2
​​ ). For every starting index, the search can continue till the end of the given string.

Space complexity : O(n)O(n). Queue of atmost nn size is needed.

Approach #4 Using Dynamic Programming [Accepted]:

Algorithm

The intuition behind this approach is that the given problem (ss) can be divided into subproblems s1s1 and s2s2. If these subproblems individually satisfy the required conditions, the complete problem, ss also satisfies the same. e.g. "catsanddog" can be split into two substrings "catsand", "dog". The subproblem "catsand" can be further divided into "cats","and", which individually are a part of the dictionary making "catsand" satisfy the condition. Going further backwards, "catsand", "dog" also satisfy the required criteria individually leading to the complete string "catsanddog" also to satisfy the criteria.

Now, well move onto the process of \text{dp}dp array formation. We make use of \text{dp}dp array of size n+1n+1, where nn is the length of the given string. We also use two index pointers ii and jj, where ii refers to the length of the substring (s's
​′
​​ ) considered currently starting from the beginning, and jj refers to the index partitioning the current substring (s's
​′
​​ ) into smaller substrings s'(0,j)s
​′
​​ (0,j) and s'(j+1,i)s
​′
​​ (j+1,i). To fill in the \text{dp}dp array, we initialize the element \text{dp}[0]dp[0] as \text{true}true, since the null string is always present in the dictionary, and the rest of the elements of \text{dp}dp as \text{false}false. We consider substrings of all possible lengths starting from the beginning by making use of index ii. For every such substring, we partition the string into two further substrings s1's1
​′
​​  and s2's2
​′
​​  in all possible ways using the index jj (Note that the ii now refers to the ending index of s2s2
​′
​​ ). Now, to fill in the entry \text{dp}[i]dp[i], we check if the \text{dp}[j]dp[j] contains \text{true}true, i.e. if the substring s1's1
​′
​​  fulfills the required criteria. If so, we further check if s2's2
​′
​​  is present in the dictionary. If both the strings fulfill the criteria, we make \text{dp}[i]dp[i] as \text{true}true, otherwise as \text{false}false.

Java

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
Complexity Analysis

Time complexity : O(n^2)O(n
​2
​​ ). Two loops are their to fill \text{dp}dp array.

Space complexity : O(n)O(n). Length of pp array is n+1n+1.

