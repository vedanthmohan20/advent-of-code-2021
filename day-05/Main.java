import java.io.*;
import java.util.*;

public class Main {
    public static long part1(ArrayList<long[][]> lines, long maxX, long maxY) {
        long[][] grid = new long[(int)maxX+2][(int)maxY+2];
        
        // increase the count of all points along the lines
        for(long[][] line : lines) {
            if(line[0][0] == line[1][0]) {
                for(long j = Math.min(line[0][1], line[1][1]); j <= Math.max(line[0][1], line[1][1]); j++) {
                    grid[(int)j][(int)line[0][0]] += 1; 
                }
            }
            else if(line[0][1] == line[1][1]){
                for(long i = Math.min(line[0][0], line[1][0]); i <= Math.max(line[0][0], line[1][0]); i++) {
                    grid[(int)line[0][1]][(int)i] += 1;
                }
            }
        }

        long ans = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] >= 2) ans++;
            }
        }
        return ans;
    }
    
    public static long part2(ArrayList<long[][]> lines, long maxX, long maxY) {
        long[][] grid = new long[(int)maxX+2][(int)maxY+2];
        
        // increase the count of all points along the lines
        for(long[][] line : lines) {
            if(line[0][0] == line[1][0]) {
                for(long j = Math.min(line[0][1], line[1][1]); j <= Math.max(line[0][1], line[1][1]); j++) {
                    grid[(int)j][(int)line[0][0]] += 1;
                }
            }
            else if(line[0][1] == line[1][1]) {
                for(long i = Math.min(line[0][0], line[1][0]); i <= Math.max(line[0][0], line[1][0]); i++) {
                    grid[(int)line[0][1]][(int)i] += 1;
                }
            }
            else {
                for(int j = (int) Math.min(line[0][1], line[1][1]); j <= (int) Math.max(line[0][1], line[1][1]); j++) {
                    for(int i = (int) Math.min(line[0][0], line[1][0]); i <= (int) Math.max(line[0][0], line[1][0]); i++) {
                        if(j-line[0][1] == ((line[1][1]-line[0][1]) / (line[1][0]-line[0][0])) * (i-line[0][0])) grid[j][i] += 1;
                    }
                }
            }
        }

        long ans = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] >= 2) ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));
        
        ArrayList<long[][]> lines = new ArrayList<>();
        long maxX = -1, maxY = -1;
        while(in.hasNextLine()) {
            String[] line = in.nextLine().strip().split(" -> ");
            
            // coverting the points from String[] to long[]
            long[] p1 = Arrays.stream(line[0].split(","))
                                .map(s -> Long.valueOf(s))
                                .mapToLong(Long::valueOf)
                                .toArray();
            long[] p2 = Arrays.stream(line[1].split(","))
                                .map(s -> Long.valueOf(s))
                                .mapToLong(Long::valueOf)
                                .toArray();

            // if the line is vertical or horizontal or diagonal at exactly 45 degrees
            if(p1[0]==p2[0] || p1[1]==p2[1] || ((Math.abs(p2[1]-p1[1]) / Math.abs(p2[0]-p1[0])) == 1)) {
                long[][] p = new long[2][2];
                p[0] = p1;
                p[1] = p2;
                lines.add(p);

                maxX = Math.max(maxX, Math.max(p1[0], p2[0]));
                maxY = Math.max(maxY, Math.max(p1[1], p2[1]));
            }
        }

        System.out.printf("Part 1: %d%n", part1(lines, maxX, maxY));
        System.out.printf("Part 2: %d%n", part2(lines, maxX, maxY));

        in.close();
    }
}
