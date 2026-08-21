package com.Lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Naive {

    // Naive Pattern Matching
    public static int naiveSearch(String text, String pattern) {

        int n = text.length();
        int m = pattern.length();
        int count = 0;

        for (int i = 0; i <= n - m; i++) {

            int j = 0;

            while (j < m && text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            if (j == m) {
                count++;
            }
        }

        return count;
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
            System.out.println("Cannot read file: " + file.getName());
        }

        return text.toString();
    }

    public static void main(String[] args) {

        File folder = new File("Dataset");

        File[] files = folder.listFiles(File::isFile);

        if (files == null || files.length == 0) {
            System.out.println("No dataset files found.");
            return;
        }

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter keyword: ");
        String pattern = sc.nextLine().trim().toLowerCase();

        for (File file : files) {

            String text = readFile(file);

            int count = naiveSearch(text, pattern);

            if (count > 0) {

                System.out.println("File Name   : " + file.getName());
                System.out.println("Occurrences : " + count);
                System.out.println("-----------------------------");
            }
        }

        sc.close();
    }
}