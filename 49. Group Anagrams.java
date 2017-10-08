public class Solution {
	public List<List<String>> groupAnagrams(String[] strs) {
		Map<String, List> hm = new HashMap<>();
		for(int i = 0; i < strs.length; i++){
			String temp = strs[i];
			char[] chartemp = temp.toCharArray();
			Arrays.sort(chartemp);
			String sorted = new String(chartemp);  //java sort 一个string的方法
			if(hm.containsKey(sorted)){
				List<String> v = hm.get(sorted);
				v.add(temp);
				hm.put(sorted, v);  //不用这么麻烦，get到是一个reference，直接add
			}
			else{
				List<String> v = new ArrayList<>();
				v.add(temp);
				hm.put(sorted, v);
			}
		}
		List<List<String>> list = new ArrayList<>();
		for(String key : hm.keySet()){
			list.add((List)hm.get(key));
		}
		return list;
	}
}



/*Summary
Approach #1: Categorize by Sorted String [Accepted]

Intuition

Two strings are anagrams if and only if their sorted strings are equal.

Algorithm

Maintain a map ans : {String -> List} where each key \text{K}K is a sorted string, and each value is the list of strings from the initial input that when sorted, are equal to \text{K}K.

In Java, we will store the key as a string, eg. code. In Python, we will store the key as a hashable tuple, eg. ('c', 'o', 'd', 'e').

Anagrams

Java

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) return new ArrayList();
        Map<String, List> ans = new HashMap<String, List>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String key = String.valueOf(ca);
            if (!ans.containsKey(key)) ans.put(key, new ArrayList());
            ans.get(key).add(s);
        }
        return new ArrayList(ans.values());
    }
}
Python

class Solution(object):
    def groupAnagrams(self, strs):
        ans = collections.defaultdict(list)
        for s in strs:
            ans[tuple(sorted(s))].append(s)
        return ans.values()
Complexity Analysis

Time Complexity: O(NK \log (K) )O(NKlog(K)), where NN is the length of strs, and KK is the maximum length of a string in strs. The outer loop has complexity O(N)O(N) as we iterate through each string. Then, we sort each string in O(K \log K)O(KlogK) time.

Space Complexity: O(N*K)O(N∗K), the total information content stored in ans.

Approach #2: Categorize by Count [Accepted]

Intuition

Two strings are anagrams if and only if their character counts (respective number of occurrences of each character) are the same.

Algorithm

We can transform each string \text{s}s into a character count, \text{count}count, consisting of 26 non-negative integers representing the number of \text{a}a's, \text{b}b's, \text{c}c's, etc. We use these counts as the basis for our hash map.

In Java, the hashable representation of our count will be a string delimited with '#' characters. For example, abbccc will be #1#2#3#0#0#0...#0 where there are 26 entries total. In python, the representation will be a tuple of the counts. For example, abbccc will be (1, 2, 3, 0, 0, ..., 0), where again there are 26 entries total.

Anagrams

Java

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) return new ArrayList();
        Map<String, List> ans = new HashMap<String, List>();
        int[] count = new int[26];
        for (String s : strs) {
            Arrays.fill(count, 0);
            for (char c : s.toCharArray()) count[c - 'a']++;

            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < 26; i++) {
                sb.append('#');
                sb.append(count[i]);
            }
            String key = sb.toString();
            if (!ans.containsKey(key)) ans.put(key, new ArrayList());
            ans.get(key).add(s);
        }
        return new ArrayList(ans.values());
    }
}
Python

class Solution:
    def groupAnagrams(strs):
        ans = collections.defaultdict(list)
        for s in strs:
            count = [0] * 26
            for c in s:
                count[ord(c) - ord('a')] += 1
            ans[tuple(count)].append(s)
        return ans.values()
Complexity Analysis

Time Complexity: O(N * K)O(N∗K), where NN is the length of strs, and KK is the maximum length of a string in strs. Counting each string is linear in the size of the string, and we count every string.

Space Complexity: O(N*K)O(N∗K), the total information content stored in ans.
*/





/*More concise
public class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String keyStr = String.valueOf(ca);
            if (!map.containsKey(keyStr)) map.put(keyStr, new ArrayList<String>());
            map.get(keyStr).add(s);
        }
        return new ArrayList<List<String>>(map.values());
    }
}
*/

/*Using primes
private static final int[] PRIMES = new int[]{2, 3, 5, 7, 11 ,13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 107};

public List<String> anagrams(String[] strs) {
    List<String> list = new LinkedList<>();
    Map<Integer, List<String>> mapString = new HashMap<>();
    int result = -1;
    for (int i = 0; i < strs.length; i++){
        int mapping = 1;
        for (int j = 0, max = strs[i].length(); j < max; j++) {
            mapping *= PRIMES[strs[i].charAt(j) - 'a'];
        }
        List<String> strings = mapString.get(mapping);
        if (strings == null) {
            strings = new LinkedList<>();
            mapString.put(mapping, strings);
        }
        strings.add(strs[i]);
    }
    for (List<String> mapList : mapString.values()){
        if (mapList.size() > 1)
            list.addAll(mapList);
    }
    return list;
}
*/
