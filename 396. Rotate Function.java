public class Solution {
	
    public int[]  rotate(int[] nums, int k) {
		
		int[] space = new int[k];
		int i;
		
		k = k % nums.length;
		
		//Too hard to count the array lable. Write it down! Draw it!
		for(i = 0; i < k; i++){
			space[i] = nums[i + (nums.length-k)];
		}
		
		for(i = nums.length - 1; i >= k; i--){
			nums[i] = nums[i-k];
		}
		
		for(i = 0; i < k; i++){
			nums[i] = space[i];
		}
		return nums;
    }	
	
	public int function(int[] nums){
		
		int result = 0;
		int i;

		for(i = 0; i< nums.length; i++){
			result = result + i*nums[i];
		}
		
		return result;
		
	}
	
    public int maxRotateFunction(int[] A) {
		
		int i;
		int max = 0;
		int temp; 
		
		max = function(rotate(A, 0)); //inti max
		for(i = 0; i < A.length; i++){
			
			temp = function(rotate(A, i));
			//if(i == 0){max = temp;} //inti max
			if(max <= temp){max = temp;}
			
		}
			
			
		return max;
    }
}

//TODO Debug