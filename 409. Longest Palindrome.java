public class Solution {
    public int longestPalindrome(String s) {

    	Map<String, String> hm = new HashMap<String, String>();
    	int result = 0;
    	for(int i = 0; i < s.length(); i++){
    		if(hm.containsKey(s.substring(i,i+1))){
    			result+=2;
    			hm.remove(s.substring(i,i+1));
    		}
    		else hm.put(s.substring(i,i+1),String.valueOf(i));
    	}
    	if(!hm.isEmpty()) result++;
        return result;
    }
}

//TODO None