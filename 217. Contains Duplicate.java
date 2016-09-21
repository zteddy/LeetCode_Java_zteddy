public class Solution {
    public boolean containsDuplicate(int[] nums) {
		
		if(nums.length == 0) return false;
		
		Arrays.sort(nums);
		
		int past = nums[0];
		for(int i = 1; i < nums.length; i++){
			if(nums[i] == past) return true;
			past = nums[i];
		}
		
		return false;
        
    }
}
//TODO Using Hash Table


/*Time Limit Exceeded
public class Solution {
    public boolean containsDuplicate(int[] nums) {
		
		
		
		for(int i = 0; i < nums.length - 1; i++){
			for(int j = i + 1; j < nums.length; j++){
				if(nums[i] == nums[j]) return true;
			}
		}
		
		return false;
        
    }
}
*/