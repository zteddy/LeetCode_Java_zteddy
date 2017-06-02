public class Solution {
    public int largestRectangleArea(int[] heights) {
        if(heights.length == 0) return 0;
        int maxArea = Integer.MIN_VALUE;
        int width = 0;
        //ArrayList <ArrayList<Integer>> dp = new ArrayList<>(); 好像这样超时了
        ArrayList dp = new ArrayList<>();
        for(int i = 0; i < heights.length; i++){
            //ArrayList<Integer> temp = new ArrayList<>();
            //int[] temp = new int[heights[i]]; //这样又out of memory了，还得压缩空间
            int h = 0;
            if(i == 0) h = heights[i];
            else h = Math.min(heights[i], heights[i-1]);

            int[] temp = new int[h];

            for(int j = 0; j < heights[i]; j++){
                if(i > 0){
                    //if(dp.get(i-1) != null){
                    //if(j < ((int[])dp.get(i-1)).length){
                    if(j < heights[i-1]){
                        if(j < ((int[])dp.get(i-1)).length){
                            width = ((int[])dp.get(i-1))[j]+1;
                        }
                        else
                            width = 2;
                    }
                    //}
                    else {width = 1;}
                }
                else {width = 1;}
                if(j < h) temp[j] = width;
                maxArea = Math.max(maxArea, width*(j+1));
            }
            dp.add(temp);
        }

        return Math.max(maxArea, 0);
    }
}

//TLE 还是被dp这个思路给限制住了，想想真正要存多少东西？

//Using a stack to maxWidth
https://leetcode.com/articles/largest-rectangle-histogram/
Approach #5 (Using Stack) [Accepted]

Algorithm

In this approach, we maintain a stack. Initially, we push a -1 onto the stack to mark the end. We start with the leftmost bar and keep pushing the current bar's index onto the stack until we get two successive numbers in descending order, i.e. until we get a[i]a[i]. Now, we start popping the numbers from the stack until we hit a number stack[j]stack[j] on the stack such that a\big[stack[j]\big] \leq a[i]a[stack[j]]≤a[i]. Every time we pop, we find out the area of rectangle formed using the current element as the height of the rectangle and the difference between the the current element's index pointed to in the original array and the element stack[top-1] -1stack[top−1]−1 as the width i.e. if we pop an element stack[top]stack[top] and i is the current index to which we are pointing in the original array, the current area of the rectangle will be considered as (i-stack[top-1]-1) \times a\big[stack[top]\big](i−stack[top−1]−1)×a[stack[top]].

Further, if we reach the end of the array, we pop all the elements of the stack and at every pop, this time we use the following equation to find the area: (stack[top]-stack[top-1]) \times a\big[stack[top]\big](stack[top]−stack[top−1])×a[stack[top]], where stack[top]stack[top] refers to the element just popped. Thus, we can get the area of the of the largest rectangle by comparing the new area found everytime.
Java

public class Solution {
    public int largestRectangleArea(int[] heights) {
        Stack < Integer > stack = new Stack < > ();
        stack.push(-1);
        int maxarea = 0;
        for (int i = 0; i < heights.length; ++i) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])
                maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
            stack.push(i);
        }
        int lastIndex = stack.peek();
        while (stack.peek() != -1)
            maxarea = Math.max(maxarea, heights[stack.pop()] * (lastIndex - stack.peek()));
        return maxarea;
    }
}

Complexity Analysis

Time complexity : O(n)O(n). nn numbers are pushed and popped.

Space complexity : O(n)O(n). Stack is used.
