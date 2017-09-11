class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {

        char[] char_s = s.toCharArray();

        int start = 0;
        int end = 0;
        int result = 0;

        Map<Character,Integer> hm = new HashMap<>();

        //需要给start和end明确的语义

        while(end < s.length()){
            while(hm.size() <= 2 && end < s.length()){
                int temp = hm.containsKey(char_s[end])?hm.get(char_s[end]):0;
                temp++;
                hm.put(char_s[end],temp);
                if(hm.size() <= 2) result = Math.max(result, end-start+1);
                end++;
            }

            while(hm.size() > 2 && start < s.length()){
                if(hm.get(char_s[start]) == 1){
                    hm.remove(char_s[start]);
                }
                else{
                    hm.put(char_s[start],hm.get(char_s[start])-1);
                }
                start++;
            }
        }

        return result;
    }
}



/*Sliding Window algorithm template to solve all the Leetcode substring search problem
https://discuss.leetcode.com/topic/71662/sliding-window-algorithm-template-to-solve-all-the-leetcode-substring-search-problem/4
Among all leetcode questions, I find that there are at least 5 substring search problem which could be solved by the sliding window algorithm.
so I sum up the algorithm template here. wish it will help you!

the template:
public class Solution {
    public List<Integer> slidingWindowTemplateByHarryChaoyangHe(String s, String t) {
        //init a collection or int value to save the result according the question.
        List<Integer> result = new LinkedList<>();
        if(t.length()> s.length()) return result;

        //create a hashmap to save the Characters of the target substring.
        //(K, V) = (Character, Frequence of the Characters)
        Map<Character, Integer> map = new HashMap<>();
        for(char c : t.toCharArray()){
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        //maintain a counter to check whether match the target string.
        int counter = map.size();//must be the map size, NOT the string size because the char may be duplicate.

        //Two Pointers: begin - left pointer of the window; end - right pointer of the window
        int begin = 0, end = 0;

        //the length of the substring which match the target string.
        int len = Integer.MAX_VALUE;

        //loop at the begining of the source string
        while(end < s.length()){

            char c = s.charAt(end);//get a character

            if( map.containsKey(c) ){
                map.put(c, map.get(c)-1);// plus or minus one
                if(map.get(c) == 0) counter--;//modify the counter according the requirement(different condition).
            }
            end++;

            //increase begin pointer to make it invalid/valid again
            while(counter == 0){ //counter condition. different question may have different condition

                char tempc = s.charAt(begin);//***be careful here: choose the char at begin pointer, NOT the end pointer
                if(map.containsKey(tempc)){
                    map.put(tempc, map.get(tempc) + 1);//plus or minus one
                    if(map.get(tempc) > 0) counter++;//modify the counter according the requirement(different condition).
                }

                // save / update(min/max) the result if find a target
                // result collections or result int value

                begin++;
            }
        }
        return result;
    }
}
Firstly, here is my sliding solution this question. I will sum up the template below this code.
2) the similar questions are:

https://leetcode.com/problems/minimum-window-substring/
https://leetcode.com/problems/longest-substring-without-repeating-characters/
https://leetcode.com/problems/substring-with-concatenation-of-all-words/
https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
https://leetcode.com/problems/find-all-anagrams-in-a-string/

3) I will give my solution for these questions use the above template one by one

Minimum-window-substring
https://leetcode.com/problems/minimum-window-substring/

public class Solution {
    public String minWindow(String s, String t) {
        if(t.length()> s.length()) return "";
        Map<Character, Integer> map = new HashMap<>();
        for(char c : t.toCharArray()){
            map.put(c, map.getOrDefault(c,0) + 1);
        }
        int counter = map.size();

        int begin = 0, end = 0;
        int head = 0;
        int len = Integer.MAX_VALUE;

        while(end < s.length()){
            char c = s.charAt(end);
            if( map.containsKey(c) ){
                map.put(c, map.get(c)-1);
                if(map.get(c) == 0) counter--;
            }
            end++;

            while(counter == 0){
                char tempc = s.charAt(begin);
                if(map.containsKey(tempc)){
                    map.put(tempc, map.get(tempc) + 1);
                    if(map.get(tempc) > 0){
                        counter++;
                    }
                }
                if(end-begin < len){
                    len = end - begin;
                    head = begin;
                }
                begin++;
            }

        }
        if(len == Integer.MAX_VALUE) return "";
        return s.substring(head, head+len);
    }
}
you may find that I only change a little code above to solve the question "Find All Anagrams in a String":
change

                if(end-begin < len){
                    len = end - begin;
                    head = begin;
                }
to

                if(end-begin == t.length()){
                    result.add(begin);
                }
longest substring without repeating characters
https://leetcode.com/problems/longest-substring-without-repeating-characters/

public class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int begin = 0, end = 0, counter = 0, d = 0;

        while (end < s.length()) {
            // > 0 means repeating character
            //if(map[s.charAt(end++)]-- > 0) counter++;
            char c = s.charAt(end);
            map.put(c, map.getOrDefault(c, 0) + 1);
            if(map.get(c) > 1) counter++;
            end++;

            while (counter > 0) {
                //if (map[s.charAt(begin++)]-- > 1) counter--;
                char charTemp = s.charAt(begin);
                if (map.get(charTemp) > 1) counter--;
                map.put(charTemp, map.get(charTemp)-1);
                begin++;
            }
            d = Math.max(d, end - begin);
        }
        return d;
    }
}
Longest Substring with At Most Two Distinct Characters
https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/

public class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        Map<Character,Integer> map = new HashMap<>();
        int start = 0, end = 0, counter = 0, len = 0;
        while(end < s.length()){
            char c = s.charAt(end);
            map.put(c, map.getOrDefault(c, 0) + 1);
            if(map.get(c) == 1) counter++;//new char
            end++;
            while(counter > 2){
                char cTemp = s.charAt(start);
                map.put(cTemp, map.get(cTemp) - 1);
                if(map.get(cTemp) == 0){
                    counter--;
                }
                start++;
            }
            len = Math.max(len, end-start);
        }
        return len;
    }
}
Substring with Concatenation of All Words
https://leetcode.com/problems/substring-with-concatenation-of-all-words/

public class Solution {
    public List<Integer> findSubstring(String S, String[] L) {
        List<Integer> res = new LinkedList<>();
        if (L.length == 0 || S.length() < L.length * L[0].length())   return res;
        int N = S.length();
        int M = L.length; // *** length
        int wl = L[0].length();
        Map<String, Integer> map = new HashMap<>(), curMap = new HashMap<>();
        for (String s : L) {
            if (map.containsKey(s))   map.put(s, map.get(s) + 1);
            else                      map.put(s, 1);
        }
        String str = null, tmp = null;
        for (int i = 0; i < wl; i++) {
            int count = 0;  // remark: reset count
            int start = i;
            for (int r = i; r + wl <= N; r += wl) {
                str = S.substring(r, r + wl);
                if (map.containsKey(str)) {
                    if (curMap.containsKey(str))   curMap.put(str, curMap.get(str) + 1);
                    else                           curMap.put(str, 1);

                    if (curMap.get(str) <= map.get(str))    count++;
                    while (curMap.get(str) > map.get(str)) {
                        tmp = S.substring(start, start + wl);
                        curMap.put(tmp, curMap.get(tmp) - 1);
                        start += wl;

                        //the same as https://leetcode.com/problems/longest-substring-without-repeating-characters/
                        if (curMap.get(tmp) < map.get(tmp)) count--;

                    }
                    if (count == M) {
                        res.add(start);
                        tmp = S.substring(start, start + wl);
                        curMap.put(tmp, curMap.get(tmp) - 1);
                        start += wl;
                        count--;
                    }
                }else {
                    curMap.clear();
                    count = 0;
                    start = r + wl;//not contain, so move the start
                }
            }
            curMap.clear();
        }
        return res;
    }
}
Find All Anagrams in a String
https://leetcode.com/problems/find-all-anagrams-in-a-string/

public class Solution {
    public List<Integer> findAnagrams(String s, String t) {
        List<Integer> result = new LinkedList<>();
        if(t.length()> s.length()) return result;
        Map<Character, Integer> map = new HashMap<>();
        for(char c : t.toCharArray()){
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int counter = map.size();

        int begin = 0, end = 0;
        int head = 0;
        int len = Integer.MAX_VALUE;


        while(end < s.length()){
            char c = s.charAt(end);
            if( map.containsKey(c) ){
                map.put(c, map.get(c)-1);
                if(map.get(c) == 0) counter--;
            }
            end++;

            while(counter == 0){
                char tempc = s.charAt(begin);
                if(map.containsKey(tempc)){
                    map.put(tempc, map.get(tempc) + 1);
                    if(map.get(tempc) > 0){
                        counter++;
                    }
                }
                if(end-begin == t.length()){
                    result.add(begin);
                }
                begin++;
            }

        }
        return result;
    }
}
*/



/*More concise but O(kn)
value in the hashmap. This way, whenever the size of the hashmap exceeds 2, we can traverse through the map to find the character with the left most index, and remove 1 character from our map. Since the range of characters is constrained, we should be able to find the left most index in constant time.

public class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if(s.length() < 1) return 0;
        HashMap<Character,Integer> index = new HashMap<Character,Integer>();
        int lo = 0;
        int hi = 0;
        int maxLength = 0;
        while(hi < s.length()) {
            if(index.size() <= 2) {
                char c = s.charAt(hi);
                index.put(c, hi);
                hi++;
            }
            if(index.size() > 2) {
                int leftMost = s.length();
                for(int i : index.values()) {
                    leftMost = Math.min(leftMost,i);
                }
                char c = s.charAt(leftMost);
                index.remove(c);
                lo = leftMost+1;
            }
            maxLength = Math.max(maxLength, hi-lo);
        }
        return maxLength;
    }
}
*/



/*Using two pointers
int lengthOfLongestSubstringTwoDistinct(string s) {
    int i = 0, j = -1;
    int maxLen = 0;
    for (int k = 1; k < s.size(); k++) {
        if (s[k] == s[k-1]) continue;
        if (j > -1 && s[k] != s[j]) {
            maxLen = max(maxLen, k - i);
            i = j + 1;
        }
        j = k - 1;
    }
    return maxLen > (s.size() - i) ? maxLen : s.size() - i;
}
smart idea.I think it uses i and j to track the last indices of two characters. i is tracking the first character, j is tracking the second character. But I still like the hashmap solution because we can easily extend that solution to K distinct characters.
*/




/*More OO designed
Basic idea:

Maintain a HashMap with counter of each entry (CountMap) to keep the count of each distinct characters in the current substring (from star to i).

Scan the string sequentially, add each character into the CountMap, if the count map has less than 2 entries (i.e. distinct characters), keep growing the substring; else, remove entries from the substring (from the star position) till the CountMap contains less than 2 entries so that a new entry can be added.

Note: this solution is also good for the general problem of find longest substring with at most K distinct characters.
Complexity: O(N) time -- scan string twice (star++, i++), O(K) space where K is the number distinct characters maintained in the CounterMap.

public class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        int rst = 0;
        CountMap cM = new CountMap();
        int star = 0;

        for (int i=0; i<s.length(); i++){
            char x = s.charAt(i);
            if(cM.containsKey(x)){
                cM.put(x);
            }else{
                while(cM.size()==2){
                    cM.remove(s.charAt(star));
                    star++;
                }
                cM.put(x);
            }
            rst = Math.max(rst, i-star+1);
        }
        return rst;
    }

    class CountMap {
        Map<Character, Integer> m = new HashMap<Character, Integer>();

        public boolean containsKey(char x){
            return m.containsKey(x);
        }

        public int size(){
            return m.size();
        }

        public void put(char x){
            if (!m.containsKey(x)){
                m.put(x, 1);
            }else{
                m.put(x, m.get(x)+1);
            }
        }

        public void remove(char x){
            if (!m.containsKey(x)){
                return;
            }else if (m.get(x)==1){
                m.remove(x);
            }else{
                m.put(x, m.get(x)-1);
            }
        }
    }
}
*/




