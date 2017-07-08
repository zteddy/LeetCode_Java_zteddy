public class Solution {
    public double myPow(double x, int n) {
        return Math.pow(x,n);
    }
}



/*5 different choices when talk with interviewers
After reading some good sharing solutions, I'd like to show them together. You can see different ideas in the code.

1. nest myPow
double myPow(double x, int n) {
    if(n<0) return 1/x * myPow(1/x, -(n+1));
    if(n==0) return 1;
    if(n==2) return x*x;
    if(n%2==0) return myPow( myPow(x, n/2), 2);
    else return x*myPow( myPow(x, n/2), 2);
}

2. double myPow
double myPow(double x, int n) {
    if(n==0) return 1;
    double t = myPow(x,n/2);
    if(n%2) return n<0 ? 1/x*t*t : x*t*t;
    else return t*t;
}

3. double x
double myPow(double x, int n) {
    if(n==0) return 1;
    if(n<0){
        n = -n;
        x = 1/x;
    }
    return n%2==0 ? myPow(x*x, n/2) : x*myPow(x*x, n/2);
}

4. iterative one
public class Solution {
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (x == 1) return 1;
        if (x == -1) {
            if (n % 2 == 0) return 1;
            else return -1;
        }
        if (n == Integer.MIN_VALUE) return 0;
        if (n < 0) {
            n = -n;
            x = 1/x;
        }
        double ret = 1.0;
        while (n > 0) {
            if ((n & 1) != 0)
                ret *= x;
            x = x * x;
            n = n >> 1;
        }
        return ret;
    }
}

5.bit operation
class Solution {
public:
    double pow(double x, int n) {
        if(n<0){
            x = 1.0/x;
            n = -n;
        }
        int unsigned m = n;
        double tbl[32] = {0};
        double result = 1;
        tbl[0] = x;
        for(int i=1;i<32;i++){
            tbl[i] = tbl[i-1]*tbl[i-1];
        }
        for(int i=0;i<32;i++){
            if( m & (0x1<<i) )
            result *= tbl[i];
        }
        return result;
    }
};
In bit format and for a unsigned number, the number is represented as k0*2^0 + k1*2^1 + ... +k31*2^31. Therefore, once we know the pow(x,2^0), pow(x,2^1), ..., pow(x,2^31), we can get pow(x,n). And pow(x,2^m) can be constructed easily as pow(x,2^m) = pow(x,2^(m-1)*pow(x,2^(m-1).
*/
