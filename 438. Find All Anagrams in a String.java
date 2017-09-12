public class Solution {
    public List<Integer> findAnagrams(String s, String p) {
    	Map<Character, Integer> hm = new HashMap<>();
    	List<Integer> result = new LinkedList<>();
    	if(s.length() < p.length()) return result;
    	for(int i = 0; i < p.length(); i++){
    		if(hm.containsKey(p.charAt(i))){
    			hm.put(p.charAt(i), hm.get(p.charAt(i))+1);    //注意Integer不能有++
    		}
    		else{
    			hm.put(p.charAt(i), 1);
    		}
    	}
    	int count = 0;
    	for(int i = s.length()-1; i >= s.length()-p.length(); i--){
    		if(hm.containsKey(s.charAt(i))){
    			if(hm.get(s.charAt(i))==0) count-=1;
    			hm.put(s.charAt(i), hm.get(s.charAt(i))-1);  //注意Integer不能有--
    			if(hm.get(s.charAt(i))==0) count+=1;
    		}
    	}
    	if(count == hm.keySet().size()) result.add(s.length()-p.length());
    	for(int i = s.length()-p.length()-1; i >= 0; i--){
    		if(hm.containsKey(s.charAt(i))){
    			if(hm.get(s.charAt(i))==0) count-=1;
    			hm.put(s.charAt(i), hm.get(s.charAt(i))-1);
    			if(hm.get(s.charAt(i))==0) count+=1;
    		}
    		if(hm.containsKey(s.charAt(i+p.length()))){
    			if(hm.get(s.charAt(i+p.length()))==0) count-=1;
    			hm.put(s.charAt(i+p.length()), hm.get(s.charAt(i+p.length()))+1);
    			if(hm.get(s.charAt(i+p.length()))==0) count+=1;
    		}
    		if(count == hm.keySet().size()) result.add(i);
    	}
    	return result;
    }
}
//其实这已经是自己想出来的sliding window了，但是可以用数组的，更简洁


//尴尬好像重复做了一遍。。。
class Solution {
    public List<Integer> findAnagrams(String s, String p) {

        if(s.length() == 0 || p.length() == 0) return new ArrayList();

        int start = 0, end = 0;
        Map<Character,Integer> words = new HashMap<>();

        for(int i = 0; i < p.length(); i++){
            int temp = words.containsKey(p.charAt(i))?words.get(p.charAt(i)):0;
            words.put(p.charAt(i),temp+1);
        }

        Map<Character,Integer> anagrams = new HashMap<>();
        char[] char_s = s.toCharArray();
        List<Integer> result = new ArrayList<>();

        if(words.containsKey(char_s[0])) anagrams.put(char_s[0],1);

        while(end < s.length()-1){
            if(end-start+1 < p.length()){
                end++;
                if(words.containsKey(char_s[end])){
                    int temp = anagrams.containsKey(char_s[end])?anagrams.get(char_s[end]):0;
                    anagrams.put(char_s[end],temp+1);
                    if(words.equals(anagrams)) result.add(start);
                }
            }
            else{
                if(anagrams.containsKey(char_s[start])){
                    anagrams.put(char_s[start],anagrams.get(char_s[start])-1);
                    if(words.equals(anagrams)) result.add(start);
                }
                start++;
            }
        }

        return result;
    }
}

//好像效率的确没有用count高


/*Using count
Same idea from a fantastic sliding window template, please refer:
https://discuss.leetcode.com/topic/30941/here-is-a-10-line-template-that-can-solve-most-substring-problems

Time Complexity will be O(n) because the "start" and "end" points will only move from left to right once.

public List<Integer> findAnagrams(String s, String p) {
    List<Integer> list = new ArrayList<>();
    if (s == null || s.length() == 0 || p == null || p.length() == 0) return list;
    int[] hash = new int[256]; //character hash
    //record each character in p to hash
    for (char c : p.toCharArray()) {
        hash[c]++;
    }
    //two points, initialize count to p's length
    int left = 0, right = 0, count = p.length();
    while (right < s.length()) {
        //move right everytime, if the character exists in p's hash, decrease the count
        //current hash value >= 1 means the character is existing in p
        if (hash[s.charAt(right++)]-- >= 1) count--;

        //when the count is down to 0, means we found the right anagram
        //then add window's left to result list
        if (count == 0) list.add(left);

        //if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
        //++ to reset the hash because we kicked out the left
        //only increase the count if the character is in p
        //the count >= 0 indicate it was original in the hash, cuz it won't go below 0
        if (right - left == p.length() && hash[s.charAt(left++)]++ >= 0) count++;
    }
    return list;
}
*/





/*Template
In "438. Find All Anagrams in a String" problem, the counter means the number of keys contained in hash table for string p. Because we want to find all t's anagrams in s, we want to check all substring that matches t's anagrams.
How can we check it? We can use a end pointer to go through s to check if an element in s matches an element in t.

For example, if t = 'abb', s = 'abbabb'.
Here we can create a hash table for string t :
Key (Value) : a(1), b(2). [Here, counter =2]
When iterating end, when it makes counter == 0, it means : substring 'abb' of s is an anagram of p. (This is because, we only decrement counter when the value of end is 0.
Now, the hash table is : Key (Value) : a(0), b(0). [Here, counter =0, we increment end.]

Knowing this, we want to continue checking whether "bba"is an anagram of t. Here, we update the begin pointer to the second char of s. Before we do so, we have to check if there is some impact by pervious begin, which is the first char of s. Because we slid the window to s.substring(1,4) instead of s.substring(0,3), we want to get rid of the previous impact of first char of s.

We use map.get(tempc) > 0 to check whether or not s.charAt(begin) is matched with a key in hash table before . If it is matched before, we should increase counter because it would be unmatched when we only want to check s.substring(0,3).

Why use map.get(tempc) > 0 ? Because in the outer while loop, for each char that is matched, its value must be larger than 0. (Because the char have to be in hash table.) In the outer while loop, we decrement the its value by 1, in the inner while loop, we increment its value by it. So, after all, its value should be larger than 0.

Finally, we should check end - begin == t.length, because we want to make sure our matched substring have the same length with string t. The counter doesn't check the length for us, it only check whether all chars in string t can be found in s.substring.

It takes me a while to understand the solution.
I hope it helps : )

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




/*
public List<Integer> findAnagrams(String s, String p) {
    List<Integer> ans = new ArrayList<>();
    if (p.length() > s.length()){
        return ans;
    }
    int[] charCounts = new int[26];
    for (char c : p.toCharArray()){
        charCounts[toInt(c)]++;
    }

    // Note: in the next iteration of this solution we may be able to move this
    // into the while loop, but this does help my understanding of the solution
    int left = 0; int right = 0; int numDiff = p.length();
    for (right = 0; right < p.length(); right++){
        char c = s.charAt(right);
        if (charCounts[toInt(c)] > 0){
            numDiff--;
        }
        charCounts[toInt(c)]--;
    }
    if (numDiff == 0){
        ans.add(0);
    }

    // At this point what does the 'charCounts' represent?
    // positive numbers represent the needed number of occurances of a given
    // character that are needed to form an anagram.
    // negative numbers represent number of occurances of a character which is not
    // part of the anagram OR extra occurances of a character which IS part of the anagram.
    // Important note: a charCounts which contains all zero counts represents a state of
    // the anagram's existence.
    while (right < s.length()){
        char leftChar = s.charAt(left++);
        if (charCounts[toInt(leftChar)] >= 0){
            // the character we're moving away from is part of the anagram
            // therefore we need to add to the difference
            numDiff++;
        }
        charCounts[toInt(leftChar)]++; // record occurance of character whether of not it's part of the anagram

        char rightChar = s.charAt(right++);
        charCounts[toInt(rightChar)]--;
        // the really interesting part, we end up with negatives in charCounts in two following two cases
        // 1. if the character is not in the anagram
        // 2. if a character IS in the anagram but we don't need any more of it
        if (charCounts[toInt(rightChar)] >= 0){
            // remember that if by subtracting the count at the right edge the result is 0 or more, it means
            // we have found a character which belongs in the anagram
            numDiff--;
        }

        if (numDiff == 0){
            ans.add(left);
        }

    }

    return ans;
}

private int toInt(char c){
    return c - 'a';
}
*/
