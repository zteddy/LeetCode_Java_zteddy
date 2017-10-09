class Solution {

    public void backtracking(String s, String[] temp, List<String> result, int arrIndex, int stringIndex){
        if(arrIndex == 4){
            if(stringIndex == s.length()){
                result.add(String.join(".",temp));
            }
            return ;
        }

        for(int i = stringIndex+1; i <= stringIndex+4; i++){
            if(i <= s.length()){
                String sub = s.substring(stringIndex,i);
                if(sub.length() > 1 && sub.charAt(0) == '0') continue;
                if(Integer.parseInt(sub) <= 255){
                    temp[arrIndex] = sub;
                    backtracking(s,temp,result,arrIndex+1,i);
                }
            }
        }
    }

    public List<String> restoreIpAddresses(String s) {

        List<String> result = new ArrayList<>();

        backtracking(s,new String[4],result,0,0);

        return result;
    }
}





/*Using Iterative
public class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<String>();
        int len = s.length();
        for(int i = 1; i<4 && i<len-2; i++){
            for(int j = i+1; j<i+4 && j<len-1; j++){
                for(int k = j+1; k<j+4 && k<len; k++){
                    String s1 = s.substring(0,i), s2 = s.substring(i,j), s3 = s.substring(j,k), s4 = s.substring(k,len);
                    if(isValid(s1) && isValid(s2) && isValid(s3) && isValid(s4)){
                        res.add(s1+"."+s2+"."+s3+"."+s4);
                    }
                }
            }
        }
        return res;
    }
    public boolean isValid(String s){
        if(s.length()>3 || s.length()==0 || (s.charAt(0)=='0' && s.length()>1) || Integer.parseInt(s)>255)
            return false;
        return true;
    }
}
3-loop divides the string s into 4 substring: s1, s2, s3, s4. Check if each substring is valid.
In isValid, strings whose length greater than 3 or equals to 0 is not valid; or if the string's length is longer than 1 and the first letter is '0' then it's invalid; or the string whose integer representation greater than 255 is invalid.
*/



/*More concise
public List<String> restoreIpAddresses(String s) {
    List<String> res = new ArrayList<>();
    helper(s,"",res,0);
    return res;
}
public void helper(String s, String tmp, List<String> res,int n){
    if(n==4){
        if(s.length()==0) res.add(tmp.substring(0,tmp.length()-1));
        //substring here to get rid of last '.'
        return;
    }
    for(int k=1;k<=3;k++){
        if(s.length()<k) continue;
        int val = Integer.parseInt(s.substring(0,k));
        if(val>255 || k!=String.valueOf(val).length()) continue;
        //in the case 010 the parseInt will return len=2 where val=10, but k=3, skip this.
        helper(s.substring(k),tmp+s.substring(0,k)+".",res,n+1);
    }
}
*/
