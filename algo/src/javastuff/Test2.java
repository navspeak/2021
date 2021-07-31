package java;

public class Test2 {

    static void changeName(String name) { name = "changedName";}

    public static void main(String[] args) {

        String name = "unitialized";
        changeName(name);
        System.out.println(name);//uninitialized

        String v1 = "Navneet";
        String v2 = new String("Navneet");
        System.out.println(v1 == v2);
        System.out.println(v1.equals(v2));

        Money income = new Money(55, "USD");
        Money expenses = new Money(55, "USD");
        boolean balanced = income.equals(expenses);
        System.out.println(balanced);
    }

    static class Money implements Comparable<Money> {
        int amount;
        String currencyCode;

        public Money(int amt, String code) {
            amount = amt;
            currencyCode = code;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Money))
                return false;
            Money other = (Money)o;
            boolean currencyCodeEquals = (this.currencyCode == null && other.currencyCode == null)
                    || (this.currencyCode != null && this.currencyCode.equals(other.currencyCode));
            return this.amount == other.amount && currencyCodeEquals;
        }

        @Override
        public int compareTo(Money o) {
            return Integer.compare(this.amount, o.amount);
        }
    }


}
