package com.Lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Practical6_NeedlemanWunsch {

    static int MATCH = 1;
    static int MISMATCH = -1;
    static int GAP = -2;

    // Read DNA sequence from human.txt
    static String readSequence(File file) {

        StringBuilder sequence = new StringBuilder();

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();

                // Ignore FASTA header if present
                if (!line.startsWith(">")) {
                    sequence.append(line);
                }
            }

            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Cannot read human.txt.");
        }

        return sequence.toString().toUpperCase();
    }

    public static void main(String[] args) {

        File file = new File("Dataset/human.txt");

        if (!file.exists()) {
            System.out.println("human.txt not found.");
            return;
        }

        // Read sequence from file
        String a = readSequence(file);

        Scanner sc = new Scanner(System.in);

        System.out.println("Sequence loaded from human.txt");
        System.out.println("Length : " + a.length());

        System.out.print("Enter second DNA sequence: ");
        String b = sc.nextLine().trim().toUpperCase();

        int n = a.length();
        int m = b.length();

        int[][] dp = new int[n + 1][m + 1];

        // Initialize
        for (int i = 0; i <= n; i++)
            dp[i][0] = i * GAP;

        for (int j = 0; j <= m; j++)
            dp[0][j] = j * GAP;

        // Needleman-Wunsch
        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= m; j++) {

                int diagonal;

                if (a.charAt(i - 1) == b.charAt(j - 1))
                    diagonal = dp[i - 1][j - 1] + MATCH;
                else
                    diagonal = dp[i - 1][j - 1] + MISMATCH;

                int up = dp[i - 1][j] + GAP;
                int left = dp[i][j - 1] + GAP;

                dp[i][j] = Math.max(
                        diagonal,
                        Math.max(up, left)
                );
            }
        }

        // Backtracking
        StringBuilder alignA = new StringBuilder();
        StringBuilder alignB = new StringBuilder();

        int i = n;
        int j = m;

        while (i > 0 || j > 0) {

            if (i > 0 && j > 0) {

                int score;

                if (a.charAt(i - 1) == b.charAt(j - 1))
                    score = MATCH;
                else
                    score = MISMATCH;

                if (dp[i][j] ==
                        dp[i - 1][j - 1] + score) {

                    alignA.append(a.charAt(i - 1));
                    alignB.append(b.charAt(j - 1));

                    i--;
                    j--;

                    continue;
                }
            }

            if (i > 0 &&
                    dp[i][j] == dp[i - 1][j] + GAP) {

                alignA.append(a.charAt(i - 1));
                alignB.append('-');

                i--;

            } else {

                alignA.append('-');
                alignB.append(b.charAt(j - 1));

                j--;
            }
        }

        System.out.println();
        System.out.println("Sequence Alignment:");

        System.out.println(
                "Human    : " + alignA.reverse());

        System.out.println(
                "Input    : " + alignB.reverse());

        System.out.println(
                "Alignment Score : " + dp[n][m]);

        sc.close();
    }
}