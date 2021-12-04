import java.io.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static long part1(ArrayList<Long> order, ArrayList<long[][]> boards) {
        boolean done = false;
        long[][] winner = new long[5][5];
        long last = 0;

        for(long cur : order) {
            for(long[][] board : boards) {

                for(int i = 0; i < 5; i++) {
                    for(int j = 0; j < 5; j++) {
                        if(board[i][j] == cur) board[i][j] = -1;
                    }
                } 

                if(bingo(board)) {
                    done = true;
                    winner = board;
                    last = cur;
                    break;
                }

            }

            if(done) break;
        }

        long unmarkedSum = 0;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(winner[i][j] != -1) unmarkedSum += winner[i][j];
            }
        }

        return unmarkedSum*last;
    }

    public static long part2(ArrayList<Long> order, ArrayList<long[][]> boards) {
        boolean[] winner = new boolean[boards.size()];
        int winnerCount = 0;
        long[][] loserBoard = new long[5][5];
        long last = -1;

        for(long cur : order) {
            int currentBoard = 0;
            for(long[][] board : boards) {
                if(winner[currentBoard]) {
                    currentBoard++;
                    continue;
                }

                for(int i = 0; i < 5; i++) {
                    for(int j = 0; j < 5; j++) {
                        if(board[i][j] == cur) board[i][j] = -1;
                    }
                } 

                if(bingo(board)) {
                    winner[currentBoard] = true;
                    winnerCount++;

                    if(winnerCount == boards.size()) {
                        loserBoard = board;
                        last = cur;
                        break;
                    }
                }

                currentBoard++;
            }

            if(winnerCount == boards.size()) break;
        }

        long unmarkedSum = 0;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(loserBoard[i][j] != -1) unmarkedSum += loserBoard[i][j];
            }
        }

        return unmarkedSum * last;
    }    

    public static boolean bingo(long[][] board) {
        boolean win = false;

        for(int i = 0; i < 5; i++) {
            boolean row = true;
            boolean col = true;

            for(int j = 0; j < 5; j++) {
                if(board[i][j] != -1) row = false;
                if(board[j][i] != -1) col = false;
            }

            if(row || col) {
                win = true;
                break;
            }
        }

        if(win) return true;
        return false;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));

        ArrayList<Long> order = Arrays.stream( in.nextLine().split(",") )
                                        .map(s -> Long.valueOf(s))
                                        .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<long[][]> boards = new ArrayList<>();
        int cur = 0;
        while(in.hasNextLine()) {
            String line = in.nextLine();

            if(line.length() > 0) {                
                long[] vals = Arrays.stream( line.strip().split("\\s+") )
                                    .map(s -> Long.parseLong(s))
                                    .mapToLong(Long::longValue)
                                    .toArray();

                long[][] cb = boards.get(boards.size()-1);
                cb[cur] = vals;
                boards.set(boards.size()-1, cb);

                cur++;
            }
            else {
                boards.add(new long[5][5]);
                cur = 0;
            }
        }
        
        System.out.printf("part 1: %d%n", part1(order, boards));
        System.out.printf("part 2: %d%n", part2(order, boards));

        in.close();
    }
}