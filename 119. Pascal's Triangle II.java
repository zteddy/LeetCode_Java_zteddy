public class Solution {
    public List<Integer> getRow(int rowIndex) {

		List<List<Integer>> PT = new ArrayList<List<Integer>>(rowIndex+1);
		
		for(int i = 1 ; i <= rowIndex+1; i++){
			if(i == 1){
				List<Integer> t = new ArrayList<Integer>(1);
				t.add(1);
				PT.add(t);
			}
			else{
				List<Integer> t = new ArrayList<Integer>(i);
				List<Integer> last = PT.get(i-2);
				t.add(1);
				for(int j = 1; j < i-1; j++){
					int num;
					num = last.get(j-1) + last.get(j);
					t.add(num);
				}
				t.add(1);
				PT.add(t);
				
			}
		}
		return PT.get(rowIndex);
	
    }
}


/*More concise solution
public static List<Integer> getRow2(int rowIndex) {
	List<Integer> ret = new ArrayList<Integer>();
	ret.add(1);
	for (int i = 1; i <= rowIndex; i++) {
		for (int j = i - 1; j >= 1; j--) {
			int tmp = ret.get(j - 1) + ret.get(j);
			ret.set(j, tmp);
		}
		ret.add(1);
	}
	return ret;
}
*/