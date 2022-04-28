package com.holeng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SongManager songManager = new SongManager();
        String url = "jdbc:sqlite:src\\com\\holeng\\database.db";
        int choice = -1;
        songManager.connectToDatabase(url);
        while(true) {
            System.out.println("\n0.Exit\n1.Create Table\n2.Show All Songs In The Library\n3.Add Song to Library\n4.Delete Any Song");
            System.out.println("5.See The Total Duration Of Library\n6.Search song by name\n7.Show Longest 5 Songs");
            try {
                choice = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (choice == 0) {
                System.out.println("Logged out.");
                break;
            } else if (choice == 1) {
                songManager.createNewTable();
            } else if (choice == 2) {
                songManager.printSongsInDatabase();
            } else if (choice == 3) {
                songManager.addSongToDatabase();
            } else if (choice == 4) {
                songManager.deleteSongByName();
            } else if (choice == 5) {
                songManager.sumOfSongsInLibrary();
            } else if (choice == 6) {
                songManager.searchSongStartsWith();
            } else if (choice == 7) {
                songManager.showLongestFiveSongs();
            } else {
                System.out.println("Invalid Choice");
            }
        }
        songManager.closeTheStream();
        songManager.closeTheConnection();

    }
}
