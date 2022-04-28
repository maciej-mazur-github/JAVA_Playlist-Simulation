package com.lists.playlist;

class Song {
    private String title;
    private String duration;
    public boolean deleted = false;

    public Song(String title, String duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "\"" + title + "\"\t" + duration;
    }

}
