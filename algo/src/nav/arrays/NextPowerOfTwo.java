package nav.arrays;

public class NextPowerOfTwo {
    public static void main(String[] args) {
        for (int i = 0; i < 25; i++) {
            int nextPowerOf2 = getNextPowerOf2(i);
            System.out.println(i + " => " + nextPowerOf2 + " => " + (2*nextPowerOf2 - 1));//size of segment tree
        }

    }

    static int getNextPowerOf2(int n){
        while((n & (n-1)) != 0) n++;
        return n;
    }
}
