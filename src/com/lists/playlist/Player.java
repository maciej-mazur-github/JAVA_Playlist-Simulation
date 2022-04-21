package com.lists.playlist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Player {
    private final LinkedList<Song> playList = new LinkedList<>();
    private final LinkedList<Album> albums = new LinkedList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void runPlayer() {
        System.out.println("The player has been started.\n\n");
        printActions();
        System.out.println();
        boolean quit = false;
        int choice;

        while (!quit) {
            System.out.print("What action do you choose now?: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    playNextSong();
                    break;
                case 2:
                    playPreviousSong();
                    break;
                case 3:
                    replayCurrentSong();
                    break;
                case 4:
                    addAlbumToPlayer();
                    break;
                case 5:
                    addAlbumToPlaylist();
                    break;
                case 6:
                    addSongToPlaylist();
                    break;
                case 7:
                    deleteCurrentSong();
                    break;
                case 8:
                    deleteSongFromPlaylist();
                    break;
                case 9:
                    deleteAlbumFromPlayer();
                    break;
                case 10:
                    showSongsInPlaylist();
                    break;
                case 11:
                    showAllAlbums();
                    break;
                case 12:
                    showSpecificAlbum();
                    break;
                case 13:
                    printActions();
                    break;
                case 0:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please check out the list of available choice numbers and try again...");
            }
        }
    }

    private void addSongToPlaylist() {   // each song is allowed to show up multiple times in the playlist
        System.out.print("Enter the title: ");
        String title = scanner.nextLine();
        System.out.print("Enter duration: ");
        double duration;

        if(scanner.hasNextDouble()) {
            duration = scanner.nextDouble();
            scanner.nextLine();
        } else {
            System.out.println("Invalid duration value. Please try again...");
            return;
        }

        playList.add(new Song(title, duration));
    }

    private void deleteSongFromPlaylist() {
        System.out.print("Enter the title you need to be removed: ");
        String title = scanner.nextLine();
        ArrayList<Integer> foundPositions = findSongInPlaylist(title);

        if(foundPositions == null) {
            System.out.println("The playlist is empty at the moment so " + title + " could not be deleted. Try adding some songs to the playlist first");
            return;
        }

        if(foundPositions.size() == 0) {
            System.out.println(title + " not found in your playlist");
            return;
        }

        for(int i = 0; i < foundPositions.size(); i++) {
            playList.remove(foundPositions.get(i));
        }
    }

    private ArrayList<Integer> findSongInPlaylist(String title) {
        if(playList.isEmpty()) {
            return null;
        }

        ArrayList<Integer> positions = new ArrayList<>();

        for(int i = 0; i < playList.size(); i++) {
            if(playList.get(i).getTitle().equals(title)) {
                positions.add(i);
            }
        }

        return positions;
    }

    private ArrayList<PairNumberSong> findSongInAlbums(String title) {
        if(albums.isEmpty()) {
            return null;
        }

        ArrayList<PairNumberSong> foundPairs = new ArrayList<>();

        for(int i = 0; i < albums.size(); i++) {
            for(int j = 0; j < albums.get(i).)
        }
    }

    private void printActions() {
        System.out.println("Press:");
        System.out.println("1 - to play next song");
        System.out.println("2 - to play previous song");
        System.out.println("3 - to replay current song");
        System.out.println("**********************************************");
        System.out.println("4 - to add album to player");
        System.out.println("5 - to add a specific album to the playlist");
        System.out.println("6 - to add a specific song to the playlist");
        System.out.println("7 - to delete current song from playlist");
        System.out.println("8 - to delete specific song from the playlist");
        System.out.println("9 - to delete album from player");
        System.out.println("10 - to show all songs in the playlist");
        System.out.println("11 - to show all currently available albums");
        System.out.println("12 - to show content of a specific album");
        System.out.println("13 - to print available actions");
        System.out.println("0 - to quit the program");
    }




    // Inner class for using it further in ArrayList of positions of searched songs (album number and the number of song in a given album)
    // One song can only appear in one album once, however it can re-appear in any other album
    private class PairNumberSong {
        private int albumNumber;
        private int songNumberInAlbum;

        public PairNumberSong(int albumNumber, int songNumberInAlbum) {
            this.albumNumber = albumNumber;
            this.songNumberInAlbum = songNumberInAlbum;
        }

        public int getAlbumNumber() {
            return albumNumber;
        }

        public int getSongNumberInAlbum() {
            return songNumberInAlbum;
        }
    }
}
