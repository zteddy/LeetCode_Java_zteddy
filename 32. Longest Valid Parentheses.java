class Solution {
    public int longestValidParentheses(String s) {
        // Stack<Character> s = new Stack<>();
        // char[] sArr = s.toCharArray();
        // int start = 0, end = 0;
        // while(end < sArr.length()){

        // }
        // if(s.length() == 0) return 0;

        int[] arr = new int[s.length()];
        char[] sArr = s.toCharArray();
        Arrays.fill(arr,-1);
        for(int i = 0; i < arr.length-1; i++){
            if(arr[i] == -1){
                if(sArr[i] == '(' && sArr[i+1] == ')'){
                    arr[i] = i+1;
                    arr[i+1] = i;
                }
            }
        }
        boolean isChange = true;
        while(isChange){
            isChange = false;
            for(int i = 0; i < arr.length; i++){
                if(arr[i] == -1) continue;
                if(i-1 >= 0 && arr[i]+1 < arr.length && i < arr[i]){
                    if(sArr[i-1] == '(' && sArr[arr[i]+1] == ')'){
                        arr[i-1] = arr[i]+1;
                        arr[arr[i]+1] = i-1;
                        arr[arr[i]] = -1;
                        arr[i] = -1;
                        isChange = true;
                    }
                }
                if(i+1 < arr.length){
                    if(sArr[i] == ')' && arr[i+1] != -1){
                        arr[arr[i]] = arr[i+1];
                        arr[arr[i+1]] = arr[i];
                        arr[i] = -1;
                        arr[i+1] = -1;
                        isChange = true;
                    }
                }
            }
        }
        int maxV = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] != -1){
                maxV = Math.max(maxV, Math.abs(i-arr[i])+1);
            }
        }

        return maxV;

    }
}





/*Summary
We need to determine the length of the largest valid substring of parentheses from a given string.

Solution

Approach #1 Brute Force [Time Limit Exceeded]

Algorithm

In this approach, we consider every possible non-empty even length substring from the given string and check whether it's a valid string of parentheses or not. In order to check the validity, we use the Stack's Method.

Every time we encounter a \text{‘(’}‘(’, we push it onto the stack. For every \text{‘)’}‘)’ encountered, we pop a \text{‘(’}‘(’ from the stack. If \text{‘(’}‘(’ isn't available on the stack for popping at anytime or if stack contains some elements after processing complete substring, the substring of parentheses is invalid. In this way, we repeat the process for every possible substring and we keep on storing the length of the longest valid string found so far.

Example:
"((())"

(( --> invalid
(( --> invalid
() --> valid, length=2
)) --> invalid
((()--> invalid
(())--> valid, length=4
maxlength=4
Java

public class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push('(');
            } else if (!stack.empty() && stack.peek() == '(') {
                stack.pop();
            } else {
                return false;
            }
        }
        return stack.empty();
    }
    public int longestValidParentheses(String s) {
        int maxlen = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 2; j <= s.length(); j+=2) {
                if (isValid(s.substring(i, j))) {
                    maxlen = Math.max(maxlen, j - i);
                }
            }
        }
        return maxlen;
    }
}
Complexity Analysis

Time complexity : O(n^3)O(n
​3
​​ ). Generating every possible substring from a string of length nn requires O(n^2)O(n
​2
​​ ). Checking validity of a string of length nn requires O(n)O(n).

Space complexity : O(n)O(n). A stack of depth nn will be required for the longest substring.

Approach #2 Using Dynamic Programming [Accepted]

Algorithm

This problem can be solved by using Dynamic Programming. We make use of a \text{dp}dp array where iith element of \text{dp}dp represents the length of the longest valid substring ending at iith index. We initialize the complete \text{dp}dp array with 0's. Now, it's obvious that the valid substrings must end with \text{‘)’}‘)’. This further leads to the conclusion that the substrings ending with \text{‘(’}‘(’ will always contain '0' at their corresponding \text{dp}dp indices. Thus, we update the \text{dp}dp array only when \text{‘)’}‘)’ is encountered.

To fill \text{dp}dp array we will check every two consecutive characters of the string and if

\text{s}[i] = \text{‘)’}s[i]=‘)’ and \text{s}[i - 1] = \text{‘(’}s[i−1]=‘(’, i.e. string looks like ``.......()" \Rightarrow‘‘.......()"⇒

\text{dp}[i]=\text{dp}[i-2]+2 dp[i]=dp[i−2]+2

We do so because the ending "()" portion is a valid substring anyhow and leads to an increment of 2 in the length of the just previous valid substring's length.

\text{s}[i] = \text{‘)’}s[i]=‘)’ and \text{s}[i - 1] = \text{‘)’}s[i−1]=‘)’, i.e. string looks like ``.......))" \Rightarrow‘‘.......))"⇒

if \text{s}[i - \text{dp}[i - 1] - 1] = \text{‘(’}s[i−dp[i−1]−1]=‘(’ then

\text{dp}[i]=\text{dp}[i-1]+\text{dp}[i-\text{dp}[i-1]-2]+2 dp[i]=dp[i−1]+dp[i−dp[i−1]−2]+2

The reason behind this is that if the 2nd last \text{‘)’}‘)’ was a part of a valid substring (say sub_ssub
​s
​​ ), for the last \text{‘)’}‘)’ to be a part of a larger substring, there must be a corresponding starting \text{‘(’}‘(’ which lies before the valid substring of which the 2nd last \text{‘)’}‘)’ is a part (i.e. before sub_ssub
​s
​​ ). Thus, if the character before sub_ssub
​s
​​  happens to be \text{‘(’}‘(’, we update the \text{dp}[i]dp[i] as an addition of 22 in the length of sub_ssub
​s
​​  which is \text{dp}[i-1]dp[i−1]. To this, we also add the length of the valid substring just before the term "(,sub_s,)" , i.e. \text{dp}[i-\text{dp}[i-1]-2]dp[i−dp[i−1]−2].

For better understanding of this method, see this example:

8 / 8
Java

public class Solution {
    public int longestValidParentheses(String s) {
        int maxans = 0;
        int dp[] = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }
}
Complexity Analysis

Time complexity : O(n)O(n). Single traversal of string to fill dp array is done.

Space complexity : O(n)O(n). dp array of size nn is used.

Approach #3 Using Stack [Accepted]

Algorithm

Instead of finding every possible string and checking its validity, we can make use of stack while scanning the given string to check if the string scanned so far is valid, and also the length of the longest valid string. In order to do so, we start by pushing -1−1 onto the stack.

For every \text{‘(’}‘(’ encountered, we push its index onto the stack.

For every \text{‘)’}‘)’ encountered, we pop the topmost element and subtract the current element's index from the top element of the stack, which gives the length of the currently encountered valid string of parentheses. If while popping the element, the stack becomes empty, we push the current element's index onto the stack. In this way, we keep on calculating the lengths of the valid substrings, and return the length of the longest valid string at the end.

See this example for better understanding.

9 / 11
Java

public class Solution {

    public int longestValidParentheses(String s) {
        int maxans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }
}
Complexity Analysis

Time complexity : O(n)O(n). nn is the length of the given string..

Space complexity : O(n)O(n). The size of stack can go up to nn.

Approach #4 Without extra space [Accepted]

Algorithm

In this approach, we make use of two counters leftleft and rightright. First, we start traversing the string from the left towards the right and for every \text{‘(’}‘(’ encountered, we increment the leftleft counter and for every \text{‘)’}‘)’ encountered, we increment the rightright counter. Whenever leftleft becomes equal to rightright, we calculate the length of the current valid string and keep track of maximum length substring found so far. If rightright becomes greater than leftleft we reset leftleft and rightright to 00.

Next, we start traversing the string from right to left and similar procedure is applied.

Example of this approach:

12 / 21
Java

public class Solution {
    public int longestValidParentheses(String s) {
        int left = 0, right = 0, maxlength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * right);
            } else if (right >= left) {
                left = right = 0;
            }
        }
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * left);
            } else if (left >= right) {
                left = right = 0;
            }
        }
        return maxlength;
    }
}
Complexity Analysis

Time complexity : O(n)O(n). Two traversals of the string.

Space complexity : O(1)O(1). Only two extra variables leftleft and rightright are needed.
*/
