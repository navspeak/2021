package nav.arrays;

public class Maths {

    public static void main(String[] args) {
        // int max => -2*10^9 to 2*10^9
        int[] point1 = {10,5};
        int[] point2 = {-1, -6};
        int xDiff = point1[0] - point1[1];
        int yDiff = point1[1] - point1[1];

    }

    // application - use a fraction as Key in hashmap
    static int gcd (int x, int y){
        int a = (x>y)? x:y;// bigger say 10
        int b = (x>y)?y:x; // smaller say 5
        if (b==0) return a;
        return gcd(b,b%a); // 5,
    }
}
