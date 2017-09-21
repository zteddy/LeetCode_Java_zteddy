class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        Queue<List<String>> q = new LinkedList<>();
        List<List<String>> result = new ArrayList<>();

        // Set<String> visited = new HashSet<>();

        List<String> temp = new ArrayList<>();
        temp.add(beginWord);

        int minLength = wordList.size();

        q.offer(temp);
        while(!q.isEmpty()){
            // Set<String> visited = new HashSet<>();
            temp = q.poll();
            if(temp.size() >= minLength) break;
            // char[] now = temp.get(temp.size()-1).toCharArray();
            String now = temp.get(temp.size()-1);
            char[] now_c = now.toCharArray();
            for(int i = 0; i < now.length(); i++){
                char original = now_c[i];
                for(char c = 'a'; c <= 'z'; c++){
                    // if(now.charAt(i) == c) continue;
                    if(c == original) continue;
                    now_c[i] = c;
                    // String newString = now.substring(0,i) + c + ((i==now.length()-1)?"":now.substring(i+1));
                    String newString = new String(now_c);
                    // if(visited.contains(newString)) continue;
                    // visited.add(newString);
                    if(temp.contains(newString)) continue;
                    if(wordList.contains(newString)){
                        List<String> newResult = new ArrayList<>(temp);
                        // newResult.addAll(temp);
                        newResult.add(newString);
                        q.offer(newResult);
                        if(newString.equals(endWord)){
                            result.add(newResult);
                            if(newResult.size() < minLength) minLength = newResult.size();
                        }
                    }
                }
                now_c[i] = original;
            }
        }

        return result;

    }
}


//TLE
//单边BFS还是太慢
//关键问题还在于没有一个dict.removeAll(wordsPerLevel)的操作！！!






//dict.removeAll(wordsPerLevel);
/**
 * we are essentially building a graph, from start, BF.
 * and at each level we find all reachable words from parent.
 * we stop if the current level contains end,
 * we return any path whose last node is end.
 *
 * to achieve BFT, use a deuqe;
 * a key improvement is to remove all the words we already reached
 * in PREVIOUS LEVEL; we don't need to try visit them again
 * in subsequent level, that is guaranteed to be non-optimal solution.
 * at each new level, we will removeAll() words reached in previous level from dict.
 */
public List<List<String>> findLadders(String start, String end, Set<String> dict) {
    List<List<String>> results = new ArrayList<List<String>>();
    dict.add(end);
    // instead of storing words we are at, we store the paths.
    Deque<List<String>> paths = new LinkedList<List<String>>();
    List<String> path0 = new LinkedList<String>();
    path0.add(start);
    paths.add(path0);
    // if we found a path ending at 'end', we will set lastLevel,
    // use this data to stop iterating further.
    int level = 1, lastLevel = Integer.MAX_VALUE;
    Set<String> wordsPerLevel = new HashSet<String>();
    while (!paths.isEmpty()) {
        List<String> path = paths.pollFirst();
        if (path.size() > level) {
            dict.removeAll(wordsPerLevel);
            wordsPerLevel.clear();
            level = path.size();
            if (level > lastLevel)
                break; // stop and return
        }
        //  try to find next word to reach, continuing from the path
        String last = path.get(level - 1);
        char[] chars = last.toCharArray();
        for (int index = 0; index < last.length(); index++) {
            char original = chars[index];
            for (char c = 'a'; c <= 'z'; c++) {
                chars[index] = c;
                String next = new String(chars);
                if (dict.contains(next)) {
                    wordsPerLevel.add(next);
                    List<String> nextPath = new LinkedList<String>(path);
                    nextPath.add(next);
                    if (next.equals(end)) {
                        results.add(nextPath);
                        lastLevel = level; // curr level is the last level
                    } else
                        paths.addLast(nextPath);
                }
            }
            chars[index] = original;
        }
    }

    return results;
}







//Using BFS to draw a Graph than DFS(Backtracking)
Explanation

The basic idea is:

1). Use BFS to find the shortest distance between start and end, tracing the distance of crossing nodes from start node to end node, and store nodes next level neighbors to HashMap;

2). Use DFS to output paths with the same distance as the shortest distance from distance HashMap: compare if the distance of the next level node equals the distance of the current node + 1.

public List<List<String>> findLadders(String start, String end, List<String> wordList) {
   HashSet<String> dict = new HashSet<String>(wordList);
   List<List<String>> res = new ArrayList<List<String>>();
   HashMap<String, ArrayList<String>> nodeNeighbors = new HashMap<String, ArrayList<String>>();// Neighbors for every node
   HashMap<String, Integer> distance = new HashMap<String, Integer>();// Distance of every node from the start node
   ArrayList<String> solution = new ArrayList<String>();

   dict.add(start);
   bfs(start, end, dict, nodeNeighbors, distance);
   dfs(start, end, dict, nodeNeighbors, distance, solution, res);
   return res;
}

// BFS: Trace every node's distance from the start node (level by level).
private void bfs(String start, String end, Set<String> dict, HashMap<String, ArrayList<String>> nodeNeighbors, HashMap<String, Integer> distance) {
  for (String str : dict)
      nodeNeighbors.put(str, new ArrayList<String>());

  Queue<String> queue = new LinkedList<String>();
  queue.offer(start);
  distance.put(start, 0);

  while (!queue.isEmpty()) {
      int count = queue.size();
      boolean foundEnd = false;
      for (int i = 0; i < count; i++) {
          String cur = queue.poll();
          int curDistance = distance.get(cur);
          ArrayList<String> neighbors = getNeighbors(cur, dict);

          for (String neighbor : neighbors) {
              nodeNeighbors.get(cur).add(neighbor);
              if (!distance.containsKey(neighbor)) {// Check if visited
                  distance.put(neighbor, curDistance + 1);
                  if (end.equals(neighbor))// Found the shortest path
                      foundEnd = true;
                  else
                      queue.offer(neighbor);
                  }
              }
          }

          if (foundEnd)
              break;
      }
  }

// Find all next level nodes.
private ArrayList<String> getNeighbors(String node, Set<String> dict) {
  ArrayList<String> res = new ArrayList<String>();
  char chs[] = node.toCharArray();

  for (char ch ='a'; ch <= 'z'; ch++) {
      for (int i = 0; i < chs.length; i++) {
          if (chs[i] == ch) continue;
          char old_ch = chs[i];
          chs[i] = ch;
          if (dict.contains(String.valueOf(chs))) {
              res.add(String.valueOf(chs));
          }
          chs[i] = old_ch;
      }

  }
  return res;
}

// DFS: output all paths with the shortest distance.
private void dfs(String cur, String end, Set<String> dict, HashMap<String, ArrayList<String>> nodeNeighbors, HashMap<String, Integer> distance, ArrayList<String> solution, List<List<String>> res) {
    solution.add(cur);
    if (end.equals(cur)) {
       res.add(new ArrayList<String>(solution));
    } else {
       for (String next : nodeNeighbors.get(cur)) {
            if (distance.get(next) == distance.get(cur) + 1) {
                 dfs(next, end, dict, nodeNeighbors, distance, solution, res);
            }
        }
    }
   solution.remove(solution.size() - 1);
}


first， nice code
secondly： two points to explain the code for better understanding：

if (distance.get(next) == distance.get(cur) + 1) {
                  dfs(next, end, dict, nodeNeighbors, distance, solution, res);
              }
in dfs , thereason for if (distance.get(next) == distance.get(cur) + 1) is just in case that the next node is the next level of current node， otherwise it can be one of the parent nodes of current node，or it is not the shortest node . Since in BFS, we record both the next level nodes and the parent node as neighbors of current node. use distance.get(cur)+1 we can make sure the path is the shortest one.

in BFS , we can be sure that the distance of each node is the shortest one , because once we have visited a node, we update the distance , if we first met one node ,it must be the shortest distance. if we met the node again ,its distance must not be less than the distance we first met and set.











//Using BFS to draw a Graph than DFS(Backtracking) 2
The solution contains two steps 1 Use BFS to construct a graph. 2. Use DFS to construct the paths from end to start.Both solutions got AC within 1s.

The first step BFS is quite important. I summarized three tricks

Using a MAP to store the min ladder of each word, or use a SET to store the words visited in current ladder, when the current ladder was completed, delete the visited words from unvisited. Thats why I have two similar solutions.
Use Character iteration to find all possible paths. Do not compare one word to all the other words and check if they only differ by one character.
One word is allowed to be inserted into the queue only ONCE. See my comments.
public class Solution {
    Map<String,List<String>> map;
    List<List<String>> results;
    public List<List<String>> findLadders(String start, String end, Set<String> dict) {
        results= new ArrayList<List<String>>();
        if (dict.size() == 0)
            return results;

        int min=Integer.MAX_VALUE;

        Queue<String> queue= new ArrayDeque<String>();
        queue.add(start);

        map = new HashMap<String,List<String>>();

        Map<String,Integer> ladder = new HashMap<String,Integer>();
        for (String string:dict)
            ladder.put(string, Integer.MAX_VALUE);
        ladder.put(start, 0);

        dict.add(end);
        //BFS: Dijisktra search
        while (!queue.isEmpty()) {

            String word = queue.poll();

            int step = ladder.get(word)+1;//'step' indicates how many steps are needed to travel to one word.

            if (step>min) break;

            for (int i = 0; i < word.length(); i++){
               StringBuilder builder = new StringBuilder(word);
                for (char ch='a';  ch <= 'z'; ch++){
                    builder.setCharAt(i,ch);
                    String new_word=builder.toString();
                    if (ladder.containsKey(new_word)) {

                        if (step>ladder.get(new_word))//Check if it is the shortest path to one word.
                            continue;
                        else if (step<ladder.get(new_word)){
                            queue.add(new_word);
                            ladder.put(new_word, step);
                        }else;// It is a KEY line. If one word already appeared in one ladder,
                              // Do not insert the same word inside the queue twice. Otherwise it gets TLE.

                        if (map.containsKey(new_word)) //Build adjacent Graph
                            map.get(new_word).add(word);
                        else{
                            List<String> list= new LinkedList<String>();
                            list.add(word);
                            map.put(new_word,list);
                            //It is possible to write three lines in one:
                            //map.put(new_word,new LinkedList<String>(Arrays.asList(new String[]{word})));
                            //Which one is better?
                        }

                        if (new_word.equals(end))
                            min=step;

                    }//End if dict contains new_word
                }//End:Iteration from 'a' to 'z'
            }//End:Iteration from the first to the last
        }//End While

        //BackTracking
        LinkedList<String> result = new LinkedList<String>();
        backTrace(end,start,result);

        return results;
    }
    private void backTrace(String word,String start,List<String> list){
        if (word.equals(start)){
            list.add(0,start);
            results.add(new ArrayList<String>(list));
            list.remove(0);
            return;
        }
        list.add(0,word);
        if (map.get(word)!=null)
            for (String s:map.get(word))
                backTrace(s,start,list);
        list.remove(0);
    }
}
Another solution using two sets. This is similar to the answer in the most viewed thread. While I found my solution more readable and efficient.

public class Solution {
    List<List<String>> results;
    List<String> list;
    Map<String,List<String>> map;
        public List<List<String>> findLadders(String start, String end, Set<String> dict) {
            results= new ArrayList<List<String>>();
            if (dict.size() == 0)
                return results;

            int curr=1,next=0;
            boolean found=false;
            list = new LinkedList<String>();
            map = new HashMap<String,List<String>>();

            Queue<String> queue= new ArrayDeque<String>();
            Set<String> unvisited = new HashSet<String>(dict);
            Set<String> visited = new HashSet<String>();

            queue.add(start);
            unvisited.add(end);
            unvisited.remove(start);
            //BFS
            while (!queue.isEmpty()) {

                String word = queue.poll();
                curr--;
                for (int i = 0; i < word.length(); i++){
                   StringBuilder builder = new StringBuilder(word);
                    for (char ch='a';  ch <= 'z'; ch++){
                        builder.setCharAt(i,ch);
                        String new_word=builder.toString();
                        if (unvisited.contains(new_word)){
                            //Handle queue
                            if (visited.add(new_word)){//Key statement,Avoid Duplicate queue insertion
                                next++;
                                queue.add(new_word);
                            }

                            if (map.containsKey(new_word))//Build Adjacent Graph
                                map.get(new_word).add(word);
                            else{
                                List<String> l= new LinkedList<String>();
                                l.add(word);
                                map.put(new_word, l);
                            }

                            if (new_word.equals(end)&&!found) found=true;

                        }

                    }//End:Iteration from 'a' to 'z'
                }//End:Iteration from the first to the last
                if (curr==0){
                    if (found) break;
                    curr=next;
                    next=0;
                    unvisited.removeAll(visited);
                    visited.clear();
                }
            }//End While

            backTrace(end,start);

            return results;
        }
        private void backTrace(String word,String start){
            if (word.equals(start)){
                list.add(0,start);
                results.add(new ArrayList<String>(list));
                list.remove(0);
                return;
            }
            list.add(0,word);
            if (map.get(word)!=null)
                for (String s:map.get(word))
                    backTrace(s,start);
            list.remove(0);
        }
    }
}
*/





//Two-end BFS
public List<List<String>> findLadders(String start, String end, Set<String> dict) {
   // hash set for both ends
   Set<String> set1 = new HashSet<String>();
   Set<String> set2 = new HashSet<String>();

   // initial words in both ends
   set1.add(start);
   set2.add(end);

   // we use a map to help construct the final result
   Map<String, List<String>> map = new HashMap<String, List<String>>();

   // build the map
   helper(dict, set1, set2, map, false);

   List<List<String>> res = new ArrayList<List<String>>();
   List<String> sol = new ArrayList<String>(Arrays.asList(start));

   // recursively build the final result
   generateList(start, end, map, sol, res);

   return res;
 }

 boolean helper(Set<String> dict, Set<String> set1, Set<String> set2, Map<String, List<String>> map, boolean flip) {
   if (set1.isEmpty()) {
     return false;
   }

   if (set1.size() > set2.size()) {
     return helper(dict, set2, set1, map, !flip);
   }

   // remove words on current both ends from the dict
   dict.removeAll(set1);
   dict.removeAll(set2);

   // as we only need the shortest paths
   // we use a boolean value help early termination
   boolean done = false;

   // set for the next level
   Set<String> set = new HashSet<String>();

   // for each string in end 1
   for (String str : set1) {
     for (int i = 0; i < str.length(); i++) {
       char[] chars = str.toCharArray();

       // change one character for every position
       for (char ch = 'a'; ch <= 'z'; ch++) {
         chars[i] = ch;

         String word = new String(chars);

         // make sure we construct the tree in the correct direction
         String key = flip ? word : str;
         String val = flip ? str : word;

         List<String> list = map.containsKey(key) ? map.get(key) : new ArrayList<String>();

         if (set2.contains(word)) {
           done = true;

           list.add(val);
           map.put(key, list);
         }

         if (!done && dict.contains(word)) {
           set.add(word);

           list.add(val);
           map.put(key, list);
         }
       }
     }
   }

   // early terminate if done is true
   return done || helper(dict, set2, set, map, !flip);
 }

 void generateList(String start, String end, Map<String, List<String>> map, List<String> sol, List<List<String>> res) {
   if (start.equals(end)) {
     res.add(new ArrayList<String>(sol));
     return;
   }

   // need this check in case the diff between start and end happens to be one
   // e.g "a", "c", {"a", "b", "c"}
   if (!map.containsKey(start)) {
     return;
   }

   for (String word : map.get(start)) {
     sol.add(word);
     generateList(word, end, map, sol, res);
     sol.remove(sol.size() - 1);
   }
 }







//More OO?
 The difference from the Word Ladder solution is that now we need to remember all possible previous nodes in the path to the node when solution is optimal. And after that construct all possible path variants.

 static private class WordVertex implements Comparable<WordVertex>{

    private String word;
    private int dist;
    private List<WordVertex> prev;
    private HashSet<WordVertex> neighbors;

    private WordVertex(String w) {
        word = w;
        dist = Integer.MAX_VALUE;
        neighbors = new HashSet<WordVertex>();
        prev = new LinkedList<WordVertex>();
    }

    @Override
    public int compareTo(WordVertex o) {
        if (dist < o.dist) {
            return -1;
        } else if (dist > o.dist) {
            return 1;
        }
        return 0;
    }
 }

 public List<List<String>> findLadders(String start, String end, Set<String> dict) {

    // Init vertices
    WordVertex startVertex = new WordVertex(start);
    WordVertex endVertex = new WordVertex(end);
    startVertex.dist = 0;
    List<WordVertex> vertices = new ArrayList<WordVertex>();
    vertices.add(startVertex);
    vertices.add(endVertex);
    for (String word:dict) {
        vertices.add(new WordVertex(word));
    }

    // Construct graph
    for(int i=0; i<vertices.size(); i++) {
        WordVertex vertex = vertices.get(i);
        for(int j=i+1; j<vertices.size(); j++) {
            WordVertex neighbor = vertices.get(j);
            int diff = 0;
            for (int k=0; k<vertex.word.length(); k++) {
                if (vertex.word.charAt(k) != neighbor.word.charAt(k) && diff++ == 1) {
                    break;
                }
            }
            if (diff == 1) {
                vertex.neighbors.add(neighbor);
                neighbor.neighbors.add(vertex);
            }

        }
    }

    // Find shortest path. Dijkstra's algorithm.
    PriorityQueue<WordVertex> queue = new PriorityQueue<WordVertex>();
    for (WordVertex v:vertices) {
        queue.add(v);
    }
    while(!queue.isEmpty()) {
        WordVertex v = queue.poll();
        if (v.dist == Integer.MAX_VALUE) continue;
        for (WordVertex n:v.neighbors) {
            if (v.dist + 1 <= n.dist) {
                n.dist = v.dist + 1;
                n.prev.add(v);   // as one of the previous candidates
                queue.remove(n);
                queue.add(n);
            }
        }
    }

    // Make result
    List<List<String>> seqs = new LinkedList<List<String>>();
    LinkedList<String> seq = new LinkedList<String>();
    constructSequences(endVertex, startVertex, seq, seqs);

    return seqs;
 }

 void constructSequences(WordVertex v, WordVertex start, LinkedList<String> seq, List<List<String>> seqs) {
    seq.addFirst(v.word);
    if (v == start) {
        seqs.add(new LinkedList<String>(seq));
    }
    for(WordVertex p:v.prev) {
        constructSequences(p, start, seq, seqs);
    }
    seq.removeFirst();
 }

