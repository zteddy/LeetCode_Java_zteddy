public class Solution {
    public String getHint(String secret, String guess) {
    	
    	Map<Character, Character> hm = new HashMap<>();
    	int[] count = new int[11];
        
        int a = 0;
        int b = 0;

        for(Integer i = 0; i < secret.length(); i++){
        	if(secret.charAt(i) == guess.charAt(i)) a++;
        	else count[secret.charAt(i)-'0']++;
        }

        for(Integer i = 0; i < secret.length(); i++){
        	if(secret.charAt(i) != guess.charAt(i)){
	        	if(count[guess.charAt(i)-'0'] != 0){
	        		b++;
	        		count[guess.charAt(i)-'0']--;
	        	}        		
        	}
        }
        return (a+"")+"A"+(b+"")+"B";
    }
}

/*More concise solution
public String getHint(String secret, String guess) {
    int a=0,b=0;
    int[] digits=new int[10];
    for(int i=0;i<secret.length();i++){
        if(secret.charAt(i)==guess.charAt(i)) a++;
        else{
            if(++digits[secret.charAt(i)-'0']<=0) b++;
            if(--digits[guess.charAt(i)-'0']>=0) b++;
        }
    }
    return a+"A"+b+"B";
}
*/