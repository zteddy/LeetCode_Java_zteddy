public class Solution {
    public boolean isOneEditDistance(String s, String t) {
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();

        int[][] dp = new int[cs.length+1][ct.length+1];

        for(int i = 0; i <= cs.length; i++){
            dp[i][0] = i;
        }

        for(int i = 0; i <= ct.length; i++){
            dp[0][i] = i;
        }

        for(int i = 1; i <= cs.length; i++){
            for(int j = 1; j <= ct.length; j++){
                if(cs[i-1] != ct[j-1]){
                    dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]);
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1]);
                    dp[i][j]++;
                }
                else dp[i][j] = dp[i-1][j-1];
            }
        }

        return (dp[cs.length][ct.length] == 1);

    }
}

//TLE
//因为不是求Edit Distance，所以可以用O(n)的方法来解



//One loop
/*
 * There're 3 possibilities to satisfy one edit distance apart:
 *
 * 1) Replace 1 char:
      s: a B c
      t: a D c
 * 2) Delete 1 char from s:
      s: a D  b c
      t: a    b c
 * 3) Delete 1 char from t
      s: a   b c
      t: a D b c
*/

public boolean isOneEditDistance(String s, String t) {
    for (int i = 0; i < Math.min(s.length(), t.length()); i++) {
        if (s.charAt(i) != t.charAt(i)) {
            if (s.length() == t.length()) // s has the same length as t, so the only possibility is replacing one char in s and t
                return s.substring(i + 1).equals(t.substring(i + 1));
            else if (s.length() < t.length()) // t is longer than s, so the only possibility is deleting one char from t
                return s.substring(i).equals(t.substring(i + 1));
            else // s is longer than t, so the only possibility is deleting one char from s
                return t.substring(i).equals(s.substring(i + 1));
        }
    }
    //All previous chars are the same, the only possibility is deleting the end char in the longer one of s and t
    return Math.abs(s.length() - t.length()) == 1;
}




//More clear
public boolean isOneEditDistance(String s, String t) {
    if(Math.abs(s.length()-t.length()) > 1) return false;
    if(s.length() == t.length()) return isOneModify(s,t);
    if(s.length() > t.length()) return isOneDel(s,t);
    return isOneDel(t,s);
}
public boolean isOneDel(String s,String t){
    for(int i=0,j=0;i<s.length() && j<t.length();i++,j++){
        if(s.charAt(i) != t.charAt(j)){
            return s.substring(i+1).equals(t.substring(j));
        }
    }
    return true;
}
public boolean isOneModify(String s,String t){
    int diff =0;
    for(int i=0;i<s.length();i++){
        if(s.charAt(i) != t.charAt(i)) diff++;
    }
    return diff==1;
}




//Three line
for (int i = 0; i < Math.min(s.length(), t.length()); i++) {
    if (s.charAt(i) != t.charAt(i)) {
        return s.substring(i + (s.length() >= t.length() ? 1 : 0)).equals(t.substring(i + (s.length() <= t.length() ? 1 : 0)));
    }
}
return Math.abs(s.length() - t.length()) == 1;




//Using two pointers
public class Solution {
    public boolean isOneEditDistance(String s, String t) {
        if(Math.abs(s.length() - t.length()) > 1)  return false;
        int i = 0, j = 0,err = 0;
        while(i<s.length() && j<t.length())
        {
            if(s.charAt(i) != t.charAt(j))
            {
                err++;
                if(err > 1)
                    return false;
                if(s.length() > t.length())
                    j--;
                else if(s.length() < t.length())
                    i--;
            }
            i++;
            j++;
        }
        return (err == 1 || (err == 0 && t.length() != s.length()))? true: false;
    }
}




//Clear two pointers
If s and t are one distance away then no matter it is insert or delete or replace the count of common characters must be max(m, n) - 1, where m is the length of s and n is the length of t. It is easy to see that the reverse is also true.

Assume the length of common prefix (from left to right) is i and the length of common suffix after i (from right to left) is j, the answer is then max(m, n) - 1 == i + j

Example 1 (1 replace)

s = "abcdefg", m = 7
t = "abcxefg", n = 7
i = 3, j = 3
max(m, n) - 1 == i + j is true
Example 2 (0 edit)

s = "abcdefg", m = 7
t = "abcdefg", n = 7
i = 7, j = 0
max(m, n) - 1 == i + j is false
Example 3 (1 insert)

s = "abcdefg", m = 7
t = "abcefg", n = 6
i = 3, j = 3
max(m, n) - 1 == i + j is true
Example 4 (1 delete 1 insert)

s = "abcdefg", m = 7
t = "abcefgh", n = 7
i = 3, j = 0
max(m, n) - 1 == i + j is false
The method is O(m+n) since any character is visited at most once.

Java

public boolean isOneEditDistance(String s, String t) {
    int m = s.length(), n = t.length();
    if (Math.abs(m - n) > 1) return false;
    int k = Math.min(m, n);
    int i = 0, j = 0;
    while (i < k && s.charAt(i) == t.charAt(i)) ++i;
    while (j < k - i && s.charAt(m - 1 - j) == t.charAt(n - 1 - j)) ++j;
    return m + n - k - 1 == i + j;
}
// Runtime : 2ms

