public class Solution {
    public boolean isValid(String s) {

    	Stack st = new Stack();
    	char[] cs = s.toCharArray();
    	for (char i : cs) {
    		char j = ' ';        //不能直接定义j = ‘’的 要在‘’里面加个空格
    							//也可以直接char j; char的默认值是'\u0000'
    		if(!st.empty())
    			j = (char)st.peek();
    		if(j == '(' && i == ')') st.pop();
    		else if(j == '[' && i == ']') st.pop();
    		else if(j == '{' && i == '}') st.pop();
    		else st.push(i);
    	}
    	return st.empty();
    }
}

//TODO None
