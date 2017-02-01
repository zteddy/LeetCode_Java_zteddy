public class Solution {
	List<String> result = new ArrayList<>();
	public void backtracking(ArrayList[] al, String digits, int i, String processing){
		if(i > digits.length()-1){
			if(processing.length()>0){
				result.add(processing);
			}
			return ;
		}
		String temp = digits.substring(i,i+1);
		int tempint = Integer.parseInt(temp);
		for(int j = 0; j < al[tempint].size(); j++){
			backtracking(al,digits,i+1,processing+al[tempint].get(j));
		}

	}
    public List<String> letterCombinations(String digits) {
    	ArrayList<String>[] al = new ArrayList[10];
    	for(int i = 0; i <10; i++){
    		al[i] = new ArrayList<String>();
    	}
    	al[2].add("a");al[2].add("b");al[2].add("c");
    	al[3].add("d");al[3].add("e");al[3].add("f");
    	al[4].add("g");al[4].add("h");al[4].add("i");
    	al[5].add("j");al[5].add("k");al[5].add("l");
    	al[6].add("m");al[6].add("n");al[6].add("o");
    	al[7].add("p");al[7].add("q");al[7].add("r");al[7].add("s");
    	al[8].add("t");al[8].add("u");al[8].add("v");
    	al[9].add("w");al[9].add("x");al[9].add("y");al[9].add("z");
    	backtracking(al,digits,0,new String());
    	return result;
    }
}



/*Using queue
public List<String> letterCombinations(String digits) {
    LinkedList<String> ans = new LinkedList<String>();
    String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    ans.add("");
    for(int i =0; i<digits.length();i++){
        int x = Character.getNumericValue(digits.charAt(i));
        while(ans.peek().length()==i){
            String t = ans.remove();
            for(char s : mapping[x].toCharArray())
                ans.add(t+s);
        }
    }
    return ans;
}
*/



/*More concise
public class Solution {
 	private static final String[] KEYS = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };

 	public List<String> letterCombinations(String digits) {
 		List<String> ret = new LinkedList<String>();
 		combination("", digits, 0, ret);
 		return ret;
 	}

 	private void combination(String prefix, String digits, int offset, List<String> ret) {
 		if (offset >= digits.length()) {
 			ret.add(prefix);
 			return;
 		}
 		String letters = KEYS[(digits.charAt(offset) - '0')];
 		for (int i = 0; i < letters.length(); i++) {
 			combination(prefix + letters.charAt(i), digits, offset + 1, ret);
 		}
 	}
 }
 */



/*Iterative
 Assume we have current answer {x1,x2,x3,x4} in the list and there is one more digit to go. The last digit has two choices: "a" and "b"; Then what we do is to first assign "a" to each element in the current answer list and put we get into a new list, {x1a,x2a,x3a,x4a}. Then assign "b" and we get {x1a,x2a,x3a,x4a,x1b,x2b,x3b,x4b}. That's the basic operation for one digit.
 Starting from an empty list, do the previous steps digit by digit. And your answer will finally come out.

 public List<String> letterCombinations(String digits) {
 	String[] data = new String[] { " ", "", "abc", "def", "ghi", "jkl",
 			"mno", "pqrs", "tuv", "wxyz" };
 	List<String> ans = new ArrayList<String>();
 	for (int i = 0; i < digits.length(); i++) {
 		char[] c = data[digits.charAt(i) - '0'].toCharArray();
 		List<String> sub = new ArrayList<String>();
 		for (int j = 0; j < c.length; j++) {
 			if (ans.isEmpty())
 				ans.add("");
 			for (String s : ans) {
 				sub.add(s + c[j]);
 			}

 			ans = sub;
 		}
 	}
 	return ans;

 }
 */
