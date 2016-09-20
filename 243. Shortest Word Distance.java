public class Solution {
    public int shortestDistance(String[] words, String word1, String word2) {
        int i;
		int j;
		int min = words.length; //make sure min alwqaqys inti to max & max always inti to min

		for(i = 0; i < words.length; i++){
			if(words[i].equals(word1)){
				for(j = 0; j< words.length; j++){
					if(words[j].equals(word2)){
						if(Math.abs(i-j) <= min) min = Math.abs(i-j);
					}
				}
	
			}
		}
		return min;
    }
}
//TODO Using One-pass