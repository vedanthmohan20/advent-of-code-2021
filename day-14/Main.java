import java.io.*;
import java.util.*;

public class Main {

    public static long part1(String template, HashMap<String, String> convertions) {
        // Operations on stringbuilders are much more efficient than operations on strings, so we use
        // stringbuilders in this brute force solution
        StringBuilder cur = new StringBuilder(template);

        for(int T = 0; T < 10; T++) {
            // creating a new stringbuilder from every pair of adjacent characters by following 
            // the pair insertion rules
            StringBuilder t = new StringBuilder();
            t.setLength(cur.length()-1);
            for(int i = 0; i < cur.length()-1; i++) {
                t.setCharAt(i, convertions.get(cur.substring(i, i+2)).charAt(0));
            }

            // combining cur and t by creating a new stringbuilder y that will store
            // their characters in alternating order starting with the first character of cur
            StringBuilder y = new StringBuilder();
            y.setLength(cur.length()+t.length());
            for(int i = 0; i < t.length(); i++) {
                y.setCharAt(2*i, cur.charAt(i));
                y.setCharAt(2*i+1, t.charAt(i));
            }
            y.setCharAt(y.length()-1, cur.charAt(cur.length()-1));

            // setting the current polymer to the polymer we just formed
            cur = y;
        }

        // iterating through all the distinct characters in the final polymer and finding the quantities
        // of the most and least common elements
        long max = 0, min = Long.MAX_VALUE;
        for(String s : Arrays.stream(cur.toString().split("")).distinct().toList()) {
            max = Math.max(max, Arrays.stream(cur.toString().split("")).filter(v -> v.equals(s)).count());
            min = Math.min(min, Arrays.stream(cur.toString().split("")).filter(v -> v.equals(s)).count());
        }

        return max-min;
    }

    public static long part2(String template, HashMap<String, String> convertions) {
        // brute force is too slow for 40 iterations, so we need a different approach
        HashMap<String, Long> pairs = new HashMap<>(); // stores the count of every pair of adjacent characters
        long[] count = new long[26]; // stores the count of the individual characters

        // initialising pairs and count with the template
        count[template.charAt(0)-'A']++;
        for(int i = 1; i < template.length(); i++) {       
            count[template.charAt(i)-'A']++;     
            String s = template.substring(i-1, i+1);
            pairs.put( s, pairs.getOrDefault(s, 0L)+1 );
        }

        for(int T = 0; T < 40; T++) {
            HashMap<String, Long> p2 = new HashMap<>();

            for(String pair : pairs.keySet()) {
                // the character that is formed by following the pair insertion rules
                String mid = convertions.get(pair);

                // increasing this characters count by the number of times it will be formed
                count[mid.charAt(0)-'A'] += pairs.get(pair);

                // forming the new pairs from the pair insertion rules
                String a = String.valueOf(pair.charAt(0))+mid;
                String b = mid+String.valueOf(pair.charAt(1));

                // increasing the count of the pairs by the number of times they will be formed
                p2.put(a, p2.getOrDefault(a, 0L)+pairs.get(pair));
                p2.put(b, p2.getOrDefault(b, 0L)+pairs.get(pair));
            }
            
            pairs = p2;
        }

        // the min cannot be 0, because we need to find the least count of an element that actually
        // occurs in the polymer
        long max = Arrays.stream(count).max().orElse(Long.MIN_VALUE);
        long min = Arrays.stream(count).filter(v -> v!=0).min().orElse(Long.MAX_VALUE);
        return (max-min);
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));
        
        String template = "";
        HashMap<String, String> convertions = new HashMap<>();
        while(in.hasNextLine()) {
            String line = in.nextLine();
            if(line.contains(">")) {
                String[] conv = line.split(" -> ");
                convertions.put(conv[0], conv[1]);
            }
            else if(line.length() > 0) {
                template = line;
            }
        }

        System.out.printf("Part 1: %d%n", part1(template, convertions));
        System.out.printf("Part 2: %d%n", part2(template, convertions));

        in.close();
    }
}