public class NumArray {
	public class SegmentTree(){
		public SegmentTreeNode root;
		public class SegmentTreeNode(){
			public int left;
			public int right;
			public int sum;
			SegmentTreeNode leftchild;
			SegmentTreeNode rightchild;
		}
		public SegmentTree(int[] nums){
			l = nums.length;
			if(l/2*2 != l) l++;
			SegmentTreeNode[] s1= new SegmentTreeNode[l];
			for(int i = 0; i < l; i++){
				s1[i].left = i;
				s1[i].right = i;
				s1[i].sum = (l>nums.length?) 0:nums[i];
			}
			l = l/2;
			SegmentTreeNode[] s2;
			while(l != 0){
				s2= new SegmentTreeNode[l];
				for(int i = 0; i < l; i++){
					s2[i].leftchild = s1[2*i];
					s2[i].rightchild = s1[2*i+1];
					s2[i].left = s2[i].leftchild.left;
					s2[i].right = s2[i].rightchild.right;
					s2[i].sum = s2[i].leftchild.sum+s2[i].rightchild.sum;
				}
				s1 = s2;
				l = l/2;
			}
			root = s2[0];
		}
		public int search(int i, int j, SegmentTreeNode n){
			int mid = (n.left+n.right)/2;
			if(mid)

		}


	}


    public NumArray(int[] nums) {

    }

    void update(int i, int val) {

    }

    public int sumRange(int i, int j) {

    }
}


// Your NumArray object will be instantiated and called as such:
// NumArray numArray = new NumArray(nums);
// numArray.sumRange(0, 1);
// numArray.update(1, 10);
// numArray.sumRange(1, 2);

//WA



/*Sqrt decomposition
private int[] b;
private int len;
private int[] nums;

public NumArray(int[] nums) {
    this.nums = nums;
    double l = Math.sqrt(nums.length);
    len = (int) Math.ceil(nums.length/l);
    b = new int [len];
    for (int i = 0; i < nums.length; i++)
        b[i / len] += nums[i];
}

public int sumRange(int i, int j) {
    int sum = 0;
    int startBlock = i / len;
    int endBlock = j / len;
    if (startBlock == endBlock) {
        for (int k = i; k <= j; k++)
            sum += nums[k];
    } else {
        for (int k = i; k <= (startBlock + 1) * len - 1; k++)
            sum += nums[k];
        for (int k = startBlock + 1; k <= endBlock - 1; k++)
            sum += b[k];
        for (int k = endBlock * len; k <= j; k++)
            sum += nums[k];
    }
    return sum;
}

public void update(int i, int val) {
    int b_l = i / len;
    b[b_l] = b[b_l] - nums[i] + val;
    nums[i] = val;
}
// Accepted
*/



/*Segment tree
	int[] tree;
	int n;
	public NumArray(int[] nums) {
	    if (nums.length > 0) {
	        n = nums.length;
	        tree = new int[n * 2];
	        buildTree(nums);
	    }
	}
	private void buildTree(int[] nums) {
	    for (int i = n, j = 0;  i < 2 * n; i++,  j++)
	        tree[i] = nums[j];
	    for (int i = n - 1; i > 0; --i)                //index start from 1
	        tree[i] = tree[i * 2] + tree[i * 2 + 1];
	}

	void update(int pos, int val) {
	    pos += n;
	    tree[pos] = val;
	    while (pos > 0) {
	        int left = pos;
	        int right = pos;
	        if (pos % 2 == 0) {
	            right = pos + 1;
	        } else {
	            left = pos - 1;
	        }
	        // parent is updated after child is updated
	        tree[pos / 2] = tree[left] + tree[right];
	        pos /= 2;
	    }
	}

	public int sumRange(int l, int r) {
	    // get leaf with value 'l'
	    l += n;
	    // get leaf with value 'r'
	    r += n;
	    int sum = 0;
	    while (l <= r) {
	        if ((l % 2) == 1) {
	           sum += tree[l];
	           l++;
	        }
	        if ((r % 2) == 0) {
	           sum += tree[r];
	           r--;
	        }
	        l /= 2;
	        r /= 2;
	    }
	    return sum;
}
*/
