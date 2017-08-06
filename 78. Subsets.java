public class Solution {
    public void backtracking(int[] nums, List<List<Integer>> result, List<Integer> now, int index){

        result.add(new ArrayList<Integer>(now)); //只在new的时候指定用什么List

        for(int i = index+1; i < nums.length; i++){ //index+1是标准的combination遍历
            now.add(nums[i]);
            backtracking(nums,result,now,i);
            now.remove(now.size()-1);
        }

    }
    public List<List<Integer>> subsets(int[] nums) {

        List<List<Integer>> result = new ArrayList<>(); //只在new的时候指定用什么List

        backtracking(nums,result,new ArrayList<Integer>(),-1);

        return result;
    }
}



/*合集
This structure might apply to many other backtracking questions, but here I am just going to demonstrate Subsets, Permutations, and Combination Sum.

Subsets : https://leetcode.com/problems/subsets/

public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(list, new ArrayList<>(), nums, 0);
    return list;
}

private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
    list.add(new ArrayList<>(tempList));
    for(int i = start; i < nums.length; i++){
        tempList.add(nums[i]);
        backtrack(list, tempList, nums, i + 1);
        tempList.remove(tempList.size() - 1);
    }
}
Subsets II (contains duplicates) : https://leetcode.com/problems/subsets-ii/

public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(list, new ArrayList<>(), nums, 0);
    return list;
}

private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, int start){
    list.add(new ArrayList<>(tempList));
    for(int i = start; i < nums.length; i++){
        if(i > start && nums[i] == nums[i-1]) continue; // skip duplicates
        tempList.add(nums[i]);
        backtrack(list, tempList, nums, i + 1);
        tempList.remove(tempList.size() - 1);
    }
}
Permutations : https://leetcode.com/problems/permutations/

public List<List<Integer>> permute(int[] nums) {
   List<List<Integer>> list = new ArrayList<>();
   // Arrays.sort(nums); // not necessary
   backtrack(list, new ArrayList<>(), nums);
   return list;
}

private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums){
   if(tempList.size() == nums.length){
      list.add(new ArrayList<>(tempList));
   } else{
      for(int i = 0; i < nums.length; i++){
         if(tempList.contains(nums[i])) continue; // element already exists, skip
         tempList.add(nums[i]);
         backtrack(list, tempList, nums);
         tempList.remove(tempList.size() - 1);
      }
   }
}
Permutations II (contains duplicates) : https://leetcode.com/problems/permutations-ii/

public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(list, new ArrayList<>(), nums, new boolean[nums.length]);
    return list;
}

private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, boolean [] used){
    if(tempList.size() == nums.length){
        list.add(new ArrayList<>(tempList));
    } else{
        for(int i = 0; i < nums.length; i++){
            if(used[i] || i > 0 && nums[i] == nums[i-1] && !used[i - 1]) continue;
            used[i] = true;
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, used);
            used[i] = false;
            tempList.remove(tempList.size() - 1);
        }
    }
}
*/



/*Using bit operation
public List<List<Integer>> subsets(int[] S) {
    Arrays.sort(S);
    int totalNumber = 1 << S.length;
    List<List<Integer>> collection = new ArrayList<List<Integer>>(totalNumber);
    for (int i=0; i<totalNumber; i++) {
        List<Integer> set = new LinkedList<Integer>();
        for (int j=0; j<S.length; j++) {
            if ((i & (1<<j)) != 0) {
                set.add(S[j]);
            }
        }
        collection.add(set);
    }
    return collection;
}

 This is an amazing solution.Learnt a lot.Let me try to explain this to those who didn't get the logic.

 Number of subsets for {1 , 2 , 3 } = 2^3 .
 why ?
case    possible outcomes for the set of subsets
  1   ->          Take or dont take = 2
  2   ->          Take or dont take = 2
  3   ->          Take or dont take = 2

therefore , total = 2*2*2 = 2^3 = { { } , {1} , {2} , {3} , {1,2} , {1,3} , {2,3} , {1,2,3} }

Lets assign bits to each outcome  -> First bit to 1 , Second bit to 2 and third bit to 3
Take = 1
Dont take = 0

0) 0 0 0  -> Dont take 3 , Dont take 2 , Dont take 1 = { }
1) 0 0 1  -> Dont take 3 , Dont take 2 ,   take 1       =  {1 }
2) 0 1 0  -> Dont take 3 ,    take 2       , Dont take 1 = { 2 }
3) 0 1 1  -> Dont take 3 ,    take 2       ,      take 1    = { 1 , 2 }
4) 1 0 0  ->    take 3      , Dont take 2  , Dont take 1 = { 3 }
5) 1 0 1  ->    take 3      , Dont take 2  ,     take 1     = { 1 , 3 }
6) 1 1 0  ->    take 3      ,    take 2       , Dont take 1 = { 2 , 3 }
7) 1 1 1  ->    take 3     ,      take 2     ,      take 1     = { 1 , 2 , 3 }

In the above logic ,Insert S[i] only if (j>>i)&1 ==true   { j E { 0,1,2,3,4,5,6,7 }   i = ith element in the input array }

element 1 is inserted only into those places where 1st bit of j is 1
   if( j >> 0 &1 )  ==> for above above eg. this is true for sl.no.( j )= 1 , 3 , 5 , 7

element 2 is inserted only into those places where 2nd bit of j is 1
   if( j >> 1 &1 )  == for above above eg. this is true for sl.no.( j ) = 2 , 3 , 6 , 7

element 3 is inserted only into those places where 3rd bit of j is 1
   if( j >> 2 & 1 )  == for above above eg. this is true for sl.no.( j ) = 4 , 5 , 6 , 7

Time complexity : O(n*2^n) , for every input element loop traverses the whole solution set length i.e. 2^n
*/



/*Using iterative
    The idea is:
起始subset集为：[]
添加S0后为：[], [S0]
添加S1后为：[], [S0], [S1], [S0, S1]
添加S2后为：[], [S0], [S1], [S0, S1], [S2], [S0, S2], [S1, S2], [S0, S1, S2]
红色subset为每次新增的。显然规律为添加Si后，新增的subset为克隆现有的所有subset，并在它们后面都加上Si。
reference: http://bangbingsyb.blogspot.com/2014/11/leetcode-subsets-i-ii.html

// the method below is adding elements and constructing subsets
public List<List<Integer>> subsets2(int[] nums) {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    List<Integer> tmp = new ArrayList<Integer>();
    result.add(tmp); // add empty set
    Arrays.sort(nums);
    for (int i=0; i<nums.length; i++){
        int n =  result.size();
        for(int j=0; j<n; j++){
            // NOTE: must create a new tmp object, and add element to it.
            tmp = new ArrayList<Integer>(result.get(j));
            tmp.add(nums[i]);
            result.add(new ArrayList<Integer>(tmp));
        }
    }
    return result;
}
*/
