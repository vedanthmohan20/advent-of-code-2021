import java.io.*;
import java.util.*;

public class Main {
    public static long part1(ArrayList<long[]> list) {
        long ans = 0;

        for(int i = 0; i < list.size(); i++) {
            for(int j = 0; j < list.get(i).length; j++) {
                if(isLowPoint(list, i, j)) {
                    ans += list.get(i)[j] + 1;
                }
            }
        }

        return ans;
    }

    public static long part2(ArrayList<long[]> list) {
        ArrayList<Long> basinSizes = new ArrayList<>();

        for(int i = 0; i < list.size(); i++) {
            for(int j = 0; j < list.get(i).length; j++) {
                if(isLowPoint(list, i, j)) {
                    basinSizes.add(getBasinSize(list, i, j));
                }
            }
        }

        Collections.sort(basinSizes);
        int n = basinSizes.size();
        return basinSizes.get(n-1)*basinSizes.get(n-2)*basinSizes.get(n-3);
    }

    public static long getBasinSize(ArrayList<long[]> list, int pi, int pj) {
        long[][] grid = new long[list.size()][list.get(0).length];
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for(int i  = 0; i < list.size(); i++) grid[i] = list.get(i);
        
        visited[pi][pj] = true;
        dfs(grid, visited, pi, pj);
        
        long size = 0;
        for(int i = 0; i < visited.length; i++) {
            for(int j = 0; j < visited[i].length; j++) {
                if(visited[i][j]) size++;
            }
        }
        return size;
    }

    public static void dfs(long[][] grid, boolean[][] visited, int i, int j) {
        if(i-1>=0 && grid[i-1][j]!=9 && grid[i-1][j]>grid[i][j]) {
            visited[i-1][j] = true;
            dfs(grid, visited, i-1, j);
        } 

        if(j>0 && grid[i][j-1]!=9 && grid[i][j-1]>grid[i][j]) {
            visited[i][j-1] = true;
            dfs(grid, visited, i, j-1);
        }

        if(j<grid[0].length-1 && grid[i][j+1]!=9 && grid[i][j+1]>grid[i][j]) {
            visited[i][j+1] = true;
            dfs(grid, visited, i, j+1);
        }

        if(i<grid.length-1 && grid[i+1][j]!=9 && grid[i+1][j]>grid[i][j]) {
            visited[i+1][j] = true;
            dfs(grid, visited, i+1, j);
        }
    }

    public static boolean isLowPoint(ArrayList<long[]> list, int i, int j) {
        boolean min = true;
        long cur = list.get(i)[j];

        if(i-1>=0 && list.get(i-1)[j]<=cur) min = false;
        if(j>0 && list.get(i)[j-1]<=cur) min = false; 
        if(j<list.get(i).length-1 && list.get(i)[j+1]<=cur) min = false; 
        if(i<list.size()-1 && list.get(i+1)[j]<=cur) min = false;

        return min;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));
        
        ArrayList<long[]> list = new ArrayList<>();
        while(in.hasNextLine()) {
            long[] line = Arrays.stream(in.nextLine().split(""))
                    .mapToLong(Long::valueOf)
                    .toArray();
            
            list.add(line);
        }

        System.out.printf("Part 1: %d%n", part1(list));
        System.out.printf("Part 2: %d%n", part2(list));

        in.close();
    }
}
