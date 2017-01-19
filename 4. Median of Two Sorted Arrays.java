public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

    }
}


//Using DAC
To solve this problem, we need to understand "What is the use of median". In statistics, the median is used for dividing a set into two equal length subsets, that one subset is always greater than the other. If we understand the use of median for dividing, we are very close to the answer.

First lets cut A into two parts at a random position i:

      left_A             |        right_A
A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
Since A has m elements, so there are m+1 kinds of cutting( i = 0 ~ m ). And we know: len(left_A) = i, len(right_A) = m - i . Note: when i = 0 , left_A is empty, and when i = m , right_A is empty.

With the same way, cut B into two parts at a random position j:

      left_B             |        right_B
B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
Put left_A and left_B into one set, and put right_A and right_B into another set. Lets name them left_part and right_part :

      left_part          |        right_part
A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
If we can ensure:

1) len(left_part) == len(right_part)
2) max(left_part) <= min(right_part)
then we divide all elements in {A, B} into two parts with equal length, and one part is always greater than the other. Then median = (max(left_part) + min(right_part))/2.

To ensure these two conditions, we just need to ensure:

(1) i + j == m - i + n - j (or: m - i + n - j + 1)
    if n >= m, we just need to set: i = 0 ~ m, j = (m + n + 1)/2 - i
(2) B[j-1] <= A[i] and A[i-1] <= B[j]
ps.1 For simplicity, I presume A[i-1],B[j-1],A[i],B[j] are always valid even if i=0/i=m/j=0/j=n . I will talk about how to deal with these edge values at last.

ps.2 Why n >= m? Because I have to make sure j is non-nagative since 0 <= i <= m and j = (m + n + 1)/2 - i. If n < m , then j may be nagative, that will lead to wrong result.

So, all we need to do is:

Searching i in [0, m], to find an object `i` that:
    B[j-1] <= A[i] and A[i-1] <= B[j], ( where j = (m + n + 1)/2 - i )
And we can do a binary search following steps described below:

<1> Set imin = 0, imax = m, then start searching in [imin, imax]

<2> Set i = (imin + imax)/2, j = (m + n + 1)/2 - i

<3> Now we have len(left_part)==len(right_part). And there are only 3 situations
     that we may encounter:
    <a> B[j-1] <= A[i] and A[i-1] <= B[j]
        Means we have found the object `i`, so stop searching.
    <b> B[j-1] > A[i]
        Means A[i] is too small. We must `ajust` i to get `B[j-1] <= A[i]`.
        Can we `increase` i?
            Yes. Because when i is increased, j will be decreased.
            So B[j-1] is decreased and A[i] is increased, and `B[j-1] <= A[i]` may
            be satisfied.
        Can we `decrease` i?
            `No!` Because when i is decreased, j will be increased.
            So B[j-1] is increased and A[i] is decreased, and B[j-1] <= A[i] will
            be never satisfied.
        So we must `increase` i. That is, we must ajust the searching range to
        [i+1, imax]. So, set imin = i+1, and goto <2>.
    <c> A[i-1] > B[j]
        Means A[i-1] is too big. And we must `decrease` i to get `A[i-1]<=B[j]`.
        That is, we must ajust the searching range to [imin, i-1].
        So, set imax = i-1, and goto <2>.
When the object i is found, the median is:

max(A[i-1], B[j-1]) (when m + n is odd)
or (max(A[i-1], B[j-1]) + min(A[i], B[j]))/2 (when m + n is even)
Now lets consider the edges values i=0,i=m,j=0,j=n where A[i-1],B[j-1],A[i],B[j] may not exist. Actually this situation is easier than you think.

What we need to do is ensuring that max(left_part) <= min(right_part). So, if i and j are not edges values(means A[i-1],B[j-1],A[i],B[j] all exist), then we must check both B[j-1] <= A[i] and A[i-1] <= B[j]. But if some of A[i-1],B[j-1],A[i],B[j] dont exist, then we dont need to check one(or both) of these two conditions. For example, if i=0, then A[i-1] doesnt exist, then we dont need to check A[i-1] <= B[j]. So, what we need to do is:

Searching i in [0, m], to find an object `i` that:
    (j == 0 or i == m or B[j-1] <= A[i]) and
    (i == 0 or j == n or A[i-1] <= B[j])
    where j = (m + n + 1)/2 - i
And in a searching loop, we will encounter only three situations:

<a> (j == 0 or i == m or B[j-1] <= A[i]) and
    (i == 0 or j = n or A[i-1] <= B[j])
    Means i is perfect, we can stop searching.

<b> j > 0 and i < m and B[j - 1] > A[i]
    Means i is too small, we must increase it.

<c> i > 0 and j < n and A[i - 1] > B[j]
    Means i is too big, we must decrease it.
Thank @Quentin.chen , him pointed out that: i < m ==> j > 0 and i > 0 ==> j < n . Because:

m <= n, i < m ==> j = (m+n+1)/2 - i > (m+n+1)/2 - m >= (2*m+1)/2 - m >= 0
m <= n, i > 0 ==> j = (m+n+1)/2 - i < (m+n+1)/2 <= (2*n+1)/2 <= n
So in situation <b> and <c>, we dont need to check whether j > 0 and whether j < n.

Below is the accepted code:

public class Solution {
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
	    // In statistics, the median is used for dividing a set into two equal length subsets, that one subset is always greater than the other there are two array, we seperated them into two part, it is left_part and right_part. If we found the value in left_part <= right_part, then we can find the answer. So we need find i, j, which satisfied (1)A[i-1] < B[j] && B[j-1] < A[i(2)len(left_part) == len(right_part).
	 //For(1) Median=(max(left_part)+min(right_part))/2.
	 //For(2)  i + j == m-i+n-j => i + j = (m+n) / 2     (or: i + j == m-i+n-j+1 => j = (m+n+1)/2 - i)

	   // left_part          |        right_part
	   // A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
	   // B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]


    int m = nums1.length;
    int n = nums2.length;
    if(m>n) return findMedianSortedArrays(nums2,nums1);
    //for all the following, we assumed m<=n;
    int imin = 0;
    int imax = m;
    int max_of_left;
    int min_of_right;
    int i=(imin+imax)/2, j=(m+n+1)/2 -i;
	    //
    while(imin <= imax){
        i=(imin+imax)/2;
        j = (m+n+1)/2 -i;
        if(i>0 && nums1[i-1] > nums2[j]){
            //it means i is too large, so decrease i
            //m <= n(we have assumed), i < m ==> j = (m+n+1)/2 - i > (m+n+1)/2 - m >= (2*m+1)/2 - m >= 0
            imax = i-1;
        }else if(i < m  && nums2[j-1] > nums1[i]){
            //it means i is too smore.
            //m <= n(we have assumed), i > 0 ==> j = (m+n+1)/2 - i < (m+n+1)/2 <= (2*n+1)/2 <= n

            imin = i+1;
        }else break;
        //i is perfect
    }
	//find left maximum value and find right maximum value
	if(i == 0) max_of_left = nums2[j-1];
	else if(j == 0) max_of_left = nums1[i-1];
	else  max_of_left = Math.max(nums1[i-1],nums2[j-1]);
	if((m + n) % 2 == 1) return max_of_left;

	if(i == m) min_of_right = nums2[j];
	else if( j == n) min_of_right = nums1[i];
	else min_of_right = Math.min(nums1[i],nums2[j]);

	return (max_of_left+min_of_right)/2.0;

    }
}



//Using DAC 2
This problem is notoriously hard to implement due to all the corner cases. Most implementations consider odd-lengthed and even-lengthed arrays as two different cases and treat them separately. As a matter of fact, with a little mind twist. These two cases can be combined as one, leading to a very simple solution where (almost) no special treatment is needed.

First, lets see the concept of 'MEDIAN' in a slightly unconventional way. That is:'

"if we cut the sorted array to two halves of EQUAL LENGTHS, then
median is the AVERAGE OF Max(lower_half) and Min(upper_half), i.e. the
two numbers immediately next to the cut".
For example, for [2 3 5 7], we make the cut between 3 and 5:

[2 3 / 5 7]
then the median = (3+5)/2. Note that I'll use '/' to represent a cut, and (number / number) to represent a cut made through a number in this article.

for [2 3 4 5 6], we make the cut right through 4 like this:

[2 3 (4/4) 5 7]

Since we split 4 into two halves, we say now both the lower and upper subarray contain 4. This notion also leads to the correct answer: (4 + 4) / 2 = 4;

For convenience, lets use L to represent the number immediately left to the cut, and R the right counterpart. In [2 3 5 7], for instance, we have L = 3 and R = 5, respectively.

We observe the index of L and R have the following relationship with the length of the array N:

N        Index of L / R
1               0 / 0
2               0 / 1
3               1 / 1
4               1 / 2
5               2 / 2
6               2 / 3
7               3 / 3
8               3 / 4
It is not hard to conclude that index of L = (N-1)/2, and R is at N/2. Thus, the median can be represented as

(L + R)/2 = (A[(N-1)/2] + A[N/2])/2
To get ready for the two array situation, let's add a few imaginary 'positions' (represented as #'s) in between numbers, and treat numbers as 'positions' as well.

[6 9 13 18]  ->   [# 6 # 9 # 13 # 18 #]    (N = 4)
position index     0 1 2 3 4 5  6 7  8     (N_Position = 9)

[6 9 11 13 18]->   [# 6 # 9 # 11 # 13 # 18 #]   (N = 5)
position index      0 1 2 3 4 5  6 7  8 9 10    (N_Position = 11)
As you can see, there are always exactly 2*N+1 'positions' regardless of length N. Therefore, the middle cut should always be made on the Nth position (0-based). Since index(L) = (N-1)/2 and index(R) = N/2 in this situation, we can infer that index(L) = (CutPosition-1)/2, index(R) = (CutPosition)/2.

Now for the two-array case:

A1: [# 1 # 2 # 3 # 4 # 5 #]    (N1 = 5, N1_positions = 11)

A2: [# 1 # 1 # 1 # 1 #]     (N2 = 4, N2_positions = 9)
Similar to the one-array problem, we need to find a cut that divides the two arrays each into two halves such that

"any number in the two left halves" <= "any number in the two right
halves".
We can also make the following observations：

There are 2N1 + 2N2 + 2 position altogether. Therefore, there must be exactly N1 + N2 positions on each side of the cut, and 2 positions directly on the cut.

Therefore, when we cut at position C2 = K in A2, then the cut position in A1 must be C1 = N1 + N2 - k. For instance, if C2 = 2, then we must have C1 = 4 + 5 - C2 = 7.

 [# 1 # 2 # 3 # (4/4) # 5 #]

 [# 1 / 1 # 1 # 1 #]
When the cuts are made, we'd have two L's and two R's. 'They are

 L1 = A1[(C1-1)/2]; R1 = A1[C1/2];
 L2 = A2[(C2-1)/2]; R2 = A2[C2/2];
In the above example,

    L1 = A1[(7-1)/2] = A1[3] = 4; R1 = A1[7/2] = A1[3] = 4;
    L2 = A2[(2-1)/2] = A2[0] = 1; R2 = A1[2/2] = A1[1] = 1;
Now how do we decide if this cut is the cut we want? Because L1, L2 are the greatest numbers on the left halves and R1, R2 are the smallest numbers on the right, we only need

L1 <= R1 && L1 <= R2 && L2 <= R1 && L2 <= R2
to make sure that any number in lower halves <= any number in upper halves. As a matter of fact, since
L1 <= R1 and L2 <= R2 are naturally guaranteed because A1 and A2 are sorted, we only need to make sure:

L1 <= R2 and L2 <= R1.

Now we can use simple binary search to find out the result.

If we have L1 > R1, it means there are too many large numbers on the left half of A1, then we must move C1 to the left (i.e. move C2 to the right);
If L2 > R1, then there are too many large numbers on the left half of A2, and we must move C2 to the left.
Otherwise, this cut is the right one.
After we find the cut, the medium can be computed as (max(L1, L2) + min(R1, R2)) / 2;
Two side notes:

A. since C1 and C2 can be mutually determined from each other, we might as well select the shorter array (say A2) and only move C2 around, and calculate C1 accordingly. That way we can achieve a run-time complexity of O(log(min(N1, N2)))

B. The only edge case is when a cut falls on the 0th(first) or the 2Nth(last) position. For instance, if C2 = 2N2, then R2 = A2[2*N2/2] = A2[N2], which exceeds the boundary of the array. To solve this problem, we can imagine that both A1 and A2 actually have two extra elements, INT_MAX at A[-1] and INT_MAX at A[N]. These additions dont change the result, but make the implementation easier: If any L falls out of the left boundary of the array, then L = INT_MIN, and if any R falls out of the right boundary, then R = INT_MAX.

I know that was not very easy to understand, but all the above reasoning eventually boils down to the following concise code:

public class Solution {
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int N1 = nums1.length, N2 = nums2.length;
    if (N1 > N2) return findMedianSortedArrays(nums2, nums1);

    int lo = 0, hi = 2 * N1;
    while (lo <= hi) {
        int C1 = (lo + hi) / 2;
        int C2 = N1 + N2 - C1;

        double L1 = (C1 == 0) ? Integer.MIN_VALUE : nums1[(C1-1)/2];
        double R1 = (C1 == 2*N1) ? Integer.MAX_VALUE : nums1[C1/2];
        double L2 = (C2 == 0) ? Integer.MIN_VALUE : nums2[(C2-1)/2];
        double R2 = (C2 == 2*N2) ? Integer.MAX_VALUE : nums2[C2/2];

        if (L1 > R2) hi = C1 - 1;
        else if (L2 > R1) lo = C1 + 1;
        else return (Math.max(L1, L2) + Math.min(R1, R2)) / 2;
    }
    return -1;
}

If you have any suggestions to make the logic and implementation even more cleaner. Please do let me know!



//Using DAC 3
This is my iterative solution using binary search. The main idea is to find the approximate location of the median and compare the elements around it to get the final result.

do binary search. suppose the shorter list is A with length n. the runtime is O(log(n)) which means no matter how large B array is, it only depends on the size of A. It makes sense because if A has only one element while B has 100 elements, the median must be one of A[0], B[49], and B[50] without check everything else. If A[0] <= B[49], B[49] is the answer; if B[49] < A[0] <= B[50], A[0] is the answer; else, B[50] is the answer.

After binary search, we get the approximate location of median. Now we just need to compare at most 4 elements to find the answer. This step is O(1).

the same solution can be applied to find kth element of 2 sorted arrays.

Here is the code:

    public double findMedianSortedArrays(int A[], int B[]) {
    int n = A.length;
    int m = B.length;
    // the following call is to make sure len(A) <= len(B).
    // yes, it calls itself, but at most once, shouldn't be
    // consider a recursive solution
    if (n > m)
        return findMedianSortedArrays(B, A);

    // now, do binary search
    int k = (n + m - 1) / 2;
    int l = 0, r = Math.min(k, n); // r is n, NOT n-1, this is important!!
    while (l < r) {
        int midA = (l + r) / 2;
        int midB = k - midA;
        if (A[midA] < B[midB])
            l = midA + 1;
        else
            r = midA;
    }

    // after binary search, we almost get the median because it must be between
    // these 4 numbers: A[l-1], A[l], B[k-l], and B[k-l+1]

    // if (n+m) is odd, the median is the larger one between A[l-1] and B[k-l].
    // and there are some corner cases we need to take care of.
    int a = Math.max(l > 0 ? A[l - 1] : Integer.MIN_VALUE, k - l >= 0 ? B[k - l] : Integer.MIN_VALUE);
    if (((n + m) & 1) == 1)
        return (double) a;

    // if (n+m) is even, the median can be calculated by
    //      median = (max(A[l-1], B[k-l]) + min(A[l], B[k-l+1]) / 2.0
    // also, there are some corner cases to take care of.
    int b = Math.min(l < n ? A[l] : Integer.MAX_VALUE, k - l + 1 < m ? B[k - l + 1] : Integer.MAX_VALUE);
    return (a + b) / 2.0;
}
Im lazy to type. But I found a very good pdf to explain my algorithm: http://ocw.alfaisal.edu/NR/rdonlyres/Electrical-Engineering-and-Computer-Science/6-046JFall-2005/30C68118-E436-4FE3-8C79-6BAFBB07D935/0/ps9sol.pdf

BTW: Thanks to xdxiaoxin. Ive removed the check "midB > k".



//Using recursive
Explanation

The key point of this problem is to ignore half part of A and B each step recursively by comparing the median of remaining A and B:

if (aMid < bMid) Keep [aRight + bLeft]

else Keep [bRight + aLeft]
As the following: time=O(log(m + n))

public double findMedianSortedArrays(int[] A, int[] B) {
	    int m = A.length, n = B.length;
	    int l = (m + n + 1) / 2;
	    int r = (m + n + 2) / 2;
	    return (getkth(A, 0, B, 0, l) + getkth(A, 0, B, 0, r)) / 2.0;
	}

public double getkth(int[] A, int aStart, int[] B, int bStart, int k) {
	if (aStart > A.length - 1) return B[bStart + k - 1];
	if (bStart > B.length - 1) return A[aStart + k - 1];
	if (k == 1) return Math.min(A[aStart], B[bStart]);

	int aMid = Integer.MAX_VALUE, bMid = Integer.MAX_VALUE;
	if (aStart + k/2 - 1 < A.length) aMid = A[aStart + k/2 - 1];
	if (bStart + k/2 - 1 < B.length) bMid = B[bStart + k/2 - 1];

	if (aMid < bMid)
	    return getkth(A, aStart + k/2, B, bStart,       k - k/2);// Check: aRight + bLeft
	else
		return getkth(A, aStart,       B, bStart + k/2, k - k/2);// Check: bRight + aLeft
}



//Using recursive 2

using divide and conquer idea, each time find the mid of both arrays

double findMedianSortedArrays(int A[], int m, int B[], int n) {
    /* A[0, 1, 2, ..., n-1, n] */
    /* A[0, 1, 2, ..., m-1, m] */
    int k = (m + n + 1) / 2;
    double v = (double)FindKth(A, 0, m - 1, B, 0, n - 1, k);

    if ((m+n) % 2 == 0) {
        int k2 = k+1;
        double v2 = (double)FindKth(A, 0, m - 1, B, 0, n - 1, k2);
        v = (v + v2) / 2;
    }

    return v;
}

// find the kth element int the two sorted arrays
// let us say: A[aMid] <= B[bMid], x: mid len of a, y: mid len of b, then wen can know
//
// (1) there will be at least (x + 1 + y) elements before bMid
// (2) there will be at least (m - x - 1 + n - y) = m + n - (x + y +1) elements after aMid
// therefore
// if k <= x + y + 1, find the kth element in a and b, but unconsidering bMid and its suffix
// if k > x + y + 1, find the k - (x + 1) th element in a and b, but unconsidering aMid and its prefix
int FindKth(int A[], int aL, int aR, int B[], int bL, int bR, int k) {
    if (aL > aR) return B[bL + k - 1];
    if (bL > bR) return A[aL + k - 1];

    int aMid = (aL + aR) / 2;
    int bMid = (bL + bR) / 2;

    if (A[aMid] <= B[bMid]) {
        if (k <= (aMid - aL) + (bMid - bL) + 1)
            return FindKth(A, aL, aR, B, bL, bMid - 1, k);
        else
            return FindKth(A, aMid + 1, aR, B, bL, bR, k - (aMid - aL) - 1);
    }
    else { // A[aMid] > B[bMid]
        if (k <= (aMid - aL) + (bMid - bL) + 1)
            return FindKth(A, aL, aMid - 1, B, bL, bR, k);
        else
            return FindKth(A, aL, aR, B, bMid + 1, bR, k - (bMid - bL) - 1);
    }
}



//Using recursive 3
We can try to find two mid point index i and j in the two arrays respectively such that A[i] falls in between B[j-1] and B[j] i.e. B[j-1] < A[i] < B[j] . Notice that this guarantees that A[i] is that (i+j+1) th smallest element because there is exactly j elements in B less than A[i] (because A[i] > B[j-1] ) and there are exactly i elements in A less than A[i]. So, if we want to find kth smallest element then we need to find middle element A[i] and B[j]
maintaining the invariant that i+j+1 = k. i.e. i + j = k – 1

Also note that if we have A[i] > B[j] then we know that kth smallest can't be in A[i+1..] or in B[..j]. So we can discard these two halves. This guarantees logarithm search time. Also, note that if A[i] > B[j] then we are already discarding elements in B[..j] which are smaller than kth smallest.

public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return findMedianSortedArrays1(nums1, nums2);
    }
	public static double findMedianSortedArrays1(int A[], int B[]) {
		int m = A.length;
		int n = B.length;

		if ((m + n) % 2 != 0) // odd
			return (double) findKth(A,0, m - 1, B, 0, n - 1, (m + n) / 2);
		else { // even
			return (findKth(A, 0, m - 1, B, 0, n - 1, (m + n) / 2)
				+ findKth(A, 0, m - 1, B, 0, n - 1, (m + n) / 2 - 1)) * 0.5;
		}
	}

	public static int findKth(int A[], int p1, int r1, int B[], int p2, int r2, int k) {
		int n1 = r1-p1+1;
		int n2 = r2-p2+1;

		//base cases
		if(n1 == 0){
			return B[p2+k];
		}
		if(n2 == 0){
			return A[p1+k];
		}
		//
		if(k == 0){
			return Math.min(A[p1], B[p2]);
		}

		//select two index i,j from A and B respectively such that If A[i] is between B[j] and B[j-1]
		//Then A[i] would be the i+j+1 smallest element because.
		//Therefore, if we choose i and j such that i+j = k-1, we are able to find the k-th smallest element.
		int i = n1/(n1+n2)*k;//let's try tp chose a middle element close to kth element in A
		int j = k-1 -i;

		//add the offset
		int mid1 = Math.min(p1+i, r1);
		int mid2 = Math.min(p2+j, r2);

		//mid1 is greater than mid2. So, median is either in A[p1...mid1] or in B[mid2+1...r2].
		//we have already see B[p2..mid2] elements smaller than kth smallest
		if(A[mid1] > B[mid2]){
			k = k - (mid2-p2+1);
			r1 = mid1;
			p2 = mid2+1;
		}
		//mid2 is greater than or equal mid1. So, median is either in A[mid1+1...r1] or in B[p2...mid2].
		//we have already see A[p1..mid1] elements smaller than kth smallest
		else{
			k = k - (mid1-p1+1);
			p1 = mid1+1;
			r2 = mid2;
		}

		return findKth(A, p1, r1, B, p2, r2, k);
	}
}



//Using max and min heap
public double findMedianSortedArrays(int A[], int B[]) {
    //create a min heap and a max heap

    PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>((A.length+B.length)/2+2, new Comparator<Integer>(){
        @Override
       public int compare(Integer a, Integer b)
       {
           if(a>b)
                return 1;
           else if(a<b)
                return -1;
           else
                return 0;
       }
    });
    PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>((A.length+B.length)/2+2, new Comparator<Integer>(){
        @Override
       public int compare(Integer a, Integer b)
       {
           if(a>b)
                return -1;
           else if(a<b)
                return 1;
           else
                return 0;
       }
    });

    //set two pointers for arr A and arr B
    int pA = 0;
    int pB = 0;

    //put one elem in the each heap by making comparsion of A[0] and B[0]
    //if one arr is empty, we should handle it by only puting elem in the heap from the non-empty arr
    if(A.length != 0 && B.length!=0){
        minHeap.add(Math.max(A[pA],B[pB]));
        maxHeap.add(Math.min(A[pA],B[pB]));
    }
    else if(A.length == 0 && B.length!=0)
        minHeap.add(B[pB]);
    else if(A.length != 0 && B.length==0)
        minHeap.add(A[pA]);
    //don't forget increment them to iterate the rest of the arr
    pA++;
    pB++;
    //when iterate the rest, if the arr is not empty, we put the elem in the one the heaps by making comparsion with the
    //peek value of the two heaps
    //each time we need to check if two heap size differ less or equal to 1

    while(pA<A.length || pB<B.length)
    {
        if(pA<A.length){
            if(maxHeap.isEmpty() || A[pA]<=maxHeap.peek())
                maxHeap.add(A[pA]);
            else
                minHeap.add(A[pA]);
        }

        if(pB<B.length){
            if(maxHeap.isEmpty() || B[pB]<=maxHeap.peek())
                maxHeap.add(B[pB]);
            else
                minHeap.add(B[pB]);
        }

        if(minHeap.size()-maxHeap.size()>1)
            maxHeap.add(minHeap.poll());
        else if(maxHeap.size()-minHeap.size()>1)
            minHeap.add(maxHeap.poll());
        pA++;
        pB++;
    }

    //in the end, if even numbers, we get the mean of the two peeks
    //else get the peek from the heap has more elems than the other
    if(minHeap.size()==maxHeap.size())
        return (double)(minHeap.peek()+maxHeap.peek())/2;
    else if(minHeap.size()>maxHeap.size())
        return minHeap.peek();
    else
        return maxHeap.peek();
}
