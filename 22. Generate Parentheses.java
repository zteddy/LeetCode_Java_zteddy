public class Solution {
    // public List<String> generateParenthesis(int n) {
    // 	Set<String> hm = new HashSet<>();
    // 	hm.add("()");

    // 	for(int i = 2; i <= n; i++){
    // 		String[] it = (String[])hm.toArray(new String[0]);
    // 		//for(String str : hm){ //java.util.ConcurrentModificationException
    // 		for(String str : it){
    // 			hm.remove(str);
    // 			hm.add("("+str+")");
    // 			hm.add("()"+str);
    // 			hm.add(str+"()");
    // 		}
    //     }

    //     List<String> result = new ArrayList<>();
    //     for(String str : hm){
    //     	result.add(str);
    //     }


    //     return result;
    // }
    List<String> result = new ArrayList<>();
    public void gpHelper(String str, int countLeft, int countRight, int balance){
    	if(balance < 0) return;
    	if(balance == 0 && countLeft == 0 && countRight == 0){
    		result.add(str);
    	}
    	if(countLeft > 0) gpHelper(str+"(",countLeft-1,countRight,balance+1);
    	if(countRight > 0) gpHelper(str+")",countLeft,countRight-1,balance-1);

    }
    public List<String> generateParenthesis(int n) {
    	gpHelper(new String(""), n, n, 0);
    	return result;
    }
}

//一开始走错方向了，下次遇到这种一眼看不出来方向的题，先脑中一遍所有方法的列表，仔细想最有可能的几种，最后锁定



/*Another backtracking
public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<String>();
        backtrack(list, "", 0, 0, n);
        return list;
    }

public void backtrack(List<String> list, String str, int open, int close, int max){

    if(str.length() == max*2){
        list.add(str);
        return;
    }

    if(open < max)
        backtrack(list, str+"(", open+1, close, max);
    if(close < open)
        backtrack(list, str+")", open, close+1, max);
}
*/



/*Using DP
My method is DP. First consider how to get the result f(n) from previous result f(0)...f(n-1).
Actually, the result f(n) will be put an extra () pair to f(n-1). Let the "(" always at the first position, to produce a valid result, we can only put ")" in a way that there will be i pairs () inside the extra () and n - 1 - i pairs () outside the extra pair.

Let us consider an example to get clear view:

f(0): ""

f(1): "("f(0)")"

f(2): "("f(0)")"f(1), "("f(1)")"

f(3): "("f(0)")"f(2), "("f(1)")"f(1), "("f(2)")"

So f(n) = "("f(0)")"f(n-1) , "("f(1)")"f(n-2) "("f(2)")"f(n-3) ... "("f(i)")"f(n-1-i) ... "(f(n-1)")"

Below is my code:

public class Solution
{
    public List<String> generateParenthesis(int n)
    {
        List<List<String>> lists = new ArrayList<>();
        lists.add(Collections.singletonList(""));

        for (int i = 1; i <= n; ++i)
        {
            final List<String> list = new ArrayList<>();

            for (int j = 0; j < i; ++j)
            {
                for (final String first : lists.get(j))
                {
                    for (final String second : lists.get(i - 1 - j))
                    {
                        list.add("(" + first + ")" + second);
                    }
                }
            }

            lists.add(list);
        }

        return lists.get(lists.size() - 1);
    }
}
*/



/*Using DP2
For 2, it should place one "()" and add another one insert it but none tail it,

'(' f(1) ')' f(0)

or add none insert it but tail it by another one,

'(' f(0) ')' f(1)

Thus for n, we can insert f(i) and tail f(j) and i+j=n-1,

'(' f(i) ')' f(j)

public List<String> generateParenthesis(int n) {
	List<String> result = new ArrayList<String>();
	if (n == 0) {
		result.add("");
	} else {
		for (int i = n - 1; i >= 0; i--) {
			List<String> insertSub = generateParenthesis(i);
			List<String> tailSub = generateParenthesis(n - 1 - i);
			for (String insert : insertSub) {
				for (String tail : tailSub) {
					result.add("(" + insert + ")" + tail);
				}
			}

		}
	}
	return result;
}
*/


