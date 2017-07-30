public class Solution {
    public boolean isConnected(String a, String b, HashMap<String,List<String>> hm){
        if(hm.containsKey(a)){
            if(hm.get(a).contains(b)){
                return true;
            }
        }

        char aa[] = a.toCharArray();
        char bb[] = b.toCharArray();

        int count = 0;

        for(int i = 0; i < aa.length; i++){
            if(aa[i] == bb[i]) count++;
        }

        if(count == aa.length-1){
            if(!hm.containsKey(a)){
                hm.put(a, new LinkedList());
            }
            hm.get(a).add(b);
            if(!hm.containsKey(b)){
                hm.put(b, new LinkedList());
            }
            hm.get(b).add(a);
            return true;
        }
        else return false;
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        HashMap<String,List<String>> graph = new HashMap<>();

        // graph.put(beginWord, new LinkedList());
        // for(int i = 0; i < wordList.size(); i++){
        //     if(isConnected(beginWord, wordList.get(i))){
        //         graph.get(beginWord).add(wordList.get(i));
        //     }
        // }

        // for(int i = 0; i < wordList.size(); i++){
        //     for(int j = i; j < wordList.size(); j++){
        //         String a = wordList.get(i);
        //         String b = wordList.get(j);
        //         if(isConnected(a,b)){
        //             if(!graph.containsKey(a)){
        //                 graph.put(a, new LinkedList());
        //             }
        //             graph.get(a).add(b);
        //         }
        //     }
        // }

        // for(int i = 0; i < wordList.size(); i++){
        //     for(int j = 0; j < i; j++){
        //         String a = wordList.get(i);
        //         String b = wordList.get(j);
        //         if(!graph.containsKey(a)){
        //             graph.put(a, new LinkedList());
        //         }
        //         if(!graph.containsKey(b)){
        //             graph.put(b, new LinkedList());
        //         }
        //         if(graph.get(b).contains(a)){
        //             graph.get(a).add(b);
        //         }

        //     }
        // }


        // for(int i = 0; i < wordList.size(); i++){
        //     for(int j = 0; j < wordList.size(); j++){
        //         String a = wordList.get(i);
        //         String b = wordList.get(j);
        //         if(isConnected(a,b)){
        //             if(!graph.containsKey(a)){
        //                 graph.put(a, new LinkedList());
        //             }
        //             graph.get(a).add(b);
        //         }
        //     }
        // }

        //BFS
        Queue<String> q = new LinkedList<>();
        Set<String> hs =  new HashSet<>();

        int count = 0;

        q.offer(beginWord);

        while(!q.isEmpty()){
            int size = q.size();
            count++;
            for(int i = 0; i < size; i++){
                String temp = q.poll();
                if(temp.equals(endWord)){
                    return count;
                }
                // for(String next:graph.get(temp)){
                //     if(!hs.contains(next)){
                //         hs.add(next);
                //         q.offer(next);
                //     }
                // }
                for(int j = 0; j < wordList.size(); j++){
                    String next = wordList.get(j);
                    if(!hs.contains(next)){
                        if(isConnected(temp, next, graph)){
                            hs.add(next);
                            q.offer(next);
                        }
                    }
                }
            }

        }

        return 0;

    }
}


//TLE



/*(n/26) times faster
public int ladderLength(String beginWord, String endWord, Set<String> wordDict) {
    Set<String> reached = new HashSet<String>();
    reached.add(beginWord);
    wordDict.add(endWord);
    int distance = 1;
    while (!reached.contains(endWord)) {
        Set<String> toAdd = new HashSet<String>();
        for (String each : reached) {
            for (int i = 0; i < each.length(); i++) {
                char[] chars = each.toCharArray();
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    chars[i] = ch;
                    String word = new String(chars);
                    if (wordDict.contains(word)) {
                        toAdd.add(word);
                        wordDict.remove(word);
                    }
                }
            }
        }
        distance++;
        if (toAdd.size() == 0) return 0;
        reached = toAdd;
    }
    return distance;
}

Basically I keep two sets of words, one set reached that represents the borders that have been reached with "distance" steps; another set wordDict that has not been reached. In the while loop, for each word in the reached set, I give all variations and check if it matches anything from wordDict, if it has a match, I add that word into toAdd set, which will be my "reached" set in the next loop, and remove the word from wordDict because I already reached it in this step. And at the end of while loop, I check the size of toAdd, which means that if I can't reach any new String from wordDict, I won't be able to reach the endWord, then just return 0. Finally if the endWord is in reached set, I return the current steps "distance".

The idea is that reached always contain only the ones we just reached in the last step, and wordDict always contain the ones that haven't been reached. This is pretty much what Dijkstra's algorithm does, or you can see this as some variation of BFS.

ps: I get TLE at the first two submissions, because when I check if wordDict has any matches with reached set, I use two for loops and determine if any pair of words differ by one. That's a huge slow-down because it'll takes m (size of reached) * n (size of wordDict) * l (length of words) time, while in this solution, it takes 26 * l * m time. So when n is huge, this solution will be (n/26) times faster.



I think we can use a queue to replace the reached set, by which we can avoid duplicate check?

public class Solution {
    public int ladderLength(String beginWord, String endWord, Set<String> wordList) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        wordList.add(endWord);
        wordList.remove(beginWord);
        int level = 1;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i=0;i<size;i++){
                String str = queue.poll();
                if(str.equals(endWord))return level;
                for(String neighbor : neighbors(str,wordList)){
                    queue.offer(neighbor);
                }
            }
            level++;
        }
        return 0;
    }

    public List<String> neighbors(String s, Set<String> wordList){
        List<String> res = new LinkedList<>();
        for(int i=0;i<s.length();i++){
            char [] chars = s.toCharArray();
            for(char ch = 'a'; ch <= 'z'; ch++){
                chars[i] = ch;
                String word = new String(chars);
                if(wordList.remove(word)){
                    res.add(word);
                }
            }
        }
        return res;
    }
}
*/



/*Two end BFS
At last I'm able to understand. I learned a lot from this solution.

It's much faster than one-end search indeed as explained in wiki.
BFS isn't equivalent to Queue. Sometimes Set is more accurate representation for nodes of level. (also handy since we need to check if we meet from two ends)
It's safe to share a visited set for both ends since if they meet same string it terminates early. (vis is useful since we cannot remove word from dict due to bidirectional search)
It seems like if(set.add()) is a little slower than if(!contains()) then add() but it's more concise.
Thank you all for sharing and explaining!

update: the dictList is of List type now. And all transformed words including endWord must be in dictList.

public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> dict = new HashSet<>(wordList), qs = new HashSet<>(), qe = new HashSet<>(), vis = new HashSet<>();
    qs.add(beginWord);
    if (dict.contains(endWord)) qe.add(endWord); // all transformed words must be in dict (including endWord)
    for (int len = 2; !qs.isEmpty(); len++) {
        Set<String> nq = new HashSet<>();
        for (String w : qs) {
            for (int j = 0; j < w.length(); j++) {
                char[] ch = w.toCharArray();
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == w.charAt(j)) continue; // beginWord and endWord not the same, so bypass itself
                    ch[j] = c;
                    String nb = String.valueOf(ch);
                    if (qe.contains(nb)) return len; // meet from two ends
                    if (dict.contains(nb) && vis.add(nb)) nq.add(nb); // not meet yet, vis is safe to use
                }
            }
        }
        qs = (nq.size() < qe.size()) ? nq : qe; // switch to small one to traverse from other end
        qe = (qs == nq) ? qe : nq;
    }
    return 0;
}

"The idea behind bidirectional search is to run two simultaneous searches—one forward from
the initial state and the other backward from the goal—hoping that the two searches meet in
the middle. The motivation is that b^(d/2) + b^(d/2) is much less than b^d. b is branch factor, d is depth. "

----- section 3.4.6 in Artificial Intelligence - A modern approach by Stuart Russel and Peter Norvig
*/

