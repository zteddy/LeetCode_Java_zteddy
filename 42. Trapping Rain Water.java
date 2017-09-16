class Solution {
    public int trap(int[] height) {

        if(height.length == 0 || height.length == 1) return 0;

        int start = 0;
        int end = 1;
        int minusSpace  = 0;

        int result = 0;
        int startHeight = 0;

        //Stage one
        while(end < height.length){
            // startHeight = height[start];
            while(end < height.length && height[end] < height[start]){
                minusSpace += height[end];
                end++;
            }
            if(end < height.length && height[end] >= height[start]){
                result += (end-start-1) * height[start] - minusSpace;
                minusSpace = 0;
                start = end;
                end++;
            }
        }

        //Stage two
        minusSpace = 0;
        if(end == height.length){
            int pin = start;
            start = height.length-2;
            end = height.length-1;
            while(start > pin){
                while(start > pin && height[start] < height[end]){
                    minusSpace += height[start];
                    start--;
                }
                if(start > pin && height[start] >= height[end]){
                    result += (end-start-1) * height[end] - minusSpace;
                    minusSpace = 0;
                    end = start;
                    start--;
                }
            }
        }


        return result;
    }
}






/*Summary
Approach #1 Brute force [Accepted]

Intuition

Do as directed in question. For each element in the array, we find the maximum level of water it can trap after the rain, which is equal to the minimum of maximum height of bars on both the sides minus its own height.

Algorithm

Initialize ans=0ans=0
Iterate the array from left to right:
Initialize max_left=0max_left=0 and max_right=0max_right=0
Iterate from the current element to the beginning of array updating: max_left=max(max_left,height[j])max_left=max(max_left,height[j])
Iterate from the current element to the end of array updating: max_right=max(max_right,height[j])max_right=max(max_right,height[j])
Add min(max_left,max_right)−height[i]min(max_left,max_right)−height[i] to \text{ans}ans
C++

int trap(vector<int>& height)
{
    int ans = 0;
    int size = height.size();
    for (int i = 1; i < size - 1; i++) {
        int max_left = 0, max_right = 0;
        for (int j = i; j >= 0; j--) { //Search the left part for max bar size
            max_left = max(max_left, height[j]);
        }
        for (int j = i; j < size; j++) { //Search the right part for max bar size
            max_right = max(max_right, height[j]);
        }
        ans += min(max_left, max_right) - height[i];
    }
    return ans;
}
Complexity Analysis

Time complexity: O(n^2)O(n
​2
​​ ). For each element of array, we iterate the left and right parts.

Space complexity: O(1)O(1) extra space.

Approach #2 Dynamic Programming [Accepted]

Intuition

In brute force, we iterate over the left and right parts again and again just to find the highest bar size upto that index. But, this could be stored. Voila, dynamic programming.

The concept is illustrated as shown:

Dynamic programming

Algorithm

Find maximum height of bar from the left end upto an index i in the array left_maxleft_max.
Find maximum height of bar from the right end upto an index i in the array right_maxright_max.
Iterate over the \text{height}height array and update ans:
Add min(max_left[i],max_right[i])−height[i]min(max_left[i],max_right[i])−height[i] to ansans
C++

int trap(vector<int>& height)
{
    if(height == null)
        return 0;
    int ans = 0;
    int size = height.size();
    vector<int> left_max(size), right_max(size);
    left_max[0] = height[0];
    for (int i = 1; i < size; i++) {
        left_max[i] = max(height[i], left_max[i - 1]);
    }
    right_max[size - 1] = height[size - 1];
    for (int i = size - 2; i >= 0; i--) {
        right_max[i] = max(height[i], right_max[i + 1]);
    }
    for (int i = 1; i < size - 1; i++) {
        ans += min(left_max[i], right_max[i]) - height[i];
    }
    return ans;
}
Complexity analysis

Time complexity: O(n)O(n).
We store the maximum heights upto a point using 2 iterations of O(n) each.
We finally update \text{ans}ans using the stored values in O(n).

Space complexity: O(n)O(n) extra space.

Additional O(n)O(n) space for left_maxleft_max and right_maxright_max arrays than in Approach #1.
Approach #3 Using stacks [Accepted]

Intuition

Instead of storing the largest bar upto an index as in Approach #2, we can use stack to keep track of the bars that are bounded by longer bars and hence, may store water. Using the stack, we can do the calculations in only one iteration.

We keep a stack and iterate over the array. We add the index of the bar to the stack if bar is smaller than or equal to the bar at top of stack, which means that the current bar is bounded by the previous bar in the stack. If we found a bar longer than that at the top, we are sure that the bar at the top of the stack is bounded by the current bar and a previous bar in the stack, hence, we can pop it and add resulting trapped water to \text{ans}ans.

Algorithm

Use stack to store the indices of the bars.
Iterate the array:
While stack is not empty and \text{height}[current]>\text{height}[st.top()]height[current]>height[st.top()]
It means that the stack element can be popped. Pop the top element as \text{top}top.
Find the distance between the current element and the element at top of stack, which is to be filled. \text{distance} = \text{current} - \text{st.top}() - 1distance=current−st.top()−1
Find the bounded height bounded_height=min(height[current],height[st.top()])−height[top]bounded_height=min(height[current],height[st.top()])−height[top]
Add resulting trapped water to answer ans+=distance∗bounded_heightans+=distance∗bounded_height
Push current index to top of the stack
Move \text{current}current to the next position
C++

int trap(vector<int>& height)
{
    int ans = 0, current = 0;
    stack<int> st;
    while (current < height.size()) {
        while (!st.empty() && height[current] > height[st.top()]) {
            int top = st.top();
            st.pop();
            if (st.empty())
                break;
            int distance = current - st.top() - 1;
            int bounded_height = min(height[current], height[st.top()]) - height[top];
            ans += distance * bounded_height;
        }
        st.push(current++);
    }
    return ans;
}
Complexity analysis

Time complexity: O(n)O(n).
Single iteration of O(n)O(n) in which each bar can be touched at most twice(due to insertion and deletion from stack) and insertion and deletion from stack takes O(1)O(1) time.
Space complexity: O(n)O(n). Stack can take upto O(n)O(n) space in case of stairs-like or flat structure.
Approach #4 Using 2 pointers [Accepted]

Intuition As in Approach #2, instead of computing the left and right parts seperately, we may think of some way to do it in one iteration. From the figure in dynamic programming approach, notice that as long as right_max[i]>left_max[i]right_max[i]>left_max[i](from element 0 to 6), the water trapped depends upon the left_max, and similar is the case when left_max[i]>right_max[i]left_max[i]>right_max[i](from element 8 to 11). So, we can say that if there is a larger bar at one end(say right), we are assured that the water trapped would be dependant on height of bar in current direction(from left to right). As soon as we find the bar at other end(right) is smaller, we start iterating in opposite direction(from right to left). We must maintain left_maxleft_max and right_maxright_max during the iteration, but now we can do it in one iteration using 2 pointers, switching between the two.

Algorithm

Initialize \text{left}left pointer to 0 and \text{right}right pointer to size-1
While \text{left}< \text{right}left<right, do:
If \text{height[left]}height[left] is smaller than \text{height[right]}height[right]
If height[left]>=left_maxheight[left]>=left_max, update left_maxleft_max
Else add left_max−height[left]left_max−height[left] to \text{ans}ans
Add 1 to \text{left}left.
Else
If height[right]>=right_maxheight[right]>=right_max, update right_maxright_max
Else add right_max−height[right]right_max−height[right] to \text{ans}ans
Subtract 1 from \text{right}right.
4 / 11
C++

int trap(vector<int>& height)
{
    int left = 0, right = height.size() - 1;
    int ans = 0;
    int left_max = 0, right_max = 0;
    while (left < right) {
        if (height[left] < height[right]) {
            height[left] >= left_max ? (left_max = height[left]) : ans += (left_max - height[left]);
            ++left;
        }
        else {
            height[right] >= right_max ? (right_max = height[right]) : ans += (right_max - height[right]);
            --right;
        }
    }
    return ans;
}
Complexity analysis

Time complexity: O(n)O(n). Single iteration of O(n)O(n).
Space complexity: O(1)O(1) extra space. Only constant space required for \text{left}left, \text{right}right, left_maxleft_max and right_maxright_max.
Analysis written by @abhinavbansal0.
*/





/*One pass
Traverse one pass with two pointers, from two sides to the middle.

public int trap(int[] A) {
    if (A.length < 3) return 0;

    int ans = 0;
    int l = 0, r = A.length - 1;

    // find the left and right edge which can hold water
    while (l < r && A[l] <= A[l + 1]) l++;
    while (l < r && A[r] <= A[r - 1]) r--;

    while (l < r) {
        int left = A[l];
        int right = A[r];
        if (left <= right) {
            // add volum until an edge larger than the left edge
            while (l < r && left >= A[++l]) {
                ans += left - A[l];
            }
        } else {
            // add volum until an edge larger than the right volum
            while (l < r && A[--r] <= right) {
                ans += right - A[r];
            }
        }
    }
    return ans;
}
*/





/*Concise
Basically this solution runs two pointers from two sides to the middle, and the plank is used to record the height of the elevation within a certain range, plank height can only increase (or remain the same) from two sides to the middle. If the current pointer is pointing at a number that is less than the current plank height, the difference between plank height and the number would be the amount of water trapped. Otherwise, A[i] == plank, no water is trapped.

public class Solution {
    public int trap(int[] A) {
        int i = 0, j = A.length - 1, result = 0, plank = 0;
        while(i <= j){
            plank = plank < Math.min(A[i], A[j]) ? Math.min(A[i], A[j]) : plank;
            result = A[i] >= A[j] ? result + (plank - A[j--]) : result + (plank - A[i++]);
        }
        return result;
    }
}
*/
