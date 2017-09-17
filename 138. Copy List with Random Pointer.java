/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if(head == null) return null;

        Map<RandomListNode,RandomListNode> mapping1 = new HashMap<>();
        Map<RandomListNode,RandomListNode> mapping2 = new HashMap<>();
        RandomListNode pointer = head;
        RandomListNode newHead = new RandomListNode(head.label);
        mapping1.put(head,newHead);
        mapping2.put(newHead,head);

        pointer = pointer.next;

        RandomListNode last = newHead;
        while(pointer != null){
            RandomListNode temp = new RandomListNode(pointer.label);
            last.next = temp;
            last = temp;
            mapping1.put(pointer,temp);
            mapping2.put(temp,pointer);
            pointer = pointer.next;
        }

        pointer = newHead;
        while(pointer != null){
            pointer.random = mapping1.get(mapping2.get(pointer).random);
            pointer = pointer.next;
        }

        return newHead;

    }
}





/*O(1) space
An intuitive solution is to keep a hash table for each node in the list, via which we just need to iterate the list in 2 rounds respectively to create nodes and assign the values for their random pointers. As a result, the space complexity of this solution is O(N), although with a linear time complexity.

As an optimised solution, we could reduce the space complexity into constant. The idea is to associate the original node with its copy node in a single linked list. In this way, we don't need extra space to keep track of the new nodes.

The algorithm is composed of the follow three steps which are also 3 iteration rounds.

Iterate the original list and duplicate each node. The duplicate
of each node follows its original immediately.
Iterate the new list and assign the random pointer for each
duplicated node.
Restore the original list and extract the duplicated nodes.
The algorithm is implemented as follows:

public RandomListNode copyRandomList(RandomListNode head) {
    RandomListNode iter = head, next;

    // First round: make copy of each node,
    // and link them together side-by-side in a single list.
    while (iter != null) {
        next = iter.next;

        RandomListNode copy = new RandomListNode(iter.label);
        iter.next = copy;
        copy.next = next;

        iter = next;
    }

    // Second round: assign random pointers for the copy nodes.
    iter = head;
    while (iter != null) {
        if (iter.random != null) {
            iter.next.random = iter.random.next;
        }
        iter = iter.next.next;
    }

    // Third round: restore the original list, and extract the copy list.
    iter = head;
    RandomListNode pseudoHead = new RandomListNode(0);
    RandomListNode copy, copyIter = pseudoHead;

    while (iter != null) {
        next = iter.next.next;

        // extract the copy
        copy = iter.next;
        copyIter.next = copy;
        copyIter = copy;

        // restore the original list
        iter.next = next;

        iter = next;
    }

    return pseudoHead.next;
}
*/





/*Using only one HashMap
I realized with Map, we don't really need to care about the internal structure of the list. What we need is just deep copy exactly what the original data. So here I just create all nodes and put <old, new> pairs into a map. Then update next and random pointers for each new node.

public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }

        final Map<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();

        RandomListNode cur = head;
        while(cur != null) {
            map.put(cur, new RandomListNode(cur.label));
            cur = cur.next;
        }

        for (Map.Entry<RandomListNode, RandomListNode> entry : map.entrySet()) {
            final RandomListNode newNode = entry.getValue();
            newNode.next = map.get(entry.getKey().next);
            newNode.random = map.get(entry.getKey().random);
        }

        return map.get(head);
    }
}
*/




/*O(1) space2

Create the copy of node 1 and insert it between node 1 & node 2 in original Linked List, create the copy of 2 and insert it between 2 & 3.. Continue in this fashion, add the copy of N after the Nth node

Now copy the random link in this fashion

original->next->random = original->random->next;

This works because original->next is nothing but copy of original and Original->random->next is nothing but copy of random.

Now restore the original and copy linked lists in this fashion in a single loop.

original->next = original->next->next;
copy->next = copy->next->next;
Make sure that last element of original->next is NULL.


public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if(head == null) return head;

        RandomListNode original=head;
        RandomListNode curOriginal=head;

        // step 1
        while(original.next != null ){
            RandomListNode copy=new RandomListNode(original.label);
            RandomListNode temp=original.next;
            original.next=copy;
            copy.next=temp;
            original=original.next.next;
        }
        original.next=new RandomListNode(original.label);

        //step 2
        while(curOriginal!= null && curOriginal.next != null){
            if(curOriginal.random != null){
                curOriginal.next.random=curOriginal.random.next;
            }
            curOriginal=curOriginal.next.next;
        }

        //step 3 and 4
        RandomListNode orig=head;
        RandomListNode copyCur=head.next;
        RandomListNode copyHead=head.next;

        while(orig.next != null && copyCur.next != null){
            orig.next=orig.next.next;
            copyCur.next=copyCur.next.next;
            orig=orig.next;
            copyCur=copyCur.next;
        }
        orig.next=null;

        return copyHead;
    }
}
*/
