class Solution {
    public boolean isSubsequence(String s, String t) {
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();

        int j = 0;
        for(int i = 0; i < sArr.length; i++){
            while(j < tArr.length && sArr[i] != tArr[j]) j++;
            if(j >= tArr.length) return false;
            j++;
        }

        return true;
    }
}


/*More concise
public class Solution {
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) return true;
        int indexS = 0, indexT = 0;
        while (indexT < t.length()) {
            if (t.charAt(indexT) == s.charAt(indexS)) {
                indexS++;
                if (indexS == s.length()) return true;
            }
            indexT++;
        }
        return false;
    }
}
*/



/*Using Binary Search
Re: Java binary search using TreeSet got TLE

I think the Map and TreeSet could be simplified by Array and binarySearch. Since we scan T from beginning to the end (index itself is in increasing order), List will be sufficient. Then we can use binarySearch to replace with TreeSet ability which is a little overkill for this problem. Here is my solution.

// Follow-up: O(N) time for pre-processing, O(Mlog?) for each S.
// Eg-1. s="abc", t="bahbgdca"
// idx=[a={1,7}, b={0,3}, c={6}]
//  i=0 ('a'): prev=1
//  i=1 ('b'): prev=3
//  i=2 ('c'): prev=6 (return true)
// Eg-2. s="abc", t="bahgdcb"
// idx=[a={1}, b={0,6}, c={5}]
//  i=0 ('a'): prev=1
//  i=1 ('b'): prev=6
//  i=2 ('c'): prev=? (return false)
public boolean isSubsequence(String s, String t) {
    List<Integer>[] idx = new List[256]; // Just for clarity
    for (int i = 0; i < t.length(); i++) {
        if (idx[t.charAt(i)] == null)
            idx[t.charAt(i)] = new ArrayList<>();
        idx[t.charAt(i)].add(i);
    }

    int prev = 0;
    for (int i = 0; i < s.length(); i++) {
        if (idx[s.charAt(i)] == null) return false; // Note: char of S does NOT exist in T causing NPE
        int j = Collections.binarySearch(idx[s.charAt(i)], prev);
        if (j < 0) j = -j - 1;
        if (j == idx[s.charAt(i)].size()) return false;
        prev = idx[s.charAt(i)].get(j) + 1;
    }
    return true;
}

@cdai Here is my idea for the follow up, based upon your solution : )

Binary search:

record the indexes for each character in t, if s[i] matches t[j], then s[i+1] should match a character in t with index bigger than j. This can be reduced to find the first element larger than a value in an sorted array (find upper bound), which can be achieved using binary search.
Trie:

For example, if s1 has been matched, s1[last char] matches t[j]. Now, s2 comes, if s1 is a prefix of s2, i.e., s1 == s2.substr[0, i-1], we can start match s2 from s2[i], right?
So, the idea is to create a Trie for all string that have been matched so far. At a node, we record the position in t which matches this char represented by the node. Now, for an incoming string s, we first search the longest prefix in the Trie, find the matching position of the last prefix-char in t, say j. Then, we can start matching the first non-prefix-char of s from j+1.
Now, if we have done the preprocessing as stated in the binary search approach, we can be even faster.
*/



/*Using Hash and BinarySearch

// Follow-up
// If we check each sk in this way, then it would be O(kn) time where k is the number of s and t is the length of t.
// This is inefficient.
// Since there is a lot of s, it would be reasonable to preprocess t to generate something that is easy to search for if a character of s is in t.
// Sounds like a HashMap, which is super suitable for search for existing stuff.

public boolean isSubsequence(String s, String t) {
    if (s == null || t == null) return false;

    Map<Character, List<Integer>> map = new HashMap<>(); //<character, index>

    //preprocess t
    for (int i = 0; i < t.length(); i++) {
        char curr = t.charAt(i);
        if (!map.containsKey(curr)) {
            map.put(curr, new ArrayList<Integer>());
        }
        map.get(curr).add(i);
    }

    int prev = -1;  //index of previous character
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);

        if (map.get(c) == null)  {
            return false;
        } else {
            List<Integer> list = map.get(c);
            prev = binarySearch(prev, list, 0, list.size() - 1);
            if (prev == -1) {
                return false;
            }
            prev++;
        }
    }

    return true;
}

private int binarySearch(int index, List<Integer> list, int start, int end) {
    while (start <= end) {
        int mid = start + (end - start) / 2;
        if (list.get(mid) < index) {
            start = mid + 1;
        } else {
            end = mid - 1;
        }
    }

    return start == list.size() ? -1 : list.get(start);
}
*/




/*Using indexOf
Actually another way of 2 pointers, guess indexOf() performs better.

Tested with other 2-pointers methods in discussion, both took 30ms+.

The one below only takes 2ms.

public class Solution
{
    public boolean isSubsequence(String s, String t)
    {
        if(t.length() < s.length()) return false;
        int prev = 0;
        for(int i = 0; i < s.length();i++)
        {
            char tempChar = s.charAt(i);
            prev = t.indexOf(tempChar,prev);
            if(prev == -1) return false;
            prev++;
        }

        return true;
    }
}
Thanks to @Ankai.Liang who looked into both functions and provided us the answer.

In case you guys do not notice, I post Liang Ankai's answer.

@Ankai.Liang said

Hi, good solution.
I checked the origin code of func "indexOf" and "charAt". These two solution both traversed the char of String one by one to search the first occurrence specific char.
The difference is that indexOf only call once function then traversed in "String.value[]" arr, but we used multiple calling function "charAt" to get the value in "String.value[]" arr.
The time expense of calling function made the difference.
*/





/*Using Hash and BinarySearch 2
public class Solution {
    //two pointer: only one input string
    public boolean isSubsequence(String s, String t) {
        if(s == null) return true;
        if(t == null) return false;

        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            j = t.indexOf(s.charAt(i), j);
            if (j < 0) return false;
            j++;
        }

        return true;
    }

    //Follow up: Binary search for multiple coming string
    public boolean isSubsequence(String s, String t) {
        //O(t.length())
        Map<Character, List<Integer>> myMap = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            if (!myMap.containsKey(t.charAt(i))) {
                myMap.put(t.charAt(i), new ArrayList<Integer>());
            }
            myMap.get(t.charAt(i)).add(i);
        }

        //search: O(s.length() * log(t.length()))
        int index = -1;
        for (int i = 0; i < s.length(); i++) {
            char ch= s.charAt(i);

            int nextIndex = getNextIndex(myMap.get(ch), index);
            if (nextIndex < 0) return false;
            index = nextIndex;
        }

        return true;
    }

    public int getNextIndex(List<Integer> list, int index) {
        if (list == null) return -1;
        int left = 0, right = list.size() - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) <= index) left = mid + 1;
            else right = mid;
        }

        return list.get(left) > index ? list.get(left) : -1;

    }
}
*/
