public class Solution {
    public boolean isIsomorphic(String s, String t) {

    	Map<Character, Character> hm = new HashMap<>();

    	if(s.length() != t.length()) return false;

    	for(int i = 0; i < s.length(); i++){
    		if(hm.get(s.charAt(i)) == null){        //自动装箱 //null是小写！
    			if(hm.containsValue(t.charAt(i))) return false;
    			else hm.put(s.charAt(i), t.charAt(i));        
    		}
    		else if(hm.get(s.charAt(i)) != t.charAt(i)) return false;
    	}

    	return true;        
    }
}

/*Genius solution
bool isIsomorphic(string s, string t) {
    int m1[256]={0}, m2[256]={0};
    int count = 1;
    for(int i=0; i<s.length(); i++) {
        if(m1[s[i]]!=m2[t[i]]) return false;
        if(m1[s[i]]==0) {
            m1[s[i]]=count;
            m2[t[i]]=count;
            count++;
        }
    }
    return true;
}
*/