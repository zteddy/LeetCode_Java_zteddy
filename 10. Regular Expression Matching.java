public class Solution {
    public boolean isMatch(String s, String p) {
    	if(s.length() == 0){
    		int lp = p.length();
    		if(lp == 0) return true;
    		else{
    			if(lp%2 == 1) return false;
    			else{
    				char[] cpt = p.toCharArray();
    				for(int k = 0; k < lp; k += 2){
    					if(cpt[k] != '*') return false;
    				}
    				return true;
    			}
    		}
    	}
    	int[] DPs = new int[s.length()+1];
    	boolean[] DPp = new boolean[p.length()+1];
    	char[] cs = s.toCharArray();
    	char[] cp = p.toCharArray();
    	for(int i = 0; i < cp.length; i++){
    		if(i+1 < cp.length && cp[i+1] == '*'){
    			if(i == cp.length-2 ){
    				DPp[i] = true;
    				DPp[i+1] = true;
    			}
				boolean temp = true;
				for(int j = 0; j < cs.length; j++){
					if(temp && (cs[j] == cp[i] || cp[i] == '.')){
						DPs[j] = 2;
						DPp[i] = true;
						DPp[i+1] = true;
					}

					temp = temp & (DPs[j] != 0);
				}
    			i++;
    		}
    		else{
    			for(int j = 0; j < cs.length+1; j++){
    				if(DPs[j] != 0) continue;
    				if(j < cs.length && (cs[j] == cp[i] || cp[i] == '.')){
    					DPs[j] = 1;
    					DPp[i] = true;
    					break;
    				}
    				if((j-1 >= 0) && (cs[j-1] == cp[i] || cp[i] == '.') && DPs[j-1] == 2) DPp[i] = true;
    				break;

    			}
    		}

    	}
    	return (DPs[cs.length-1] != 0) && DPp[cp.length-1];
    }
}
//Too comlpicated


//Using two-dimensional DP
This Solution use 2D DP. beat 90% solutions, very simple.

Here are some conditions to figure out, then the logic can be very straightforward.

1, If p.charAt(j) == s.charAt(i) :  dp[i][j] = dp[i-1][j-1];
2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
3, If p.charAt(j) == '*':
   here are two sub conditions:
               1   if p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2]  //in this case, a* only counts as empty
               2   if p.charAt(i-1) == s.charAt(i) or p.charAt(i-1) == '.':
                              dp[i][j] = dp[i-1][j]    //in this case, a* counts as multiple a
                           or dp[i][j] = dp[i][j-1]   // in this case, a* counts as single a
                           or dp[i][j] = dp[i][j-2]   // in this case, a* counts as empty
Here is the solution

public boolean isMatch(String s, String p) {

    if (s == null || p == null) {
        return false;
    }
    boolean[][] dp = new boolean[s.length()+1][p.length()+1];
    dp[0][0] = true;
    for (int i = 0; i < p.length(); i++) {
        if (p.charAt(i) == '*' && dp[0][i-1]) {
            dp[0][i+1] = true;
        }
    }
    for (int i = 0 ; i < s.length(); i++) {
        for (int j = 0; j < p.length(); j++) {
            if (p.charAt(j) == '.') {
                dp[i+1][j+1] = dp[i][j];
            }
            if (p.charAt(j) == s.charAt(i)) {
                dp[i+1][j+1] = dp[i][j];
            }
            if (p.charAt(j) == '*') {
                if (p.charAt(j-1) != s.charAt(i) && p.charAt(j-1) != '.') {
                    dp[i+1][j+1] = dp[i+1][j-1];
                } else {
                    dp[i+1][j+1] = (dp[i+1][j] || dp[i][j+1] || dp[i+1][j-1]);
                }
                //matrix[i-1][j]:abb vs ab*: depends on ab vs ab*
                //matrix[i][j-2] a  vs ab*  depends on a vs a
                //matrix[i][j-1] ab vs ab*: depends on ab vs ab
            }
        }
    }
    return dp[s.length()][p.length()];
}
https://raw.githubusercontent.com/hot13399/leetcode-graphic-answer/master/10.%20Regular%20Expression%20Matching.jpg



//Using one-dimensional DP 1
The dp algorithm is known by many other solutions. An optimization is to reduce the storage to O(n) with only one row of data.

public boolean isMatch(String s, String p) {
	/**
	 * This solution is assuming s has no regular expressions.
	 *
	 * dp: res[i][j]=is s[0,...,i-1] matched with p[0,...,j-1];
	 *
	 * If p[j-1]!='*', res[i][j] = res[i-1][j-1] &&
	 * (s[i-1]==p[j-1]||p[j-1]=='.'). Otherwise, res[i][j] is true if
	 * res[i][j-1] or res[i][j-2] or
	 * res[i-1][j]&&(s[i-1]==p[j-2]||p[j-2]=='.'), and notice the third
	 * 'or' case includes the first 'or'.
	 *
	 *
	 * Boundaries: res[0][0]=true;//s=p="". res[i][0]=false, i>0.
	 * res[0][j]=is p[0,...,j-1] empty, j>0, and so res[0][1]=false,
	 * res[0][j]=p[j-1]=='*'&&res[0][j-2].
	 *
	 * O(n) space is enough to store a row of res.
	 */

	int m = s.length(), n = p.length();
	boolean[] res = new boolean[n + 1];
	res[0] = true;

	int i, j;
	for (j = 2; j <= n; j++)
		res[j] = res[j - 2] && p.charAt(j - 1) == '*';

	char pc, sc, tc;
	boolean pre, cur; // pre=res[i - 1][j - 1], cur=res[i-1][j]

	for (i = 1; i <= m; i++) {
		pre = res[0];
		res[0] = false;
		sc = s.charAt(i - 1);

		for (j = 1; j <= n; j++) {
			cur = res[j];
			pc = p.charAt(j - 1);
			if (pc != '*')
				res[j] = pre && (sc == pc || pc == '.');
			else {
				// pc == '*' then it has a preceding char, i.e. j>1
				tc = p.charAt(j - 2);
				res[j] = res[j - 2] || (res[j] && (sc == tc || tc == '.'));
			}
			pre = cur;
		}
	}

	return res[n];
}



//Using one-dimensional Dp 2
A 2D space DP solution with core comment for you to understand.

public class Solution {
    public boolean isMatch(String s, String p) {
        // DP
        boolean opt[][] = new boolean[s.length()+1][p.length()+1];
        // base case
        opt[0][0] = true;
        boolean valid = false;
        for(int j = 2;j <= p.length();j+=2){
            if(p.charAt(j-1)=='*'){ valid = true; opt[0][j] = true;}
            else{ valid = false;}
            if(!valid) break;
        }
        // iteration
        for(int i = 1;i <= s.length();i++){
            for(int j = 1;j <= p.length();j++){
                opt[i][j] = false;
                if(s.charAt(i-1)==p.charAt(j-1) || p.charAt(j-1)=='.') opt[i][j] = opt[i-1][j-1];
                else if(p.charAt(j-1)=='*'){
                    if(s.charAt(i-1)==p.charAt(j-2) || p.charAt(j-2)=='.')
                        opt[i][j] = opt[i-1][j] || opt[i][j-2];
                        // opt[i-1][j] do take s[i] to match p[j-1],p[j]
                        // opt[i][j-2] don't take s[i] to match p[j-1],p[j]
                    else
                        opt[i][j] = opt[i][j-2];
                        // opt[i][j-2] cannot take s[i] to match p[j-1],p[j]
                }
            }
        }
        return opt[s.length()][p.length()];
    }
}
As we can seen, current column will only be affected by previous column or current column itself. Just need a pre[] array to store the previous array.

public class Solution {
    public boolean isMatch(String s, String p) {
        // DP
        boolean opt[] = new boolean[p.length()+1];
        boolean pre[] = new boolean[p.length()+1];
        // base case
        pre[0] = true;
        boolean valid = false;
        for(int j = 2;j <= p.length();j+=2){
            if(p.charAt(j-1)=='*'){ valid = true; pre[j] = true;}
            else{ valid = false;}
            if(!valid) break;
        }
        // iteration
        for(int i = 1;i <= s.length();i++){
            for(int j = 1;j <= p.length();j++){
                opt[j] = false;
                if(s.charAt(i-1)==p.charAt(j-1) || p.charAt(j-1)=='.') opt[j] = pre[j-1];
                else if(p.charAt(j-1)=='*'){
                    if(s.charAt(i-1)==p.charAt(j-2) || p.charAt(j-2)=='.')
                        opt[j] = pre[j] || opt[j-2];
                    else
                        opt[j] = opt[j-2];
                }
            }
            for(int j = 0;j <= p.length();j++)
                pre[j] = opt[j];
        }
        return pre[p.length()];
    }
}
And the pre[] array can be further eliminated, and only 1D array is used. But using two arrays is more clear.



//???
public boolean isMatch(String s, String p) {
    boolean[] match = new boolean[s.length()+1];
    Arrays.fill(match, false);
    match[s.length()] = true;
    for(int i=p.length()-1;i>=0;i--){
        if(p.charAt(i)=='*'){
            for(int j=s.length()-1;j>=0;j--)    match[j] = match[j]||match[j+1]&&(p.charAt(i-1)=='.'||s.charAt(j)==p.charAt(i-1));
            i--;
        }
        else{
            for(int j=0;j<s.length();j++)   match[j] = match[j+1]&&(p.charAt(i)=='.'||p.charAt(i)==s.charAt(j));
            match[s.length()] = false;
        }
    }
    return match[0];
}


//Using resursive
public class Solution {
    public boolean isMatch(String s, String p) {
        for(int i = 0; i < p.length(); s = s.substring(1)) {
            char c = p.charAt(i);
        	if(i + 1 >= p.length() || p.charAt(i + 1) != '*')
        		i++;
        	else if(isMatch(s, p.substring(i + 2)))
        		return true;

        	if(s.isEmpty() || (c != '.' && c != s.charAt(0)))
        		return false;
        }

        return s.isEmpty();
    }
}

Recursion version:
public class Solution {
    public boolean isMatch(String s, String p) {
        return p.isEmpty() ?
        s.isEmpty() :
        p.length() > 1 && p.charAt(1) == '*' ?
        isMatch(s, p.substring(2)) ?
        true :
        s.isEmpty() || (s.charAt(0) != p.charAt(0) && p.charAt(0) != '.') ?
        false :
        isMatch(s.substring(1), p) :
        s.isEmpty() || (s.charAt(0) != p.charAt(0) && p.charAt(0) != '.') ?
        false :
        isMatch(s.substring(1), p.substring(1));
    }
}
