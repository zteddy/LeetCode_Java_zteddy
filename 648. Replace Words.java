class Solution {
    public String replaceWords(List<String> dict, String sentence) {
        Collections.sort(dict);
        String[] sArr= sentence.split(" ");

        for(int i = 0; i < sArr.length; i++){
            for(int j = 0; j < dict.size(); j++){
                if(sArr[i].startsWith(dict.get(j))){
                    sArr[i] = dict.get(j);
                    break;
                }
            }
        }
        return String.join(" ", sArr);
    }
}




/*Using Trie Tree
public String replaceWords(List<String> dict, String sentence) {
    String[] tokens = sentence.split(" ");
    TrieNode trie = buildTrie(dict);
    return replaceWords(tokens, trie);
}

private String replaceWords(String[] tokens, TrieNode root) {
    StringBuilder stringBuilder = new StringBuilder();
    for (String token : tokens) {
        stringBuilder.append(getShortestReplacement(token, root));
        stringBuilder.append(" ");
    }
    return stringBuilder.substring(0, stringBuilder.length()-1);
}

private String getShortestReplacement(String token, final TrieNode root) {
    TrieNode temp = root;
    StringBuilder stringBuilder = new StringBuilder();
    for (char c : token.toCharArray()) {
        stringBuilder.append(c);
        if (temp.children[c - 'a'] != null) {
            if (temp.children[c - 'a'].isWord) {
                return stringBuilder.toString();
            }
            temp = temp.children[c - 'a'];
        } else {
            return token;
        }
    }
    return token;
}

private TrieNode buildTrie(List<String> dict) {
    TrieNode root = new TrieNode(' ');
    for (String word : dict) {
        TrieNode temp = root;
        for (char c : word.toCharArray()) {
            if (temp.children[c - 'a'] == null) {
                temp.children[c - 'a'] = new TrieNode(c);
            }
            temp = temp.children[c - 'a'];
        }
        temp.isWord = true;
    }
    return root;
}

public class TrieNode {
    char val;
    TrieNode[] children;
    boolean isWord;

    public TrieNode(char val) {
        this.val = val;
        this.children = new TrieNode[26];
        this.isWord = false;
    }
}
*/



/*Recursive Trie
The only modification to the standard Trie, is that we need a function getShortestPrefix that returns the shortest prefix of the given word in the trie, if the shortest prefix exists or return the original word. Once we have this, all we have to do is iterate through the sentence and replace each word with the getShortestPrefix(word) in the trie.

public String replaceWords(List<String> dict, String sentence) {
    Trie trie = new Trie(256);
    dict.forEach(s -> trie.insert(s));
    List<String> res = new ArrayList<>();
    Arrays.stream(sentence.split(" ")).forEach(str -> res.add(trie.getShortestPrefix(str)));
    return res.stream().collect(Collectors.joining(" "));
}


class Trie {
    private int R;
    private TrieNode root;

    public Trie(int R) {
        this.R = R;
        root = new TrieNode();
    }

    // Returns the shortest prefix of the word that is there in the trie
    // If no such prefix exists, return the original word
    public String getShortestPrefix(String word) {
        int len =  getShortestPrefix(root, word, -1);
        return (len < 1) ? word : word.substring(0, len);
    }

    private int getShortestPrefix(TrieNode root, String word, int res) {
        if(root == null || word.isEmpty()) return 0;
        if(root.isWord) return res + 1;
        return getShortestPrefix(root.next[word.charAt(0)], word.substring(1), res+1);
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        insert(root, word);
    }

    private void insert(TrieNode root, String word) {
        if (word.isEmpty()) { root.isWord = true; return; }
        if (root.next[word.charAt(0)] == null) root.next[word.charAt(0)] = new TrieNode();
        insert(root.next[word.charAt(0)], word.substring(1));
    }

    private class TrieNode {
        private TrieNode[] next = new TrieNode[R];
        private boolean isWord;
    }
}
*/
