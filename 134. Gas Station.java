public class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
    	if(gas.length == 0) return -1;
    	int balance[] = new int[gas.length];
    	for(int i = 0; i < gas.length; i++){
    		balance[i] = gas[i] - cost[i];
    	}
    	int currentGas = 0;
    	int x;
    	boolean check;
    	boolean prev = (balance[gas.length-1] < 0);
    	ArrayList<Integer> wait = new ArrayList<>();
    	for(int i = 0; i < gas.length; i++){
    		if(prev && balance[i] >= 0) wait.add(i);
    		prev = (balance[i] < 0);
    	}
    	if(wait.size() == 0) return balance[0] >= 0 ? 0 : -1;
    	for(int i : wait){
    		//if(prev && balance[i] >= 0){
    			check = true;
    			for(int j = 0; j < gas.length; j++){
    				x = (i+j)%gas.length;
    				currentGas += balance[x];
    				if(currentGas < 0) check = false;
    			}
    			if(check) return i;
    		//}
    		// = (balance[i] < 0);
    	}
    	return -1;
    }
}


/*
I have thought for a long time and got two ideas:

If car starts at A and can not reach B. Any station between A and B
can not reach B.(B is the first station that A can not reach.)
If the total number of gas is bigger than the total number of cost. There must be a solution.
(Should I prove them?)
Here is my solution based on those ideas:

class Solution {
public:
    int canCompleteCircuit(vector<int> &gas, vector<int> &cost) {
        int start(0),total(0),tank(0);
        //if car fails at 'start', record the next station
        for(int i=0;i<gas.size();i++) if((tank=tank+gas[i]-cost[i])<0) {start=i+1;total+=tank;tank=0;}
        return (total+tank<0)? -1:start;
    }
};

Proof to the first point: say there is a point C between A and B -- that is A can reach C but cannot reach B. Since A cannot reach B, the gas collected between A and B is short of the cost. Starting from A, at the time when the car reaches C, it brings in gas >= 0, and the car still cannot reach B. Thus if the car just starts from C, it definitely cannot reach B.

Proof for the second point:

If there is only one gas station, it’s true.

If there are two gas stations a and b, and gas(a) cannot afford cost(a), i.e., gas(a) < cost(a), then gas(b) must be greater than cost(b), i.e., gas(b) > cost(b), since gas(a) + gas(b) > cost(a) + cost(b); so there must be a way too.

If there are three gas stations a, b, and c, where gas(a) < cost(a), i.e., we cannot travel from a to b directly, then:
either if gas(b) < cost(b), i.e., we cannot travel from b to c directly, then cost(c) > cost(c), so we can start at c and travel to a; since gas(b) < cost(b), gas(c) + gas(a) must be greater than cost(c) + cost(a), so we can continue traveling from a to b. Key Point: this can be considered as there is one station at c’ with gas(c’) = gas(c) + gas(a) and the cost from c’ to b is cost(c’) = cost(c) + cost(a), and the problem reduces to a problem with two stations. This in turn becomes the problem with two stations above.

or if gas(b) >= cost(b), we can travel from b to c directly. Similar to the case above, this problem can reduce to a problem with two stations b’ and a, where gas(b’) = gas(b) + gas(c) and cost(b’) = cost(b) + cost(c). Since gas(a) < cost(a), gas(b’) must be greater than cost(b’), so it’s solved too.

For problems with more stations, we can reduce them in a similar way. In fact, as seen above for the example of three stations, the problem of two stations can also reduce to the initial problem with one station.

More readable version: http://blog.shengwei.li/leetcode-gas-station/
*/




/*
We prove the following statement.
If sum of all gas[i]-cost[i] is greater than or equal to 0, then there is a start position you can travel the whole circle.
Let i be the index such that the the partial sum

gas[0]-cost[0]+gas[1]-cost[1]+...+gas[i]-cost[i]
is the smallest, then the start position should be start=i+1 ( start=0 if i=n-1). Consider any other partial sum, for example,

gas[0]-cost[0]+gas[1]-cost[1]+...+gas[i]-cost[i]+gas[i+1]-cost[i+1]
Since gas[0]-cost[0]+gas[1]-cost[1]+...+gas[i]-cost[i] is the smallest, we must have

gas[i+1]-cost[i+1]>=0
in order for gas[0]-cost[0]+gas[1]-cost[1]+...+gas[i]-cost[i]+gas[i+1]-cost[i+1] to be greater.
The same reasoning gives that

 gas[i+1]-cost[i+1]>=0
 gas[i+1]-cost[i+1]+gas[i+2]-cost[i+2]>=0
 .......
 gas[i+1]-cost[i+1]+gas[i+2]-cost[i+2]+...+gas[n-1]-cost[n-1]>=0
What about for the partial sums that wraps around?

gas[0]-cost[0]+gas[1]-cost[1]+...+gas[j]-cost[j] + gas[i+1]-cost[i+1]+...+gas[n-1]-cost[n-1]
>=
gas[0]-cost[0]+gas[1]-cost[1]+...+gas[i]-cost[i] + gas[i+1]-cost[i+1]+...+gas[n-1]-cost[n-1]
>=0
The last inequality is due to the assumption that the entire sum of gas[k]-cost[k] is greater than or equal to 0.
So we have that all the partial sums

gas[i+1]-cost[i+1]>=0,
gas[i+1]-cost[i+1]+gas[i+2]-cost[i+2]>=0,
gas[i+1]-cost[i+1]+gas[i+2]-cost[i+2]+...+gas[n-1]-cost[n-1]>=0,
...
gas[i+1]-cost[i+1]+...+gas[n-1]-cost[n-1] + gas[0]-cost[0]+gas[1]-cost[1]+...+gas[j]-cost[j]>=0,
...
Thus i+1 is the position to start. Coding using this reasoning is as follows:

class Solution {
public:
    int canCompleteCircuit(vector<int>& gas, vector<int>& cost) {
        int n = gas.size();
        int total(0), subsum(INT_MAX), start(0);
        for(int i = 0; i < n; ++i){
            total += gas[i] - cost[i];
            if(total < subsum) {
                subsum = total;
                start = i + 1;
            }
        }
        return (total < 0) ?  -1 : (start%n);
    }
};

My solution is accepted. The idea is if the sum of the gas is greater than the sum of cost, there must be a solution.

Next, accumulate the "surplus" or "deficit" along the circle, at one point, you will have the biggest deficit. Starting from the next station, you will never run into deficit so you can travel around the circle.

The solution is so straightforward, makes me wonder am I missing anything?

Added: Proof of existence of solution when the sum of gas is on less than the sum of cost

With that assumption, let's check the situation where there is only one station Greatest Net Deficit (GND)
Note that the net surplus(NS) is the result of all the previous stations, a negative NS mean the car can not reach the next station.. If we start from the station from the station with the GND, which put the NS for that station at 0, then the NS will be positive for all station except the starting station, which can be positive or zero. Any way, the car can travel the circle.

Next assume there are k station with equal GND, if we start from the first of them K1, we'll arrive in the next GND station K2 with 0 gas left, which means we can take K1-K2 path out of the circle without affecting our solution. Keep doing that we'll get back to the previous situation. So we know that there will be a least one solution given the sum of gas is greater than the sum of the cost.
*/



/*
class Solution {
public:
    int canCompleteCircuit(vector<int> &gas, vector<int> &cost) {
        int i, j, n = gas.size();


         //If start from i, stop before station x -> no station k from i + 1 to x - 1 can reach x.
         //Bcoz if so, i can reach k and k can reach x, then i reaches x. Contradiction.
         //Thus i can jump directly to x instead of i + 1, bringing complexity from O(n^2) to O(n).

        // start from station i
        for (i = 0; i < n; i += j) {
            int gas_left = 0;
            // forward j stations
            for (j = 1; j <= n; j++) {
                int k = (i + j - 1) % n;
                gas_left += gas[k] - cost[k];
                if (gas_left < 0)
                    break;
            }
            if (j > n)
                return i;
        }

        return -1;
    }
};
*/
