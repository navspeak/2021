package nav.arrays;

public class LinkedList {
    int value;
    LinkedList next;
    LinkedList(int val){this.value = value; next = null;}
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
        System.out.println();
    }


    public static void main(String[] args) {
       LinkedList head = createLinkedList(1,2,3);
       print(head);
    }
}
