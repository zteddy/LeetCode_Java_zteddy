public class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
    	Map<Integer, Integer> hm;
    	int target;
    	List temp;
    	List<List<Integer>> result = new ArrayList<>();
    	//Object[] n;
    	// int[] dnums;
    	// hm = new HashMap<>();
    	// int[] n =new int[nums.length];
    	// int k = 0;
    	// for(int i = 0; i < nums.length; i++){
    	// 	if(!hm.containsKey(nums[i])){
    	// 		n[k] = nums[i];
    	// 		hm.put(nums[i], nums[i]);
    	// 		k++;
    	// 	}
    	// }
    	// int length = hm.size();
     	for(int i = 0; i < length; i++){
    		target = 0 - n[i];
    		hm = new HashMap<>();

    		for(int j = 0; j < i; j++){
    			if(j == i) continue;
    			if(hm.containsKey(target - n[j])){
    				temp = new ArrayList();
    				temp.add(n[i]);
    				temp.add(target - n[j]);
    				temp.add(n[j]);
    				result.add(temp);
    			}
    			hm.put(n[j],n[j]);
    		}
    	}
    	return result;
    }
}
//WA Can not handle duplicate


//Using sort & two pointers
public List<List<Integer>> threeSum(int[] num) {
    Arrays.sort(num);
    List<List<Integer>> res = new LinkedList<>();
    for (int i = 0; i < num.length-2; i++) {
        if (i == 0 || (i > 0 && num[i] != num[i-1])) {
            int lo = i+1, hi = num.length-1, sum = 0 - num[i];
            while (lo < hi) {
                if (num[lo] + num[hi] == sum) {
                    res.add(Arrays.asList(num[i], num[lo], num[hi]));
                    while (lo < hi && num[lo] == num[lo+1]) lo++;
                    while (lo < hi && num[hi] == num[hi-1]) hi--;
                    lo++; hi--;
                } else if (num[lo] + num[hi] < sum) {
                    // improve: skip duplicates
                    while (lo < hi && num[lo] == num[lo+1]) lo++;
                    lo++;
                } else {
                    // improve: skip duplicates
                    while (lo < hi && num[hi] == num[hi-1]) hi--;
                    hi--;
                }
            }
        }
    }
    return res;
}

//Remove duplicate
public class Solution {

    int a, b, c;
    List<List<Integer>> result = new ArrayList();

    public List<List<Integer>> threeSum(int[] num) {
        Arrays.sort(num);

        for (int i = 0 ; i <= num.length - 3; i++) {
            a = num[i];
            for (int j = i+1, k = num.length - 1; j < k;) {
                b = num[j];
                c = num[k];
                if (b + c == -1*a) {
                    List list = new ArrayList<Integer>();
                    list.add(a);
                    list.add(b);
                    list.add(c);
                    result.add(list);
                    j++;
                    k--;
                } else if (b + c < -1*a) {
                    j++;
                } else {
                    k--;
                }
            }
        }

        // remove duplicated items.
        for (int i = result.size() - 1; i >= 1; i--) {
            for (int j = i-1; j >= 0; j--) {
                if (result.get(i).get(0) == result.get(j).get(0)
                    && result.get(i).get(1) == result.get(j).get(1)
                    && result.get(i).get(2) == result.get(j).get(2)) {
                    result.remove(j);
                    i--;
                }
            }
        }
        return result;
    }

}


