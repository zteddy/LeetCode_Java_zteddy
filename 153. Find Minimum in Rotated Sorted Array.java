public class Solution {
	public int bSearch(int[] nums, int start, int end){
		if(start == end) return nums[start];
		if(start == end-1) return (nums[start]>nums[end])? nums[end] : nums[start];
		int middle = (start+end)/2;
		if(nums[end] < nums[middle])
			return bSearch(nums,middle,end);
		else
			return bSearch(nums,start,middle);
	}
    public int findMin(int[] nums) {
    	return bSearch(nums,0,nums.length-1);
    }
}

//写case，找规律！



/*Using iterative
Classic binary search problem.

Looking at subarray with index [start,end]. We can find out that if the first member is less than the last member, there's no rotation in the array. So we could directly return the first element in this subarray.

If the first element is larger than the last one, then we compute the element in the middle, and compare it with the first element. If value of the element in the middle is larger than the first element, we know the rotation is at the second half of this array. Else, it is in the first half in the array.

Welcome to put your comments and suggestions.

int findMin(vector<int> &num) {
    int start=0,end=num.size()-1;

    while (start<end) {
        if (num[start]<num[end])
            return num[start];

        int mid = (start+end)/2;

        if (num[mid]>=num[start]) {
            start = mid+1;
        } else {
            end = mid;
        }
    }

    return num[start];
}
*/

