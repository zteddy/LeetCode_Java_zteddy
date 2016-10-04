public class Solution {
	public boolean isStrobogrammatic(String num) {

	    if(num.length() == 0) return false;

	    //Map<String, String> hm = new HashMap<String, String>();

	    String num2 = new String(num);
	    int count = 0;
	    int a;
	    int b;

	    for(int i = 0; i < num.length(); i++){
	    	a = i;
	    	b = num.length()-i-1;
	    	if(num.charAt(a) == '1' && num2.charAt(b) == '1') count++;
	    	if(num.charAt(a) == '8' && num2.charAt(b) == '8') count++;
	    	if(num.charAt(a) == '0' && num2.charAt(b) == '0') count++;
	    	if(num.charAt(a) == '6' && num2.charAt(b) == '9') count++;
	    	if(num.charAt(a) == '9' && num2.charAt(b) == '6') count++;
	    }

	    if(count == num.length()) return true;
	    else return false;

	}
}

/*Concise and genius
public boolean isStrobogrammatic(String num) {
    for (int i=0, j=num.length()-1; i <= j; i++, j--)
        if (!"00 11 88 696".contains(num.charAt(i) + "" + num.charAt(j)))
            return false;
    return true;
}
*/

/*Using HashTable
public boolean isStrobogrammatic(String num) {
    Map<Character, Character> map = new HashMap<Character, Character>();
    map.put('6', '9');
    map.put('9', '6');
    map.put('0', '0');
    map.put('1', '1');
    map.put('8', '8');
   
    int l = 0, r = num.length() - 1;
    while (l <= r) {
        if (!map.containsKey(num.charAt(l))) return false;
        if (map.get(num.charAt(l)) != num.charAt(r))
            return false;
        l++;
        r--;
    }
    
    return true;
}
*/