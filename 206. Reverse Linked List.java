/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode reverseList(ListNode head) {

        if(head == null || head.next == null) return head;

        ListNode before = head;         //forget to put head.next to null;
        ListNode now = head.next;
        ListNode after;

        while(now != null){
        	after = now.next;
        	now.next = before;
        	before = now;
        	now = after;
        }

        head.next = null;    //Important!

        return before;

    }
}