public class Solution {
    public int[] plusOne(int[] digits) {

		int carry_over = 0;
		//plus one
		for(int i = digits.length-1; i>=0; i--){
			if(i == digits.length-1) digits[i]++;
			else{
				digits[i] += carry_over;
			}

			if(digits[i] == 10){
				carry_over = 1;
				digits[i] = 0;
			}
			else{
				carry_over = 0;
			}
		}

		/*
		int num = 0;
		int k = 0;
		//cal the number
		for(int i = digits.length; i>=0; i--){
			num += digits[i]*(10^k);
			k++;
		}
		if(carry_over == 1) num += 10^(k+1);

		return num;
		*/

		int[] digits_plus_one = new int[digits.length+1];
		//carry over
		if(carry_over == 1){
			digits_plus_one[0] = 1;
			for(int i = 0; i < digits.length; i++){
				digits_plus_one[i+1] = digits[i];
			}
			return digits_plus_one;
		}
		else return digits;

    }
}

/*More concise
public int[] plusOne(int[] digits) {

    int n = digits.length;
    for(int i=n-1; i>=0; i--) {
        if(digits[i] < 9) {
            digits[i]++;
            return digits;
        }

        digits[i] = 0;
    }

    int[] newNumber = new int [n+1];
    newNumber[0] = 1;

    return newNumber;
}
*/
