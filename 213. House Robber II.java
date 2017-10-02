class Solution {
    public int rob(int[] nums) {
        if(nums.length == 1) return nums[0];
        int result = Integer.MIN_VALUE;
        int prev = 0;
        int curr = 0;
        for(int i = 1; i < nums.length; i++){
            int temp = curr;
            curr = Math.max(prev+nums[i], curr);
            prev = temp;
        }
        result = Math.max(result, curr);

        prev = 0;
        curr = 0;
        for(int i = 0; i < nums.length-1; i++){
            int temp = curr;
            curr = Math.max(prev+nums[i], curr);
            prev = temp;
        }
        result = Math.max(result, curr);

        return result;

    }
}
