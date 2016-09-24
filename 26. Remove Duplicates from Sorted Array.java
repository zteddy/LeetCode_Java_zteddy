public class Solution {
    public int removeDuplicates(int[] nums) {
		
		int front;
		int now = 1;
		int count = 0;
		 
		int flength = nums.length;
		 
		if(nums.length == 0) return flength;
		 
		front = nums[0];
		int max = nums[nums.length-1];
		 
		while(now < nums.length){
			if(nums[now] == front) count++;
			else{
				front = nums[now];
				count = 0;
			}
			
			if(count > 0){
				nums[now] = max;
				flength--;
			}
			now++;
		}
		
		Arrays.sort(nums);
		return flength;
    }
}

/*Better solution
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    int i = 0;
    for (int j = 1; j < nums.length; j++) {
        if (nums[j] != nums[i]) {
            i++;
            nums[i] = nums[j];
        }
    }
    return i + 1;
}
*/

//Think again before begin!