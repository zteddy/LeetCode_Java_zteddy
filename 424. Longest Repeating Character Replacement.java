class Solution {
    public int characterReplacement(String s, int k) {

        char[] char_s = s.toCharArray();

        int start = 0, end = 0, maxLen = 0, diff = 0, dominantCount = 0;
        // char dominantChar = '';

        Map<Character,Integer> hm = new HashMap<>();
        Map<Integer,HashSet<Character>> dominant = new HashMap<>();

        while(end < s.length()){
            if(diff <= k){
                char now = char_s[end];

                int temp = hm.containsKey(now)?hm.get(now):0;
                hm.put(now,temp+1);

                if(dominant.get(temp) != null){
                    dominant.get(temp).remove(now);
                }
                if(dominant.get(temp+1) == null){
                    HashSet<Character> ts = new HashSet<>();
                    ts.add(now);
                    dominant.put(temp+1,ts);
                }
                else{
                    dominant.get(temp+1).add(now);
                }

                if(hm.get(now) >  dominantCount){
                    dominantCount = hm.get(now);
                }
                diff = end-start+1 - dominantCount;
                if(diff <= k)
                    maxLen = Math.max(end-start+1,maxLen);
                end++;
            }
            else{
                char now = char_s[start];
                int temp = hm.get(now);
                hm.put(now,temp-1);
                dominant.get(temp).remove(now);
                if(dominant.get(temp-1) == null){
                    HashSet<Character> ts = new HashSet<>();
                    ts.add(now);
                }
                else{
                    dominant.get(temp-1).add(now);
                }
                if(temp == dominantCount){
                    if(dominant.get(dominantCount).size() == 0)
                        dominantCount--;
                }
                start++;
                diff = end-start - dominantCount;

            }
        }
        return maxLen;
    }
}



//对start和end的语义要求还是很高，我还是没有定义清楚
//写得还不够简洁



/*More concise 并不需要记录实时的dominantCount，因为我们能用它维持窗口大小只扩大不缩小
   public int characterReplacement(String s, int k) {
        int len = s.length();
        int[] count = new int[26];
        int start = 0, maxCount = 0, maxLength = 0;
        for (int end = 0; end < len; end++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(end) - 'A']);
            while (end - start + 1 - maxCount > k) {
                count[s.charAt(start) - 'A']--;
                start++;
            }
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }
There's no edge case for this question. The initial step is to extend the window to its limit, that is, the longest we can get to with maximum number of modifications. Until then the variable start will remain at 0.

Then as end increase, the whole substring from 0 to end will violate the rule, so we need to update start accordingly (slide the window). We move start to the right until the whole string satisfy the constraint again. Then each time we reach such situation, we update our max length.

This solution is great, best so far. However, it requires a bit more explanation.

Since we are only interested in the longest valid substring, our sliding windows need not shrink, even if a window may cover an invalid substring. We either grow the window by appending one char on the right, or shift the whole window to the right by one. And we only grow the window when the count of the new char exceeds the historical max count (from a previous window that covers a valid substring).

That is, we do not need the accurate max count of the current window; we only care if the max count exceeds the historical max count; and that can only happen because of the new char.

Here's my implementation that's a bit shorter

public int characterReplacement(String s, int k)
{
    int[] count = new int[128];
    int max=0;
    int start=0;
    for(int end=0; end<s.length(); end++)
    {
        max = Math.max(max, ++count[s.charAt(end)]);
        if(max+k<=end-start)
            count[s.charAt(start++)]--;
    }
    return s.length()-start;
}

@tsuvmxwu Allow me to expand on your explanation:

Basics:

We use a window.
The window starts at the left side of the string and the length of the window is initialized to zero.
The window only stays the same or grows in length as the algorithm proceeds.
The algorithm grows the window to the maximum valid length according to the rules of the game.
The algorithm returns the length of the window upon completion.
Setup:

The length of the window is defined by the following equation: end - start + 1.
The values of both start and end are subject to change during the execution of the algorithm.
The value of end starts at 0 and gets incremented by 1 with each execution of the loop.
But unless a certain condition is met, the value of start will also gets incremented with each execution of the loop, keeping the length of the window unchanged.
That condition is met when the number of the most commonly occuring character in the window + k is at least as large as the length of the window (the value of k determines how many of the less commonly occurring characters there can be). This condition would be required to create a string containing all the same characters from the characters contained within the window.
Execution:

Right in the beginning, the length of the window is going to be able to grow to at least the value of k.
After that initial growth, the algorithm becomes a search to find the window that contains the greatest number of reoccurring characters.
Whenever including the character at end results in an increase in the greatest number of reoccurring characters ever encountered in a window tested so far, the window grows by one (by not incrementing start).
When determining whether or not including another character at the end of the window results in the increase described above, only the occurrence of the newly included character in the window and the running all-time max need to be taken into account (after all, only the frequency of the newly included character is increasing).
Even if/when the value of start is incremented (i.e. the left side of the window is moved to the right), the all-time max doesn't need to be reset to reflect what's currently in the window because 1) at this point in the algorithm, the all-time maximum number of reoccurring characters in a window is what we're using to determine the all-time longest window; and 2) we only care about positive developments in our search (i.e. we find a window that contains an even greater number of reoccurring characters than any other window we have tested so far and therefore is longer than any window we have tested so far). The algorithm becomes a search for the max and we only need to set the max when we have a new max.
*/




/*Bad example
The problem says that we can make at most k changes to the string (any character can be replaced with any other character). So, let's say there were no constraints like the k. Given a string convert it to a string with all same characters with minimal changes. The answer to this is

length of the entire string - number of times of the maximum occurring character in the string
Given this, we can apply the at most k changes constraint and maintain a sliding window such that

(length of substring - number of times of the maximum occurring character in the substring) <= k
class Solution {
public:
    int characterReplacement(string s, int k) {
        vector<int> counts(26, 0);
        int start = 0;
        int maxCharCount = 0;
        int n = s.length();
        int result = 0;
        for(int end = 0; end < n; end++){
            counts[s[end]-'A']++;
            if(maxCharCount < counts[s[end]-'A']){
                maxCharCount = counts[s[end]-'A'];
            }
            while(end-start-maxCharCount+1 > k){
                counts[s[start]-'A']--;
                start++;
                for(int i = 0; i < 26; i++){
                    if(maxCharCount < counts[i]){
                        maxCharCount = counts[i];
                    }
                }
            }
            result = max(result, end-start+1);
        }
        return result;
    }
};


Nice solution! But a little advice, this step:

for(int i = 0; i < 26; i++){
    if(maxCharCount < counts[i]){
        maxCharCount = counts[i];
    }
}
is not necessary, because when the sliding window shrinks, the counts array won't get larger. So basically maxCharCount never be updated in this loop. Correct me if I'm wrong :)

G googleguang   @vesion
Reputation:  17
@vesion Yes, I know what you mean. The for loop didn't update the maxCharCount since it can't be smaller than any element in the counts. We should set maxCharCount as 0 before the for loop.

However, the original code works. Even more, we can change the while loop as a if clause and delete the while loop. Change it to

    int characterReplacement(string s, int k) {
        vector<int> count(26, 0);
        int max_len = 0, max_occur = 0, start = 0;
        for (int end = 0; end < s.size(); end++) {
            char ch = s[end] - 'A';
            count[ch]++;
            max_occur = max(max_occur, count[ch]);
            if (end - start - max_occur + 1 > k) {
                count[s[start] - 'A']--;
                start++;
            }
            max_len = end - start + 1;
        }
        return max_len;
    }
Because when we get a result, we don't need to calculate a new result worse than the first one. So the max_len will increment only.
*/

