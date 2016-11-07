class TrieNode {
    public char value;
    public TrieNode[] children;
    public boolean isend;
    // Initialize your data structure here.
    public TrieNode() {
        children = new TrieNode[26];        //对于class只new好像就是null
    }
}

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
        root.isend = false;
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        char[] w = word.toCharArray();
        TrieNode temp;
        temp = root;
        for(int i = 0; i < w.length; i++){
            if(temp.children[w[i]-'a'] == null){
                temp.children[w[i]-'a'] = new TrieNode();
                temp.children[w[i]-'a'].value = w[i];
                temp = temp.children[w[i]-'a'];
            }
            else
                temp = temp = temp.children[w[i]-'a'];
        }
        temp.isend = true;
    }

    // Returns if the word is in the trie
    public boolean search(String word) {
        char[] w = word.toCharArray();
        TrieNode temp;
        temp = root;
        for(int i = 0; i < w.length; i++){
            if(temp.children[w[i]-'a'] == null){
                return false;
            }
            else
                temp = temp = temp.children[w[i]-'a'];
        }
        if(temp.isend != true) return false;
        else return true;

    }


    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        char[] w = prefix.toCharArray();
        TrieNode temp;
        temp = root;
        for(int i = 0; i < w.length; i++){
            if(temp.children[w[i]-'a'] == null){
                return false;
            }
            else
                temp = temp = temp.children[w[i]-'a'];
        }
        return true;
    }
}

// Your Trie object will be instantiated and called as such:
// Trie trie = new Trie();
// trie.insert("somestring");
// trie.search("key");


/*More private
class TrieNode {
    // R links to node children
    private TrieNode[] links;
    private final int R = 26;
    private boolean isEnd;
    public TrieNode() {
        links = new TrieNode[R];
    }
    public boolean containsKey(char ch) {
        return links[ch -'a'] != null;
    }
    public TrieNode get(char ch) {
        return links[ch -'a'];
    }
    public void put(char ch, TrieNode node) {
        links[ch -'a'] = node;
    }
    public void setEnd() {
        isEnd = true;
    }
    public boolean isEnd() {
        return isEnd;
    }
}
class Trie {
    private TrieNode root;
    public Trie() {
        root = new TrieNode();
    }
    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (!node.containsKey(currentChar)) {
                node.put(currentChar, new TrieNode());
            }
            node = node.get(currentChar);
        }
        node.setEnd();
    }
    // search a prefix or whole key in trie and
    // returns the node where search ends
    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
           char curLetter = word.charAt(i);
           if (node.containsKey(curLetter)) {
               node = node.get(curLetter);
           } else {
               return null;
           }
        }
        return node;
    }
    // Returns if the word is in the trie.
    public boolean search(String word) {
       TrieNode node = searchPrefix(word);
       return node != null && node.isEnd();
    }
    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }
}
