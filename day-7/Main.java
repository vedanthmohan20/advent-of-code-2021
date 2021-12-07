import java.io.*;
import java.util.*;

public class Main {
    public static long part1(long[] list) {
        long ans = Long.MAX_VALUE;
        long min = Arrays.stream(list).min().orElse(Long.MAX_VALUE);
        long max = Arrays.stream(list).max().orElse(Long.MIN_VALUE);
        for(long i = min; i <= max; i++) {
            long val = i;
            ans = Math.min( Arrays.stream(list).map(v -> Math.abs(v-val) ).sum() , ans);
        }
        return ans;        
    }

    public static long part2(long[] list) {
        long ans = Long.MAX_VALUE;
        long min = Arrays.stream(list).min().orElse(Long.MAX_VALUE);
        long max = Arrays.stream(list).max().orElse(Long.MIN_VALUE);
        for(long i = min; i <= max; i++) {
            long val = i;
            ans = Math.min( Arrays.stream(list).map(v -> ( Math.abs(v-val)*(Math.abs(v-val)+1) )/2 ).sum() , ans);
        }
        return ans;        
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));  

        long[] list = Arrays.stream(in.nextLine().strip().split(",")).map(s->Long.parseLong(s)).mapToLong(Long::valueOf).toArray();

        System.out.printf("Part 1: %d%n", part1(list));
        System.out.printf("Part 2: %d%n", part2(list));

        in.close();
    }
}