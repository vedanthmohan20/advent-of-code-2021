import java.io.*;
import java.util.*;

public class Main {
    public static long part1(ArrayList<String> lines) {
        long ans = 0;
        for(String line : lines) {
            Stack<Character> stack = new Stack<>();
            
            for(char c : line.toCharArray()) {
                if(c == ')') {
                    if(stack.peek() == '(') stack.pop();
                    else {
                        ans += 3L;
                        break;
                    }
                }
                else if(c == ']') {
                    if(stack.peek() == '[') stack.pop();
                    else {
                        ans += 57L;
                        break;
                    }
                }
               else if(c == '}') {
                    if(stack.peek() == '{') stack.pop();
                    else {
                        ans += 1197L;
                        break;
                    }
                }
                else if(c == '>') {
                    if(stack.peek() == '<') stack.pop();
                    else {
                        ans += 25137L;
                        break;
                    }
                }
                else stack.push(c);
            }
        }

        return ans;
    }

    public static long part2(ArrayList<String> lines) {
        ArrayList<Long> scores = new ArrayList<>();

        for(String line : lines) {
            Stack<Character> stack = new Stack<>();
            boolean incomplete = true;
            
            for(char c : line.toCharArray()) {
                if(c == ')') {
                    if(stack.peek() == '(') stack.pop();
                    else {
                        incomplete = false;
                        break;
                    }
                }
                else if(c == ']') {
                    if(stack.peek() == '[') stack.pop();
                    else {
                        incomplete = false;
                        break;
                    }
                }
               else if(c == '}') {
                    if(stack.peek() == '{') stack.pop();
                    else {
                        incomplete = false;
                        break;
                    }
                }
                else if(c == '>') {
                    if(stack.peek() == '<') stack.pop();
                    else {
                        incomplete = false;
                        break;
                    }
                }
                else stack.push(c);
            }

            if(incomplete) {
                long score = 0;
                while(!stack.empty()) {
                    char c = stack.pop();
                    score *= 5;
                    if(c == '(') score += 1L;
                    else if(c == '[') score += 2L;
                    else if(c == '{') score += 3L;
                    else score += 4L;
                }
                scores.add(score);
            }
        }

        Collections.sort(scores);
        return scores.get(scores.size() / 2);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));

        ArrayList<String> lines = new ArrayList<>();
        while(in.hasNextLine()) {
            String line = in.nextLine();
            lines.add(line);
        }

        System.out.printf("Part 1: %d%n", part1(lines));
        System.out.printf("Part 2: %d%n", part2(lines));

        in.close();
    }
}