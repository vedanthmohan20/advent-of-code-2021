import java.io.*;
import java.util.*;

public class Main {

    public static int part1(HashMap<String, HashSet<String>> map) {
        Stack<String> visited = new Stack<>();
        visited.push("start");

        int[] ans = new int[]{0};
        dfs(map, visited, ans);
        
        return ans[0];
    }

    public static void dfs(HashMap<String, HashSet<String>> map, Stack<String> visited, int[] ans) {
        if(visited.peek().equals("end")) {
            ans[0]++;
        }
        else {
            for(String adj : map.get(visited.peek())) { // for every node thats adjacent to the current node
                if(isSmall(adj)) {
                    if(!visited.contains(adj)) { 
                        visited.push(adj); // since the node is small, we can only visit it if we have not done so earlier
                        dfs(map, visited, ans);
                    }
                }
                else { 
                    visited.push(adj); // since the node is not small, we dont care if we have visited it before
                    dfs(map, visited, ans);
                }
            }
        }
        visited.pop(); // after going through all paths from the current node, we pop it from the stack
    }

    public static int part2(HashMap<String, HashSet<String>> map) {  
        Stack<String> visited = new Stack<>();
        visited.push("start");
        
        int[] ans = new int[]{0};
        dfs(map, visited, ans); // part 1 is a subset of part 2, so all paths that were found in part 1 are valid paths in part 2  

        for(String sp : map.keySet()) {
            // now we add the paths in which a single small node is visited exactly twice (but the start and end nodes can still only
            //                                                                             be visited exactly once)
            if(isSmall(sp) && !sp.equals("start") && !sp.equals("end")) {
                visited.push("start");
                dfs2(map, visited, ans, sp); // adds the number of paths in which node sp is visited exactly twice
            }
        }
        
        return ans[0];
    }

    public static void dfs2(HashMap<String, HashSet<String>> map, Stack<String> visited, int[] ans, String sp) {
        if(visited.peek().equals("end")) {
            if(count(visited, sp) == 2) { // only increments the answer if the required node is visited exactly twice
                ans[0]++;
            }
        }
        else {
            for(String adj : map.get(visited.peek())) {
                if(isSmall(adj)) {
                    if(!visited.contains(adj)) {
                        visited.push(adj);
                        dfs2(map, visited, ans, sp);
                    }
                    else { // unlike in the dfs function, we need to allow the small node sp to get visited twice
                        if(adj.equals(sp) && count(visited, sp)==1) {
                            visited.push(adj);
                            dfs2(map, visited, ans, sp);
                        }
                    }
                }
                else {
                    visited.push(adj);
                    dfs2(map, visited, ans, sp);
                }
            }
        }
        visited.pop();
    }

    public static int count(Stack<String> visited, String sp) {
        int ret = 0;
        
        for(String s : visited) {
            if(s.equals(sp)) {
                ret++;
            }
        }

        return ret;
    }

    public static boolean isSmall(String s) {
        return s.equals(s.toLowerCase());
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));
        
        HashMap<String, HashSet<String>> map = new HashMap<>();
        while(in.hasNextLine()) {
            String[] line = in.nextLine().strip().split("-");

            if(!map.containsKey(line[0])) {
                map.put(line[0], new HashSet<>());
            }
            map.get(line[0]).add(line[1]);

            if(!map.containsKey(line[1])) {
                map.put(line[1], new HashSet<>());
            }
            map.get(line[1]).add(line[0]);
        }

        System.out.printf("Part 1: %d%n", part1(map));
        System.out.printf("Part 2: %d%n", part2(map));
        
        in.close();
    }

}