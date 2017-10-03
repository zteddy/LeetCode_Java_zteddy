class Solution {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<int[]> result = new ArrayList<>();
        int i = 0, j = 0;
        while(i < nums1.length && j < nums2.length && result.size() < k){
            int[] temp = new int[2];
            temp[0] = nums1[i];
            temp[1] = nums2[j];
            result.add(temp);
            if(i == nums1.length-1){
                j++;
                i = 0;
                continue;
            }
            if(j == nums2.length-1){
                i++;
                j = 0
                continue;
            }
            if(i == j){
                if(nums1[i+1]+nums2[j] > nums1[i]+nums2[j+1]) j++;
                else i++;
            }
            else if(i > j){}

        }
        return result;
    }
}

//WA
//有点晕，以为肯定能以某种方式扫描数组，愣是没找出index变化规律。。。





//Using Heap
https://discuss.leetcode.com/topic/50885/simple-java-o-klogk-solution-with-explanation
Basic idea: Use min_heap to keep track on next minimum pair sum, and we only need to maintain K possible candidates in the data structure.

Some observations: For every numbers in nums1, its best partner(yields min sum) always strats from nums2[0] since arrays are all sorted; And for a specific number in nums1, its next candidate sould be [this specific number] + nums2[current_associated_index + 1], unless out of boundary;)

Here is a simple example demonstrate how this algorithm works.

image

The run time complexity is O(kLogk) since que.size <= k and we do at most k loop.

public class Solution {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> que = new PriorityQueue<>((a,b)->a[0]+a[1]-b[0]-b[1]);
        List<int[]> res = new ArrayList<>();
        if(nums1.length==0 || nums2.length==0 || k==0) return res;
        for(int i=0; i<nums1.length && i<k; i++) que.offer(new int[]{nums1[i], nums2[0], 0});
        while(k-- > 0 && !que.isEmpty()){
            int[] cur = que.poll();
            res.add(new int[]{cur[0], cur[1]});
            if(cur[2] == nums2.length-1) continue;
            que.offer(new int[]{cur[0],nums2[cur[2]+1], cur[2]+1});
        }
        return res;
    }
}





//Record
Because both array are sorted, so we can keep track of the paired index. Therefore, we do not need to go through all combinations when k < nums1.length + num2.length. Time complexity is O(k*m) where m is the length of the shorter array.

public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    List<int[]> ret = new ArrayList<int[]>();
    if (nums1.length == 0 || nums2.length == 0 || k == 0) {
        return ret;
    }

    int[] index = new int[nums1.length];
    while (k-- > 0) {
        int min_val = Integer.MAX_VALUE;
        int in = -1;
        for (int i = 0; i < nums1.length; i++) {
            if (index[i] >= nums2.length) {
                continue;
            }
            if (nums1[i] + nums2[index[i]] < min_val) {
                min_val = nums1[i] + nums2[index[i]];
                in = i;
            }
        }
        if (in == -1) {
            break;
        }
        int[] temp = {nums1[in], nums2[index[in]]};
        ret.add(temp);
        index[in]++;
    }
    return ret;
}




//Merge K lists
The idea lying behind this problem is the same as merging K sorted lists:
List 1: a1+b1, a1+b2, a1+b3,a1+b4,a1+b5,...........
List 2: a2+b1, a2+b2, a2+b3,a2+b4,a2+b5,...........
List 3: a3+b1, a3+b2, a3+b3,a3+b4,a3+b5,...........
......
So, first we need to add the first element in every list to the heap. Then, we keep popping out the top of the heap and adding the next element in the list which the currently popped out element comes from, until we get k smallest pairs or all the pairs are used up. The time complexity is: O(max(length1, k) * log(length1)), where length1 is the length of the first ascending array.

public class Solution {
    public List<int[]> kSmallestPairs(final int[] nums1, final int[] nums2, int k) {
        ArrayList<int[]> topKInts = new ArrayList<>();
        PriorityQueue<int[]> pq;

        if(nums1.length==0 || nums2.length==0) {
            return topKInts;
        }

        pq = new PriorityQueue(nums1.length, new Comparator<int[]>() {
            @Override
            public int compare(int[] indices1, int[] indices2) {
                if(nums1[ indices1[ 0 ] ]+nums2[ indices1[ 1 ] ] < nums1[ indices2[ 0 ] ]+nums2[ indices2[ 1 ] ]) {
                    return -1;
                } else if(nums1[ indices1[ 0 ] ]+nums2[ indices1[ 1 ] ] > nums1[ indices2[ 0 ] ]+nums2[ indices2[ 1 ] ]) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        for(int cnt = 0; cnt < nums1.length; cnt++) {
            pq.offer(new int[]{cnt, 0});
        }

        while(!pq.isEmpty() && topKInts.size()<k) {
            int[] indices = pq.poll();
            topKInts.add(new int[]{nums1[ indices[ 0 ] ], nums2[ indices[ 1 ] ]});
            indices[ 1 ]++;
            if(indices[ 1 ] < nums2.length) {
                pq.offer(indices);
            }
        }

        return topKInts;
    }
}

//dijkstra like?
public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    List<int[]> result = new ArrayList<int[]>();
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
        public int compare(int[] pair1, int[] pair2){
            return (nums1[pair1[0]] + nums2[pair1[1]]) - (nums1[pair2[0]] + nums2[pair2[1]]);
        }
    });

    int length1 = nums1.length;
    int length2 = nums2.length;
    boolean[][] visited = new boolean[length1][length2];

    add(pq, visited, nums1, nums2, 0, 0);
    while(pq.size() > 0 && result.size() < k){
        int[] next = pq.poll();
        result.add(new int[]{nums1[next[0]], nums2[next[1]]});
        add(pq, visited, nums1, nums2, next[0] + 1, next[1]);
        add(pq, visited, nums1, nums2, next[0], next[1] + 1);
    }

    return result;
}

private void add(PriorityQueue<int[]> pq, boolean[][] visited, int[] nums1, int[] nums2, int n1, int n2){
    if(n1 < nums1.length && n2 < nums2.length && !visited[n1][n2]){
        pq.add(new int[]{n1, n2});
        visited[n1][n2] = true;
    }
}



//dijkstra
class Solution(object):
    def kSmallestPairs(self, nums1, nums2, k):
        """
        :type nums1: List[int]
        :type nums2: List[int]
        :type k: int
        :rtype: List[List[int]]
        """
        import heapq
        ret = []
        if len(nums1) * len(nums2) > 0:
            queue = [(nums1[0] + nums2[0], (0, 0))]
            visited = {}
            while len(ret) < k and queue:
                _, (i, j) = heapq.heappop(queue)
                ret.append((nums1[i], nums2[j]))
                if j + 1 < len(nums2) and (i, j + 1) not in visited:
                        heapq.heappush(queue, (nums1[i] + nums2[j + 1], (i, j + 1)))
                        visited[(i, j + 1)] = 1
                if i + 1 < len(nums1) and (i + 1, j) not in visited:
                        heapq.heappush(queue, (nums1[i + 1] + nums2[j], (i + 1, j)))
                        visited[(i + 1, j)] = 1
        return ret



//Wow
Now that I can find the kth smallest element in a sorted n×n matrix in time O(min(n, k)), I can finally solve this problem in O(k).

The idea:

If nums1 or nums2 are larger than k, shrink them to size k.
Build a virtual matrix of the pair sums, i.e., matrix[i][j] = nums1[i] + nums2[j]. Make it a square matrix by padding with "infinity" if necessary. With "virtual" I mean its entries will be computed on the fly, and only those that are needed. This is necessary to stay within O(k) time.
Find the kth smallest sum kthSum by using that other algorithm.
Use a saddleback search variation to discount the pairs with sum smaller than kthSum. After this, k tells how many pairs we need whose sum equals kthSum.
Collect all pairs with sum smaller than kthSum as well as k pairs whose sum equals kthSum.
Each of those steps only takes O(k) time.

The code (minus the code for kthSmallest, which you can copy verbatim from my solution to the other problem):

class Solution(object):
    def kSmallestPairs(self, nums1_, nums2_, k):

        # Use at most the first k of each, then get the sizes.
        nums1 = nums1_[:k]
        nums2 = nums2_[:k]
        m, n = len(nums1), len(nums2)

        # Gotta Catch 'Em All?
        if k >= m * n:
            return [[a, b] for a in nums1 for b in nums2]

        # Build a virtual matrix.
        N, inf = max(m, n), float('inf')
        class Row:
            def __init__(self, i):
                self.i = i
            def __getitem__(self, j):
                return nums1[self.i] + nums2[j] if self.i < m and j < n else inf
        matrix = map(Row, range(N))

        # Get the k-th sum.
        kthSum = self.kthSmallest(matrix, k)

        # Discount the pairs with sum smaller than the k-th.
        j = min(k, n)
        for a in nums1:
            while j and a + nums2[j-1] >= kthSum:
                j -= 1
            k -= j

        # Collect and return the pairs.
        pairs = []
        for a in nums1:
            for b in nums2:
                if a + b >= kthSum + (k > 0):
                    break
                pairs.append([a, b])
                k -= a + b == kthSum
        return pairs

    def kthSmallest(self, matrix, k):

        # copy & paste from https://discuss.leetcode.com/topic/53126/o-n-from-paper-yes-o-rows
Thanks to @zhiqing_xiao for pointing out that my previous way of capping the input lists might not be O(k). It was this:

def kSmallestPairs(self, nums1, nums2, k):
    del nums1[k:]
    del nums2[k:]





//Steps
Several solutions from naive to more elaborate. I found it helpful to visualize the input as an m×n matrix of sums, for example for nums1=[1,7,11], and nums2=[2,4,6]:

      2   4   6
   +------------
 1 |  3   5   7
 7 |  9  11  13
11 | 13  15  17
Of course the smallest pair overall is in the top left corner, the one with sum 3. We don't even need to look anywhere else. After including that pair in the output, the next-smaller pair must be the next on the right (sum=5) or the next below (sum=9). We can keep a "horizon" of possible candidates, implemented as a heap / priority-queue, and roughly speaking we'll grow from the top left corner towards the right/bottom. That's what my solution 5 does. Solution 4 is similar, not quite as efficient but a lot shorter and my favorite.


Solution 1: Brute Force (accepted in 560 ms)
Just produce all pairs, sort them by sum, and return the first k.

def kSmallestPairs(self, nums1, nums2, k):
    return sorted(itertools.product(nums1, nums2), key=sum)[:k]
Solution 2: Clean Brute Force (accepted in 532 ms)
The above produces tuples and while the judge doesn't care, it's cleaner to make them lists as requested:

def kSmallestPairs(self, nums1, nums2, k):
    return map(list, sorted(itertools.product(nums1, nums2), key=sum)[:k])
Solution 3: Less Brute Force (accepted in 296 ms)
Still going through all pairs, but only with a generator and heapq.nsmallest, which uses a heap of size k. So this only takes O(k) extra memory and O(mn log k) time.

def kSmallestPairs(self, nums1, nums2, k):
    return map(list, heapq.nsmallest(k, itertools.product(nums1, nums2), key=sum))
Or (accepted in 368 ms):

def kSmallestPairs(self, nums1, nums2, k):
    return heapq.nsmallest(k, ([u, v] for u in nums1 for v in nums2), key=sum)
Solution 4: Efficient (accepted in 112 ms)
The brute force solutions computed the whole matrix (see visualization above). This solution doesn't. It turns each row into a generator of triples [u+v, u, v], only computing the next when asked for one. And then merges these generators with a heap. Takes O(m + k*log(m)) time and O(m) extra space.

def kSmallestPairs(self, nums1, nums2, k):
    streams = map(lambda u: ([u+v, u, v] for v in nums2), nums1)
    stream = heapq.merge(*streams)
    return [suv[1:] for suv in itertools.islice(stream, k)]
Solution 5: More efficient (accepted in 104 ms)
The previous solution right away considered (the first pair of) all matrix rows (see visualization above). This one doesn't. It starts off only with the very first pair at the top-left corner of the matrix, and expands from there as needed. Whenever a pair is chosen into the output result, the next pair in the row gets added to the priority queue of current options. Also, if the chosen pair is the first one in its row, then the first pair in the next row is added to the queue.

def kSmallestPairs(self, nums1, nums2, k):
    queue = []
    def push(i, j):
        if i < len(nums1) and j < len(nums2):
            heapq.heappush(queue, [nums1[i] + nums2[j], i, j])
    push(0, 0)
    pairs = []
    while queue and len(pairs) < k:
        _, i, j = heapq.heappop(queue)
        pairs.append([nums1[i], nums2[j]])
        push(i, j + 1)
        if j == 0:
            push(i + 1, 0)
    return pairs



