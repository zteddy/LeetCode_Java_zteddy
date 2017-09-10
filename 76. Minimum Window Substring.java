class Solution {
    public String minWindow(String s, String t) {
        Map<Character,Integer> dict = new HashMap<>();
        Map<Character,Integer> temp = new HashMap<>();
        //不能是Map<> dict = new HashMap<Character,Integer>();
        for(int i = 0; i < t.length(); i++){
            Integer value = dict.containsKey(t.charAt(i))? dict.get(t.charAt(i)):0;
            dict.put(t.charAt(i), value+1);
            // dict.put(t.charAt(i),1);
            // temp.put(t.charAt(i),1);
        }

        int minLength = Integer.MAX_VALUE;
        String result = "";

        int head = 0;
        int tail = 0;

        int recordForTail = 0;

        //collect first
        while(tail < s.length()){
            if(dict.containsKey(s.charAt(tail))){
                Integer value = temp.containsKey(s.charAt(tail))? temp.get(s.charAt(tail)):0;
                temp.put(s.charAt(tail), value+1);
                //正统方法
                //不知道为什么temp.get(s.charAt(tail))++不行
            }

            // if(temp.size() == dict.size()) break;
            if(temp.equals(dict)) break;
            tail++;
        }

        recordForTail = tail;


        if(temp.equals(dict) && tail-head+1 < minLength && tail-head+1 >= 0){
            result = tail == s.length()? s.substring(head):s.substring(head,tail+1);
            //不知道有没有更好的方法。。。
            minLength = tail-head+1;
        }



        while(head < s.length() && tail < s.length()){

            //delete one
            while(head < s.length()){
                if(tail-head+1 < minLength && tail-head+1 >= 0){
                    result = tail == s.length()? s.substring(head):s.substring(head,tail+1);
                    //不知道有没有更好的方法。。。
                    minLength = tail-head+1;
                }
                if(temp.containsKey(s.charAt(head))){
                    temp.put(s.charAt(head),temp.get(s.charAt(head))-1);
                    // if(temp.get(s.charAt(head)) == 0){
                    //     temp.remove(s.charAt(head));
                    // }
                }
                head++;
                recordForTail = tail;
                if(!temp.equals(dict)) break;
            }

            //add one
            tail++;
            while(tail < s.length() ){
                if(temp.containsKey(s.charAt(tail))){
                    temp.put(s.charAt(tail),temp.get(s.charAt(tail))+1);
                }
                // if(!temp.containsKey(s.charAt(tail)) && dict.containsKey(s.charAt(tail))){
                //     temp.put(s.charAt(tail),1);
                // }

                if(temp.equals(dict)){
                    recordForTail = tail;
                    break;
                }
                tail++;
            }

            // if(tail-head+1 < minLength && tail-head+1 >= 0){
            //     result = tail == s.length()? s.substring(head):s.substring(head,tail+1);
            //     //不知道有没有更好的方法。。。
            //     minLength = tail-head+1;
            // }
        }

        tail = recordForTail;


        while(head < s.length()){
            if(temp.equals(dict) && tail-head+1 < minLength && tail-head+1 >= 0){
                result = tail == s.length()? s.substring(head):s.substring(head,tail+1);
                //不知道有没有更好的方法。。。
                minLength = tail-head+1;
            }
            if(temp.containsKey(s.charAt(head))){
                temp.put(s.charAt(head),temp.get(s.charAt(head))-1);
                // if(temp.get(s.charAt(head)) == 0){
                //     temp.remove(s.charAt(head));
                // }
            }
            head++;
        }

        return result;

    }
}



//这题失败在 1.没有事先吧所有逻辑验证 2.中途发现题意理解错了之后没有思考原来的想法是否能继续用
//如果是只看字符存不存在不计数量的话这个方法是可以的
//主要是比较两个Hashmap的思路除了问题





//Use array and counter
I will first give the solution then show you the magic template.

The code of solving this problem is below. It might be the shortest among all solutions provided in Discuss.

1. Use two pointers: start and end to represent a window.
2. Move end to find a valid window.
3. When a valid window is found, move start to find a smaller window.
To check if a window is valid, we use a map to store (char, count) for chars in t. And use counter for the number of chars of t to be found in s. The key part is m[s[end]]--;. We decrease count for each char in s. If it does not exist in t, the count will be negative.

string minWindow(string s, string t) {
        vector<int> map(128,0);
        for(auto c: t) map[c]++;
        int counter=t.size(), begin=0, end=0, d=INT_MAX, head=0;
        while(end<s.size()){
            if(map[s[end++]]-->0) counter--; //in t
            while(counter==0){ //valid
                if(end-begin<d)  d=end-(head=begin);
                if(map[s[begin++]]++==0) counter++;  //make it invalid
            }
        }
        return d==INT_MAX? "":s.substr(head, d);
    }

Same code written in java

public String minWindow(String s, String t) {
    HashMap<Character,Integer> map = new HashMap();
    for(char c : s.toCharArray())
        map.put(c,0);
    for(char c : t.toCharArray())
    {
        if(map.containsKey(c))
            map.put(c,map.get(c)+1);
        else
            return "";
    }

    int start =0, end=0, minStart=0,minLen = Integer.MAX_VALUE, counter = t.length();
    while(end < s.length())
    {
        char c1 = s.charAt(end);
        if(map.get(c1) > 0)
            counter--;
        map.put(c1,map.get(c1)-1);

        end++;

        while(counter == 0)
        {
            if(minLen > end-start)
            {
                minLen = end-start;
                minStart = start;
            }

            char c2 = s.charAt(start);
            map.put(c2, map.get(c2)+1);

            if(map.get(c2) > 0)
                counter++;

            start++;
        }
    }
    return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart,minStart+minLen);
}


Here comes the template.

For most substring problem, we are given a string and need to find a substring of it which satisfy some restrictions. A general way is to use a hashmap assisted with two pointers. The template is given below.

int findSubstring(string s){
        vector<int> map(128,0);
        int counter; // check whether the substring is valid
        int begin=0, end=0; //two pointers, one point to tail and one  head
        int d; //the length of substring

        for() { /* initialize the hash map here */ }

        while(end<s.size()){

            if(map[s[end++]]-- ?){  /* modify counter here */ }

            while(/* counter condition */){

                 /* update d here if finding minimum*/

                //increase begin to make it invalid/valid again

                if(map[s[begin++]]++ ?){ /*modify counter here*/ }
            }

            /* update d here if finding maximum*/
        }
        return d;
  }
One thing needs to be mentioned is that when asked to find maximum substring, we should update maximum after the inner while loop to guarantee that the substring is valid. On the other hand, when asked to find minimum substring, we should update minimum inside the inner while loop.

The code of solving Longest Substring with At Most Two Distinct Characters is below:

int lengthOfLongestSubstringTwoDistinct(string s) {
        vector<int> map(128, 0);
        int counter=0, begin=0, end=0, d=0;
        while(end<s.size()){
            if(map[s[end++]]++==0) counter++;
            while(counter>2) if(map[s[begin++]]--==1) counter--;
            d=max(d, end-begin);
        }
        return d;
    }
The code of solving Longest Substring Without Repeating Characters is below:

Update 01.04.2016, thanks @weiyi3 for advise.

int lengthOfLongestSubstring(string s) {
        vector<int> map(128,0);
        int counter=0, begin=0, end=0, d=0;
        while(end<s.size()){
            if(map[s[end++]]++>0) counter++;
            while(counter>0) if(map[s[begin++]]-->1) counter--;
            d=max(d, end-begin); //while valid, update d
        }
        return d;
    }
I think this post deserves some upvotes! : )








//Three ways
// according to http://leetcode.com/2010/11/finding-minimum-window-in-s-which.html
// finds the first window that satisfies the constraint
// then continue maintaining the constraint throughout
// time complexity O(2N)
string minWindow(string S, string T) {
    int m = S.size(), n = T.size();
    if (n <= 0 || m < n)
        return "";

    int require[128] = {0}, found[128] = {0};
    for (int k = 0; k < n; ++k) require[T[k]]++;

    int count = 0;
    int minLen = INT_MAX, minIndex = 0;
    for (int s = 0, e = 0; e < m; ++e) {
        // skip characters not in T
        if (require[S[e]] == 0) continue;
        if (++found[S[e]] <= require[S[e]]) count++;

        // windows constrain is sastisfied
        if (count == n) {
            // advance begin index as far as possible
            // stop when advancing breaks window constraint
            while (require[S[s]] == 0 || found[S[s]] > require[S[s]]) {
                if (found[S[s]] > require[S[s]]) found[S[s]]--;
                ++s;
            }
            // update minmum window
            if (e - s + 1 < minLen) {
                minLen = e - s + 1;
                minIndex = s;
            }
        }
    }

    if (minLen == INT_MAX) return "";
    return S.substr(minIndex, minLen);
}

string minWindow(string S, string T) {
    int m = S.size(), n = T.size();
    if (n <= 0 || m < n)
        return "";

    int require[128] = {0}, chSet[128] = {0};
    for (int k = 0; k < n; ++k) {
        require[T[k]]++;
        chSet[T[k]] = 1;
    }

    int minLen = INT_MAX, minIndex = 0;
    int i = -1, j = 0;
    while (i < m && j < m) {
        if (n) {
            ++i;
            require[S[i]]--;
            if (chSet[S[i]] && require[S[i]] >= 0) n--;
        }
        else {
            if (minLen > i - j + 1) {
                minLen = i - j + 1;
                minIndex = j;
            }
            require[S[j]]++;
            if (chSet[S[j]] && require[S[j]] > 0) n++;
            ++j;
        }
    }

    if (minLen == INT_MAX) return "";
    return S.substr(minIndex, minLen);
}

// the most concise one
// maintain a window with two pointers (left side and right side)
string minWindow(string S, string T) {
    int m = S.size(), n = T.size();
    if (n <= 0 || m < n) return "";

    int require[128] = {0};
    for (int i = 0; i < n; ++i) require[T[i]]++;

    int count = 0;
    int minLen = INT_MAX, minIndex = 0;
    for (int s = 0, e = 0; e < m; ++e) {
        require[S[e]]--;
        if (require[S[e]] >= 0) count++;
        while (count == n) {
            if (e - s + 1 < minLen) {
                minLen = e - s + 1;
                minIndex = s;
            }
            require[S[s]]++;
            if (require[S[s]] > 0) count--;
            s++;
        }
    }

    if (minLen == INT_MAX) return "";
    return S.substr(minIndex, minLen);
}






//Using Hashmap
public String minWindow(String S, String T) {
    int[] result = new int[] {-1, S.length()};
    int counter = 0;
    Map<Character, Integer> expected = new HashMap<>();
    Map<Character, Integer> window = new HashMap<>();

    for (int i = 0; i < T.length(); i++) {
        if (!expected.containsKey(T.charAt(i))) expected.put(T.charAt(i), 0);
        expected.put(T.charAt(i), expected.get(T.charAt(i)) + 1);
    }
    for (int i = 0, j = 0; j < S.length(); j++) {
        char cur = S.charAt(j);
        if (expected.containsKey(cur)) {
            if (!window.containsKey(cur)) window.put(cur, 0);
            window.put(cur, window.get(cur) + 1);
            if (window.get(cur) <= expected.get(cur)) counter++;
            if (counter == T.length()) {
                char remove = S.charAt(i);
                while (!expected.containsKey(remove) || window.get(remove) > expected.get(remove)){
                    if (expected.containsKey(remove)) window.put(remove, window.get(remove) - 1);
                    remove = S.charAt(++i);;
                }
                if (j - i < result[1] - result[0]) result = new int[]{i, j};
            }
        }
    }
    return result[1] - result[0] < S.length() ? S.substring(result[0], result[1] + 1) : "";
}




