public class Solution {
    public boolean backtracking(int[] stones, int k, int now, HashSet s){
        if(k <= 0) return false;
        int target = stones[now]+k;
        if(!s.contains(target)) return false;


        while(stones[now] != target){
            now++;
            if(now >= stones.length) return false;
        }

        if(now == stones.length-1) return true;

        boolean result = false;

        return backtracking(stones,k-1,now,s)
            | backtracking(stones,k,now,s)
            | backtracking(stones,k+1,now,s);

    }
    public boolean canCross(int[] stones) {
        HashSet<Integer> s = new HashSet<>();
        for(int i = 0; i < stones.length; i++){
            s.add(stones[i]);
        }

        return backtracking(stones,1,0,s);
    }
}

//TLE
//想错方向了，应该Backtracking不行就马上换个方向想

/*Using hashmap
Use map to represent a mapping from the stone (not index) to the steps that can be taken from this stone.

so this will be

[0,1,3,5,6,8,12,17]

{17=[], 0=[1], 1=[1, 2], 3=[1, 2, 3], 5=[1, 2, 3], 6=[1, 2, 3, 4], 8=[1, 2, 3, 4], 12=[3, 4, 5]}

Notice that no need to calculate the last stone.

On each step, we look if any other stone can be reached from it, if so, we update that stone's steps by adding step, step + 1, step - 1. If we can reach the final stone, we return true. No need to calculate to the last stone.

Here is the code:

public boolean canCross(int[] stones) {
    // the most progressive arrange is [0, 1, 3, 6, 10, 15, 21, ...]
    // the right-most point is at most 0 + (1 + len - 1) * (len - 1) / 2
    if(stones == null || stones.length == 0 || stones[1] != 1 ||
                    stones[stones.length - 1] > (stones.length * (stones.length - 1)) / 2) return false;

    if (stones.length == 0) {
        return true;
    }

    HashMap<Integer, HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>(stones.length);
    map.put(0, new HashSet<Integer>());
    map.get(0).add(1);
    for (int i = 1; i < stones.length; i++) {
        map.put(stones[i], new HashSet<Integer>() );
    }

    for (int i = 0; i < stones.length - 1; i++) {
        int stone = stones[i];
        for (int step : map.get(stone)) {
            int reach = step + stone;
            if (reach == stones[stones.length - 1]) {
                return true;
            }
            HashSet<Integer> set = map.get(reach);
            if (set != null) {
                set.add(step);
                if (step - 1 > 0) set.add(step - 1);
                set.add(step + 1);
            }
        }
    }

    return false;
}
*/


/*Using memorization
public class Solution {
    public boolean canCross(int[] stones) {
        if(stones[1] != 1) return false;
        int n = stones.length;
        int[][] memo = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++)
            {
                memo[i][j] = -1;
            }
        }

        return canCross(stones, 0, 0, memo, n);
    }
    private boolean canCross(int[] stones, int i, int j, int[][] memo, int n) {
        if(memo[i][j]!=-1) return memo[i][j] == 1;
        if(i == n - 1) {
            memo[i][j] = 1;
            return true;
        }

        for(int k = i + 1; k < n; k++) {
            if(stones[k] < j - 1 + stones[i]) continue;
            else if(stones[k] > j + 1 + stones[i]) {
                memo[i][j] = 0;
                return false;
            }
            else {
                if(canCross(stones, k, stones[k] - stones[i], memo, n)) {
                    memo[i][j] = 1;
                    return true;
                }
            }
        }
        memo[i][j] = 0;
        return false;
    }
*/



/*跟我想的一样，只是我已经用了set就不要index的话就能过
public class Solution {
     public boolean canCross(int[] stones) {
        if (stones == null || stones.length == 0) {return false;}
        int n = stones.length;
        if (n == 1) {return true;}
        if (stones[1] != 1) {return false;}
        if (n == 2) {return true;}
        int last = stones[n - 1];
        HashSet<Integer> hs = new HashSet();
        for (int i = 0; i < n; i++) {
            if (i > 3 && stones[i] > stones[i - 1] * 2) {return false;} // The two stones are too far away.
            hs.add(stones[i]);
        }
        return canReach(hs, last, 1, 1);
    }

    private boolean canReach(HashSet<Integer> hs, int last, int pos, int jump) {
        if (pos + jump - 1 == last || pos + jump == last || pos + jump + 1 == last) {
            return true;
        }
        if (hs.contains(pos + jump + 1)) {
            if (canReach(hs, last, pos + jump + 1, jump + 1)) {return true;}
        }
        if (hs.contains(pos + jump)) {
            if (canReach(hs, last, pos + jump, jump)) {return true;}
        }
        if (jump > 1 && hs.contains(pos + jump - 1)) {
            if (canReach(hs, last, pos + jump - 1, jump - 1)) {return true;}
        }
        return false;
    }
}
*/
