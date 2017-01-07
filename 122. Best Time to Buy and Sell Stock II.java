public class Solution {
    public int maxProfit(int[] prices) {
    	int low = 0;
    	int high = 0;
    	int profit = 0;
    	if(prices.length == 0) return 0;
    	while(high < prices.length){
    		if(high != 0 && prices[high] < prices[high-1]){
    			profit += prices[high-1] - prices[low];
    			low = high;
    		}
    		high++;
    	}
    	profit += prices[high-1] - prices[low];
    	return profit;
    }
}

/*More concise
public class Solution {
public int maxProfit(int[] prices) {
    int total = 0;
    for (int i=0; i< prices.length-1; i++) {
        if (prices[i+1]>prices[i]) total += prices[i+1]-prices[i];
    }

    return total;
}
*/
