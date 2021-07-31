package java;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
// https://www.geeksforgeeks.org/java-collectors/
public class Streams {
    public static void main(String[] args) {
        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");
        List<String> collect = myList.stream()
                .filter(s -> s.endsWith("1"))
                .map(c -> c.toUpperCase())
                .sorted()
                .collect(Collectors.toList());
        System.out.println(Arrays.toString(collect.toArray()));

        Stream.of("a1", "a2", "a3").
                filter(s->s.startsWith("a")).// put c instead of a
                findFirst(). // returns Optional
                ifPresentOrElse(v->System.out.println("Found " + v), ()-> System.out.println("Nothing found"));
        /* ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)*/

        Deque<Integer> collect1 = IntStream.range(0, 1000)
                .map(Utils::divideBy5)
                .mapToObj(Integer::valueOf)//same as boxed
                .sorted(Comparator.reverseOrder())
                //.sorted(Comparator.comparingInt(Integer::intValue).reversed())
                .collect(Collectors.toCollection(ArrayDeque::new));// LinkedList.new


        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        Stream<Integer> stream = Stream.of(4, 5);
        stream.collect(Collectors.toCollection(() -> list));
        System.out.println(list);//12345

        List<City> cities = new ArrayList<>(Arrays.asList(
                new City("New Delhi", 33.5),
                new City("Mexico", 14),
                new City("New York", 13),
                new City("Dubai", 43),
                new City("London", 15),
                new City("Alaska", 1),
                new City("Kolkata", 30),
                new City("Sydney", 11),
                new City("Mexico", 14),
                new City("Dubai", 43)
        ));


        // get names of all cities with temp > 15
        Set<String> set = cities.stream()
                .filter(city-> city.temp > 10)
                .map(city->city.getName()) // <R> Stream<R>	map(Function<? super T,? extends R>mapper)
                .collect(Collectors.toSet());
        Set<String> set1 = cities.stream()
                .filter(city-> city.temp > 10)
                .map(City::getName) // <R> Stream<R>	map(Function<? super T,? extends R>mapper)
                .collect(Collectors.toSet());

//        int[] a = new int[]{1,2,3};
//        List<Integer> x = IntStream.of(a).mapToObj(Integer::valueOf).collect(Collectors.toList());
//        List<Integer> y = IntStream.of(a).boxed().collect(Collectors.toList());
        // Collector<T, ?, Map< K, U>> toMap(Function keyMapper, Function valueMapper):
        Map<String, Double> map = null; // will fail if duplicate found
        try {
            map = cities.stream().filter(c -> c.getTemp() > 10)
                    .collect(Collectors.toMap(City::getName, City::getTemp));
            for (Map.Entry<String, Double> entry: map.entrySet()){
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
        // We can avoid and fix this issue by avoiding the key collision(in case of duplicate keys)
        // with the third argument that is BinaryOperator.


        Map<String, List<City>> collect4 = cities.stream()
                .collect(Collectors.groupingBy(City::getName));

        Map<String, Long> collect3 = cities.stream()
                .collect(Collectors.groupingBy(City::getName, Collectors.counting()));

        Map<String, Integer> collect2 = cities.stream()
                .collect(Collectors.groupingBy(c -> c.name,//City::getName
                        Collectors.collectingAndThen(
                                Collectors.counting(), f -> f.intValue()
                        )));

        System.out.println(cities.stream()
                .map(c -> "[Name = " + c.getName() + ", Temp =" + c.getTemp() + "]")
                .collect(Collectors.joining(" , ")));

        final Map<Boolean, List<City>> collect5 = cities.stream()
                .collect(Collectors.partitioningBy(c -> c.temp > 15));
        // false: [city1NotMeetingCriteria, ....], True: [city1MeetingCriteria, ...]

        Map<String, Double> map1 = cities.stream().filter(c -> c.getTemp() > 10)
                .collect(Collectors.toMap(City::getName, City::getTemp, (key, dupKey) -> key));

        Stream.of("dog", "cat")              // stream of 2 Strings
                .map(s -> s.length())    // stream of 2 Integers: [3, 3]
                .forEach(System.out::println);

        Stream.of("dog", "cat")             // stream of 2 Strings
                .flatMapToInt(s -> s.chars()) ;  // stream of 6 ints:      [d, o, g, c, a, t]

        //https://www.baeldung.com/java-flatten-nested-collections
        List<Integer> l1 = Arrays.asList(1,2,3);
        List<Integer> l2 = Arrays.asList(11,22,33);
        List<Integer> l3 = Arrays.asList(111,222,333);
        List<List<Integer>> l = Arrays.asList(l1,l2,l3);
        List<Integer> merged = l.stream().flatMap(Collection::stream).collect(Collectors.toList());


        //Stream.of(Arrays.asList(1,2,3))

        "abc".chars().forEach(System.out::println); //97,98,99
        "abc".chars().map(x->(char)x).forEach(System.out::println); // 97,98,99
        "abc".chars().mapToObj(x->(char)x).forEach(System.out::println); //a,b.c
        //System.out.println(Character.forDigit('9', 10)); => 9 as char
    }

    public static class Utils {
        public static int divideBy5(int value){
            return value / 5;
        }
    }

    @Data
    @AllArgsConstructor
    public static class City {
        private String name;
        private double temp;
    }
}
