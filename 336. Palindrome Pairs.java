class Solution {
    public boolean isPanlindrome(String s){
        char[] sArr = s.toCharArray();
        for(int i = 0; i < sArr.length/2; i++){
            if(sArr[i] != sArr[sArr.length-i-1]) return false;
        }
        return true;

    }
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i < words.length; i++){
            for(int j = i+1; j < words.length; j++){
                if(isPanlindrome(words[i]+words[j])){
                    List<Integer> temp = new ArrayList<>();
                    temp.add(i);
                    temp.add(j);
                    result.add(temp);
                }
                if(isPanlindrome(words[j]+words[i])){
                    List<Integer> temp = new ArrayList<>();
                    temp.add(j);
                    temp.add(i);
                    result.add(temp);
                }
            }
        }
        return result;
    }
}


//TLE





//Using HashMap form O(NNK) to O(NKK)
public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> ret = new ArrayList<>();
    if (words == null || words.length < 2) return ret;
    Map<String, Integer> map = new HashMap<String, Integer>();
    for (int i=0; i<words.length; i++) map.put(words[i], i);
    for (int i=0; i<words.length; i++) {
        // System.out.println(words[i]);
        for (int j=0; j<=words[i].length(); j++) { // notice it should be "j <= words[i].length()"
            String str1 = words[i].substring(0, j);
            String str2 = words[i].substring(j);
            if (isPalindrome(str1)) {
                String str2rvs = new StringBuilder(str2).reverse().toString();
                if (map.containsKey(str2rvs) && map.get(str2rvs) != i) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(map.get(str2rvs));
                    list.add(i);
                    ret.add(list);
                    // System.out.printf("isPal(str1): %s\n", list.toString());
                }
            }
            if (isPalindrome(str2)) {
                String str1rvs = new StringBuilder(str1).reverse().toString();
                // check "str.length() != 0" to avoid duplicates
                if (map.containsKey(str1rvs) && map.get(str1rvs) != i && str2.length()!=0) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(i);
                    list.add(map.get(str1rvs));
                    ret.add(list);
                    // System.out.printf("isPal(str2): %s\n", list.toString());
                }
            }
        }
    }
    return ret;
}

private boolean isPalindrome(String str) {
    int left = 0;
    int right = str.length() - 1;
    while (left <= right) {
        if (str.charAt(left++) !=  str.charAt(right--)) return false;
    }
    return true;
}
The <= in for (int j=0; j<=words[i].length(); j++) is aimed to handle empty string in the input. Consider the test case of ["a", ""];

Since we now use <= in for (int j=0; j<=words[i].length(); j++) instead of <. There may be duplicates in the output (consider test case ["abcd", "dcba"]). Therefore I put a str2.length()!=0 to avoid duplicates.

Another way to avoid duplicates is to use Set<List<Integer>> ret = new HashSet<>(); and return new ArrayList<>(ret);





//Using Trie
Apparently there is a O(n^2*k) naive solution for this problem, with n the total number of words in the "words" array and k the average length of each word:

For each word, we simply go through the words array and check whether the concatenated string is a palindrome or not.

Of course this will result in TLE as expected. To improve the algorithm, we need to reduce the number of words that is needed to check for each word, instead of iterating through the whole array. This prompted me to think if I can extract any useful information out of the process of checking whether the concatenated string is a palindrome, so that it can help eliminate as many words as possible for the rest of the words array.

To begin, here is the technique I employed to check for palindromes: maintain two pointers i and j, with i pointing to the start of the string and j to the end of the string. Characters pointed by i and j are compared. If at any time the characters pointed by them are not the same, we conclude the string is not a palindrome. Otherwise we move the two pointers towards each other until they meet in the middle and the string is a palindrome.

By examining the process above, I do find something that we may take advantage of to get rid of words that need to be checked otherwise. For example, lets say we want to append words to w0, which starts with character 'a'. Then we only need to consider words ending with character 'a', i.e., this will single out all words ending with character 'a'. If the second character of w0 is 'b' for instance, we can further reduce our candidate set to words ending with string "ba", etc. Our naive solution throws away all this "useful" information and repeats the comparison, which leads to the undesired O(n^2*k) time complexity.

In order to exploit the information gathered so far, we obviously need to restructure all the words in the words array. If you are familiar with Trie structure (I believe you are, since LeetCode has problems for it. In case you are not, see Trie), it will come to mind as we need to deal with words with common suffixes. The next step is to design the structure for each Trie node. There are at least two fields that should be covered for each TrieNode: a TrieNode array denoting the next layer of nodes and a boolean (or integer) to signify the end of a word. So our tentative TrieNode will look like this:

class TrieNode {
    TrieNode[] next;
    boolean isWord;
}
One point here is that we assume all the words contain lowercase letters only. This is not specified in the problem statement so you probably need to confirm with the interviewer (here I assume it is the case)

Now we will rearrange each word into this Trie structure: for each word, simply starting from its last character and identify the node at the next layer by indexing into roots next array with index given by the difference between the ending character and character 'a'. If the indexed node is null, create a new node. Continue to the next layer and towards the beginning of the word in this manner until we are done with the word, at which point we will label the isWord field of the final node as true.

After building up the Trie structure, we can proceed to search for pairs of palindromes for each word in the words array. I will use the following example to explain how it works and make possible modifications of the TrieNode we proposed above.

Lets say we have these words: ["ba", "a", "aaa"], the Trie structure will be as follows:

        root (f)
           | 'a'
          n1 (t)
     ------------
 'b' |          | 'a'
    n2 (t)    n3 (f)
                | 'a'
              n4 (t)
The letter in parentheses indicates the value of isWord for each node: f ==> false and t ==> true. The letter beside each vertical line denotes the index into the next array of the corresponding node. For example, for the first vertical line, 'a' means root.next[0] is not null. Similarly 'b' means n1.next[1] is not null, and so on.

Here is the searching process:

For word "ba", starting from the first character 'b', index into the root.next array with index given by 'b' - 'a' = 1. The corresponding node is null, then we know there are no words ending at this character, so the searching process is terminated;
For word "a", again indexing into array root.next at index given by 'a' - 'a' = 0 will yield node n1, which is not null. We then check the value of n1.isWord. If it is true, then it is possible to obtain a palindrome by appending this word to the one currently being examined (a.k.a word "a"). Also note that the two words should be distinct, but the n1.isWord field provides no information about the word itself, which makes it impossible to distinguish the two words. So it is necessary to modify the structure of the TrieNode so that we can identify the word it represents. One easy way is to have an integer field to remember the index of the word in the words array. For non-word nodes, this integer will take negative values (-1 for example) while for those representing a word, it will be non-negative values. Suppose we have made this modification, then the two words will be identified to be the same, so we discard this pair combination. Since the word "a" has only one letter, it seems we are done with it. Or do we? Not really. What if there are words with suffix "a" ("aaa" in this case)? We need to continue to check the rest part of these words (such as "aa" for the word "aaa") and see if the rest forms a palindrome. If it is, then appending this word ("aaa" in this case) to the original word ("a") will also form a palindrome ("aaaa"). Here I take another strategy: add an integer list to each TrieNode; the list will record the indices of all words satisfying the following two conditions: each word has a suffix represented by the current Trie node; the rest of the word forms a palindrome.
Before I get to the third word "aaa", let me spell out the new TrieNode and the corresponding Trie structure for the above array.

TrieNode:

class TrieNode {
    TrieNode[] next;
    int index;
    List<Integer> list;

    TrieNode() {
        next = new TrieNode[26];
        index = -1;
        list = new ArrayList<>();
    }
}
Trie structure:

          root (-1,[1,2])
            | 'a'
          n1 (1,[0,1,2])
    ---------------------
'b' |                 | 'a'
  n2 (0,[0])    n3 (-1,[2])
                      | 'a'
                 n4 (2,[2])
The first integer in the parentheses is the index of the word in the words array (defaulted to -1). The integers in the square bracket are the indices of words satisfying the two conditions mentioned above.

Lets continue with the third word "aaa" with this new structure. Indexing into array root.next at index given by 'a' - 'a' = 0 will yield node n1 and n1.index = 1 >= 0, which means we have a valid word now. The index of this word (which is 1) is also different from the index of the word currently being visited, a.k.a "aaa" (which is 2). So pair (2,1) is a possible concatenation to form a palindrome. But still we need to check the rest of "aaa" (excluding the substring represented by current node n1 which is "a" from the beginning of "aaa") to see if it is a palindrome. If so, (2,1) will be a valid combination. We continue in this fashion until we reach the end of "aaa". Lastly we will check n4.list to see if there are any words satisfying the two conditions specified in step 2 which are different from current word, and add the corresponding valid pairs.

Both building and searching the Trie structure take O(n*k^2), which set the total time complexity of the solution. See the complete Java program in the answer.

Here is a complete Java program:

class TrieNode {
    TrieNode[] next;
    int index;
    List<Integer> list;

    TrieNode() {
        next = new TrieNode[26];
        index = -1;
        list = new ArrayList<>();
    }
}

public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> res = new ArrayList<>();

    TrieNode root = new TrieNode();
    for (int i = 0; i < words.length; i++) addWord(root, words[i], i);
    for (int i = 0; i < words.length; i++) search(words, i, root, res);

    return res;
}

private void addWord(TrieNode root, String word, int index) {
    for (int i = word.length() - 1; i >= 0; i--) {
        int j = word.charAt(i) - 'a';
        if (root.next[j] == null) root.next[j] = new TrieNode();
        if (isPalindrome(word, 0, i)) root.list.add(index);
        root = root.next[j];
    }

    root.list.add(index);
    root.index = index;
}

private void search(String[] words, int i, TrieNode root, List<List<Integer>> res) {
    for (int j = 0; j < words[i].length(); j++) {
        if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
            res.add(Arrays.asList(i, root.index));
        }

        root = root.next[words[i].charAt(j) - 'a'];
        if (root == null) return;
    }

    for (int j : root.list) {
        if (i == j) continue;
        res.add(Arrays.asList(i, j));
    }
}

private boolean isPalindrome(String word, int i, int j) {
    while (i < j) {
        if (word.charAt(i++) != word.charAt(j--)) return false;
    }

    return true;
}
We have the TrieNode structure at the top. In the "palindromePairs" function, we build up the Trie by adding each word, then searching for valid pairs for each word and record the results in the "res" list. The last "isPalindrome" function checks if the substring [i, j] (both inclusive) of the given word is a palindrome.






//Using Manacher
Added: a (not so) naive solution

If two concatenated words form a palindrome, then there are three cases to consider:

+---s1---+---s2--+     +---s1---+-s2-+    +-s1-+---s2---+
|abcdefgh|hgfedcba|    |abcdxyyx|dcba|    |abcd|xyyxdcba|
Case 1 is when one string is a mirror image of another. Case 2 is when the first string is longer than the other and consists of the mirror image of the other (prefix) and a palindrome (suffix). Case 3 is a mirror image of case 2. Case 1 can also be considered a special subcase of either case 2 or case 3 with an empty palindrome suffix/prefix.

Of these three, case 1 is definitely the easiest because we just need to look up a word in a reverse string-to-index map (words are unique, so no multimaps needed). If we iterate over the list with s1 as the current string, then case 2 is also much easier than case 3 because when we locate a prefix/palindrome split inside s1 we just need to look up for the reversed prefix in the map.

Case 3 is trickier, but we can get rid of case 3 altogether if we just make another run with the reversed words! This way case 3 turns into case 2. We only need to consider case 1 in one of these runs in order to avoid duplicate combinations. With that in mind, I present the following 154 ms solution:

public List<List<Integer>> palindromePairs(String[] words) {
    Map<String, Integer> index = new HashMap<>();
    Map<String, Integer> revIndex = new HashMap<>();
    String[] revWords = new String[words.length];
    for (int i = 0; i < words.length; ++i) {
        String s = words[i];
        String r = new StringBuilder(s).reverse().toString();
        index.put(s, i);
        revIndex.put(r, i);
        revWords[i] = r;
    }
    List<List<Integer>> result = new ArrayList<>();
    result.addAll(findPairs(words, revWords, revIndex, false));
    result.addAll(findPairs(revWords, words, index, true));
    return result;
}

private static List<List<Integer>> findPairs(String[] words, String[] revWords, Map<String, Integer> revIndex, boolean reverse) {
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < words.length; ++i) {
        String s = words[i];
        for (int k = reverse ? 1 : 0; k <= s.length(); ++k) { // check suffixes, <= because we allow empty words
            Integer j = revIndex.get(s.substring(k));
            if (j != null && j != i) { // reversed suffix is present in the words list
                // check whether the prefix is a palindrome
                if (s.regionMatches(0, revWords[i], s.length() - k, k)) {
                    result.add(reverse ? Arrays.asList(i, j) : Arrays.asList(j, i));
                }
            }
        }
    }
    return result;
}
Now this was actually the third solution I came up with. The first one was really ugly because it considered all three cases separately. The second one is below.

The ugly optimized solution

As in other solutions posted here, I used Manacher's algorithm to quickly determine whether some part of a string is a palindrome or not. That gets rid of one O(nk^2) part, where k is the average word length and n is the number of words.

Another part is numerous calls to substring, so my idea is to avoid copying a substring unless there's a good chance that it's actually present in the list. I do this by creating a kind of ad-hoc hash tables for both reversed and non-reversed words.

We start by iterating over the list of the words and compute hashes for both reversed and non-reversed words. However, because I later calculate hashes of suffixes on the fly, which means that I calculate them right-to-left, so it is kind of mixed up which hash is reversed and which is not.

Then we just compute every suffix's hash and look up the matching words. Updating the hash as we go, we avoid O(nk^2) complexity and get O(nk) which is the best we can get since we have to analyze all words. To consider all cases, we do it twice, for reversed and non-reversed words.

public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> result = new ArrayList<>();
    int bCount = Integer.highestOneBit(words.length - 1) << 1; // round up to power of 2
    List<Integer>[] buckets = new List[bCount];
    List<Integer>[] revBuckets = new List[bCount];
    String[] revWords = new String[words.length];
    for (int i = 0; i < words.length; ++i) {
        String s = words[i];
        String r = new StringBuilder(s).reverse().toString();
        revWords[i] = r;
        int h = 0, hrev = 0;
        for (int j = 0; j < s.length(); ++j) {
            h = h * 17 + r.charAt(j); // will compute hash for suffixes in reversed order
            hrev = hrev * 17 + s.charAt(j); // so here s and r are swapped
        }
        h = (h & Integer.MAX_VALUE) % buckets.length;
        hrev = (hrev & Integer.MAX_VALUE) % revBuckets.length;
        if (buckets[h] == null) {
            buckets[h] = new ArrayList<>();
        }
        buckets[h].add(i);
        if (revBuckets[hrev] == null) {
            revBuckets[hrev] = new ArrayList();
        }
        revBuckets[hrev].add(i);
    }
    for (int i = 0; i < words.length; ++i) {
        String s = words[i];
        int[] m = manacher(s);
        for (int j : findPairs(s, m, 0, revBuckets, revWords)) {
            if (i != j) {
                result.add(Arrays.asList(j, i));
            }
        }
        s = revWords[i];
        for (int j = 0, k = m.length - 1; j < k; ++j, --k) {
            int tmp = m[j];
            m[j] = m[k];
            m[k] = tmp;
        }
        for (int j : findPairs(s, m, 1, buckets, words)) {
            if (i != j) {
                result.add(Arrays.asList(i, j));
            }
        }
    }
    return result;
}

private static List<Integer> findPairs(String s, int[] manacher, int end,
        List<Integer>[] buckets, String[] words) {
    List<Integer> pairs = new ArrayList<>();
    for (int i = s.length(), h = 0;; ) {
        assert i + manacher[i] <= 2 * i;
        if (i + manacher[i] == 2 * i) {
            List<Integer> bucket = buckets[(h & Integer.MAX_VALUE) % buckets.length];
            if (bucket != null) {
                for (int j : bucket) {
                    if (s.length() - i == words[j].length() && s.regionMatches(i, words[j], 0, words[j].length())) {
                        pairs.add(j);
                    }
                }
            }
        }
        if (--i < end) {
            break;
        }
        h = h * 17 + s.charAt(i);
    }
    return pairs;
}

private static int[] manacher(String s) {
    final int n = s.length() * 2 + 1;
    int[] p = new int[n];
    for (int i = 0, c = 0; i < n; ++i) {
        int r = c + p[c], il, ir;
        if (i > r) {
            il = i - 1;
            ir = i + 1;
        } else {
            int i2 = c - (i - c);
            if (i + p[i2] >= r) {
                ir = r + 1;
                il = i - (ir - i);
                p[i] = r - i;
            } else {
                p[i] = p[i2];
                il = ir = -1; // skip the check
            }
        }
        while (il >= 0 && ir < n && ((il & 1) == 0 || s.charAt(il / 2) == s.charAt(ir / 2))) {
            ++p[i];
            --il;
            ++ir;
        }
        if (i + p[i] > r) {
            c = i;
        }
    }
    return p;
}




//Brutal Force的优化
public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    for(int i = 0; i < words.length; i++) {
        for(int j = 0; j < words.length; j++) {
            if(i != j && isCombinedStringsPalindrome(words[i], words[j])) {
                List<Integer> list = new ArrayList<Integer>();
                list.add(i);
                list.add(j);
                result.add(list);
            }
        }
    }
    return result;
}

public boolean isCombinedStringsPalindrome(String a, String b) {
    // in case of 'lls' and 's', it returns false.
    // but returns true for 's' and 'lls', which is covered when words[i] = 's';
    int aEnd = a.length()-1, aStart = 0;
    int bEnd = b.length()-1, bStart = 0;
    //compare both a and b, regardless of their lengths
    while(bEnd >= 0 && aStart <= aEnd) {
        if(a.charAt(aStart) != b.charAt(bEnd)) return false;
        bEnd--;aStart++;
    }

    // if 'a' is still remaining, check if it's a palindrome.
    while(aStart < aEnd) {
        if(a.charAt(aStart) != a.charAt(aEnd)) return false;
        aStart++;aEnd--;
    }

    // if 'b' is still remaining, check if it's a palindrome.
    while(bStart < bEnd) {
        if(b.charAt(bStart) != b.charAt(bEnd)) return false;
        bStart++;bEnd--;
    }

    return true;
}





//Using Trie 2
Thanks ChiCelines solution, I learnt from the Python code and create this java version. The raw time complexity calculate as O(n * len * len).

Ill appreciate it if you could provide any suggestion.

public class Solution {
    class TrieNode {
        TrieNode[] children;
        ArrayList<Integer> ids;
        int index; // index of current word
        public TrieNode() {
            this.children = new TrieNode[26];
            this.ids = new ArrayList<Integer>();
            this.index = -1;
        }
    }

    private TrieNode root;

    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        if (words.length < 2) return list;
        List<Integer> empty = new ArrayList<Integer>(); // to record index of empty word
        root = new TrieNode();

        for (int i = 0; i < words.length; i++) { // O(n*len*len)
            if (words[i].length() == 0) {
                empty.add(i);
                continue;
            }
            insert(words[i], i);
        }

        for (int i = 0; i < words.length; i++) { // (n*len)
            search(words[i], list, i, empty);
        }
        return list;
    }

    private void search(String word, List<List<Integer>> list, int idx, List<Integer> empty) {
        char[] wd = word.toCharArray();
        TrieNode cur = root;

        for (int i = wd.length - 1; i >= 0; i--) { // O(len)
            int pt = wd[i] - 'a';
            if (cur.index != -1 && isPalin(wd, 0, i)) { // prefix is palin, "cur.index != -1" means the end of a word
                List<Integer> li = new ArrayList<Integer>();
                li.add(cur.index);
                li.add(idx);
                list.add(li);
            }
            if (cur.children[pt] == null) return; // nothing to compare for the next step
            cur = cur.children[pt];
        }

        if (cur.ids.size() != 0) { // suffix is palin
            for (Integer in : cur.ids) {
                if (in != idx) {
                    List<Integer> li = new ArrayList<Integer>();
                    li.add(in);
                    li.add(idx);
                    list.add(li);
                } else {
                    for (Integer em : empty) {
                        List<Integer> li = new ArrayList<Integer>();
                        li.add(em);
                        li.add(idx);
                        list.add(li);
                        li = new ArrayList<Integer>();
                        li.add(idx);
                        li.add(em);
                        list.add(li);
                    }
                }
            }
        }
    }

    private void insert(String word, int idx) { // O(len*len)
        char[] wd = word.toCharArray();
        TrieNode cur = root;

        for (int i = 0; i < wd.length; i++) {
            int pt = wd[i] - 'a';
            if (cur.children[pt] == null) {
                cur.children[pt] = new TrieNode();
            }
            cur = cur.children[pt];
            if (i == wd.length - 1) {
                cur.index = idx;
            }
            if (isPalin(wd, i + 1, wd.length - 1)) {
                cur.ids.add(idx);
            }
        }
    }

    private boolean isPalin(char[] str, int i, int j) {
        while (j - i > 0) {
            if (str[i++] != str[j--]) return false;
        }
        return true;
    }
}
