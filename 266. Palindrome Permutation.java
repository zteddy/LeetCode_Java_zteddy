public class Solution {
    public boolean canPermutePalindrome(String s) {

    	Map<Character, Character> hm = new HashMap<>();

    	//if(s.length() == 0) return false;

    	for(int i = 0; i < s.length(); i++){
    		if(hm.containsKey(s.charAt(i))) hm.remove(s.charAt(i));
    		else hm.put(s.charAt(i), s.charAt(i));
    	}

    	if(hm.size() > 1) return false;

    	return true;
        
    }
}

//TODO None