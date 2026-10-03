package com.Lab8;

import java.util.*;

public class EdmondsKarp {
    static int n;
    // BFS to find an augmenting path
    static boolean bfs(int[][] capacity, int[][] flow,
                       int source, int sink, int[] parent) {
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < n; v++) {
                int residual = capacity[u][v] - flow[u][v];
                if (!visited[v] && residual > 0) {
                    parent[v] = u;
                    visited[v] = true;
                    queue.add(v);
                    if (v == sink)
                        return true;
                }
            }
        }
        return false;
    }
    // Edmonds-Karp Algorithm
    static int edmondsKarp(int[][] capacity, int source, int sink) {
        int[][] flow = new int[n][n];
        int[] parent = new int[n];
        int maxFlow = 0;
        while (bfs(capacity, flow, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            // Find minimum capacity in the path
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(
                    pathFlow,
                    capacity[u][v] - flow[u][v]
                );
            }
            // Update flow
            for (int v = sink; v != source; v = parent[v]) {
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
        System.out.print("Enter number of students: ");
        int students = sc.nextInt();
        System.out.print("Enter number of projects: ");
        int projects = sc.nextInt();
        int source = 0;
        int studentStart = 1;
        int projectStart = students + 1;
        int sink = students + projects + 1;
        n = sink + 1;
        int[][] capacity = new int[n][n];
        // Source -> Students
        for (int i = 0; i < students; i++) {
            capacity[source][studentStart + i] = 1;
        }
        // Student -> Project
        System.out.println("Enter student-project matrix:");
        for (int i = 0; i < students; i++) {
            for (int j = 0; j < projects; j++) {
                int value = sc.nextInt();
                if (value == 1) {
                    capacity[studentStart + i]
                            [projectStart + j] = 1;
                }
            }
        }
        // Projects -> Sink
        for (int j = 0; j < projects; j++) {
            capacity[projectStart + j][sink] = 1;
        }
        int maxMatching = edmondsKarp(capacity, source, sink);
        System.out.println("Maximum Bipartite Matching : " + maxMatching);
        sc.close();
    }
}