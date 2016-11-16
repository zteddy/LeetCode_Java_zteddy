/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {

    	Map<ListNode, ListNode> hm = new HashMap<>();

    	ListNode p = head;

    	if(p != null){
    		if(hm.containsKey(p)) return true;
    		else hm.put(p,p);
    		p = p.next;
    	}

    	return false;
    }
}

/*Approach #2 (Two Pointers) [Accepted]

Intuition

Imagine two runners running on a track at different speed. What happens when the track is actually a circle?

Algorithm

The space complexity can be reduced to O(1) by considering two pointers at different speed - a slow pointer and a fast pointer. The slow pointer moves one step at a time while the fast pointer moves two steps at a time.

If there is no cycle in the list, the fast pointer will eventually reach the end and we can return false in this case.

Now consider a cyclic list and imagine the slow and fast pointers are two runners racing around a circle track. The fast runner will eventually meet the slow runner. Why? Consider this case (we name it case A) - The fast runner is just one step behind the slow runner. In the next iteration, they both increment one and two steps respectively and meet each other.

How about other cases? For example, we have not considered cases where the fast runner is two or three steps behind the slow runner yet. This is simple, because in the next or next's next iteration, this case will be reduced to case A mentioned above.

public boolean hasCycle(ListNode head) {
    if (head == null || head.next == null) {
        return false;
    }
    ListNode slow = head;
    ListNode fast = head.next;
    while (slow != fast) {
        if (fast == null || fast.next == null) {
            return false;
        }
        slow = slow.next;
        fast = fast.next.next;
    }
    return true;
}
Complexity analysis

Time complexity : O(n). Let us denote nn as the total number of nodes in the linked list. To analyze its time complexity, we consider the following two cases separately.

List has no cycle:
The fast pointer reaches the end first and the run time depends on the list's length, which is O(n).

List has a cycle:
We break down the movement of the slow pointer into two steps, the non-cyclic part and the cyclic part:

The slow pointer takes "non-cyclic length" steps to enter the cycle. At this point, the fast pointer has already reached the cycle. \text{Number of iterations} = \text{non-cyclic length} = NNumber of iterations=non-cyclic length=N

Both pointers are now in the cycle. Consider two runners running in a cycle - the fast runner moves 2 steps while the slow runner moves 1 steps at a time. Since the speed difference is 1, it takes \frac{\text{distance between the 2 runners}}{\text{difference of speed}}
​difference of speed
​
​distance between the 2 runners
​​  loops for the fast runner to catch up with the slow runner. As the distance is at most "cyclic length K" and the speed difference is 1, we conclude that \text{Number of iterations} = \text{almost } `` \text{cyclic length K} ".Number of iterations=almost ‘‘cyclic length K".

Therefore, the worst case time complexity is O(N+K)O(N+K), which is O(n)O(n).

Space complexity : O(1). We only use two nodes (slow and fast) so the space complexity is O(1).
*/
