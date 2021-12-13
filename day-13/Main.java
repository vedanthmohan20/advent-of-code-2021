import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {

    public static long part1(HashSet<int[]> points, String[] fold) {
        long ans = 0;
        int val = Integer.parseInt(fold[1]);

        if(fold[0].equals("x")) {
            ans = points.stream()
                        .map(v -> v[0]>val ? (new int[]{val-Math.abs(v[0]-val), v[1]}) : (v))
                        .filter(v -> v[0]>=0)
                        .map(v -> String.valueOf(v[0])+","+String.valueOf(v[1]))
                        .distinct().count();

        }
        else {
            ans = points.stream()
                        .map(v -> v[1]>val ? (new int[]{v[0], val-Math.abs(v[1]-val)}) : (v))
                        .filter(v -> v[1]>=0)
                        .map(v -> String.valueOf(v[0])+","+String.valueOf(v[1]))
                        .distinct().count();
        }

        return ans;
    }
    
    public static void part2(HashSet<int[]> points, ArrayList<String[]> folds) {
        Stream<int[]> stream = points.stream();
        for(String[] fold : folds) {
            int val = Integer.parseInt(fold[1]);

            if(fold[0].equals("x")) {
                stream = stream.map(v -> v[0]>val ? (new int[]{val-Math.abs(v[0]-val), v[1]}) : (v))
                               .filter(v -> v[0]>=0);
            }
            else {
                stream = stream.map(v -> v[1]>val ? (new int[]{v[0], val-Math.abs(v[1]-val)}) : (v))
                               .filter(v -> v[1]>=0);
            }
        }

        int[][] board = new int[39][6];
        stream.forEach(v -> board[v[0]][v[1]] = 1);

        for(int j = 0; j < 6; j++) {
            for(int i = 0; i < 39; i++) {
                if(board[i][j] == 1) System.out.print("#");
                else System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));
        
        HashSet<int[]> points = new HashSet<>();
        ArrayList<String[]> folds = new ArrayList<>();
        while(in.hasNextLine()) {
            String line = in.nextLine();
            if(line.contains(",")) {
                int[] p = Arrays.stream( line.strip().split(",") ).mapToInt(Integer::valueOf).toArray();
                points.add(p);
            }
            else if(line.length()>0) {
                String[] fold = line.strip().split(" ")[2].split("=");
                folds.add(fold);
            }
        }
        
        System.out.printf("Part 1: %d%n", part1(points, folds.get(0)));
        System.out.println("Part 2: ");
        part2(points, folds);

        in.close();
    }
}