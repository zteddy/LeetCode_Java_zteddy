public class Solution {
    public String addBinary(String a, String b) {
     
        int rlength = Math.max(a.length(), b.length())+1;
        int[] result = new int[rlength];
        int temp1 = 0;
        int temp2 = 0;
        int add = 0;

        
        for(int i = 0; i < rlength; i++){
        	if(a.length()-1-i >= 0)
        		temp1 = a.charAt(a.length()-1-i) - '0';
        	else
        		temp1 = 0;

        	if(b.length()-1-i >= 0)
        		temp2 = b.charAt(b.length()-1-i) - '0';
        	else
        		temp2 = 0;

        	result[rlength-1-i]  = temp1+temp2;
        }

        for(int i = 0; i < rlength; i++){

        	result[rlength-1-i] += add;

        	if(result[rlength-1-i] >= 2){
        		add = 1;
        		result[rlength-1-i] -= 2;
        	}
        	else add = 0;
        }

        char[] cresult;

        if(result[0] == 0){
        	cresult = new char[rlength-1];
        	for(int i = 0; i < rlength-1; i++){
        		cresult[i] = (char)(result[i+1] + '0');
        	}
        }
        else{
        	cresult = new char[rlength];
        	for(int i = 0; i < rlength; i++){
        		cresult[i] = (char)(result[i] + '0');
        	}
        }
        String sresult = new String(cresult);
  
        return sresult;
    }
}


/* More concise solution
public class Solution {
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() -1, carry = 0;
        while (i >= 0 || j >= 0) {
            int sum = carry;
            if (j >= 0) sum += b.charAt(j--) - '0';
            if (i >= 0) sum += a.charAt(i--) - '0';
            sb.append(sum % 2);
            carry = sum / 2;
        }
        if (carry != 0) sb.append(carry);
        return sb.reverse().toString();
    }
}
*/
