package javastuff;
//https://www.baeldung.com/java-static-default-methods
public class One {


    public static void main(String[] args) {

        FooImplementation fi = new FooImplementation();
        System.out.println(fi.HelloWorld());
        System.out.println(Foo.CustomMessage("Hi"));
        fi.bar();
    }
}

interface Foo{
    // Default Method - Optional can be 0 or more
    public default String HelloWorld() {
        return "Hello World";
    }
    // Static Method - Optional can be 0 or more
    public static String CustomMessage(String msg) {
        return msg;
    }
    // Single Abstract Method
    public void bar();
}

interface Baz{
    // Default Method - Optional can be 0 or more
    public default String HelloWorld() {
        return "Hello World";
    }
    // Static Method - Optional can be 0 or more
    public static String CustomMessage(String msg) {
        return msg;
    }
    // Single Abstract Method
    public void bar();
}

class FooImplementation implements Foo/*, Baz*/ { // If we implement Baz as well which has same default method
                                         // we get into Diamond problem, and it won't compile unless we
                                        //  override the default method
    // Default Method - Optional to Override
//    @Override
//    public String HelloWorld() {
//        return "Hello Java 8";
//    }

    // Method Override
    @Override
    public void bar() {
        System.out.println("Hello World");
    }
}
