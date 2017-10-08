public class Solution {
    public boolean canPermutePalindrome(String s) {

    	Map<Character, Character> hm = new HashMap<>();

    	//if(s.length() == 0) return false;

    	for(int i = 0; i < s.length(); i++){
    		if(hm.containsKey(s.charAt(i))) hm.remove(s.charAt(i));
    		else hm.put(s.charAt(i), s.charAt(i));
    	}

    	if(hm.size() > 1) return false;

    	return true;

    }
}

//TODO None



/*Summary
Approach #1 Brute Force [Accepted]

If a string with an even length is a palindrome, every character in the string must always occur an even number of times. If the string with an odd length is a palindrome, every character except one of the characters must always occur an even number of times. Thus, in case of a palindrome, the number of characters with odd number of occurences can't exceed 1(1 in case of odd length and 0 in case of even length).

Based on the above observation, we can find the solution for the given problem. The given string could contain atmost all the ASCII characters from 0 to 127. Thus, we iterate over all the characters from 0 to 127. For every character chosen, we again iterate over the given string ss and find the number of occurences, chch, of the current character in ss. We also keep a track of the number of characters in the given string ss with odd number of occurences in a variable countcount.

If, for any character currently considered, its corresponding count, chch, happens to be odd, we increment the value of countcount, to reflect the same. In case of even value of chch for any character, the countcount remains unchanged.

If, for any character, the countcount becomes greater than 1, it indicates that the given string ss can't lead to the formation of a palindromic permutation based on the reasoning discussed above. But, if the value of countcount remains lesser than 2 even when all the possible characters have been considered, it indicates that a palindromic permutation can be formed from the given string ss.

Java

public class Solution {
    public boolean canPermutePalindrome(String s) {
        int count = 0;
        for (char i = 0; i < 128 && count <= 1; i++) {
            int ct = 0;
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == i)
                    ct++;
            }
            count += ct % 2;
        }
        return count <= 1;
    }
}
Complexity Analysis

Time complexity : O(128*n)O(128∗n). We iterate constant number of times(128) over the string ss of length nn giving a time complexity of 128n128n.

Space complexity : O(1)O(1). Constant extra space is used.

Approach #2 Using HashMap [Accepted]

Algorithm

From the discussion above, we know that to solve the given problem, we need to count the number of characters with odd number of occurences in the given string ss. To do so, we can also make use of a hashmap, mapmap. This mapmap takes the form (character_i, number of occurences of character_i)(character
​i
​​ ,numberofoccurencesofcharacter
​i
​​ ).

We traverse over the given string ss. For every new character found in ss, we create a new entry in the mapmap for this character with the number of occurences as 1. Whenever we find the same character again, we update the number of occurences appropriately.

At the end, we traverse over the mapmap created and find the number of characters with odd number of occurences. If this countcount happens to exceed 1 at any step, we conclude that a palindromic permutation isn't possible for the string ss. But, if we can reach the end of the string with countcount lesser than 2, we conclude that a palindromic permutation is possible for ss.

The following animation illustrates the process.

1 / 13
Java

public class Solution {
 public boolean canPermutePalindrome(String s) {
     HashMap < Character, Integer > map = new HashMap < > ();
     for (int i = 0; i < s.length(); i++) {
         map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
     }
     int count = 0;
     for (char key: map.keySet()) {
         count += map.get(key) % 2;
     }
     return count <= 1;
 }
}
Complexity Analysis

Time complexity : O(n)O(n). We traverse over the given string ss with nn characters once. We also traverse over the mapmap which can grow upto a size of nn in case all characters in ss are distinct.

Space complexity : O(n)O(n). The hashmap can grow upto a size of nn, in case all the characters in ss are distinct.

Approach #3 Using Array [Accepted]

Algorithm

Instead of making use of the inbuilt Hashmap, we can make use of an array as a hashmap. For this, we make use of an array mapmap with length 128. Each index of this mapmap corresponds to one of the 128 ASCII characters possible.

We traverse over the string ss and put in the number of occurences of each character in this mapmap appropriately as done in the last case. Later on, we find the number of characters with odd number of occurences to determine if a palindromic permutation is possible for the string ss or not as done in previous approaches.

Java

public class Solution {
    public boolean canPermutePalindrome(String s) {
        int[] map = new int[128];
        for (int i = 0; i < s.length(); i++) {
            map[s.charAt(i)]++;
        }
        int count = 0;
        for (int key = 0; key < map.length && count <= 1; key++) {
            count += map[key] % 2;
        }
        return count <= 1;
    }
}
Complexity Analysis

Time complexity : O(n)O(n). We traverse once over the string ss of length nn. Then, we traverse over the mapmap of length 128(constant).

Space complexity : O(1)O(1). Constant extra space is used for mapmap of size 128.

Approach #4 Single Pass [Accepted]:

Algorithm

Instead of first traversing over the string ss for finding the number of occurences of each element and then determining the countcount of characters with odd number of occurences in ss, we can determine the value of countcount on the fly while traversing over ss.

For this, we traverse over ss and update the number of occurences of the character just encountered in the mapmap. But, whevenever we update any entry in mapmap, we also check if its value becomes even or odd. We start of with a countcount value of 0. If the value of the entry just updated in mapmap happens to be odd, we increment the value of countcount to indicate that one more character with odd number of occurences has been found. But, if this entry happens to be even, we decrement the value of countcount to indicate that the number of characters with odd number of occurences has reduced by one.

But, in this case, we need to traverse till the end of the string to determine the final result, unlike the last approaches, where we could stop the traversal over mapmap as soon as the countcount exceeded 1. This is because, even if the number of elements with odd number of occurences may seem very large at the current moment, but their occurences could turn out to be even when we traverse further in the string ss.

At the end, we again check if the value of countcount is lesser than 2 to conclude that a palindromic permutation is possible for the string ss.

Java

public class Solution {
    public boolean canPermutePalindrome(String s) {
        int[] map = new int[128];
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            map[s.charAt(i)]++;
            if (map[s.charAt(i)] % 2 == 0)
                count--;
            else
                count++;
        }
        return count <= 1;
    }
}
Complexity Analysis

Time complexity : O(n)O(n). We traverse over the string ss of length nn once only.

Space complexity : O(128)O(128). A mapmap of constant size(128) is used.

Approach #5 Using Set [Accepted]:

Algorithm

Another modification of the last approach could be by making use of a setset for keeping track of the number of elements with odd number of occurences in ss. For doing this, we traverse over the characters of the string ss. Whenver the number of occurences of a character becomes odd, we put its entry in the setset. Later on, if we find the same element again, lead to its number of occurences as even, we remove its entry from the setset. Thus, if the element occurs again(indicating an odd number of occurences), its entry won't exist in the setset.

Based on this idea, when we find a character in the string ss that isn't present in the setset(indicating an odd number of occurences currently for this character), we put its corresponding entry in the setset. If we find a character that is already present in the setset(indicating an even number of occurences currently for this character), we remove its corresponding entry from the setset.

At the end, the size of setset indicates the number of elements with odd number of occurences in ss. If it is lesser than 2, a palindromic permutation of the string ss is possible, otherwise not.

Below code is inspired by @StefanPochmann

Java

public class Solution {
    public boolean canPermutePalindrome(String s) {
        Set < Character > set = new HashSet < > ();
        for (int i = 0; i < s.length(); i++) {
            if (!set.add(s.charAt(i)))
                set.remove(s.charAt(i));
        }
        return set.size() <= 1;
    }
}
Complexity Analysis

Time complexity : O(n)O(n). We traverse over the string ss of length nn once only.

Space complexity : O(n)O(n). The setset can grow upto a maximum size of nn in case of all distinct elements.
*/
