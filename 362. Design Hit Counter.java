class HitCounter {
    int gap;
    Queue<Integer> q;
    // Set<Integer> s;

    /** Initialize your data structure here. */
    public HitCounter() {
        this.gap = 5*60;
        q = new LinkedList<>();
        // s = new HashSet<>();
    }

    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        // while(!q.isEmpty() && timestamp - q.peek() > gap) q.poll();
        q.offer(timestamp);

    }

    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        while(!q.isEmpty() && timestamp - q.peek() >= gap) q.poll();
        return q.size();

    }
}

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */




/*Design
Since this is a design question, we need to ask interviewer how this class is going to be used?
A working code is not the answer to this question, but how you adjust your program to meet different use cases.

Consider: There are 1000 frequent hit() followed by 1 getHits(). If we only do removal in getHits() function, it will be very time consuming. For me, I prefer to do removal in both hit() and getHits(), so that the program avoids system lag in this case.
This is important when you design a time-critical system.
*/



/*Using Circular Buffer
public class HitCounter {
    private int[] times;
    private int[] hits;
    /** Initialize your data structure here. */
    public HitCounter() {
        times = new int[300];
        hits = new int[300];
    }

    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        int index = timestamp % 300;
        if (times[index] != timestamp) {
            times[index] = timestamp;
            hits[index] = 1;
        } else {
            hits[index]++;
        }
    }

    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        int total = 0;
        for (int i = 0; i < 300; i++) {
            if (timestamp - times[i] < 300) {
                total += hits[i];
            }
        }
        return total;
    }
}
*/









/*Using move
This solution is based on the idea in this post:
https://nuttynanaus.wordpress.com/2014/03/09/software-engineer-interview-questions/
There are two solutions, the first one we choose 1s as granularity, the other is full accuracy(see the post).
We call move() before hit() and getHits(). move() will take time at most O(N), where N is the length of the array.

public class HitCounter {
    int N;
    int[] count;
    int lastPosition;
    int lastTime;
    int sum;

    /** Initialize your data structure here. */
    public HitCounter() {
        N = 300;
        count = new int[N];
        lastPosition = 0;
        lastTime = 0;
        sum = 0;
    }

    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        move(timestamp);
        count[lastPosition]++;
        sum++;
    }

    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        move(timestamp);
        return sum;
    }

    void move(int timestamp){
        int gap = Math.min(timestamp-lastTime, N);
        for(int i=0; i<gap;i++){
            lastPosition = (lastPosition+1)%N;
            sum -= count[lastPosition];
            count[lastPosition] = 0;
        }
        lastTime = timestamp;
    }
}
*/



/*Using two LinkedList'
public class HitCounter {

    private int sum;
    private LinkedList<Integer> time;
    private LinkedList<Integer> hits;

    /** Initialize your data structure here. */
    public HitCounter() {
        sum = 0;
        time = new LinkedList<Integer>();
        hits = new LinkedList<Integer>();
    }

    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        if(time.isEmpty() || time.getLast() != timestamp) {
            time.addLast(timestamp);
            hits.addLast(1);
        }
        else hits.addLast(hits.removeLast()+1);
        sum++;
    }

    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        int head = timestamp - 300;
        while(!time.isEmpty() && time.getFirst() <= head) {
            time.removeFirst();
            sum -= hits.removeFirst();
        }
        return sum;
    }
}
*/




/*Concurrent
Just started learning distributed systems, your comments are welcome!

Use a long number as the total hit count since the beginning. The number range of long should be able to cover a very long time, even assuming 1M hits per second.
Use the combination of a TreeMap and an AtomicInteger to avoid locking the whole Map. The AtomicInteger itself should be able to block concurrent access for that second, and later timestamp will not be blocked.
I guess appending is much cheaper than updating/deleting, so I don't delete any data if that timestamp has passed. 1 Second is 1 entry in each Map, I don't know how large space each entry takes, assuming 100 bytes, then 1 day is 8 MB. Hopefully it is acceptable?
At the time of getSum(int timestamp), I am not sure how to guarantee that get runs after all increments are done....
import java.util.concurrent.atomic.*;

public class HitCounter {
    private TreeMap<Integer, AtomicInteger> counts;
    private HashMap<Integer, Long> cache;
    Object lock;

    /** Initialize your data structure here. */
    public HitCounter() {
        counts = new TreeMap<Integer, AtomicInteger>();
        cache = new HashMap<Integer, Long>();
        cache.put(0, 0L);
        lock = new Object();
    }

    /**
     * Record a hit.
     *
     * @param timestamp
     *            - The current timestamp (in seconds granularity).
     */
    public void hit(int timestamp) {
        if (!counts.containsKey(timestamp)) {
            synchronized (lock) {
                if (!counts.containsKey(timestamp)) {
                    counts.put(timestamp, new AtomicInteger(0));
                }
            }
        }

        counts.get(timestamp).incrementAndGet();
    }

    /**
     * Return the number of hits in the past 5 minutes.
     *
     * @param timestamp
     *            - The current timestamp (in seconds granularity).
     */
    public int getHits(int timestamp) {
        if (timestamp <= 300) {
            return (int) getSum(timestamp);
        } else {
            return (int) (getSum(timestamp) - getSum(timestamp - 300));
        }
    }

    private long getSum(int timestamp) {
        if (cache.containsKey(timestamp)) {
            return cache.get(timestamp);
        } else {
            AtomicInteger currentAI = counts.get(timestamp);
            long current = currentAI == null ? 0L : currentAI.get();
            current += getSum(timestamp - 1);

            if (counts.size() > 0 && counts.lastKey() > timestamp) {
                cache.put(timestamp, current);
            }

            return current;
        }
    }
}
*/





/*Using LinkedHashMap
final int  fiveMin = 300;
LinkedHashMap<Integer,Integer> map;
/** Initialize your data structure here. */
public HitCounter() {
    map = new LinkedHashMap<Integer,Integer>(){
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer,Integer> eldest){
            return map.size() > fiveMin;
        }
        };
}

/** Record a hit.
    @param timestamp - The current timestamp (in seconds granularity). */
public void hit(int timestamp) {
    map.put(timestamp,map.getOrDefault(timestamp,0)+1);
}

/** Return the number of hits in the past 5 minutes.
    @param timestamp - The current timestamp (in seconds granularity). */
public int getHits(int timestamp) {
    int start = timestamp - fiveMin;
    int sum =0;
    for(int tsp:map.keySet()){
        if(tsp>start)
            sum += map.get(tsp);
    }
    return sum;
}
*/
