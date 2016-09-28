/* The read4 API is defined in the parent class Reader4.
      int read4(char[] buf); */

public class Solution extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    public int read(char[] buf, int n) {
		
		int count = 0;
		count = read4(buf);
		int temp = 0;
		int result = 0;
		
		if(count >= n) return n;
		
		while(count < n){
			temp = read4(buf);
			count += temp;
			if(temp < 4) return result = (count <= n)? count:n;
			
		}
		
		return result = (count <= n)? count:n;
        
    }
}