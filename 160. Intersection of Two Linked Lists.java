/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

    	Map<ListNode, ListNode> hm = new HashMap<>();
    	ListNode pa = headA;
    	ListNode pb = headB;

    	if(headA == null || headB == null) return null;

    	while(pa != null){
    		hm.put(pa, pa);
    		pa = pa.next;
    	}

    	while(pb != null){
    		if(hm.containsKey(pb)) return pb;
    		pb = pb.next;
    	}

    	return null;

    }
}

/*Scanning length first
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    int lenA = 0, lenB = 0;
    ListNode currA = headA, currB = headB;
    while(currA != null) {
        lenA++;
        currA = currA.next;
    }
    while(currB != null) {
        lenB++;
        currB = currB.next;
    }
    currA = headA;
    currB = headB;
    if(lenA > lenB) {
        for(int i=0; i<lenA-lenB; i++)
            currA = currA.next;
    } else {
        for(int i=0; i<lenB-lenA; i++)
            currB = currB.next;
    }
    while(currA != currB) {
        currA = currA.next;
        currB = currB.next;
    }
    return currA;
}
*/

/*Approach #3 (Two Pointers) [Accepted]

Maintain two pointers pA and pB initialized at the head of A and B, respectively. Then let them both traverse through the lists, one node at a time.
When pA reaches the end of a list, then redirect it to the head of B (yes, B, that's right.); similarly when pB reaches the end of a list, redirect it the head of A.
If at any point pA meets pB, then pA/pB is the intersection node.
To see why the above trick would work, consider the following two lists: A = {1,3,5,7,9,11} and B = {2,4,9,11}, which are intersected at node '9'.
Since B.length (=4) < A.length (=6), pB would reach the end of the merged list first, because pB traverses exactly 2 nodes less than pA does.
By redirecting pB to head A, and pA to head B, we now ask pB to travel exactly 2 more nodes than pA would. So in the second iteration, they are guaranteed to reach the intersection node at the same time.
If two lists have intersection, then their last nodes must be the same one. So when pA/pB reaches the end of a list, record the last element of A/B respectively.
If the two last elements are not the same one, then the two lists have no intersections.

public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		if( null==headA || null==headB )
			return null;

		ListNode curA = headA, curB = headB;
		while( curA!=curB){
			curA = curA==null?headB:curA.next;
			curB = curB==null?headA:curB.next;
		}
		return curA;
    }

Complexity Analysis

Time complexity : O(m+n).
Space complexity : O(1).
*/
