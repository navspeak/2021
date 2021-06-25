package nav.recurison;

public class NoOfBST {
    public static int numberOfBinaryTreeTopologies(int n) {
        if (n<=1) return 1;
        int [] F = new int[n+1];
        F[0] = F[1] =1;
        int result = 0;
        for(int i=2; i <= n; i++){
            result = 0;
            for(int j=0; j< i; j++){
                result += F[j]*F[i-1-j];
            }
            F[i] = result;
        }
        return F[n];
    }
}

/*
1 1 2 5
F[0]F[2]+F[1]F[1]+F[2][0] = 2+1+2
F[2] = F[0]*F[1] + F[1]*F[0]
F[n] = Sum(F[i][n-i-1) i= 0 to n

1 => 1   A  =1
2 => 2    A    A = 2 F(0)(1)
        B        B
3 =>   A              A          =2 + 1.1 + 2 = 5
          (AB)    A       B

4 => A              B              B              D
       (CDE)     C     DE     CD        E     CDE

     F(0).F(3) +  F(1).F(2) + F(2).F(1) + F(3)F(0)
	= 1.5+1.2+2.1+5.1 = 3+2+2+3 = 10

*/


