/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
	public int deCode(ListNode n){
		int result = 0;
		int k = 1;
		while(n!=null){
			result += n.val*k;
			k *= 10;
			n = n.next;
		}
		return result;
	}

	public ListNode enCode(int x){
		ListNode head;
        ListNode last;
		ListNode temp;
		last = new ListNode(x%10);
		head = last;

		while((x - x%10)/10 != 0){
			x = (x - x%10)/10;
			temp = new ListNode(x%10);
			last.next = temp;
			last = temp;
		}

		return head;
	}

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	int n1 = deCode(l1);
    	int n2 = deCode(l2);

    	return enCode(n1+n2);
    }
}

//不能用这种方法，int会越界！！！

//Add directly
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode c1 = l1;
        ListNode c2 = l2;
        ListNode sentinel = new ListNode(0);
        ListNode d = sentinel;
        int sum = 0;
        while (c1 != null || c2 != null) {
            sum /= 10;
            if (c1 != null) {
                sum += c1.val;
                c1 = c1.next;
            }
            if (c2 != null) {
                sum += c2.val;
                c2 = c2.next;
            }
            d.next = new ListNode(sum % 10);
            d = d.next;
        }
        if (sum / 10 == 1)
            d.next = new ListNode(1);
        return sentinel.next;
    }
}



//Clear named solution
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummyHead = new ListNode(0);
    ListNode p = l1, q = l2, curr = dummyHead;
    int carry = 0;
    while (p != null || q != null) {
        int x = (p != null) ? p.val : 0;
        int y = (q != null) ? q.val : 0;
        int sum = carry + x + y;
        carry = sum / 10;
        curr.next = new ListNode(sum % 10);
        curr = curr.next;
        if (p != null) p = p.next;
        if (q != null) q = q.next;
    }
    if (carry > 0) {
        curr.next = new ListNode(carry);
    }
    return dummyHead.next;
}

