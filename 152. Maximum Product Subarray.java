public class Solution {
    public int maxProduct(int[] nums) {
    	int max = nums[0];
    	int p = 1;
    	int mp = 1;
    	int withoutfmp = 1;
    	boolean flag = false;
    	for(int i = 0; i < nums.length; i++){
    		if(nums[i] == 0){
    			p = 1;
    			mp = 1;
    			withoutfmp = 1;
    			flag = false;
    			if(max < 0) max = 0;
    			continue;
    		}
    		if(nums[i] < 0){
    			withoutfmp *= nums[i];
    			if(withoutfmp > max) max = withoutfmp;
    			if(!flag){
    				withoutfmp = 1;
    				flag = true;
    			}
    			if(mp < 0){
    				mp *= nums[i];
    				p = mp;
    			}
    			else{
    				p = 1;
    				mp *= nums[i];
    				continue;
    			}
    		}
    		else{
    			p *= nums[i];
    			mp *= nums[i];
    			withoutfmp *= nums[i];
    		}
    		if(p > max) max = p;
    		if(withoutfmp > max) max = withoutfmp;
    	}
    	return max;
    }
}


/*More concise
public int maxProduct(int[] nums) {
    int max = nums[0], maxToHere = nums[0], minToHere = nums[0];
    for (int i = 1; i < nums.length; i++) {
        int temp = maxToHere;
        maxToHere = Math.max(Math.max(minToHere * nums[i], maxToHere * nums[i]), nums[i]);
        minToHere = Math.min(Math.min(minToHere * nums[i], temp * nums[i]), nums[i]);
        max = Math.max(max, maxToHere);
    }
    return max;
}
*/
