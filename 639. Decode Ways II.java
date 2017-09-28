class Solution {
    public int numDecodings(String s) {

        char[] c_s = s.toCharArray();

        if(s.length() == 0) return 0;

        long[] dp = new long[c_s.length+1];
        dp[0] = 1;

        for(int i = 0; i < c_s.length; i++){
            if(c_s[i] == '0'){
                if(i > 0 && c_s[i-1] == '*') dp[i+1] = 2*dp[i-1]%1000000007;
                else if(i > 0 && c_s[i-1]-'0' <= 2 && c_s[i-1]-'0' > 0) dp[i+1] = dp[i-1]%1000000007;
                else return 0;
                continue;
            }
            if(c_s[i] == '*'){
                if(i > 0){
                    if(c_s[i-1] == '*') dp[i+1] = (dp[i-1]*15 + dp[i]*9)%1000000007;
                    else if(c_s[i-1] == '2') dp[i+1] = (dp[i-1]*6 + dp[i]*9)%1000000007;
                    else if(c_s[i-1] == '1') dp[i+1] = (dp[i-1]*9 + dp[i]*9)%1000000007;
                    else dp[i+1] = 9*dp[i]%1000000007;
                }
                else dp[i+1] = 9*dp[i]%1000000007;
                continue;

            }

            if(i > 0 && c_s[i-1] == '*'){
                if(c_s[i]-'0' <= 6) dp[i+1] = (dp[i-1]*2 + dp[i])%1000000007;
                else dp[i+1] = (dp[i-1] + dp[i])%1000000007;
            }
            else if(i > 0 && ((c_s[i]-'0' <= 6 && c_s[i-1]-'0' <= 2) || (c_s[i-1]-'0' <= 1)) && c_s[i-1]-'0' != 0){
                dp[i+1] = (dp[i-1] + dp[i])%1000000007;
            }
            else{
                dp[i+1] = dp[i]%1000000007;
            }
            // dp[i+1] %= 1000000007;
        }

        return (int)(dp[c_s.length]%1000000007);

    }
}


//mod什么的有毒吧






/*Summary
Approach #1 Using Recursion with memoization [Stack Overflow]

Algorithm

In order to find the solution to the given problem, we need to consider every case possible(for the arrangement of the input digits/characters) and what value needs to be considered for each case. Let's look at each of the possibilites one by one.

Firstly, let's assume, we have a function ways(s,i) which returns the number of ways to decode the input string ss, if only the characters upto the i^{th}i
​th
​​  index in this string are considered. We start off by calling the function ways(s, s.length()-1) i.e. by considering the full length of this string ss.

We started by using the last index of the string ss. Suppose, currently, we called the function as ways(s,i). Let's look at how we proceed. At every step, we need to look at the current character at the last index (ii) and we need to determine the number of ways of decoding that using this i^{th}i
​th
​​  character could add to the total value. There are the following possiblities for the i^{th}i
​th
​​  character.

The i^{th}i
​th
​​  character could be a *. In this case, firstly, we can see that this * could be decoded into any of the digits from 1-9. Thus, for every decoding possible upto the index i-1i−1, this * could be replaced by any of these digits(1-9). Thus, the total number of decodings is 9 times the number of decodings possible for the same string upto the index i-1i−1. Thus, this * initially adds a factor of 9*ways(s,i-1) to the total value.

Decode_Ways

Apart from this, this * at the i^{th}i
​th
​​  index could also contribute further to the total number of ways depending upon the character/digit at its preceding index. If the preceding character happens to be a 1, by combining this 1 with the current *, we could obtain any of the digits from 11-19 which could be decoded into any of the characters from K-S. We need to note that these decodings are in addition to the ones already obtained above by considering only a single current *(1-9 decoding to A-J). Thus, this 1* pair could be replaced by any of the numbers from 11-19 irrespective of the decodings done for the previous indices(before i-1i−1). Thus, this 1* pair leads to 8 times the number of decodings possible with the string ss upto the index i-2i−2. Thus, this adds a factor of 9 * ways(s, i - 2) to the total number of decodings.

Similarly, a 2* pair obtained by a 2 at the index i-1i−1 could be considered of the numbers from 21-26(decoding into U-Z), adding a total of 6 times the number of decodings possible upto the index i-2i−2.

Decode_Ways

On the same basis, if the character at the index i-1i−1 happens to be another *, this ** pairing could be considered as any of the numbers from 11-19(9) and 21-26(6). Thus, the total number of decodings will be 15(9+6) times the number of decodings possible upto the index i-2i−2.

Now, if the i^{th}i
​th
​​  character could be a digit from 1-9 as well. In this case, the number of decodings that considering this single digit can contribute to the total number is equal to the number of decodings that can be contributed by the digits upto the index i-1i−1. But, if the i^{th}i
​th
​​  character is
a 0, this 0 alone can't contribute anything to the total number of decodings(but it can only contribute if the digit preceding it is a 1 or 2. We'll consider this case below).

Apart from the value obtained(just above) for the digit at the i^{th}i
​th
​​  index being anyone from 0-9, this digit could also pair with the digit at the preceding index, contributing a value dependent on the previous digit. If the previous digit happens to be a 1, this 1 can combine with any of the current digits forming a valid number in the range 10-19. Thus, in this case, we can consider a pair formed by the current and the preceding digit, and, the number of decodings possible by considering the decoded character to be a one formed using this pair, is equal to the total number of decodings possible by using the digits upto the index i-2i−2 only.

But, if the previous digit is a 2, a valid number for decoding could only be a one from the range 20-26. Thus, if the current digit is lesser than 7, again this pairing could add decodings with count equal to the ones possible by using the digits upto the (i-2)^{th}(i−2)
​th
​​  index only.

Further, if the previous digit happens to be a *, the additional number of decodings depend on the current digit again i.e. If the current digit is greater than 6, this * could lead to pairings only in the range 17-19(* can't be replaced by 2 leading to 27-29). Thus, additional decodings with count equal to the decodings possible upto the index i-2i−2.

On the other hand, if the current digit is lesser than 7, this * could be replaced by either a 1 or a 2 leading to the decodings 10-16 and 20-26 respectively. Thus, the total number of decodings possible by considering this pair is equal to twice the number of decodings possible upto the index i-2i−2(since * can now be replaced by two values).

This way, by considering every possible case, we can obtain the required number of decodings by making use of the recursive function ways as and where necessary.

By making use of memoization, we can reduce the time complexity owing to duplicate function calls.

Java

public class Solution {
    int M = 1000000007;
    public int numDecodings(String s) {
        Integer[] memo=new Integer[s.length()];
        return ways(s, s.length() - 1,memo);
    }
    public int ways(String s, int i,Integer[] memo) {
        if (i < 0)
            return 1;
        if(memo[i]!=null)
            return memo[i];
        if (s.charAt(i) == '*') {
            long res = 9 * ways(s, i - 1,memo);
            if (i > 0 && s.charAt(i - 1) == '1')
                res = (res + 9 * ways(s, i - 2,memo)) % M;
            else if (i > 0 && s.charAt(i - 1) == '2')
                res = (res + 6 * ways(s, i - 2,memo)) % M;
            else if (i > 0 && s.charAt(i - 1) == '*')
                res = (res + 15 * ways(s, i - 2,memo)) % M;
            memo[i]=(int)res;
            return memo[i];
        }
        long res = s.charAt(i) != '0' ? ways(s, i - 1,memo) : 0;
        if (i > 0 && s.charAt(i - 1) == '1')
            res = (res + ways(s, i - 2,memo)) % M;
        else if (i > 0 && s.charAt(i - 1) == '2' && s.charAt(i) <= '6')
            res = (res + ways(s, i - 2,memo)) % M;
        else if (i > 0 && s.charAt(i - 1) == '*')
                res = (res + (s.charAt(i)<='6'?2:1) * ways(s, i - 2,memo)) % M;
        memo[i]= (int)res;
        return memo[i];
    }
}
Complexity Analysis

Time complexity : O(n)O(n). Size of recursion tree can go upto nn, since memomemo array is filled exactly once. Here, nn refers to the length of the input string.

Space complexity : O(n)O(n). The depth of recursion tree can go upto nn.

Approach #2 Dynamic Programming [Accepted]

Algorithm

From the solutions discussed above, we can observe that the number of decodings possible upto any index, ii, is dependent only on the characters upto the index ii and not on any of the characters following it. This leads us to the idea that this problem can be solved by making use of Dynamic Programming.

We can also easily observe from the recursive solution that, the number of decodings possible upto the index ii can be determined easily if we know the number of decodings possible upto the index i-1i−1 and i-2i−2. Thus, we fill in the dpdp array in a forward manner. dp[i]dp[i] is used to store the number of decodings possible by considering the characters in the given string ss upto the (i-1)^{th}(i−1)
​th
​​  index only(including it).

The equations for filling this dpdp at any step again depend on the current character and the just preceding character. These equations are similar to the ones used in the recursive solution.

The following animation illustrates the process of filling the dpdp for a simple example.

1 / 10
Java

public class Solution {
    int M = 1000000007;
    public int numDecodings(String s) {
        long[] dp = new long[s.length() + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '*' ? 9 : s.charAt(0) == '0' ? 0 : 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '*') {
                dp[i + 1] = 9 * dp[i];
                if (s.charAt(i - 1) == '1')
                    dp[i + 1] = (dp[i + 1] + 9 * dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '2')
                    dp[i + 1] = (dp[i + 1] + 6 * dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '*')
                    dp[i + 1] = (dp[i + 1] + 15 * dp[i - 1]) % M;
            } else {
                dp[i + 1] = s.charAt(i) != '0' ? dp[i] : 0;
                if (s.charAt(i - 1) == '1')
                    dp[i + 1] = (dp[i + 1] + dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '2' && s.charAt(i) <= '6')
                    dp[i + 1] = (dp[i + 1] + dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '*')
                    dp[i + 1] = (dp[i + 1] + (s.charAt(i) <= '6' ? 2 : 1) * dp[i - 1]) % M;
            }
        }
        return (int) dp[s.length()];
    }
}
Complexity Analysis

Time complexity : O(n)O(n). dpdp array of size n+1n+1 is filled once only. Here, nn refers to the length of the input string.

Space complexity : O(n)O(n). dpdp array of size n+1n+1 is used.

Approach #3 Constant Space Dynamic Programming [Accepted]:

Algorithm

In the last approach, we can observe that only the last two values dp[i-2]dp[i−2] and dp[i-1]dp[i−1] are used to fill the entry at dp[i-1]dp[i−1]. We can save some space in the last approach, if instead of maintaining a whole dpdp array of length nn, we keep a track of only the required last two values. The rest of the process remains the same as in the last approach.

Java

public class Solution {
    int M = 1000000007;
    public int numDecodings(String s) {
        long first = 1, second = s.charAt(0) == '*' ? 9 : s.charAt(0) == '0' ? 0 : 1;
        for (int i = 1; i < s.length(); i++) {
            long temp = second;
            if (s.charAt(i) == '*') {
                second = 9 * second;
                if (s.charAt(i - 1) == '1')
                    second = (second + 9 * first) % M;
                else if (s.charAt(i - 1) == '2')
                    second = (second + 6 * first) % M;
                else if (s.charAt(i - 1) == '*')
                    second = (second + 15 * first) % M;
            } else {
                second = s.charAt(i) != '0' ? second : 0;
                if (s.charAt(i - 1) == '1')
                    second = (second + first) % M;
                else if (s.charAt(i - 1) == '2' && s.charAt(i) <= '6')
                    second = (second + first) % M;
                else if (s.charAt(i - 1) == '*')
                    second = (second + (s.charAt(i) <= '6' ? 2 : 1) * first) % M;
            }
            first = temp;
        }
        return (int) second;
    }

}
Complexity Analysis

Time complexity : O(n)O(n). Single loop upto nn is required to find the required result. Here, nn refers to the length of the input string ss.

Space complexity : O(1)O(1). Constant space is used.
*/





/*6666666
Here, I try to provide not only code but also the steps and thoughts of solving this problem completely which can also be applied to many other DP problems.

(1) Try to solve this problem in Backtracking way, because it is the most straight-forward method.

 E.g '12*3'
                   12*3
              /             \
           12*(3)           12(*3)
         /     \            /      \
    12(*)(3)  1(2*)(3)  1(2)(*3)   ""
      /    \      \       /
1(2)(*)(3) ""     ""     ""
    /
   ""
(2) There are many nodes visited multiple times, like 12 and 1. Most importantly, the subtree of those nodes are "exactly" the same. The backtracking solution possibly can be improved by DP. Try to merge the same nodes.

        12*3
        /  \
      12*  |
       | \ |
       |  12
       | / |
       1   |
        \  |
          ""
(3) Successfully merge those repeated nodes and find out the optimal substructure, which is:

      current state = COMBINE(next state ,  next next state)
      e.g
              12*
              / \
            COMBINE (few different conditions)
            /     \
           12      1
Therefore, we can solve this problem by DP in bottom-up way instead of top-down memoization.
Now, we formulate the optimal substructure:

    dp[i] = COMBINE dp[i-1] and dp[i-2]
where dp[i] --> number of all possible decode ways of substring s(0 : i-1). Just keep it in mind.
But we need to notice there are some different conditions for this COMBINE operation.

(4) The pseudo code of solution would be:

Solution{
    //initial conditions
    dp[0] = ??
       :

    //bottom up method
    foreach( i ){
        dp[i] = COMBINE dp[i-1] and dp[i-2] ;
    }

    //Return
    return dp[last];
}
The COMBINE part will be something like:

    // for dp[i-1]
    if(condition A)
        dp[i] +=??  dp[i-1];
    else if(condition B)
        dp[i] +=??  dp[i-1];
            :
            :

     // for dp[i-2]
    if(condition C)
        dp[i] +=?? dp[i-2];
    else if(condition D)
        dp[i] +=?? dp[i-2];
             :
(5) The core step of this solution is to find out all possible conditions and their corresponding operation of combination.

        For dp[i-1]:

                  /           \
                 /             \
            s[i-1]='*'    s[i-1]>0
                |               |
          + 9*dp[i-1]        + dp[i-1]


        For dp[i-2]:

                   /                                  \
                  /                                    \
              s[n-2]='1'||'2'                         s[n-2]='*'
              /            \                       /             \
        s[n-1]='*'         s[n-1]!='*'          s[n-1]='*'       s[n-1]!='*'
         /       \               |                  |              /         \
  s[n-2]='1'  s[n-2]='2'   num(i-2,i-1)<=26         |         s[n-1]<=6    s[n-1]>6
      |            |             |                  |              |            |
 + 9*dp[i-2]   + 6*dp[i-2]     + dp[i-2]       + 15*dp[i-2]    + 2*dp[i-2]   + dp[i-2]
(6) Fill up the initial conditions before i = 2.
Because we need to check if num(i-2,i-1)<=26 for each i, we make the bottom-up process to begin from i=2. Just fill up the dp[0] and dp[1] by observation and by the definition of dp[i] --> number of all possible decode ways of substring s(0 : i-1).

         dp[0]=1;
         /      \
    s[0]=='*'  s[0]!='*'
        |         |
    dp[1]=9     dp[1]=1
(7) The final Solution:

    public int numDecodings(String s) {
        //initial conditions
        long[] dp = new long[s.length()+1];
        dp[0] = 1;
        if(s.charAt(0) == '0'){
            return 0;
        }
        dp[1] = (s.charAt(0) == '*') ? 9 : 1;

        //bottom up method
        for(int i = 2; i <= s.length(); i++){
            char first = s.charAt(i-2);
            char second = s.charAt(i-1);

            // For dp[i-1]
            if(second == '*'){
                dp[i] += 9*dp[i-1];
            }else if(second > '0'){
                dp[i] += dp[i-1];
            }

            // For dp[i-2]
            if(first == '*'){
                if(second == '*'){
                    dp[i] += 15*dp[i-2];
                }else if(second <= '6'){
                    dp[i] += 2*dp[i-2];
                }else{
                    dp[i] += dp[i-2];
                }
            }else if(first == '1' || first == '2'){
                if(second == '*'){
                    if(first == '1'){
                       dp[i] += 9*dp[i-2];
                    }else{ // first == '2'
                       dp[i] += 6*dp[i-2];
                    }
                }else if( ((first-'0')*10 + (second-'0')) <= 26 ){
                    dp[i] += dp[i-2];
                }
            }

            dp[i] %= 1000000007;
        }
        //sReturn
        return (int)dp[s.length()];
    }
P.S The space complexity can be further improved to O(1) because the current state i is only related to i-1 and i-2 during the bottom-up.
*/
