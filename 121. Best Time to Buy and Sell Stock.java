public class Solution {
    public int maxProfit(int[] prices) {
		if(prices.length == 0) return 0;
		if(prices.length == 1) return 0;
		
		int present_min = prices[0];
		for(int i = 1; i < prices.length; i++){
			present_min = Math.min(present_min, prices[i]);
			prices[i] -= present_min; 
		}//cal the max profit each selling day
		
		int max_profit = prices[1];
		for(i = 1; i < prices.length; i++){
			max_profit = Math.max(max_profit, prices[i]);
		}//find the max profit of all selling days
		
		if(max_profit <= 0) return 0;
	
		return max_profit;
		
    }
}
//TODO None

/* More concise
public class Solution {
    public int maxProfit(int prices[]) {
        int minprice = Integer.MAX_VALUE; //that is how they inti min;
        int maxprofit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minprice)
                minprice = prices[i];
            else if (prices[i] - minprice > maxprofit)
                maxprofit = prices[i] - minprice;
        }
        return maxprofit;
    }
}
*/