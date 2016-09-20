public class Solution {
    public int majorityElement(int[] nums) {
		Arrays.sort(nums);
		int count = 0;
		int now = nums[0];
		for(int i = 0; i< nums.length; i++){
			if(nums[i] == now){
				count++;
				if(count > nums.length/2) return now;
			}
			else{
				now = nums[i];
				count = 1;
				if(count > nums.length/2) return now;
			}
		}
		return now;

    }
}

/* More brief solution
public class Solution {
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
	  int len = nums.length;
	  return nums[len/2];
    }
}
*/

/* Genius solution
public class Solution {
    public int majorityElement(int[] num) {

        int major=num[0], count = 1;
        for(int i=1; i<num.length;i++){
            if(count==0){
                count++;
                major=num[i];
            }else if(major==num[i]){
                count++;
            }else count--;
            
        }
        return major;
    }
}
*/
