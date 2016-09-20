public class Solution {
    public void moveZeroes(int[] nums) {
		int rear = nums.length - 1;
		int i;
		int j;
		for(i = rear; i >= 0; i--){ //注意中间的那个条件是最后一次的入口
			if(nums[i] == 0){
				for(j = i; j< rear; j++){
					nums[j] = nums[j+1];
				}
				nums[rear] = 0;
				rear--;
			}
		}
    }
}
//TODO Optimal(also a One-pass)