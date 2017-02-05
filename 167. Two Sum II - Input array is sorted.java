public class Solution {
    public int[] twoSum(int[] numbers, int target) {
    	Map<Integer, Integer> hm = new HashMap<>();
    	int[] result = new int[2];
    	for(int i = 0; i < numbers.length; i++){
    		if(hm.containsKey(numbers[i])){
    			result[0] = hm.get(numbers[i])+1;
    			result[1] = i+1;
    		}
    		else{
    			hm.put(target-numbers[i], i);
    		}
    	}
    	return result;
    }
}



/*Using two pointers
public int[] twoSum(int[] num, int target) {
    int[] indice = new int[2];
    if (num == null || num.length < 2) return indice;
    int left = 0, right = num.length - 1;
    while (left < right) {
        int v = num[left] + num[right];
        if (v == target) {
            indice[0] = left + 1;
            indice[1] = right + 1;
            break;
        } else if (v > target) {
            right --;
        } else {
            left ++;
        }
    }
    return indice;
}

The code is clean and elegant, but I would change one line:
long v = num[left] + num[right];
in case of the integer overflow.
*/



/*Using binary search
vector<int> twoSum(vector<int> &numbers, int target) {
    if(numbers.empty()) return {};
    for(int i=0; i<numbers.size()-1; i++) {
        int start=i+1, end=numbers.size()-1, gap=target-numbers[i];
        while(start <= end) {
            int m = start+(end-start)/2;
            if(numbers[m] == gap) return {i+1,m+1};
            else if(numbers[m] > gap) end=m-1;
            else start=m+1;
        }
    }
}
*/
