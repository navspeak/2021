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


    }
}
