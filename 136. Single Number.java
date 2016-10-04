public class Solution {
    public int singleNumber(int[] nums) {

    	Map<Integer, Integer> hm = new HashMap<Integer, Integer>();
    	Integer temp;

    	for(Integer i = 0; i < nums.length; i++){
    		temp = new Integer(nums[i]);
    		if(hm.containsKey(temp)) hm.remove(temp);
    		else hm.put(temp, i);
    	}

    	for (int j = 0; j < nums.length; j++) {
    		if(hm.containsKey(nums[j])) return nums[j];
    	}

    	return nums[0];


        
    }
}

/*Genius solution using bitwise way
we use bitwise XOR to solve this problem :

first , we have to know the bitwise XOR in java
0 ^ N = N
N ^ N = 0
So..... if N is the single number
N1 ^ N1 ^ N2 ^ N2 ^..............^ Nx ^ Nx ^ N
= (N1^N1) ^ (N2^N2) ^..............^ (Nx^Nx) ^ N
= 0 ^ 0 ^ ..........^ 0 ^ N
= N

public int singleNumber(int[] nums) {
    int ans =0;
    
    int len = nums.length;
    for(int i=0;i!=len;i++)
        ans ^= nums[i];
    
    return ans;
    
}
*/