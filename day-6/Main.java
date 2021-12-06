import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    public static long part1(ArrayList<Integer> state) {
        long[] count = new long[9];
        for(Integer val : state) count[val]++;

        int day = 0;
        while(day < 80) {
            long temp = count[0];
            for(int i = 0; i < 8; i++) {
                count[i] = count[i+1];
            }
            count[8] = temp;
            count[6] += temp;
            
            day++;
        }

        return Arrays.stream(count).sum();
    }

    public static long part2(ArrayList<Integer> state) {
        long[] count = new long[9];
        for(Integer val : state) count[val]++;

        int day = 0;
        while(day < 256) {
            long temp = count[0];
            for(int i = 0; i < 8; i++) {
                count[i] = count[i+1];
            }
            count[8] = temp;
            count[6] += temp;
            
            day++;
        }

        return Arrays.stream(count).sum();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));
        
        ArrayList<Integer> state = Arrays.stream(in.nextLine().strip().split(","))
                                .map(s -> Integer.parseInt(s))
                                .collect(Collectors.toCollection(ArrayList::new));

        System.out.printf("Part 1: %d%n", part1(state));
        System.out.printf("Part 1: %d%n", part2(state));

        in.close();
    }
}