public class NumArray {
	public int sumMatrix[][];

    public NumArray(int[] nums) {
    	sumMatrix = new int[nums.length][nums.length];
    	for(int i = 0; i < nums.length; i++){
    		sumMatrix[0][i] = nums[i];
    	}
    	for(int i = 1; i < nums.length; i++){
    		for(int j = 0; j < nums.length-i; j++){
    			sumMatrix[i][j] = nums[j] + sumMatrix[i-1][j+1];
    		}
    	}
    }

    public int sumRange(int i, int j) {
    	return sumMatrix[j-i][i];
    }
}

// Your NumArray object will be instantiated and called as such:
// NumArray numArray = new NumArray(nums);
// numArray.sumRange(0, 1);
// numArray.sumRange(1, 2);

//TLE


/*AC
public class NumArray {
    int[] sum;
    public NumArray(int[] nums) {
        sum = new int[nums.length];
        if(nums.length>0)sum[0]=nums[0];
        for(int i=1; i<nums.length; i++){
            sum[i]=sum[i-1]+nums[i];
        }
    }

    public int sumRange(int i, int j) {
        if(i==0)return sum[j];
        return sum[j]-sum[i-1];
    }
}
*/
