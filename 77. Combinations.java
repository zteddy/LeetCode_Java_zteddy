public class Solution {

    public void backtracking(int n, List<List<Integer>> result, List<Integer> now, int k, int index){

        if(now.size() == k){
            result.add(new ArrayList<Integer>(now));
        }

        for(int i = index+1; i <= n; i++){
            now.add(i);
            backtracking(n,result,now,k,i);
            now.remove(now.size()-1);
        }

    }

    public List<List<Integer>> combine(int n, int k) {


        List<List<Integer>> result = new ArrayList<>();

        backtracking(n,result,new ArrayList<>(),k,0);

        return result;

    }
}



/*Using mathmatics
Basically, this solution follows the idea of the mathematical formula C(n,k)=C(n-1,k-1)+C(n-1,k).

Here C(n,k) is divided into two situations. Situation one, number n is selected, so we only need to select k-1 from n-1 next. Situation two, number n is not selected, and the rest job is selecting k from n-1.

public class Solution {
    public List<List<Integer>> combine(int n, int k) {
        if (k == n || k == 0) {
            List<Integer> row = new LinkedList<>();
            for (int i = 1; i <= k; ++i) {
                row.add(i);
            }
            return new LinkedList<>(Arrays.asList(row));
        }
        List<List<Integer>> result = this.combine(n - 1, k - 1);
        result.forEach(e -> e.add(n));
        result.addAll(this.combine(n - 1, k));
        return result;
    }
}
*/



/*Using iteration
Hi guys!

The idea is to iteratively generate combinations for all lengths from 1 to k. We start with a list of all numbers <= n as combinations for k == 1. When we have all combinations of length k-1, we can get the new ones for a length k with trying to add to each one all elements that are <= n and greater than the last element of a current combination.

I think the code here will be much more understandable than further attempts to explain. :) See below.

Hope it helps!

public class Solution {
    public List<List<Integer>> combine(int n, int k) {
        if (k == 0 || n == 0 || k > n) return Collections.emptyList();
        List<List<Integer>> combs = new ArrayList<>();
        for (int i = 1; i <= n; i++) combs.add(Arrays.asList(i));
        for (int i = 2; i <= k; i++) {
            List<List<Integer>> newCombs = new ArrayList<>();
            for (int j = i; j <= n; j++) {
                for (List<Integer> comb : combs) {
                    if (comb.get(comb.size()-1) < j) {
                        List<Integer> newComb = new ArrayList<>(comb);
                        newComb.add(j);
                        newCombs.add(newComb);
                    }
                }
            }
            combs = newCombs;
        }
        return combs;
    }
}
*/



/*Add a small trick to end search early
public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> results = new ArrayList<>();
    dfs(1, n, k, new ArrayList<Integer>(), results);
    return results;
}

private void dfs(int crt, int n, int level, List<Integer> comb, List<List<Integer>> results) {
    if (level == 0) {
        List<Integer> newComb = new ArrayList<>(comb);
        results.add(newComb);
        return;
    }
    int size = comb.size();
    for (int i = crt, max = n - level + 1; i <= max; i++) {
    //end search when its not possible to have any combination
        comb.add(i);
        dfs(i + 1, n, level - 1, comb, results);
        comb.remove(size);
    }
}
*/
