public class Solution {
	public class UF{
		int[] id;
		int[] size;
		int count;
		public UF(int n){
			id = new int[n];
			size = new int[n];
			count = n;
			for(int i = 0; i < n; i++){
				id[i] = i;
				size[i] = 1;
			}
		}
		public int find(int a){
			while(a != id[a]){
				id[a] = id[id[a]];
				a = id[a];
			}
			return a;
		}
		public void union(int a, int b){
			int aa = find(a);
			int bb = find(b);
			if(size[aa] > size[bb]){
				id[bb] = aa;
				size[aa] += size[bb];
			}
			else{
				id[aa] = bb;
				size[bb] += size[aa];
			}
			count--;
		}
	}
    public int longestConsecutive(int[] nums) {

    }
}

//感觉思维被UF定死了
//用一个map寸value-index才是关键


//Brilliant solution using hashmap
We will use HashMap. The key thing is to keep track of the sequence length and store that in the boundary points of the sequence. For example, as a result, for sequence {1, 2, 3, 4, 5}, map.get(1) and map.get(5) should both return 5.

Whenever a new element n is inserted into the map, do two things:

See if n - 1 and n + 1 exist in the map, and if so, it means there is an existing sequence next to n. Variables left and right will be the length of those two sequences, while 0 means there is no sequence and n will be the boundary point later. Store (left + right + 1) as the associated value to key n into the map.
Use left and right to locate the other end of the sequences to the left and right of n respectively, and replace the value with the new length.
Everything inside the for loop is O(1) so the total time is O(n). Please comment if you see something wrong. Thanks.

public int longestConsecutive(int[] num) {
    int res = 0;
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int n : num) {
        if (!map.containsKey(n)) {
            int left = (map.containsKey(n - 1)) ? map.get(n - 1) : 0;
            int right = (map.containsKey(n + 1)) ? map.get(n + 1) : 0;
            // sum: length of the sequence n is in
            int sum = left + right + 1;
            map.put(n, sum);

            // keep track of the max length
            res = Math.max(res, sum);

            // extend the length to the boundary(s)
            // of the sequence
            // will do nothing if n has no neighbors
            map.put(n - left, sum);
            map.put(n + right, sum);
        }
        else {
            // duplicates
            continue;
        }
    }
    return res;
}



//Brilliant solution
First turn the input into a set of numbers. That takes O(n) and then we can ask in O(1) whether we have a certain number.

Then go through the numbers. If the number x is the start of a streak (i.e., x-1 is not in the set), then test y = x+1, x+2, x+3, ... and stop at the first number y not in the set. The length of the streak is then simply y-x and we update our global best with that. Since we check each streak only once, this is overall O(n). This ran in 44 ms on the OJ, one of the fastest Python submissions.

def longestConsecutive(self, nums):
    nums = set(nums)
    best = 0
    for x in nums:
        if x - 1 not in nums:
            y = x + 1
            while y in nums:
                y += 1
            best = max(best, y - x)
    return best



//Using boundary
use a hash map to store boundary information of consecutive sequence for each element; there 4 cases when a new element i reached:

neither i+1 nor i-1 has been seen: m[i]=1;

both i+1 and i-1 have been seen: extend m[i+m[i+1]] and m[i-m[i-1]] to each other;

only i+1 has been seen: extend m[i+m[i+1]] and m[i] to each other;

only i-1 has been seen: extend m[i-m[i-1]] and m[i] to each other.

int longestConsecutive(vector<int> &num) {
	unordered_map<int, int> m;
	int r = 0;
	for (int i : num) {
		if (m[i]) continue;
		r = max(r, m[i] = m[i + m[i + 1]] = m[i - m[i - 1]] = m[i + 1] + m[i - 1] + 1);
	}
	return r;
}



//Using set
Using a set to collect all elements that hasnt been visited. search element will be O(1) and eliminates visiting element again.

public class Solution {
	public int longestConsecutive(int[] nums) {
	    if(nums == null || nums.length == 0) return 0;

	    Set<Integer> set = new HashSet<Integer>();

	    for(int num: nums) set.add(num);
	    int max = 1;
	    for(int num: nums) {
	        if(set.remove(num)) {//num hasn't been visited
	            int val = num;
	            int sum = 1;
	            while(set.remove(val-1)) val--;
	            sum += num - val;

	            val = num;
	            while(set.remove(val+1)) val++;
	            sum += val - num;

	            max = Math.max(max, sum);
	        }
	    }
	    return max;
	}
}



//Using union-find
I implemented the following code which is a slight improvement over the one suggested in the original post. I have optimized the retrieval of the maxUnion (getHighestRank in my case) to O(1).

Also, @liangyue268 Weighted Union Find with Path Compression isnt exactly O(nlogn) but more close to O(n). This is because the path compression function (see find operation) exhibits growth which follows the Inverse Ackermann Function. Both union and find are not exactly 1 but are very very very close to 1, visit here for more details.

import java.util.Map;
import java.util.HashMap;

public class Solution {
    public int longestConsecutive(int[] nums) {
        final int length = nums.length;
        if (length <= 1) return length;

        final Map<Integer, Integer> elementIndexMap = new HashMap();
        final UnionFind uf = new UnionFind(length);
        for (int p = 0; p < length; p++) {
            final int i = nums[p];
            if (elementIndexMap.containsKey(i)) continue;
            if (elementIndexMap.containsKey(i+1)) uf.union(p, elementIndexMap.get(i+1));
            if (elementIndexMap.containsKey(i-1)) uf.union(p, elementIndexMap.get(i-1));
            elementIndexMap.put(i, p);
        }
        return uf.getHighestRank();
    }

    private final class UnionFind {
        final private int[] sequenceTree;
        final private int[] rank;
        private int highestRank;

        UnionFind(int length) {
            sequenceTree = new int[length];
            rank = new int[length];
            highestRank = 1;
            for (int i = 0; i < length; i++) {
                sequenceTree[i] = i;
                rank[i] = 1;
            }
        }

        void union(int p, int q) {
            final int pId = find(p); final int qId = find(q);

            if (pId == qId) return;

            int localHighestRank = 1;
            if (rank[pId] < rank[qId]) {
                sequenceTree[pId] = qId;
                rank[qId] += rank[pId];
                localHighestRank = rank[qId];
            } else {
                sequenceTree[qId] = pId;
                rank[pId] += rank[qId];
                localHighestRank = rank[pId];
            }
            highestRank = Math.max(highestRank, localHighestRank);
        }

        int find(int p) {
            while (p != sequenceTree[p]) {
                sequenceTree[p] = sequenceTree[sequenceTree[p]];
                p = sequenceTree[p];
            }
            return p;
        }

        int getHighestRank() { return highestRank; }
    }
}



//Using boundary2
Use a hashmap to map a number to its longest consecutive sequence length, each time find a new consecutive sequence, only the begin number and end number need to be modified.

public class Solution {
    public int longestConsecutive(int[] num) {
        int longest = 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0;i < num.length;i++){
            // if there is no duplicates, these two lines can be commented
            if(map.containsKey(num[i])) continue;
            map.put(num[i],1);

            int end = num[i];
            int begin = num[i];
            if(map.containsKey(num[i]+1))
                end = num[i] + map.get(num[i]+1);
            if(map.containsKey(num[i]-1))
                begin = num[i] - map.get(num[i]-1);
            longest = Math.max(longest, end-begin+1);
            map.put(end, end-begin+1);
            map.put(begin, end-begin+1);
        }
        return longest;
    }
}
