class Solution {
    public String units(int num){
        String[] result = new String[]{"", " One"," Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine"};
        return result[num];
    }

    public String tenPlus(int num){
        String[] result = new String[]{"Ten","Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        return result[num-10];
    }

    public String tens(int num){
        String[] result = new String[]{"Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        return result[num-2];
    }

    public String twoDigits(int num){
        if(num >= 100 || num <= 0) return "";

        String result = "";

        if(num < 10) result = units(num);
        else if(num < 20) result = tenPlus(num);
        // else if(num < 100) return tens(num/10) + " " + units(num%10);
        else if(num < 100) result = tens(num/10) + units(num%10);

        return result.trim();
    }

    public String hundreds(int num){

        String result = "";

        if(num < 100) result = twoDigits(num);
        result = units(num/100) + " Hundred " + twoDigits(num%100);

        return result.trim();
    }

    // public String threeDigits(int num){
    //     return units(num/1000) + "Thousand" + hundreds(num%1000);
    // }

    public String helper(int num){
        String result =  "";

        if(num < 100) result = twoDigits(num);
        else if(num < 1000) result = hundreds(num);
        else if(num < 1000000) result = helper(num/1000) + " Thousand " + helper(num%1000);
        else if(num < 1000000000) result = helper(num/1000000) + " Million " + helper(num%1000000);
        else result = helper(num/1000000000) + " Billion " + helper(num%1000000000);

        return result.trim();
    }

    public String numberToWords(int num) {

        if(num == 0) return "Zero";
        else return helper(num);

    }
}






/*More concise
private final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
private final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
private final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};

public String numberToWords(int num) {
    if (num == 0) return "Zero";

    int i = 0;
    String words = "";

    while (num > 0) {
        if (num % 1000 != 0)
            words = helper(num % 1000) +THOUSANDS[i] + " " + words;
        num /= 1000;
        i++;
    }

    return words.trim();
}

private String helper(int num) {
    if (num == 0)
        return "";
    else if (num < 20)
        return LESS_THAN_20[num] + " ";
    else if (num < 100)
        return TENS[num / 10] + " " + helper(num % 10);
    else
        return LESS_THAN_20[num / 100] + " Hundred " + helper(num % 100);
}
*/
