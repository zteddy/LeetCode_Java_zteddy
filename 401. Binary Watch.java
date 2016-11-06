public class Solution {
	List<Integer> h;
	List<Integer> m;
	HashMap<Integer, Integer> dm;
	HashMap<Integer, Integer> dh;
	public void hoursBT(int n, int count, HashMap hm){
		if(n <= 4 && n >=0){
			if(n == 0){
				if(!dh.containsKey(count)){
					h.add(count);
					dh.put(count, count);
				}
			}
			else{
				for(int i = 3; i >= 0; i--){
					if(hm.containsKey(i)){
						int a = (int)(count + Math.pow(2,i));
						//count += Math.pow(2,i);
						hm.remove(i);
						if(a < 12) hoursBT(n-1, a, hm);
						hm.put(i,i);
					}
				}
			}
		}
	}

	public void minutesBT(int n, int count, HashMap hm){
		if(n <= 6 && n >=0){
			if(n == 0){
				if(!dm.containsKey(count)){
					m.add(count);
					dm.put(count, count);
				}
			}
			else{
				for(int i = 5; i >= 0; i--){
					if(hm.containsKey(i)){
						int a = (int)(count + Math.pow(2,i));
						hm.remove(i);
						if(a < 60) minutesBT(n-1, a, hm);
						hm.put(i,i);
					}
				}
			}
		}
	}


    public List<String> readBinaryWatch(int num) {
    	List<String> result = new ArrayList<>();
    	String temp;
    	HashMap<Integer, Integer> hmh = new HashMap<>();
    	hmh.put(0,0);
    	hmh.put(1,1);
    	hmh.put(2,2);
    	hmh.put(3,3);
    	HashMap<Integer, Integer> hmm = new HashMap<>();
    	hmm.put(0,0);
    	hmm.put(1,1);
    	hmm.put(2,2);
    	hmm.put(3,3);
    	hmm.put(4,4);
    	hmm.put(5,5);


    	if(num > 10) return new ArrayList<String>();
    	for(int i = num; i >= 0; i--){
    		int j = num - i;
    		m = new ArrayList<>();
    		h = new ArrayList<>();
    		dm = new HashMap<>();
    		dh = new HashMap<>();
    		hoursBT(i, 0, hmh);
    		minutesBT(j, 0, hmm);
    		for(int mm: m){
    			for(int hh: h){
    				temp = new String();
    				temp += (hh+"")+":";
    				if(mm < 10) temp += "0"+(mm+"");
    				else temp += (mm+"");
    				result.add(temp);
    			}
    		}
    	}
    	return result;
    }
}

/*DP
public class Solution {
    public List<String> readBinaryWatch(int num) {
        List<String> res = new ArrayList<>();
        int[] nums1 = new int[]{8, 4, 2, 1}, nums2 = new int[]{32, 16, 8, 4, 2, 1};
        for(int i = 0; i <= num; i++) {
            List<Integer> list1 = generateDigit(nums1, i);
            List<Integer> list2 = generateDigit(nums2, num - i);
            for(int num1: list1) {
                if(num1 >= 12) continue;
                for(int num2: list2) {
                    if(num2 >= 60) continue;
                    res.add(num1 + ":" + (num2 < 10 ? "0" + num2 : num2));
                }
            }
        }
        return res;
    }

    private List<Integer> generateDigit(int[] nums, int count) {
        List<Integer> res = new ArrayList<>();
        generateDigitHelper(nums, count, 0, 0, res);
        return res;
    }

    private void generateDigitHelper(int[] nums, int count, int pos, int sum, List<Integer> res) {
        if(count == 0) {
            res.add(sum);
            return;
        }

        for(int i = pos; i < nums.length; i++) {
            generateDigitHelper(nums, count - 1, i + 1, sum + nums[i], res);
        }
    }
}
*/

/*Super short
public List<String> readBinaryWatch(int num) {
    List<String> times = new ArrayList<>();
    for (int h=0; h<12; h++)
        for (int m=0; m<60; m++)
            if (Integer.bitCount(h * 64 + m) == num)
                times.add(String.format("%d:%02d", h, m));
    return times;
}
*/

/*Just for fun
public class Solution {
		String[][] hour = {{"0"},  // hours contains 0 1's
				   {"1", "2", "4", "8"},   // hours contains 1 1's
				   {"3", "5", "6", "9", "10"},  // hours contains 2 1's
				   {"7", "11"}};  // hours contains 3 1's
		String[][] minute = {{"00"},  // mins contains 0 1's
			             {"01", "02", "04", "08", "16", "32"},  // mins contains 1 1's
			             {"03", "05", "06", "09", "10", "12", "17", "18", "20", "24", "33", "34", "36", "40", "48"},  // mins contains 2 1's
			             {"07", "11", "13", "14", "19", "21", "22", "25", "26", "28", "35", "37", "38", "41", "42", "44", "49", "50", "52", "56"},  // mins contains 3 1's
			             {"15", "23", "27", "29", "30", "39", "43", "45", "46", "51", "53", "54", "57", "58"},  // mins contains 4 1's
			             {"31", "47", "55", "59"}};  // mins contains 5 1's
    public List<String> readBinaryWatch(int num) {
		List<String> ret = new ArrayList();
               // loop from 0 to 3 which is the max number of bits can be set in hours (4 bits)
		for (int i = 0; i <= 3 && i <= n; i++) {
                        // this if condition is to make sure the index from minutes array would be valid
			if (n - i <= 5) {
                               // if we have i 1's in hours, then we need n - i 1's in minutes, that's why the arrays were created by grouping the number of 1's bits
				for (String str1 : hour[i]) {
					for (String str2 : minute[n - i]) {
						ret.add(str1 + ":" + str2);
					}
				}
			}
		}
		return ret;
    }
}
*/


