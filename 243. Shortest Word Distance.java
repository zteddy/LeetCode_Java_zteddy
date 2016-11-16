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

/*Approach #2 (One-pass) [Accepted]
public int shortestDistance(String[] words, String word1, String word2) {
    int i1 = -1, i2 = -1;
    int minDistance = words.length;
    int currentDistance;
    for (int i = 0; i < words.length; i++) {
        if (words[i].equals(word1)) {
            i1 = i;
        } else if (words[i].equals(word2)) {
            i2 = i;
        }

        if (i1 != -1 && i2 != -1) {
            minDistance = Math.min(minDistance, Math.abs(i1 - i2));
        }
    }
    return minDistance;
}
*/
