public class Solution {
    public String countAndSay(int n) {
		
		String result = new String("1");
		
		for(int i = 1; i < n; i++){
			StringBuffer temp = new StringBuffer();
			char key = result.charAt(0);
			int j = 0;
			int count = 0;
			while(j < result.length()){
				if(result.charAt(j) == key) count++;
				else{
					temp.append(Integer.toString(count));
					temp.append(Character.toString(key));
					key = result.charAt(j);
					count = 1;
				}
				j++;
			}
			
			temp.append(Integer.toString(count));
			temp.append(Character.toString(key));
		
			result = temp.toString();

		}  
		
		return result;
		       
    }
}

//TODO None