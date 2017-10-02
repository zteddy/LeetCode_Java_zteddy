class Solution {

    public boolean test(String pattern, String str, Map<String,Integer> use){
        Map<String,String> hm = new HashMap<>();
        int indexNow = 0;

        for(int i = 0; i < pattern.length(); i++){
            String key = pattern.substring(i,i+1);
            int keyLen = use.get(key);
            String value = str.substring(indexNow,indexNow+keyLen);
            indexNow = indexNow+keyLen;
            if(hm.containsKey(key)){
                if(!hm.get(key).equals(value)) return false;
            }
            else{
                if(hm.containsValue(value)) return false;
                hm.put(key,value);
            }
        }
        return true;
    }

    public boolean backtracking(String pattern, String str, Map<String,Integer> count, Map<String,Integer> use, int used, List<String> sequence, int index){
        if(used == str.length() && index == sequence.size()){
            return test(pattern, str, use);
        }
        if(used == str.length() || index == sequence.size()){
            return false;
        }
        boolean result = false;
        String change = sequence.get(index);
        int thisCount = count.get(change);
        int originalUse = use.get(change);
        for(int i = 1; i <= (str.length()-used)/thisCount; i++){
            use.put(change,i);
            result |= backtracking(pattern,str,count,use,used+thisCount*i,sequence,index+1);
            use.put(change,originalUse);
        }

        return result;
    }


    public boolean wordPatternMatch(String pattern, String str) {
        // if(str.length()%pattern.length() != 0) return false;
        Map<String,Integer> count = new HashMap<>();
        for(int i = 0; i < pattern.length(); i++){
            String key = pattern.substring(i,i+1);
            int temp = count.containsKey(key)?count.get(key):0;
            count.put(key,temp+1);
        }

        Map<String,Integer> use = new HashMap<>();
        List<String> sequence = new ArrayList<>();
        for(String s:count.keySet()){
            use.put(s,1);
            sequence.add(s);
        }

        // int x = str.length()/pattern.length();

        return backtracking(pattern,str,count,use,0,sequence,0);
    }
}



/*Seems to be a simpler idea
We can solve this problem using backtracking, we just have to keep trying to use a character in the pattern to match different length of substrings in the input string, keep trying till we go through the input string and the pattern.

For example, input string is "redblueredblue", and the pattern is "abab", first let's use 'a' to match "r", 'b' to match "e", then we find that 'a' does not match "d", so we do backtracking, use 'b' to match "ed", so on and so forth ...

When we do the recursion, if the pattern character exists in the hash map already, we just have to see if we can use it to match the same length of the string. For example, let's say we have the following map:

'a': "red"

'b': "blue"

now when we see 'a' again, we know that it should match "red", the length is 3, then let's see if str[i ... i+3] matches 'a', where i is the current index of the input string. Thanks to StefanPochmann's suggestion, in Java we can elegantly use str.startsWith(s, i) to do the check.

Also thanks to i-tikhonov's suggestion, we can use a hash set to avoid duplicate matches, if character a matches string "red", then character b cannot be used to match "red". In my opinion though, we can say apple (pattern 'a') is "fruit", orange (pattern 'o') is "fruit", so they can match the same string, anyhow, I guess it really depends on how the problem states.

The following code should pass OJ now, if we don't need to worry about the duplicate matches, just remove the code that associates with the hash set.

public class Solution {

  public boolean wordPatternMatch(String pattern, String str) {
    Map<Character, String> map = new HashMap<>();
    Set<String> set = new HashSet<>();

    return isMatch(str, 0, pattern, 0, map, set);
  }

  boolean isMatch(String str, int i, String pat, int j, Map<Character, String> map, Set<String> set) {
    // base case
    if (i == str.length() && j == pat.length()) return true;
    if (i == str.length() || j == pat.length()) return false;

    // get current pattern character
    char c = pat.charAt(j);

    // if the pattern character exists
    if (map.containsKey(c)) {
      String s = map.get(c);

      // then check if we can use it to match str[i...i+s.length()]
      if (!str.startsWith(s, i)) {
        return false;
      }

      // if it can match, great, continue to match the rest
      return isMatch(str, i + s.length(), pat, j + 1, map, set);
    }

    // pattern character does not exist in the map
    for (int k = i; k < str.length(); k++) {
      String p = str.substring(i, k + 1);

      if (set.contains(p)) {
        continue;
      }

      // create or update it
      map.put(c, p);
      set.add(p);

      // continue to match the rest
      if (isMatch(str, k + 1, pat, j + 1, map, set)) {
        return true;
      }

      // backtracking
      map.remove(c);
      set.remove(p);
    }

    // we've tried our best but still no luck
    return false;
  }

}
*/



/*Much more concise
public class Solution {
    Map<Character,String> map =new HashMap();
    Set<String> set =new HashSet();
    public boolean wordPatternMatch(String pattern, String str) {
        if(pattern.isEmpty()) return str.isEmpty();
        if(map.containsKey(pattern.charAt(0))){
            String value= map.get(pattern.charAt(0));
            if(str.length()<value.length() || !str.substring(0,value.length()).equals(value)) return false;
            if(wordPatternMatch(pattern.substring(1),str.substring(value.length()))) return true;
        }else{
            for(int i=1;i<=str.length();i++){
                if(set.contains(str.substring(0,i))) continue;
                map.put(pattern.charAt(0),str.substring(0,i));
                set.add(str.substring(0,i));
                if(wordPatternMatch(pattern.substring(1),str.substring(i))) return true;
                set.remove(str.substring(0,i));
                map.remove(pattern.charAt(0));
            }
        }
        return false;
    }
}
*/




/*Some optimization
The idea might not be so different, but I tried to optimize the solution as much as I could. In concrete:

Instead of using HashMap, I use a String array to store the character --> String mapping
Instead of trying all combinations, I only try necessary/possible ones.
I'd like to explain the second point a little bit more: Suppose we are successful in mapping the first i characters in pattern, and we are now at the j location of str. If i+1's character in pattern is not already mapped, then we would want to try all possible substrings in str, that is, the substring could be of length 1 (j's character), or length 2 (j and j+1), etc. Normally we would try up to the end of str, but this is really not necessary, because we have to spare enough space future characters in pattern. If this is not clear enough, let's take the following as an example:

pattern = "abca"
str = "xxxyyzzxxx"
Suppose we have successfully matched a to xxx and b to yy, and now we are at the third character of pattern, i.e., character c. We have not mapped c to anything, so we could try any of the following:

z
zz
zzx
zzxx
zzxxx
But do we really need to try them all? The answer is NO. Because we have already mapped a and b, and under this constraint, we have to save enough space for the fourth character of pattern, i.e., a. In other words, we can at most try z and zz, otherwise we are doomed to return false when we reach the fourth character a. This is what endPoint is about in the code.

Code in Java:

public boolean wordPatternMatch(String pattern, String str) {
    String[] map = new String[26]; // mapping of characters 'a' - 'z'
    HashSet<String> set = new HashSet<>(); // mapped result of 'a' - 'z'
    return wordPatternMatch(pattern, str, map, set, 0, str.length()-1, 0, pattern.length()-1);
}
private boolean wordPatternMatch(String pattern, String str, String[] map, HashSet<String> set, int start, int end, int startP, int endP) {
    if(startP==endP+1 && start==end+1) return true; // both pattern and str are exhausted
    if((startP>endP && start<=end) || (startP<endP && start>end)) return false; // either of pattern or str is exhausted

    char ch = pattern.charAt(startP);
    String matched = map[ch-'a'];
    if(matched!=null) { // ch is already mapped, then continue
        int count = matched.length();
        return start+count<=end+1 && matched.equals(str.substring(start, start+count)) // substring equals previously mapped string
                && wordPatternMatch(pattern, str, map, set, start+matched.length(), end, startP+1, endP); // moving forward
    }
    else {
        int endPoint = end;
        for(int i=endP; i>startP; i--) {
            endPoint -= map[pattern.charAt(i)-'a']==null ? 1 : map[pattern.charAt(i)-'a'].length();
        }
        for(int i=start; i<=endPoint; i++) { // try every possible mapping, from 1 character to the end
            matched = str.substring(start, i+1);
            if(set.contains(matched)) continue; // different pattern cannot map to same substring

            map[ch-'a'] = matched; // move forward, add corresponding mapping and set content
            set.add(matched);

            if(wordPatternMatch(pattern, str, map, set, i+1, end, startP+1, endP)) return true;

            else { // backtracking, remove corresponding mapping and set content
                map[ch-'a'] = null;
                set.remove(matched);
            }
        }
    }
    return false; // exhausted
}
*/



/*A simple one
public class Solution {
    public boolean wordPatternMatch(String pattern, String str) {
        Map<Character, String> map = new HashMap<>();

        return helper(pattern, 0, str, 0, map);
    }

    public boolean helper(String pattern, int pPos, String str, int sPos, Map<Character, String> map) {
        if(sPos == str.length() && pPos == pattern.length()) return true;

        if(sPos == str.length() || pPos == pattern.length()) return false;

        char c = pattern.charAt(pPos);

        for(int i = sPos; i < str.length(); i++) {
            String substr = str.substring(sPos, i+1);

            if(map.containsKey(c) && map.get(c).equals(substr) ) {
                if(helper(pattern, pPos+1, str, i+1, map)) return true;
            }

            if(!map.containsKey(c) && !map.containsValue(substr) ) {
                map.put(c, substr);
                if(helper(pattern, pPos+1, str, i+1, map)) return true;
                map.remove(c);
            }
        }
        return false;
    }
}
*/



/*Another simple and concise
public boolean wordPatternMatch(String pattern, String str) {
        if(pattern.length()==0 && str.length()==0) return true;
        if(pattern.length()==0) return false;
        HashMap map = new HashMap();
        return helper(pattern, str, 0, 0, map);
    }

public boolean helper(String pattern, String str, Integer i, int j, HashMap map) {
    if(i==pattern.length() && j==str.length()) return true;
    if(i>=pattern.length() || j>=str.length()) return false;
    for(int k = j+1; k <= str.length(); ++k) {
        String temp = str.substring(j, k);
        char c = pattern.charAt(i);
        Object a = map.put(c, i);
        Object b = map.put(temp, i);
        if(a == b) {
            if(helper(pattern, str, i+1, k, map)) return true;
        }
        map.put(c, a);
        map.put(temp, b);
    }
    return false;
}
*/
