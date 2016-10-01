public class Solution {
    public int strStr(String haystack, String needle) {

    	int front = 0;
    	int begin = 0;
		String temp;

    	if(needle.length() == 0) return 0;

    	String head = Character.toString(needle.charAt(0));

    	while(front <= haystack.length() - needle.length()){
    		begin = haystack.indexOf(head, front);
    		if(begin == -1) return -1;

            if(begin+needle.length()<haystack.length())
                temp = haystack.substring(begin, begin+needle.length());
            else
                temp = haystack.substring(begin);
            //这里substring()有些坑


    		if(needle.equals(temp)) return begin;

    		front = begin+1;
    	}

    	return -1;
        
    }
}

/*More concise
public class Solution {
    public int strStr(String haystack, String needle) {
        int l1 = haystack.length(), l2 = needle.length();
        if (l1 < l2) {
            return -1;
        } else if (l2 == 0) {
            return 0;
        }
        int threshold = l1 - l2;
        for (int i = 0; i <= threshold; ++i) {
            if (haystack.substring(i,i+l2).equals(needle)) {
                return i;
            }
        }
        return -1;
    }
}
*/

/*Elegant solution
public int strStr(String haystack, String needle) {
  for (int i = 0; ; i++) {
    for (int j = 0; ; j++) {
      if (j == needle.length()) return i;
      if (i + j == haystack.length()) return -1;
      if (needle.charAt(j) != haystack.charAt(i + j)) break;
    }
  }
}
*/

/*KMP algrithm
At first special thanks to @jianchao.li.fighter's introduction to KMP algorithm and 2 helpful links in his answer to this problem. Here is his answer: https://leetcode.com/discuss/38998/explained-4ms-easy-c-solution and below are the 2 links to refer to.

http://jakeboxer.com/blog/2009/12/13/the-knuth-morris-pratt-algorithm-in-my-own-words/
http://www.geeksforgeeks.org/searching-for-patterns-set-2-kmp-algorithm/
For those who don't know what KMP is please read the content in the 2 links first. And here I just add some detailed supplementary comments on how to build up the lps[] based on the original code and comments from the 2nd link. Hope this will help those who are still confused how the lps[] is built (especially for the tricky part) after read the content in the 2 links above.

void computeLPSArray(char *pat, int M, int *lps)
{
  int len = 0;  // lenght of the previous longest prefix suffix
  int i;

  lps[0] = 0; // lps[0] is always 0
  i = 1;

  // the loop calculates lps[i] for i = 1 to M-1
  while (i < M)
  {
     //example "abababca" and i==5, len==3. The longest prefix suffix is "aba", when pat[i]==pat[len],
     //we get new prefix "abab" and new suffix "abab", so increase length of  current lps by 1 and go to next iteration. 
     if (pat[i] == pat[len])
     {
       len++;
       lps[i] = len;
       i++;
     }
     else // (pat[i] != pat[len])
     {
       if (len != 0)
       {
         len = lps[len-1];
         //This is tricky. Consider the example "ababe......ababc", i is index of 'c', len==4. The longest prefix suffix is "abab",
         //when pat[i]!=pat[len], we get new prefix "ababe" and suffix "ababc", which are not equal. 
         //This means we can't increment length of lps based on current lps "abab" with len==4. We may want to increment it based on
         //the longest prefix suffix with length < len==4, which by definition is lps of "abab". So we set len to lps[len-1],
         //which is 2, now the lps is "ab". Then check pat[i]==pat[len] again due to the while loop, which is also the reason
         //why we do not increment i here. The iteration of i terminate until len==0 (didn't find lps ends with pat[i]) or found
         //a lps ends with pat[i].
       }
       else // if (len == 0)
       { // there isn't any lps ends with pat[i], so set lps[i] = 0 and go to next iteration.
         lps[i] = 0;
         i++;
       }
     }
  }
}  
*/

/*
public class Solution {
    public String strStr(String haystack, String needle) 
    {
        if(haystack== null) return null;
        if(needle==null || needle.length()==0) return haystack;
        if(needle.length()>haystack.length()) return null;

        int pat_length = needle.length();
        int right[] = new int[256];

        for(int i=0;i<256;i++)
          right[i] =-1;
        for(int i=0;i<pat_length;i++)
          right[needle.charAt(i)] =i;

        int rtn = search(right,haystack,needle);
        if(rtn == haystack.length()) return null;
        else 
            return haystack.substring(rtn);
    }
    public int search(int[] right, String haystack,String needle)
    {
        int M = haystack.length();
        int N = needle.length();
        int i,j;
        int skip =0;
        for(i=0; i<=M-N; i+=skip)
        {
            skip =0;
            for(j=N-1;j>=0;j--)
            {
                if(needle.charAt(j)!=haystack.charAt(i+j))
                {
                    skip = j-right[haystack.charAt(j+i)];
                    if(skip<=0) skip=1;
                    break;
                }
            }
            if(skip ==0) return i;
        }
        return M;
    }
}

I tried DFA version of KMP, it turned out that it exceeds the time limit. 
I went back to the Algorithm book and checked time complexity of KMP(DFA version). 
Surprisingly, the typical runtime of KMP is same as brute force, which is 1.1N. 
It only improves the worst case runtime from NM to 2N. This is why leetcode accepts the brute force.
The Boyer-Moore algorithm only needs O(N/M) for average runtime and O(NM) for worst case.
Hope this helps. : )

*/