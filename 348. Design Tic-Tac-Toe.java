lass TicTacToe {
    int[][][] status;
    int n;

    /** Initialize your data structure here. */
    public TicTacToe(int n) {
        status = new int[2][n][n];
        this.n = n;
    }

    /** Player {player} makes a move at ({row}, {col}).
        @param row The row of the board.
        @param col The column of the board.
        @param player The player, can be either 1 or 2.
        @return The current winning condition, can be either:
                0: No one wins.
                1: Player 1 wins.
                2: Player 2 wins. */
    public int move(int row, int col, int player) {
        status[player-1][row][col] = 1;
        int horizontal = 1, vertical = 1;
        int flag = 0;
        for(int i = 0; i < n; i++){
            horizontal *= status[player-1][row][i];
        }
        flag += horizontal;
        if(flag > 0) return player;
        for(int i = 0; i < n; i++){
            vertical *= status[player-1][i][col];
        }
        flag += vertical;
        if(flag > 0) return player;
        int diagonal = 1;
        if(row == col){
            diagonal = 1;
            for(int i = 0; i < n; i++){
                diagonal *= status[player-1][i][i];
            }
            flag += diagonal;
        }
        if(row+col == n-1){
            diagonal = 1;
            for(int i = 0; i < n; i++){
                diagonal *= status[player-1][i][n-i-1];
            }
            flag += diagonal;
        }
        if(flag > 0) return player;
        return 0;
    }
}

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */



/*O(1) and Less space
public class TicTacToe {

    private int[] rows;
    private int[] cols;
    private int size;
    private int diagonal;
    private int anti_diagonal;

    public TicTacToe(int n) {
        size = n;
        rows = new int[n];
        cols = new int[n];
    }

    public int move(int row, int col, int player) {
        int add = player == 1 ? 1 : -1;
        if(col == row){
            diagonal += add;
        }
        if(col == size - 1 - row){
            anti_diagonal += add;
        }
        rows[row] += add;
        cols[col] += add;
        if(Math.abs(rows[row]) == size || Math.abs(cols[col]) == size|| Math.abs(diagonal) == size || Math.abs(anti_diagonal) == size){
            return player;
        }
        return 0;
    }
}
*/




/*O(1)
public class TicTacToe {

    public TicTacToe(int n) {
        count = new int[6*n][3];
    }

    public int move(int row, int col, int player) {
        int n = count.length / 6;
        for (int x : new int[]{row, n+col, 2*n+row+col, 5*n+row-col})
            if (++count[x][player] == n)
                return player;
        return 0;
    }

    int[][] count;
}


@StefanPochmann

Brilliant solution!
However I feel some space could be saved by choosing initial size of count matrix to be 5n * 3 instead of 6n * 3, since 2n+row+col should never exceed 4n-2.

Let me know if there is some other reason for choosing initial size to be 6n * 3 instead of 5n * 3.

Modified solution should look like below:

public class TicTacToe {

    public TicTacToe(int n) {
        count = new int[5*n][3];
    }

    public int move(int row, int col, int player) {
        int n = count.length / 5;
        for (int x : new int[]{row, n+col, 2*n+row+col, 4*n+row-col})
            if (++count[x][player] == n)
                return player;
        return 0;
    }

    int[][] count;
}
*/
