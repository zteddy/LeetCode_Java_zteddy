public class Solution {
    public String longestCommonPrefix(String[] strs) {
        StringBuffer result = new StringBuffer();
		char temp = ' '; //注意要初始化
		int p = 0;
		boolean flag = true;
		
		if(strs.length == 0) return result.toString();
		
		while(flag == true){
			if(p < strs[0].length())
				temp = strs[0].charAt(p);
			else
				flag = false;
			
			for(int i = 1; i < strs.length; i++){
				if(p < strs[i].length()){
					if(strs[i].charAt(p) != temp) flag = false;
				}
				else flag = false;
			}
			
			if(flag == true){
				result.append(temp);
			}
			
			p++; //自己检查定义的每个量是否都被用到了
		}
		
		return result.toString();
    }
}

//发现自己还是只会用指针
/*Using indexOf() & substring()
public String longestCommonPrefix(String[] strs) {
    if(strs == null || strs.length == 0)    return "";
    String pre = strs[0];
    int i = 1;
    while(i < strs.length){
        while(strs[i].indexOf(pre) != 0)
            pre = pre.substring(0,pre.length()-1);
        i++;
    }
    return pre;
}
*/

/*Using sort()
 public String longestCommonPrefix(String[] strs) {
        StringBuilder result = new StringBuilder();
        
        if (strs!= null && strs.length > 0){
        
            Arrays.sort(strs);
            
            char [] a = strs[0].toCharArray();
            char [] b = strs[strs.length-1].toCharArray();
            
            for (int i = 0; i < a.length; i ++){
                if (b.length > i && b[i] == a[i]){
                    result.append(b[i]);
                }
                else {
                    return result.toString();
                }
            }
        return result.toString();
    }
 }
*/