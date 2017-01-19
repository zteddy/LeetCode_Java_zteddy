public class Solution {
    public boolean canJump(int[] nums) {
    	int canReach = 0;
    	int now = 0;
    	int max;
    	while(now <= canReach){
    		max = 0;
    		for(int i = now; i <= canReach; i++){
    			if(nums[i]+i > max) max = nums[i]+i;
    		}
    		if(max >= nums.length-1) return true;
    		now = canReach;
    		canReach = max;
    		if(now == canReach) break;
    	}
    	return false;
    }
}


/*From back
bool canJump(int A[], int n) {
    int last=n-1,i,j;
    for(i=n-2;i>=0;i--){
        if(i+A[i]>=last)last=i;
    }
    return last<=0;
}
*/
