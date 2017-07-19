public class Solution {

    char[][] xboard = new char[9][9];

    public boolean backtracking(char[][] board, boolean[][] check, int i, int j){
        // if(i == board.length-1 && j == board[0].length-1) return false;


        boolean c = false;
        for(int a = 0; a < check.length; a++){
            for(int b = 0; b < check[0].length; b++){
                c = c | check[a][b];
            }
        }
        if(c == false) return true;
        // xboard[i][j] = 'a';

        // if(!(i == 0 && j == 0)){
            j++;
            if(j >= board[0].length){
                i++;
                j = 0;
            }
            if(i >= board.length){
                return false;
            }

        // }


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

        // xboard[i][j] = 'a';

        boolean result = false;

        for(int x = 0; x < 9; x++){
            if(check[i][x] == false) continue;
            if(check[j+9][x] == false) continue;
            if(check[i/3+j/3*3+18][x] == false) continue;

            check[i][x] = false;
            check[j+9][x] = false;
            check[i/3+j/3*3+18][x] = false;

            xboard[i][j] = (char)(x + '1');


            // xboard[i][j] = 'a';


            result = result | backtracking(board, check, i , j);




            if(result == true) return true;
            xboard[i][j] = '.';

            check[i][x] = true;
            check[j+9][x] = true;
            check[i/3+j/3*3+18][x] = true;

        }

        return result;


    }

    public void solveSudoku(char[][] board) {
        boolean[][] check = new boolean[27][9];

        for(int i = 0; i < check.length; i++){
            for(int j = 0; j < check[0].length; j++){
                check[i][j] = true;
            }
        }

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                xboard[i][j] = board[i][j];
                if(board[i][j] != '.'){

                    int x = board[i][j] - '1';

                    check[i][x] = false;
                    check[j+9][x] = false;
                    check[i/3+j/3*3+18][x] = false;
                }
            }
        }

//         for(int i = 0; i < board.length; i++){
//             for(int j = 0; j < board[0].length; j++){

//                 xboard[i][j] = '7';

//             }
//         }



        backtracking(board, check, 0 , -1);

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == '.'){
                    board[i][j] = xboard[i][j];
                }

            }
        }

    }
}




/*More concise
Try 1 through 9 for each cell. The time complexity should be 9 ^ m (m represents the number of blanks to be filled in), since each blank can have 9 choices. Details see comments inside code. Let me know your suggestions.

Sorry for being late to answer the time complexity question

public class Solution {
    public void solveSudoku(char[][] board) {
        if(board == null || board.length == 0)
            return;
        solve(board);
    }

    public boolean solve(char[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == '.'){
                    for(char c = '1'; c <= '9'; c++){//trial. Try 1 through 9
                        if(isValid(board, i, j, c)){
                            board[i][j] = c; //Put c for this cell

                            if(solve(board))
                                return true; //If it's the solution return true
                            else
                                board[i][j] = '.'; //Otherwise go back
                        }
                    }

                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(char[][] board, int row, int col, char c){
        for(int i = 0; i < 9; i++) {
            if(board[i][col] != '.' && board[i][col] == c) return false; //check row
            if(board[row][i] != '.' && board[row][i] == c) return false; //check column
            if(board[3 * (row / 3) + i / 3][ 3 * (col / 3) + i % 3] != '.' &&
board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) return false; //check 3*3 block
        }
        return true;
    }
}
*/




/*Using bit operation
Singapore's prime minister Lee Hsien Loong showcased his Sudoku Solver C code. You can read his original Facebook post here and another news reporting it here.

I have made some slight modification to adapt it so it can be tested on LeetCode OJ. It passed all 6/6 test cases with a runtime of 1 ms. Pretty impressive for a prime minister, huh?

// Original author: Hsien Loong Lee (http://bit.ly/1zfIGMc)
// Slight modification by @1337c0d3r to adapt to run on LeetCode OJ.
// https://leetcode.com/problems/sudoku-solver/
int InBlock[81], InRow[81], InCol[81];

const int BLANK = 0;
const int ONES = 0x3fe;     // Binary 1111111110

int Entry[81];  // Records entries 1-9 in the grid, as the corresponding bit set to 1
int Block[9], Row[9], Col[9];   // Each int is a 9-bit array

int SeqPtr = 0;
int Sequence[81];



void SwapSeqEntries(int S1, int S2)
{
     int temp = Sequence[S2];
     Sequence[S2] = Sequence[S1];
     Sequence[S1] = temp;
}


void InitEntry(int i, int j, int val)
{
     int Square = 9 * i + j;
     int valbit = 1 << val;
     int SeqPtr2;

     // add suitable checks for data consistency

     Entry[Square] = valbit;
     Block[InBlock[Square]] &= ~valbit;
     Col[InCol[Square]] &= ~valbit; // Simpler Col[j] &= ~valbit;
     Row[InRow[Square]] &= ~valbit; // Simpler Row[i] &= ~valbit;

     SeqPtr2 = SeqPtr;
     while (SeqPtr2 < 81 && Sequence[SeqPtr2] != Square)
           SeqPtr2++ ;

     SwapSeqEntries(SeqPtr, SeqPtr2);
     SeqPtr++;
}


void PrintArray(char **board)
{
     int i, j, valbit, val, Square;
     char ch;

     Square = 0;

     for (i = 0; i < 9; i++) {
         for (j = 0; j < 9; j++) {
             valbit = Entry[Square++];
             if (valbit == 0) ch = '-';
             else {
                 for (val = 1; val <= 9; val++)
                     if (valbit == (1 << val)) {
                        ch = '0' + val;
                        break;
                     }
             }
             board[i][j] = ch;
         }
     }
}


int NextSeq(int S)
{
    int S2, Square, Possibles, BitCount;
    int T, MinBitCount = 100;

    for (T = S; T < 81; T++) {
        Square = Sequence[T];
        Possibles = Block[InBlock[Square]] & Row[InRow[Square]] & Col[InCol[Square]];
        BitCount = 0;
        while (Possibles) {
           Possibles &= ~(Possibles & -Possibles);
           BitCount++;
        }

        if (BitCount < MinBitCount) {
           MinBitCount = BitCount;
           S2 = T;
        }
    }

    return S2;
}


void Place(int S, char** board)
{
    if (S >= 81) {
        PrintArray(board);
        return;
    }

    int S2 = NextSeq(S);
    SwapSeqEntries(S, S2);

    int Square = Sequence[S];

    int     BlockIndex = InBlock[Square],
            RowIndex = InRow[Square],
            ColIndex = InCol[Square];

    int     Possibles = Block[BlockIndex] & Row[RowIndex] & Col[ColIndex];
    while (Possibles) {
          int valbit = Possibles & (-Possibles); // Lowest 1 bit in Possibles
          Possibles &= ~valbit;
          Entry[Square] = valbit;
          Block[BlockIndex] &= ~valbit;
          Row[RowIndex] &= ~valbit;
          Col[ColIndex] &= ~valbit;

          Place(S + 1, board);

          Entry[Square] = BLANK; // Could be moved out of the loop
          Block[BlockIndex] |= valbit;
          Row[RowIndex] |= valbit;
          Col[ColIndex] |= valbit;
    }

    SwapSeqEntries(S, S2);
}

void solveSudoku(char **board, int m, int n) {
    SeqPtr = 0;
    int i, j, Square;

    for (i = 0; i < 9; i++)
        for (j = 0; j < 9; j++) {
            Square = 9 * i + j;
            InRow[Square] = i;
            InCol[Square] = j;
            InBlock[Square] = (i / 3) * 3 + ( j / 3);
        }


    for (Square = 0; Square < 81; Square++) {
        Sequence[Square] = Square;
        Entry[Square] = BLANK;
    }

    for (i = 0; i < 9; i++)
        Block[i] = Row[i] = Col[i] = ONES;

    for (int i = 0; i < 9; ++i)
       for (int j = 0; j < 9; ++j) {
           if ('.' != board[i][j])
                InitEntry(i, j, board[i][j] - '0');
       }

    Place(SeqPtr, board);
}
*/




/*Dancing Links?
https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/sudoku.paper.html
const int N = 9;
const int MaxN = N * N * N + 5;
const int MaxM = N * N * 4 + 5;
const int maxnode = MaxN * MaxM;
struct DLX
{
    int n, m, size;
    int U[maxnode], D[maxnode], R[maxnode], L[maxnode], Row[maxnode], Col[maxnode];
    int H[MaxN], S[MaxM];
    int ansd, ans[MaxN];
    void init(int _n, int _m)
    {
        n = _n;
        m = _m;
        for (int i = 0; i <= m; i++)
        {
            S[i] = 0;
            U[i] = D[i] = i;
            L[i] = i - 1;
            R[i] = i + 1;
        }
        R[m] = 0;
        L[0] = m;
        size = m;
        for (int i = 1; i <= n; i++)H[i] = -1;
    }
    void Link(int r, int c)
    {
        ++S[Col[++size] = c];
        Row[size] = r;
        D[size] = D[c];
        U[D[c]] = size;
        U[size] = c;
        D[c] = size;
        if (H[r] < 0)H[r] = L[size] = R[size] = size;
        else
        {
            R[size] = R[H[r]];
            L[R[H[r]]] = size;
            L[size] = H[r];
            R[H[r]] = size;
        }
    }
    void remove(int c)
    {
        L[R[c]] = L[c];
        R[L[c]] = R[c];
        for (int i = D[c]; i != c; i = D[i])
            for (int j = R[i]; j != i; j = R[j])
            {
                U[D[j]] = U[j];
                D[U[j]] = D[j];
                --S[Col[j]];
            }
    }
    void resume(int c)
    {
        for (int i = U[c]; i != c; i = U[i])
            for (int j = L[i]; j != i; j = L[j])
                ++S[Col[U[D[j]] = D[U[j]] = j]];
        L[R[c]] = R[L[c]] = c;
    }
    bool Dance(int d)
    {
        if (R[0] == 0)
        {
            ansd = d;
            return true;
        }
        int c = R[0];
        for (int i = R[0]; i != 0; i = R[i])
            if (S[i] < S[c])
                c = i;
        remove(c);
        for (int i = D[c]; i != c; i = D[i])
        {
            ans[d] = Row[i];
            for (int j = R[i]; j != i; j = R[j])remove(Col[j]);
            if (Dance(d + 1))return true;
            for (int j = L[i]; j != i; j = L[j])resume(Col[j]);
        }
        resume(c);
        return false;
    }
};
DLX dlx;
int ans[MaxN + 10];
class Solution
{
public:
    void solveSudoku(vector< vector<char> >& board)
    {
        dlx.init(N * N * N, N * N * 4);
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                char num = board[i][j];
                for (int k = 1; k <= N; k++)
                    if (num == '.' || num == k + '0')
                    {
                        int line = (i * N + j) * N + k;
                        dlx.Link(line, i * N + j + 1);
                        dlx.Link(line, 81 + i * N + k);
                        dlx.Link(line, 162 + j * N + k);
                        dlx.Link(line, 243 + (i / 3 * 3 + j / 3) * N + k);
                    }
            }
        }
        dlx.Dance(0);
        for (int i = 0; i < dlx.ansd; i++) ans[(dlx.ans[i] - 1) / N] = (dlx.ans[i] - 1) % N + 1;
        for (int i = 0; i < N ; i++)
            for(int j=0; j<N; j++) board[i][j]=ans[i*N+j]+'0';
    }
};
*/
