public class Solution {
    public void moveZeroes(int[] nums) {
		int rear = nums.length - 1;
		int i;
		int j;
		for(i = rear; i >= 0; i--){ //ע���м���Ǹ����������һ�ε����
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