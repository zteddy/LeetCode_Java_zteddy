public class Solution {
    public char findTheDifference(String s, String t) {

    	int[] counts = new int[26];
    	int[] countt = new int[26];

    	for(int i = 0; i < s.length(); i++){
    		counts[s.charAt(i) - 'a']++;
    	}

    	for(int i = 0; i < t.length(); i++){
    		countt[t.charAt(i) - 'a']++;
    	}

    	for(int i = 0; i < 26; i++){
    		if(countt[i] != counts[i]) return (char)(i+'a');
    	}

    	return (char)(i+'a');
    }
}

/*Using bit manipulation
public char findTheDifference(String s, String t) {
	int n = t.length();
	char c = t.charAt(n - 1);
	for (int i = 0; i < n - 1; ++i) {
		c ^= s.charAt(i);
		c ^= t.charAt(i);
	}
	return c;
}
*/