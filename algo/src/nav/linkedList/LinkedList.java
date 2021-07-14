package nav.linkedList;

public class LinkedList {
    int value;
    LinkedList next;
    LinkedList(int val){
        this.value = val; next = null;
    }
    LinkedList(){};

}

class TestLinkedList {

    static LinkedList createLinkedList(int... vals){
        LinkedList head = null, ptr = null;
        for(int i: vals){
            if (ptr == null){
                ptr = new LinkedList(i);
                head = ptr;
            } else {
                ptr.next = new LinkedList(i);
                ptr = ptr.next;
            }
        }
        return head;
    }

    static void print(LinkedList head) {
        LinkedList ptr = head;
        while(ptr!=null){
            System.out.printf("%s->",ptr.value);
            ptr = ptr.next;
        }
        System.out.printf("NULL\n");
    }

    static void removeKthNode(LinkedList head, int k){
        LinkedList first = head, second = head, prevToFirst = null;
        int move = 0;
        while(move < k){
            second = second.next;
            move++;
        }

        //                .  .
        // 1->2->3->4->5->6   k=1 i.e. 6

        //          .  .  .  .
        // 1->2->3->4->5->6   k=3 i.e. 4
        while (second != null) {
            prevToFirst = first;
            first = first.next;
            second = second.next;
        }
        if (prevToFirst != null)
            prevToFirst.next = (first == null )? null : first.next;
        else {
            head.value = head.next.value;
            head.next = head.next.next;
        }
    }

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
//    class Solution {
//        public ListNode removeNthFromEnd(ListNode head, int n) {
//
//            ListNode first = head;
//            ListNode second = head;
//            ListNode prev = head;
//
//            int count = 0;
//            while (count < n){
//                second = second.next;
//                count++;
//            }
//
//            if (second == null) return first.next; // [1,2,3] n = 3 => 2,3
//
//            while(second !=null){
//                prev = first;
//                first = first.next;
//                second = second.next;
//            }
//
//            prev.next = first.next;
//            return head;
//        }
//    }

    public static LinkedList sumOfLinkedLists(LinkedList linkedListOne, LinkedList linkedListTwo) {
        LinkedList ptr1 = linkedListOne, ptr2 = linkedListTwo, newList = null, ptr3 = null;
        int carry = 0, sum = 0;
        while(ptr1 != null && ptr2 != null){
            sum = ptr1.value + ptr2.value + carry;
            carry = sum /10;
            LinkedList node = new LinkedList(sum%10);
            if (newList == null){
                newList = node;
                ptr3 = newList;
            } else {
                ptr3.next = node;
                ptr3 = ptr3.next;
            }
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }
        LinkedList ptr = ptr1 == null? ptr2:ptr1;
        while(ptr != null){
            sum = ptr.value + carry;
            carry = sum / 10;
            LinkedList node = new LinkedList(sum % 10);
            ptr3.next = node;
            ptr3 = ptr3.next;
            ptr = ptr.next;
        }
        if (carry > 0){
            LinkedList node = new LinkedList(carry);
            ptr3.next = node;
            ptr3 = ptr3.next;
        }

        return newList;
    }

    static LinkedList mergeLinkedLists(LinkedList headOne, LinkedList headTwo) {
        if (headOne == null) return headTwo;
        if (headTwo == null) return headOne;
        LinkedList p1 = headOne, p2 = headTwo, ret = null;
		/*
		        1--> 2--> 5--> 6
                1--> 2--> 3--> 8--> 9

                prev
		             p1
		        1    2    5--> 6
		        |   ^
		       \/  /  \.
                    1    2--> 3--> 8--> 9
                         p2
                ret

		*/
        while (p1!= null && p2!= null) {

            if (ret == null) {
                ret = p1.value <= p2.value ? p1 : p2;
            }

            if (p1.value <= p2.value) {
                LinkedList prev = p1;
                while (p1 != null && p2!= null && p1.value <= p2.value) {
                    prev = p1;
                    p1 = p1.next;
                }
                p1 = prev;
                LinkedList nextp1 = p1.next;
                p1.next = p2;
                p1 = nextp1;
            } else {
                LinkedList prev = p2;
                while (p1 != null && p2!= null && p1.value > p2.value) {
                    prev = p2;
                    p2 = p2.next;
                }
                p2 = prev;
                LinkedList nextp2 = p2.next;
                p2.next = p1;
                p2 = nextp2;
            }
        }
        return ret;
    }

    static LinkedList mergeLinkedListsRec(LinkedList headOne, LinkedList headTwo){
        if (headOne == null) return headTwo;
        if (headTwo == null) return headOne;
        LinkedList one =  headOne.value<= headTwo.value? headOne:headTwo;
        LinkedList two =  headOne.value > headTwo.value? headOne:headTwo;
        LinkedList ptr = mergeLinkedListsRec(one.next, two);
        one.next = ptr;
        return one;
    }

    public static void main(String[] args) {
        LinkedList head = null;
        for(int i = 0; i <= 10; i++) {
            head = createLinkedList(1,2,3,4,5,6,7,8,9,10);
            print(head);
            removeKthNode(head, i);
            print(head);
            System.out.println("=====");
        }

        head = createLinkedList(2,4,7,1);
        LinkedList head1 = createLinkedList(9,4,5);
        print(head);
        print(head1);
        LinkedList p = sumOfLinkedLists(head, head1);
        print(p);
        System.out.println("-------");
        LinkedList one = createLinkedList(1,1,1,1,3, 4, 5,5,5,5, 10);
        LinkedList two = createLinkedList(1,1,1,2, 2, 5,6,10,10);
        print(mergeLinkedLists(one, two));

        LinkedList one_ = createLinkedList(1,1,1,1,3, 4, 5,5,5,5, 10);
        LinkedList two_ = createLinkedList(1,1,1,2, 2, 5,6,10,10);
        print(mergeLinkedListsRec(one_, two_));

        System.out.println("==========");
        LinkedList swapping = createLinkedList(1,2,3,4,5,6);
        print(swapping);
        LinkedList swapped = swapNodes(swapping, 2);
        print(swapped);

        System.out.println("==========");
        LinkedList removenth = createLinkedList(1,2,3,4,5);
        LinkedList removednth = removeNthFromEnd(removenth, 2);
        print(removednth);


    }

    public static LinkedList removeNthFromEnd(LinkedList head, int n) {

        if (head == null) return null;
        // keep a distance of n between two pointers
        LinkedList first = null, second =null;

        // 1,2,(3),4,[5],6,7 => n = 3 size = 7;
        // p1 = 4 p2=7 diff = 3
        // 1,2,3,4,5,6,7
        //      p1
        // i=4       ;4
        for (int i = 0; i < n+1; i++){
            if (second == null){
                second = head;
            }else {
                second = second.next;
            }

        }

        first = head;
        if (second == null) return first.next; // [1,2,3] n = 3 => 2,3
        while(second.next != null){
            first = first.next;
            second = second.next;
        }
        LinkedList nodeToRemove = first.next;
        first.next = nodeToRemove.next;
        //nodeToRemove.next = null;
        return head;

    }
 //only values
    public static LinkedList swapNodes(LinkedList head, int k) {

        if (head == null) return head;
        int n = 0;
        LinkedList ptr = head;
        while (ptr != null){
            n++;
            ptr = ptr.next;
        }
        /*
           1->*2-3->4-*5->6   k=2, n=6

         */
        int firstPtrPos = k, secondPtrPos = n - k+1; // 1-based indexing
        LinkedList p1 = null, p2 = null;
        if (secondPtrPos < firstPtrPos ){ // [1,2] k=2 => firstPtrPos = 2, secondPtrPos = 2-2+1 = 1
            int tmp = firstPtrPos;
            firstPtrPos = secondPtrPos;
            secondPtrPos = tmp;
        }
        // case 1: 1->2->3, k =2, n=3 => firstPtrPos = 1 secondPtrPos= 1 => same node to be swapped
        if (k < 1 || firstPtrPos == secondPtrPos ) return head;
        //case 2: 1-2-3-4-5-6-7-8, k=3, n = 8 => firstPtrPos = 3 secondPtrPos= 8-3 + 1 = 6

        int i =0;
        for (; i < firstPtrPos; i++){
            if (p1 == null){
                p1 = head;
            } else{
                p1 = p1.next;
            }
        }
        p2 = p1;
        for (; i < secondPtrPos; i++){
            p2 = p2.next;
        }

        LinkedList tmp = p1;
        p1.value = p2.value;
        p2.value = tmp.value;
        return head;
        /*
           [1] => n = 1, k=1 => firstPtrPos = 1, secondPtrPos = 1-1+1
           [1,2] => n=2, k=1 => firstPtrPos = 1, secondPtrPos = 2-1+1 =2
           [1,2,3] =>n
         */

    }
}
