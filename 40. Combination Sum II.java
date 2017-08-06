public class Solution {
    public void backtracking(int[] candidates, int target, List<Integer> now, int sumNow, List<List<Integer>> result, int index){
        if(sumNow == target){
            List<Integer> r = new ArrayList<>();
            r.addAll(now);
            result.add(r);
        }
        if(sumNow < target){
            for(int i = index+1; i < candidates.length; i++){
                if(result.size() != 0){
                    if(now.size() == 0 && result.get(result.size()-1).get(0) == candidates[i]){
                        continue;
                    }
                }
                if(i != 0){
                    if(candidates[i] == candidates[i-1] && !now.contains(candidates[i])){
                        continue;
                    }
                }
                now.add(candidates[i]);
                backtracking(candidates,target,now,sumNow+candidates[i],result,i);
                now.remove(now.size()-1);
            }
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(candidates);

        backtracking(candidates,target,new ArrayList<Integer>(),0,result,-1);

        return result;

    }
}


//WA
//Can't handle the duplication problem



//跳过条件666666
public List<List<Integer>> combinationSum2(int[] cand, int target) {
    Arrays.sort(cand);
    List<List<Integer>> res = new ArrayList<List<Integer>>();
    List<Integer> path = new ArrayList<Integer>();
    dfs_com(cand, 0, target, path, res);
    return res;
}
void dfs_com(int[] cand, int cur, int target, List<Integer> path, List<List<Integer>> res) {
    if (target == 0) {
        res.add(new ArrayList(path));
        return ;
    }
    if (target < 0) return;
    for (int i = cur; i < cand.length; i++){
        if (i > cur && cand[i] == cand[i-1]) continue;
        path.add(path.size(), cand[i]);
        dfs_com(cand, i+1, target - cand[i], path, res);
        path.remove(path.size()-1);
    }
}

