package com.Lab7;

import java.util.Scanner;

public class FordFulkerson {
    static int n;

    // BFS to find an augmenting path
    static boolean bfs(int[][] capacity, int[][] flow,
                       int source, int sink, int[] parent) {
        boolean[] visited = new boolean[n];
        int[] queue = new int[n];
        int front = 0;
        int rear = 0;
        queue[rear++] = source;
        visited[source] = true;
        parent[source] = -1;
        while (front < rear) {
            int u = queue[front++];
            for (int v = 0; v < n; v++) {
                int residual =
                        capacity[u][v] - flow[u][v];
                if (!visited[v] && residual > 0) {
                    queue[rear++] = v;
                    parent[v] = u;
                    visited[v] = true;
                    if (v == sink)
                        return true;
                }
            }
        }
        return false;
    }
    // Ford-Fulkerson Algorithm
    static int fordFulkerson(int[][] capacity,
                             int source,
                             int sink) {
        int[][] flow = new int[n][n];
        int[] parent = new int[n];
        int maxFlow = 0;
        while (bfs(capacity, flow,
                   source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            // Find minimum capacity in path
            for (int v = sink; v != source;
                 v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(
                        pathFlow,
                        capacity[u][v] - flow[u][v]);
            }
            // Update flow
            for (int v = sink; v != source;
                 v = parent[v]) {
                int u = parent[v];
                flow[u][v] += pathFlow;
                flow[v][u] -= pathFlow;
            }
            maxFlow += pathFlow;
        }
        return maxFlow;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of papers: ");
        n = sc.nextInt();
        int[][] capacity = new int[n][n];
        System.out.println(
                "Enter citation capacity matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                capacity[i][j] = sc.nextInt();
            }
        }
        System.out.print("Enter source paper: ");
        int source = sc.nextInt();

        System.out.print("Enter destination paper: ");
        int sink = sc.nextInt();

        int maxFlow =
                fordFulkerson(
                        capacity,
                        source,
                        sink);

        System.out.println(
                "Maximum Citation Flow : "
                + maxFlow);

        sc.close();
    }
}