


/*Need new data structure
public class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
		int[][] array_with_index = new int[nums.length][2];
		
		for(int i = 0; i < nums.length; i++){
			array_with_index[i][0] = nums[i];
			array_with_index[i][1] = i;
		}
		
		Arrays.sort(array_with_index[]);
		
        
    }
}
*/

/*Time Limit Exceeded
public class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        for(int i = 1; i <= k; i++){
			for(int j = 0; j+i < nums.length; j++){
				if(nums[j] == nums[j+i]) return true;
			}
		}
		return false;
    }
}
*/

/*Time Limit Exceeded
public class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        for(int i = 0; i < nums.length; i++){
			for(int j = 1; j <= k; j++){
				if((i+j) < nums.length){
					if(nums[i] == nums[i+j]) return true;
				}
			}
		}
		return false;
    }
}
