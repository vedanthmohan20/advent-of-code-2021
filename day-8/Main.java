import java.io.*;
import java.util.*;

public class Main {
    public static int part1(ArrayList<String[]> list) {
        int ans = 0;
        for(String[] line : list) {
            for(String s : line) {
                if(s.length()==2 || s.length()==3 || s.length()==4 || s.length()==7) 
                    ans++;
            }
        }
        return ans;
    }

    public static long part2(ArrayList<String[]> map, ArrayList<String[]> list) {
        long ans = 0;

        for(int i = 0; i < map.size(); i++) {
            String[] encodings = new String[10];

            for(String s : map.get(i)) {
                if(s.length()==2) encodings[1] = binec(s);
                if(s.length()==3) encodings[7] = binec(s);
                if(s.length()==4) encodings[4] = binec(s);
                if(s.length()==7) encodings[8] = binec(s);
            }

            for(String s : Arrays.stream(map.get(i)).filter(s -> s.length()==5).toList()) {
                if((Integer.parseInt(encodings[1], 2) & Integer.parseInt(binec(s), 2)) == Integer.parseInt(encodings[1], 2))
                    encodings[3] = binec(s); // 3 => 1 & 3 == 1
                if((Integer.parseInt(encodings[4], 2) | Integer.parseInt(binec(s), 2)) == Integer.parseInt(encodings[8], 2))
                    encodings[2] = binec(s); // 2 => 4 | 2 == 8
            }
            for(String s : Arrays.stream(map.get(i)).filter(s -> s.length()==5).toList()) { 
                if(s.length()==5 && !(binec(s).equals(encodings[2]) || binec(s).equals(encodings[3])))
                    encodings[5] = binec(s); // 5 => length 5 and is not same as 2 or 3
            }

            for(String s : Arrays.stream(map.get(i)).filter(s -> s.length()==6).toList()) {
                if((Integer.parseInt(encodings[5], 2) | Integer.parseInt(binec(s), 2)) == Integer.parseInt(encodings[8], 2))
                    encodings[0] = binec(s); // 0 => 5 | 0 == 8
                if((Integer.parseInt(encodings[1], 2) | Integer.parseInt(binec(s), 2)) == Integer.parseInt(encodings[8], 2))
                    encodings[6] = binec(s); // 6 => 1 | 6 == 8
            }
            for(String s : Arrays.stream(map.get(i)).filter(s -> s.length()==6).toList()) { 
                if(s.length()==6 && !(binec(s).equals(encodings[0]) || binec(s).equals(encodings[6])))
                    encodings[9] = binec(s); // 9 => length 6 and is not same as 0 or 6
            }

            String curNum = "";
            for(String s : list.get(i)) {
                for(int j = 0; j < 10; j++) {
                    if( binec(s).equals(encodings[j]) ) curNum += String.valueOf(j);
                }
            }
            ans += Long.parseLong(curNum);
        }

        return ans;
    }

    public static String binec(String s) {
        char[] ret = new char[7];
        for(int i = 0; i < ret.length; i++) ret[i] = '0';
        for(char c : s.toCharArray()) ret[c-'a'] = '1';
        return new String(ret);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));

        ArrayList<String[]> map = new ArrayList<>();
        ArrayList<String[]> list = new ArrayList<>();
        while(in.hasNextLine()) {
            String str = in.nextLine();
            
            String[] line1 = Arrays.stream(str.substring(0, str.indexOf("|")-1).split(" "))
                                    .map(s -> s.toCharArray())
                                    .map(s -> new String(s))
                                    .toArray(String[]::new);;
            String[] line2 = Arrays.stream(str.substring(str.indexOf("|")+2).split(" "))
                                    .map(s -> s.toCharArray())
                                    .map(s -> new String(s))
                                    .toArray(String[]::new);;

            map.add(line1);
            list.add(line2);
        }

        System.out.printf("Part 1: %d%n", part1(list));
        System.out.printf("Part 2: %d%n", part2(map, list));

        in.close();
    }
}