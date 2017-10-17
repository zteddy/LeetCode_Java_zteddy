class Solution {
    public int kEmptySlots(int[] flowers, int k) {
        int[] date = new int[flowers.length];
        for(int i = 0; i < flowers.length; i++){
            date[flowers[i]-1] = i;
        }
        if(k >= flowers.length) return -1;

        int i = 0;
        int j = 0;

        PriorityQueue<Integer> heap = new PriorityQueue<>();

        for(j = 1; j < k+1; j++) heap.offer(date[j]);

        int result = Integer.MAX_VALUE;


        while(j < flowers.length){
            // System.out.println(j);
            // System.out.println(heap);
            int sideMax = Math.max(date[i],date[j]);
            if(heap.size() == 0 || heap.peek() > sideMax)
                result = Math.min(result,sideMax+1);
            i++;
            if(heap.size() != 0 ){
                heap.remove(date[i]);
                heap.offer(date[j]);
            }
            j++;
        }

        return result==Integer.MAX_VALUE?-1:result;
    }
}





/*666
It seems that this question has some mistakes. I think there are two places that might lead to misunderstandings: (please feel free to tell me if I'm incorrect)

flowers[i] = x should mean that the unique flower that blooms at day i+1 (not i) will be at position x.
If you can get multiple possible results, then you need to return the minimum one.
The idea is to use an array days[] to record each position's flower's blooming day. That means days[i] is the blooming day of the flower in position i+1. We just need to find a subarray days[left, left+1,..., left+k-1, right] which satisfies: for any i = left+1,..., left+k-1, we can have days[left] < days[i] && days[right] < days[i]. Then, the result is max(days[left], days[right]).

Java version:

public int kEmptySlots(int[] flowers, int k) {
    int[] days =  new int[flowers.length];
    for(int i=0; i<flowers.length; i++)days[flowers[i] - 1] = i + 1;
    int left = 0, right = k + 1, res = Integer.MAX_VALUE;
    for(int i = 0; right < days.length; i++){
        if(days[i] < days[left] || days[i] <= days[right]){
            if(i == right)res = Math.min(res, Math.max(days[left], days[right]));   //we get a valid subarray
            left = i;
            right = k + 1 + i;
        }
    }
    return (res == Integer.MAX_VALUE)?-1:res;
}
*/



/*Using Mono-queue  比如 1 10 11 13 2， 当2来的时候，保存10 11 13 是没有意义的，因为只要2还在window里，10 11 12就不可能是min
Solution 2: Maintain a window using mono-queue, the queue dayMin is an ascending queue that put the day# with minimum days in the first element of queue. The space complexity is O(n) and time complexity is O(n). However, the solution is slower on the OJ test cases compared to method 1. Probably due to the small test samples and extra overhead for maintaining the queue.

class Solution {
    public int kEmptySlots(int[] flowers, int k) {
        if (flowers == null || flowers.length == 0 || k + 2 > flowers.length) {
            return -1;
        }
        int n = flowers.length;
        int[] days = new int[n];
        for (int i = 0; i < n; i++) {
            int index = flowers[i] - 1;
            days[index] = i + 1;
        }
        int res = Integer.MAX_VALUE;
        // Create an ascending queue
        Deque<Integer> dayMin = new ArrayDeque<>();
        for (int i = 1; i < k + 1; i++) {
            while (!dayMin.isEmpty() && days[dayMin.peekLast()] > days[i]) {
                dayMin.removeLast();
            }
            dayMin.addLast(i);
        }
        for (int i = k + 1; i < n; i++) {
            int max = Math.max(days[i - k - 1], days[i]);
            if (k == 0) {
                res = Math.min(res, max);
                continue;
            }
            if (max < days[dayMin.peekFirst()]) {
                res = Math.min(res, max);
            }
            // retire expired element
            if (dayMin.peekFirst() == i - k) {
                dayMin.removeFirst();
            }
            while (!dayMin.isEmpty() && days[dayMin.peekLast()] > days[i]) {
                dayMin.removeLast();
            }
            dayMin.add(i);
        }
        return (res == Integer.MAX_VALUE) ? -1 : res;
    }
}
*/




/*Summary
Approach #1: Insert Into Sorted Structure [Accepted]

Intuition

Let's add flowers in the order they bloom. When each flower blooms, we check it's neighbors to see if they can satisfy the condition with the current flower.

Algorithm

We'll maintain active, a sorted data structure containing every flower that has currently bloomed. When we add a flower to active, we should check it's lower and higher neighbors. If some neighbor satisfies the condition, we know the condition occurred first on this day.

Python

class Solution(object):
    def kEmptySlots(self, flowers, k):
        active = []
        for day, flower in enumerate(flowers, 1):
            i = bisect.bisect(active, flower)
            for neighbor in active[i-(i>0):i+1]:
                if abs(neighbor - flower) - 1 == k:
                    return day
            active.insert(i, flower)
        return -1
Java

class Solution {
    public int kEmptySlots(int[] flowers, int k) {
        TreeSet<Integer> active = new TreeSet();
        int day = 0;
        for (int flower: flowers) {
            day++;
            active.add(flower);
            Integer lower = active.lower(flower)
            Integer higher = active.higher(flower);
            if (lower != null && flower - lower - 1 == k ||
                    higher != null && higher - flower - 1 == k)
                return day;
        }
        return -1;
    }
}

Okay, I will add explain myself.

First understand the representation of array(non zero indexed)
[1,3,2] means on, day 1, flower will bloom at index 1.
On day 2, flower will bloom at index 3.
On day 3, flower will bloom at index 2.
So basically this array holds days as index, and value as position the flower will bloom.

This representation will make the problem simpler to understand now.
So now consider example - [4,1,3,5,2].
Day 1 : [0,0,0,B,0] - flower at index 4 bloom : setContains : [4]
Day 2 : [B,0,0,B,0] - flower at index 1 bloom : setContains : [1,4] : k = 2
Day 3 : [B,0,B,B,0] - flower at index 3 bloom : setContains : [1,3,4] : k = 1, k = 0
Day 4 : [B,0,B,B,B] - flower at index 5 bloom : setContains : [1,3,4,5] : k = 0
Day 5 : [B,B,B,B,B] - flower at index 2 bloom : setContains : [1,2,3,4,5] : k = 0

On day 2, when you add 1, set has [1,4]. This means that there are no flowers that bloom in between these two positions. (which is 2,3). So two flowers.(k=2)
On day 3, when you add 3, set has [1,3,4]. This means that there are no flowers that bloom in between 1-3, and 3-4. (k = 1, and k = 0)

Similarly on day 4, when you add 5, position to left is 4. So k = 0. and so on.

So every day when a flower blooms, we just need to find out it's left and right bloomed flowers. All the flowers in between is guaranteed to be no-bloom.

Definition of set.higher : Returns the least element in this set strictly greater than the given element, or null if there is no such element.
Definition of set.lower : Returns the greatest element in this set strictly less than the given element, or null if there is no such element

Complexity Analysis

Time Complexity (Java): O(N \log N)O(NlogN), where NN is the length of flowers. Every insertion and search is O(\log N)O(logN).

Time Complexity (Python): O(N^2)O(N
​2
​​ ). As above, except list.insert is O(N)O(N).

Space Complexity: O(N)O(N), the size of active.

Approach #2: Sliding Window [Accepted]

Intuition

For each contiguous block ("window") of k positions in the flower bed, we know it satisfies the condition in the problem statement if the minimum blooming date of this window is larger than the blooming date of the left and right neighbors.

Because these windows overlap, we can calculate these minimum queries more efficiently using a sliding window structure.

Algorithm

Let days[x] = i be the time that the flower at position x blooms. For each window of k days, let's query the minimum of this window in (amortized) constant time using a MinQueue, a data structure built just for this task. If this minimum is larger than it's two neighbors, then we know this is a place where "k empty slots" occurs, and we record this candidate answer.

To operate a MinQueue, the key invariant is that mins will be an increasing list of candidate answers to the query MinQueue.min.

For example, if our queue is [1, 3, 6, 2, 4, 8], then mins will be [1, 2, 4, 8]. As we MinQueue.popleft, mins will become [2, 4, 8], then after 3 more popleft's will become [4, 8], then after 1 more popleft will become [8].

As we MinQueue.append, we should maintain this invariant. We do it by popping any elements larger than the one we are inserting. For example, if we appended 5 to [1, 3, 6, 2, 4, 8], then mins which was [1, 2, 4, 8] becomes [1, 2, 4, 5].

Note that we used a simpler variant of MinQueue that requires every inserted element to be unique to ensure correctness. Also, the operations are amortized constant time because every element will be inserted and removed exactly once from each queue.

Python

from collections import deque
class MinQueue(deque):
    def __init__(self):
        deque.__init__(self)
        self.mins = deque()

    def append(self, x):
        deque.append(self, x)
        while self.mins and x < self.mins[-1]:
            self.mins.pop()
        self.mins.append(x)

    def popleft(self):
        x = deque.popleft(self)
        if self.mins[0] == x:
            self.mins.popleft()
        return x

    def min(self):
        return self.mins[0]

class Solution(object):
    def kEmptySlots(self, flowers, k):
        days = [0] * len(flowers)
        for day, position in enumerate(flowers, 1):
            days[position - 1] = day

        window = MinQueue()
        ans = len(days)

        for i, day in enumerate(days):
            window.append(day)
            if k <= i < len(days) - 1:
                window.popleft()
                if k == 0 or days[i-k] < window.min() > days[i+1]:
                    ans = min(ans, max(days[i-k], days[i+1]))

        return ans if ans <= len(days) else -1
Java

class Solution {
    public int kEmptySlots(int[] flowers, int k) {
        int[] days = new int[flowers.length];
        for (int i = 0; i < flowers.length; i++) {
            days[flowers[i] - 1] = i + 1;
        }

        MinQueue<Integer> window = new MinQueue();
        int ans = days.length;

        for (int i = 0; i < days.length; i++) {
            int day = days[i];
            window.addLast(day);
            if (k <= i && i < days.length - 1) {
                window.pollFirst();
                if (k == 0 || days[i-k] < window.min() && days[i+1] < window.min()) {
                    ans = Math.min(ans, Math.max(days[i-k], days[i+1]));
                }
            }
        }

        return ans < days.length ? ans : -1;
    }
}

class MinQueue<E extends Comparable<E>> extends ArrayDeque<E> {
    Deque<E> mins;

    public MinQueue() {
        mins = new ArrayDeque<E>();
    }

    @Override
    public void addLast(E x) {
        super.addLast(x);
        while (mins.peekLast() != null &&
                x.compareTo(mins.peekLast()) < 0) {
            mins.pollLast();
        }
        mins.addLast(x);
    }

    @Override
    public E pollFirst() {
        E x = super.pollFirst();
        if (x == mins.peekFirst()) mins.pollFirst();
        return x;
    }

    public E min() {
        return mins.peekFirst();
    }
}
Complexity Analysis

Time Complexity: O(N)O(N), where NN is the length of flowers. In enumerating through the O(N)O(N) outer loop, we do constant work as MinQueue.popleft and MinQueue.min operations are (amortized) constant time.

Space Complexity: O(N)O(N), the size of our window.
*/





/*Using Bucket Sort?
The point of the question is how to quickly check whether there are any flowers within k. We can use bucket with width of k to check this in constant time by just checking the 2 next buckets.

class Solution {
    public int kEmptySlots(int[] flowers, int k) {
        if (flowers == null || k < 0)
            return 0;
        int n = flowers.length;
        int[] minArr = null;
        int[] maxArr = null;
        minArr = new int[(k != 0 ? n / k : n) + 1];
        maxArr = new int[(k != 0 ? n / k : n) + 1];
        int day = 1;
        for (int x : flowers) {
            int index = k != 0 ? x / k : x;
            minArr[index] = Math.min(minArr[index] != 0? minArr[index] : x, x);
            maxArr[index] = Math.max(maxArr[index] != 0? maxArr[index] : x, x);

            if (index + 2 < minArr.length && minArr[index + 2] != 0 && minArr[index + 2] - maxArr[index] == k + 1 && minArr[index + 1] == 0)
                return day;
            if (index + 1 < minArr.length && minArr[index + 1] != 0 && minArr[index + 1] - maxArr[index] == k + 1)
                return day;

            if (index - 2 >= 0 && maxArr[index - 2] != 0 && minArr[index] - maxArr[index - 2] == k + 1 && maxArr[index - 1] == 0)
                return day;

            if (index - 1 >= 0 && maxArr[index - 1] != 0 && minArr[index] - maxArr[index - 1] == k + 1)
                return day;

            day++;
        }

        return -1;
    }
}
*/




/*Summary 2
There are a bunch of solutions for this problem, which in general fall into two categories: process the data day by day or process the data slot by slot. These are actually the two perspectives for solving this type of problems. The former is equivalent to iterating over time while the latter equivalent to iterating over position (the (time, position) combination can be replaced by other duos, such as (w, h) in lc354 , or (t, d) in lc630).

Since the input array is given in terms of time (i.e., data are arranged in ascending days), it's most natural to go with the first perspective, so its solutions will be explained first. Then we will take a different view of the problem and see how we can obtain better solutions by switching to the second perspective.

I -- Iterate over time

For notational purpose, let's denote the input array as flowers with length of n. As shown in the problem description, flowers[i] stands for the position of the flower that will bloom in the (i + 1)-th day. Now given an integer k, we are required to output the first day such that there exists two flowers in the status of blooming, and the number of flowers between them is k and these flowers are not blooming.

Side notes:

The array index i starts from 0 while the count of the day starts from 1, so we have the (i + 1)-th day notation.

Though not specified explicitly in the problem description, if there are multiple days that satisfy the above condition, we are expected to return the first such day.

Per our definition above, for this perspective, the flowers array will be scanned linearly from left to right, corresponding to iterating over time. Assume we are currently on the (i + 1)-th day. Let xc = flowers[i], xl = xc - (k + 1), xr = xc + (k + 1), it's straightforward to show that if any of the following two statements is true, the current day will satisfy the aforementioned condition (and vice versa):

xl is a valid slot; the flower at xl is blooming; all flowers between xl and xc are not blooming.

xr is a valid slot; the flower at xr is blooming; all flowers between xc and xr are not blooming.

Take statement 1 as an example (analyses of statement 2 are similar). Checking whether xl is a valid slot and whether the flower at xl is blooming is easy. Since if a flower is in blooming status, it must have been visited before the current day. We may maintain a collection of the positions of visited flowers and see if xl belongs to the collection. The tricky part is how to tell whether those flowers between xl and xc are blooming or not.

The naive way would be testing these flowers one by one, which will yield at best the O(nk) solution. This, to no surprise, will be met with TLE. Therefore we have to come up with a relatively faster way to do the range testing. Fortunately there is one observation to our advantage: the order of the positions of visited flowers in the collection does not matter so we are free to arrange them in a way that range testing can be done easily. And a naive try would be sorting, which turns out to be working pretty nicely.

So assume the collection is sorted in ascending order according to the positions of the visited flowers. We have multiple ways to check if any flower between xl and xc is blooming. For example, we can find the first blooming flower to the left of xc and check if it is the same as xl; or we can count the total numbers of flowers in blooming status whose position is no more than xl and no more than xc, respectively, and see if the former is equal to the latter minus one, and so forth. All these can be done in O(logn) time, a big improvement from the naive linear scan method.

One more point worth noting is that the collection is expanding as more and more flowers are visited (i.e., the searching space is dynamic). So we have to strike a balance between searching and insertion, which prompts use of data structures like balanced binary search tree (BST) or binary indexed tree (BIT). Here are the two corresponding solutions, both of which run at O(nlogn) time with O(n) space.

1. BST-based solution

For this problem, we don't have to design our own balanced BST. Instead we can take advantage of the built-in TreeSet of Java (or equivalent type in other languages), thanks to the fact that there are no duplicates of the positions in the collection. The range testing can be done using the first method I mentioned above: find the first blooming flower to the left of xc and check if it is the same as xl. Here is the Java program:

public int kEmptySlots(int[] flowers, int k) {
    TreeSet<Integer> set = new TreeSet<>();

    for (int i = 0; i < flowers.length; i++) {
        int xc = flowers[i], xl = xc - (k + 1), xr = xc + (k + 1);

        Integer l = set.lower(xc);
        if (l != null && l == xl) return i + 1;

        Integer r = set.higher(xc);
        if (r != null && r == xr) return i + 1;

        set.add(xc);
    }

    return -1;
}
2. BIT-based solution

Here we need two arrays: one to keep track of visited flowers, the other to serve as the BIT so we can count the total numbers of flowers in blooming status whose position is no more than xl, xc and xr, respectively. Here is the Java program:

public int kEmptySlots(int[] flowers, int k) {
    int n = flowers.length;
    boolean[] visited = new boolean[n + 1];
    int[] bit = new int[n + 1];

    for (int i = 0; i < n; i++) {
        int xc = flowers[i], xl = xc - (k + 1), xr = xc + (k + 1);

        insert(bit, xc);
        visited[xc] = true;

        int xc_cnt = search(bit, xc), xl_cnt = xc_cnt - 1, xr_cnt = xc_cnt + 1;

        if (xl > 0 && visited[xl] && search(bit, xl) == xl_cnt) return i + 1;
        if (xr <= n && visited[xr] && search(bit, xr) == xr_cnt) return i + 1;
    }

    return -1;
}

private int search(int[] bit, int i) {
    int sum = 0;
    for (; i > 0; i -= i & -i) sum += bit[i];
    return sum;
}

private void insert(int[] bit, int i) {
    for (; i < bit.length; i += i & -i) bit[i] += 1;
}
Final remarks for this perspective: it is relatively easy to come up with O(nlogn) solutions, if you have experience with BST and BIT before, though O(n)solution is possible. Next we will shift our focus and take the other perspective to see if we can further improve the time performance here.

II -- Iterate over position

For this perspective, we need to first transform our input array flowers into another array days so that days[i] represents the day on which the flower at position i + 1 will bloom (note again index i starts from 0). Now given an integer k, we are required to output the first day such that there exists two flowers in the status of blooming, and the number of flowers between them is k and these flowers are not blooming.

What is the difference between this perspective and the previous one? The answer is: all the candidate ranges of position of the flowers are readily known and are arranged in ascending order. Let [j, i] represent flowers from position j + 1 to position i + 1 (both inclusive). Then [j, i] is a candidate range if j = i - (k + 1). This is because if we can determine that all flowers from positions j + 2 to i will bloom after both flowers at position j + 1 and position i + 1, let d = max(days[i], days[j]), then d will be a day that satisfies the aforementioned condition (though may not be the first such day) and thus qualifies as a candidate day for the final answer. We just need to choose the smallest one from all these candidate days.

So how do we check if all flowers from positions j + 2 to i will bloom after both flowers at position j + 1 and i + 1? The solution is simple: of all the flowers from positions j + 2 to i, find the one that will bloom first and denote the day on which it blooms as d_min, then compare d_min with d. If d_min > d, then d is a valid candidate day as specified above; otherwise it is not. Again, we will have multiple ways to implement this idea.

1. PriorityQueue-based solution

A straightforward way to keep track of minimum blooming day of the flowers from positions j + 2 to i would be using a priority queue. Also since the positions are changing as the candidate ranges are shifting, the priority queue should store the positions of the flowers instead of blooming days so that we can get rid of invalid positions easily. Here is the Java program, which runs at O(nlogn) time with O(n) space:

public int kEmptySlots(int[] flowers, int k) {
    int n = flowers.length, res = n + 1;
    int[] days = new int[n];

    for (int i = 0; i < n; i++) {
        days[flowers[i] - 1] = i + 1;
    }

    PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
        public int compare(Integer i, Integer j) {
            return Integer.compare(days[i], days[j]);
        }
    });

    for (int i = 0, j = i - (k + 1); i < n; i++, j++) {
        while (!pq.isEmpty() && pq.peek() <= j) pq.poll();

        if (j >= 0 && (pq.isEmpty() || days[pq.peek()] > Math.max(days[i], days[j]))) {
            res = Math.min(res, Math.max(days[i], days[j]));
        }

        pq.offer(i);
    }

    return (res > n ? -1 : res);
}
2. Deque-based solution

It turned out that the blooming days of the flowers within the candidate range can be maintained in descending order using a double-ended queue (deque), The key here is to get rid of positions with blooming days larger than that of the current position before adding it to the deque from the left (this is because as long as the current position is in the deque, the position with minimum blooming day cannot be these removed positions). Each position will be pushed into and popped out from the deque once, so the overall time complexity will be O(n). Here is the Java program, where I used an array to serve as the deque with l and r as its left and right boundaries (the built-in Deque seems to be slower than this home-made version):

public int kEmptySlots(int[] flowers, int k) {
    int n = flowers.length, l = n, r = n - 1, res = n + 1;
    int[] days = new int[n];
    int[] deque = new int[n];

    for (int i = 0; i < n; i++) {
        days[flowers[i] - 1] = i + 1;
    }

    for (int i = 0, j = i - (k + 1); i < n; i++, j++) {
        while (l <= r && deque[r] <= j) r--;

        if (j >= 0 && (r < l || days[deque[r]] > Math.max(days[i], days[j]))) {
            res = Math.min(res, Math.max(days[i], days[j]));
        }

        while (l <= r && days[i] <= days[deque[l]]) l++; // Get rid of larger days before adding

        deque[--l] = i;
    }

    return (res > n ? -1 : res);
}
3. No-queue solution

To see how we can get this no-queue solution (more info here), let's take a look back at the two queue-based solutions above. The downside of those solutions is that we have to check all candidate ranges one by one, which turns out to be unnecessary. For example, if [j, i] is the current candidate range, min is the position corresponding to the minimum blooming day d_min in the queue such that d_min < days[j], then all candidate ranges [j', i'] with j < j' < min can be skipped, because the blooming day of the left boundary days[j'] will always be greater than d_min and thus cannot be valid ranges.

So instead of testing all the candidate ranges one by one, we set up a target range and try to validate it by doing a linear scan, then update the target range according to the validation result. In the following Java program, the target range is denoted as [l, r], with l and r as its left and right boundaries. di, dl and dr are the blooming days for position i + 1, 'l + 1' and r + 1, respectively. To validate the target range, we need to compare di with dl and dr. The target range will be invalid if di < dl or di < dr, and we can skip some of the candidate ranges to reset the target range to [i, i + k + 1]. On the other hand, if the target range is valid, i will eventually be equal to r (or di will be equal to dr), and we need to update the final result (to find the minimum day) as well as set up a new target range. The two cases can be combined into one as shown below:

public int kEmptySlots(int[] flowers, int k) {
    int n = flowers.length, res = n + 1;
    int[] days = new int[n];

    for (int i = 0; i < n; i++) {
        days[flowers[i] - 1] = i + 1;
    }

    for (int i = 1, l = 0, r = k + 1; i < n && r < n; i++) {
        int di = days[i], dl = days[l], dr = days[r];

        if (di < dl || di <= dr) {
            if (di == dr) res = Math.min(res, Math.max(dl, dr)); // target range is valid so update final result
            l = i;
            r = i + (k + 1);
        }
    }

    return (res > n ? -1 : res);
}
Final remarks: this type of problems typically exhibit "symmetric" features in the input data set. For this problem, we have equivalent perspectives of the input data as either flowers or days. Similar situations can be found for lc354 and lc630. However, in the presence of extra restrictions, this symmetry may be broken and solutions may favor one perspective over the other. In such cases, it would be advisable to make attempts from both perspectives and choose the one that suits you best.
*/
