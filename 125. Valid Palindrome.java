public class Solution {
	public boolean isAlphabet(char c){
		if((c >= 'a' && c <= 'z')||(c >= 'A' && c <= 'Z'))
			return true;
		return false;
	}
	public boolean isNumeric(char c){
		if(c >= '0' && c <= '9')
			return true;
		return false;	
	}
	
    public boolean isPalindrome(String s) {
        int front = 0;
		int rear = s.length()-1;
		
		
		while(front < rear){
			
			while(!isAlphabet(s.charAt(front)) && !isNumeric(s.charAt(front))){
				front++;
				if(front == s.length()-1) break;
			}
			while(!isAlphabet(s.charAt(rear)) && !isNumeric(s.charAt(rear))){
				rear--;
				if(rear == 0) break;
			}
			
			if(!isAlphabet(s.charAt(front)) && !isNumeric(s.charAt(front)) && !isAlphabet(s.charAt(rear)) && !isNumeric(s.charAt(rear)))
				return true;
			if(s.charAt(front) != s.charAt(rear) && (Math.abs(s.charAt(front)-s.charAt(rear)) != Math.abs('a' - 'A')))
				return false;
			if(Math.abs(s.charAt(front)-s.charAt(rear)) == Math.abs('a' - 'A') && (isNumeric(s.charAt(front)) || isNumeric(s.charAt(rear))))
				return false;
			
			
			front++;
			rear--;
		}
		
		return true;
    }
}

/*Using Character.isletterOrDigit()
public class Solution {
    public boolean isPalindrome(String s) {
        if (s.isEmpty()) {
        	return true;
        }
        int head = 0, tail = s.length() - 1;
        char cHead, cTail;
        while(head <= tail) {
        	cHead = s.charAt(head);
        	cTail = s.charAt(tail);
        	if (!Character.isLetterOrDigit(cHead)) {
        		head++;
        	} else if(!Character.isLetterOrDigit(cTail)) {
        		tail--;
        	} else {
        		if (Character.toLowerCase(cHead) != Character.toLowerCase(cTail)) {
        			return false;
        		}
        		head++;
        		tail--;
        	}
        }
        
        return true;
    }
}
*/

/*Using String.replaceAll() String.toLowerCase()
public class Solution {
    public boolean isPalindrome(String s) {
        s = s.toLowerCase(); // convert all to lower cases.
        s = s.replaceAll("[^a-z^0-9]+", ""); // remove all non-digital and non-letter.
        int len = s.length();
        for (int i = 0; i < len; i++){
            if (s.charAt(i) != s.charAt(len - i - 1)){
                return false;
            }
        }
        return true;
   }
}
*/

/*Using StringBuffer.reverse()
public class Solution {
    public boolean isPalindrome(String s) {
        String actual = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        String rev = new StringBuffer(actual).reverse().toString();
        return actual.equals(rev);
    }
}
*/