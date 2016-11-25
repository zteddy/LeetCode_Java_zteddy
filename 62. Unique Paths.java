public class Solution {
    public int uniquePaths(int m, int n) {
    	int[][] dp = new int[m+1][n+1];
    	dp[1][1] = 1;
    	for(int i = 1; i < m+1; i++){
    		for(int j = 1; j < n+1; j++){
    			if(i == 1 && j == 1) continue;
    			dp[i][j] = dp[i-1][j]+dp[i][j-1];
    		}
    	}
    	return dp[m][n];
    }
}

/*O(n) space
public class Solution {
    public int uniquePaths(int m, int n) {
        int[] arr = new int[m];
        for (int i = 0; i < m; i++) {
            arr[i] = 1;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                arr[j] = arr[j] + arr[j-1];
            }
        }
        return arr[m-1];
    }
}
*/

/*Using math
Binomial coefficient:

class Solution {
    public:
        int uniquePaths(int m, int n) {
            int N = n + m - 2;// how much steps we need to do
            int k = m - 1; // number of steps that need to go down
            double res = 1;
            // here we calculate the total possible path number
            // Combination(N, k) = n! / (k!(n - k)!)
            // reduce the numerator and denominator and get
            // C = ( (n - k + 1) * (n - k + 2) * ... * n ) / k!
            for (int i = 1; i <= k; i++)
                res = res * (N - k + i) / i;
            return (int)res;
        }
    };

First of all you should understand that we need to do n + m - 2 movements : m - 1 down, n - 1 right, because we start from cell (1, 1).

Secondly, the path it is the sequence of movements( go down / go right),
therefore we can say that two paths are different
when there is i-th (1 .. m + n - 2) movement in path1 differ i-th movement in path2.

So, how we can build paths.
Let's choose (n - 1) movements(number of steps to the right) from (m + n - 2),
and rest (m - 1) is (number of steps down).

I think now it is obvious that count of different paths are all combinations (n - 1) movements from (m + n-2).
*/

/*Using math2
This is a combinatorial problem and can be solved without DP. For mxn grid, robot has to move exactly m-1 steps down and n-1 steps right and these can be done in any order.

For the eg., given in question, 3x7 matrix, robot needs to take 2+6 = 8 steps with 2 down and 6 right in any order. That is nothing but a permutation problem. Denote down as 'D' and right as 'R', following is one of the path :-

D R R R D R R R

We have to tell the total number of permutations of the above given word. So, decrease both m & n by 1 and apply following formula:-

Total permutations = (m+n)! / (m! * n!)

Following is my code doing the same :-

public class Solution {
    public int uniquePaths(int m, int n) {
        if(m == 1 || n == 1)
            return 1;
        m--;
        n--;
        if(m < n) {              // Swap, so that m is the bigger number
            m = m + n;
            n = m - n;
            m = m - n;
        }
        long res = 1;
        int j = 1;
        for(int i = m+1; i <= m+n; i++, j++){       // Instead of taking factorial, keep on multiply & divide
            res *= i;
            res /= j;
        }

        return (int)res;
    }
}
*/

/*Using math3
If you mark the south move as '1' and the east move as '0'. This problem shall be equal to :
Given (m+n-2) bits. you can fill in '1' for (m-1) times and '0' for (n-1) times, what is the number of different numbers?
the result is clear that the formula shall be C(m-1)(m+n-2), where m-1 is the superscript behind C and m+n-2 is the subscript behind C.
To avoid overflow, I write the program in this manner.

public class Solution {

	public int uniquePaths(int m, int n) {

	     long result = 1;
	     for(int i=0;i<Math.min(m-1,n-1);i++)
	         result = result*(m+n-2-i)/(i+1);
	     return (int)result;

	}
}
*/
