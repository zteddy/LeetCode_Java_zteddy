/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode swapPairs(ListNode head) {

    	ListNode a = null;
    	ListNode b = null;
    	ListNode c = null;
    	ListNode temp;

    	ListNode dummy = new ListNode(0);  //Important!!! Fake head
    	/*
    	if(head == null) dummy.next = head;
    	else if(head.next == null) dummy.next = head;
    	else dummy.next = head.next; 
    	*/
    	dummy.next = head;
    	a = dummy;
    	b = a.next;
    	while(b != null){
    		c = b.next;
    		if(c != null){
	    		temp = c.next;
	    		c.next = b;
	    		b.next = temp;
	    		a.next = c;
    		}
    		a = b;
    		b = a.next; 
    	}

    	return dummy.next;
    }
}


/*Using recursive
public class Solution {
    public ListNode swapPairs(ListNode head) {
        if ((head == null)||(head.next == null))
            return head;
        ListNode n = head.next;
        head.next = swapPairs(head.next.next);
        n.next = head;
        return n;
    }
}
*/