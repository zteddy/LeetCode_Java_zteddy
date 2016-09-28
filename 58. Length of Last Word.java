public class Solution {
    public int lengthOfLastWord(String s){
		int slength = s.length();
		if(slength == 0) return 0;
		
		int j = slength - 1;
		while(j >= 0 && s.charAt(j) == ' ') j--;
		//while(s.charAt(j) == ' ' && j >= 0) j--; will out of bound
		
		if(j < 0) return 0;
		
		int count = 0;
		while(j >= 0 && s.charAt(j) != ' '){
			j--;
			count++;
		} 
		
		return count;
        
    }
}

/* Using split()
public class Solution {
    public int lengthOfLastWord(String s) {
        String[] parts = s.split(" ");
        if (parts.length == 0) return 0;
        return parts[parts.length - 1].length();
    }
}
*/

/* Using trim()
public int lengthOfLastWord(String s) {
    return s.trim().length()-s.trim().lastIndexOf(" ")-1;
}
*/