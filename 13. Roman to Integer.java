public class Solution {
	
	public int romanCharToInt(char a){
		switch(a){
			case 'I': 
				return 1;
			case 'X': 
				return 10;
			case 'C': 
				return 100;
			case 'M': 
				return 1000;
			case 'V': 
				return 5;
			case 'L': 
				return 50;
			case 'D': 
				return 500;	
			default:
				return 0;
		}	
	}
	
    public int romanToInt(String s) {
        
		char[] ca_s = s.toCharArray();
		int result = 0;
		int previous;
		
		if(ca_s.length == 0) return 0;
		
		previous = romanCharToInt(ca_s[ca_s.length-1]);
		result = previous;
		for(int i = ca_s.length-2; i >= 0; i--){
			
			if((ca_s[i] == 'I' || ca_s[i] == 'X' || ca_s[i] == 'C') 
			&& romanCharToInt(ca_s[i]) < previous) result -= romanCharToInt(ca_s[i]);
			else result += romanCharToInt(ca_s[i]);
			
			previous = romanCharToInt(ca_s[i]);
					
		}
						
		return result;
    }
}

//TODO None