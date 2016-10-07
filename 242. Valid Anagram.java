public class Solution {
    public boolean isAnagram(String s, String t) {

    	if(s.length() != t.length()) return false;

    	int[] count = new int[26];

    	for(int i = 0; i < s.length(); i++){
    		count[s.charAt(i)-'a']++;
    	}

    	for(int i = 0; i < t.length(); i++){
    		count[t.charAt(i)-'a']--;
    	}

    	for(int i = 0; i < count.length; i++){
    		if(count[i] != 0) return false;
    	}

    	return true;
    }
}

/*Using sorting
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
        return false;
    }
    char[] str1 = s.toCharArray();
    char[] str2 = t.toCharArray();
    Arrays.sort(str1);
    Arrays.sort(str2);
    return Arrays.equals(str1, str2);
}
*/

/*More concise and better in detail
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
        return false;
    }
    int[] counter = new int[26];
    for (int i = 0; i < s.length(); i++) {
        counter[s.charAt(i) - 'a']++;
        counter[t.charAt(i) - 'a']--;
    }
    for (int count : counter) {
        if (count != 0) {
            return false;
        }
    }
    return true;
}
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
        return false;
    }
    int[] table = new int[26];
    for (int i = 0; i < s.length(); i++) {
        table[s.charAt(i) - 'a']++;
    }
    for (int i = 0; i < t.length(); i++) {
        table[t.charAt(i) - 'a']--;
        if (table[t.charAt(i) - 'a'] < 0) {
            return false;
        }
    }
    return true;
}
*/