// class Solution {
//     public int[] exclusiveTime(int n, List<String> logs) {
//         int[] result = new int[n];
//         String prevState = "";
//         int prevTime = 0;
//         int prevId = -1;
//         for(String s:logs){
//             String[] sp = s.split(":");
//             int currId = Integer.parseInt(sp[0]);
//             String currState = sp[1];
//             int currTime = Integer.parseInt(sp[2]);
//             if(!(prevState.equals("end") && currState.equals("start")) && prevId >= 0){
//                 if(prevState.equals(currState))
//                     result[prevState.equals("end")?currId:prevId] += currTime-prevTime;
//                 else
//                     result[prevId] += currTime-prevTime+1;
//             }
//             prevId = currId;
//             prevState = currState;
//             prevTime = currTime;
//         }
//         return result;
//     }
// }


//一开始想错方向了，实际上还要个stack


class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] result = new int[n];
        Stack<Log> st = new Stack<>();
        for(String s:logs){
            String[] sp = s.split(":");
            int currId = Integer.parseInt(sp[0]);
            String currState = sp[1];
            int currTime = Integer.parseInt(sp[2]);
            Log curr = new Log(currId,currState,currTime);
            if(st.empty()) st.push(curr);
            else{
                Log prev = st.peek();
                if(prev.status.equals("start") && curr.status.equals("start")){
                    result[prev.id] += curr.timestamp-prev.timestamp;
                    st.push(curr);
                }
                else if(prev.status.equals("start") && curr.status.equals("end")){
                    result[prev.id] += curr.timestamp-prev.timestamp+1;
                    st.pop();
                    st.push(curr);
                }
                else if(prev.status.equals("end") && curr.status.equals("start")){
                    st.pop();
                    if(!st.empty()){
                        Log before = st.peek();
                        result[before.id] += curr.timestamp-prev.timestamp-1;
                    }
                    st.push(curr);
                }
                else if(prev.status.equals("end") && curr.status.equals("end")){
                    result[curr.id] += curr.timestamp-prev.timestamp;
                    st.pop();
                    st.pop();
                    st.push(curr);
                }
            }
        }
        return result;

    }
    class Log{
        int id;
        String status;
        int timestamp;
        public Log(int i, String s, int t){
            this.id = i;
            this.status = s;
            this.timestamp = t;
        }

    }
}



/*More concise
Approach #2 Better Approach [Accepted]

Algorithm

In the last approach, for every function on the top of the stackstack, we incremented the current time and the exclusive time of this same function till the current time became equal to the start/end time of the next function.

Instead of doing this incrementing step by step, we can directly use the difference between the next function's start/stop time and the current function's start/stop time. The rest of the process remains the same as in the last approach.

The following animation illustrates the process.

1 / 8
Java

public int[] exclusiveTime(int n, List<String> logs) {
    // separate time to several intervals, add interval to their function
    int[] result = new int[n];
    Stack<Integer> st = new Stack<>();
    int pre = 0;
    // pre means the start of the interval
    for(String log: logs) {
        String[] arr = log.split(":");
        if(arr[1].equals("start")) {
            if(!st.isEmpty())  result[st.peek()] += Integer.parseInt(arr[2]) - pre;
            // arr[2] is the start of next interval, doesn't belong to current interval.
            st.push(Integer.parseInt(arr[0]));
            pre = Integer.parseInt(arr[2]);
        } else {
            result[st.pop()] += Integer.parseInt(arr[2]) - pre + 1;
            // arr[2] is end of current interval, belong to current interval. That's why we have +1 here
            pre = Integer.parseInt(arr[2]) + 1;
            // pre means the start of next interval, so we need to +1
        }
    }
    return result;
}

Complexity Analysis

Time complexity : O(n)O(n). We iterate over the entire logslogs array just once. Here, nn refers to the number of elements in the logslogs list.

Space complexity : The stackstack can grow upto a depth of atmost n/2n/2. Here, nn refers to the number of elements in the given logslogs list.
*/




/*Another stack
public class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] res = new int[n];
        Stack<Pair> s = new Stack<>();
        for(int i=0;i<logs.size();i++){
            String[] tmp = logs.get(i).split(":");
            int id = Integer.parseInt(tmp[0]);
            int time = Integer.parseInt(tmp[2]);
            if(tmp[1].equals("start")){
                s.push(new Pair(id,time,0));
            }
            else{
                Pair tmpPair = s.pop();
                int exclusiveTime = time - tmpPair.t +1;
                res[id] += exclusiveTime-tmpPair.otherTime;
                if(!s.empty()){
                    Pair peek = s.peek();
                    peek.otherTime += exclusiveTime;
                }
            }
        }
        return res;
    }
   class Pair{
        int id,t,otherTime;
        Pair(int id, int t,int otherTime){
            this.id = id;
            this.t = t;
            this.otherTime = otherTime;
        }
    }
}
*/
