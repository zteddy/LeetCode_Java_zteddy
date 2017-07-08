public class Solution {
    public int binarySearch(int[] nums, int start, int end, int target){
        boolean left = false;
        boolean right = false;
        if(end<0) return -1;
        if(start == end){
            if(target == nums[start]) return start;
            else return -1;
        }
        if(start == end -1){
            if(target == nums[start]) return start;
            if(target == nums[end]) return end;
            return -1;
        }
        int mid = (start+end)/2;
        if(nums[start]<nums[mid]) left = true;
        if(nums[mid]<nums[end]) right = true;
        if(target<nums[mid]){
            if(left&right){
                if(target<nums[start])
                    return -1;
                else
                    return binarySearch(nums,start,mid,target);
            }
            else{
                if(left){
                    if(target<nums[start])
                        return binarySearch(nums,mid,end,target);
                    else
                        return binarySearch(nums,start,mid,target);
                }
                else
                    return binarySearch(nums,start,mid,target);
            }
        }
        else{
            if(left&right){
                if(target>nums[end])
                    return -1;
                else
                    return binarySearch(nums,mid,end,target);
            }
            else{
                if(right){
                    if(target>nums[end])
                        return binarySearch(nums,start,mid,target);
                    else
                        return binarySearch(nums,mid,end,target);
                }
                else
                    return binarySearch(nums,mid,end,target);
            }

        }
    }

    public int search(int[] nums, int target) {
        return binarySearch(nums,0,nums.length-1,target);
    }
}




/*finde rotation first
public int search(int[] nums, int target) {
    int minIdx = findMinIdx(nums);
    if (target == nums[minIdx]) return minIdx;
    int m = nums.length;
    int start = (target <= nums[m - 1]) ? minIdx : 0;
    int end = (target > nums[m - 1]) ? minIdx : m - 1;

    while (start <= end) {
        int mid = start + (end - start) / 2;
        if (nums[mid] == target) return mid;
        else if (target > nums[mid]) start = mid + 1;
        else end = mid - 1;
    }
    return -1;
}

public int findMinIdx(int[] nums) {
    int start = 0, end = nums.length - 1;
    while (start < end) {
        int mid = start + (end -  start) / 2;
        if (nums[mid] > nums[end]) start = mid + 1;
        else end = mid;
    }
    return start;
}
*/



/*more concise
The idea is that when rotating the array, there must be one half of the array that is still in sorted order.
For example, 6 7 1 2 3 4 5, the order is disrupted from the point between 7 and 1. So when doing binary search, we can make a judgement that which part is ordered and whether the target is in that range, if yes, continue the search in that half, if not continue in the other half.

public class Solution {
    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end){
            int mid = (start + end) / 2;
            if (nums[mid] == target)
                return mid;

            if (nums[start] <= nums[mid]){
                 if (target < nums[mid] && target >= nums[start])
                    end = mid - 1;
                 else
                    start = mid + 1;
            }

            if (nums[mid] <= nums[end]){
                if (target > nums[mid] && target <= nums[end])
                    start = mid + 1;
                 else
                    end = mid - 1;
            }
        }
        return -1;
    }
}
*/

