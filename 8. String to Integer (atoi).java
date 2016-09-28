public class Solution {
    public int myAtoi(String str) {
        
		if(str == "") return 0;
			
		String temp = str.trim();
		double result = 0;
		StringBuffer dstr = new StringBuffer();
		
		
		
		for(int i = 0; i < temp.length(); i++){
			if(temp.charAt(i) == '1' || temp.charAt(i) == '2' || temp.charAt(i) == '3' 
			|| temp.charAt(i) == '4' || temp.charAt(i) == '5' || temp.charAt(i) == '6' 
			|| temp.charAt(i) == '7' || temp.charAt(i) == '8' || temp.charAt(i) == '9' 
			|| temp.charAt(i) == '0' || temp.charAt(i) == '+' || temp.charAt(i) == '-')
				dstr.append(temp.charAt(i));
			else break;
		}
		
		StringBuffer dstr2 = new StringBuffer();
		for(int i = 0; i < dstr.toString().length(); i++){
			if(dstr.toString().charAt(i) == '1' || dstr.toString().charAt(i) == '2' || dstr.toString().charAt(i) == '3' 
			|| dstr.toString().charAt(i) == '4' || dstr.toString().charAt(i) == '5' || dstr.toString().charAt(i) == '6' 
			|| dstr.toString().charAt(i) == '7' || dstr.toString().charAt(i) == '8' || dstr.toString().charAt(i) == '9' 
			|| dstr.toString().charAt(i) == '0')
				dstr2.append(dstr.toString().charAt(i));
		}
		
		for(int i = dstr2.length()-1; i >= 0; i--){
			result += (dstr2.toString().charAt(i) - '0') * Math.pow(10,(dstr2.length()-1-i));
		}
		
		if(dstr.length() == dstr2.length()){
			if(result > 2147483647) return 2147483647;
			else if(result < -2147483647) return -2147483648;
			else return (int)result;
		}
		else if(dstr.length() == dstr2.length()+1){
			if(dstr.charAt(0) == '-'){
				if(-result < -2147483647) return -2147483648;
				else return (int)-result;
			}
			else if(dstr.charAt(0) == '+'){
				if(result > 2147483647) return 2147483647;
				else return (int)result;
			}
		}
	    
	    return 0;
    }
}

//Design first Coding second!!!!!!

/*More clear
public int myAtoi(String str) {
    int index = 0, sign = 1, total = 0;
    //1. Empty string
    if(str.length() == 0) return 0;

    //2. Remove Spaces
    while(str.charAt(index) == ' ' && index < str.length())
        index ++;

    //3. Handle signs
    if(str.charAt(index) == '+' || str.charAt(index) == '-'){
        sign = str.charAt(index) == '+' ? 1 : -1;
        index ++;
    }
    
    //4. Convert number and avoid overflow
    while(index < str.length()){
        int digit = str.charAt(index) - '0';
        if(digit < 0 || digit > 9) break;

        //check if total will be overflow after 10 times and add digit
        if(Integer.MAX_VALUE/10 < total || Integer.MAX_VALUE/10 == total && Integer.MAX_VALUE %10 < digit)
            return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;

        total = 10 * total + digit;
        index ++;
    }
    return total * sign;
}
*/

/*More clear2
public class Solution {
	public int atoi(String str) {
		boolean neg = false; // flag to mark if the converted integer positive or negative. 
		StringBuilder buf = new StringBuilder(); // temp buffer to store the converted string
		
		// check if the string is null or empty
		if (str == null || str.isEmpty()) return 0;
		
		// trim the leading whitespaces
		str = str.trim();
		
		// if string contains only whitespace characters
		if (str.isEmpty()) return 0;
		
		// get length of the trimed string
		int length = str.length();
		
		// Check if the first character of the string
		if (isNeg(str.charAt(0))) neg = true;
		else if (isPos(str.charAt(0))) neg = false;
		else if (Character.isDigit(str.charAt(0))) buf.append(str.charAt(0));
		else return 0;
		
		// check the first sequence of digit characters in the string
		int start = 1;
		while (start < length && Character.isDigit(str.charAt(start))) {
			buf.append(str.charAt(start));
			start++;
		}
		
		// check if the buf is empty
		if (buf.length() == 0) return 0;
		
		// check if the converted integer is overflowed
		long result;
		if (buf.length() <= 10) {
			result = toInteger(buf, neg);
		} else if (neg) {
			return Integer.MIN_VALUE;
		} else
			return Integer.MAX_VALUE;
			
		// Post-processing the convert long to int
		if (neg && result <= Integer.MAX_VALUE) {
			return 0 - (int) result;
		} else if (!neg && result <= Integer.MAX_VALUE) {
			return (int) result;
		} else if (neg && result > Integer.MAX_VALUE) {
			return Integer.MIN_VALUE;
		} else return Integer.MAX_VALUE;
	}

	private boolean isPos(char ch) {
		return ch == '+';
	}

	private boolean isNeg(char ch) {
		return ch == '-';
	}

	private long toInteger(StringBuilder buf, boolean neg) {
		int len = buf.length();
		long result = 0;
		for (int i = 0; i < len; i++) {
			result += Character.getNumericValue(buf.charAt(i)) * Math.pow(10, len - i - 1);
		}
		
		return result;
	}
}
*/

/*More clear3
public static int myAtoi(String str) {
    if (str.isEmpty()) return 0;
    int sign = 1, base = 0, i = 0;
    while (str.charAt(i) == ' ')
        i++;
    if (str.charAt(i) == '-' || str.charAt(i) == '+')
        sign = str.charAt(i++) == '-' ? -1 : 1;
    while (i < str.length() && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
        if (base > Integer.MAX_VALUE / 10 || (base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7)) {
            return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        base = 10 * base + (str.charAt(i++) - '0');
    }
    return base * sign;
}
*/