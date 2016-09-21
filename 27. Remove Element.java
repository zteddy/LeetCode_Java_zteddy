public class Solution {
    public int removeElement(int[] nums, int val) {
		
		int length = 0;
		
		int now = 0;
		int search = 0;
		
		if(nums.length != 0){
			
			for(search = 0;search < nums.length; search++){
			
				if(nums[search] != val){
					nums[now] = nums[search];
					now++;
					length++;
				}
				
			}

		}
		
		return length;
        
    }
}
//TODO None