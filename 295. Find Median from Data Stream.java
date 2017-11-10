class MedianFinder {
    List<Integer> data;

    /** initialize your data structure here. */
    public MedianFinder() {
        this.data = new ArrayList<>();
    }

    public void addNum(int num) {
        data.add(num);
        int i = data.size()-1;
        while(i >= 1){
            if(data.get(i) < data.get(i-1)){
                swap(data,i,i-1);
                i--;
            }
            else break;
        }

    }

    public double findMedian() {
        int len = data.size();
        if(len%2 == 0){
            return ((double)data.get(len/2) + (double)data.get(len/2-1))/2;
        }
        else
            return (double)data.get(len/2);

    }

    public void swap(List<Integer> data, int i, int j){
        int temp = data.get(i);
        data.set(i,data.get(j));
        data.set(j,temp);
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */


//TLE


class MedianFinder {

    private int count;
    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;


    /** initialize your data structure here. */
    public MedianFinder() {
        this.count = 0;
        this.minHeap = new PriorityQueue<>(
            new Comparator<Integer>(){
                public int compare(Integer a, Integer b){return a-b;}
            }
            );
        this.maxHeap = new PriorityQueue<>(
            new Comparator<Integer>(){
                public int compare(Integer a, Integer b){return b-a;}
            }
            );
    }

    public void addNum(int num) {
        if(count == 0){
            maxHeap.offer(num);
            count++;
            return ;
        }
        if(count%2 == 0){
            if(num > maxHeap.peek()){
                minHeap.offer(num);
                int temp = minHeap.poll();
                maxHeap.offer(temp);
            }
            else{
                maxHeap.offer(num);
            }
        }
        else{
            if(num > maxHeap.peek()){
                minHeap.offer(num);
            }
            else{
                maxHeap.offer(num);
                int temp = maxHeap.poll();
                minHeap.offer(temp);
            }
        }
        count++;
    }

    public double findMedian() {
        if(count%2 == 0){
            return ((double)minHeap.peek()+(double)maxHeap.peek())/2;
        }
        else{
            return maxHeap.peek();
        }

    }
}



/*More concise
I keep two heaps (or priority queues):

Max-heap small has the smaller half of the numbers.
Min-heap large has the larger half of the numbers.
This gives me direct access to the one or two middle values (they're the tops of the heaps), so getting the median takes O(1) time. And adding a number takes O(log n) time.

Supporting both min- and max-heap is more or less cumbersome, depending on the language, so I simply negate the numbers in the heap in which I want the reverse of the default order. To prevent this from causing a bug with -231 (which negated is itself, when using 32-bit ints), I use integer types larger than 32 bits.

Using larger integer types also prevents an overflow error when taking the mean of the two middle numbers. I think almost all solutions posted previously have that bug.

Update: These are pretty short already, but by now I wrote even shorter ones.

Java

class MedianFinder {

    private Queue<Long> small = new PriorityQueue(),
                        large = new PriorityQueue();

    public void addNum(int num) {
        large.add((long) num);
        small.add(-large.poll());
        if (large.size() < small.size())
            large.add(-small.poll());
    }

    public double findMedian() {
        return large.size() > small.size()
               ? large.peek()
               : (large.peek() - small.peek()) / 2.0;
    }
};
Props to larrywang2014's solution for making me aware that I can use Queue in the declaration instead of PriorityQueue (that's all I got from him, though (just saying because I just saw he changed his previously longer addNum and it's now equivalent to mine)).
*/





/*More concise 2
class MedianFinder {
    // max queue is always larger or equal to min queue
    PriorityQueue<Integer> min = new PriorityQueue();
    PriorityQueue<Integer> max = new PriorityQueue(1000, Collections.reverseOrder());
    // Adds a number into the data structure.
    public void addNum(int num) {
        max.offer(num);
        min.offer(max.poll());
        if (max.size() < min.size()){
            max.offer(min.poll());
        }
    }

    // Returns the median of current data stream
    public double findMedian() {
        if (max.size() == min.size()) return (max.peek() + min.peek()) /  2.0;
        else return max.peek();
    }
};
*/






/*Summary
Approach #1 Simple Sorting [Time Limit Exceeded]

Intuition

Do what the question says.

Algorithm

Store the numbers in a resize-able container. Every time you need to output the median, sort the container and output the median.

class MedianFinder {
    vector<double> store;

public:
    // Adds a number into the data structure.
    void addNum(int num)
    {
        store.push_back(num);
    }

    // Returns the median of current data stream
    double findMedian()
    {
        sort(store.begin(), store.end());

        int n = store.size();
        return (n & 1 ? (store[n / 2 - 1] + store[n / 2]) * 0.5 : store[n / 2]);
    }
};

Complexity Analysis

Time complexity: O(n \cdot log(n)) + O(1) \simeq O(n \cdot log(n))O(n⋅log(n))+O(1)≃O(n⋅log(n)).

Adding a number takes amortized O(1)O(1) time for a container with an efficient resizing scheme.
Finding the median is primarily dependent on the sorting that takes place. This takes O(n \cdot log(n))O(n⋅log(n)) time for a standard comparative sort.
Space complexity: O(n)O(n) linear space to hold input in a container. No extra space other than that needed (since sorting can usually be done in-place).

Approach #2 Insertion Sort [Time Limit Exceeded]

Intuition

Keeping our input container always sorted (i.e. maintaining the sorted nature of the container as an invariant).

Algorithm

Which algorithm allows a number to be added to a sorted list of numbers and yet keeps the entire list sorted? Well, for one, insertion sort!

We assume that the current list is already sorted. When a new number comes, we have to add it to the list while maintaining the sorted nature of the list. This is achieved easily by finding the correct place to insert the incoming number, using a binary search (remember, the list is always sorted). Once the position is found, we need to shift all higher elements by one space to make room for the incoming number.

This method would work well when the amount of insertion queries is lesser or about the same as the amount of median finding queries.

class MedianFinder {
    vector<int> store; // resize-able container

public:
    // Adds a number into the data structure.
    void addNum(int num)
    {
        if (store.empty())
            store.push_back(num);
        else
            store.insert(lower_bound(store.begin(), store.end(), num), num);     // binary search and insertion combined
    }

    // Returns the median of current data stream
    double findMedian()
    {
        int n = store.size();
        return n & 1 ? store[n / 2] : (store[n / 2 - 1] + store[n / 2]) * 0.5;
    }
};


Complexity Analysis

Time complexity: O(n) + O(log(n)) \approx O(n)O(n)+O(log(n))≈O(n).

Binary Search takes O(log(n))O(log(n)) time to find correct insertion position.
Insertion can take up to O(n)O(n) time since elements have to be shifted inside the container to make room for the new element.
Pop quiz: Can we use a linear search instead of a binary search to find insertion position, without incurring any significant runtime penalty?
Space complexity: O(n)O(n) linear space to hold input in a container.
Approach #3 Two Heaps! [Accepted]

Intuition

The above two approaches gave us some valuable insights on how to tackle this problem. Concretely, one can infer two things:

If we could maintain direct access to median elements at all times, then finding the median would take a constant amount of time.
If we could find a reasonably fast way of adding numbers to our containers, additional penalties incurred could be lessened.
But perhaps the most important insight, which is not readily observable, is the fact that we only need a consistent way to access the median elements. Keeping the entire input sorted is not a requirement.

Well, if only there were a data structure which could handle our needs.
As it turns out there are two data structures for the job:

Heaps (or Priority Queues 1)
Self-balancing Binary Search Trees (we'll talk more about them in Approach #4)
Heaps are a natural ingredient for this dish! Adding elements to them take logarithmic order of time. They also give direct access to the maximal/minimal elements in a group.

If we could maintain two heaps in the following way:

A max-heap to store the smaller half of the input numbers
A min-heap to store the larger half of the input numbers
This gives access to median values in the input: they comprise the top of the heaps!

Wait, what? How?

If the following conditions are met:

Both the heaps are balanced (or nearly balanced)
The max-heap contains all the smaller numbers while the min-heap contains all the larger numbers
then we can say that:

All the numbers in the max-heap are smaller or equal to the top element of the max-heap (let's call it xx)
All the numbers in the min-heap are larger or equal to the top element of the min-heap (let's call it yy)
Then xx and/or yy are smaller than (or equal to) almost half of the elements and larger than (or equal to) the other half. That is the definition of median elements.

This leads us to a huge point of pain in this approach: balancing the two heaps!

Algorithm

Two priority queues:

A max-heap lo to store the smaller half of the numbers
A min-heap hi to store the larger half of the numbers
The max-heap lo is allowed to store, at worst, one more element more than the min-heap hi. Hence if we have processed kk elements:

If k = 2*n + 1 \quad (\forall \, n \in \mathbb{Z})k=2∗n+1(∀n∈Z), then lo is allowed to hold n+1n+1 elements, while hi can hold nn elements.
If k = 2*n \quad (\forall \, n \in \mathbb{Z})k=2∗n(∀n∈Z), then both heaps are balanced and hold nn elements each.
This gives us the nice property that when the heaps are perfectly balanced, the median can be derived from the tops of both heaps. Otherwise, the top of the max-heap lo holds the legitimate median.

Adding a number num:

Add num to max-heap lo. Since lo received a new element, we must do a balancing step for hi. So remove the largest element from lo and offer it to hi.
The min-heap hi might end holding more elements than the max-heap lo, after the previous operation. We fix that by removing the smallest element from hi and offering it to lo.
The above step ensures that we do not disturb the nice little size property we just mentioned.

A little example will clear this up! Say we take input from the stream [41, 35, 62, 5, 97, 108]. The run-though of the algorithm looks like this:

Adding number 41
MaxHeap lo: [41]           // MaxHeap stores the largest value at the top (index 0)
MinHeap hi: []             // MinHeap stores the smallest value at the top (index 0)
Median is 41
=======================
Adding number 35
MaxHeap lo: [35]
MinHeap hi: [41]
Median is 38
=======================
Adding number 62
MaxHeap lo: [41, 35]
MinHeap hi: [62]
Median is 41
=======================
Adding number 4
MaxHeap lo: [35, 4]
MinHeap hi: [41, 62]
Median is 38
=======================
Adding number 97
MaxHeap lo: [41, 35, 4]
MinHeap hi: [62, 97]
Median is 41
=======================
Adding number 108
MaxHeap lo: [41, 35, 4]
MinHeap hi: [62, 97, 108]
Median is 51.5

class MedianFinder {
    priority_queue<int> lo;                              // max heap
    priority_queue<int, vector<int>, greater<int>> hi;   // min heap

public:
    // Adds a number into the data structure.
    void addNum(int num)
    {
        lo.push(num);                                    // Add to max heap

        hi.push(lo.top());                               // balancing step
        lo.pop();

        if (lo.size() < hi.size()) {                     // maintain size property
            lo.push(hi.top());
            hi.pop();
        }
    }

    // Returns the median of current data stream
    double findMedian()
    {
        return lo.size() > hi.size() ? (double) lo.top() : (lo.top() + hi.top()) * 0.5;
    }
};

Complexity Analysis

Time complexity: O(5 * log(n)) + O(1) \approx O(log(n))O(5∗log(n))+O(1)≈O(log(n)).

At worst, there are three heap insertions and two heap deletions from the top. Each of these takes about O(log(n))O(log(n)) time.
Finding the mean takes constant O(1)O(1) time since the tops of heaps are directly accessible.
Space complexity: O(n)O(n) linear space to hold input in containers.

Approach #4 Multiset and Two Pointers [Accepted]

Intuition

Self-balancing Binary Search Trees (like an AVL Tree) have some very interesting properties. They maintain the tree's height to a logarithmic bound. Thus inserting a new element has reasonably good time performance. The median always winds up in the root of the tree and/or one of its children. Solving this problem using the same approach as Approach #3 but using a Self-balancing BST seems like a good choice. Except the fact that implementing such a tree is not trivial and prone to errors.

Why reinvent the wheel? Most languages implement a multiset class which emulates such behavior. The only problem remains keeping track of the median elements. That is easily solved with pointers! 2

We maintain two pointers: one for the lower median element and the other for the higher median element. When the total number of elements is odd, both the pointers point to the same median element (since there is only one median in this case). When the number of elements is even, the pointers point to two consecutive elements, whose mean is the representative median of the input.

Algorithm

Two iterators/pointers lo_median and hi_median, which iterate over the data multiset.

While adding a number num, three cases arise:

The container is currently empty. Hence we simply insert num and set both pointers to point to this element.
The container currently holds an odd number of elements. This means that both the pointers currently point to the same element.

If num is not equal to the current median element, then num goes on either side of it. Whichever side it goes, the size of that part increases and hence the corresponding pointer is updated. For example, if num is less than the median element, the size of the lesser half of input increases by 11 on inserting num. Thus it makes sense to decrement lo_median.
If num is equal to the current median element, then the action taken is dependent on how num is inserted into data. NOTE: In our given C++ code example, std::multiset::insert inserts an element after all elements of equal value. Hence we increment hi_median.
The container currently holds an even number of elements. This means that the pointers currently point to consecutive elements.

If num is a number between both median elements, then num becomes the new median. Both pointers must point to it.
Otherwise, num increases the size of either the lesser or higher half of the input. We update the pointers accordingly. It is important to remember that both the pointers must point to the same element now.
Finding the median is easy! It is simply the mean of the elements pointed to by the two pointers lo_median and hi_median.



class MedianFinder {
    multiset<int> data;
    multiset<int>::iterator lo_median, hi_median;

public:
    MedianFinder()
        : lo_median(data.end())
        , hi_median(data.end())
    {
    }

    void addNum(int num)
    {
        const size_t n = data.size();   // store previous size

        data.insert(num);               // insert into multiset

        if (!n) {
            // no elements before, one element now
            lo_median = hi_median = data.begin();
        }
        else if (n & 1) {
            // odd size before (i.e. lo == hi), even size now (i.e. hi = lo + 1)

            if (num < *lo_median)       // num < lo
                lo_median--;
            else                        // num >= hi
                hi_median++;            // insertion at end of equal range
        }
        else {
            // even size before (i.e. hi = lo + 1), odd size now (i.e. lo == hi)

            if (num > *lo_median && num < *hi_median) {
                lo_median++;                    // num in between lo and hi
                hi_median--;
            }
            else if (num >= *hi_median)         // num inserted after hi
                lo_median++;
            else                                // num <= lo < hi
                lo_median = --hi_median;        // insertion at end of equal range spoils lo
        }
    }

    double findMedian()
    {
        return (*lo_median + *hi_median) * 0.5;
    }
};


A much shorter (but harder to understand), one pointer version 3 of this solution is given below:


class MedianFinder {
    multiset<int> data;
    multiset<int>::iterator mid;

public:
    MedianFinder()
        : mid(data.end())
    {
    }

    void addNum(int num)
    {
        const int n = data.size();
        data.insert(num);

        if (!n)                                 // first element inserted
            mid = data.begin();
        else if (num < *mid)                    // median is decreased
            mid = (n & 1 ? mid : prev(mid));
        else                                    // median is increased
            mid = (n & 1 ? next(mid) : mid);
    }

    double findMedian()
    {
        const int n = data.size();
        return (*mid + *next(mid, n % 2 - 1)) * 0.5;
    }
};

Complexity Analysis

Time complexity: O(log(n)) + O(1) \approx O(log(n))O(log(n))+O(1)≈O(log(n)).

Inserting a number takes O(log(n))O(log(n)) time for a standard multiset scheme. 4
Finding the mean takes constant O(1)O(1) time since the median elements are directly accessible from the two pointers.
Space complexity: O(n)O(n) linear space to hold input in container.

Further Thoughts

There are so many ways around this problem, that frankly, it is scary. Here are a few more that I came across:

Buckets! If the numbers in the stream are statistically distributed, then it is easier to keep track of buckets where the median would land, than the entire array. Once you know the correct bucket, simply sort it find the median. If the bucket size is significantly smaller than the size of input processed, this results in huge time saving. @mitbbs8080 has an interesting implementation here.

Reservoir Sampling. Following along the lines of using buckets: if the stream is statistically distributed, you can rely on Reservoir Sampling. Basically, if you could maintain just one good bucket (or reservoir) which could hold a representative sample of the entire stream, you could estimate the median of the entire stream from just this one bucket. This means good time and memory performance. Reservoir Sampling lets you do just that. Determining a "good" size for your reservoir? Now, that's a whole other challenge. A good explanation for this can be found in this StackOverflow answer.

Segment Trees are a great data structure if you need to do a lot of insertions or a lot of read queries over a limited range of input values. They allow us to do all such operations fast and in roughly the same amount of time, always. The only problem is that they are far from trivial to implement. Take a look at my introductory article on Segment Trees if you are interested.

Order Statistic Trees are data structures which seem to be tailor-made for this problem. They have all the nice features of a BST, but also let you find the k^{th}k
​th
​​  order element stored in the tree. They are a pain to implement and no standard interview would require you to code these up. But they are fun to use if they are already implemented in the language of your choice. 5

Analysis written by @babhishek21.

Priority Queues queue out elements based on a predefined priority. They are an abstract concept and can, as such, be implemented in many different ways. Heaps are an efficient way to implement Priority Queues. ↩

Shout-out to @pharese for this approach. ↩

Inspired from this post by @StefanPochmann. ↩

Hinting can reduce that to amortized constant O(1)O(1) time. ↩

GNU libstdc++ users are in luck! Take a look at this StackOverflow answer. ↩

*/




/*Using BST
class MedianFinder {
    class TreeNode{
        int val;
        TreeNode parent,left,right;
        TreeNode(int val, TreeNode p){
            this.val=val;
            this.parent=p;
            left=null;
            right=null;
        }
        void add(int num){
            if(num>=val){
                if(right==null)
                    right=new TreeNode(num,this);
                else
                    right.add(num);
            }else{
                if(left==null)
                    left=new TreeNode(num,this);
                else
                    left.add(num);
            }
        }
        TreeNode next(){
            TreeNode ret;
            if(right!=null){
                ret=right;
                while(ret.left!=null)
                    ret=ret.left;
            }else{
                ret=this;
                while(ret.parent.right==ret)
                    ret=ret.parent;
                ret=ret.parent;
            }
            return ret;
        }
        TreeNode prev(){
            TreeNode ret;
            if(left!=null){
                ret=left;
                while(ret.right!=null)
                    ret=ret.right;
            }else{
                ret=this;
                while(ret.parent.left==ret)
                    ret=ret.parent;
                ret=ret.parent;
            }
            return ret;
        }
    }
    int n;
    TreeNode root, curr;
    // Adds a number into the data structure.
    public void addNum(int num) {
        if(root==null){
            root = new TreeNode(num,null);
            curr=root;
            n=1;
        }else{
            root.add(num);
            n++;
            if(n%2==1){
                if(curr.val<=num)
                    curr=curr.next();
            }else
                if(curr.val>num)
                    curr=curr.prev();
        }
    }

    // Returns the median of current data stream
    public double findMedian() {
        if(n%2==0){
            return ((double)curr.next().val+curr.val)/2;
        }else
            return curr.val;
    }
};

// Your MedianFinder object will be instantiated and called as such:
// MedianFinder mf = new MedianFinder();
// mf.addNum(1);
// mf.findMedian();
*/





/*Using BST 2
Some notes: This solution uses an ordinary binary tree for simplicity's sake, which means it is likely to be unbalanced. Given enough time one may well use a balanced binary tree implementation to guarantee O(logn) runtime for addNum(). It is easy to see that findMedian() runs in O(1).

By using a binary tree, we can easily keep the input numbers in nondecreasing order. Observe that whenever a number is added, the numbers used to calculate the median never shift by more than 1 position (in an imagined array representation) to the left or to the right. Let's see an example:
[2], number used to calculate median is 2.
[2,3], numbers used are 2,3 (expanding 1 to right)
[0,2,3], use 2 (shrinking 1 to left)
[0,1,2,3], use 1,2 (expanding 1 to left)
[0,1,2,3,4], use 2 (shrinking 1 to right)
....and so on.

With this observation, in MedianFinder I employ 2 variables medianLeft and medianRight to keep track of numbers we need to calculate the median. When size is odd, they point to the same node, otherwise they always point to 2 nodes which have predecessor/successor relationship. When adding a node, we just need to check the size of our MedianFinder tree, then depending on whether the new number is inserted to the left, inbetween, or to the right of our 2 median trackers, we will change medianLeft and medianRight to point to the correct nodes. Because the position never shifts more than 1, we can simply use predecessor() or successor() on the desired node to update it. Those 2 methods run in O(logn) when the tree is balanced, hence the O(logn) runtime of addNum().

Hope this helps!

public class MedianFinder {
    private Node root;
    private Node medianLeft;
    private Node medianRight;
    private int size;

    public MedianFinder() {
    }

    // Adds a number into the data structure.
    public void addNum(int num) {
        if (root == null) {
            root = new Node(num);
            medianLeft = root;
            medianRight = root;
        }
        else {
            root.addNode(num);
            if (size % 2 == 0) {
                if (num < medianLeft.data) {
                    medianRight = medianLeft;
                }
                else if (medianLeft.data <= num && num < medianRight.data) {
                    medianLeft = medianLeft.successor();
                    medianRight = medianRight.predecessor();
                }
                else if (medianRight.data <= num) {
                    medianLeft = medianRight;
                }
            }
            else {
                if (num < medianLeft.data) {
                    medianLeft = medianLeft.predecessor();
                }
                else {
                    medianRight = medianRight.successor();
                }
            }
        }
        size++;
    }

    // Returns the median of current data stream
    public double findMedian() {
        return (medianLeft.data + medianRight.data) / 2.0;
    }

    class Node {
        private Node parent;
        private Node left;
        private Node right;
        private int data;

        public Node(int data) {
            this.data = data;
        }

        public void addNode(int data) {
            if (data >= this.data) {
              if (right == null) {
                right = new Node(data);
                right.parent = this;
              }
              else
                right.addNode(data);
            }
            else {
              if (left == null) {
                left = new Node(data);
                left.parent = this;
              }
              else
                left.addNode(data);
            }
        }

        public Node predecessor() {
            if (left != null)
                return left.rightMost();

            Node predecessor = parent;
            Node child = this;

            while (predecessor != null && child != predecessor.right) {
                child = predecessor;
                predecessor = predecessor.parent;
            }

            return predecessor;
        }

        public Node successor() {
            if (right != null)
                return right.leftMost();

            Node successor = parent;
            Node child = this;

            while (successor != null && child != successor.left) {
                child = successor;
                successor = successor.parent;
            }

            return successor;
        }

        public Node leftMost(){
            if (left == null)
                return this;
            return left.leftMost();
        }

        private Node rightMost() {
            if (right == null)
                return this;
            return right.rightMost();
        }

    }
};
*/
