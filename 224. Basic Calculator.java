class Solution {
    public int calculate(String s) {
        Stack<Character> st = new Stack<>();
        int i = 0;
        int sign = 1;
        boolean flag = false;
        if(s.charAt(i) == '-'){
            sign = -1;
            i++;
        }
        int result = 0;
        int num = 0;
        s += '+';
        while(i < s.length()){
            if(s.charAt(i) == ' '){
                i++;
                continue;
            }
            if(s.charAt(i) == '+' || s.charAt(i) == '-'){
                result += flag? sign/num:sign*num;
                flag = false;
                sign = s.charAt(i) == '+'? 1:-1;
                num = 0;
            }
            else if(s.charAt(i) == '*' || s.charAt(i) == '/'){
                num = flag? sign/num:num*sign;
                flag = false;
                sign = num;
                if(s.charAt(i) == '/') flag = true;
                num = 0;
            }
            else{
                //digit
                num *= 10;
                num += s.charAt(i) - '0';
            }
            i++;
        }

        return result;

    }
}

//Much elegent than II



/*Using stack
public class Solution {
    public int calculate(String s) {
        int len;
        if(s==null || (len = s.length())==0) return 0;
        Stack<Integer> stack = new Stack<Integer>();
        int num = 0;
        char sign = '+';
        for(int i=0;i<len;i++){
            if(Character.isDigit(s.charAt(i))){
                num = num*10+s.charAt(i)-'0';
            }
            if((!Character.isDigit(s.charAt(i)) &&' '!=s.charAt(i)) || i==len-1){
                if(sign=='-'){
                    stack.push(-num);
                }
                if(sign=='+'){
                    stack.push(num);
                }
                if(sign=='*'){
                    stack.push(stack.pop()*num);
                }
                if(sign=='/'){
                    stack.push(stack.pop()/num);
                }
                sign = s.charAt(i);
                num = 0;
            }
        }

        int re = 0;
        for(int i:stack){
            re += i;
        }
        return re;
    }
}
*/



/*Using prev
public int calculate(String s) {
    if (s == null) return 0;
    s = s.trim().replaceAll(" +", "");
    int length = s.length();

    int res = 0;
    long preVal = 0; // initial preVal is 0
    char sign = '+'; // initial sign is +
    int i = 0;
    while (i < length) {
        long curVal = 0;
        while (i < length && (int)s.charAt(i) <= 57 && (int)s.charAt(i) >= 48) { // int
            curVal = curVal*10 + (s.charAt(i) - '0');
            i++;
        }
        if (sign == '+') {
            res += preVal;  // update res
            preVal = curVal;
        } else if (sign == '-') {
            res += preVal;  // update res
            preVal = -curVal;
        } else if (sign == '*') {
            preVal = preVal * curVal; // not update res, combine preVal & curVal and keep loop
        } else if (sign == '/') {
            preVal = preVal / curVal; // not update res, combine preVal & curVal and keep loop
        }
        if (i < length) { // getting new sign
            sign = s.charAt(i);
            i++;
        }
    }
    res += preVal;
    return res;
}
*/




/*Explain
To have O(1) space solution, we have to drop the stack. To see why we can drop it, we need to reexamine the main purpose of the stack: it is used to hold temporary results for partial expressions with lower precedence levels.

For problem 224. Basic Calculator, the depth of precedence levels is unknown, since we can have arbitrary levels of parentheses in the expression. Therefore we do need the stack in the solution.

However for the current problem, we only have two precedence levels, lower level with '+' & '-' operations and higher level with '*' & '/' operations. So the stack can be replaced by two variables, one for the lower level and the other for the higher level. Note that when we are done with a partial expression involving '/' & '*' operations only, the result will fall back to the lower level.

Now let's look at each level separately.

First of course we will have a variable "num" to represent the current number involved in the operations.

For the lower level, we use a variable "pre" to denote the partial result. And as usual we will have a variable "sign" to indicate the sign of the incoming result.

For the higher level, we use a variable "curr" to represent the partial result, and another variable "op" to indicate what operation should be performed:

If op = 0, no '*' or '/' operation is needed and we simply assign num to curr;
if op = 1, we perform multiplication: curr = curr * num;
if op = -1, we perform division: curr = curr / num.
The key now is to figure out what to do depending on the scanned character from string s. There are three cases:

A digit is hit: As usual we will update the variable "num". One more step here is that we need to determine if this is the last digit of the current number. If so, we need to perform the corresponding operation depending on the value of "op" and update the value of "curr" (It is assumed that we are at the higher precedence level by default);
A ' * ' or '/' is hit: We need to update the value of "op" and reset "num" to 0;
A '+' or '-' is hit: Current higher precedence level is over, so the partial result (which is denoted by "curr") will fall back to the lower level and can be incorporated into the lower level partial result "pre". And of course we need to update the "sign" as well as reset "op" and "num" to 0.
One last point is that the string will end with digit or space, so we need to add the result for the last partial higher level expression to "pre". Here is the Java program.

public int calculate(String s) {
    int pre = 0, curr = 0, sign = 1, op = 0, num = 0;

    for (int i = 0; i < s.length(); i++) {
        if (Character.isDigit(s.charAt(i))) {
            num = num * 10 + (s.charAt(i) - '0');
            if (i == s.length() - 1 || !Character.isDigit(s.charAt(i + 1))) {
                curr = (op == 0 ? num : (op == 1 ? curr * num : curr / num));
            }

        } else if (s.charAt(i) == '*' || s.charAt(i) == '/') {
            op = (s.charAt(i) == '*' ? 1 : -1);
            num = 0;

        } else if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            pre += sign * curr;
            sign = (s.charAt(i) == '+' ? 1 : -1);
            op = 0;
            num = 0;
        }
    }

    return pre + sign * curr;
}
*/
