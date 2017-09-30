class Solution {

    public void backtracking(int n, List<Integer> tempResult, List<List<Integer>> result, Map<Integer,List<List<Integer>>> memo, boolean isFirst){
        if(n == 1){
            result.add(new ArrayList(tempResult));
            return ;
        }

        //TODO: use memo
        for(int i = (isFirst?2:tempResult.get(tempResult.size()-1)); i <= (isFirst?n-1:n); i++){
            if(n%i == 0){
                tempResult.add(i);
                backtracking(n/i,tempResult,result,memo,false);
                tempResult.remove(tempResult.size()-1);
            }
        }
    }


    public List<List<Integer>> getFactors(int n) {

        Map<Integer,List<List<Integer>>> memo = new HashMap<>();
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> tempResult = new ArrayList<>();

        if(n == 1) return result;

        backtracking(n,tempResult,result,memo,true);

        return result;

    }
}






/*top-down
Let's consider how to find a list of candidates that forms n1*n2=n, let's call [n1,n2] a 2-tuple, we loop from 2 to sqrt(n) and find all n1 that is a factor of n, then n2=n/n1. n1<n2 always.

how to build our solution that from above sub-problem? Essentially you ask all the N-tuples that can multiply to n. and N-tuple is made up by N-1-tuple plus one number. Above described process is clearly a recursion, and the termination condition is you've tried all combinations.

But what forms the combination? suppose a number n%i==0, a full set would be

2-tuple, [i, n/i]
N-tuple (N>2) eg. [i, all N-tuples of (n/i)]
So below is the bottom up approach

public List<List<Integer>> getFactors(int n) {
    return getFactors(n, 2);
}

private List<List<Integer>> getFactors(int n, int min) {
    List<List<Integer>> list = new LinkedList<>();

    int end = (int) Math.sqrt(n);
    for (int i = min; i <= end; i++) {
        if (n % i != 0) continue;
        list.add(new LinkedList<>(Arrays.asList(i, n / i)));//add 2-tuple

        List<List<Integer>> prev = getFactors(n / i, i);
        for (List<Integer> inner : prev) ((LinkedList<Integer>) inner).addFirst(i);
        list.addAll(prev);//add N-tuple(N>2)
    }

    return list;
}
And top down using stack.

public List<List<Integer>> getFactors(int n) {

    List<List<Integer>> res = new ArrayList<>();
    combination(n, 2, new Stack<>(), res);
    return res;
}

public void combination(int n, int start, Stack<Integer> stack, List<List<Integer>> res) {

    int end = (int) Math.sqrt(n);
    for (int i = start; i <= end; i++) {
        if (n % i != 0) continue;
        res.add(new ArrayList<>(stack));
        res.get(res.size() - 1).add(i);
        res.get(res.size() - 1).add(n / i);

        stack.push(i);
        combination(n / i, i, stack, res);
        stack.pop();
    }
}
*/




/*More Concise
public List<List<Integer>> getFactors(int n) {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    helper(result, new ArrayList<Integer>(), n, 2);
    return result;
}

public void helper(List<List<Integer>> result, List<Integer> item, int n, int start){
    if (n <= 1) {
        if (item.size() > 1) {
            result.add(new ArrayList<Integer>(item));
        }
        return;
    }

    for (int i = start; i <= n; ++i) {
        if (n % i == 0) {
            item.add(i);
            helper(result, item, n/i, i);
            item.remove(item.size()-1);
        }
    }
}

2 small changes make it much faster, please look my comments for the 2 changes in the code

public class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> results = new ArrayList<>();
        if (n <=3) {
            return results;
        }

        getFactors(n, 2, new ArrayList<Integer>(), results);
        return results;
    }

    private void getFactors(int n, int start, List<Integer> current, List<List<Integer>> results) {
        if (n == 1) {
            if (current.size() > 1) {
                results.add(new ArrayList<Integer>(current));
            }
            return;
        }


        for (int i = start; i <= (int) Math.sqrt(n); i++) {  // ==> here, change 1
            if (n % i != 0) {
                continue;
            }
            current.add(i);
            getFactors(n/i, i, current, results);
            current.remove(current.size()-1);
        }

        int i = n; // ===> here, change 2
        current.add(i);
        getFactors(n/i, i, current, results);
        current.remove(current.size()-1);
    }
}
*/
