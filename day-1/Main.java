import java.io.*;
import java.util.*;

public class Main {
    public static long part1(ArrayList<Long> nums) {
        long ans = 0;
        
        for(int i = 1; i < nums.size(); i++) {
            if(nums.get(i) > nums.get(i-1)) ans++;
        }

        return ans;
    }

    public static long part2(ArrayList<Long> nums) {
        long ans = 0;

        for(int i = 1; i < nums.size(); i++) {
            nums.set(i, nums.get(i)+nums.get(i-1));
        }
        for(int i = 3; i < nums.size(); i++) {
            long cur = nums.get(i) - nums.get(i-3);
            long prev = nums.get(i-1) - (i-4 < 0 ? 0 : nums.get(i-4)); 
            if(cur > prev) ans++;
        }

        return ans;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));

        ArrayList<Long> nums = new ArrayList<>();
        while(in.hasNextLine()) {
            Long value = Long.parseLong(in.nextLine());
            nums.add(value);
        }
        
        System.out.printf("part 1: %d%n", part1(nums));
        System.out.printf("part 2: %d%n", part2(nums));

        in.close();
    }
}