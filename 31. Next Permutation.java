class Solution {
    public void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public void nextPermutation(int[] nums) {

        boolean flag = false;

        for(int i = nums.length-1; i >0; i--){
            if(nums[i-1] < nums[i]){
                int largeMin = Integer.MAX_VALUE;
                int largeMinIndex = i-1;
                for(int j = i; j < nums.length; j++){
                    if(nums[j] > nums[i-1] && nums[j] < largeMin){
                        largeMin = nums[j];
                        largeMinIndex = j;
                    }
                }
                swap(nums, i-1, largeMinIndex);
                flag = true;
                // for(int j = i; j < nums.length-1; j++){
                //     if(nums[j] > nums[j+1]) swap(nums, j, j+1);
                // }
                Arrays.sort(nums,i,nums.length);
                break;
            }
        }

        if(!flag){
            for(int i = 0; i < nums.length/2; i++){
                swap(nums, i, nums.length-i-1);
            }
        }
    }
}



//耐！心！找！规！律！
//好像不用排序，只要reverse就行了



/*Summary
Summary

We need to find the next lexicographic permutation of the given list of numbers than the number formed by the given array.

Solution

Approach #1 Brute Force [Time Limit Exceeded]

Algorithm

In this approach, we find out every possible permutation of list formed by the elements of the given array and find out the permutation which is just larger than the given one. But this one will be a very naive approach, since it requires us to find out every possible permutation which will take really long time and the implementation is complex. Thus, this approach is not acceptable at all. Hence, we move on directly to the correct approach.

Complexity Analysis

Time complexity : O(n!)O(n!). Total possible permutations is n!n!.
Space complexity : O(n)O(n). Since an array will be used to store the permutations.
Approach #2 Single Pass Approach [Accepted]

Algorithm

First, we observe that for any given sequence that is in descending order, no next larger permutation is possible. For example, no next permutation is possible for the following array: [9, 5, 4, 3, 1]

We need to find the first pair of two successive numbers a[i]a[i] and a[i-1]a[i−1], from the right, which satisfy a[i] > a[i-1]a[i]>a[i−1]. Now, no rearrangements to the right of a[i-1]a[i−1] can create a larger permutation since that subarray consists of numbers in descending order. Thus, we need to rearrange the numbers to the right of a[i-1]a[i−1] including itself.

Now, what kind of rearrangement will produce the next larger number? We want to create the permutation just larger than the current one. Therefore, we need to replace the number a[i-1]a[i−1] with the number which is just larger than itself among the numbers lying to its right section, say a[j]a[j].

 Next Permutation

We swap the numbers a[i-1]a[i−1] and a[j]a[j]. We now have the correct number at index i-1i−1. But still the current permutation isn't the permutation that we are looking for. We need the smallest permutation that can be formed by using the numbers only to the right of a[i-1]a[i−1]. Therefore, we need to place those numbers in ascending order to get their smallest permutation.

But, recall that while scanning the numbers from the right, we simply kept decrementing the index until we found the pair a[i]a[i] and a[i-1]a[i−1] where, a[i] > a[i-1]a[i]>a[i−1]. Thus, all numbers to the right of a[i-1]a[i−1] were already sorted in descending order. Furthermore, swapping a[i-1]a[i−1] and a[j]a[j] didn't change that order. Therefore, we simply need to reverse the numbers following a[i-1]a[i−1] to get the next smallest lexicographic permutation.

The following animation will make things clearer:

Next Permutation

Java

public class Solution {
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
Complexity Analysis

Time complexity : O(n)O(n). In worst case, only two scans of the whole array are needed.

Space complexity : O(1)O(1). No extra space is used. In place replacements are done.
*/





/*Improve with Binary Search

    Idea:
    1. Reverse find first number which breaks descending order.
    2. Exchange this number with the least number that's greater than this number.
    3. Reverse sort the numbers after the exchanged number.


public class Solution {
    public void nextPermutation(int[] nums) {
        //1. Reverse find first number which breaks descending order.
        int i=nums.length-1;
        for(; i>=1; i--)
            if(nums[i-1]<nums[i]) break;

        //if no break found in step 1
        if(i==0){
            //for case "1" and "1111"
            if(nums.length==1 || nums[0]==nums[1]) return;
            //for case "54321"
            int lo=i, hi=nums.length-1;
            while(lo<hi) swap(nums, lo++, hi--);
            return;
        }

        //2. Exchange this number with the least number that's greater than this number.
        //2.1 Find the least number that's greater using binary search, O(log(nums.length-i))
        int j = binarySearchLeastGreater(nums, i, nums.length-1, nums[i-1]);

        //2.2 Exchange the numbers
        if(j!=-1) swap(nums, i-1, j);

        //3. Reverse sort the numbers after the exchanged number.
        int lo=i, hi=nums.length-1;
        while(lo<hi) swap(nums, lo++, hi--);
    }

    public int binarySearchLeastGreater(int[] nums, int lo, int hi, int key){
        while(lo<=hi){
            int mid = lo + (hi-lo)/2;
            if(nums[mid]>key){
                lo = mid+1;
            } else {
                hi = mid-1;
            }
        }
        return hi;
    }

    public void swap(int[] nums, int i, int j){
        int tmp = nums[j];
        nums[j] = nums[i];
        nums[i] = tmp;
    }
}
*/
