import java.io.*;
import java.util.*;

public class Main {
    public static long part1(ArrayList<String> list) {
        long x = 0, y = 0;
        
        for(String line : list) {
            String dir = line.split(" ")[0];
            long value = Long.parseLong(line.split(" ")[1]);

            if(dir.equals("up")) y -= value;
            else if(dir.equals("down")) y += value;
            else x += value;
        }

        return x*y;
    }

    public static long part2(ArrayList<String> list) {
        long x = 0, y = 0, aim = 0;
        
        for(String line : list) {
            String dir = line.split(" ")[0];
            long value = Long.parseLong(line.split(" ")[1]);

            if(dir.equals("up")) aim -= value;
            else if(dir.equals("down")) aim += value;
            else {
                x += value;
                y += (aim*value);
            }
        }

        return x*y;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));

        ArrayList<String> list = new ArrayList<>();
        while(in.hasNextLine()) {
            list.add(in.nextLine());
        }
        
        System.out.printf("part 1: %d%n", part1(list));
        System.out.printf("part 2: %d%n", part2(list));

        in.close();
    }
}
