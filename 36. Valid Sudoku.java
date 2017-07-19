public class Solution {
    public boolean backtracking(char[][] board, boolean[][] check, int i, int j){
        if(i == board.length-1 && j == board[0].length-1) return false;
        boolean c = false;
        for(int a = 0; a < check.length; a++){
            for(int b = 0; b < check[0].length; b++){
                c = c & check[a][b];
            }
        }
        if(c == false) return true;

        while(board[i][j] != '.'){
            j++;
            if(j >= board[0].length){
                i++;
                j = 0;
            }
            if(i >= board.length){
                return false;
            }
        }



        boolean result = false;

        for(int x = 0; x < 9; x++){
            if(check[i][x] == false) continue;
            if(check[j+9][x] == false) continue;
            if(check[i/3+j/3*3+18][x] == false) continue;

            check[i][x] = false;
            check[j+9][x] = false;
            check[i/3+j/3*3+18][x] = false;

            result = result | backtracking(board, check, i , j);

            check[i][x] = true;
            check[j+9][x] = true;
            check[i/3+j/3*3+18][x] = true;

        }

        return result;


    }

    public boolean isValidSudoku(char[][] board) {
        boolean[][] check = new boolean[27][9];

        for(int i = 0; i < check.length; i++){
            for(int j = 0; j < check[0].length; j++){
                check[i][j] = true;
            }
        }

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] != '.'){
                    int x = board[i][j] - '1';
                    if(check[i][x] == false) return false;
                    if(check[j+9][x] == false) return false;
                    if(check[i/3+j/3*3+18][x] == false) return false;
                    check[i][x] = false;
                    check[j+9][x] = false;
                    check[i/3+j/3*3+18][x] = false;
                }
            }
        }

        return backtracking(board, check, 0 , 0);
        //其实这里return true 就行了；

    }
}

//理解错了题意，基本上做成了37.Sudok Solver, 稍微处理一下corner cases 和传递就可以变成Sudoku Slover了


/*Using hashset
public boolean isValidSudoku(char[][] board) {
    for(int i = 0; i<9; i++){
        HashSet<Character> rows = new HashSet<Character>();
        HashSet<Character> columns = new HashSet<Character>();
        HashSet<Character> cube = new HashSet<Character>();
        for (int j = 0; j < 9;j++){
            if(board[i][j]!='.' && !rows.add(board[i][j]))
                return false;
            if(board[j][i]!='.' && !columns.add(board[j][i]))
                return false;
            int RowIndex = 3*(i/3);
            int ColIndex = 3*(i%3);
            if(board[RowIndex + j/3][ColIndex + j%3]!='.' && !cube.add(board[RowIndex + j/3][ColIndex + j%3]))
                return false;
        }
    }
    return true;
}
*/



/*Using encoding
Collect the set of things we see, encoded as strings. For example:

'4' in row 7 is encoded as "(4)7".
'4' in column 7 is encoded as "7(4)".
'4' in the top-right block is encoded as "0(4)2".
Scream false if we ever fail to add something because it was already added (i.e., seen before).

public boolean isValidSudoku(char[][] board) {
    Set seen = new HashSet();
    for (int i=0; i<9; ++i) {
        for (int j=0; j<9; ++j) {
            if (board[i][j] != '.') {
                String b = "(" + board[i][j] + ")";
                if (!seen.add(b + i) || !seen.add(j + b) || !seen.add(i/3 + b + j/3))
                    return false;
            }
        }
    }
    return true;
}
Edit: Just occurred to me that we can also make it really clear and self-explaining. I'm loving it.

public boolean isValidSudoku(char[][] board) {
    Set seen = new HashSet();
    for (int i=0; i<9; ++i) {
        for (int j=0; j<9; ++j) {
            char number = board[i][j];
            if (number != '.')
                if (!seen.add(number + " in row " + i) ||
                    !seen.add(number + " in column " + j) ||
                    !seen.add(number + " in block " + i/3 + "-" + j/3))
                    return false;
        }
    }
    return true;
}
*/




