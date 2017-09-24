class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = 0;
        int column = 0;

        if(matrix.length == 0 || matrix[0].length == 0) return false;

        while(row < matrix.length && target > matrix[row][matrix[0].length-1]) row++;

        if(row == matrix.length) return false;

        for(column = 0; column < matrix[0].length; column++){
            if(matrix[row][column] == target) return true;
        }

        return false;

    }
}




/*Using Binary Search

n * m matrix convert to an array => matrix[x][y] => a[x * m + y]

an array convert to n * m matrix => a[x] =>matrix[x / m][x % m];

class Solution {
public:
    bool searchMatrix(vector<vector<int> > &matrix, int target) {
        int n = matrix.size();
        int m = matrix[0].size();
        int l = 0, r = m * n - 1;
        while (l != r){
            int mid = (l + r - 1) >> 1;
            if (matrix[mid / m][mid % m] < target)
                l = mid + 1;
            else
                r = mid;
        }
        return matrix[r / m][r % m] == target;
    }
};
*/




/*Using binary search for row and column
Use binary search for matrix[i][0] to find the row where target is in, and then use binary search for matrix[row][j] to find target. This solution is better because it avoids multiplication overflow(height*width) and / and % while it's complexity is the same as solution1.

class Solution {
public:
    bool searchMatrix(vector<vector<int> > &matrix,int target)
    {
        if(matrix.empty())  return false;

        int heigth = matrix.size();
        int width = matrix[0].size();

        if(matrix[0][0] > target || matrix[heigth-1][width-1] < target)     return false;

        int head = 0;
        int tail = heigth-1;
        int mid;
        while(head != tail && matrix[tail][0] > target)
        {
            mid = (head+tail+1)/2;
            if(matrix[mid][0] < target)     head = mid;
            else if(matrix[mid][0] > target)    tail = mid-1;
            else    return true;
        }
        int row = tail;
        head = 0,tail = width-1;
        while(head <= tail)
        {
            mid = (head+tail)/2;
            if(matrix[row][mid] < target)
                head = mid + 1;
            else if(matrix[row][mid] > target)
                tail = mid -1;
            else return true;
        }
        return false;
    }
};
*/
