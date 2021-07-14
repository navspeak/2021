package nav;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ToString
public class Main {

    public static void main(String[] args) {
        Set<Integer> test = new HashSet<>();
        System.out.println(test.add(5));
        System.out.println(test.add(5));//false

//        Queue<Integer> q = new LinkedList<>();
//        q.poll(); // null if empty, remove exception;
//        q.add(8);
        int[] emptyArray = new int[10];
        Arrays.fill(emptyArray, 1);
        IntStream.of(emptyArray).boxed().collect(Collectors.toList());
        IntStream.of(emptyArray).sum();
        int[][] twoD = new int[5][];
        // Array to List
        Integer[] arr = new Integer[]{1,2,3}; // if you had int[] instead it won't work
        List<Integer> ints = Arrays.asList(arr);

        int p = 5, q = 9;
        for (int i = 0; i < 10; i++)
            System.out.println(p + new Random().nextInt(q-p));

        Integer[] a1 = {1,2,3};
        List<Integer> l1 = Arrays.stream(a1).collect(Collectors.toList()); // again int[] to List<Integer> won't work
        l1 = List.of(a1); // better
        int[] a_1 = {1,2,3};
        l1 = IntStream.of(a_1).boxed().collect(Collectors.toList());

        //1 create a List from an Array and sort it.
        List<Integer> listFromIntArray = Arrays.stream(new int[]{1,2,3}).boxed().collect(Collectors.toList());
        /*or */ listFromIntArray = IntStream.of(1,2,3,4).boxed().collect(Collectors.toList());
        listFromIntArray.sort(Comparator.naturalOrder());

        // 2. Set to List
        Set<List<Integer>> set = new HashSet<>();

        set.add(Arrays.asList(1,2,3));
        List<List<Integer>> listOfList = List.copyOf(set);

        List<Integer> triplets = Arrays.asList(10,2,3);
        triplets.sort(Comparator.naturalOrder());
        int[][] _2darr = { {1,2}, {6,8},{3,1}};
        Arrays.sort(_2darr, (x,y) -> Integer.compare(x[0],y[0])); //  return (x < y) ? -1 : ((x == y) ? 0 : 1);

        //ListOp();
        //LinkedList();
        //dequeOp();
        //queueOp();
        //priorityQ();
        //treeMapOp();
        List<Integer> list = new ArrayList<>();
        System.out.println(list.contains(4));

//        List<List<Integer>> lofl = new ArrayList<>();
//        lofl.add(new ArrayList<>());
//        System.out.println(Arrays.deepToString(lofl.toArray()));
//        List<List<Integer>> permutations = getPermutations(new ArrayList(Arrays.asList(1,2,3)));
//        List<String> perms = getPermutations("");
//        System.out.println(Arrays.toString(perms.toArray()));
        System.out.println(Arrays.deepToString(powerset(new ArrayList<>(Arrays.asList(1,2,3))).toArray()));
    }


/* T: N/2*2^N | S(O(N)*/
    public static List<List<Integer>> powerset(List<Integer> array) {
        List<List<Integer>> result = new ArrayList<>();
        if (array.size() == 0){
            result.add(new ArrayList<>());
            return result;
        }
        Integer curr = array.remove(0);
        List<List<Integer>> subList = powerset(array);


        for (List<Integer> list: subList){
            result.add(new ArrayList<>(list));
            list.add(curr);
            result.add(list);
        }
        return result;
    }



    public static List<List<Integer>> getPermutations(List<Integer> array) {
        List<List<Integer>> result = new ArrayList<>();
        if (array.size() == 0) {
            return result;
        }
        int curr = array.remove(0);
        List<List<Integer>> subResult = getPermutations(array);
        if (subResult.size() == 0) {
            result.add(new ArrayList<>(Arrays.asList(curr)));
            return result;
        }
        for(List<Integer> list: subResult){
            for(int i = 0; i <= list.size(); i++){
                List<Integer> tmpList = new ArrayList<>(list);
                tmpList.add(i, curr);
                result.add(tmpList);
            }
        }

        return result;
    }
//https://www.youtube.com/watch?v=6JF-fxiDjIc - Nick White
    private static void treeMapOp() {
        TreeMap<Integer, Integer> map = new TreeMap<>(Comparator.reverseOrder());
        map.put(1,1);
        map.put(10,2);
        map.put(188,3);
        map.put(17,4);
        map.put(19,4);
        map.put(81,5);
        map.replace(81,7);

        System.out.println(map.floorEntry(17).getValue());
    }

    private static void priorityQ() {
        Queue<Person> people = new PriorityQueue<>(Comparator.reverseOrder());
        people.add(Person.builder().age(1).build());
        people.add(Person.builder().age(10).build());
        people.add(Person.builder().age(15).build());
        people.add(Person.builder().age(100).build());
        people.add(Person.builder().age(12).build());
        people.forEach(System.out::println);
    }

    @Builder
    @Data
    static class Person implements  Comparable<Person> {
        int age;
        public int compareTo(Person o){
            return Integer.compare(this.age, o.age);
        }
    }

    private static void queueOp() {
        Queue<Integer> q = new ArrayDeque<>(); // or linkedList
        q.add(0);
        //deque.push(1); // Not applicable
        q.offer(1);
        q.add(2);
        q.add(3);
        q.add(4); // same as addLast

        //deque.addLast(5); // NA
        System.out.println(q); // 1 0 2 3 4 5
//   NA     System.out.println(deque.pop()); // 1 from index 0
//   NA     System.out.println(deque.pop()); // 0 from index 0
        System.out.println(q.poll()); //poll will also give from 0
//        System.out.println(deque.pollFirst()); //poll will also give from 0
        System.out.println(q);
        System.out.println(q.remove()); //remove will also give from first
        System.out.println(q);
    }

    private static void dequeOp() {
        Deque<Integer> deque = new LinkedList<>();
        deque.add(0);
        deque.push(1); // adds at index 0
        deque.add(2);
        deque.add(3);
        deque.add(4); // same as addLast
        deque.addLast(5); // same as addLast
        System.out.println(deque); // 1 0 2 3 4 5
        System.out.println(deque.pop()); // 1 from index 0
        System.out.println(deque.pop()); // 0 from index 0
        System.out.println(deque.poll()); //poll will also give from 0
        System.out.println(deque.pollFirst()); //poll will also give from 0
        System.out.println(deque);
        System.out.println(deque.pollLast()); //pollLast will also give from last
        System.out.println(deque.remove()); //remove will remove from last also give from last
        System.out.println(deque);
    }

    private static void LinkedList() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.addFirst(0);
        linkedList.addLast(4); // 0 1 2 4
        System.out.println(linkedList.remove());
        System.out.println(linkedList.removeFirst());
        System.out.println(linkedList);
        linkedList.removeIf(i -> i == 2);
        System.out.println(linkedList);
    }

    private static void ListOp() {
        List<Integer> integerList = new LinkedList<>();
        System.out.println(integerList.isEmpty()); // NPE if list = null
        //integerList.add(1);
        integerList.add(0,3);
        integerList.add(1,4);
        integerList.add(2);
        integerList.add(3);
        integerList.set(3,8);
        //System.out.println(integerList.remove(NEED INDEX));
        System.out.println(integerList);
    }
}
