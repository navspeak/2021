package javastuff;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

// https://www.youtube.com/watch?v=a_BoqsnVR2U
// Spring uses it to store SpringSecurity Context per request
public class ThreadLocalExample {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Svc1()::process);
        t1.start();
        Thread.sleep(100);
        Thread t2 = new Thread(new Svc2()::process);
        t2.start();
        t1.join();
        t2.join();
    }
}



//https://stackoverflow.com/questions/1138769/why-is-the-clone-method-protected-in-java-lang-object
class UserContextHolder implements  Cloneable{
    public static ThreadLocal<User> holder = ThreadLocal.withInitial(()-> {
        User u = new User();
        System.out.println("initial val " + u);
        return u;
    });
    //public static ThreadLocal<User> holder = new ThreadLocal<>();

}
class Svc1 {
    public void process(){
        User value = UserContextHolder.holder.get();
        System.out.println("svc1 " + value);
        UserContextHolder.holder.set(value); // set only in this thread
        value = UserContextHolder.holder.get();
        System.out.println("svc1 " + value);
        UserContextHolder.holder.remove();
        System.out.println("svc1 " + UserContextHolder.holder.get() == null ? "null": UserContextHolder.holder.get());
    }
}

class Svc2 {
    public void process(){
       User value = UserContextHolder.holder.get();
       System.out.println("svc2 " + value);
    }
}

class User implements  Iterable<User>{

    @Override
    public Iterator<User> iterator() {
        return null;
    }
}