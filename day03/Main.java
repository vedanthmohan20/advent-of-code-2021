import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static long part1(ArrayList<String> list) {
        int size = list.size();
        int[] counts = new int[list.get(0).length()];

        for(String s : list) {
            for(int i = 0; i < s.length(); i++) {
                if(s.charAt(i) == '0') counts[i]++;
            }
        }

        String gamma = "", epsilon = "";
        for(int zc : counts) {
            if(zc > size/2) {
                gamma += "0";
                epsilon += "1";
            }
            else {
                gamma += "1";
                epsilon += "0";
            }
        }

        return Long.parseLong(gamma, 2) * Long.parseLong(epsilon, 2);
    }
    
    public static long part2(ArrayList<String> list) {
        ArrayList<String> l1 = new ArrayList<>(list);
        for(int i = 0; i < list.get(0).length(); i++) {
            int ind = i;
            int zc = 0;
            
            for(String s : l1) {
                if(s.charAt(i) == '0') zc++;
            }

            if(zc > l1.size()/2) { // 0 is the most common bit at the current position
                l1 = l1.stream()
                                .filter(s -> s.charAt(ind) == '0') // only retains strings with 0 at the current position
                                .collect(Collectors.toCollection(ArrayList::new)); // converting Stream<T> to ArrayList<T>
            }
            else { // equal, or 1 is the most common bit at the current position
                l1 = l1.stream()
                                .filter(s -> s.charAt(ind) == '1') // only retains strings with 1 at the current position
                                .collect(Collectors.toCollection(ArrayList::new)); // converting Stream<T> to ArrayList<T>
            }
            
            if(l1.size() == 1) break;
        }
        
        ArrayList<String> l2 = new ArrayList<>(list);
        for(int i = 0; i < list.get(0).length(); i++) {
            int ind = i;
            int zc = 0;

            for(String s : l2) {
                if(s.charAt(i) == '0') zc++;
            }

            if(zc > l2.size()/2) { // 1 is the least common bit at the current position
                l2 = l2.stream()
                                .filter(s -> s.charAt(ind) == '1') // only retains strings with 1 at the current position
                                .collect(Collectors.toCollection(ArrayList::new)); // converting Stream<T> to ArrayList<T>
            }
            else { // equal, or 0 is the least common bit at the current position
                l2 = l2.stream()
                                .filter(s -> s.charAt(ind) == '0') // only retains strings with 0 at the current position
                                .collect(Collectors.toCollection(ArrayList::new)); // converting Stream<T> to ArrayList<T>
            }

            if(l2.size() == 1) break;
        }

        return Long.parseLong(l1.get(0), 2) * Long.parseLong(l2.get(0), 2);
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
