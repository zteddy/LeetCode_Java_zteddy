public class Solution {
    public List<List<Integer>> generate(int numRows) {
		
		List<List<Integer>> PT = new ArrayList<List<Integer>>(numRows);
		
		for(int i = 1 ; i <= numRows; i++){
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
		return PT;
    }
}
//TODO None