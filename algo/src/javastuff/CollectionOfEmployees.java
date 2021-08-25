package javastuff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionOfEmployees {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>(
                Arrays.asList(
                        new Employee("Navneet", "Kumar", 11),
                        new Employee("Navneet", "Kumar", 10),
                        new Employee("Ram", "Kumar", 11),
                        new Employee("David", "XYZ", 11),
                        new Employee("Navneet", "Kumar", 12)
                )
        );
        Map<String, List<Employee>> collect = employees.stream().collect(Collectors.groupingBy(e -> e.lastName + " " + e.deptNo));
        collect.forEach((k,v)-> {
            System.out.println(k);
            for(Employee e: v) {
                System.out.println( " " + e.toString());
            }
            System.out.println("======");
        });

        Map<String, Long> collect1 = employees.stream().
                collect(Collectors.groupingBy(e -> e.lastName + " " + e.deptNo, Collectors.counting()));
        System.out.println("++++++++++++++++");
        collect1.forEach((k,v)-> {
            System.out.println(k + "->" + v);

        });

        Map<Employee, List<Employee>> map = new HashMap<>();
        for(Employee e: employees){
            System.out.println("Putting "+ e + " hash " + e.hashCode());
            if (map.containsKey(e)){
                List<Employee> listOfEmp = map.get(e);
                listOfEmp.add(e);
                System.out.println("Already contained " + e);
            } else {
                List<Employee> listOfEmp = new ArrayList<>(Arrays.asList(e));
                map.put(e, new ArrayList<>(Arrays.asList(e)) );
            }
        }

        map.forEach((k,v)-> {
            System.out.println(k);
            for(Employee e: v) {
                System.out.println( " " + e.toString());
            }
            System.out.println("======");
        });

        List<Item> items = Arrays.asList(
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 20, new BigDecimal("19.99")),
                new Item("orang", 10, new BigDecimal("29.99")),
                new Item("watermelon", 10, new BigDecimal("29.99")),
                new Item("papaya", 20, new BigDecimal("9.99")),
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 10, new BigDecimal("19.99")),
                new Item("apple", 20, new BigDecimal("9.99"))
        );

        Map<String, Long> counting = items.stream().collect(
                Collectors.groupingBy(Item::getName, Collectors.counting()));

        System.out.println(counting);// {papaya=1, banana=2, apple=3, orang=1, watermelon=1}


        Map<String, Integer> sum = items.stream().collect(
                Collectors.groupingBy(Item::getName, Collectors.summingInt(Item::getQty)));

        System.out.println(sum);// {papaya=20, banana=30, apple=40, orang=10, watermelon=10}
    }
}

@AllArgsConstructor
@Data
@ToString
class Employee{
    String firstName;
    String lastName;
    int deptNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (deptNo != employee.deptNo) return false;
        return lastName != null ? lastName.equals(employee.lastName) : employee.lastName == null;
    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + deptNo;
        return result;
    }
}
@AllArgsConstructor
@Data
@ToString
class Item {

    private String name;
    private int qty;
    private BigDecimal price;

    //constructors, getter/setters
}