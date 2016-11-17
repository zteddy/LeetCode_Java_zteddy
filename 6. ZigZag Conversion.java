public class Solution {
    public String convert(String s, int numRows) {

		StringBuffer result = new StringBuffer();
		List<String> index = new ArrayList<String>();

		int in = 0;
		int front = 0;
		int rear = 0;
		int a = 0;
		int b = 0;
		if(numRows > 1){
			while(in < s.length()){
				index.add(String.valueOf(in));
				in += (numRows-1)*2;
			}
			index.add(String.valueOf(in));

			front = 0;
			rear = index.size()-1;

			for(int i = 2; i<= numRows; i++){
				for(int j = front; j <= rear; j++){
					a = Integer.parseInt(index.get(j)) - 1;
					b = Integer.parseInt(index.get(j)) + 1;

					if(a >= 0 && !index.contains(String.valueOf(a))) index.add(String.valueOf(a));
					if(b >= 0 && !index.contains(String.valueOf(a))) index.add(String.valueOf(b));
				}
				front = rear + 1;
				rear = index.size() - 1;
			}

			for(int i = 0;i < index.size()-1; i++){
				if(Integer.parseInt(index.get(i)) < s.length()){
					result.append(s.charAt(Integer.parseInt(index.get(i))));
				}
			}

			return result.toString();

		}else if(numRows == 1){
			return s;
		}else{
			return "";
		}
    }
}

//TODO Debug
//WA

//Just construct the picture directly
Create nRows StringBuffers, and keep collecting characters from original string to corresponding StringBuffer.
Just take care of your index to keep them in bound.

public String convert(String s, int nRows) {
    char[] c = s.toCharArray();
    int len = c.length;
    StringBuffer[] sb = new StringBuffer[nRows];
    for (int i = 0; i < sb.length; i++) sb[i] = new StringBuffer();

    int i = 0;
    while (i < len) {
        for (int idx = 0; idx < nRows && i < len; idx++) // vertically down
            sb[idx].append(c[i++]);
        for (int idx = nRows-2; idx >= 1 && i < len; idx--) // obliquely up
            sb[idx].append(c[i++]);
    }
    for (int idx = 1; idx < sb.length; idx++)
        sb[0].append(sb[idx]);
    return sb[0].toString();
}

//Same as above
public class Solution {
public String convert(String s, int numRows) {
    if(numRows<=1)return s;
    StringBuilder[] sb=new StringBuilder[numRows];
    for(int i=0;i<sb.length;i++){
        sb[i]=new StringBuilder("");   //init every sb element **important step!!!!
    }
    int incre=1;
    int index=0;
    for(int i=0;i<s.length();i++){
        sb[index].append(s.charAt(i));
        if(index==0){incre=1;}
        if(index==numRows-1){incre=-1;}
        index+=incre;
    }
    String re="";
    for(int i=0;i<sb.length;i++){
        re+=sb[i];
    }
    return re.toString();
}
}

//Using pattern insight
The distribution of the elements is period.

P   A   H   N
A P L S I I G
Y   I   R
For example, the following has 4 periods(cycles):

P   | A   | H   | N
A P | L S | I I | G
Y   | I   | R   |
The size of every period is defined as "cycle"

cycle = (2*nRows - 2), except nRows == 1.
In this example, (2*nRows - 2) = 4.

In every period, every row has 2 elements, except the first row and the last row.

Suppose the current row is i, the index of the first element is j:

j = i + cycle*k, k = 0, 1, 2, ...
The index of the second element is secondJ:

secondJ = (j - i) + cycle - i
(j-i) is the start of current period, (j-i) + cycle is the start of next period.

string convert(string s, int nRows) {
        if(nRows <= 1) return s;
        string result = "";
        //the size of a cycle(period)
        int cycle = 2 * nRows - 2;
        for(int i = 0; i < nRows; ++i)
        {
            for(int j = i; j < s.length(); j = j + cycle){
               result = result + s[j];
               int secondJ = (j - i) + cycle - i;
               if(i != 0 && i != nRows-1 && secondJ < s.length())
                   result = result + s[secondJ];
            }
        }
        return result;
    }


