public class Solution {
    public void reverseWords(char[] s) {
        reverse(s,0,s.length-1);
        for(int i = 0; i < s.length; i++){
            int j = i;
            while(j < s.length && s[j] != ' ') j++;
            reverse(s,i,j-1);
            i = j;
        }

    }

    public void reverse(char[] s, int start, int end){
        while(start < end){
            char temp = s[start];
            s[start] = s[end];
            s[end] = temp;
            start++;
            end--;
        }
    }
}
