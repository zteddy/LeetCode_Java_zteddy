public class Solution {
    public boolean containsDuplicate(int[] nums) {

		if(nums.length == 0) return false;

		Arrays.sort(nums);

		int past = nums[0];
		for(int i = 1; i < nums.length; i++){
			if(nums[i] == past) return true;
			past = nums[i];
		}

		return false;

    }
}

/*Approach #3 (Hash Table) [Accepted]
public boolean containsDuplicate(int[] nums) {
    Set<Integer> set = new HashSet<>(nums.length);
    for (int x: nums) {
        if (set.contains(x)) return true;
        set.add(x);
    }
    return false;
}
*/
