package com.Lab1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Practical1 {
	
	public static void main(String[] args) {

	    File folder = new File("Dataset");
	    System.out.println("Dataset Exists: " + folder.exists());
	    File[] files = folder.listFiles();

	    if (files == null || files.length == 0) {
	        System.out.println("No text files found.");
	        return;
	    }
        System.out.println("Total number of files : " + files.length);
        System.out.println();
        for (File file : files) {
            int characters = 0;
            try {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    characters += line.length();
                }
                System.out.println("File Name : " + file.getName());
                System.out.println("Characters : " + characters);
                System.out.println("-----------------------------");
            } catch (FileNotFoundException e) {
                System.out.println("Cannot read file : " + file.getName());
            }
        }
    }
}