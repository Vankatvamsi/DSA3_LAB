package com.Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RabinKarp {

    // Rabin-Karp Pattern Matching
    public static int rabinKarpSearch(String text, String pattern) {

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return 0;
        }

        int count = 0;

        int base = 256;
        int prime = 101;

        int patternHash = 0;
        int textHash = 0;
        int highestPower = 1;

        // Calculate highest power of base
        for (int i = 0; i < m - 1; i++) {
            highestPower = (highestPower * base) % prime;
        }

        // Calculate initial hash values
        for (int i = 0; i < m; i++) {

            patternHash =
                    (base * patternHash + pattern.charAt(i)) % prime;

            textHash =
                    (base * textHash + text.charAt(i)) % prime;
        }

        // Slide the pattern over the text
        for (int i = 0; i <= n - m; i++) {

            // If hash values match, compare characters
            if (patternHash == textHash) {

                boolean match = true;

                for (int j = 0; j < m; j++) {

                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    count++;
                }
            }

            // Calculate hash for next window
            if (i < n - m) {

                textHash =
                        (base * (textHash
                        - text.charAt(i) * highestPower)
                        + text.charAt(i + m)) % prime;

                if (textHash < 0) {
                    textHash += prime;
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

            System.out.println(
                    "Cannot read file: " + file.getName());
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

        String pattern = sc.nextLine()
                .trim()
                .toLowerCase();

        if (pattern.isEmpty()) {

            System.out.println("Keyword cannot be empty.");
            sc.close();
            return;
        }

        for (File file : files) {

            String text = readFile(file);

            int count = rabinKarpSearch(text, pattern);

            if (count > 0) {

                System.out.println(
                        "File Name   : " + file.getName());

                System.out.println(
                        "Occurrences : " + count);

                System.out.println(
                        "-----------------------------");
            }
        }

        sc.close();
    }
}