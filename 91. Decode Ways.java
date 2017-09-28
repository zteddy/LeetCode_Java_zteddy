class Solution {
    public int numDecodings(String s) {

        char[] c_s = s.toCharArray();

        if(s.length() == 0) return 0;

        int[] dp = new int[c_s.length+1];
        dp[0] = 1;

        for(int i = 0; i < c_s.length; i++){
            if(c_s[i] == '0'){
                if(i > 0 && c_s[i-1]-'0' <= 2 && c_s[i-1]-'0' > 0) dp[i+1] = dp[i-1];
                else return 0;
                continue;
            }
            if(i > 0 && ((c_s[i]-'0' <= 6 && c_s[i-1]-'0' <= 2) || (c_s[i-1]-'0' <= 1)) && c_s[i-1]-'0' != 0){
                dp[i+1] = dp[i-1] + dp[i];
            }
            else{
                dp[i+1] = dp[i];
            }
        }

        return dp[c_s.length];

    }
}



//一定要先手算一些简单的例子，寻找规律和考虑corner cases




/*More concise
I used a dp array of size n + 1 to save subproblem solutions. dp[0] means an empty string will have one way to decode, dp[1] means the way to decode a string of size 1. I then check one digit and two digit combination and save the results along the way. In the end, dp[n] will be the end result.

public class Solution {
    public int numDecodings(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length();
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = s.charAt(0) != '0' ? 1 : 0;
        for(int i = 2; i <= n; i++) {
            int first = Integer.valueOf(s.substring(i-1, i));   //好像后面的index可以是s.length()的
            int second = Integer.valueOf(s.substring(i-2, i));
            if(first >= 1 && first <= 9) {
               dp[i] += dp[i-1];
            }
            if(second >= 10 && second <= 26) {
                dp[i] += dp[i-2];
            }
        }
        return dp[n];
    }
}
*/




/*O(1) space DP
int numDecodings(string s) {
    if (!s.size() || s.front() == '0') return 0;
    // r2: decode ways of s[i-2] , r1: decode ways of s[i-1]
    int r1 = 1, r2 = 1;

    for (int i = 1; i < s.size(); i++) {
        // zero voids ways of the last because zero cannot be used separately
        if (s[i] == '0') r1 = 0;

        // possible two-digit letter, so new r1 is sum of both while new r2 is the old r1
        if (s[i - 1] == '1' || s[i - 1] == '2' && s[i] <= '6') {
            r1 = r2 + r1;
            r2 = r1 - r2;
        }

        // one-digit letter, no new way added
        else {
            r2 = r1;
        }
    }

    return r1;
}
*/


/*O(1) space DP 2
public int numDecodings(String s) {
    int n1 =1, n2=1, n3=0;
    if(s.length()==0||s.charAt(0)=='0') return 0;
    for(int i=2; i<=s.length(); i++)
    {
        n3=0;
        if(s.charAt(i-1)!='0') n3=n2;
        int num = Integer.parseInt(s.substring(i-2,i));
        if(num>=10 && num<=26) n3+=n1;
        n1=n2;
        n2=n3;
    }
    return n2;
}
*/




/*Path-Memorizing DFS
public class Solution {
    public int numDecodings(String s) {

        if (s == null || s.length() == 0)
            return 0;

        Set<String> symbols = new HashSet<String>();
        for (int i=1; i<=26; i++){
            symbols.add(String.valueOf(i));
        }

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        return numDec(s, 0,  map, symbols);
    }

    private int numDec(String str, int start,  Map<Integer, Integer> map, Set<String> symbols) {
        Integer count = map.get(start);
        if (count != null){
            return count;
        }

        if (start == str.length()){
            return 1;
        }

        int numWays = 0;
        if ((start + 1 <= str. length()) &&
            symbols.contains(str.substring(start, start + 1)) && symbols.contains(str.substring(start, start + 1)))
            numWays += numDec(str, start + 1, map, symbols);

        if ((start + 2 <= str. length()) &&
                symbols.contains(str.substring(start, start + 2)) && symbols.contains(str.substring(start, start + 2)))
            numWays += numDec(str, start + 2, map, symbols);

        map.put(start, numWays);

        return numWays;
    }
}
*/



/*More clear
This solution certainly isn't brand new, see similar concepts here, here, here, and probably a few other top rated. Mine also isn't the shortest. However, that is kind of the point. Dynamic Programming is one of my weaknesses and making the solution shorter usually makes it more abstract and difficult to understand. My solution below is a bit more drawn out with more descriptive variable names to help make it a bit easier to understand.

The basic concept is to build up the number of ways to get to state i from all the previous states less than i. We can do this by initializing a cache with a size of s.length() + 1. We always set waysToDecode[0] to 1 because there is only 1 way to decode an empty string. We can then build up the number of ways to decode starting from the first value and work our way to the end.

We only ever need to look at the character at i - 1 because we can't have values greater than 26, so three digits is never possible. There are four possibilities to consider: 1) The previous value is 0 and the current value is 0, we can't make progress, return 0. 2) The current value is 0, we have to use the previous value, if it is greater than 2, we can't make progress, return 0, otherwise we have to transition to this state from waysToDecode[i - 1]. 3) The previous value is 0, we can't use the previous, so the only way to transition to the current state is from the previous, so use waysToDecode[i]. 4) lastly, both previous and curr can be used so there are two ways to transition to the current state, thus at waysToDecode[i + 1] we can get here by using all the ways we can get to waysToDecode[i] + all the ways to get to waysToDecode[i - 1].

Keep in mind that the indices are adjusted for the cache because its size differs from the string size.

public class Solution {
    public int numDecodings(String s) {
        if (s.isEmpty() || s.charAt(0) - '0' == 0)
        {
            return 0;
        }

        int[] waysToDecode = new int[s.length() + 1];
        waysToDecode[0] = 1;
        waysToDecode[1] = 1;
        for (int i = 1; i < s.length(); i++)
        {
            int curr = s.charAt(i) - '0';
            int prev = s.charAt(i - 1) - '0';

            // can't make progress, return 0
            if (prev == 0 && curr == 0 || (curr == 0 && (prev * 10 + curr > 26)))
            {
                return 0;
            }
            // can't use the previous value, so can only get to this state from the previous
            else if (prev == 0 || (prev * 10 + curr) > 26)
            {
                waysToDecode[i + 1] = waysToDecode[i];
            }
            // can't use current state, can only get to this state from i - 1 state
            else if (curr == 0)
            {
                waysToDecode[i + 1] = waysToDecode[i - 1];
            }
            // can get to this state from the previous two states
            else
            {
                waysToDecode[i + 1] = waysToDecode[i] + waysToDecode[i - 1];
            }
        }

        return waysToDecode[waysToDecode.length - 1];
    }
}
*/
