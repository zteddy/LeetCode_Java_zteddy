public class Solution {

    public void backtracking(int[] nums, List<List<Integer>> result, List<Integer> now, int[] used){

        if(now.size() == nums.length){
            result.add(new ArrayList<Integer>(now));
        }

        for(int i = 0; i < nums.length; i++){
            if(used[i] == 1) continue;
            if(i > 0){
                if(nums[i-1] == nums[i] && used[i-1] == 0) continue;
            }
            //实际上等效于给duplication的num赋予了先后顺序
            //遇到trick的时候仔细考虑duplication是怎么发生的，再解决

            used[i] = 1;
            now.add(nums[i]);
            backtracking(nums,result,now,used);
            now.remove(now.size()-1);
            used[i] = 0;
        }

    }

    public List<List<Integer>> permuteUnique(int[] nums) {

        int[] used = new int[nums.length];
        Arrays.fill(used,0);
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        backtracking(nums,result,new ArrayList<>(),used);

        return result;

    }
}



/*Using swap
public class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums==null || nums.length==0) { return ans; }
        permute(ans, nums, 0);
        return ans;
    }

    private void permute(List<List<Integer>> ans, int[] nums, int index) {
        if (index == nums.length) {
            List<Integer> temp = new ArrayList<>();
            for (int num: nums) { temp.add(num); }
            ans.add(temp);
            return;
        }
        Set<Integer> appeared = new HashSet<>();
        for (int i=index; i<nums.length; ++i) {
            if (appeared.add(nums[i])) {
                swap(nums, index, i);
                permute(ans, nums, index+1);
                swap(nums, index, i);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int save = nums[i];
        nums[i] = nums[j];
        nums[j] = save;
    }
}
*/



/*Using iteration
Hi guys!

Here's an iterative solution which doesn't use nextPermutation helper. It builds the permutations for i-1 first elements of an input array and tries to insert the ith element into all positions of each prebuilt i-1 permutation. I couldn't come up with more effective controling of uniqueness than just using a Set.

See the code below!

public class Solution {
    public List<List<Integer>> permuteUnique(int[] num) {
        LinkedList<List<Integer>> res = new LinkedList<>();
        res.add(new ArrayList<>());
        for (int i = 0; i < num.length; i++) {
            Set<String> cache = new HashSet<>();
            while (res.peekFirst().size() == i) {
                List<Integer> l = res.removeFirst();
                for (int j = 0; j <= l.size(); j++) {
                    List<Integer> newL = new ArrayList<>(l.subList(0,j));
                    newL.add(num[i]);
                    newL.addAll(l.subList(j,l.size()));
                    if (cache.add(newL.toString())) res.add(newL);
                }
            }
        }
        return res;
    }
}
*/
