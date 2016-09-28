public class Solution {
    public String convert(String s, int numRows) {
		
		StringBuffer result = new StringBuffer();
		List<String> index = new ArrayList<String>();
		
		int in = 0;
		int front = 0;
		int rear = 0;
		int a = 0;
		int b = 0;
		if(numRows > 1){
			while(in < s.length()){
				index.add(String.valueOf(in));
				in += (numRows-1)*2;
			}
			index.add(String.valueOf(in));
			
			front = 0;
			rear = index.size()-1;
			
			for(int i = 2; i<= numRows; i++){
				for(int j = front; j <= rear; j++){
					a = Integer.parseInt(index.get(j)) - 1;
					b = Integer.parseInt(index.get(j)) + 1;

					if(a >= 0 && !index.contains(String.valueOf(a))) index.add(String.valueOf(a));
					if(b >= 0 && !index.contains(String.valueOf(a))) index.add(String.valueOf(b));
				}
				front = rear + 1;
				rear = index.size() - 1;
			}
			
			for(int i = 0;i < index.size()-1; i++){
				if(Integer.parseInt(index.get(i)) < s.length()){
					result.append(s.charAt(Integer.parseInt(index.get(i))));
				}
			}
			
			return result.toString();

		}else if(numRows == 1){
			return s;
		}else{
			return "";
		}  
    }
}

//TODO Debug