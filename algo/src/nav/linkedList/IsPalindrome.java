package nav.linkedList;
// https://leetcode.com/problems/palindrome-linked-list/submissions/
// approach:
// - go to mid. Reverse the 2nd half and compare
public class IsPalindrome {
    private static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public boolean isPalindrome(ListNode head) {

        ListNode fast = head, slow = head;
        while(fast!=null && fast.next !=null){
            fast = fast.next.next;
            slow = slow.next;
        }

        slow = reverse(slow);
        fast = head;
        while(slow != null){
            if (fast.val != slow.val) return false;
            fast = fast.next;
            slow = slow.next;
        }
        return true;
    }

    ListNode reverse(ListNode head){
        if (head == null || head.next == null) return head;
        ListNode rev = reverse(head.next);
        head.next.next =head;
        head.next = null;
        return rev;
    }
}
