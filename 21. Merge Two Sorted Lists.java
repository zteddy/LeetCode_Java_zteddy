/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    	ListNode p1;
    	ListNode p2;
    	ListNode fakehead = new ListNode(0);
    	fakehead.next = l1;
    	ListNode b_p1;
    	ListNode a_p2;

    	p1 = l1;
    	p2 = l2;
    	b_p1 = fakehead;

    	if(l1 == null) return l2;

    	while(p1 != null && p2 != null){
    		a_p2 = p2.next;
    		if(p2.val <= p1.val){
    			b_p1.next = p2;
    			p2.next = p1;
    			b_p1 = p2;
    			p2 = a_p2;
    		}
    		else{
    			b_p1 = p1;
    			p1 = p1.next;
    		}
    	}

    	if(p1 == null && p2 != null) b_p1.next = p2;

    	return fakehead.next;
 
        
    }
}

/*Using recursive solution
public ListNode mergeTwoLists(ListNode l1, ListNode l2){
		if(l1 == null) return l2;
		if(l2 == null) return l1;
		if(l1.val < l2.val){
			l1.next = mergeTwoLists(l1.next, l2);
			return l1;
		} else{
			l2.next = mergeTwoLists(l1, l2.next);
			return l2;
		}
}
*/

/*More clear solution
 public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
	if (l1 == null || l2 == null) {
		return l1 == null ? l2 : l1;
	}
	ListNode head = null;
	ListNode node = null;
	if (l1.val < l2.val) {
		head = l1;
		l1 = l1.next;
	} else {
		head = l2;
		l2 = l2.next;
	}
	node = head;

	while (l1 != null && l2 != null) {
		if (l1.val < l2.val) {
			node.next = l1;
			l1 = l1.next;
		} else {
			node.next = l2;
			l2 = l2.next;
		}
		node = node.next;
	}
	if (l1 == null) {
		node.next = l2;
	}
	if (l2 == null) {
		node.next = l1;
	}
	return head;
}
*/