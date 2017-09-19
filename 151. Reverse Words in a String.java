public class Solution {
    public String reverseWords(String s) {
        // s = s.trim();
        // String[] temp = s.split(" ");
        // for(int i = 0; i < temp.length/2; i++){
        //     String t = temp[i];
        //     temp[i] = temp[temp.length-i-1];
        //     temp[temp.length-i-1] = t;
        // }

        // String result = "";

        // for(String i:temp){
        //     if(!i.equals(" ")) result += i.trim();
        // }

        // return result;

        String result = "";
        int end = 0;
        int start = 0;

        while(start < s.length() && end < s.length()){
            while(start < s.length() && end < s.length() && s.charAt(start) == ' '){
                end++;
                start++;
            }
            while(end < s.length() && s.charAt(end) != ' '){
                end++;
            }
            if(start < s.length() && end <= s.length())
                result = (end==s.length()?s.substring(start):s.substring(start,end)) + " " + result;
            start = end;
        }

        return result.trim();


    }
}




/*Using reverse
public class Solution {

  public String reverseWords(String s) {
    if (s == null) return null;

    char[] a = s.toCharArray();
    int n = a.length;

    // step 1. reverse the whole string
    reverse(a, 0, n - 1);
    // step 2. reverse each word
    reverseWords(a, n);
    // step 3. clean up spaces
    return cleanSpaces(a, n);
  }

  void reverseWords(char[] a, int n) {
    int i = 0, j = 0;

    while (i < n) {
      while (i < j || i < n && a[i] == ' ') i++; // skip spaces
      while (j < i || j < n && a[j] != ' ') j++; // skip non spaces
      reverse(a, i, j - 1);                      // reverse the word
    }
  }

  // trim leading, trailing and multiple spaces
  String cleanSpaces(char[] a, int n) {
    int i = 0, j = 0;

    while (j < n) {
      while (j < n && a[j] == ' ') j++;             // skip spaces
      while (j < n && a[j] != ' ') a[i++] = a[j++]; // keep non spaces
      while (j < n && a[j] == ' ') j++;             // skip spaces
      if (j < n) a[i++] = ' ';                      // keep only one space
    }

    return new String(a).substring(0, i);
  }

  // reverse a[] from a[i] to a[j]
  private void reverse(char[] a, int i, int j) {
    while (i < j) {
      char t = a[i];
      a[i++] = a[j];
      a[j--] = t;
    }
  }
}
*/





/*Using regex
String[] parts = s.trim().split("\\s+");
String out = "";
for (int i = parts.length - 1; i > 0; i--) {
    out += parts[i] + " ";
}
return out + parts[0];
I'm splitting on the regex for one-or-more whitespace, this takes care of multiple spaces/tabs/newlines/etc in the input. Since the input could have leading/trailing whitespace, which would result in empty matches, I first trim the input string.

Now there could be three possibilities:

The input is empty: "", parts will contain [""]. The for loop is skipped and "" + "" is returned.
The input contains only one part: "a", parts will contain ["a"]. The for loop is skipped and "" + "a" is returned.
The input contains multiple parts: "a b c", reverse the order of all but the first part: "c b " in the for loop and return "c b " + "a".
Obviously this is not the fastest or most memory efficient way to solve the problem, but optimizations should only be done when they are needed. Readable code is usually more important than efficient code.

How to make it efficient?

Use a StringBuilder to concatenate the string parts, instead of concatenating strings directly. This will (I assume) build something like a linked-list of string parts, and only allocate the new string when you need it, instead of on each concatenation.
Iterate over the string, instead of using trim/split. Store the index of the last character in the word, when you find the first character, copy the substring to the output string.
Instead of using substring, insert the word-characters directly in the StringBuilder. Assuming they're using a linked-list or tree, this could be a whole last faster.
*/



/*Using regex2
public String reverseWords(String s) {
    String[] words = s.trim().split(" +");
    Collections.reverse(Arrays.asList(words));
    return String.join(" ", words);
}
*/



/*Using reverse
class Solution {
    Blockquote

    In place word reverse ignoring new String object built. Idea is to reverse each word then reverse the whole string. Space is tackled by trimming and shifting chars to left i.e if i points to space and i-1 also point to space shift chars starting from i+1 left by one place. Decrement end pointer pointing to end of string.

    Example steps:

    s =" Hello World "

    Trim s = "Hello World"

    Reverse each word - s ="olleH dlroW"

    Reverse whole string s= "World Hello"

    @param s

    @return reversed string object


    Blockquote
    public static String reverseWords(String s) {
        if (s == null)
            return null;

        char[] str = s.toCharArray();
        int start = 0, end = str.length - 1;

        // Trim start of string
        while (start <= end && str[start] == ' ')
            start++;

        //Trim end of string
        while (end >= 0 && str[end] == ' ')
            end--;

        if (start > end)
            return new String("");

        int i = start;
        while (i <= end) {
            if (str[i] != ' ') {
                // case when i points to a start of word -  find the word reverse it
                int j = i + 1;
                while (j <= end && str[j] != ' ')
                    j++;
                reverse(str, i, j - 1);
                i = j;
            } else {
                if (str[i - 1] == ' ') {
                    //case when prev char is also space - shift char to left by 1 and decrease end pointer
                    int j = i;
                    while (j <= end - 1) {
                        str[j] = str[j + 1];
                        j++;
                    }
                    end--;
                } else
                    // case when there is just single space
                    i++;
            }
        }
        //Now that all words are reversed, time to reverse the entire string pointed by start and end - This step reverses the words in string
        reverse(str, start, end);
        // return new string object pointed by start with len = end -start + 1
        return new String(str, start, end - start + 1);
    }

    private static void reverse(char[] str, int begin, int end) {
        while (begin < end) {
            char temp = str[begin];
            str[begin] = str[end];
            str[end] = temp;
            begin++;
            end--;
        }
    }
}

*/
