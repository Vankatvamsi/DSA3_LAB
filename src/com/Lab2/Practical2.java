package com.Lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Practical2 {
    public static void main(String[] args) {
        File folder = new File("Dataset");
        File[] files = folder.listFiles(File::isFile);
        if (files == null || files.length == 0) {
            System.out.println("No dataset files found.");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("   QUERY PROCESSING & ARTICLE RETRIEVAL");
        System.out.print("Enter query: ");
        String query = input.nextLine().trim();
        if (query.isEmpty()) {
            System.out.println("Query cannot be empty.");
            input.close();
            return;
        }
        query = query.toLowerCase();
        int matchingArticles = 0;
        int totalOccurrences = 0;
        System.out.println("Searching for: " + query);
        System.out.println("----------------------------------------------");
        for (File file : files) {
            int occurrences = 0;
            try {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine().toLowerCase();
                    int position = 0;
                    while ((position = line.indexOf(query, position)) != -1) {
                        occurrences++;
                        position += query.length();
                    }
                }
                sc.close();
                if (occurrences > 0) {
                    matchingArticles++;
                    totalOccurrences += occurrences;
                    System.out.println("Article     : " + file.getName());
                    System.out.println("Occurrences : " + occurrences);
                    System.out.println("----------------------------------------------");
                }
            } catch (FileNotFoundException e) {
                System.out.println(
                    "Cannot read file: " + file.getName()
                );
            }
        }	
        if (matchingArticles == 0) {
            System.out.println();
            System.out.println("No articles found for the given query.");
        }

    }
}