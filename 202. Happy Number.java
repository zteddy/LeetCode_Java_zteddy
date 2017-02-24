public class Solution {
	public int sosod(int n){
		int digit = 0;
		int remain = n;
		int result = 0;

		while(remain != 0){
			digit = remain % 10;
			remain = remain / 10;
			result += digit * digit;
		}

		return result;
	}

    public boolean isHappy(int n) {

        Map<Integer, Integer> hm = new HashMap<Integer, Integer>();

        Integer s = sosod(n);
        while(!hm.containsKey(s) && s != 1){
        	hm.put(s,s);
        	s = sosod(s);
        }

        if(s == 1)
        	return true;
        else
        	return false;
    }
}




/*Using two pointers
int digitSquareSum(int n) {
    int sum = 0, tmp;
    while (n) {
        tmp = n % 10;
        sum += tmp * tmp;
        n /= 10;
    }
    return sum;
}

bool isHappy(int n) {
    int slow, fast;
    slow = fast = n;
    do {
        slow = digitSquareSum(slow);
        fast = digitSquareSum(fast);
        fast = digitSquareSum(fast);
    } while(slow != fast);
    if (slow == 1) return 1;
    else return 0;
}
*/



/*Genius solution using math
bool isHappy(int n) {
    while(n>6){
        int next = 0;
        while(n){next+=(n%10)*(n%10); n/=10;}
        n = next;
    }
    return n==1;
}
*/
