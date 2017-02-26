public class Solution {
    public int rob(int[] nums) {

    }
}

//WA



//Approach #1 (Dynamic Programming) [Accepted]
It could be overwhelming thinking of all possibilities on which houses to rob.
A natural way to approach this problem is to work on the simplest case first.
Let us denote that:

f(k) = Largest amount that you can rob from the first k houses.
Ai = Amount of money at the ith house.

Let us look at the case n = 1, clearly f(1) = A1.
Now, let us look at n = 2, which f(2) = max(A1, A2).
For n = 3, you have basically the following two options:

Rob the third house, and add its amount to the first houses amount.
Do not rob the third house, and stick with the maximum amount of the first two houses.

Clearly, you would want to choose the larger of the two options at each step.
Therefore, we could summarize the formula as following:

f(k) = max(f(k – 2) + Ak, f(k – 1))

We choose the base case as f(–1) = f(0) = 0, which will greatly simplify our code as you can see.

The answer will be calculated as f(n). We could use an array to store and calculate the result, but since at each step you only need the previous two maximum values, two variables are suffice.

public int rob(int[] num) {
    int prevMax = 0;
    int currMax = 0;
    for (int x : num) {
        int temp = currMax;
        currMax = Math.max(prevMax + x, currMax);
        prevMax = temp;
    }
    return currMax;
}



//DP
public int rob(int[] num) {
    int[][] dp = new int[num.length + 1][2];
    for (int i = 1; i <= num.length; i++) {
        dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
        dp[i][1] = num[i - 1] + dp[i - 1][0];
    }
    return Math.max(dp[num.length][0], dp[num.length][1]);
}

dp[i][1] means we rob the current house and dp[i][0] means we dont,

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


//Also DP
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

