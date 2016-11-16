public class Solution {
    public void rotate(int[] nums, int k) {

		int[] space = new int[k];
		int i;

		k = k % nums.length;

		//Too hard to count the array lable. Write it down! Draw it!
		for(i = 0; i < k; i++){
			space[i] = nums[i + (nums.length-k)];
		}

		for(i = nums.length - 1; i >= k; i--){
			nums[i] = nums[i-k];
		}

		for(i = 0; i < k; i++){
			nums[i] = space[i];
		}
    }
}

/*Approach #3 Using Cyclic Replacements [Accepted]
public class Solution {
    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }
}
*/

/*Approach #4 Using Reverse [Accepted]
public class Solution {
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }
    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}
*/
