package javastuff.designPatterns;

import java.util.function.BiFunction;

// using java 8
public class StrategyPattern {
    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> add = Math::addExact;
        BiFunction<Integer, Integer, Integer> subtract = Math::subtractExact;
        BiFunction<Integer, Integer, Integer> multiply = Math::multiplyExact;

        Function3<BiFunction<Integer, Integer, Integer>,
                Integer, Integer, Integer> execute = (callback, a, b) -> callback.apply(a, b);

        System.out.println("Add:      " + execute.apply(add, 3, 4));
        System.out.println("Subtract: " + execute.apply(subtract, 3, 4));
        System.out.println("Multiply: " + execute.apply(multiply, 3, 4));
    }

    @FunctionalInterface
    interface Function3 <CALLBACK, ARG1, ARG2, RETURN> {
        public RETURN apply (CALLBACK callback, ARG1 a, ARG2 b);
    }
}
