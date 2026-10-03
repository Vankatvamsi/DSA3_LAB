package com.Lab10;

import java.util.Scanner;

public class JobScheduling {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of jobs: ");
        int n = sc.nextInt();

        int[] deadline = new int[n];
        int[] profit = new int[n];
        int[] job = new int[n];

        System.out.println("Enter deadline and profit:");

        for (int i = 0; i < n; i++) {
            job[i] = i + 1;

            System.out.print("J" + job[i] + ": ");
            deadline[i] = sc.nextInt();
            profit[i] = sc.nextInt();
        }

        // Sort jobs according to profit
        for (int i = 0; i < n - 1; i++) {

            for (int j = i + 1; j < n; j++) {

                if (profit[i] < profit[j]) {

                    int temp = profit[i];
                    profit[i] = profit[j];
                    profit[j] = temp;

                    temp = deadline[i];
                    deadline[i] = deadline[j];
                    deadline[j] = temp;

                    temp = job[i];
                    job[i] = job[j];
                    job[j] = temp;
                }
            }
        }

        // Find maximum deadline
        int maxDeadline = 0;

        for (int i = 0; i < n; i++) {
            if (deadline[i] > maxDeadline) {
                maxDeadline = deadline[i];
            }
        }

        int[] slot = new int[maxDeadline + 1];

        for (int i = 0; i <= maxDeadline; i++) {
            slot[i] = -1;
        }

        int totalProfit = 0;

        // Schedule jobs
        for (int i = 0; i < n; i++) {

            for (int j = deadline[i]; j > 0; j--) {

                if (slot[j] == -1) {

                    slot[j] = job[i];
                    totalProfit += profit[i];

                    break;
                }
            }
        }

        System.out.print("Scheduled Jobs : ");

        for (int i = 1; i <= maxDeadline; i++) {

            if (slot[i] != -1) {
                System.out.print("J" + slot[i] + " ");
            }
        }

        System.out.println();
        System.out.println("Total Profit : " + totalProfit);

        sc.close();
    }
}