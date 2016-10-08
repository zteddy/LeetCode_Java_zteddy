/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode removeElements(ListNode head, int val) {

    	ListNode fakehead = new ListNode(0);
    	fakehead.next = head;
    	
    	ListNode previous = fakehead;
    	ListNode now = head;
    	

    	while(now != null){
    		if(now.val == val){
    			previous.next = now.next;
    			now = previous.next;
    		}
    		else{
    			previous = now;
    			now = now.next;
    		}
    	}

    	return fakehead.next;
    }
}

/*Using recursive
public ListNode removeElements(ListNode head, int val) {
        if (head == null) return null;
        head.next = removeElements(head.next, val);
        return head.val == val ? head.next : head;
}
*/

/*Another way except fakehead
public ListNode removeElements(ListNode head, int val) {
    if (head == null) return null;
    ListNode pointer = head;
    while (pointer.next != null) {
        if (pointer.next.val == val) pointer.next = pointer.next.next;
        else pointer = pointer.next;
    }
    return head.val == val ? head.next : head;
}
*/