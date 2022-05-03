package com.lists.playlist;

class Song {
    private String title;
    private String artist;
    private String albumName;
    private String duration;
    public boolean deleted = false;

    public Song(String title, String artist, String albumName, String duration) {
        this.title = title;
        this.artist = artist;
        this.albumName = albumName;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "\"" + title + "\" (" + duration + ") from album \"" + albumName + "\" by " + artist;
    }

}
