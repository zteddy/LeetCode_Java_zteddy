class Solution {
    public String nextClosestTime(String time) {
        List<Integer> element = new ArrayList<Integer>();
        for(int i = 0; i < 5; i++){
            if(i == 2) continue;
            int number = time.charAt(i) - '0';
            if(!element.contains(number)) element.add(number);
        }
        Collections.sort(element);

        // String timeStr = time.substring(0,2)+time.substring(3);
        // int timeInt = Integer.parseInt(timeStr);

        int now;
        int newNumber;

        now = time.charAt(4) - '0';
        for(int j:element){
            if(j > now){
                newNumber = Integer.parseInt(time.substring(3)) - now + j;
                if(newNumber < 60) return time.substring(0,2)+":"+(newNumber<10?"0"+newNumber:newNumber);
                else break;
            }
        }

        now = time.charAt(3) - '0';
        for(int j:element){
            if(j > now){
                newNumber = j*10+element.get(0);
                if(newNumber < 60) return time.substring(0,2)+":"+(newNumber<10?"0"+newNumber:newNumber);
                else break;
            }
        }

        String minutes = (element.get(0)*11<10?"0"+element.get(0)*11:element.get(0)*11)+"";

        now = time.charAt(1) - '0';
        for(int j:element){
            if(j > now){
                newNumber = Integer.parseInt(time.substring(0,2)) - now + j;
                if(newNumber < 24) return (newNumber<10?"0"+newNumber:newNumber)+":"+minutes;
                else break;
            }
        }

        now = time.charAt(0) - '0';
        for(int j:element){
            if(j > now){
                newNumber = j*10+element.get(0);
                if(newNumber < 24) return (newNumber<10?"0"+newNumber:newNumber)+":"+minutes;
                else break;
            }
        }

        return minutes+":"+minutes;
    }
}



/*Super fast
class Solution {
    public String nextClosestTime(String time) {
        char[] digit = new char[]{time.charAt(0), time.charAt(1), time.charAt(3), time.charAt(4)};
        Arrays.sort(digit);
        char[] result = time.toCharArray();
        for(int pos = 4; pos >= 0; --pos) {
            if(pos == 2)
                continue;
            int idx = 0;
            while(idx < 4 && digit[idx] <= result[pos])
                ++idx;
            if(idx < 4)
                if((pos == 0 && digit[idx] <= '2') ||
                   (pos == 1 && (result[0] <= '1' || digit[idx] <= '3')) ||
                   (pos == 3 && digit[idx] <= '5') ||
                   pos == 4) {
                    result[pos] = digit[idx];
                    break;
                }
            result[pos] = digit[0];
        }
        return new String(result);
    }
}
*/




/*Summary
Approach #1: Simulation [Accepted]

Intuition and Algorithm

Simulate the clock going forward by one minute. Each time it moves forward, if all the digits are allowed, then return the current time.

The natural way to represent the time is as an integer t in the range 0 <= t < 24 * 60. Then the hours are t / 60, the minutes are t % 60, and each digit of the hours and minutes can be found by hours / 10, hours % 10 etc.

Python

class Solution(object):
    def nextClosestTime(self, time):
        cur = 60 * int(time[:2]) + int(time[3:])
        allowed = {int(x) for x in time if x != ':'}
        while True:
            cur = (cur + 1) % (24 * 60)
            if all(digit in allowed
                    for block in divmod(cur, 60)
                    for digit in divmod(block, 10)):
                return "{:02d}:{:02d}".format(*divmod(cur, 60))
Java

class Solution {
    public String nextClosestTime(String time) {
        int cur = 60 * Integer.parseInt(time.substring(0, 2));
        cur += Integer.parseInt(time.substring(3));
        Set<Integer> allowed = new HashSet();
        for (char c: time.toCharArray()) if (c != ':') {
            allowed.add(c - '0');
        }

        while (true) {
            cur = (cur + 1) % (24 * 60);
            int[] digits = new int[]{cur / 60 / 10, cur / 60 % 10, cur % 60 / 10, cur % 60 % 10};
            search : {
                for (int d: digits) if (!allowed.contains(d)) break search;
                return String.format("%02d:%02d", cur / 60, cur % 60);
            }
        }
    }
}
Complexity Analysis

Time Complexity: O(1)O(1). We try up to 24 * 6024∗60 possible times until we find the correct time.

Space Complexity: O(1)O(1).

Approach #2: Build From Allowed Digits [Accepted]

Intuition and Algorithm

We have up to 4 different allowed digits, which naively gives us 4 * 4 * 4 * 4 possible times. For each possible time, let's check that it can be displayed on a clock: ie., hours < 24 and mins < 60. The best possible time != start is the one with the smallest cand_elapsed = (time - start) % (24 * 60), as this represents the time that has elapsed since start, and where the modulo operation is taken to be always non-negative.

For example, if we have start = 720 (ie. noon), then times like 12:05 = 725 means that (725 - 720) % (24 * 60) = 5 seconds have elapsed; while times like 00:10 = 10 means that (10 - 720) % (24 * 60) = -710 % (24 * 60) = 730 seconds have elapsed.

Also, we should make sure to handle cand_elapsed carefully. When our current candidate time cur is equal to the given starting time, then cand_elapsed will be 0 and we should handle this case appropriately.

Python

class Solution(object):
    def nextClosestTime(self, time):
        ans = start = 60 * int(time[:2]) + int(time[3:])
        elapsed = 24 * 60
        allowed = {int(x) for x in time if x != ':'}
        for h1, h2, m1, m2 in itertools.product(allowed, repeat = 4):
            hours, mins = 10 * h1 + h2, 10 * m1 + m2
            if hours < 24 and mins < 60:
                cur = hours * 60 + mins
                cand_elapsed = (cur - start) % (24 * 60)
                if 0 < cand_elapsed < elapsed:
                    ans = cur
                    elapsed = cand_elapsed

        return "{:02d}:{:02d}".format(*divmod(ans, 60))
Java

class Solution {
    public String nextClosestTime(String time) {
        int start = 60 * Integer.parseInt(time.substring(0, 2));
        start += Integer.parseInt(time.substring(3));
        int ans = start;
        int elapsed = 24 * 60;
        Set<Integer> allowed = new HashSet();
        for (char c: time.toCharArray()) if (c != ':') {
            allowed.add(c - '0');
        }

        for (int h1: allowed) for (int h2: allowed) if (h1 * 10 + h2 < 24) {
            for (int m1: allowed) for (int m2: allowed) if (m1 * 10 + m2 < 60) {
                int cur = 60 * (h1 * 10 + h2) + (m1 * 10 + m2);
                int candElapsed = Math.floorMod(cur - start, 24 * 60);
                if (0 < candElapsed && candElapsed < elapsed) {
                    ans = cur;
                    elapsed = candElapsed;
                }
            }
        }

        return String.format("%02d:%02d", ans / 60, ans % 60);
    }
}
Complexity Analysis

Time Complexity: O(1)O(1). We all 4^44
​4
​​  possible times and take the best one.

Space Complexity: O(1)O(1).
*/





/*Using backtracking
Since there are at max 4 * 4 * 4 * 4 = 256 possible times, just try them all...

class Solution {
    int diff = Integer.MAX_VALUE;
    String result = "";

    public String nextClosestTime(String time) {
        Set<Integer> set = new HashSet<>();
        set.add(Integer.parseInt(time.substring(0, 1)));
        set.add(Integer.parseInt(time.substring(1, 2)));
        set.add(Integer.parseInt(time.substring(3, 4)));
        set.add(Integer.parseInt(time.substring(4, 5)));

        if (set.size() == 1) return time;

        List<Integer> digits = new ArrayList<>(set);
        int minute = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3, 5));

        dfs(digits, "", 0, minute);

        return result;
    }

    private void dfs(List<Integer> digits, String cur, int pos, int target) {
        if (pos == 4) {
            int m = Integer.parseInt(cur.substring(0, 2)) * 60 + Integer.parseInt(cur.substring(2, 4));
            if (m == target) return;
            int d = m - target > 0 ? m - target : 1440 + m - target;
            if (d < diff) {
                diff = d;
                result = cur.substring(0, 2) + ":" + cur.substring(2, 4);
            }
            return;
        }

        for (int i = 0; i < digits.size(); i++) {
            if (pos == 0 && digits.get(i) > 2) continue;
            if (pos == 1 && Integer.parseInt(cur) * 10 + digits.get(i) > 23) continue;
            if (pos == 2 && digits.get(i) > 5) continue;
            if (pos == 3 && Integer.parseInt(cur.substring(2)) * 10 + digits.get(i) > 59) continue;
            dfs(digits, cur + digits.get(i), pos + 1, target);
        }
    }
}
*/




/*Using Iterative
Similar idea. Use nested loop.

public String nextClosestTime(String time) {
    char[] digits = new char[4];
    digits[0] = time.charAt(0);
    digits[1] = time.charAt(1);
    digits[2] = time.charAt(3);
    digits[3] = time.charAt(4);

    Set<String> timeSet = new HashSet<>();
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
                for (int l = 0; l < 4; l++) {
                    String candidate = ""+digits[i]+""+digits[j]+":"+digits[k]+""+digits[l];
                    if (isValidTime(candidate)) timeSet.add(candidate);
                }
            }
        }
    }

    List<String> timeList = new ArrayList<>();
    timeList.addAll(timeSet);
    Collections.sort(timeList);
    int index = timeList.indexOf(time);
    return index==timeList.size()-1 ? timeList.get(0) : timeList.get(index+1);

}

public boolean isValidTime(String time) {
    int hour = Integer.parseInt(time.substring(0,2));
    int min = Integer.parseInt(time.substring(3,5));
    return hour >= 0 && hour <= 23 && min >= 0 && min <= 59;
}
*/






/*Three ways
Solution 1:
we can try to increase the minute and the hour one by one. If all these four digits of the next time is in hashset, we find it and output! because these four digits are all reused.

    public String nextClosestTime(String time) {
        String[] val = time.split(":");
        Set<Integer> set = new HashSet<>();
        int hour = add(set, val[0]);
        int minu = add(set, val[1]);

        int[] times = new int[] {hour, minu};
        nxt(times);

        while (!contains(times[0], times[1], set)) {
            nxt(times);
        }
        return valid(times[0]) + ":" + valid(times[1]);
    }

    public void nxt(int[] time) {
        int hour = time[0];
        int minu = time[1];
        minu ++;
        if (minu == 60) {
            hour ++;
            minu = 0;
            if (hour == 24) hour = 0;
        }
        time[0] = hour;
        time[1] = minu;
    }

    public int add(Set<Integer> set, String timeStr) {
        int time = Integer.parseInt(timeStr);
        set.add(time / 10);
        set.add(time % 10);
        return time;
    }

    public String valid(int time) {
        if (time >= 0 && time <= 9) return "0" + time;
        else return time + "";
    }

    public boolean contains(int hour, int minu, Set<Integer> set) {
        return set.contains(hour / 10) && set.contains(hour % 10) && set.contains(minu / 10) && set.contains(minu % 10);
    }
but in this way, the cost will be 24* 60 = 1440.

Solution 2:
Because these four digits can be reused,so the next time can be constructed by these digits, so we try to use dfs to search all the next time.

it will search 4 * 4 * 4 * 4 = 256 times.

    int diff = 0x3f3f3f3f;
    String result = "";
    int h;
    int m;
    public String nextClosestTime(String time) {
        int[] digit = new int[4];
        int tot = 0;
        String[] val = time.split(":");
        int hour = Integer.parseInt(val[0]);
        int minu = Integer.parseInt(val[1]);
        digit[tot++] = hour / 10;
        digit[tot++] = hour % 10;
        digit[tot++] = minu / 10;
        digit[tot++] = minu % 10;

        h = hour;
        m = minu;

        dfs(digit, 0, new int[4]);

        return result;
    }

    void dfs(int[] digit, int i, int[] ans) {
        if (i == 4) {
            int hour = 10 * ans[0] + ans[1];
            int minu = 10 * ans[2] + ans[3];
            if (hour >= 0 && hour <= 23 && minu >= 0 && minu <= 59) {
                int df = diff(hour, minu);
                if (df < diff) {
                    diff = df;
                    result = valid(hour) + ":" + valid(minu);
                }
            }
        }
        else {
            for (int j = 0; j < 4; ++j) {
                ans[i] = digit[j];
                dfs(digit, i + 1, ans);
            }
        }
    }

    int diff(int hour, int minu) {
        int c2o = 60 * 60 - h * 60 - m;
        int n2o = 60 * 60 - hour * 60 - minu;
        return n2o < c2o ? c2o - n2o : c2o - n2o + 3600;
    }

    public String valid(int time) {
        if (time >= 0 && time <= 9) return "0" + time;
        else return time + "";
    }
Solution 3:
Of course, we can try to prune, if hour and minute is not valid, just stop search.

for the test case "23:59", it will search 24 times, but solution 2 searches 256 times.

    int diff = 0x3f3f3f3f;
    String result = "";
    int h;
    int m;
    public String nextClosestTime(String time) {
        int[] digit = new int[4];
        int tot = 0;
        String[] val = time.split(":");
        int hour = Integer.parseInt(val[0]);
        int minu = Integer.parseInt(val[1]);
        digit[tot++] = hour / 10;
        digit[tot++] = hour % 10;
        digit[tot++] = minu / 10;
        digit[tot++] = minu % 10;

        h = hour;
        m = minu;

        dfs(digit, 0, new int[4]);

        return result;
    }

    void dfs(int[] digit, int i, int[] ans) {
        if (i == 4) {
            int hour = 10 * ans[0] + ans[1];
            int minu = 10 * ans[2] + ans[3];
            int df = diff(hour, minu);
            if (df < diff) {
                diff = df;
                result = valid(hour) + ":" + valid(minu);
            }
        }
        else {
            for (int j = 0; j < 4; ++j) {
                ans[i] = digit[j];
                if (i == 1) {
                    int hour = 10 * ans[0] + ans[1];
                    if (hour >= 0 && hour <= 23) dfs(digit, i + 1, ans);
                }
                else if (i == 3) {
                    int minu = 10 * ans[2] + ans[3];
                    if (minu >= 0 && minu <= 59) dfs(digit, i + 1, ans);
                }
                else {
                    dfs(digit, i + 1, ans);
                }
            }
        }
    }

    int diff(int hour, int minu) {
        int c2o = 60 * 60 - h * 60 - m;
        int n2o = 60 * 60 - hour * 60 - minu;
        return n2o < c2o ? c2o - n2o : c2o - n2o + 3600;
    }

    public String valid(int time) {
        if (time >= 0 && time <= 9) return "0" + time;
        else return time + "";
    }
*/




/*more concise
public String nextClosestTime(String s) {
    int[] time = new int[]{Integer.parseInt(s.substring(0,2)), Integer.parseInt(s.substring(3))};
    for (int i=time[0]; i<48; i++)
        for (int j= i == time[0] ? time[1] + 1 : 0; j<60; j++)
            if (isValid(s, String.format("%02d", i % 24)) && isValid(s, String.format("%02d", j)))
                return String.format("%02d", i % 24) + ":" + String.format("%02d", j);
    return "";
}

public boolean isValid(String time, String newTime) {
    for (char c : newTime.toCharArray())
        if (time.indexOf(c) == -1) return false;
    return true;
}
*/




/*Similar
This approach here is trying to find next digit for each postion in "HH:MM" from right to left. If the next digit is greater than current digit, return directly and keep other digits unchanged.
Here is the steps: (e.g. "17:38")

Retrieve all four digits from given string and sort them in asscending order, "17:38" -> digits[] {'1', '3', '7', '8'}

Apply findNext() from the right most digit to left most digit, try to find next greater digit from digits[] (if exist) which is suitable for current position, otherwise return the minimum digit (digits[0]):

"HH:M_": there is no upperLimit for this position (0-9). Just pick the next different digit in the sequence. In the example above, '8' is already the greatest one, so we change it into the smallest one (digits[0] i.e. '1') and move to next step: "17:38" -> "17:31"

"HH:_M": the upperLimit is '5' (00~59). The next different digit for '3' is '7', which is greater than '5', so we should omit it and try next. Similarly, '8' is beyond the limit, so we should choose next, i.e. '1': "17:38" -> "17:11"

"H_:MM": the upperLimit depends on digits[0]. If digits[0] == '2', then it should be no more than '3'; else no upperLimit (0-9). Here we have digits[0] = '1', so we could choose any digit we want. The next digit for '7' is '8', so we change it and return the result directly. "17:38" -> "18:11"

"_H:MM": the upperLimit is '2' (00~23). Here we don't need to change because we have found a greater available digit in previous position. So "18:11" is the disired next closest time.

class Solution {

    public String nextClosestTime(String time) {
        char[] result = time.toCharArray();
        char[] digits = new char[] {result[0], result[1], result[3], result[4]};
        Arrays.sort(digits);

        // find next digit for HH:M_
        result[4] = findNext(result[4], (char)('9' + 1), digits);  // no upperLimit for this digit, i.e. 0-9
        if(result[4] > time.charAt(4)) return String.valueOf(result);  // e.g. 23:43 -> 23:44

        // find next digit for HH:_M
        result[3] = findNext(result[3], '5', digits);
        if(result[3] > time.charAt(3)) return String.valueOf(result);  // e.g. 14:29 -> 14:41

        // find next digit for H_:MM
        result[1] = digits[0] == '2' ? findNext(result[1], '3', digits) : findNext(result[1], (char)('9' + 1), digits);
        if(result[1] > time.charAt(1)) return String.valueOf(result);  // e.g. 02:37 -> 03:00

        // find next digit for _H:MM
        result[0] = findNext(result[0], '2', digits);
        return String.valueOf(result);  // e.g. 19:59 -> 11:11
    }


    private char findNext(char current, char upperLimit, char[] digits) {
        //System.out.println(current);
        if(current == upperLimit) {
            return digits[0];
        }
        int pos = Arrays.binarySearch(digits, current) + 1;
        while(pos < 4 && (digits[pos] > upperLimit || digits[pos] == current)) { // traverse one by one to find next greater digit
            pos++;
        }
        return pos == 4 ? digits[0] : digits[pos];
    }

}
*/



/*Using regex
public String nextClosestTime(String time) {
    int h = Integer.parseInt(time.substring(0, 2));
    int m = Integer.parseInt(time.substring(3));
    for (int t=60*h+m+1; ; t++) {
        String s = String.format("%02d:%02d", t / 60 % 24, t % 60);
        if (s.matches("[" + time + "]*"))
            return s;
    }
}
*/
