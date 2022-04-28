package com.holeng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

public class SongManager {
    Connection conn = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    void connectToDatabase(String url) {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void printSongsInDatabase() {
        ArrayList<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM SongLibrary";
        Statement st;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Song song = new Song(rs.getString("Name"),rs.getString("Singer"),rs.getString("Albume"),rs.getInt("Duration"));
                songs.add(song);
            }
            for (Song s : songs) {
                int mins = s.getDuration() / 60;
                int secs = s.getDuration() % 60;
                String duration = String.format("%02d:%02d",mins,secs);
                System.out.println("----------------------------------");
                System.out.println("Name: " + s.getName() +"\nSinger: " + s.getSinger() + "\nAlbume: " + s.getAlbume() +"\nDuration: " + duration);
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void addSongToDatabase() {
        try {
            System.out.println("Enter the song name: ");
            String name = reader.readLine();
            System.out.println("Enter the singer: ");
            String singer = reader.readLine();
            System.out.println("Enter the albume name: ");
            String albume = reader.readLine();
            System.out.println("Enter the duration(seconds): ");
            int duration = Integer.parseInt(reader.readLine());

            Song song = new Song(name,singer,albume,duration);

            String sql = "INSERT INTO SongLibrary VALUES(?,?,?,?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1,song.getName());
            ps.setString(2,song.getSinger());
            ps.setString(3, song.getAlbume());
            ps.setInt(4,song.getDuration());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createNewTable() {
        PreparedStatement ps = null;
        String sql = "CREATE TABLE IF NOT EXISTS SongLibrary(Name TEXT, Singer TEXT, Albume TEXT, Duration INT)";
        try {
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void deleteSongByName() {
        String name = "";
        System.out.println("Please enter the song name you want to delete: ");
        try {
            name = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Are you sure about deleting " + name + "\n[Y]es/[N]o");
        try {
            String choice = reader.readLine();
            if (choice.equals("Y") || choice.equals("y")) {
                System.out.println("Deleting " + name + "...");
            } else {
                System.out.println("Deleting cancelled");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sql = "DELETE FROM SongLibrary WHERE Name = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void sumOfSongsInLibrary() {
        String sql = "SELECT SUM(Duration) FROM SongLibrary";
        Statement st = null;
        int sum = 0;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            sum = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int hours = sum / 3600;
        int mins = (sum % 3600) / 60;
        int secs = sum % 60;
        String duration = String.format("%02d:%02d:%02d",hours,mins,secs);
        System.out.println("Total Duration: " + sum + " seconds." + "(" + duration + ")");
    }

    void searchSongStartsWith() {
        try {
            ArrayList<Song> songs = new ArrayList<>();
            System.out.println("Please enter your substring to search the song: ");
            String name = reader.readLine();
            String sql = "SELECT * FROM SongLibrary WHERE Name LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Song song = new Song(rs.getString("Name"),rs.getString("Singer"),rs.getString("Albume"),rs.getInt("Duration"));
                songs.add(song);
            }
            System.out.println("Songs that starts with " + name + ":");
            for (Song s : songs) {
                int mins = s.getDuration() / 60;
                int secs = s.getDuration() % 60;
                String duration = String.format("%02d:%02d",mins,secs);
                System.out.println("----------------------------------");
                System.out.println("Name: " + s.getName() +"\nSinger: " + s.getSinger() + "\nAlbume: " + s.getAlbume() +"\nDuration: " + duration);
                System.out.println("----------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showLongestFiveSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM SongLibrary ORDER BY Duration DESC LIMIT 5";
        Statement st = null;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Song song = new Song(rs.getString("Name"),rs.getString("Singer"),rs.getString("Albume"),rs.getInt("Duration"));
                songs.add(song);
            }
            for (Song s : songs) {
                int mins = s.getDuration() / 60;
                int secs = s.getDuration() % 60;
                String duration = String.format("%02d:%02d",mins,secs);
                System.out.println("----------------------------------");
                System.out.println("Name: " + s.getName() +"\nSinger: " + s.getSinger() + "\nAlbume: " + s.getAlbume() +"\nDuration: " + duration);
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void closeTheStream() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void closeTheConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}