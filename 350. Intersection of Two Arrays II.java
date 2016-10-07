public class Solution {

	public int[] toIntArray(List<Integer> list){
		int[] ret = new int[list.size()];
		for(int i = 0;i < ret.length;i++)
		    ret[i] = list.get(i);
		return ret;
	}

    public int[] intersect(int[] nums1, int[] nums2) {

    	Map<Integer, Integer> hm = new HashMap<>();

    	List<Integer> result = new ArrayList<>();

    	for(int i = 0; i < nums1.length; i++){
    		if(!hm.containsKey(nums1[i])) hm.put(nums1[i], 1);
    		else hm.put(nums1[i],hm.get(nums1[i])+1); //Can't use ++ use +1 !!!
    		/*
    		else{
    			Integer temp;
    			temp = Integer.parseInt(hm.get(nums1[i]).toString());
    			hm.put(nums1[i],temp++);
    		}
    		*/
    	}

    	for(int i = 0; i < nums2.length; i++){
    		if(hm.containsKey(nums2[i])){
    			if(hm.get(nums2[i]) > 0){
    				result.add(nums2[i]);
    				hm.put(nums2[i],hm.get(nums2[i])-1);
    			}
    			/*
    			Integer temp;
    			temp = Integer.parseInt(hm.get(nums2[i]).toString());
    			if(temp > 0){
    				hm.put(nums2[i],temp--);
    				result.add(temp);
    			}
    			*/

    		}

    	}
    	
    	int[] r = toIntArray(result);

    	return r;
    }
}

//太多复杂的类型转换！！！
//TODO None