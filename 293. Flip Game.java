public class Solution {
    public List<String> generatePossibleNextMoves(String s) {
        
		int now;
		char previous;
		int slength = s.length();
		List<String> result = new ArrayList<String>(); 
		
		if(slength <= 1) return result;
		
		char[] s_ca = s.toCharArray();
		
		previous = s_ca[0];
		for(now = 1; now < slength; now++){
			if(previous == '+' && s_ca[now] == '+'){
				//char[] temp = s_ca;  好像这样temp会变成s_ca的一个引用
				char[] temp = s.toCharArray();
				temp[now] = '-';
				temp[now-1] = '-';
				String a = new String(temp);
				result.add(a);
			}
			previous = s_ca[now];
		}
		
		return result;
		
    }
}

/* More concise solution
public List<String> generatePossibleNextMoves(String s) {
    List list = new ArrayList();
    for (int i=-1; (i = s.indexOf("++", i+1)) >= 0; )
        list.add(s.substring(0, i) + "--" + s.substring(i+2));
    return list;
}
*/