import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));

        List<int[]> list = new ArrayList<>();
        while (in.hasNext()) {
            int[] row = Arrays.stream(in.nextLine().split(""))
                .mapToInt(Integer::valueOf)
                .toArray();
            list.add(row);
        }

        int[][] a = new int[list.size()][list.get(0).length];
        for (int i = 0; i < list.size(); i++) {
            a[i] = list.get(i);
        }
        
        System.out.printf("Part 1: %d%n", part1(a));
        System.out.printf("Part 2: %d%n", part2(a));

        in.close();
    }

    private static int part1(int[][] a) {
        int n = a.length, m = a[0].length;
        List<int[]>[] adj = new ArrayList[n * m];
        for (int i = 0; i < n*m; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < n*m; i++) {
            int row = i / m;
            int column = i % m;

            if (row + 1 < n) 
                adj[i].add(new int[]{(row+1)*m+column, a[row+1][column]});
            if (row - 1 >= 0) 
                adj[i].add(new int[]{(row-1)*m+column, a[row-1][column]});
            if (column + 1 < n) 
                adj[i].add(new int[]{row*m+column+1, a[row][column+1]});
            if (column - 1 >= 0) 
                adj[i].add(new int[]{row*m+column-1, a[row][column-1]});
        }

        Dijkstra d = new Dijkstra(adj);
        d.dijkstra(0);
        return d.getShortestDistanceToNode(n*m-1);
    }

    private static int part2(int[][] b) {
        int[][] a = new int[b.length*5][b[0].length*5];
        for (int i = 0; i < 4*b.length; i++) {
            for (int j = 0; j < 4*b[0].length; j++) {
                if (i < b.length && j < b[0].length) {
                    a[i][j] = b[i][j];
                    a[i+4*b.length][j+4*b[0].length] = a[i][j] + 8 >= 10 ? (a[i][j] + 9) % 10 : a[i][j] + 8;
                }

                a[i + b.length][j] = a[i][j] + 1 == 10 ? 1 : a[i][j] + 1;
                a[i][j + b[0].length] = a[i][j] + 1 == 10 ? 1 : a[i][j] + 1;
            }
        }

        int n = a.length, m = a[0].length;
        List<int[]>[] adj = new ArrayList[n * m];
        for (int i = 0; i < n*m; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < n*m; i++) {
            int row = i / m;
            int column = i % m;

            if (row + 1 < n) 
                adj[i].add(new int[]{(row+1)*m+column, a[row+1][column]});
            if (row - 1 >= 0) 
                adj[i].add(new int[]{(row-1)*m+column, a[row-1][column]});
            if (column + 1 < n) 
                adj[i].add(new int[]{row*m+column+1, a[row][column+1]});
            if (column - 1 >= 0) 
                adj[i].add(new int[]{row*m+column-1, a[row][column-1]});
        }

        Dijkstra d = new Dijkstra(adj);
        d.dijkstra(0);
        return d.getShortestDistanceToNode(n*m-1);
    }
}

class Dijkstra {
    private final int INF = Integer.MAX_VALUE;
    private int numberOfNodes;
    private List<int[]>[] adjacencyList;
    private int[] shortestDistanceToNode;
    private int[] parentOfNode;

    public Dijkstra(List<int[]>[] adjacencyList) {
        this.adjacencyList = adjacencyList;
        this.numberOfNodes = adjacencyList.length;
        this.shortestDistanceToNode = new int[numberOfNodes];
        this.parentOfNode = new int[numberOfNodes];
    }

    public int[] dijkstra(int source) {
        Arrays.fill(shortestDistanceToNode, INF);
        Arrays.fill(parentOfNode, -1);

        PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> Integer.compare(x[1], y[1]));
        for(int node = 0; node < numberOfNodes; node++) {
            if(node == source)
                pq.add(new int[]{node, 0});
            else
                pq.add(new int[]{node, INF});
        }

        shortestDistanceToNode[source] = 0;
        for(int i = 0; i < numberOfNodes; i++) {
            int[] closestNode = pq.poll();

            while(shortestDistanceToNode[closestNode[0]] < closestNode[1])
                closestNode = pq.poll();
            int currentNode = closestNode[0];

            if(shortestDistanceToNode[currentNode] == INF)
                break;

            for(int[] edge : adjacencyList[currentNode]) {
                int adjacentNode = edge[0];
                int edgeWeight = edge[1];
                if(shortestDistanceToNode[currentNode] + edgeWeight < shortestDistanceToNode[adjacentNode]) {
                    parentOfNode[adjacentNode] = currentNode;
                    shortestDistanceToNode[adjacentNode] = shortestDistanceToNode[currentNode] + edgeWeight;

                    pq.add(new int[]{adjacentNode, shortestDistanceToNode[adjacentNode]});
                }
            }
        }

        return shortestDistanceToNode;
    }

    public int getShortestDistanceToNode(int u) {
        return shortestDistanceToNode[u];
    }
}