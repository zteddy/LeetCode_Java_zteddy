public class Solution {
	public boolean wSBT(int i, int j, char[][] board, String s, boolean[][] visited){
		//if(s.length() == 0) return true;
		if(visited[i][j]) return false;
		if(s.length() == 1 && board[i][j] == s.charAt(0)) return true;
		if(board[i][j] != s.charAt(0)) return false;
		visited[i][j] =true;
		int tall = board.length;
		int width = board[0].length;
		boolean result = false;
		if(i>0) result |= wSBT(i-1,j,board,s.substring(1,s.length()),visited);
		if(j>0) result |= wSBT(i,j-1,board,s.substring(1,s.length()),visited);
		if(i+1<tall) result |= wSBT(i+1,j,board,s.substring(1,s.length()),visited);
		if(j+1<width) result |= wSBT(i,j+1,board,s.substring(1,s.length()),visited);
		if(tall == 1 && width == 1 && s.length() == 1) return true;
		visited[i][j] = false;  //很关键，java传数组的时候传的是reference，所以一定要退出的时候清除visited
		return result;
	}

    public boolean exist(char[][] board, String word) {
		char x = word.charAt(0);
		int tall = board.length;
		int width = board[0].length;
		//boolean[][] visited = new boolean[tall][width];
    	for(int i = 0; i < board.length; i++){
    		for(int j = 0; j < board[0].length; j++){
    			boolean[][] visited = new boolean[tall][width];
    			if(board[i][j] == x){
    				if(wSBT(i,j,board,word,visited)) return true;
    			}
    		}
    	}
    	return false;
    }
}

//WA TLE




public class Solution {
	public boolean wSBT(int i, int j, char[][] board, char[] s, boolean[][] visited, int count){
		//if(s.length() == 0) return true;
		if(visited[i][j]) return false;
		if(count == s.length-1 && s[count] == board[i][j]) return true;
		// if(s.length() == 1 && board[i][j] == s.charAt(0)) return true;
		if(board[i][j] != s[count]) return false;
		visited[i][j] =true;
		int tall = board.length;
		int width = board[0].length;
		boolean result = false;
		if(i>0) result |= wSBT(i-1,j,board,s,visited,count+1);
		if(j>0) result |= wSBT(i,j-1,board,s,visited,count+1);
		if(i+1<tall) result |= wSBT(i+1,j,board,s,visited,count+1);
		if(j+1<width) result |= wSBT(i,j+1,board,s,visited,count+1);
		if(tall == 1 && width == 1 && count == s.length-1) return true;
		visited[i][j] = false;  //很关键，java传数组的时候传的是reference，所以一定要退出的时候清除visited
		return result;
	}

    public boolean exist(char[][] board, String word) {
    	char[] s = word.toCharArray();
		int tall = board.length;
		int width = board[0].length;
		//boolean[][] visited = new boolean[tall][width];
    	for(int i = 0; i < board.length; i++){
    		for(int j = 0; j < board[0].length; j++){
    			boolean[][] visited = new boolean[tall][width];
    			if(board[i][j] == s[0]){
    				if(wSBT(i,j,board,s,visited,0)) return true;
    			}
    		}
    	}
    	return false;
    }
}

//WA TLE

//是因为每次都判断ij是否越界了所以慢？





//Using backtracking and mask
Here accepted solution based on recursion. To save memory I decuded to apply bit mask for every visited cell. Please check board[y][x] ^= 256;

public boolean exist(char[][] board, String word) {
    char[] w = word.toCharArray();
    for (int y=0; y<board.length; y++) {
    	for (int x=0; x<board[y].length; x++) {
    		if (exist(board, y, x, w, 0)) return true;
    	}
    }
    return false;
}

private boolean exist(char[][] board, int y, int x, char[] word, int i) {
	if (i == word.length) return true;
	if (y<0 || x<0 || y == board.length || x == board[y].length) return false;
	if (board[y][x] != word[i]) return false;
	board[y][x] ^= 256;
	boolean exist = exist(board, y, x+1, word, i+1)
		|| exist(board, y, x-1, word, i+1)
		|| exist(board, y+1, x, word, i+1)
		|| exist(board, y-1, x, word, i+1);
	board[y][x] ^= 256;
	return exist;
}
