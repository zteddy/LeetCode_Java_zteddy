public class Solution {
	
	public boolean isVowels(char c){
		if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'){
			return true;
		}else if(c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U'){
			return true;	
		}
		return false;
	}
	
    public String reverseVowels(String s) {
		
		char[] ca_s = s.toCharArray();
		int front = 0;
		int rear = ca_s.length - 1;
		char temp;
		
		if(rear == 0) return s;
		
		while(front < rear){
			while(!isVowels(ca_s[front]) && front < ca_s.length-1 ) front++;
			while(!isVowels(ca_s[rear]) && rear> 0) rear--;
			//一定要想好在各种情况下这个边界怎么取
			
			if(front >=  rear) break;
			else{
				temp = ca_s[front];
				ca_s[front] = ca_s[rear];
				ca_s[rear] = temp;
				front++;
				rear--;
			}
		
		}
		
		String result = new String(ca_s);
		 
		return result;
		 
    }
}

/*No need for isVowels function using String.contains()
public class Solution {
public String reverseVowels(String s) {
    if(s == null || s.length()==0) return s;
    String vowels = "aeiouAEIOU";
    char[] chars = s.toCharArray();
    int start = 0;
    int end = s.length()-1;
    while(start<end){
        
        while(start<end && !vowels.contains(chars[start]+"")){
            start++;
        }
        
        while(start<end && !vowels.contains(chars[end]+"")){
            end--;
        }
        
        char temp = chars[start];
        chars[start] = chars[end];
        chars[end] = temp;
        
        start++;
        end--;
    }
    return new String(chars);
}
*/