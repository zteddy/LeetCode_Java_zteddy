// class AllOne {
//     Map<String,Integer> KeyToValue;
//     Map<Integer,Set<String>> ValueToKey;
//     int maxValue;
//     int minValue;

//     /** Initialize your data structure here. */
//     public AllOne() {
//         this.KeyToValue = new HashMap<>();
//         this.ValueToKey = new HashMap<>();
//         this.maxValue = Integer.MIN_VALUE;
//         this.minValue = Integer.MAX_VALUE;

//     }

//     /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
//     public void inc(String key) {
//         int now = KeyToValue.containsKey(key)?KeyToValue.get(key):0;
//         now++;
//         KeyToValue.put(key,now);
//         if(ValueToKey.containsKey(now-1) && ValueToKey.get(now-1).contains(key)){
//             ValueToKey.get(now-1).remove(key);
//             if(ValueToKey.get(now-1).size() == 0 && now-1 == minValue) minValue++;
//             if(ValueToKey.get(now-1).size() == 0) ValueToKey.remove(now-1);
//         }
//         if(ValueToKey.containsKey(now)){
//             ValueToKey.get(now).add(key);
//         }
//         else{
//             Set<String> temp = new HashSet<>();
//             temp.add(key);
//             ValueToKey.put(now,temp);
//         }
//         // Set<String> temp = ValueToKey.containsKey(now)?ValueToKey.get(now):new HashSet<>();
//         // temp.add(key);
//         // if(now-1 == minValue) minValue++;
//         if(now > maxValue) maxValue = now;
//         if(now < minValue) minValue = now;
//     }

//     /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
//     public void dec(String key) {
//         if(!KeyToValue.containsKey(key)) return ;
//         int temp = KeyToValue.get(key);
//         ValueToKey.get(temp).remove(key);
//         if(temp == 1){
//             KeyToValue.remove(key);
//             ValueToKey.get(1).remove(key);
//             if(ValueToKey.get(1).size() == 0 && temp == maxValue) maxValue--;
//             if(ValueToKey.get(1).size() == 0 && temp == minValue) minValue--;
//             if(ValueToKey.get(1).size() == 0) ValueToKey.remove(1);
//         }
//         else{
//             ValueToKey.get(temp).remove(key);
//             if(ValueToKey.get(temp).size() == 0 && temp == maxValue) maxValue--;
//             if(ValueToKey.get(temp).size() == 0) ValueToKey.remove(temp);
//             if(ValueToKey.get(temp).size() == 0 && temp == minValue) minValue--;
//             KeyToValue.put(key,temp-1);
//             if(ValueToKey.containsKey(temp-1)){
//                 ValueToKey.get(temp-1).add(key);
//             }
//             else{
//                 Set<String> tempS = new HashSet<>();
//                 tempS.add(key);
//                 ValueToKey.put(temp-1,tempS);
//             }
//             // Set<String> tempS = ValueToKey.containsKey(temp-1)?ValueToKey.get(temp-1):new HashSet<>();
//             // tempS.add(key);
//         }
//         // if(temp == maxValue) maxValue--;
//         // if(temp-1 < minValue) minValue = temp-1;
//         if(minValue == 0){
//             for(int i = minValue; i <= maxValue; i++){
//                 if(ValueToKey.containsKey(i) && ValueToKey.get(i).size() > 0){
//                     minValue = i;
//                     break;
//                 }
//             }
//         }
//         if(temp-1 > maxValue) maxValue = temp-1;

//     }

//     /** Returns one of the keys with maximal value. */
//     public String getMaxKey() {
//         if(!ValueToKey.containsKey(maxValue)) return "";
//         return (String)ValueToKey.get(maxValue).iterator().next();

//     }

//     /** Returns one of the keys with Minimal value. */
//     public String getMinKey() {
//         if(!ValueToKey.containsKey(minValue)) return "";
//         return (String)ValueToKey.get(minValue).iterator().next();

//     }
// }

// /**
//  * Your AllOne object will be instantiated and called as such:
//  * AllOne obj = new AllOne();
//  * obj.inc(key);
//  * obj.dec(key);
//  * String param_3 = obj.getMaxKey();
//  * String param_4 = obj.getMinKey();
//  */


//晚上做晕晕的，没想好就开始做了


class AllOne {
    Map<String,Integer> KeyToValue;
    Map<Integer,Node> ValueToKey;
    int maxValue;
    int minValue;
    Node head;
    Node tail;

    /** Initialize your data structure here. */
    public AllOne() {
        this.KeyToValue = new HashMap<>();
        this.ValueToKey = new HashMap<>();
        // this.maxValue = Integer.MIN_VALUE;
        // this.minValue = Integer.MAX_VALUE;
        this.maxValue = Integer.MIN_VALUE;
        this.minValue = Integer.MAX_VALUE;
        this.head = new Node();
        this.tail = new Node();
        head.right= tail;
        tail.left = head;
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        if(!KeyToValue.containsKey(key)){
            KeyToValue.put(key,1);
            if(!ValueToKey.containsKey(1)){
                Node temp = new Node();
                temp.keys.add(key);
                ValueToKey.put(1,temp);
                insertNodeAfter(temp,head);
            }
            else{
                ValueToKey.get(1).keys.add(key);
            }
        }
        else{
            int pre = KeyToValue.get(key);
            int cur = pre+1;
            Node curNode;
            Node preNode =  ValueToKey.get(pre);
            //add
            if(!ValueToKey.containsKey(cur)){
                curNode = new Node();
                curNode.keys.add(key);
                ValueToKey.put(cur,curNode);
                insertNodeAfter(curNode,preNode);
            }
            else{
                curNode = ValueToKey.get(cur);
                curNode.keys.add(key);
            }
            //delete
            preNode.keys.remove(key);
            if(preNode.keys.size() == 0){
                deleteNode(preNode);
                ValueToKey.remove(pre);
            }
            KeyToValue.put(key,cur);
        }
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        if(!KeyToValue.containsKey(key)) return ;
        int pre = KeyToValue.get(key);
        int cur = pre-1;
        if(pre == 1){
            ValueToKey.get(1).keys.remove(key);
            if(ValueToKey.get(1).keys.size() == 0){
                deleteNode(ValueToKey.get(1));
                ValueToKey.remove(1);
            }
            KeyToValue.remove(key);
        }
        else{
            Node curNode;
            Node preNode =  ValueToKey.get(pre);
            //add
            if(!ValueToKey.containsKey(cur)){
                curNode = new Node();
                curNode.keys.add(key);
                ValueToKey.put(cur,curNode);
                insertNodeBefore(curNode,preNode);
            }
            else{
                curNode = ValueToKey.get(cur);
                curNode.keys.add(key);
            }
            //delete
            preNode.keys.remove(key);
            if(preNode.keys.size() == 0){
                deleteNode(preNode);
                ValueToKey.remove(pre);
            }
            KeyToValue.put(key,cur);
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        if(tail.left == head) return "";
        return (String)tail.left.keys.iterator().next();

    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        if(head.right == tail) return "";
        return (String)head.right.keys.iterator().next();

    }

    class Node{
        Node left;
        Node right;
        Set<String> keys;

        public Node(){
            this.left = null;
            this.right = null;
            this.keys = new HashSet<>();
        }
    }

    public void deleteNode(Node n){
        if(n.left != null) n.left.right = n.right;
        if(n.right != null) n.right.left = n.left;
    }

    public void insertNodeAfter(Node n, Node target){
        if(target.right != null){
            n.right = target.right;
            n.left = target;
            target.right.left = n;
            target.right = n;
        }
        else{
            target.right = n;
            n.left = target;
        }
    }

    public void insertNodeBefore(Node n, Node target){
        if(target.left != null){
            n.right = target;
            n.left = target.left;
            target.left.right = n;
            target.left = n;
        }
        else{
            target.left = n;
            n.right = target;
        }
    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
