class Logger {
    Map<String,Integer> hm;

    /** Initialize your data structure here. */
    public Logger() {
        this.hm = new HashMap<>();
    }

    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
        If this method returns false, the message will not be printed.
        The timestamp is in seconds granularity. */
    public boolean shouldPrintMessage(int timestamp, String message) {
        if(!hm.containsKey(message)){
            hm.put(message,timestamp);
            return true;
        }
        else{
            int pre = hm.get(message);
            if(timestamp - pre >= 10){
                hm.put(message,timestamp);
                return true;
            }
            else return false;
        }

    }
}

/**
 * Your Logger object will be instantiated and called as such:
 * Logger obj = new Logger();
 * boolean param_1 = obj.shouldPrintMessage(timestamp,message);
 */




/*Using Heap
A typical (accepted) solution is to keep a hash map of String that maps to the recent time stamp.
But this way, it needs to keep the record of the entire messages, even when the message is rare.

Alternatively, I keep a heap to get rid of the old message and set of String to keep the recent messages only. This approach would make sense when the number of logs within 10 minutes time window is not too large and when we have lots of different messages.

class Log {
    int timestamp;
    String message;
    public Log(int aTimestamp, String aMessage) {
        timestamp = aTimestamp;
        message = aMessage;
    }
}

public class Logger {
    PriorityQueue<Log> recentLogs;
    Set<String> recentMessages;

    Initialize your data structure here.
    public Logger() {
        recentLogs = new PriorityQueue<Log>(10, new Comparator<Log>() {
            public int compare(Log l1, Log l2) {
                return l1.timestamp - l2.timestamp;
            }
        });

        recentMessages = new HashSet<String>();
    }

    Returns true if the message should be printed in the given timestamp, otherwise returns false.
        If this method returns false, the message will not be printed.
        The timestamp is in seconds granularity.
    public boolean shouldPrintMessage(int timestamp, String message) {
        while (recentLogs.size() > 0)   {
            Log log = recentLogs.peek();
            // discard the logs older than 10 minutes
            if (timestamp - log.timestamp >= 10) {
                recentLogs.poll();
                recentMessages.remove(log.message);
            } else
                break;
        }
        boolean res = !recentMessages.contains(message);
        if (res) {
            recentLogs.add(new Log(timestamp, message));
            recentMessages.add(message);
        }
        return res;
    }
}
*/



/*Using Queue
@sculd Made the same way, but a bit simpler as you don't need priority queue here - all messages seem to come in non-descending order, so it looks much conciser:

public class Logger {
    Queue<Tuple> q = new ArrayDeque<>();
    Set<String> dict = new HashSet<>();

    public Logger() {}

    public boolean shouldPrintMessage(int timestamp, String message) {
        while (!q.isEmpty() && q.peek().t <= timestamp - 10) {
            Tuple next = q.poll();
            dict.remove(next.msg);
        }
        if (!dict.contains(message)) {
            q.offer(new Tuple(timestamp, message));
            dict.add(message);
            return true;
        }
        return false;
    }
    private static class Tuple {
        int t;
        String msg;
        public Tuple(int t, String msg) {
            this.t = t;
            this.msg = msg;
        }
    }
}
*/




/*Using LinkedHashMap
public class Logger {

    public Map<String, Integer> map;
    int lastSecond = 0;

    Initialize your data structure here.
    public Logger() {
        map = new java.util.LinkedHashMap<String, Integer>(100, 0.6f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                return lastSecond - eldest.getValue() > 10;
            }
        };
    }

    Returns true if the message should be printed in the given timestamp, otherwise returns false.
        If this method returns false, the message will not be printed.
        The timestamp is in seconds granularity.
    public boolean shouldPrintMessage(int timestamp, String message) {
        lastSecond = timestamp;
        if(!map.containsKey(message)||timestamp - map.get(message) >= 10){
            map.put(message,timestamp);
            return true;
        }
        return false;
    }
}
*/




/*Using Circular Buffer
public class Logger {
    private int[] buckets;
    private Set[] sets;
    Initialize your data structure here.
    public Logger() {
        buckets = new int[10];
        sets = new Set[10];
        for (int i = 0; i < sets.length; ++i) {
            sets[i] = new HashSet<String>();
        }
    }

    Returns true if the message should be printed in the given timestamp, otherwise returns false.
        If this method returns false, the message will not be printed.
        The timestamp is in seconds granularity.
    public boolean shouldPrintMessage(int timestamp, String message) {
        int idx = timestamp % 10;
        if (timestamp != buckets[idx]) {
            sets[idx].clear();
            buckets[idx] = timestamp;
        }
        for (int i = 0; i < buckets.length; ++i) {
            if (timestamp - buckets[i] < 10) {
                if (sets[i].contains(message)) {
                    return false;
                }
            }
        }
        sets[idx].add(message);
        return true;
    }
}
*/


/*OO design
public class Logger {

    private class Log {
        String m;
        int t;
        public Log(String m, int t) {
            this.m = m;
            this.t = t;
        }
    }

    private static final int LOG_LAST_TIME = 10;
    LinkedList<Log> logList;
    Set<String> messageSet;

    Initialize your data structure here.
    public Logger() {
        logList = new LinkedList<>();
        messageSet = new HashSet<>();
    }

    Returns true if the message should be printed in the given timestamp, otherwise returns false.
        If this method returns false, the message will not be printed.
        The timestamp is in seconds granularity.
    public boolean shouldPrintMessage(int timestamp, String message) {
        while(!logList.isEmpty()) {
            if(logList.peek().t <= timestamp - LOG_LAST_TIME) {
                messageSet.remove(logList.poll().m);
            } else {
                break;
            }
        }
        if(messageSet.contains(message)) {
            return false;
        }
        logList.offer(new Log(message, timestamp));
        messageSet.add(message);
        return true;
    }
}
*/
