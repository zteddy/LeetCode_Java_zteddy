public class Solution {
	public int[] mem;
	public int memRec(int n){
		if(n == 0) return 0;
		int min = Integer.MAX_VALUE;
		if(mem[n] != -1) return mem[n];
		int temp;
		for(int i = 1; n-i*i >= 0; i++){
			if(mem[n-i*i] != -1) temp = mem[n-i*i];
			else
				temp = memRec(n-i*i);
			if(temp < min) min = temp;
		}
		mem[n] = min+1;
		return min+1;
	}
    public int numSquares(int n) {
    	mem = new int[n+1];
    	for(int i = 0; i < mem.length; i++){
    		mem[i] = -1;
    	}               //Arrays.fill(dp, Integer.MAX_VALUE);
    	return memRec(n);
    }
}


/*More concise
public class Solution {
    public int numSquares(int n) {
        int[] record = new int[n+1];
        for(int i=0;i<=n;i++){
            record[i] = i;
            for(int j=1;j*j<=i;j++){
                record[i] = Math.min(record[i-j*j]+1,record[i]);
            }
        }
        return record[n];
    }
}
*/



/*Using DP
1.Dynamic Programming: 440ms

class Solution
{
public:
    int numSquares(int n)
    {
        if (n <= 0)
        {
            return 0;
        }

        // cntPerfectSquares[i] = the least number of perfect square numbers
        // which sum to i. Note that cntPerfectSquares[0] is 0.
        vector<int> cntPerfectSquares(n + 1, INT_MAX);
        cntPerfectSquares[0] = 0;
        for (int i = 1; i <= n; i++)
        {
            // For each i, it must be the sum of some number (i - j*j) and
            // a perfect square number (j*j).
            for (int j = 1; j*j <= i; j++)
            {
                cntPerfectSquares[i] =
                    min(cntPerfectSquares[i], cntPerfectSquares[i - j*j] + 1);
            }
        }

        return cntPerfectSquares.back();
    }
};
2.Static Dynamic Programming: 12ms

class Solution
{
public:
    int numSquares(int n)
    {
        if (n <= 0)
        {
            return 0;
        }

        // cntPerfectSquares[i] = the least number of perfect square numbers
        // which sum to i. Since cntPerfectSquares is a static vector, if
        // cntPerfectSquares.size() > n, we have already calculated the result
        // during previous function calls and we can just return the result now.
        static vector<int> cntPerfectSquares({0});

        // While cntPerfectSquares.size() <= n, we need to incrementally
        // calculate the next result until we get the result for n.
        while (cntPerfectSquares.size() <= n)
        {
            int m = cntPerfectSquares.size();
            int cntSquares = INT_MAX;
            for (int i = 1; i*i <= m; i++)
            {
                cntSquares = min(cntSquares, cntPerfectSquares[m - i*i] + 1);
            }

            cntPerfectSquares.push_back(cntSquares);
        }

        return cntPerfectSquares[n];
    }
};
*/



/*Using BFS
4.Breadth-First Search: 80ms

class Solution
{
public:
    int numSquares(int n)
    {
        if (n <= 0)
        {
            return 0;
        }

        // perfectSquares contain all perfect square numbers which
        // are smaller than or equal to n.
        vector<int> perfectSquares;
        // cntPerfectSquares[i - 1] = the least number of perfect
        // square numbers which sum to i.
        vector<int> cntPerfectSquares(n);

        // Get all the perfect square numbers which are smaller than
        // or equal to n.
        for (int i = 1; i*i <= n; i++)
        {
            perfectSquares.push_back(i*i);
            cntPerfectSquares[i*i - 1] = 1;
        }

        // If n is a perfect square number, return 1 immediately.
        if (perfectSquares.back() == n)
        {
            return 1;
        }

        // Consider a graph which consists of number 0, 1,...,n as
        // its nodes. Node j is connected to node i via an edge if
        // and only if either j = i + (a perfect square number) or
        // i = j + (a perfect square number). Starting from node 0,
        // do the breadth-first search. If we reach node n at step
        // m, then the least number of perfect square numbers which
        // sum to n is m. Here since we have already obtained the
        // perfect square numbers, we have actually finished the
        // search at step 1.
        queue<int> searchQ;
        for (auto& i : perfectSquares)
        {
            searchQ.push(i);
        }

        int currCntPerfectSquares = 1;
        while (!searchQ.empty())
        {
            currCntPerfectSquares++;

            int searchQSize = searchQ.size();
            for (int i = 0; i < searchQSize; i++)
            {
                int tmp = searchQ.front();
                // Check the neighbors of node tmp which are the sum
                // of tmp and a perfect square number.
                for (auto& j : perfectSquares)
                {
                    if (tmp + j == n)
                    {
                        // We have reached node n.
                        return currCntPerfectSquares;
                    }
                    else if ((tmp + j < n) && (cntPerfectSquares[tmp + j - 1] == 0))
                    {
                        // If cntPerfectSquares[tmp + j - 1] > 0, this is not
                        // the first time that we visit this node and we should
                        // skip the node (tmp + j).
                        cntPerfectSquares[tmp + j - 1] = currCntPerfectSquares;
                        searchQ.push(tmp + j);
                    }
                    else if (tmp + j > n)
                    {
                        // We don't need to consider the nodes which are greater ]
                        // than n.
                        break;
                    }
                }

                searchQ.pop();
            }
        }

        return 0;
    }
};
*/




/*Using math 2
For better measurement, I wrapped the actual solution in a 10000-loop. This got accepted in 344 ms (every time in three submits), so without the wrapper it should take about 0.0344 ms. I tried a few variations and this is the fastest I managed to do.

(Update: After qgambit2's challenge, I optimized my my original approach and now that's my fastest, with about 180 ms.)

First I use the fact that four squares always suffice and the fact that four squares are only needed for numbers of the form 4a(8b+7). After that part, I know that the answer is 1, 2 or 3, and I try to build n as sum of one or two squares.

For that, I use a kind of two-pointers-approach. Instead of going through squares a2 and checking whether n-a2 is a square (which would involve computing lots of square roots), imagine you start with a=02 and b=floor(sqrt(n))2 and as long as a<=b, either make a the next larger square or make b the next smaller square, depending on whether the sum of the two squares is too small or too large (or return 2, if it's exactly right).

But in order to improve speed further, I use that squares are sums of consecutive odd numbers starting at 1 (for example, 25=1+3+5+7+9), and my a and b aren't squares but the corresponding odd numbers. And instead of computing the sum of the two squares, I just add to or subtract from n, trying to reach zero. This way, my main part doesn't even have multiplications. Just simple addition/subtraction and comparisons.

The solution:

int numSquaresReal(int n) {
    while (n % 4 == 0)
        n /= 4;
    if (n % 8 == 7)
        return 4;
    int a = -1, b = sqrt(n);
    n -= b * b;
    b += b + 1;
    while (a <= b) {
        if (n < 0)
            n += b -= 2;
        else if (n > 0)
            n -= a += 2;
        else
            return a < 0 ? 1 : 2;
    }
    return 3;
}
The wrapper for better time measurement:

int numSquares(int n) {
    int sum = 0;
    for (int i=0; i<10000; i++)
        sum += numSquaresReal(n);
    return sum / 10000;
}
*/
