class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        if(s.length() == 0 || words.length == 0) return null;

        Map<String,Integer> hm = new HashMap<>();
        Map<String,Integer> copy = new HashMap<>();
        int pattern = words[0].length();

        for(String ss: words){
            int temp = !hm.containsKey(ss)?0:hm.get(ss);
            hm.put(ss,temp+1);
        }
        copy.putAll(hm);

        int start = 0, end = 0, count = words.length;
        List<Integer> result = ArrayList<>();

        while(end < s.length()-pattern){
            String key = end+pattern<s.length?s.substring(end):s.substring(end,end+pattern);
            if(hm.containsKey(key) && hm.size() != 0){
                hm.put(key,hm.get(key)-1);
                count--;
                if(hm.get(key)==0) hm.remove(key);
                if(hm.size() == 0){
                    result.add(start);
                }
                end += pattern;
            }
            else{
                if(count < words.length){
                    key = start+pattern<s.length?s.substring(start):s.substring(start,start+pattern);
                    if(copy.containsKey(key)){
                        hm.put(key,hm.get(key)+1);
                        count++;
                        start += pattern;
                        continue;
                    }
                }
                start++;
                end = start;
                hm.putAll(copy);
                count = words.length;
            }
        }

        return result;
    }
}



//多一层循环应该就可以做出来的，但本以为是严格O(n)，就没有继续做下去










//多了一层i<wl的精妙循环 好像也是O(n)？ L/wl*wl
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






//???
Its not too hard to find some resemblance between this problem and minimum-window-substring. Actually the main difference is the fact that we are interested at some interval length: we want intervals with fixed length K * M, where K is the number of strings in the "words" array and M the length of each target string. In order to apply the same idea we used for that problem, all we need to do is to map each string from the "words" array to something we are able to index (I prefer to use hashing for this). Also, in order to speed up the algorithm, we can find all occurrences of those strings in S (which is equivalent to do it on demand, but we will potentially do the same matching twice). Notice that, we can simply apply these occurrences as they appear because we are assured that no word is contained by some other. Finally, we use all this information to process each possibility. Notice here that, the fact that all strings has the same length, implies that we have just M (being M the length of each target string) possible starting points, hence we end up performing M linear scans over array with length O(N/M) (being N the length of S) and that makes the scanning stage of the algorithm to be linear on the length of S.

public List<Integer> findSubstring(String s, String[] words) {
    int N = s.length();
    List<Integer> indexes = new ArrayList<Integer>(s.length());
    if (words.length == 0) {
        return indexes;
    }
    int M = words[0].length();
    if (N < M * words.length) {
        return indexes;
    }
    int last = N - M + 1;

    //map each string in words array to some index and compute target counters
    Map<String, Integer> mapping = new HashMap<String, Integer>(words.length);
    int [][] table = new int[2][words.length];
    int failures = 0, index = 0;
    for (int i = 0; i < words.length; ++i) {
        Integer mapped = mapping.get(words[i]);
        if (mapped == null) {
            ++failures;
            mapping.put(words[i], index);
            mapped = index++;
        }
        ++table[0][mapped];
    }

    //find all occurrences at string S and map them to their current integer, -1 means no such string is in words array
    int [] smapping = new int[last];
    for (int i = 0; i < last; ++i) {
        String section = s.substring(i, i + M);
        Integer mapped = mapping.get(section);
        if (mapped == null) {
            smapping[i] = -1;
        } else {
            smapping[i] = mapped;
        }
    }

    //fix the number of linear scans
    for (int i = 0; i < M; ++i) {
        //reset scan variables
        int currentFailures = failures; //number of current mismatches
        int left = i, right = i;
        Arrays.fill(table[1], 0);
        //here, simple solve the minimum-window-substring problem
        while (right < last) {
            while (currentFailures > 0 && right < last) {
                int target = smapping[right];
                if (target != -1 && ++table[1][target] == table[0][target]) {
                    --currentFailures;
                }
                right += M;
            }
            while (currentFailures == 0 && left < right) {
                int target = smapping[left];
                if (target != -1 && --table[1][target] == table[0][target] - 1) {
                    int length = right - left;
                    //instead of checking every window, we know exactly the length we want
                    if ((length / M) ==  words.length) {
                        indexes.add(left);
                    }
                    ++currentFailures;
                }
                left += M;
            }
        }

    }
    return indexes;
}




//Concise
My idea is pretty simple. Just build a map for the words and their relative count in L. Then we traverse through S to check whether there is a match.

public static List<Integer> findSubstring(String S, String[] L) {
    List<Integer> res = new ArrayList<Integer>();
    if (S == null || L == null || L.length == 0) return res;
    int len = L[0].length(); // length of each word

    Map<String, Integer> map = new HashMap<String, Integer>(); // map for L
    for (String w : L) map.put(w, map.containsKey(w) ? map.get(w) + 1 : 1);

    for (int i = 0; i <= S.length() - len * L.length; i++) {
        Map<String, Integer> copy = new HashMap<String, Integer>(map);
        for (int j = 0; j < L.length; j++) { // checkc if match
            String str = S.substring(i + j*len, i + j*len + len); // next word
            if (copy.containsKey(str)) { // is in remaining words
                int count = copy.get(str);
                if (count == 1) copy.remove(str);
                else copy.put(str, count - 1);
                if (copy.isEmpty()) { // matches
                    res.add(i);
                    break;
                }
            } else break; // not in L
        }
    }
    return res;
}




//Using hashmap.equals()
Hey, guys!

Actually, there's nothing special in my solution except the comparably short Java code. It's just a simple sliding window approach which is greatly described in other posts.

In short, we got the source histogram from the dictionary L and build the new histogram for each possible window comparing it with the help of Javas equals method to the source one. Additionally, for the sake of tiny optimization, we check the starting word for being in the dictionary.

public List<Integer> findSubstring(String S, String[] L) {
    List<Integer> result = new ArrayList<>();
    int size = L[0].length();
    if (L.length == 0 || L[0].isEmpty() || L[0].length() > S.length())
        return result;
    Map<String, Integer> hist = new HashMap<>();
    for (String w : L) {
        hist.put(w, !hist.containsKey(w) ? 1 : hist.get(w)+1);
    }
    for (int i = 0; i+size*L.length <= S.length(); i++) {
        if (hist.containsKey(S.substring(i, i+size))) {
            Map<String, Integer> currHist = new HashMap<>();
            for (int j = 0; j < L.length; j++) {
                String word = S.substring(i+j*size, i+(j+1)*size);
                currHist.put(word, !currHist.containsKey(word) ?
                        1 : currHist.get(word)+1);
            }
            if (currHist.equals(hist)) result.add(i);
        }
    }
    return result;
}


Slightly improved version, so it consistently passes the test cases without getting TLE:

  public List<Integer> findSubstring(String s, String[] words) {
    List<Integer> ans = new ArrayList<>();
    if (s == null || words.length == 0) return ans;
    int n = words.length, wordLen = words[0].length();
    Map<String, Integer> hist = new HashMap<>();
    for (String w : words) {
        hist.put(w, hist.getOrDefault(w, 0)+1);
    }
    Map<String, Integer> curHist = new HashMap<>();
    for (int i = 0; i <= s.length() - n*wordLen; i++) {
        if (hist.containsKey(s.substring(i, i+wordLen))) {
            curHist.clear();
            for (int j = 0; j < n; j++) {
                String word = s.substring(i + j*wordLen, i+(j+1)*wordLen);
                if (hist.containsKey(word)) {
                    curHist.put(word, curHist.getOrDefault(word, 0) + 1);
                    if (curHist.get(word) > hist.get(word))
                        break;
                } else
                    break;
            }
            if (hist.equals(curHist)) ans.add(i);
        }
    }
    return ans;
}




//Using Trie Tree ???
The idea is quite simple. Just use a trie tree to accelerate testing whether a substring is valid. The value of each TrieNode is used to deal with duplication and to mark whether the word is used before.

static class TrieNode {
   int value = 0;
   Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();
}

TrieNode trie;

// build a trie tree
public List<Integer> findSubstring(String S, String[] L) {
    trie = buildTrie(L);
    int length = getTotalLength(L);
    List<Integer> result = new LinkedList<Integer>();
    for (int i = 0; i < S.length() - length + 1; i++) {
        if (isSubString(S, i, i + length))
            result.add(i);
    }
    return result;
}

private int getTotalLength(String[] L) {
    int sum = 0;
    for (String l : L)
        sum += l.length();
    return sum;
}

private TrieNode buildTrie(String[] L) {
    TrieNode root = new TrieNode();
    for (String l : L)
        addWord(root, l);
    return root;
}

private void addWord(TrieNode root, String s) {
    TrieNode node = root;
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        TrieNode next = node.children.get(c);
        if (next == null) {
            next = new TrieNode();
            node.children.put(c, next);
        }
        node = next;
    }
    node.value++;
}

private boolean isSubString(String S, int start, int end) {
    if (start == end)
        return true;
    // search in the trie tree
    TrieNode node = trie;
    for (int i = start; i < end; i++) {
        char c = S.charAt(i);
        if (node.children.get(c) == null)
            return false;
        node = node.children.get(c);
        if (node.value > 0) {  // leaf & can be used
            node.value--; // mark as used
            if (isSubString(S, i + 1, end)) {
                node.value++; // mark as unused
                return true;
            }
            node.value++; // mark as unused
        }
    }
    return false;
}





//Clearly explained
Maybe this is the so called window technique. The window was moved right by the while(){} loop. Since substring() is actually expensive in Java, each time I store the substring in a variable to avoid repeatedly calling the substring() function.

More specifically, there are three cases that the window changes:

1.The substring at the right of the window is not in words dictionary, then we move the whole window to the right side of this substring and set the window back to an empty window.

2.The substring at the right of window is a candidate word and not used out by the current window, then we add this substring into window. The windows right boundary extends. Now if the window is a valid solution, add the start index of window to result and cut off the head word of the window for further checking, the windows left boundary shrinks.

3.The substring at the right of window is a candidate word and is used out by the current window. Then we cut off the head word of the window, the windows left boundary shrinks (This would be done repeatedly by the while loop until the substring is not used out by the current window and could be added into the window).

public class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<Integer>();
        if(words.length==0||words[0].length()==0) return res;
        Map<String,Integer> wordDict = new HashMap<String,Integer>();
        for(String word : words) {
            if(!wordDict.containsKey(word)) wordDict.put(word,1);
            else wordDict.put(word,wordDict.get(word) + 1);
        }
        Map<String,Integer> currWords = new HashMap<String,Integer>();
        int len = words[0].length();
        for(int i = 0; i < len; i++) {
            int k = i, j = i; //k is at the head of the window and j is the last.
            int addedCount = 0; //to indicate whether we add index to res.
            while(k<= s.length()-len*words.length&&j + len <= s.length()) { //make sure the remaining length is enough.
                String subWord = s.substring(j,j+len);
                if(!wordDict.containsKey(subWord)) { //the substring is not in words, head jumps to the right of this substring.
                    addedCount = 0;
                    currWords.clear();
                    j += len;
                    k = j;
                    continue;
                }
                if(!currWords.containsKey(subWord)||currWords.get(subWord)!=wordDict.get(subWord)) {
                    if(!currWords.containsKey(subWord)) currWords.put(subWord,1);
                    else currWords.put(subWord,currWords.get(subWord) + 1); //update the current words we used.
                    addedCount++;
                    if(addedCount == words.length) { //if get a index, add it to res. And we need to continue checking
                        res.add(k);
                        addedCount--; //remove the head and check new substring, so count-- and move head to new position.
                        String preHead = s.substring(k,k+len);
                        if(currWords.get(preHead)==1) currWords.remove(preHead); //update the currWords map.
                        else currWords.put(preHead,currWords.get(preHead)-1);
                        k += len;
                    }
                    j += len;
                }
                else { //the current substring was used out before. Move head len steps right.
                    String preHead = s.substring(k,k+len);
                    addedCount--;
                    if(currWords.get(preHead)==1) currWords.remove(preHead); //update the currWords map.
                    else currWords.put(preHead,currWords.get(preHead)-1);
                    k += len; //don't move j this case.
                }
            }
            currWords.clear();
        }
        return res;
    }
}
