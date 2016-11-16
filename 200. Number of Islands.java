public class Solution {
    public int numIslands(char[][] grid) {
    	int y = grid.length;
    	if(y == 0) return 0;
    	int x = grid[0].length;  //注意这里哪个是行，哪个是列
    	if(x == 0) return 0;

    	int[] ds = new int[x*y];
    	for(int i = 0; i < y; i++){
    		for(int j = 0; j < x; j++){
    			if(grid[i][j] != '1') continue;
    			int n = i*x+j;
    			if(ds[n] != 0) continue;
    			ds[n] = -1;
    			if(j != 0){
    				if(grid[i][j-1] == '1'){
    					if(ds[i*x+(j-1)] != 0)
    						ds[n] = i*x+(j-1);
    					else ds[i*x+(j-1)] = n;
    				}
    				//break;
    			}
    			if(i != 0){
    				if(grid[i-1][j] == '1'){
    					if(ds[(i-1)*x+j] != 0)
    						ds[n] = (i-1)*x+j;
    					else ds[(i-1)*x+j] = n;
    				}
    				//break;
    			}
    			if(i != y-1){
    				if(grid[i+1][j] == '1'){
    					if(ds[(i+1)*x+j] != 0)
    						ds[n] = (i+1)*x+j;
    					else ds[(i+1)*x+j] = n;
    				}
    			}
    			if(j != x-1){
    				if(grid[i][j+1] == '1'){
    					if(ds[i*x+(j+1)] != 0)
    						ds[n] = i*x+(j+1);
    					else ds[i*x+(j+1)] = n;
    				}
    			}
    		}
    	}
    	int count = 0;
    	for(int i = 0; i < x*y; i++){
    		if(ds[i]<0) count++;
    	}
    	return count;
    }
}

//WA

//UF
For any problem I work on, I will try to generalize some reusable template out for future use.
We have limited time during interview and too much to worry about, so having some code template to use is very handy.
For this problem, although it is easier and probably suggested to use BFS, but Union find also comes handy and can be easily extended to solve Island 2 and Surrounded regions.

I separate all the union find logic in a separate class and use 1d version to make the code clear.
I also use a 2d array for the 4 direction visit. int[][] distance = {{1,0},{-1,0},{0,1},{0,-1}};

int[][] distance = {{1,0},{-1,0},{0,1},{0,-1}};
public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0 || grid[0].length == 0)  {
        return 0;
    }
    UnionFind uf = new UnionFind(grid);
    int rows = grid.length;
    int cols = grid[0].length;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (grid[i][j] == '1') {
                for (int[] d : distance) {
                    int x = i + d[0];
                    int y = j + d[1];
                    if (x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] == '1') {
                        int id1 = i*cols+j;
                        int id2 = x*cols+y;
                        uf.union(id1, id2);
                    }
                }
            }
        }
    }
    return uf.count;
}

class UnionFind {
    int[] father;
    int m, n;
    int count = 0;
    UnionFind(char[][] grid) {
        m = grid.length;
        n = grid[0].length;
        father = new int[m*n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    int id = i * n + j;
                    father[id] = id;
                    count++;
                }
            }
        }
    }
    public void union(int node1, int node2) {
        int find1 = find(node1);
        int find2 = find(node2);
        if(find1 != find2) {
            father[find1] = find2;
            count--;
        }
    }
    public int find (int node) {
        if (father[node] == node) {
            return node;
        }
        father[node] = find(father[node]);
        return father[node];
    }
}


//DFS
public int numIslands(char[][] grid) {
    int islands = 0;
    if (grid != null && grid.length != 0 && grid[0].length != 0) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    islands++;
                }
            }
        }
    }
    return islands;
}

private void dfs(char[][] grid, int x, int y) {
    if (x < 0 || grid.length <= x || y < 0 || grid[0].length <= y || grid[x][y] != '1') {
        return;
    }
    grid[x][y] = 'x';
    dfs(grid, x + 1, y);
    dfs(grid, x - 1, y);
    dfs(grid, x, y + 1);
    dfs(grid, x, y - 1);
}
