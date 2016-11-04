public class Solution {
    public int climbStairs(int n) {
    	if(n == 0) return 0;
    	if(n == 1) return 1;
    	if(n == 2) return 2;

    	int d2 = 1;
    	int d1 = 2;
    	int result = 0;

    	for(int i = 3; i <= n; i++){
    		result = d2 + d1;
    		d2 = d1;
    		d1 = result;
    	}

    	return result;
    }
}

//TODO None
