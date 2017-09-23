class Solution {
    public int[] productExceptSelf(int[] nums) {

    }
}


//WA
//这更像是道智力题





//Left than Right
public class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            res[i] *= right;
            right *= nums[i];
        }
        return res;
    }
}

Thank @lycjava3 for this smart solution. To understand it easily, let me explain it with an example.

Given numbers [2, 3, 4, 5], regarding the third number 4, the product of array except 4 is 2*3*5 which consists of two parts: left 2*3 and right 5. The product is left*right. We can get lefts and rights:

Numbers:     2    3    4     5
Lefts:            2  2*3 2*3*4
Rights:  3*4*5  4*5    5
Let’s fill the empty with 1:

Numbers:     2    3    4     5
Lefts:       1    2  2*3 2*3*4
Rights:  3*4*5  4*5    5     1
We can calculate lefts and rights in 2 loops. The time complexity is O(n).

We store lefts in result array. If we allocate a new array for rights. The space complexity is O(n). To make it O(1), we just need to store it in a variable which is right in @lycjava3’s code.

Clear code with comments:

public class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        // Calculate lefts and store in res.
        int left = 1;
        for (int i = 0; i < n; i++) {
            if (i > 0)
                left = left * nums[i - 1];
            res[i] = left;
        }
        // Calculate rights and the product from the end of the array.
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1)
                right = right * nums[i + 1];
            res[i] *= right;
        }
        return res;
    }
}





//One Pass
One pass, if dont count the initialization of the 'result'...

    int[] result = new int[nums.length];
    for (int i = 0; i < result.length; i++) result[i] = 1;
    int left = 1, right = 1;
    for (int i = 0, j = nums.length - 1; i < nums.length - 1; i++, j--) {
        left *= nums[i];
        right *= nums[j];
        result[i + 1] *= left;
        result[j - 1] *= right;
    }
    return result;
edit 2016/04/05:

EXPLAINATION:

Thinking of the 'nums' array [1, 2, 3, 4, 5, 6], and the 'result' array [1, 1, 1, 1, 1, 1]. Every number in 'nums' will be multiplied in 'result' array except itself, then we will get the map below:

  1 2 3 4 5 6
  -----------
1|  1 1 1 1 1
2|2   2 2 2 2
3|3 3   3 3 3
4|4 4 4   4 4
5|5 5 5 5   5
6|6 6 6 6 6

(horizontal axis is nums array, vertical axis is multiplied times)
Noticed the regular pattern of the upper triangular and lower triangular. Using integers to store the products of the lower and upper triangulars, then we can do it in one pass:

i : left index of the nums array
j : right index of the nums array
left : left products multiplied from nums[0] to nums[i].
right : right products multiplied from nums[j] to nums[nums.length - 1].
We multiply left to result[i + 1] ((i, i + 1) in the uppper triangular),

and multiply right to result[j - 1] ((j, j - 1) in the lower triangular),

finally we have calculated the products of the nums except current.

Sorry for my poor English...= =!
Checking more of my solutions at: https://github.com/dss886/LeetCode/tree/master/src/leetcode
