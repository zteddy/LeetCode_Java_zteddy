public class Solution {
    public void backTracking(int n, List<String> result){
        if(result.size() >= Math.pow(2,n)) flag = true;
        if(flag == false){
            temp = result.get(result.size()-1);
            for(int i = 0; i < temp.length; i++){
                Set<String> setR
            }
        }



    }
    public List<Integer> grayCode(int n) {

    }
}


//不知道怎么检测重复的String，用重载的HashSet？


//Base Knowledge
Just added information for those who are interested (all credited to Wiki gray code


        The purpose of this function is to convert an unsigned
        binary number to reflected binary Gray code.

        The operator >> is shift right. The operator ^ is exclusive or.

unsigned int binaryToGray(unsigned int num)
{
        return (num >> 1) ^ num;
}


        The purpose of this function is to convert a reflected binary
        Gray code number to a binary number.

unsigned int grayToBinary(unsigned int num)
{
    unsigned int mask;
    for (mask = num >> 1; mask != 0; mask = mask >> 1)
    {
        num = num ^ mask;
    }
    return num;
}



I agree with loick. I don't think it's a knowledge base problem. It's also my first time to hear about Gray Code. But after trying some small cases, I still figured out an algorithm for it.

From my intuition, the problem is like Hanoi. If you're able to solve n = 2 case, then you can kind of repeat it twice to achieve n = 3 case. Lets try to extend n = 2 case to n = 3 case first.

When n = 2, the sequence is
00 -> 01 -> 11 -> 10
If you want to extend it to n=3 directly without modifying old part, there are only two possible sequence, and they are not hard to find out.

000 -> 001 -> 011 -> 010 -> 110 -> 111 -> 101 -> 100

000 -> 001 -> 011 -> 010 -> 110 -> 100 -> 101 -> 111

So now, the problem is, which one should we choose. I would choose the first one for two reasons.

The last elements have similar form in both n=2 and n=3 case. They are 1 follows bunch of 0's. Since we hope to extend the same algorithm to n=4 n=5... cases. It's good to preserve some properties.

If we only look at the last 2 digits, we can see that in the first sequence, the second half is exact the reverse of the first half, that means, we can systematically generate the second half according to the first half.

Thats how I figured out the algorithm. Hope that helps!



//Using Bit Manipulation
public List<Integer> grayCode(int n) {
    List<Integer> result = new LinkedList<>();
    for (int i = 0; i < 1<<n; i++) result.add(i ^ i>>1);
    return result;
}
The idea is simple. G(i) = i^ (i/2).



//Using iterative
My idea is to generate the sequence iteratively. For example, when n=3, we can get the result based on n=2.
00,01,11,10 -> (000,001,011,010 ) (110,111,101,100). The middle two numbers only differ at their highest bit, while the rest numbers of part two are exactly symmetric of part one. It is easy to see its correctness.
Code is simple:

public List<Integer> grayCode(int n) {
    List<Integer> rs=new ArrayList<Integer>();
    rs.add(0);
    for(int i=0;i<n;i++){
        int size=rs.size();
        for(int k=size-1;k>=0;k--)
            rs.add(rs.get(k) | 1<<i);
    }
    return rs;
}




//Using backtracking
public static List<Integer> grayCode(int n) {
    List<Integer> code = new ArrayList<Integer>();
    code.add(0);
    genCodes(code,n,0,1<<n);
    return code;
}
public static void genCodes(List<Integer> code, int n, int curr, int size) {
    if(code.size()==size) return;
    for(int i=0;i<n;i++) {
        int mask = 1<<i;
        int el = (curr^mask);
        if(!code.contains(el)) {
            code.add(el);
            genCodes(code,n,el,size);
            if(code.size()==size) return;
            code.remove(new Integer(el));
        }
    }
}

