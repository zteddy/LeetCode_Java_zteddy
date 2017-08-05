public class Solution {

    public void backtracking(int k, int n, List<Integer> now, int sumNow, List<List<Integer>> result, int index){
        if(sumNow == n && now.size() == k){
            List<Integer> r = new ArrayList<>();
            r.addAll(now);
            result.add(r);
        }
        if(sumNow < n && now.size() < k){
            for(int i = index+1; i < 10; i++){ //index+1 to avoid duplication, give a order of choices
                now.add(i);
                backtracking(k,n,now,sumNow+i,result,i);
                now.remove(now.size()-1);
            }
        }
    }

    public List<List<Integer>> combinationSum3(int k, int n) {

        List<List<Integer>> result = new ArrayList<>();

        backtracking(k,n,new ArrayList<Integer>(),0,result,0);

        return result;

    }
}
