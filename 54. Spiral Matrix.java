class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {

        List<Integer> result = new ArrayList<>();
        if(matrix.length == 0 || matrix[0].length == 0) return result;

        int up = 0;
        int down = matrix.length-1;
        int left = 0;
        int right = matrix[0].length-1;

        int i = 0, j = 0;

        while(true){
            while(j <= right){
                result.add(matrix[i][j]);
                j++;
            }
            j--;
            i++;
            up++;
            if(i > down) break;
            // if(up == down && left == right) break;
            while(i <= down){
                result.add(matrix[i][j]);
                i++;
            }
            i--;
            j--;
            right--;
            if(j < left) break;
            // if(up == down && left == right) break;
            while(j >= left){
                result.add(matrix[i][j]);
                j--;
            }
            j++;
            i--;
            down--;
            if(i < up) break;
            // if(up == down && left == right) break;
            while(i >= up){
                result.add(matrix[i][j]);
                i--;
            }
            i++;
            j++;
            left++;
            if(j > right) break;
            // if(up == down && left == right) break;
        }
        return result;
    }
}





/*Summary
Approach #1: Simulation [Accepted]

Intuition

Draw the path that the spiral makes. We know that the path should turn clockwise whenever it would go out of bounds or into a cell that was previously visited.

Algorithm

Let the array have \text{R}R rows and \text{C}C columns. \text{seen[r][c]}seen[r][c] denotes that the cell on the\text{r}r-th row and \text{c}c-th column was previously visited. Our current position is \text{(r, c)}(r, c), facing direction \text{di}di, and we want to visit \text{R}R x \text{C}C total cells.

As we move through the matrix, our candidate next position is \text{(cr, cc)}(cr, cc). If the candidate is in the bounds of the matrix and unseen, then it becomes our next position; otherwise, our next position is the one after performing a clockwise turn.

Java

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List ans = new ArrayList();
        if (matrix.length == 0) return ans;
        int R = matrix.length, C = matrix[0].length;
        boolean[][] seen = new boolean[R][C];
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};
        int r = 0, c = 0, di = 0;
        for (int i = 0; i < R * C; i++) {
            ans.add(matrix[r][c]);
            seen[r][c] = true;
            int cr = r + dr[di];
            int cc = c + dc[di];
            if (0 <= cr && cr < R && 0 <= cc && cc < C && !seen[cr][cc]){
                r = cr;
                c = cc;
            } else {
                di = (di + 1) % 4;
                r += dr[di];
                c += dc[di];
            }
        }
        return ans;
    }
}
Python

class Solution(object):
    def spiralOrder(self, matrix):
        if not matrix: return []
        R, C = len(matrix), len(matrix[0])
        seen = [[False] * C for _ in matrix]
        ans = []
        dr = [0, 1, 0, -1]
        dc = [1, 0, -1, 0]
        r = c = di = 0
        for _ in range(R * C):
            ans.append(matrix[r][c])
            seen[r][c] = True
            cr, cc = r + dr[di], c + dc[di]
            if 0 <= cr < R and 0 <= cc < C and not seen[cr][cc]:
                r, c = cr, cc
            else:
                di = (di + 1) % 4
                r, c = r + dr[di], c + dc[di]
        return ans
Complexity Analysis

Time Complexity: O(N)O(N), where NN is the total number of elements in the input matrix. We add every element in the matrix to our final answer.

Space Complexity: O(N)O(N), the information stored in seen and in ans.

Approach #2: Layer-by-Layer [Accepted]

Intuition

The answer will be all the elements in clockwise order from the first-outer layer, followed by the elements from the second-outer layer, and so on.

Algorithm

We define the \text{k}k-th outer layer of a matrix as all elements that have minimum distance to some border equal to \text{k}k. For example, the following matrix has all elements in the first-outer layer equal to 1, all elements in the second-outer layer equal to 2, and all elements in the third-outer layer equal to 3.

[[1, 1, 1, 1, 1, 1, 1],
 [1, 2, 2, 2, 2, 2, 1],
 [1, 2, 3, 3, 3, 2, 1],
 [1, 2, 2, 2, 2, 2, 1],
 [1, 1, 1, 1, 1, 1, 1]]
For each outer layer, we want to iterate through its elements in clockwise order starting from the top left corner. Suppose the current outer layer has top-left coordinates \text{(r1, c1)}(r1, c1) and bottom-right coordinates \text{(r2, c2)}(r2, c2).

Then, the top row is the set of elements \text{(r1, c)}(r1, c) for \text{c = c1,...,c2}c = c1,...,c2, in that order. The rest of the right side is the set of elements \text{(r, c2)}(r, c2) for \text{r = r1+1,...,r2}r = r1+1,...,r2, in that order. Then, if there are four sides to this layer (ie., r1 < r2r1 < r2 and c1 < c2c1 < c2), we iterate through the bottom side and left side as shown in the solutions below.

SpiralMatrix

Java

class Solution {
    public List < Integer > spiralOrder(int[][] matrix) {
        List ans = new ArrayList();
        if (matrix.length == 0)
            return ans;
        int r1 = 0, r2 = matrix.length - 1;
        int c1 = 0, c2 = matrix[0].length - 1;
        while (r1 <= r2 && c1 <= c2) {
            for (int c = c1; c <= c2; c++) ans.add(matrix[r1][c]);
            for (int r = r1 + 1; r <= r2; r++) ans.add(matrix[r][c2]);
            if (r1 < r2 && c1 < c2) {
                for (int c = c2 - 1; c > c1; c--) ans.add(matrix[r2][c]);
                for (int r = r2; r > r1; r--) ans.add(matrix[r][c1]);
            }
            r1++;
            r2--;
            c1++;
            c2--;
        }
        return ans;
    }
}
Python

class Solution(object):
    def spiralOrder(self, matrix):
        def spiral_coords(r1, c1, r2, c2):
            for c in range(c1, c2 + 1):
                yield r1, c
            for r in range(r1 + 1, r2 + 1):
                yield r, c2
            if r1 < r2 and c1 < c2:
                for c in range(c2 - 1, c1, -1):
                    yield r2, c
                for r in range(r2, r1, -1):
                    yield r, c1

        if not matrix: return []
        ans = []
        r1, r2 = 0, len(matrix) - 1
        c1, c2 = 0, len(matrix[0]) - 1
        while r1 <= r2 and c1 <= c2:
            for r, c in spiral_coords(r1, c1, r2, c2):
                ans.append(matrix[r][c])
            r1 += 1; r2 -= 1
            c1 += 1; c2 -= 1
        return ans
Complexity Analysis

Time Complexity: O(N)O(N), where NN is the total number of elements in the input matrix. We add every element in the matrix to our final answer.

Space Complexity: O(N)O(N), the information stored in ans.
*/





/*Using wall
This is a very simple and easy to understand solution. I traverse right and increment rowBegin, then traverse down and decrement colEnd, then I traverse left and decrement rowEnd, and finally I traverse up and increment colBegin.

The only tricky part is that when I traverse left or up I have to check whether the row or col still exists to prevent duplicates. If anyone can do the same thing without that check, please let me know!

Any comments greatly appreciated.

public class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {

        List<Integer> res = new ArrayList<Integer>();

        if (matrix.length == 0) {
            return res;
        }

        int rowBegin = 0;
        int rowEnd = matrix.length-1;
        int colBegin = 0;
        int colEnd = matrix[0].length - 1;

        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            // Traverse Right
            for (int j = colBegin; j <= colEnd; j ++) {
                res.add(matrix[rowBegin][j]);
            }
            rowBegin++;

            // Traverse Down
            for (int j = rowBegin; j <= rowEnd; j ++) {
                res.add(matrix[j][colEnd]);
            }
            colEnd--;

            if (rowBegin <= rowEnd) {
                // Traverse Left
                for (int j = colEnd; j >= colBegin; j --) {
                    res.add(matrix[rowEnd][j]);
                }
            }
            rowEnd--;

            if (colBegin <= colEnd) {
                // Traver Up
                for (int j = rowEnd; j >= rowBegin; j --) {
                    res.add(matrix[j][colBegin]);
                }
            }
            colBegin ++;
        }

        return res;
    }
}
*?
