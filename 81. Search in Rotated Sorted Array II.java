public class Solution {
    public boolean binarySearch(int[] nums, int target, int start, int end){
        if(start == end){
            if(nums[start] == target) return true;
            else return false;
        }
        if(end < 0) return false;

        int mid = (start+end)/2;

        if(nums[start] == nums[mid] || nums[mid] == nums[end])
            return binarySearch(nums,target,start,mid)||binarySearch(nums,target,mid+1,end);

        if (nums[start] < nums[mid]){
             if (target < nums[mid] && target >= nums[start])
                return binarySearch(nums,target,start,mid);
             else
                return binarySearch(nums,target,mid,end);
        }
        if (nums[mid] < nums[end]){
            if (target > nums[mid] && target <= nums[end])
                return binarySearch(nums,target,mid,end);
             else
                return binarySearch(nums,target,start,mid);
        }

        return false;


    }
    public boolean search(int[] nums, int target) {
        return binarySearch(nums,target,0,nums.length-1);
    }
}



/*move bound
As we cannot determine which of the above is true, the best we can do is to move left one step to the right and repeat the process again. Therefore, we are able to construct a worst case input which runs in O(n), for example: the input 11111111...115.

Below is a pretty concise code (thanks to bridger) for your reference which I found from the old discuss.

public boolean search(int[] nums, int target) {
    int start = 0, end = nums.length - 1, mid = -1;
    while(start <= end) {
        mid = (start + end) / 2;
        if (nums[mid] == target) {
            return true;
        }
        //If we know for sure right side is sorted or left side is unsorted
        if (nums[mid] < nums[end] || nums[mid] < nums[start]) {
            if (target > nums[mid] && target <= nums[end]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        //If we know for sure left side is sorted or right side is unsorted
        } else if (nums[mid] > nums[start] || nums[mid] > nums[end]) {
            if (target < nums[mid] && target >= nums[start]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        //If we get here, that means nums[start] == nums[mid] == nums[end], then shifting out
        //any of the two sides won't change the result but can help remove duplicate from
        //consideration, here we just use end-- but left++ works too
        } else {
            end--;
        }
    }

    return false;
}
*/
