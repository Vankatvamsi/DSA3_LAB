package com.Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
            System.out.println("Cannot read file: "
                    + file.getName());
        }

        return text.toString();
    }


    // Create suffix-based word sequences
    public static Set<String> createSuffixes(
            String text, int size) {

        String[] words = text.split("\\s+");

        Set<String> suffixes = new HashSet<>();

        for (int i = 0; i <= words.length - size; i++) {

            StringBuilder sequence = new StringBuilder();

            for (int j = 0; j < size; j++) {

                String word = words[i + j]
                        .replaceAll("[^a-zA-Z0-9]", "");

                sequence.append(word);

                if (j < size - 1) {
                    sequence.append(" ");
                }
            }

            suffixes.add(sequence.toString());
        }

        return suffixes;
    }


    // Calculate similarity
    public static double calculateSimilarity(
            Set<String> set1,
            Set<String> set2) {

        if (set1.isEmpty() || set2.isEmpty()) {
            return 0;
        }

        Set<String> common = new HashSet<>(set1);

        common.retainAll(set2);

        Set<String> all = new HashSet<>(set1);
        all.addAll(set2);

        return ((double) common.size()
                / all.size()) * 100;
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
            System.out.println("First document not found.");
            sc.close();
            return;
        }

        if (!file2.exists()) {
            System.out.println("Second document not found.");
            sc.close();
            return;
        }

        System.out.println();
        System.out.println("Reading documents...");

        String text1 = readFile(file1);
        String text2 = readFile(file2);

        /*
         * Number of consecutive words used
         * to create suffix-based sequences.
         */
        int sequenceSize = 3;

        System.out.println("Processing suffix sequences...");

        Set<String> suffixes1 =
                createSuffixes(text1, sequenceSize);

        Set<String> suffixes2 =
                createSuffixes(text2, sequenceSize);

        double similarity =
                calculateSimilarity(
                        suffixes1,
                        suffixes2);

        System.out.println();
        System.out.println("Document 1 : "
                + file1.getName());

        System.out.println("Document 2 : "
                + file2.getName());

        System.out.println("Similarity : "
                + String.format("%.2f", similarity)
                + "%");

        sc.close();
    }
}