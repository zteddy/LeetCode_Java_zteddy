public class Solution {

    public int[] rotate(int[] nums, int k) { //这里传的nums是reference！！！！！

    	/*
    	int[] space = new int[k];
    	int i;

    	k = k % nums.length;

    	//Too hard to count the array lable. Write it down! Draw it!
    	for(i = 0; i < k; i++){
    		space[i] = nums[i + (nums.length-k)];
    	}

    	for(i = nums.length - 1; i >= k; i--){
    		nums[i] = nums[i-k];
    	}

    	for(i = 0; i < k; i++){
    		nums[i] = space[i];
    	}
    	return nums;
    	*/


		int[] space = new int[k];
		int i;
		int aaa[] = new int[nums.length];

		k = k % nums.length;

		//Too hard to count the array lable. Write it down! Draw it!
		for(i = 0; i < k; i++){
			space[i] = nums[i + (nums.length-k)];
		}

		for(i = nums.length - 1; i >= k; i--){
			aaa[i] = nums[i-k];
		}

		for(i = 0; i < k; i++){
			aaa[i] = space[i];
		}
		return aaa;
    }

	public int function(int[] nums){

		int result = 0;
		int i;

		for(i = 0; i< nums.length; i++){
			result = result + i*nums[i];
		}

		return result;

	}

    public int maxRotateFunction(int[] A) { //这里传的A是reference！！！！！

    	if(A.length == 0) return 0;

		int i;
		int max = Integer.MIN_VALUE;
		int temp;
		int index = -1;

		//max = function(rotate(A, 0)); //inti max
		for(i = 0; i < A.length+1; i++){

			temp = function(rotate(A, i));
			//if(i == 4) return temp;
			//if(i == 0){max = temp;} //inti max
			if(max < temp){max = temp; index = i;}
			//if(i == 4) return temp;

		}

		//return index;


		//return function(rotate(A, 4));
		return max;
		//[-27,186,136,149,50,177,90,111,141,96,151,63,161,120,125,120,7,-72]
    }
}


/*Using math

F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1]
F(k-1) = 0 * Bk-1[0] + 1 * Bk-1[1] + ... + (n-1) * Bk-1[n-1]
       = 0 * Bk[1] + 1 * Bk[2] + ... + (n-2) * Bk[n-1] + (n-1) * Bk[0]

Then,

F(k) - F(k-1) = Bk[1] + Bk[2] + ... + Bk[n-1] + (1-n)Bk[0]
              = (Bk[0] + ... + Bk[n-1]) - nBk[0]
              = sum - nBk[0]

Thus,

F(k) = F(k-1) + sum - nBk[0]

What is Bk[0]?

k = 0; B[0] = A[0];
k = 1; B[0] = A[len-1];
k = 2; B[0] = A[len-2];
...


public class Solution {

    public int maxRotateFunction(int[] A) {

int allSum = 0;
int len = A.length;
int F = 0;
for (int i = 0; i < len; i++) {
    F += i * A[i];
    allSum += A[i];
}
int max = F;
int index = -1;
for (int i = len - 1; i >= 1; i--) {
    F = F + allSum - len * A[i];
    if(max < F) { max = F; index = i;}
    max = Math.max(F, max);
}
return index;

    }
}
*/
