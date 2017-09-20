/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        ListNode a = head, b = head;
        if(k == 0) return head;
        if(head == null) return head;
        for(int i = 0; i < k; i++){
            if(b == null){
                b = head;
                k = k % i;
            }
            b = b.next;
        }
        if(b == null) return head;
        ListNode c = b.next;
        while(c != null){
            a = a.next;
            b = b.next;
            c = c.next;
        }

        ListNode result = a.next;

        a.next = null;
        b.next = head;

        return result;

    }
}






/*Using count
Since n may be a large number compared to the length of list. So we need to know the length of linked list.After that, move the list after the (l-n%l )th node to the front to finish the rotation.

Ex: {1,2,3} k=2 Move the list after the 1st node to the front

Ex: {1,2,3} k=5, In this case Move the list after (3-5%3=1)st node to the front.

So the code has three parts.

Get the length

Move to the (l-n%l)th node

3)Do the rotation

public ListNode rotateRight(ListNode head, int n) {
    if (head==null||head.next==null) return head;
    ListNode dummy=new ListNode(0);
    dummy.next=head;
    ListNode fast=dummy,slow=dummy;

    int i;
    for (i=0;fast.next!=null;i++)//Get the total length
        fast=fast.next;

    for (int j=i-n%i;j>0;j--) //Get the i-n%i th node
        slow=slow.next;

    fast.next=dummy.next; //Do the rotation
    dummy.next=slow.next;
    slow.next=null;

    return dummy.next;
}
*/





/*Build a cycle
My idea is, first concat the tail to the head, building a cycle, then decide where to break the cycle to get the resulting list. The advantage of the method is, you dont need to worry about n being larger than the length of the list.

public ListNode rotateRight(ListNode head, int n) {
    if (head == null) return null;
    ListNode tail = head;                       //first, build a cycle
    while (tail.next != null){
        tail = tail.next;
    }
    tail.next = head;                          //cycle built.
    ListNode fast = head, slow = head;         //now find where to break the cycle
    while (n-->0){
        fast = fast.next;                      //move the fast runner first
    }
    while (fast!=tail){
        fast = fast.next;                      //then move the fast and the slow runners together
        slow = slow.next;
    }
    head = slow.next;                          //break the cycle at after the slow runner
    slow.next = null;
    return head;                              // return the new head
}
*/

