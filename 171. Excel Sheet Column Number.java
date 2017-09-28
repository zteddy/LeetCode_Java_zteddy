class Solution {
    public int titleToNumber(String s) {

        char[] c_s = s.toCharArray();
        int result = 0;

        for(int i = 0; i < c_s.length; i++){
            result *= 26;
            result += (int)(c_s[i] - 'A') + 1;
        }

        return result;

    }
}
