import java.io.*;
import java.util.*;

public class Main {
    public static long part1(int[][] grid) {
        long ans = 0;

        for(int T = 0; T < 100; T++) {
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    grid[i][j]++;
                }
            }

            ans += flash(grid)[0];
        }

        return ans;
    }

    public static long part2(int[][] grid) {
        long ans = 0;

        while(true) {
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    grid[i][j]++;
                }
            }

            ans++;
            if(flash(grid)[1] == 1) break;
        }

        return ans;
    }

    public static long[] flash(int[][] grid) {
        boolean[][] flashed = new boolean[10][10];
        boolean change = true;
        long retVal = 0, retBoo = 1L;

        while(change) {
            change = false;
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    if(grid[i][j]>9 && !flashed[i][j]) {
                        flashed[i][j] = true;
                        change = true;
                        retVal++;

                        if(i-1>=0 && j-1>=0) grid[i-1][j-1]++;
                        if(i-1>=0) grid[i-1][j]++;
                        if(i-1>=0 && j+1<10) grid[i-1][j+1]++;
                        if(j-1>=0) grid[i][j-1]++;
                        if(j+1<10) grid[i][j+1]++;
                        if(i+1<10 && j-1>=0) grid[i+1][j-1]++;
                        if(i+1<10) grid[i+1][j]++;
                        if(i+1<10 && j+1<10) grid[i+1][j+1]++;
                    }
                }
            }
        }

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(flashed[i][j])  grid[i][j] = 0;
                else retBoo = 0;
            }
        }

        return new long[]{retVal, retBoo};
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));
        
        int[][] grid1 = new int[10][10];
        int[][] grid2 = new int[10][10];
        for(int i = 0; i < 10; i++) {
            grid1[i] = Arrays.stream( in.nextLine().split("") )
                            .mapToInt(Integer::valueOf)
                            .toArray();
            grid2[i] = grid1[i].clone();
        }

        System.out.printf("Part 1: %d%n", part1(grid1));
        System.out.printf("Part 2: %d%n", part2(grid2));

        in.close();
    }
}