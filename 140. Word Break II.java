public class Solution {

    public void backtracking(String s, List<String> wordDict, List<String> result, List<String> temp, Map<String,List<String>> memo,  int endIndex){
        if(endIndex == s.length()){
            result.add(String.join(" ", temp));
            return;
        }
        List<String> newtemp = new ArrayList<>(temp);
        for(int i = endIndex+1; i <= s.length(); i++){
            if(memo.containsKey(s.substring(endIndex,i))){
                List<String> m = new ArrayList<>(memo.get(s.substring(endIndex,i)));
                for(int j = 0; j < m.size(); j++){
                    newtemp.add(m.get(j));
                }
                if(newtemp.size()>1){
                    memo.put(s.substring(0,i),new ArrayList<>(newtemp));
                }
                backtracking(s,wordDict,result,newtemp,memo,i);
                for(int j = 0; j < m.size(); j++){
                    newtemp.remove(newtemp.size()-1);
                }
                // newtemp.removeAll(m);
                continue;

            }
            else if(wordDict.contains(s.substring(endIndex,i))){
                newtemp.add(s.substring(endIndex,i));
                if(newtemp.size()>1){
                    memo.put(s.substring(0,i),new ArrayList<>(newtemp));
                }
                backtracking(s,wordDict,result,newtemp,memo,i);
                newtemp.remove(newtemp.size()-1);
            }
        }
    }

    public List<String> wordBreak(String s, List<String> wordDict) {

        List<String> result = new ArrayList<>();
        Map<String,List<String>> memo = new HashMap<>();

        backtracking(s,wordDict,result,new ArrayList<>(),memo,0);

        Collections.sort(result);

        return result;
    }
}



//WA



//Summary
Summary

Given a string ss and a dictionary of words dictdict, add spaces in ss to construct every possible sentence such that each word is valid as per the given dictionary. Return all such possible sentences.

Solution

Approach #1 Brute Force [Time Limit Exceeded]

Algorithm

The naive approach to solve this problem is to use recursion. For finding the solution, we check every possible prefix of that string (ss) in the dictionary of words, if it is found in the dictionary (say s1s1), then the recursive function is called for the remaining portion of that string. This function returns the prefix s1s1 appended by the result of the recursive call using the remaining portion of the string (s-s1s−s1), if the remaining portion is a substring which can lead to the formation of a valid sentence as per the dictionary. Otherwise, empty list is returned.

Java

public class Solution {
    public List<String> wordBreak(String s, Set<String> wordDict) {
        return word_Break(s, wordDict, 0);
    }
    public List<String> word_Break(String s, Set<String> wordDict, int start) {
        LinkedList<String> res = new LinkedList<>();
        if (start == s.length()) {
            res.add("");
        }
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordDict.contains(s.substring(start, end))) {
                List<String> list = word_Break(s, wordDict, end);
                for (String l : list) {
                    res.add(s.substring(start, end) + (l.equals("") ? "" : " ") + l);
                }
            }
        }
        return res;
    }
}
Complexity Analysis

Time complexity : O(n^n)O(n
​n
​​ ). Consider the worst case where s=``aaaaaaa"s=‘‘aaaaaaa" and every prefix of ss is present in the dictionary of words, then the recursion tree can grow up to n^nn
​n
​​ .

Space complexity : O(n^3)O(n
​3
​​ ). In worst case the depth of recursion tree can go up to nn and nodes can contain nn strings each of length nn.

Approach #2 Recursion with memoization [Accepted]

Algorithm

In the previous approach we can see that many subproblems were redundant, i.e we were calling the recursive function multiple times for the same substring appearing through multiple paths. To avoid this we can use memorization method, where we are making use of a hashmap to store the results in the form of a key:valuekey:value pair. In this hashmap, the keykey used is the starting index of the string currently considered and the valuevalue contains all the sentences which can be formed using the substring from this starting index onwards. Thus, if we encounter the same starting index from different function calls, we can return the result directly from the hashmap rather than going for redundant function calls.

With memorization many redundant subproblems are avoided and recursion tree is pruned and thus it reduces the time complexity by a large factor.

Java

public class Solution {

    public List<String> wordBreak(String s, Set<String> wordDict) {
        return word_Break(s, wordDict, 0);
    }
    HashMap<Integer, List<String>> map = new HashMap<>();

    public List<String> word_Break(String s, Set<String> wordDict, int start) {
        if (map.containsKey(start)) {
            return map.get(start);
        }
        LinkedList<String> res = new LinkedList<>();
        if (start == s.length()) {
            res.add("");
        }
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordDict.contains(s.substring(start, end))) {
                List<String> list = word_Break(s, wordDict, end);
                for (String l : list) {
                    res.add(s.substring(start, end) + (l.equals("") ? "" : " ") + l);
                }
            }
        }
        map.put(start, res);
        return res;
    }
}
Complexity Analysis

Time complexity : O(n^3)O(n
​3
​​ ). Size of recursion tree can go up to n^2n
​2
​​ . The creation of list takes nn time.

Space complexity : O(n^3)O(n
​3
​​ ).The depth of the recursion tree can go up to nn and each activation record can contains a string list of size nn.

Approach #3 Using Dynamic Programming [Time Limit Exceeded]:

Algorithm

The intuition behind this approach is that the given problem (ss) can be divided into subproblems s1s1 and s2s2. If these subproblems individually satisfy the required conditions, the complete problem, ss also satisfies the same. e.g. "catsanddog" can be split into two substrings "catsand", "dog". The subproblem "catsand" can be further divided into "cats","and", which individually are a part of the dictionary making "catsand" satisfy the condition. Going further backwards, "catsand", "dog" also satisfy the required criteria individually leading to the complete string "catsanddog" also to satisfy the criteria.

Now, well move onto the process of \text{dp}dp array formation. We make use of \text{dp}dp array (in the form of a linked list) of size n+1n+1, where nn is the length of the given string. \text{dp}[k]dp[k] is used to store every possible sentence having all valid dictionary words using the substring s[0:k-1]s[0:k−1]. We also use two index pointers ii and jj, where ii refers to the length of the substring (s's
​′
​​ ) considered currently starting from the beginning, and jj refers to the index partitioning the current substring (s's
​′
​​ ) into smaller substrings s'(0,j)s
​′
​​ (0,j) and s'(j+1,i)s
​′
​​ (j+1,i). To fill in the \text{dp}dp array, we initialize the element \text{dp}[0]dp[0] as an empty string, since no sentence can be formed using a word of size 0. We consider substrings of all possible lengths starting from the beginning by making use of index ii. For every such substring, we partition the string into two further substrings s1's1
​′
​​  and s2's2
​′
​​  in all possible ways using the index jj ( Note that the ii now refers to the ending index of s2's2
​′
​​ ). Now, to fill in the entry \text{dp}[i]dp[i], we check if the \text{dp}[j]dp[j] contains a non-empty string i.e. if some valid sentence can be formed using s1's1
​′
​​ . If so, we further check if s2's2
​′
​​  is present in the dictionary. If both the conditions are satified, we append the substring s2's2
​′
​​  to every possible sentence that can be formed up to the index jj (which is already stored in d[j]d[j]). These newly formed sentences are stored in \text{dp}[i]dp[i]. Finally the element \text{dp}[n]dp[n] (nn refers to the length of the given string ss) gives all possible valid sentences using the complete string ss.

Java

public class Solution {
    public List<String> wordBreak(String s, Set<String> wordDict) {
        LinkedList<String>[] dp = new LinkedList[s.length() + 1];
        LinkedList<String> initial = new LinkedList<>();
        initial.add("");
        dp[0] = initial;
        for (int i = 1; i <= s.length(); i++) {
            LinkedList<String> list = new LinkedList<>();
            for (int j = 0; j < i; j++) {
                if (dp[j].size() > 0 && wordDict.contains(s.substring(j, i))) {
                    for (String l : dp[j]) {
                        list.add(l + (l.equals("") ? "" : " ") + s.substring(j, i));
                    }
                }
            }
            dp[i] = list;
        }
        return dp[s.length()];
    }
}
Complexity Analysis

Time complexity : O(n^3)O(n
​3
​​ ). Two loops are required to fill \text{dp}dp array and one loop for appending a list .

Space complexity : O(n^3)O(n
​3
​​ ). Length of \text{dp}dp array is nn and each value of \text{dp}dp array contains a list of string i.e. n^2n
​2
​​  space.
