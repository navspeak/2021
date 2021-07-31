package java.designPatterns;

import lombok.Getter;
import lombok.Setter;
// https://www.baeldung.com/java-state-design-pattern
// behavorial
public class InterfaceFSM {
    public static void main(String[] args) {
        Package pkg = new Package();
        //assertThat(pkg.getState(), instanceOf(OrderedState.class));
        pkg.nextState();
        //assertThat(pkg.getState(), instanceOf(DeliveredState.class));
        pkg.nextState();
        //assertThat(pkg.getState(), instanceOf(ReceivedState.class));

        Package pkg2 = new Package();
        pkg2.setState(new DeliveredState());
        pkg2.prevState();
        //assertThat(pkg.getState(), instanceOf(OrderedState.class));
    }
}

interface PackageState {
    void next(Package pkg);
    void prev(Package pkg);
    void printStatus();
}
class OrderedState implements PackageState /*,Cloneable*/{
    @Override
    public void next(Package pkg) {
        pkg.setState(new DeliveredState());
    }

    @Override
    public void prev(Package pkg) {
        System.out.println("The package is in its root state.");
    }

    @Override
    public void printStatus() {
        System.out.println("Package ordered, not delivered to the office yet.");
    }

//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
}

class DeliveredState implements PackageState {

    @Override
    public void next(Package pkg) {
        pkg.setState(new ReceivedState());
    }

    @Override
    public void prev(Package pkg) {
        pkg.setState(new OrderedState());
    }

    @Override
    public void printStatus() {
        System.out.println("Package delivered to post office, not received yet.");
    }
}

class ReceivedState implements PackageState {
    @Setter
    String receivedBy;

    @Override
    public void next(Package pkg) {
        System.out.println("This package is already received by a client.");
    }

    @Override
    public void prev(Package pkg) {
        pkg.setState(new DeliveredState());
    }

    @Override
    public void printStatus() {
        System.out.println("Package Received by " + receivedBy);
    }
}
class Package{
    @Setter @Getter
    private PackageState state = new OrderedState();
    public void prevState() {
        state.prev(this);
    }

    public void nextState() {
        state.next(this);
    }

    public void printStatus() {
        state.printStatus();
    }
}