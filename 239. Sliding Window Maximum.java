class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {

        int start = 0;
        int end = 0;
        int max = Integer.MIN_VALUE;

        int maxPointer = 0;
        int p = 0;
        int[] result = new int[nums.length-k+1];

        if(nums.length == 0) return new int[0];

        //init
        for(end = 0; end < k; end++){
            // System.out.println(end);
            if(nums[end] > max){
                max = nums[end];
                maxPointer = end;
                // System.out.println(maxPointer);
            }
        }
        result[p++] = max;
        end--;

        while(end < nums.length-1){
            while(start < maxPointer && end < nums.length-1){
                start++;
                end++;
                // System.out.println(end);
                if(nums[end] > max){
                    max = nums[end];
                    maxPointer = end;
                    // System.out.println(maxPointer);
                }
                result[p++] = max;
            }

            //re-scan
            if(end < nums.length-1){
                start++;
                end++;
                // System.out.println(end);
                max = nums[start];
                for(int i = start; i <= end; i++){
                    if(nums[i] >= max){
                        maxPointer = i;
                        max = nums[i];
                    }
                }
                result[p++] = max;

            }
        }

        return result;
    }
}

//Not O(n) [9,8,7,6,5,4,3,2,1]






/*66666Using Deque
We scan the array from 0 to n-1, keep "promising" elements in the deque. The algorithm is amortized O(n) as each element is put and polled once.

At each i, we keep "promising" elements, which are potentially max number in window [i-(k-1),i] or any subsequent window. This means

If an element in the deque and it is out of i-(k-1), we discard them. We just need to poll from the head, as we are using a deque and elements are ordered as the sequence in the array

Now only those elements within [i-(k-1),i] are in the deque. We then discard elements smaller than a[i] from the tail. This is because if a[x] <a[i] and x<i, then a[x] has no chance to be the "max" in [i-(k-1),i], or any other subsequent window: a[i] would always be a better candidate.

As a result elements in the deque are ordered in both sequence in array and their value. At each step the head of the deque is the max element in [i-(k-1),i]

public int[] maxSlidingWindow(int[] a, int k) {
    if (a == null || k <= 0) {
        return new int[0];
    }
    int n = a.length;
    int[] r = new int[n-k+1];
    int ri = 0;
    // store index
    Deque<Integer> q = new ArrayDeque<>();
    for (int i = 0; i < a.length; i++) {
        // remove numbers out of range k
        while (!q.isEmpty() && q.peek() < i - k + 1) {
            q.poll();
        }
        // remove smaller numbers in k range as they are useless
        while (!q.isEmpty() && a[q.peekLast()] < a[i]) {
            q.pollLast();
        }
        // q contains index... r contains content
        q.offer(i);
        if (i >= k - 1) {
            r[ri++] = a[q.peek()];
        }
    }
    return r;
}
*/




/*6666 Two-Pass
For Example: A = [2,1,3,4,6,3,8,9,10,12,56], w=4

partition the array in blocks of size w=4. The last block may have less then w.
2, 1, 3, 4 | 6, 3, 8, 9 | 10, 12, 56|

Traverse the list from start to end and calculate max_so_far. Reset max after each block boundary (of w elements).
left_max[] = 2, 2, 3, 4 | 6, 6, 8, 9 | 10, 12, 56

Similarly calculate max in future by traversing from end to start.
right_max[] = 4, 4, 4, 4 | 9, 9, 9, 9 | 56, 56, 56

now, sliding max at each position i in current window, sliding-max(i) = max{right_max(i), left_max(i+w-1)}
sliding_max = 4, 6, 6, 8, 9, 10, 12, 56

code:

 public static int[] slidingWindowMax(final int[] in, final int w) {
    final int[] max_left = new int[in.length];
    final int[] max_right = new int[in.length];

    max_left[0] = in[0];
    max_right[in.length - 1] = in[in.length - 1];

    for (int i = 1; i < in.length; i++) {
        max_left[i] = (i % w == 0) ? in[i] : Math.max(max_left[i - 1], in[i]);

        final int j = in.length - i - 1;
        max_right[j] = (j % w == 0) ? in[j] : Math.max(max_right[j + 1], in[j]);
    }

    final int[] sliding_max = new int[in.length - w + 1];
    for (int i = 0, j = 0; i + w <= in.length; i++) {
        sliding_max[j++] = Math.max(max_right[i], max_left[i + w - 1]);
    }

    return sliding_max;
}

This is a nice solution. With some simple comparison, you can see this solution is somewhat equivalent to the deque solution.

Because of the property of sliding window, you can find all these "anchor" points (advocated in OP's example), and then each sliding window can be divided into two parts. The idea is to find the maximum of the left part and the maximum of the right part, and then obtain the maxima by comparing the two maximums. The keen observation is that all the left maximums can be found by a right-to-left transverse, while all the right maximums can be found by a left-to-right transverse.

Now think in the deque solution. When you add the new numbers into the deque from right, you will be comparing that number to the right-most number in the deque. If the new number is larger, then you pop out the right-most number and continue the comparison. This procedure is the same as the left-to-right transverse in OP's solution. Now let's say you continue the aforementioned procedure until you met the first number in the deque that is greater than the new number. You stop and push the new number into your deque. This procedure is similar to the right-to-left transverse in OP's solution.
(The anchor points are determined automatically in deque, as you always pop out points that no longer belong to your sliding window.)

In summary, all the "pop" action in deque solution corresponds to the "left-to-right" transverse in OP's solution and all the "push" action corresponds to the "right-to-left" transverse in OP's solution.
*/




/*More OO Deque
Sliding window minimum/maximum = monotonic queue. I smelled the solution just when I read the title.
This is essentially same idea as others' deque solution, but this is much more standardized and modulized. If you ever need to use it in your real product, this code is definitely more preferable.

What does Monoqueue do here:

It has three basic options:

push: push an element into the queue; O (1) (amortized)

pop: pop an element out of the queue; O(1) (pop = remove, it can't report this element)

max: report the max element in queue;O(1)

It takes only O(n) time to process a N-size sliding window minimum/maximum problem.

Note: different from a priority queue (which takes O(nlogk) to solve this problem), it doesn't pop the max element: It pops the first element (in original order) in queue.

class Monoqueue
{
    deque<pair<int, int>> m_deque; //pair.first: the actual value,
                                   //pair.second: how many elements were deleted between it and the one before it.
    public:
        void push(int val)
        {
            int count = 0;
            while(!m_deque.empty() && m_deque.back().first < val)
            {
                count += m_deque.back().second + 1;
                m_deque.pop_back();
            }
            m_deque.emplace_back(val, count);
        };
        int max()
        {
            return m_deque.front().first;
        }
        void pop ()
        {
            if (m_deque.front().second > 0)
            {
                m_deque.front().second --;
                return;
            }
            m_deque.pop_front();
        }
};
struct Solution {
    vector<int> maxSlidingWindow(vector<int>& nums, int k) {
        vector<int> results;
        Monoqueue mq;
        k = min(k, (int)nums.size());
        int i = 0;
        for (;i < k - 1; ++i) //push first k - 1 numbers;
        {
            mq.push(nums[i]);
        }
        for (; i < nums.size(); ++i)
        {
            mq.push(nums[i]);            // push a new element to queue;
            results.push_back(mq.max()); // report the current max in queue;
            mq.pop();                    // pop first element in queue;
        }
        return results;
    }
};
*/




/*Uisng two stacks
The idea of the solution is to maintain two stacks: s1 and s2. We hope s1's peek is always to keep the largest value in the current k numbers. So we use s1 to store the numbers whose index is in increasing order and values is in decreasing order. For example give k=4 numbers 1, 3 ,9, 6. s1 only need to store 9 and 6 with 9 is on the peek of the stack.

When we slide the window, we need to keep dumping the numbers at the left end of the window and adding new numbers on the right end of the window. When we have a new number in the window, we push just the new number in stack s2. Also, we keep record the largest value in s2. If the peek of s1 is smaller than the largest value in s2, it means the largest value in the current k numbers is in s2. Thus, we need to empty s1 and move the elements stored in s2 to s1. Note that we do not need to move all the elements in s2 to s1. Only the numbers whose index is in increasing order and values is in decreasing order are pushed into s1.

For example,
1,3,9,6,7,1, 2 , 5 given k=4

step 1: window 1,3,9,6 s1: 9, 6 s2:empty maxInStack2=Integer.MIN_VALUE;

step 2: window 3,9,6,7 s1: 9,6 s2:7 maxInStack2=7

step3: window 9,6,7,1 s1: 9, 6 s2:7,1 maxInStack2=7

step4: window 6,7,1, 2 note 9 is removed from window, so s1: 6 s2: 7, 1 ,2 maxInStack2=7
Then we find that maxInStack2> s1.peek(). update s1.
After updating s1, we have s1: 7, 2 s2: empty maxInStack2=Integer.MIN_VALUE;

step5: window 7,1,2,5 s1:7,2 s2:5, maxInStack2=5;
In worst case, every number in the array is visited twice. Thus the complexity is O(n)

public class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums==null||nums.length==0) return nums;
        int [] result= new int[nums.length-k+1];
        int maxInStack2=Integer.MIN_VALUE;
        Stack<Integer> s1 =new Stack<Integer>();
        Stack<Integer> s2 =new Stack<Integer>();
        for(int i=k-1;i>=0;i--){
            if(s1.isEmpty()){
                s1.push(nums[i]);
            }else if(nums[i]>=s1.peek()){
                s1.push(nums[i]);
            }
        }
        result[0]=s1.peek();
        for(int i=1;i<result.length;i++){
            int newItem=nums[i+k-1];
            int removeItem=nums[i-1];
            if(removeItem==s1.peek()){
                s1.pop();
            }
            if(newItem>maxInStack2){
                maxInStack2=newItem;
            }
            s2.push(newItem);
            if(s1.isEmpty()||maxInStack2>s1.peek()){
                while(s1.isEmpty()==false){
                    s1.pop();
                }
                while(s2.isEmpty()==false){
                    int temp=s2.pop();
                    if(s1.isEmpty()||temp>=s1.peek()){
                        s1.push(temp);
                    }
                }
                result[i]=maxInStack2;
                maxInStack2=Integer.MIN_VALUE;
            }else{
                result[i]=s1.peek();
            }
        }
        return result;
    }
}
*/
