public class Solution {
    public int minDistance(String word1, String word2) {
    	if(word1.length() == 0 || word2.length() == 0) return word1.length() == 0? word2.length() : word1.length();
    	int[][] DP = new int[word1.length()+1][word2.length()+1];
    	for(int i = 1; i < word1.length()+1; i++){
    		for(int j = 1; j < word2.length()+1; j++){
    			DP[i][0] = i;
    			DP[0][j] = j;
    			int ii = i-1;
    			int jj = j-1;
    			if(word1.charAt(ii) == word2.charAt(jj)) DP[i][j] = DP[ii][jj];
    			else DP[i][j] = Math.min(Math.min(DP[i][jj]+1, DP[ii][j]+1), DP[ii][jj]+1);
    		}
    	}
    	return DP[word1.length()][word2.length()];
    }
}

//TODO None
