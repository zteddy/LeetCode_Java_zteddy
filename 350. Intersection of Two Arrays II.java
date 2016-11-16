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

Follow up:
What if the given array is already sorted? How would you optimize your algorithm?

/*Using sort
Arrays.sort(nums1);
  Arrays.sort(nums2);
  int pnt1 = 0;
  int pnt2 = 0;
  ArrayList<Integer> myList = new ArrayList<Integer>();
  while((pnt1 < nums1.length) &&(pnt2< nums2.length)){
      if(nums1[pnt1]<nums2[pnt2]){
          pnt1++;
      }
      else{
          if(nums1[pnt1]>nums2[pnt2]){
              pnt2++;
          }
          else{
              myList.add(nums1[pnt1]);
              pnt1++;
              pnt2++;
          }
      }
  }
  int[] res = new int[myList.size()];
  for(int i = 0; i<res.length; i++){
      res[i] = (Integer)myList.get(i);
  }
  return res;
}
*/



What if nums1's size is small compared to nums2's size? Which algorithm is better?


/*
class Solution {
public:
    vector<int> intersect(vector<int>& nums1, vector<int>& nums2) {
        unordered_map<int, int> mymap;
        vector<int> res;
        for(auto it : nums1) {
            mymap[it]++;
        }
        for(auto it : nums2) {
            if(--mymap[it] >= 0) {
                res.push_back(it);
            }
        }
        return res;

    }
};
*/



What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?



If only nums2 cannot fit in memory, put all elements of nums1 into a HashMap, read chunks of array that fit into the memory, and record the intersections.

If both nums1 and nums2 are so huge that neither fit into the memory, sort them individually (external sort), then read 2 elements from each array at a time in memory, record intersections.

6 months ago reply quote
FOLLOW-UP 19 13
POSTS 4.5k
VIEWS Reply Back To Leetcode    Mark unread   Not Watching   Sort by
20
L lzb700m
Reputation:  180
Thanks for the solution. I think the second part of the solution is impractical, if you read 2 elements at a time, this procedure will take forever. In principle, we want minimize the number of disk access during the run-time.

An improvement can be sort them using external sort, read (lets say) 2G of each into memory and then using the 2 pointer technique, then read 2G more from the array that has been exhausted. Repeat this until no more data to read from disk.

But I am not sure this solution is good enough for an interview setting. Maybe the interviewer is expecting some solution using Map-Reduce paradigm.

