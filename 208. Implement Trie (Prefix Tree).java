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


/*Summary
Trie node structure

Trie is a rooted tree. Its nodes have the following fields:

Maximum of RR links to its children, where each link corresponds to one of RR character values from dataset alphabet. In this article we assume that RR is 26, the number of lowercase latin letters.
Boolean field which specifies whether the node corresponds to the end of the key, or is just a key prefix.
Representation of a key in trie

Figure 6. Representation of a key "leet" in trie.

Java

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
Two of the most common operations in a trie are insertion of a key and search for a key.

Insertion of a key to a trie

We insert a key by searching into the trie. We start from the root and search a link, which corresponds to the first key character. There are two cases :

A link exists. Then we move down the tree following the link to the next child level. The algorithm continues with searching for the next key character.
A link does not exist. Then we create a new node and link it with the parent's link matching the current key character. We repeat this step until we encounter the last character of the key, then we mark the current node as an end node and the algorithm finishes.
Insertion of keys into a trie

Figure 7. Insertion of keys into a trie.

Java

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
}
Complexity Analysis

Time complexity : O(m)O(m), where m is the key length.
In each iteration of the algorithm, we either examine or create a node in the trie till we reach the end of the key. This takes only mm operations.

Space complexity : O(m)O(m).
In the worst case newly inserted key doesn't share a prefix with the the keys already inserted in the trie. We have to add mm new nodes, which takes us O(m)O(m) space.

Search for a key in a trie

Each key is represented in the trie as a path from the root to the internal node or leaf. We start from the root with the first key character. We examine the current node for a link corresponding to the key character. There are two cases :

A link exist. We move to the next node in the path following this link, and proceed searching for the next key character.
A link does not exist. If there are no available key characters and current node is marked as isEnd we return true. Otherwise there are possible two cases in each of them we return false :

There are key characters left, but it is impossible to follow the key path in the trie, and the key is missing.
No key characters left, but current node is not marked as isEnd. Therefore the search key is only a prefix of another key in the trie.
Search of a key in a trie

Figure 8. Search for a key in a trie.

Java

class Trie {
    ...

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
}
Complexity Analysis

Time complexity : O(m)O(m) In each step of the algorithm we search for the next key character. In the worst case the algorithm performs mm operations.

Space complexity : O(1)O(1)

Search for a key prefix in a trie

The approach is very similar to the one we used for searching a key in a trie. We traverse the trie from the root, till there are no characters left in key prefix or it is impossible to continue the path in the trie with the current key character. The only difference with the mentioned above search for a key algorithm is that when we come to an end of the key prefix, we always return true. We don't need to consider the isEnd mark of the current trie node, because we are searching for a prefix of a key, not for a whole key.

Search of a key prefix in a trie

Figure 9. Search for a key prefix in a trie.

Java

class Trie {
    ...

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }
}
Complexity Analysis

Time complexity : O(m)O(m)

Space complexity : O(1)O(1)
*/

