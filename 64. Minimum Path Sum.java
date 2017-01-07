public class Solution {
    public int minPathSum(int[][] grid) {
    	for(int j = 0; j < grid[0].length; j++){
    		for(int i = 0; i < grid.length; i++){
    			if(i == 0 & j == 0) continue;
    			else
    				if(j == 0) grid[i][j] = grid[i-1][j]+grid[i][j];
    			else
    				if(i == 0) grid[i][j] = grid[i][j-1]+grid[i][j];
    			else
    				grid[i][j] = Math.min(grid[i-1][j]+grid[i][j], grid[i][j-1]+grid[i][j]);
    		}
    	}
    	return grid[grid.length-1][grid[0].length-1];
    }
}

/*More concise
int m = grid.length, n = grid[0].length;
for(int i = 0; i < m; i++){
	for(int j = 0; j < n; j++){
	if(i == 0 && j != 0) grid[i][j] += grid[i][j-1];
	if(i != 0 && j == 0) grid[i][j] += grid[i-1][j];
	if (i != 0 && j != 0) grid[i][j] += Math.min(grid[i-1][j], grid[i][j-1]);
	}
}
return grid[m-1][n-1];
*/
