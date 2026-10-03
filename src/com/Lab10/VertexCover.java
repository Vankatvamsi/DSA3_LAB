package com.Lab10;

import java.util.Scanner;

public class VertexCover {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        int n = sc.nextInt();

        int[][] graph = new int[n][n];

        System.out.println("Enter adjacency matrix:");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = sc.nextInt();
            }
        }

        boolean[] selected = new boolean[n];

        // 2-Approximation Vertex Cover
        for (int u = 0; u < n; u++) {

            for (int v = u + 1; v < n; v++) {

                if (graph[u][v] == 1 &&
                    !selected[u] &&
                    !selected[v]) {

                    selected[u] = true;
                    selected[v] = true;
                }
            }
        }

        System.out.print("Vertex Cover : ");

        for (int i = 0; i < n; i++) {
            if (selected[i]) {
                System.out.print(i + " ");
            }
        }

        sc.close();
    }
}