public class Solution {
    public int maxSubArray(int[] nums) {
    	int thisSum = 0;
    	int maxSum = Integer.MIN_VALUE;
    	for(int i = 0; i < nums.length; i++){
    		thisSum += nums[i];
    		if(thisSum > maxSum) maxSum = thisSum;
    		if(thisSum < 0) thisSum = 0;
    	}
    	return maxSum;
    }
}

/*Analysis of DP
Analysis of this problem:
Apparently, this is a optimization problem, which can be usually solved by DP. So when it comes to DP, the first thing for us to figure out is the format of the sub problem(or the state of each sub problem). The format of the sub problem can be helpful when we are trying to come up with the recursive relation.

At first, I think the sub problem should look like: maxSubArray(int A[], int i, int j), which means the maxSubArray for A[i: j]. In this way, our goal is to figure out what maxSubArray(A, 0, A.length - 1) is. However, if we define the format of the sub problem in this way, it's hard to find the connection from the sub problem to the original problem(at least for me). In other words, I can't find a way to divided the original problem into the sub problems and use the solutions of the sub problems to somehow create the solution of the original one.

So I change the format of the sub problem into something like: maxSubArray(int A[], int i), which means the maxSubArray for A[0:i ] which must has A[i] as the end element. Note that now the sub problem's format is less flexible and less powerful than the previous one because there's a limitation that A[i] should be contained in that sequence and we have to keep track of each solution of the sub problem to update the global optimal value. However, now the connect between the sub problem & the original one becomes clearer:

maxSubArray(A, i) = maxSubArray(A, i - 1) > 0 ? maxSubArray(A, i - 1) : 0 + A[i];

And here's the code

public int maxSubArray(int[] A) {
        int n = A.length;
        int[] dp = new int[n];//dp[i] means the maximum subarray ending with A[i];
        dp[0] = A[0];
        int max = dp[0];

        for(int i = 1; i < n; i++){
            dp[i] = A[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
            max = Math.max(max, dp[i]);
        }

        return max;
}
*/

/*Divide and Conquer
public class Solution {
public int maxSubArray(int[] nums) {

    // Divide-and-conquer method.
    // The maximum summation of subarray can only exists under following conditions:
    // 1. the maximum summation of subarray exists in left half.
    // 2. the maximum summation of subarray exists in right half.
    // 3. the maximum summation of subarray exists crossing the midpoints to left and right.
    // 1 and 2 can be reached by using recursive calls to left half and right half of the subarraies.
    // Condition 3 can be found starting from the middle point to the left,
    // then starting from the middle point to the right. Then adds up these two parts and return.

    // T(n) = 2*T(n/2) + O(n)
    // this program runs in O(nlogn) time


    // int maxsum = subArray(nums, 0, nums.length-1);
    // return maxsum;
}

private int subArray(int[] A, int left, int right){
    if (left == right){
        //base case
        return A[left];
    }
    int mid = left + (right-left)/2;
    int leftsum = subArray(A, left, mid); //left part of the subarray sum, condition 1
    int rightsum = subArray(A, mid+1, right); //right part of the subarray sum, condition 2
    int middlesum = midSubArray(A, left, mid, right); //cross part of the subarray sum, condition 3
    // System.out.println(leftsum);
    // System.out.println(rightsum);
    // System.out.println(middlesum);

    //following if condition will return middlesum if lost the "=" under the conditon of leftsum = rightsum, which may be problematic if leftsum = rightsum < middlesum.
    //for example: [-1, -1, -2, -2]
    if (leftsum >= rightsum && leftsum >= middlesum){
        return leftsum;
    }
    if (rightsum >= leftsum && rightsum >= middlesum){
        return rightsum;
    }
    return middlesum;
}

private int midSubArray(int[] A, int left, int mid, int right){
    int leftsum = Integer.MIN_VALUE;
    int rightsum = Integer.MIN_VALUE;
    int sum = 0;
    for (int i = mid; i >= left; i--){
        sum += A[i];
        if (leftsum < sum){
            leftsum = sum;
        }
    }

    sum = 0;
    for (int j = mid + 1; j <= right; j++){
        sum += A[j];
        if (rightsum < sum){
            rightsum = sum;
        }
    }

    return leftsum + rightsum;
}
*/

/*Different DP
Explanation

Although there're some other simplified solutions, but DP solution can make the original thought for this problem clearer. In this solution, dp[i] means the largest sum among the subarrays whose last element is A[i].

Solution1. DP Solution - O(n) time, O(n) space

public int maxSubArray(int[] A) {
	int dp[] = new int[A.length]; int max = A[0]; dp[0] = A[0];
	for (int i = 1; i < A.length; i++) {
		dp[i] = Math.max(dp[i-1] + A[i] ,A[i]);
		max = Math.max(max, dp[i]);
	}
	return max;
}
Solution2. Simplified DP Solution - O(n) time, O(1) space - Special thanks for TWiStErRob's smart comment

The basic idea is to check previous sum, reset it to 0 if it's less than 0.

public int maxSubArray(int[] A) {
    int res = Integer.MIN_VALUE, sum = 0;
    for (int i = 0; i < A.length; i++) {
        sum = Math.max(sum, 0) + A[i];
        res = Math.max(res, sum);
    }
    return res;
}
*/
