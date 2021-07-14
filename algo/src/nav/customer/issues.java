package nav.customer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class issues {
    public static void main(String[] args) {
        /*
        1. DOS using Antivirus
        2. Http Authz header - memory leak in REST adapter
        3. Proxy bug in Http client
        4. PDSOE deployment issues: https://knowledgebase.progress.com/articles/Knowledge/deployment-of-paar-file-failed-with-error-updating-descriptor-error-parsing-descriptor-file
        5. 
         */

        Deque<Integer> deque = new ArrayDeque<>();
        deque.push(0);
        deque.push(1);
        deque.push(2);
        deque.push(3);
        deque.add(4);
        deque.forEach(System.out::println); // 3 2 1 0 4
        System.out.println();
        System.out.println(deque.removeLast()); // at last is 4
        System.out.println(deque.pop()); // pop from head i.e. 0 => 3
        System.out.println();
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println();
        stack.forEach(System.out::println); //

    }

    /*
     1 - Ownership
       - jsdo, RADIUS
     2 - Invent ans Simply
     3. Customer Obsession
     4. Success and Scale Bring Broad Responsibility
     5. Learn and Be Curious
     5. Highest Standard
     6. Deliver Results
     7. Bias of action


     apache bench mark, jmeter
     ab -c 10 -t 5  "http://localhost:3055/sync"

     https://www.progress.com/customers/details/dmsi-software-rollbase
     */
}
