class LFUCache {

    int capacity;
    Map<Integer,DLinkedNode> mapping;
    Map<Integer,DLinkedNode> leader;
    DLinkedNode head;
    DLinkedNode tail;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.mapping = new HashMap<>();
        this.leader = new HashMap<>();
        this.head = new DLinkedNode(0,0);
        this.tail = new DLinkedNode(0,0);
        this.head.right = this.tail;
        this.tail.left = this.head;
    }

    public int get(int key) {
        if(this.capacity == 0) return -1;
        if(mapping.containsKey(key)){
            update(key);
            return mapping.get(key).value;
        }
        else{
            return -1;
        }
    }

    public void put(int key, int value) {
        if(this.capacity == 0) return;
        if(mapping.size() == capacity && !mapping.containsKey(key)){
            DLinkedNode temp = this.head.right;
            if(leader.containsKey(temp.count) && leader.get(temp.count) == temp)
                leader.remove(temp.count);
            mapping.remove(temp.key);
            delete(temp);
        }
        if(mapping.containsKey(key)){
            update(key);
            mapping.get(key).value = value;
        }
        else{
            DLinkedNode newD = new DLinkedNode(key,value);
            newD.count = 0;
            mapping.put(key,newD);
            if(leader.containsKey(0)){
                insertAfterTarget(newD,leader.get(0));
                leader.put(0,newD);
            }
            else{
                leader.put(0,newD);
                insertAfterTarget(newD,this.head);
            }
        }
    }


    class DLinkedNode{
        int key;
        int value;
        int count;
        DLinkedNode left;
        DLinkedNode right;

        public DLinkedNode(int key, int value){
            this.key = key;
            this.value = value;
            this.count = 0;
            this.left = null;
            this.right = null;
        }
    }

    public void delete(DLinkedNode d){
        d.left.right = d.right;
        d.right.left = d.left;
    }

    public void insertAfterTarget(DLinkedNode d, DLinkedNode target){
        d.left = target;
        d.right = target.right;
        target.right = d;
        d.right.left = d;
    }

    public void insertBeforeTarget(DLinkedNode d, DLinkedNode target){
        d.right = target;
        d.left = target.left;
        target.left = d;
        d.left.right = d;
    }


    public void update(int key){
        boolean flag = false;
        DLinkedNode temp = mapping.get(key);
        if(leader.get(temp.count) == temp){
            flag = true;
            if(temp.left != this.head && temp.left.count == temp.count){
                leader.put(temp.count,temp.left);
            }
            else{
                leader.remove(temp.count);
            }
        }
        // else{
        //     delete(temp);
        //     insertAfterTarget(temp,leader.get(temp.count));
        // }
        temp.count+=1;
        if(leader.containsKey(temp.count)){
            delete(temp);
            insertAfterTarget(temp,leader.get(temp.count));
        }
        else{
            if(!flag){
                delete(temp);
                insertAfterTarget(temp,leader.get(temp.count-1));
            }
        }
        leader.put(temp.count,temp);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */






/*Using LinkedHashMap
Two HashMaps are used, one to store <key, value> pair, another store the <key, node>.
I use double linked list to keep the frequent of each key. In each double linked list node, keys with the same count are saved using java built in LinkedHashSet. This can keep the order.
Every time, one key is referenced, first find the current node corresponding to the key, If the following node exist and the frequent is larger by one, add key to the keys of the following node, else create a new node and add it following the current node.
All operations are guaranteed to be O(1).

public class LFUCache {
    private Node head = null;
    private int cap = 0;
    private HashMap<Integer, Integer> valueHash = null;
    private HashMap<Integer, Node> nodeHash = null;

    public LFUCache(int capacity) {
        this.cap = capacity;
        valueHash = new HashMap<Integer, Integer>();
        nodeHash = new HashMap<Integer, Node>();
    }

    public int get(int key) {
        if (valueHash.containsKey(key)) {
            increaseCount(key);
            return valueHash.get(key);
        }
        return -1;
    }

    public void set(int key, int value) {
        if ( cap == 0 ) return;
        if (valueHash.containsKey(key)) {
            valueHash.put(key, value);
        } else {
            if (valueHash.size() < cap) {
                valueHash.put(key, value);
            } else {
                removeOld();
                valueHash.put(key, value);
            }
            addToHead(key);
        }
        increaseCount(key);
    }

    private void addToHead(int key) {
        if (head == null) {
            head = new Node(0);
            head.keys.add(key);
        } else if (head.count > 0) {
            Node node = new Node(0);
            node.keys.add(key);
            node.next = head;
            head.prev = node;
            head = node;
        } else {
            head.keys.add(key);
        }
        nodeHash.put(key, head);
    }

    private void increaseCount(int key) {
        Node node = nodeHash.get(key);
        node.keys.remove(key);

        if (node.next == null) {
            node.next = new Node(node.count+1);
            node.next.prev = node;
            node.next.keys.add(key);
        } else if (node.next.count == node.count+1) {
            node.next.keys.add(key);
        } else {
            Node tmp = new Node(node.count+1);
            tmp.keys.add(key);
            tmp.prev = node;
            tmp.next = node.next;
            node.next.prev = tmp;
            node.next = tmp;
        }

        nodeHash.put(key, node.next);
        if (node.keys.size() == 0) remove(node);
    }

    private void removeOld() {
        if (head == null) return;
        int old = 0;
        for (int n: head.keys) {
            old = n;
            break;
        }
        head.keys.remove(old);
        if (head.keys.size() == 0) remove(head);
        nodeHash.remove(old);
        valueHash.remove(old);
    }

    private void remove(Node node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }

    class Node {
        public int count = 0;
        public LinkedHashSet<Integer> keys = null;
        public Node prev = null, next = null;

        public Node(int count) {
            this.count = count;
            keys = new LinkedHashSet<Integer>();
            prev = next = null;
        }
    }
}
*/



/*Using LinkedHashMap2
public class LFUCache {
    HashMap<Integer, Integer> vals;
    HashMap<Integer, Integer> counts;
    HashMap<Integer, LinkedHashSet<Integer>> lists;
    int cap;
    int min = -1;
    public LFUCache(int capacity) {
        cap = capacity;
        vals = new HashMap<>();
        counts = new HashMap<>();
        lists = new HashMap<>();
        lists.put(1, new LinkedHashSet<>());
    }

    public int get(int key) {
        if(!vals.containsKey(key))
            return -1;
        int count = counts.get(key);
        counts.put(key, count+1);
        lists.get(count).remove(key);
        if(count==min && lists.get(count).size()==0)
            min++;
        if(!lists.containsKey(count+1))
            lists.put(count+1, new LinkedHashSet<>());
        lists.get(count+1).add(key);
        return vals.get(key);
    }

    public void set(int key, int value) {
        if(cap<=0)
            return;
        if(vals.containsKey(key)) {
            vals.put(key, value);
            get(key);
            return;
        }
        if(vals.size() >= cap) {
            int evit = lists.get(min).iterator().next();
            lists.get(min).remove(evit);
            vals.remove(evit);
        }
        vals.put(key, value);
        counts.put(key, 1);
        min = 1;
        lists.get(1).add(key);
    }
}
*/





/*Three different ways
The first one: PriorityQueue + HashMap set O(capacity) get O(capacity)
The second one: TreeMap + HashMap set O(log(capacity)) get O(log(capacity))
The third one: HashMap + HashMap + DoubleLinkedList set O(1) get O(1)

PriorityQueue + HashMap: set O(capacity) get O(capacity)
'''

long stamp;
int capacity;
int num;
PriorityQueue<Pair> minHeap;
HashMap<Integer, Pair> hashMap;

// @param capacity, an integer
public LFUCache(int capacity) {
    // Write your code here
    this.capacity = capacity;
    num = 0;
    minHeap = new PriorityQueue<Pair>();
    hashMap = new HashMap<Integer, Pair>();
    stamp = 0;
}

// @param key, an integer
// @param value, an integer
// @return nothing
public void set(int key, int value) {
    if (capacity == 0) {
        return;
    }
    // Write your code here
    if (hashMap.containsKey(key)) {
        Pair old = hashMap.get(key);
        minHeap.remove(old);

        Pair newNode = new Pair(key, value, old.times + 1, stamp++);

        hashMap.put(key, newNode);
        minHeap.offer(newNode);
    } else if (num == capacity) {
        Pair old = minHeap.poll();
        hashMap.remove(old.key);

        Pair newNode = new Pair(key, value, 1, stamp++);

        hashMap.put(key, newNode);
        minHeap.offer(newNode);
    } else {
        num++;
        Pair pair = new Pair(key, value, 1, stamp++);
        hashMap.put(key, pair);
        minHeap.offer(pair);
    }
}

public int get(int key) {
    if (capacity == 0) {
        return -1;
    }
    // Write your code here
    if (hashMap.containsKey(key)) {
        Pair old = hashMap.get(key);
        minHeap.remove(old);

        Pair newNode = new Pair(key, old.value, old.times + 1, stamp++);

        hashMap.put(key, newNode);
        minHeap.offer(newNode);
        return hashMap.get(key).value;
    } else {
        return -1;
    }
}

class Pair implements Comparable<Pair> {
    long stamp;
    int key;
    int value;
    int times;
    public Pair(int key, int value, int times, long stamp) {
        this.key = key;
        this.value = value;
        this.times = times;
        this.stamp = stamp;
    }

    public int compareTo(Pair that) {
        if (this.times == that.times) {
            return (int)(this.stamp - that.stamp);
        } else {
            return this.times - that.times;
        }
    }
}
'''

TreeMap + HashMap: set O(log(capacity)) get O(log(capacity))

'''

private int capacity;
private int stamp;
private HashMap<Integer, Tuple> hashMap;
private TreeMap<Tuple, Integer> treeMap;
public LFUCache(int capacity) {
    this.capacity = capacity;
    stamp = 0;
    hashMap = new HashMap<Integer, Tuple>();
    treeMap = new TreeMap<Tuple, Integer>(new Comparator<Tuple>() {
        public int compare(Tuple t1, Tuple t2) {
            if (t1.times == t2.times) {
                return t1.stamp - t2.stamp;
            }
            return t1.times - t2.times;
        }
    });
}

public int get(int key) {
    if (capacity == 0) {
        return -1;
    }
    if (!hashMap.containsKey(key)) {
        return -1;
    }
    Tuple old = hashMap.get(key);
    treeMap.remove(old);
    Tuple newTuple = new Tuple(old.value, stamp++, old.times + 1);
    treeMap.put(newTuple, key);
    hashMap.put(key, newTuple);
    return old.value;
}

public void set(int key, int value) {
    if (capacity == 0) {
        return;
    }
    if (hashMap.containsKey(key)) {
        Tuple old = hashMap.get(key);
        Tuple newTuple = new Tuple(value, stamp++, old.times + 1);
        treeMap.remove(old);
        hashMap.put(key, newTuple);
        treeMap.put(newTuple, key);
    } else {
        if (treeMap.size() == capacity) {
            int endKey = treeMap.pollFirstEntry().getValue();
            hashMap.remove(endKey);
        }
        Tuple newTuple = new Tuple(value, stamp++, 1);
        hashMap.put(key, newTuple);
        treeMap.put(newTuple, key);
    }
}
class Tuple {
    int value;
    int times;
    int stamp;
    public Tuple (int value, int stamp, int times) {
        this.value = value;
        this.stamp = stamp;
        this.times = times;
    }
}
'''
HashMap + HashMap + DoubleLinkedList: set O(1) get O(1)

map1 save the nodes in the cache
finalNodes save the newest node which has appeared ''key'' times.
Using a doubleLinkedList to save the nodes in the cache.if a node appeared more times or is a new comer, the position in the list of this node is as back as possible.

'''

private int capacity;
private int count;
private HashMap<Integer, Tuple> map1; // whether appeared
private HashMap<Integer, Tuple> finalNodes; // value : the final node of key times
private Tuple dummyHead;
private Tuple dummyEnd;

public LFUCache(int capacity) {
    this.capacity = capacity;
    count = 0;
    map1 = new HashMap<Integer, Tuple>();
    finalNodes = new HashMap<>();
    dummyHead = new Tuple(0, 0, 0);
    dummyEnd = new Tuple(0, 0, 0);
    dummyHead.next = dummyEnd;
    dummyEnd.prev = dummyHead;
}

public int get(int key) {
    if (capacity == 0 || !map1.containsKey(key)) {
        return -1;
    }
    Tuple old = map1.get(key);
    set(key, old.value);
    return old.value;
}

public void set(int key, int value) {
    if (capacity == 0) {
        return;
    }
    if (map1.containsKey(key)) { // this key has appeared
        Tuple cur = map1.get(key);
        if (finalNodes.get(cur.times) == cur && finalNodes.get(cur.times + 1) == null) { // the position should not change
            finalNodes.put(cur.times, cur.prev.times == cur.times ? cur.prev : null);
            cur.times++;
            cur.value = value;
            finalNodes.put(cur.times, cur);
            return;
        }
        removeNode(cur); // remove node cur
        if (finalNodes.get(cur.times) == cur) {
            finalNodes.put(cur.times, cur.prev.times == cur.times ? cur.prev : null);
        }
        cur.times++;
        cur.value = value;
        Tuple finalNode = finalNodes.get(cur.times) == null ? finalNodes.get(cur.times - 1) : finalNodes.get(cur.times);
        insertNode(finalNode, cur);
        finalNodes.put(cur.times, cur); // cur is the final node whitch appeared cur.times
    } else if (count == capacity) { // reach limt of the cache
        Tuple head = dummyHead.next;
        removeNode(head); //remove the first which appeared least times and is the least Used
        map1.remove(head.key);
        if (finalNodes.get(head.times) == head) {
            finalNodes.remove(head.times);
        }
        Tuple cur = new Tuple(key, value, 1);
        if (finalNodes.get(1) == null) {
            insertNode(dummyHead, cur);
        } else {
            Tuple finalNode = finalNodes.get(1);
            insertNode(finalNode, cur);
        }
        finalNodes.put(1, cur);
        map1.put(key, cur);
    } else {
        count++;
        Tuple cur = new Tuple(key, value, 1);
        if (finalNodes.get(1) == null){
           insertNode(dummyHead, cur);
        } else {
            Tuple finalNode = finalNodes.get(1);
            insertNode(finalNode, cur);
        }
        finalNodes.put(1, cur);
        map1.put(key, cur);
    }
}

public void insertNode(Tuple t1, Tuple t2) {
    t2.next = t1.next;
    t1.next.prev = t2;
    t1.next = t2;
    t2.prev = t1;
}

public void removeNode(Tuple node) {
    node.next.prev = node.prev;
    node.prev.next = node.next;
}
class Tuple {
    int key;
    int value;
    int times;
    Tuple prev;
    Tuple next;
    public Tuple(int key, int value, int times) {
        this.key = key;
        this.value = value;
        this.times = times;
    }
}
'''
*/



/*Using HashMap to store list
public class LFUCache {
    class Node {
        int key, val, cnt;
        Node prev, next;
        Node(int key, int val) {
            this.key = key;
            this.val = val;
            cnt = 1;
        }
    }

    class DLList {
        Node head, tail;
        int size;
        DLList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        void add(Node node) {
            head.next.prev = node;
            node.next = head.next;
            node.prev = head;
            head.next = node;
            size++;
        }

        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        Node removeLast() {
            if (size > 0) {
                Node node = tail.prev;
                remove(node);
                return node;
            }
            else return null;
        }
    }

    int capacity, size, min;
    Map<Integer, Node> nodeMap;
    Map<Integer, DLList> countMap;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        nodeMap = new HashMap<>();
        countMap = new HashMap<>();
    }

    public int get(int key) {
        Node node = nodeMap.get(key);
        if (node == null) return -1;
        update(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        Node node;
        if (nodeMap.containsKey(key)) {
            node = nodeMap.get(key);
            node.val = value;
            update(node);
        }
        else {
            node = new Node(key, value);
            nodeMap.put(key, node);
            if (size == capacity) {
                DLList lastList = countMap.get(min);
                nodeMap.remove(lastList.removeLast().key);
                size--;
            }
            size++;
            min = 1;
            DLList newList = countMap.getOrDefault(node.cnt, new DLList());
            newList.add(node);
            countMap.put(node.cnt, newList);
        }
    }

    private void update(Node node) {
        DLList oldList = countMap.get(node.cnt);
        oldList.remove(node);
        if (node.cnt == min && oldList.size == 0) min++;
        node.cnt++;
        DLList newList = countMap.getOrDefault(node.cnt, new DLList());
        newList.add(node);
        countMap.put(node.cnt, newList);
    }
}
*/
