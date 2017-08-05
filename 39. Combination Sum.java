public class Solution {

    public void backtracking(int[] candidates, int target, List<Integer> now, int sumNow, List<List<Integer>> result, int index){
        if(sumNow == target){
            //Beacuse it is a reference, we can't directly result.add(now)
            List<Integer> r = new ArrayList<>();
            r.addAll(now);
            result.add(r);
            //more concise using result.add(new ArrayList<>(now));

        }
        if(sumNow < target){
            for(int i = index; i < candidates.length; i++){
                now.add(candidates[i]);
                backtracking(candidates,target,now,sumNow+candidates[i],result,i);
                now.remove(now.size()-1);
            }
        }
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<List<Integer>> result = new ArrayList<>();

        backtracking(candidates,target,new ArrayList<Integer>(),0,result,0);

        return result;

    }
}



/*Using DP
Hi guys!

The main idea reminds an approach for solving coins/knapsack problem - to store the result for all i < target and create the solution from them. For that for each t from 1 to our target we try every candidate which is less or equal to t in ascending order. For each candidate "c" we run through all combinations for target t-c starting with the value greater or equal than c to avoid duplicates and store only ordered combinations.

public class Solution {
    public List<List<Integer>> combinationSum(int[] cands, int t) {
        Arrays.sort(cands); // sort candidates to try them in asc order
        List<List<List<Integer>>> dp = new ArrayList<>();
        for (int i = 1; i <= t; i++) { // run through all targets from 1 to t
            List<List<Integer>> newList = new ArrayList(); // combs for curr i
            // run through all candidates <= i
            for (int j = 0; j < cands.length && cands[j] <= i; j++) {
                // special case when curr target is equal to curr candidate
                if (i == cands[j]) newList.add(Arrays.asList(cands[j]));
                // if current candidate is less than the target use prev results
                else for (List<Integer> l : dp.get(i-cands[j]-1)) {
                    if (cands[j] <= l.get(0)) {
                        List cl = new ArrayList<>();
                        cl.add(cands[j]); cl.addAll(l);
                        newList.add(cl);
                    }
                }
            }
            dp.add(newList);
        }
        return dp.get(t-1);
    }
}
Hope it helps!
*/



/*Using stack
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    Arrays.sort(candidates);
    int i=0, size = candidates.length, sum=0;
    Stack<Integer> combi = new Stack<>(), indices = new Stack<>();
    List<List<Integer>> result = new ArrayList<>();
    while (i < size) {
        if (sum + candidates[i]>= target) {
            if (sum + candidates[i] == target) {
                combi.push(candidates[i]);
                result.add(new ArrayList<>(combi));
                combi.pop();
            }
            // indices stack and combination stack should have the same size all the time
            if (!indices.empty()){
                sum -= combi.pop();
                i = indices.pop();
                while (i == size-1 && !indices.empty()) {
                    i = indices.pop();
                    sum -= combi.pop();

                }
            }
            i++;
        } else {
            combi.push(candidates[i]);
            sum +=candidates[i];
            indices.push(i);
        }
    }
    return result;
}
*/
