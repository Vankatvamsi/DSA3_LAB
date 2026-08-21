package com.Lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KMP {

    // Create LPS Array
    public static int[] computeLPS(String pattern) {

        int m = pattern.length();

        int[] lps = new int[m];

        int length = 0;
        int i = 1;

        while (i < m) {

            if (pattern.charAt(i) == pattern.charAt(length)) {

                length++;
                lps[i] = length;
                i++;

            } else {

                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // KMP Pattern Matching
    public static int kmpSearch(String text, String pattern) {

        int n = text.length();
        int m = pattern.length();

        int count = 0;

        int[] lps = computeLPS(pattern);

        int i = 0;
        int j = 0;

        while (i < n) {

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {

                count++;

                j = lps[j - 1];

            } else if (i < n &&
                       text.charAt(i) != pattern.charAt(j)) {

                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
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

            int count = kmpSearch(text, pattern);

            if (count > 0) {

                System.out.println("File Name   : " + file.getName());
                System.out.println("Occurrences : " + count);
                System.out.println("-----------------------------");
            }
        }

        sc.close();
    }
}