/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

public class Solution {
    public boolean isPalindrome(ListNode head) {

    	if(head == null) return true;
    	Map<Integer, Integer> hm = new HashMap<>();

    	ListNode p;
    	p = head;

    	while(p != null){
    		if(hm.containsKey(p.val)) hm.remove(p.val);
    		else hm.put(p.val,p.val);
    		p = p.next;
    	}

    	if(hm.size() <= 1) return true;
    	else return false;
    }
}

//WA



//AC two-pointer solution
public class Solution {
    public boolean isPalindrome(ListNode head) {
        if(head == null) {
            return true;
        }
        ListNode p1 = head;
        ListNode p2 = head;
        ListNode p3 = p1.next;
        ListNode pre = p1;
        //find mid pointer, and reverse head half part
        while(p2.next != null && p2.next.next != null) {
            p2 = p2.next.next;
            pre = p1;
            p1 = p3;
            p3 = p3.next;
            p1.next = pre;
        }

        //odd number of elements, need left move p1 one step
        if(p2.next == null) {
            p1 = p1.next;
        }
        else {   //even number of elements, do nothing

        }
        //compare from mid to head/tail
        while(p3 != null) {
            if(p1.val != p3.val) {
                return false;
            }
            p1 = p1.next;
            p3 = p3.next;
        }
        return true;

    }
}


//AC recursive solution
public class Solution {
    public ListNode root;
    public boolean isPalindrome(ListNode head) {
        root = head;
        return (head == null) ? true : _isPalindrome(head);
    }

    public boolean _isPalindrome(ListNode head) {
        boolean flag = true;
        if (head.next != null) {
            flag = _isPalindrome(head.next);
        }
        if (flag && root.val == head.val) {
            root = root.next;
            return true;
        }
        return false;
    }
}
