public class Solution {
	public double factorial(int x){
		double result = 1;
		for(int i = 1; i <= x; i++){
			result *= i;
		}
		return result;
	}

    public int numWays(int n, int k) {
    	if(n == 0 || k == 0) return 0;
    	double total = Math.pow(k,n);
    	double fn = factorial(n);
    	for(int i = 3; i <= n; i++){
    		total -= fn*k/(factorial(i)*factorial(n-i));
    	}
    	return (int)total;
    }
}

/*DP
public int numWays(int n, int k) {
    if(n == 0) return 0;
    else if(n == 1) return k;
    int diffColorCounts = k*(k-1);
    int sameColorCounts = k;
    for(int i=2; i<n; i++) {
        int temp = diffColorCounts;
        diffColorCounts = (diffColorCounts + sameColorCounts) * (k-1);
        sameColorCounts = temp;
    }
    return diffColorCounts + sameColorCounts;
}
We divided it into two cases.

the last two posts have the same color, the number of ways to paint in this case is sameColorCounts.

the last two posts have different colors, and the number of ways in this case is diffColorCounts.

The reason why we have these two cases is that we can easily compute both of them, and that is all I do. When adding a new post, we can use the same color as the last one (if allowed) or different color. If we use different color, there're k-1 options, and the outcomes shoule belong to the diffColorCounts category. If we use same color, there's only one option, and we can only do this when the last two have different colors (which is the diffColorCounts). There we have our induction step.

Here is an example, let's say we have 3 posts and 3 colors. The first two posts we have 9 ways to do them, (1,1), (1,2), (1,3), (2,1), (2,2), (2,3), (3,1), (3,2), (3,3). Now we know that

diffColorCounts = 6;
And

sameColorCounts = 3;
Now for the third post, we can compute these two variables like this:

If we use different colors than the last one (the second one), these ways can be added into diffColorCounts, so if the last one is 3, we can use 1 or 2, if it's 1, we can use 2 or 3, etc. Apparently there are (diffColorCounts + sameColorCounts) * (k-1) possible ways.

If we use the same color as the last one, we would trigger a violation in these three cases (1,1,1), (2,2,2) and (3,3,3). This is because they already used the same color for the last two posts. So is there a count that rules out these kind of cases? YES, the diffColorCounts. So in cases within diffColorCounts, we can use the same color as the last one without worrying about triggering the violation. And now as we append a same-color post to them, the former diffColorCounts becomes the current sameColorCounts.

Then we can keep going until we reach the n. And finally just sum up these two variables as result.

Hope this would be clearer.
*/

/*Also DP
public class Solution {
    public int numWays(int n, int k) {
        if(n == 0 || k == 0) return 0;
        int[] dp = new int[n];
        for(int i = 0; i < n; i++){
            if(i == 0){
                dp[i] = k;
            }
            else if(i == 1){
                dp[i] = k*k;
            }
            else{
                dp[i] = dp[i-1]*(k-1) + dp[i-2]*(k-1);
            }
        }
        return dp[n-1];
    }
}
*/

/*Math
This should be fast. It's already consuming 0ms so I have no motive to further optimize it. As a statistician, I solved this problem by counting the possible ways of painting.

The answer is $\sum{d=0}^{\lfloor n/2 \rfloor} f(n,d)(k \cdot (k-1)^{n-d-1})$. (Oh! I wish TeX is supported here...) And f(n,d) is defined to be the possible ways to have d adjacent same-color posts.

long factorial(int x) {
    long res = 1;
    if (0 == x)
        return 1;
    for (int i = 1; i != x+1; ++i) {
        res *= i;
    }
    return res;
}

int ipow(int x, int y) {
    long res = x;
    if (0 == y)
        return 1;
    for (int i = 1; i != y; ++i)
        res *= x;
    return res;
}

int f(int n, int d) {
    long res = 1;
    for (int i = n - d; i != n - 2*d; --i) {
        if (i == d) {
            res = res / factorial(n-2*d);
            return res;
        }
        res *= i;
    }
    res = res / factorial(d);
    return res;
}

int numWays(int n, int k) {
    int sum = 0;
    int halfn = n/2;
    if (n == 0) return 0;
    // d is the number of adjacent same color posts
    for (int d = 0; d != halfn+1; ++d) {
        sum += k * ipow(k-1, n-d-1) * f(n,d);
    }
    return sum;
}
*/
