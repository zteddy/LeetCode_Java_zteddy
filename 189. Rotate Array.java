public class Solution {
    public void rotate(int[] nums, int k) {
		
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
		

    }
}
//TODO Using Cyclic Replacements, Using Reverse