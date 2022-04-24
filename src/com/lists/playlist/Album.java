package com.lists.playlist;

import java.util.ArrayList;

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
}
