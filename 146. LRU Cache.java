class LRUCache {
    Map<Integer,Integer> lhm;
    int capacity;

    public LRUCache(int capacity) {
        this.lhm = new LinkedHashMap<>(capacity,0.75f,true);
        this.capacity = capacity;
    }

    public int get(int key) {
        // value = lhm.get(key);
        // lhm.remove(key);
        // lhm.put(key,value);
        // return value;
        return this.lhm.get(key)==null?-1:this.lhm.get(key);
    }

    public void put(int key, int value) {
        if(!lhm.containsKey(key) && lhm.size() == this.capacity){
            Iterator<Integer> it =  this.lhm.keySet().iterator();
            this.lhm.remove(it.next());
        }
        this.lhm.put(key,value);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */






/*Using double linkedlist and hashmap
The problem can be solved with a hashtable that keeps track of the keys and its values in the double linked list. One interesting property about double linked list is that the node can remove itself without other reference. In addition, it takes constant time to add and remove nodes from the head or tail.

One particularity about the double linked list that I implemented is that I create a pseudo head and tail to mark the boundary, so that we dont need to check the NULL node during the update. This makes the code more concise and clean, and also it is good for the performance as well.

Voila, here is the code.

class DLinkedNode {
    int key;
    int value;
    DLinkedNode pre;
    DLinkedNode post;
}


//Always add the new node right after head;

private void addNode(DLinkedNode node){
    node.pre = head;
    node.post = head.post;

    head.post.pre = node;
    head.post = node;
}


//Remove an existing node from the linked list.

private void removeNode(DLinkedNode node){
    DLinkedNode pre = node.pre;
    DLinkedNode post = node.post;

    pre.post = post;
    post.pre = pre;
}


//Move certain node in between to the head.

private void moveToHead(DLinkedNode node){
    this.removeNode(node);
    this.addNode(node);
}

// pop the current tail.
private DLinkedNode popTail(){
    DLinkedNode res = tail.pre;
    this.removeNode(res);
    return res;
}

private Hashtable<Integer, DLinkedNode>
    cache = new Hashtable<Integer, DLinkedNode>();
private int count;
private int capacity;
private DLinkedNode head, tail;

public LRUCache(int capacity) {
    this.count = 0;
    this.capacity = capacity;

    head = new DLinkedNode();
    head.pre = null;

    tail = new DLinkedNode();
    tail.post = null;

    head.post = tail;
    tail.pre = head;
}

public int get(int key) {

    DLinkedNode node = cache.get(key);
    if(node == null){
        return -1; // should raise exception here.
    }

    // move the accessed node to the head;
    this.moveToHead(node);

    return node.value;
}


public void set(int key, int value) {
    DLinkedNode node = cache.get(key);

    if(node == null){

        DLinkedNode newNode = new DLinkedNode();
        newNode.key = key;
        newNode.value = value;

        this.cache.put(key, newNode);
        this.addNode(newNode);

        ++count;

        if(count > capacity){
            // pop the tail
            DLinkedNode tail = this.popTail();
            this.cache.remove(tail.key);
            --count;
        }
    }else{
        // update the value.
        node.value = value;
        this.moveToHead(node);
    }

}


Excellent idea! Based on your solution, I moved all methods on DLinkNode to inside the inner class to make the structure neater. I also removed 'count' which is not necessary and kept 'head' and 'tail' as real nodes instead of some Nil.

public class LRUCache {
    private Map<Integer, DLinkNode> cache;
    DLinkNode tail = null;
    DLinkNode head = null;
    int capacity;

    public LRUCache(int capacity) {
        cache = new HashMap<Integer, DLinkNode>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            DLinkNode target = cache.get(key);
            int value = target.value;
            target.update();
            return value;
        } else return -1;
    }

    public void set(int key, int value) {
        if (cache.containsKey(key)) {
            DLinkNode target = cache.get(key);
            target.value = value;
            target.update();
        } else {
            if (capacity == 0) return;
            if (cache.size() == capacity) {
                cache.remove(head.key);
                head.removeFromHead();
            }
            DLinkNode newNode = new DLinkNode(key, value);
            newNode.append();
            cache.put(key, newNode);
        }
    }

    class DLinkNode {
        int key;
        int value;
        DLinkNode left = null;
        DLinkNode right = null;
        public DLinkNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
        // remove head from list and update head reference.
        private void removeFromHead() {
            // if 'this' is the only node, set both head and tail to null.
            if (tail == this) {
                head = null;
                tail = null;
            } else {
                head = this.right;
                head.left = null;
            }
        }
        private void update() {
            // no need to update if accessing the most revently used value.
            if (tail == this) return;
            else {
                 // remove from current postion and update nodes (if any) on both sides.
                if (this != head) {
                    this.left.right = this.right;
                } else {
                    head = this.right;
                }
                this.right.left = this.left;
                 // append to tail.
                this.append();
            }
        }
        private void append() {
            // inserting the first node.
            if (tail == null) {
                head = this;
                tail = this;
            // appned as tail and update tail reference.
            } else {
                this.right = null;
                this.left = tail;
                tail.right =this;
                tail = this;
            }
        }
    }
}
*/




/*More concise
This is the laziest implementation using Java's LinkedHashMap. In the real interview, however, it is definitely not what interviewer expected.

    import java.util.LinkedHashMap;
    public class LRUCache {
        private LinkedHashMap<Integer, Integer> map;
        private final int CAPACITY;
        public LRUCache(int capacity) {
            CAPACITY = capacity;
            map = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true){
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    return size() > CAPACITY;
                }
            };
        }
        public int get(int key) {
            return map.getOrDefault(key, -1);
        }
        public void set(int key, int value) {
            map.put(key, value);
        }
    }
Several points to mention:

In the constructor, the third boolean parameter specifies the ordering mode. If we set it to true, it will be in access order. (https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html#LinkedHashMap-int-float-boolean-)
By overriding removeEldestEntry in this way, we do not need to take care of it ourselves. It will automatically remove the least recent one when the size of map exceeds the specified capacity.(https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html#removeEldestEntry-java.util.Map.Entry-)
*/



