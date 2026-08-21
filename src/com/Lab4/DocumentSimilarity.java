package com.Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DocumentSimilarity {

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


    // Find common substrings using suffix-based processing
    public static int findCommonCharacters(
            String text1, String text2) {

        int n = text1.length();
        int m = text2.length();

        int[][] suffix = new int[n + 1][m + 1];

        int commonCharacters = 0;

        for (int i = n - 1; i >= 0; i--) {

            for (int j = m - 1; j >= 0; j--) {

                if (text1.charAt(i) == text2.charAt(j)) {

                    suffix[i][j] =
                            suffix[i + 1][j + 1] + 1;

                    commonCharacters =
                            Math.max(
                                    commonCharacters,
                                    suffix[i][j]);
                }
            }
        }

        return commonCharacters;
    }


    // Calculate similarity percentage
    public static double calculateSimilarity(
            String text1, String text2) {

        int commonLength =
                findCommonCharacters(text1, text2);

        int smallerLength =
                Math.min(text1.length(), text2.length());

        if (smallerLength == 0) {
            return 0;
        }

        return ((double) commonLength
                / smallerLength) * 100;
    }


    public static void main(String[] args) {

        File folder = new File("Dataset");

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first document name: ");
        String file1Name = sc.nextLine().trim();

        System.out.print("Enter second document name: ");
        String file2Name = sc.nextLine().trim();

        File file1 = new File(folder, file1Name);
        File file2 = new File(folder, file2Name);

        if (!file1.exists()) {

            System.out.println(
                    "First document not found.");

            sc.close();
            return;
        }

        if (!file2.exists()) {

            System.out.println(
                    "Second document not found.");

            sc.close();
            return;
        }

        String text1 = readFile(file1);
        String text2 = readFile(file2);

        double similarity =
                calculateSimilarity(text1, text2);

        System.out.println();
        System.out.println("Document 1 : "
                + file1.getName());

        System.out.println("Document 2 : "
                + file2.getName());

        System.out.println(
                "Similarity : "
                + String.format("%.2f", similarity)
                + "%");

        sc.close();
    }
}