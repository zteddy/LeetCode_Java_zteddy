/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {

    	Map<Integer, ListNode> hm = new HashMap<>();

    	ListNode fakehead = new ListNode(0);
		fakehead.next = head;

    	ListNode p = fakehead;
    	Integer count = 0;

    	while(p != null){
    		hm.put(count, p);
    		p = p.next;
    		count += 1;
    	}

    	ListNode before = hm.get(count - n -1);
    	ListNode after = hm.get(count - n + 1);

    	before.next = after;

        return fakehead.next;
    }
}

/*One-pass solution: Using two pointers
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode first = dummy;
    ListNode second = dummy;
    // Advances first pointer so that the gap between first and second is n nodes apart
    for (int i = 1; i <= n + 1; i++) {
        first = first.next;
    }
    // Move first to the end, maintaining the gap
    while (first != null) {
        first = first.next;
        second = second.next;
    }
    second.next = second.next.next;
    return dummy.next;
}
*/