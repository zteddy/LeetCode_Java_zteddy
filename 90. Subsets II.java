public class Solution {

    public void backtracking(int[] nums, List<List<Integer>> result, List<Integer> now, int index){

        result.add(new ArrayList<Integer>(now));

        for(int i = index+1; i < nums.length; i++){
            if(i > index+1 && nums[i] == nums[i-1]) continue;
            //Arrays.sort+这神奇的一行似乎是这类问题解决duplication的trick
            now.add(nums[i]);
            backtracking(nums,result,now,i);
            now.remove(now.size()-1);
        }

    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        backtracking(nums,result,new ArrayList<Integer>(),-1);

        return result;

    }
}



/*Using iteration
public List<List<Integer>> subsetsWithDup(int[] num) {
  Arrays.sort(num);
  List<List<Integer>> ret = new ArrayList<>();
  ret.add(new ArrayList<Integer>());

  int size = 0, startIndex;
  for(int i = 0; i < num.length; i++) {
    startIndex = (i >= 1 && num[i] == num[i - 1]) ? size : 0;
    size = ret.size();
    for(int j = startIndex; j < size; j++) {
      List<Integer> temp = new ArrayList<>(ret.get(j));
      temp.add(num[i]);
      ret.add(temp);
    }
  }
  return ret;
}
*/



/*Using bit operation
public class Solution {
    public List<List<Integer>> subsetsWithDup(int[] num) {
        Arrays.sort(num);
        List<List<Integer>> lists = new ArrayList<>();
        int subsetNum = 1<<num.length;
        for(int i=0;i<subsetNum;i++){
            List<Integer> list = new ArrayList<>();
            boolean illegal=false;
            for(int j=0;j<num.length;j++){
                if((i>>j&1)==1){
                    if(j>0&&num[j]==num[j-1]&&(i>>(j-1)&1)==0){
                        illegal=true;
                        break;
                    }else{
                        list.add(num[j]);
                    }
                }
            }
            if(!illegal){
               lists.add(list);
            }

        }
        return lists;
    }
}
The idea is using every bit to represent one element in the set. The total number is 2 to num.length. Then we need to avoid duplicates. After we sort the array, the same number will be adjacent to each other.

For example the set is {1,1,1}. We can get subset {} and {1} first. That's great.
Then we need to pay attention. Suppose we have a subset x, which including the second 1 but not the first 1, x is a duplicate.
That's why I write the predicate:
if(j>0&&num[j]==num[j-1]&&(i>>(j-1)&1)==0){
illegal=true;
break;
}

Hope someone can explain it better. I will go to gym now.
*/




/*好像contains能比较list？
The solution of subset II could be easily derived from the answer of subset I.

Here is my answer of subset I:

public class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());

        for (int num: nums) {
            List<List<Integer>> resDup = new ArrayList<>(res);
            for (List<Integer> list:resDup) {
                List<Integer> tmpList = new ArrayList<>(list);
                list.add(num);
                res.add(tmpList);
            }
        }
        return res;
    }
}
In this problem, we need to change two things:

Sort the input nums, so that we won't get lists such as [1,4] and [4, 1] at the same time.
Check duplicates when adding new list to res.
Here is Subset II solution based on subset I solution:
public class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<Integer>());
        Arrays.sort(nums); //important: sort nums

        for (int num: nums) {
            List<List<Integer>> resDup = new ArrayList<>(res);
            for (List<Integer> list: resDup) {
                List<Integer> tmp = new ArrayList<>(list);
                tmp.add(num);
                if (!res.contains(tmp))  //check duplicates
                    res.add(tmp);
            }
        }
        return res;
    }
}
@viditbhatia-hotmail-com contains() method internally will call equals() method,
Below is what equals() method will do:

Compares the specified object with this list for equality. Returns true if and only if the specified object is also a list, both lists have the same size, and all corresponding pairs of elements in the two lists are equal. (Two elements e1 and e2 are equal if (e1==null ? e2==null : e1.equals(e2)).) In other words, two lists are defined to be equal if they contain the same elements in the same order. This definition ensures that the equals method works properly across different implementations of the List interface.
*/




/*Using hashCode()
Two solution, recursion and non-recursion:

Recursion solution:

public class Solution {
    Set<Integer> hash = new HashSet<>();
    List<List<Integer>> res = new ArrayList<>();
    int n;
    int[] nums;

    public void search(List<Integer> l, int p) {
        if (p == n) {
            int h = l.hashCode();
            if (!hash.contains(h)) {
                hash.add(h);
                res.add(new ArrayList<>(l));
            }
            return;
        }
        l.add(nums[p]);
        search(l, p+1);
        l.remove(l.size()-1);
        search(l, p+1);
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        this.n = nums.length; this.nums = nums;
        Arrays.sort(nums);
        search(new ArrayList<Integer>(), 0);
        return res;
    }
}
*/
