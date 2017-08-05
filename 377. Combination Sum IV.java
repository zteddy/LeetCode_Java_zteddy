public class Solution {
    // public void backtracking(int[] nums, int target, ArrayList<Integer> now, int sumNow, ArrayList<ArrayList<Integer>> result){
    //     if(sumNow == target){
    //         result.add(now);
    //     }
    //     if(sumNow < target){
    //         for(int i = 0; i < nums.length; i++){
    //             now.add(nums[i]);
    //             backtracking(nums,target,now,sumNow+nums[i],result);
    //             now.remove(now.size()-1);
    //         }
    //     }
    // }

    //It is not a backtracking problem
    //It only need number of results, we don't need to cal the detail of these results.
    //So those subproblems become duplicate problems.


    public int combinationSum4(int[] nums, int target) {

        // ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        // backtracking(nums,target,new ArrayList<Integer>(),0,result);

        // return result.size();

        int[] dp = new int[target+1];

        dp[0] = 1;

        for(int i = 1; i <= target; i++){
            dp[i] = 0;
            for(int j = 0; j < nums.length; j++){
                if(i-nums[j] >= 0) dp[i] += dp[i-nums[j]];
            }
        }

        return dp[target];
    }
}




/*Top down
private int[] dp;

public int combinationSum4(int[] nums, int target) {
    dp = new int[target + 1];
    Arrays.fill(dp, -1);
    dp[0] = 1;
    return helper(nums, target);
}

private int helper(int[] nums, int target) {
    if (dp[target] != -1) {
        return dp[target];
    }
    int res = 0;
    for (int i = 0; i < nums.length; i++) {
        if (target >= nums[i]) {
            res += helper(nums, target - nums[i]);
        }
    }
    dp[target] = res;
    return res;
}
*/






