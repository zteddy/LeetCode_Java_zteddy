class Solution {
    public int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        if(n == 0) return result;

        int up = 0;
        int down = n-1;
        int left = 0;
        int right = n-1;
        int count = 1;

        int i = 0, j = 0;

        while(true){
            while(j <= right){
                result[i][j] = count++;
                j++;
            }
            j--;
            i++;
            up++;
            if(i > down) break;
            // if(up == down && left == right) break;
            while(i <= down){
                result[i][j] = count++;
                i++;
            }
            i--;
            j--;
            right--;
            if(j < left) break;
            // if(up == down && left == right) break;
            while(j >= left){
                result[i][j] = count++;
                j--;
            }
            j++;
            i--;
            down--;
            if(i < up) break;
            // if(up == down && left == right) break;
            while(i >= up){
                result[i][j] = count++;
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
