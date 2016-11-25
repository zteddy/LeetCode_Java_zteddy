public class Solution {
    public int reverse(int x) {
    	long result = 0;
    	int digit;
    	int flag = 0;  //尽量inti

    	if(x)

    	if(x < 0){
    		flag = -1;
    		x = -x;
    	}

    	//while(x % 10 == 0) x /= 10;

    	while(x != 0){
    		digit = x % 10;
    		result += digit;
    		x = x/10;
    		result *= 10;
    	}

    	result /= 10;

    	if(flag == -1) result = -result;

    	return (int)result;
    }
}

//需要check overflow啊啊啊

//Smart check
If overflow exists, the new result will not equal previous one.
No flags needed. No hard code like 0xf7777777 needed.
Sorry for my bad english.
public int reverse(int x)
{
    int result = 0;

    while (x != 0)
    {
        int tail = x % 10;
        int newResult = result * 10 + tail;
        if ((newResult - tail) / 10 != result)
        { return 0; }
        result = newResult;
        x = x / 10;
    }

    return result;
}

//Using long
public int reverse(int x) {
    long rev= 0;
    while( x != 0){
        rev= rev*10 + x % 10;
        x= x/10;
        if( rev > Integer.MAX_VALUE || rev < Integer.MIN_VALUE)
            return 0;
    }
    return (int) rev;
}

//Hard code check
n the solution, the ret value is compared with 214748364, why?

The Integer.MAX_VALUE should be 2147483647 , isnt it?

The solution code is as below:

public int reverse(int x) {
   int ret = 0;
   while (x != 0) {
      // handle overflow/underflow
      if (Math.abs(ret) > 214748364) {
         return 0;
      }
      ret = ret * 10 + x % 10;
      x /= 10;
   }
   return ret;
}

Its because it is compared before the multiplication by 10. Any number above 214748364 will yield minimum 2147483650 in value (overflow occurs).

We dont check Math.abs(ret)==214748364, because the only acceptable input that will cause this is if the first digit of x (last digit of ret) is 1, and that will not cause overflow. (2147483641 <2147483647)
