class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        char[] char_s = s.toCharArray();

        int start = 0;
        int end = 0;
        int result = 0;

        Map<Character,Integer> hm = new HashMap<>();


        while(end < s.length()){
            while(hm.size() <= k && end < s.length()){
                int temp = hm.containsKey(char_s[end])?hm.get(char_s[end]):0;
                temp++;
                hm.put(char_s[end],temp);
                if(hm.size() <= k) result = Math.max(result, end-start+1);
                end++;
            }

            while(hm.size() > k && start < s.length()){
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






/*More concise
feel it is not a new question, just use num to track the number of distinct characters within the slide window

public class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int[] count = new int[256];
        int num = 0, i = 0, res = 0;
        for (int j = 0; j < s.length(); j++) {
            if (count[s.charAt(j)]++ == 0) num++;
            if (num > k) {
                while (--count[s.charAt(i++)] > 0);
                num--;
            }
            res = Math.max(res, j - i + 1);
        }
        return res;
    }
}
*/



/*More concise2
This problem can be solved using two pointers. The important part is while (map.size() > k), we move left pointer to make sure the map size is less or equal to k. This can be easily extended to any number of unique characters.

public int lengthOfLongestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> map = new HashMap<>();
    int left = 0;
    int best = 0;
    for(int i = 0; i < s.length(); i++) {
        // character at the right pointer
        char c = s.charAt(i);
        map.put(c, map.getOrDefault(c, 0) + 1);
        // make sure map size is valid, no need to check left pointer less than s.length()
        while (map.size() > k) {
            char leftChar = s.charAt(left);
            map.put(leftChar, map.get(leftChar) - 1);
            if (map.get(leftChar) == 0) {
                map.remove(leftChar);
            }
            left++;
        }
        best = Math.max(best, i - left + 1);
    }
    return best;
}
*/




/*???
Solving the problem with O(n) time is not enough, some interviewer may require this solution as a followup. Instead of recording each char's count, we keep track of char's last occurrence. If you consider k as constant, it is also a O(n) algorithm.

inWindow keeps track of each char in window and its last occurrence position

lastOccurrence is used to find the char in window with left most last occurrence. A better idea is to use a PriorityQueue, as it takes O(1) to getMin, However Java's PQ does not support O(logn) update a internal node, it takes O(n). TreeMap takes O(logn) to do both getMin and update.
Every time when the window is full of k distinct chars, we lookup TreeMap to find the one with leftmost last occurrence and set left bound j to be 1 + first to exclude the char to allow new char coming into window.

public class Solution {
    public int lengthOfLongestSubstringKDistinct(String str, int k) {
        if (str == null || str.isEmpty() || k == 0) {
            return 0;
        }
        TreeMap<Integer, Character> lastOccurrence = new TreeMap<>();
        Map<Character, Integer> inWindow = new HashMap<>();
        int j = 0;
        int max = 1;
        for (int i = 0; i < str.length(); i++) {
            char in = str.charAt(i);
            while (inWindow.size() == k && !inWindow.containsKey(in)) {
                int first = lastOccurrence.firstKey();
                char out = lastOccurrence.get(first);
                inWindow.remove(out);
                lastOccurrence.remove(first);
                j = first + 1;
            }
            //update or add in's position in both maps
            if (inWindow.containsKey(in)) {
                lastOccurrence.remove(inWindow.get(in));
            }
            inWindow.put(in, i);
            lastOccurrence.put(i, in);
            max = Math.max(max, i - j + 1);
        }
        return max;
    }
}
*/

