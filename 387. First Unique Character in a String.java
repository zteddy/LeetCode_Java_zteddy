public class Solution {
    public int firstUniqChar(String s) {
    	Map<Character, Integer> hm = new HashMap<>();
    	for(int i = 0; i < s.length(); i++){
    		char temp = s.charAt(i);
    		if(!hm.containsKey(temp)) hm.put(temp, 1);
    		else hm.put(temp, 2);
    	}
    	for(int i = 0; i < s.length(); i++){
    		char temp = s.charAt(i);
    		if(hm.get(temp) == 1) return i;
    	}
    	return -1;
    }
}

/*Using array
 public int firstUniqChar(String s) {
    int[] alp =new int[26];
    char[] arr =s.toCharArray();
    for(char c : arr ){
        alp[c-'a']++;
    }
    for(int i=0;i<arr.length;i++){
        if (alp[arr[i]-'a']==1) return i;
    }
    return -1;
}
*/
