public class Solution {
    public int compareVersion(String version1, String version2) {
    	double v1temp = 0;
    	double v2temp = 0;
    	
    	String[] temp1 = version1.split("\\.");
    	String[] temp2 = version2.split("\\.");

    	int l = Math.max(temp1.length, temp2.length);

    	double[] v1 = new double[l];
    	double[] v2 = new double[l];

    	for(int i = temp1.length-1; i >= 0; i--){
    		v1temp = 0;
    		for(int j = 0; j < temp1[i].length(); j++){
    			if(temp1[i].charAt(j) <= '9' && temp1[i].charAt(j) >= '0'){
	    			v1temp += temp1[i].charAt(j) - '0';
	    			v1temp *= 10;
    			}
    		}
    		v1[i] = v1temp;
    	}

    	for(int i = temp2.length-1; i >= 0; i--){
    		v2temp = 0;
    		for(int j = 0; j < temp2[i].length(); j++){
    			if(temp2[i].charAt(j) <= '9' && temp2[i].charAt(j) >= '0'){
	    			v2temp += temp2[i].charAt(j) - '0';
	    			v2temp *= 10;
    			}
    		}
    		v2[i] = v2temp;
    	}

    	for(int i = 0; i < l; i++){
    		if(v1[i]>v2[i]) return 1;
    		else if(v1[i]<v2[i]) return -1;
    	}

    	return 0; 
    }
}

/*More concise solution
public class Solution {
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        
        int longest = v1.length > v2.length? v1.length: v2.length;
        
        for(int i=0; i<longest; i++)
        {
            int ver1 = i<v1.length? Integer.parseInt(v1[i]): 0;
            int ver2 = i<v2.length? Integer.parseInt(v2[i]): 0;
            
            if(ver1> ver2) return 1;
            if(ver1 < ver2) return -1;
        }
        return 0;
    }
}
*/