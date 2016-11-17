public class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
    	Map<Integer, Integer> m = new HashMap<>();
    	for(int i = 0; i < nums.length; i++){
    		if(m.containsKey(nums[i])){
    			if(Math.abs(m.get(nums[i]) - i) <= k)
    				return true;
    			else
    				m.put(nums[i], i);
    		}
    		else
    			m.put(nums[i], i);
    	}
    	return false;
    }
}

/*Using sliding window

Explanation: It iterates over the array using a sliding window. The front of the window is at i, the rear of the window is k steps back. The elements within that window are maintained using a set. While adding new element to the set, if add() returns false, it means the element already exists in the set. At that point, we return true. If the control reaches out of for loop, it means that inner return true never executed, meaning no such duplicate element was found.

public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < nums.length; i++){
            if(i > k) set.remove(nums[i-k-1]);
            if(!set.add(nums[i])) return true;
        }
        return false;
 }


/*Need new data structure
public class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
		int[][] array_with_index = new int[nums.length][2];

		for(int i = 0; i < nums.length; i++){
			array_with_index[i][0] = nums[i];
			array_with_index[i][1] = i;
		}

		Arrays.sort(array_with_index[]);


    }
}
*/

/*Time Limit Exceeded
public class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        for(int i = 1; i <= k; i++){
			for(int j = 0; j+i < nums.length; j++){
				if(nums[j] == nums[j+i]) return true;
			}
		}
		return false;
    }
}
*/

/*Time Limit Exceeded
public class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        for(int i = 0; i < nums.length; i++){
			for(int j = 1; j <= k; j++){
				if((i+j) < nums.length){
					if(nums[i] == nums[i+j]) return true;
				}
			}
		}
		return false;
    }
}
