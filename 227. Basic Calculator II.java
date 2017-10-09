class Solution {
    public int calculate(Stack<Character> st){
        int result = 0;
        int now = 0;
        int k = 0;
        boolean flag = false;
        while(!st.empty()){
            char temp = st.pop();

            if(temp == '(') break;
            if(temp == '+'){
                result += now;
                now = 0;
                k = 0;
                flag = true;
                continue;
            }
            else if(temp == '-'){
                result -= now;
                now = 0;
                k = 0;
                flag = true;
                continue;
            }

            now += (temp-'0')*Math.pow(10,k);
            flag = false;
            k++;
        }

        if(!flag) result+=now;


        return result;

    }
    public int calculate(String s) {
        Stack<Character> st = new Stack<>();
        for(char c:s.toCharArray()){
            if(c == ' ') continue;
            if(c == ')'){
                int temp = calculate(st);
                if(temp < 0){
                    if(!st.empty() && st.peek() == '-'){
                        st.pop();
                        st.push('+');
                    }
                    else{
                        st.push('-');
                    }
                    temp = -temp;
                }
                String ss = temp+"";
                for(char a:ss.toCharArray()){
                    st.push(a);
                }
                continue;
            }
            st.push(c);
        }
        return calculate(st);
    }
}


//虽然做出来了，而且想法还可以，但是有点丑陋




/*Using stack 1
Simple iterative solution by identifying characters one by one. One important thing is that the input is valid, which means the parentheses are always paired and in order.
Only 5 possible input we need to pay attention:

digit: it should be one digit from the current number
'+': number is over, we can add the previous number and start a new number
'-': same as above
'(': push the previous result and the sign into the stack, set result to 0, just calculate the new result within the parenthesis.
')': pop out the top two numbers from stack, first one is the sign before this pair of parenthesis, second is the temporary result before this pair of parenthesis. We add them together.
Finally if there is only one number, from the above solution, we haven't add the number to the result, so we do a check see if the number is zero.

public int calculate(String s) {
    Stack<Integer> stack = new Stack<Integer>();
    int result = 0;
    int number = 0;
    int sign = 1;
    for(int i = 0; i < s.length(); i++){
        char c = s.charAt(i);
        if(Character.isDigit(c)){
            number = 10 * number + (int)(c - '0');
        }else if(c == '+'){
            result += sign * number;
            number = 0;
            sign = 1;
        }else if(c == '-'){
            result += sign * number;
            number = 0;
            sign = -1;
        }else if(c == '('){
            //we push the result first, then sign;
            stack.push(result);
            stack.push(sign);
            //reset the sign and result for the value in the parenthesis
            sign = 1;
            result = 0;
        }else if(c == ')'){
            result += sign * number;
            number = 0;
            result *= stack.pop();    //stack.pop() is the sign before the parenthesis
            result += stack.pop();   //stack.pop() now is the result calculated before the parenthesis

        }
    }
    if(number != 0) result += sign * number;
    return result;
}
*/




/*Convert first
+1 for your concise and clean code. My solution seems to be really different from others.
First I reformed the input expression by rules of:

remove all '(', ')', ' ';
reverse the express string;
add '+' or '-' to the end of the express.
By this approach, the reformed expression will be easy to handled.

"1 + 1"                             =>   "1+1+"
" 2-1 + 2 "                        =>   "2+1-2+"
"(1+(4+5+2)-3)+(6+8)"    =>   "8+6+3-2+5+4+1+"
"2-(5-6)"                          =>   "6+5-2+"
Java code:

public int calculate(String s) {
    if(s == null)
        return 0;
    s = reform(s);
    int result = 0, num = 0, base = 1;
    for(char c: s.toCharArray())
        switch(c){
        case '+': result += num; num = 0; base = 1; break;
        case '-': result -= num; num = 0; base = 1; break;
        default: num += (c - '0') * base; base *= 10;
        }
    return result;
}

private String reform(String s) {
    StringBuilder sb = new StringBuilder();
    Stack<Boolean> stack = new Stack<>();
    stack.push(true);
    boolean add = true;
    for(char c: s.toCharArray())
        switch(c){
        case ' ': break;
        case '(': stack.push(add); break;
        case ')': stack.pop(); break;
        case '+':
            add = stack.peek();
            sb.append(stack.peek() ? '+' : '-');
            break;
        case '-':
            add = !stack.peek();
            sb.append(stack.peek() ? '-' : '+');
            break;
        default: sb.append(c);
        }
    if(sb.charAt(0) != '+' || sb.charAt(0) != '-')
        sb.insert(0, '+');
    return sb.reverse().toString();
}
*/





/*Using stack 2
Thanks for your answer. The following answer might be more slick (18ms):

Principle:

(Sign before '+'/'-') = (This context sign);
(Sign after '+'/'-') = (This context sign) * (1 or -1);
Algorithm:

Start from +1 sign and scan s from left to right;
if c == digit: This number = Last digit * 10 + This digit;
if c == '+': Add num to result before this sign; This sign = Last context sign * 1; clear num;
if c == '-': Add num to result before this sign; This sign = Last context sign * -1; clear num;
if c == '(': Push this context sign to stack;
if c == ')': Pop this context and we come back to last context;
Add the last num. This is because we only add number after '+' / '-'.
Implementation:

public int calculate(String s) {
    if(s == null) return 0;

    int result = 0;
    int sign = 1;
    int num = 0;

    Stack<Integer> stack = new Stack<Integer>();
    stack.push(sign);

    for(int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);

        if(c >= '0' && c <= '9') {
            num = num * 10 + (c - '0');

        } else if(c == '+' || c == '-') {
            result += sign * num;
            sign = stack.peek() * (c == '+' ? 1: -1);
            num = 0;

        } else if(c == '(') {
            stack.push(sign);

        } else if(c == ')') {
            stack.pop();
        }
    }

    result += sign * num;
    return result;
}
*/




/*Using infix prefix
The solution has 2 steps:

parse the input string and convert it to postfix notation.
evaluate the postfix string from step 1.
Infix to postfix conversion

converting a simple expression string that doesn't contain brackets to postfix is explained here. You can imagine the expression between brackets as a new simple expression (which we know how to convert to postfix). So when we encounter opening bracket "(" push it to the top stack. When we encounter a closing bracket ")" keep popping from stack until we find the matching "(", here we are removing all operators that belong to the expression between brackets. Then pop the "(" from the stack.

One more thing to take into consideration, we don't want any operator to pop the "(" from the stack except the ")". We can handle this be assigning the "(" the lowest rank such that no operator can pop it.

Evaluate postfix expression

postfix evaluation is explained here

If you have any ideas how to cut down the run time, please share your ideas :D.

Disclaimer: I didn't write the included links, however I find them simple and neat.

public class Solution {
    int rank(char op){
        // the bigger the number, the higher the rank
        switch(op){
            case '+':return 1;
            case '-':return 1;
            case '*':return 2;
            case '/':return 2;
            default :return 0; // '('
        }
    }
    List<Object> infixToPostfix(String s) {
        Stack<Character> operators = new Stack<Character>();
        List<Object> postfix = new LinkedList<Object>();

        int numberBuffer = 0;
        boolean bufferingOperand = false;
        for (char c : s.toCharArray()) {
            if (c >= '0' && c <= '9') {
                numberBuffer = numberBuffer * 10 + c - '0';
                bufferingOperand = true;
            } else {
                if(bufferingOperand)
                    postfix.add(numberBuffer);
                numberBuffer = 0;
                bufferingOperand = false;

                if (c == ' '|| c == '\t')
                    continue;

                if (c == '(') {
                    operators.push('(');
                } else if (c == ')') {
                    while (operators.peek() != '(')
                        postfix.add(operators.pop());
                    operators.pop(); // popping "("
                } else { // operator
                    while (!operators.isEmpty() && rank(c) <= rank(operators.peek()))
                        postfix.add(operators.pop());
                    operators.push(c);
                }
            }

        }
        if (bufferingOperand)
            postfix.add(numberBuffer);

        while (!operators.isEmpty())
            postfix.add(operators.pop());

        return postfix;
    }

    int evaluatePostfix(List<Object> postfix) {
        Stack<Integer> operands = new Stack<Integer>();
        int a = 0, b = 0;
        for (Object s : postfix) {
            if(s instanceof Character){
                char c = (Character) s;
                b = operands.pop();
                a = operands.pop();
                switch (c) {
                    case '+': operands.push(a + b); break;
                    case '-': operands.push(a - b); break;
                    case '*': operands.push(a * b); break;
                    default : operands.push(a / b);
                }
            }else { // instanceof Integer
                operands.push((Integer)s);
            }
        }
        return operands.pop();
    }

    public int calculate(String s) {
        return evaluatePostfix(infixToPostfix(s));
    }

}
*/


/*Using recursive
public int calculate(String s) {
    if (s.length() == 0) return 0;
    s = "(" + s + ")";
    int[] p = {0};
    return eval(s, p);
}
// calculate value between parentheses
private int eval(String s, int[] p){
    int val = 0;
    int i = p[0];
    int oper = 1; //1:+ -1:-
    int num = 0;
    while(i < s.length()){
        char c = s.charAt(i);
        switch(c){
            case '+': val = val + oper * num; num = 0; oper = 1; i++; break;// end of number and set operator
            case '-': val = val + oper * num; num = 0; oper = -1; i++; break;// end of number and set operator
            case '(': p[0] = i + 1; val = val + oper * eval(s, p); i = p[0]; break; // start a new eval
            case ')': p[0] = i + 1; return val + oper * num; // end current eval and return. Note that we need to deal with the last num
            case ' ': i++; continue;
            default : num = num * 10 + c - '0'; i++;
        }
    }
    return val;
}
*/
