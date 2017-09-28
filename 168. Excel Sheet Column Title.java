class Solution {
    public String convertToTitle(int n) {

        String result = "";

        n--;

        while(n >= 0){
            int a = n % 26;

            char c = (char)(a + 'A');
            result = c + result;
            if(n == 0) break;
            n = n/26;
            n--;
        }

        return result;

    }
}



/*More concise
public class Solution {
    public String convertToTitle(int n) {
        StringBuilder result = new StringBuilder();

        while(n>0){
            n--;
            result.insert(0, (char)('A' + n % 26));
            n /= 26;
        }

        return result.toString();
    }
}
*/
