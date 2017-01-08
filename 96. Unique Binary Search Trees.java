public class Solution {
    public int numTrees(int n) {
    	if(n == 0) return 0;
    	int[] count = new int[n+1];
    	count[0] = 1;
    	for(int k = 1; k <= n; k++){
    		for(int i = 1; i <= k; i++){
    			count[k] += count[k-i]*count[i-1];
    		}
    	}
    	return count[n];
    }
}

/*Using Catalan number
int numTrees(int n) {
    long long ans=1,i;
    for(i=1;i<=n;i++)
        ans = ans*(i+n)/i;
    return ans/i;
}
*/
