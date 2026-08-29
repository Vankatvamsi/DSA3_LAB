package com.Lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EditDistance {

    // Wagner-Fischer Algorithm
    public static int editDistance(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[n + 1][m + 1];
        // Initialize first row
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }
        // Initialize first column
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }
        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (str1.charAt(i - 1) ==
                    str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int insert = dp[i][j - 1];
                    int delete = dp[i - 1][j];
                    int replace = dp[i - 1][j - 1];
                    dp[i][j] = 1 +
                            Math.min(insert,
                            Math.min(delete, replace));
                }
            }
        }
        return dp[n][m];
    }
    // Read file
    public static String readFile(File file) {
        StringBuilder text = new StringBuilder();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                text.append(sc.nextLine().toLowerCase());
                text.append(" ");
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(
                    "Cannot read file: " + file.getName());
        }
        return text.toString();
    }
    // Fuzzy search in a file
    public static int fuzzySearch(
            String text,
            String query,
            int maxDistance) {
        String[] words = text.split("\\s+");
        int bestDistance = Integer.MAX_VALUE;
        for (String word : words) {
            // Remove punctuation
            word = word.replaceAll(
                    "[^a-zA-Z0-9]", "");
            if (word.isEmpty()) {
                continue;
            }
            int distance =
                    editDistance(word, query);
            if (distance < bestDistance) {
                bestDistance = distance;
            }
            if (distance <= maxDistance) {
                return distance;
            }
        }
        return bestDistance;
    }
    public static void main(String[] args) {
        File folder = new File("Dataset");
        File[] files = folder.listFiles(File::isFile);
        if (files == null || files.length == 0) {
            System.out.println("No dataset files found.");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println(
                "       EDIT DISTANCE & FUZZY SEARCH");
        System.out.print("Enter keyword: ");
        String query = sc.nextLine()
                .trim()
                .toLowerCase();
        if (query.isEmpty()) {
            System.out.println(
                    "Keyword cannot be empty.");

            sc.close();
            return;
        }
        System.out.print(
                "Enter maximum edit distance: ");
        int maxDistance = sc.nextInt();
        System.out.println();
        for (File file : files) {
            String text = readFile(file);
            int distance =
                    fuzzySearch(
                            text,
                            query,
                            maxDistance);
            if (distance <= maxDistance) {
                System.out.println(
                        "File Name     : "
                        + file.getName());
                System.out.println(
                        "Edit Distance : "
                        + distance);
                System.out.println(
                        "-----------------------------");
            }
        }
        sc.close();
    }
}