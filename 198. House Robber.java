public class Solution {
    public int rob(int[] nums) {

    }
}


/*DP
public int rob(int[] num) {
    int[][] dp = new int[num.length + 1][2];
    for (int i = 1; i <= num.length; i++) {
        dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
        dp[i][1] = num[i - 1] + dp[i - 1][0];
    }
    return Math.max(dp[num.length][0], dp[num.length][1]);
}

dp[i][1] means we rob the current house and dp[i][0] means we don't,

so it is easy to convert this to O(1) space

public int rob(int[] num) {
    int prevNo = 0;
    int prevYes = 0;
    for (int n : num) {
        int temp = prevNo;
        prevNo = Math.max(prevNo, prevYes);
        prevYes = n + temp;
    }
    return Math.max(prevNo, prevYes);
}
*/

/*Also DP
public int rob(int[] nums) {
    if(nums.length==0) return 0;
    if(nums.length==1) return nums[0];

    //Initialize an arrays to store the money
	int[] mark = new int[nums.length];

    //We can infer the formula from problem:mark[i]=max(num[i]+mark[i-2],mark[i-1])
    //so initialize two nums at first.
	mark[0] = nums[0];
	mark[1] = Math.max(nums[0], nums[1]);

    //Using Dynamic Programming to mark the max money in loop.
	for(int i=2;i<nums.length;i++){
		mark[i] = Math.max(nums[i]+mark[i-2], mark[i-1]);
	}
	return mark[nums.length-1];
}
*/
