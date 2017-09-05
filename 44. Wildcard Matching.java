class Solution {
    public boolean isMatch(String s, String p) {

        if(s.length() == 0 && p.length() == 0) return true;
        if(s.length() == 0 && p.equals("*")) return true;
        if(s.length() == 0 || p.length() == 0) return false;


        char[] ss = s.toCharArray();
        char[] pp = p.toCharArray();

        int[][] dp = new int[p.length()][s.length()];

        // boolean flag = false;


        for(int i = 0; i < p.length(); i++){
            // flag = true;
            for(int j = 0; j < s.length(); j++){
                if(pp[i] == '*'){
                    if(i == 0) dp[i][j] = 2;
                    else{
                        // if(j == 0 && (pp[i-1] == '*' || i == p.length()-1)) dp[i][j] = true;
                        // if(j > 0 && (dp[i-1][j-1] || dp[i][j-1])) dp[i][j] = true;
                        // if((dp[i-1][j] && (pp[i-1] == '*' || i == p.length()-1)) || (j > 0 && (dp[i][j-1] || dp[i-1][j-1]))) dp[i][j] = true;
                        // if((dp[i-1][j] && (pp[i-1] == '*')) || (j > 0 && (dp[i][j-1] || dp[i-1][j-1]))) dp[i][j] = true;
                        if(j == 0 || dp[i][j-1] == 0) dp[i][j] = dp[i-1][j];
                        else dp[i][j] = 2;

                    }
                }
                else{
                    // if(!flag) continue;
                    if(i == 0){
                        if((pp[i] == ss[j] || pp[i] == '?') && j == 0){
                            dp[i][j] = 1;
                            // flag = false;
                        }
                    }
                    else{
                        // if(dp[i-1][j]) continue;
                        // if(j == 0 && (pp[i] == ss[j] || pp[i] == '?')){
                        //     dp[i][j] = true;
                        //     flag = false;
                        // }
                        // if(j > 0 && (pp[i] == ss[j] || pp[i] == '?') && dp[i-1][j-1]){
                        //     dp[i][j] = true;
                        //     flag = false;
                        // }
                        // if((pp[i] == ss[j] || pp[i] == '?') && (j > 0 && dp[i-1][j-1] || dp[i-1][j] && pp[i-1] == '*')){
                        //     dp[i][j] = true;
                        //     flag = false;
                        // }
                        if(pp[i] == ss[j] || pp[i] == '?'){
                            if(j > 0 && dp[i-1][j-1] != 0 || dp[i-1][j] == 2){
                                dp[i][j] = 1;
                            }
                        }
                    }
                }
            }
        }

        // if(s.length() < p.length())
        //     return dp[s.length()-1][s.length()-1];
        // else
        //     return dp[p.length()-1][s.length()-1];


        // return dp[p.length()-1][s.length()-1] || (p.length() > 1 && pp[p.length()-1] == '*' && dp[p.length()-2][s.length()-1]);

        // return dp[p.length()-1][s.length()-1];
        return dp[p.length()-1][s.length()-1] != 0;


    }
}


//还是一开始的逻辑没有想清楚，用了这么多条件，做了这么多修改说明这道题已经失败了




/*Using two pointers
e.g.

abed
?b*d**

a=?, go on, b=b, go on,
e=*, save * position star=3, save s position ss = 3, p++
e!=d,  check if there was a *, yes, ss++, s=ss; p=star+1
d=d, go on, meet the end.
check the rest element in p, if all are *, true, else false;

Note that in char array, the last is NOT NULL, to check the end, use  "*p"  or "*p=='\0'".


Here is my re-write in Java

boolean comparison(String str, String pattern) {
        int s = 0, p = 0, match = 0, starIdx = -1;
        while (s < str.length()){
            // advancing both pointers
            if (p < pattern.length()  && (pattern.charAt(p) == '?' || str.charAt(s) == pattern.charAt(p))){
                s++;
                p++;
            }
            // * found, only advancing pattern pointer
            else if (p < pattern.length() && pattern.charAt(p) == '*'){
                starIdx = p;
                match = s;
                p++;
            }
           // last pattern pointer was *, advancing string pointer
            else if (starIdx != -1){
                p = starIdx + 1;
                match++;
                s = match;
            }
           //current pattern pointer is not star, last patter pointer was not *
          //characters do not match
            else return false;
        }

        //check for remaining characters in pattern
        while (p < pattern.length() && pattern.charAt(p) == '*')
            p++;

        return p == pattern.length();
}
*/



/*More concise DP 数组加了一行一列就handel了两边empty string的情况
The original post has DP 2d array index from high to low, which is not quite intuitive.

Below is another 2D dp solution. Ideal is identical.

dp[i][j] denotes whether s[0....i-1] matches p[0.....j-1],

First, we need to initialize dp[i][0], i= [1,m]. All the dp[i][0] should be false because p has nothing in it.

Then, initialize dp[0][j], j = [1, n]. In this case, s has nothing, to get dp[0][j] = true, p must be '*', '*', '**',etc. Once p.charAt(j-1) != '*', all the dp[0][j] afterwards will be false.

Then start the typical DP loop.

Though this solution is clear and easy to understand. It is not good enough in the interview. it takes O(mn) time and O(mn) space.

Improvement: 1) optimize 2d dp to 1d dp, this will save space, reduce space complexity to O(N). 2) use iterative 2-pointer.

public boolean isMatch_2d_method(String s, String p) {
    int m=s.length(), n=p.length();
    boolean[][] dp = new boolean[m+1][n+1];
    dp[0][0] = true;
    for (int i=1; i<=m; i++) {
        dp[i][0] = false;
    }

    for(int j=1; j<=n; j++) {
        if(p.charAt(j-1)=='*'){
            dp[0][j] = true;
        } else {
            break;
        }
    }

    for(int i=1; i<=m; i++) {
        for(int j=1; j<=n; j++) {
            if (p.charAt(j-1)!='*') {
                dp[i][j] = dp[i-1][j-1] && (s.charAt(i-1)==p.charAt(j-1) || p.charAt(j-1)=='?');
            } else {
                dp[i][j] = dp[i-1][j] || dp[i][j-1];
            }
        }
    }
    return dp[m][n];
}
*/
