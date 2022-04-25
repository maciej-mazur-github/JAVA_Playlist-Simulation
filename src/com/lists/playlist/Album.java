package com.lists.playlist;

import java.util.ArrayList;
import java.util.Scanner;

class Album {
    private String albumName;
    private String artist;
    private ArrayList<Song> songs;

    public Album(String albumName, String artist) {
        this.albumName = albumName;
        this.artist = artist;
        this.songs = new ArrayList<>();
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtist() {
        return artist;
    }

    public int getAlbumSize() {
        return songs.size();
    }

    public Song getSong(int trackNumber) {
        return songs.get(trackNumber);
    }

    public void showSongs() {
        System.out.println("\t\"" + albumName + "\" - " + artist + ":");
        for(int i = 0; i < songs.size(); i++) {
            System.out.println("\t\t" + songs.get(i).toString());
        }
    }

    public int findSong(String title) {
        int position = -1;

        for(int i = 0; i < songs.size(); i++) {
            if(songs.get(i).getTitle().equals(title)) {
                position = i;
            }
        }

        return position;
    }

    public static Album createNewAlbum(String artistName, String albumName, int numberOfSongs) {
        Album album = new Album(albumName, artistName);
        Scanner scanner = new Scanner(System.in);
        String title;
        double duration;

        for(int i = 0; i < numberOfSongs; i ++) {
            System.out.println("Song " + (i + 1) + "/" + numberOfSongs + ": ");
            System.out.print("\tEnter song title: ");
            title = scanner.nextLine();
            System.out.print("\tEnter song duration");
            if(!scanner.hasNextDouble()) {
                System.out.println("Invalid value entered as duration. Breaking out of album creation process. Try again.");
                return null;
            }
            duration = scanner.nextDouble();
            scanner.nextLine();
            album.songs.add(new Song(title, duration));
        }

        return album;
    }
}














