/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {

	public void deleteNode(ListNode previous, ListNode now){
		previous.next = now.next;
	}

    public ListNode deleteDuplicates(ListNode head) {

    	Map<Integer, Integer> hm = new HashMap<>();

    	if(head == null) return head;

    	ListNode now = head.next;
    	ListNode previous = head;

    	hm.put(head.val, head.val);

    	while(now != null){

    		if(hm.containsKey(now.val)){      //int can be auto-trans to Integer
    			deleteNode(previous, now);
    			now = previous.next;
    		}
    		else{
    			hm.put(now.val, now.val);
    			previous = now;
    			now = now.next;
    		}
    	}

    	return head; 
    }
}

//TODO None