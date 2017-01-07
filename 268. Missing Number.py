class Solution(object):
    def missingNumber(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        x = 0
        for i in nums:
            x ^= i
        for i in range(len(nums)+1):
            x ^= i
        return x

"""3 ideas
1.XOR
public int missingNumber(int[] nums) { //xor
    int res = nums.length;
    for(int i=0; i<nums.length; i++){
        res ^= i;
        res ^= nums[i];
    }
    return res;
}
2.SUM
public int missingNumber(int[] nums) { //sum
    int len = nums.length;
    int sum = (0+len)*(len+1)/2;
    for(int i=0; i<len; i++)
        sum-=nums[i];
    return sum;
}

We can do SUM this way to avoid overflow.

public class Solution {
    public int missingNumber(int[] nums) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++){
            sum += i;
            sum -= nums[i];
        }
        sum += nums.length;
        return sum;
    }
}


3.Binary Search
public int missingNumber(int[] nums) { //binary search
    Arrays.sort(nums);
    int left = 0, right = nums.length, mid= (left + right)/2;
    while(left<right){
        mid = (left + right)/2;
        if(nums[mid]>mid) right = mid;
        else left = mid+1;
    }
    return left;
}
Summary:
If the array is in order, I prefer Binary Search method. Otherwise, the XOR method is better.
"""

"""Explaination of xor
The basic idea is to use XOR operation. We all know that a^b^b =a, which means two xor operations with the same number will eliminate the number and reveal the original number.
In this solution, I apply XOR operation to both the index and value of the array. In a complete array with no missing numbers, the index and value should be perfectly corresponding( nums[index] = index), so in a missing array, what left finally is the missing number.

public int missingNumber(int[] nums) {

    int xor = 0, i = 0;
    for (i = 0; i < nums.length; i++) {
        xor = xor ^ i ^ nums[i];
    }

    return xor ^ i;
}
"""


"""Using swapping
1.swapping number solution

public int MissingNumber(int[] nums) {
    for(int i = 0; i < nums.Length; i++)
    {
        while(i < nums.Length && nums[i] == i) i++;
        while(i < nums.Length && nums[i] != i)
        {
            if(nums[i] >= nums.Length || nums[i] < 0) break;
            nums[i] = nums[i] ^ nums[nums[i]] ^ (nums[nums[i]] = nums[i]);
        }
    }
    for(int i = 0; i < nums.Length; i++)
        if(nums[i] != i) return i;
    return nums.Length;
}
1.2 Another swapping solution by avoiding the 2nd loop. Idea from novostary.

public int MissingNumber(int[] nums) {
    int lastIndex = nums.Length;
    for(int i = 0; i < nums.Length; )
        if(nums[i] == i) i++;
        else if(nums[i] < nums.Length)
            nums[i] = nums[i] ^ nums[nums[i]] ^ (nums[nums[i]] = nums[i]);
        else lastIndex = i++;
    return lastIndex;
}
"""
