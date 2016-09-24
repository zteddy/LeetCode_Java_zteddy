public class Solution {
    public String reverseString(String s) {
		
        int slength = s.length();
		char[] reverse = new char[slength];
		
		for(int i = 0; i < slength; i++){
			reverse[i] = s.charAt(slength-i-1);
		}
		
		String result = new String(reverse);
		
		return result;
		
		
    }
}

//TODO None