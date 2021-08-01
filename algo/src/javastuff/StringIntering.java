package javastuff;

public class StringIntering {
    public static void main(String[] args) {
        String alice = "al" + "ice";
        String bob = "alic" + "e";
        System.out.println(alice.equals(bob));  // true
        System.out.println(alice == bob);       // true

        String alice1 = new String("alice1");
        String bob1 = new String("alice1");
        System.out.println(alice1.equals(bob1));  // true
        System.out.println(alice1 == bob1);       // false

        String alice2 = new String("alice2");
        String bob2 = new String("alice2");
        alice2 = alice2.intern();
        bob2 = bob2.intern();
        System.out.println(alice2.equals(bob2));  // true
        System.out.println(alice2 == bob2);       // true
    }
}
