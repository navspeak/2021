package nav.linkedList;

public class SwapNodes {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode prev = null;
        ListNode p1 = head, p2 = null;
        while(p1 != null && p1.next!=null){
            p2 = p1.next;
            if (prev == null){
                head = p2;
            }
            ListNode p2Next = p2.next;
            if (p2!= null) p2.next = p1;
            if (prev != null){
                prev.next = p2;
            }
            p1.next = p2Next;
            prev = p1;
            p1 = p2Next;
        }
        return head;
        // ListNode p1 = null, p2 = head, tmp = null;
        // int i = 0;
        // while(p2!=null && i < 2) {
        //     tmp = p2.next;
        //     p2.next = p1;
        //     p1 = p2;
        //     p2 = tmp;
        //     i++;
        // }
        // head.next = swapPairs(p2);
        // return p1;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
