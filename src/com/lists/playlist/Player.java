package com.lists.playlist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Player {
    private final LinkedList<Song> playList = new LinkedList<>();
    private final LinkedList<Album> albums = new LinkedList<>();
    private final Scanner scanner = new Scanner(System.in);
    private ListIterator<Song> currentPlaylistPosition;
    boolean goingForward = true;

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
                case 0:
                    chooseTrackToPlay();
                    break;
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
                    addExistingAlbumToPlayer();
                    break;
                case 5:
                    addNewAlbumToPlayer();
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
                    findSongInPlaylist();
                    break;
                case 14:
                    findSongInAlbums();
                    break;
                case 15:
                    quit = true;
                    break;
                case 0:
                    printActions();
                    break;
                default:
                    System.out.println("Invalid choice. Please check out the list of available choice numbers and try again...");
            }
        }
    }

    private void replayCurrentSong() {
        if(playList.isEmpty()) {
            System.out.println("Your playlist is currently empty. Please try adding some songs to it first");
            return;
        }

        ListIterator<Song> iterator;

        if(currentPlaylistPosition == null) {
            iterator = playList.listIterator();
        } else {
            iterator = currentPlaylistPosition;
        }

        if(goingForward) {
            if(iterator.hasPrevious()) { // to check whether iterator is not at the beginning of the list
                System.out.println("Now playing " + iterator.previous());
                goingForward = false;
            } else {       // goingForward set to true and having no previous element can only be possible when no song has been played yet
                System.out.println("No song has been played yet. You can only repeat a song that was played at least once");
            }
        } else {
            System.out.println("Now playing " + iterator.next());  // when goingForward is false that means there is at least one next element, no need to check this then
            goingForward = true;
        }
    }

    private void playPreviousSong() {
        if(playList.isEmpty()) {
            System.out.println("Your playlist is currently empty. Please try adding some songs to it first");
            return;
        }

        ListIterator<Song> iterator;

        if(currentPlaylistPosition == null) {
            iterator = playList.listIterator();
        } else {
            iterator = currentPlaylistPosition;
        }

        if(goingForward)
            if(iterator.hasPrevious()) {
                iterator.previous();  // in case change of direction is required first skip the song played recently
            }


        if(iterator.hasPrevious()) {
            System.out.println("Now playing " + iterator.previous());
            goingForward = false;
            currentPlaylistPosition = iterator;  // saving iterator position to let it be used further by playPreviousSong(), playNextSong() or replayCurrentSong()
        } else {
            System.out.println("You have already reached the beginning of the playlist. You can start moving forward now or pick a specific track number");
        }
    }

    private void playNextSong() {
        if(playList.isEmpty()) {
            System.out.println("Your playlist is currently empty. Please try adding some songs to it first");
            return;
        }

        ListIterator<Song> iterator;

        if(currentPlaylistPosition == null) {
            iterator = playList.listIterator();
        } else {
            iterator = currentPlaylistPosition;
        }

        if(!goingForward)    // goingForward being false implies having at least one next element
            iterator.next();  // in case change of direction is required first skip the song played recently

        if(iterator.hasNext()) {
            System.out.println("Now playing " + iterator.next());
            goingForward = true;
            currentPlaylistPosition = iterator;
        } else {
            System.out.println("You have already reached the end of the playlist. You can start moving backwards now or pick a specific track number");
        }


    }

    private void printActions() {
        System.out.println("Press:");
        System.out.println("1 - to play next song");
        System.out.println("2 - to play previous song");
        System.out.println("3 - to replay current song");
        System.out.println("**********************************************");
        System.out.println("4 - to add a new album to the player");
        System.out.println("5 - to add a specific album to the playlist");
        System.out.println("6 - to add a specific song to the playlist");
        System.out.println("7 - to delete current song from playlist");
        System.out.println("8 - to delete specific song from the playlist");
        System.out.println("9 - to delete album from player");
        System.out.println("10 - to show all songs in the playlist");
        System.out.println("11 - to show all currently available albums");
        System.out.println("12 - to show content of a specific album");
        System.out.println("13 - to find the song in playlist");
        System.out.println("14 - to find the song in albums");
        System.out.println("15 - to quit the program");
        System.out.println("0 - to print available actions");

    }

    private void deleteAlbumFromPlayer() {
        if(!showAllAlbumTitles()) {
            return;
        }

        System.out.print("\nWhich one of them would like to be deleted from the player store? (choose number only) ");

        if(!scanner.hasNextInt()) {
            System.out.println("Invalid value entered. Numbers allowed only. Try again");
            return;
        }

        int choice = scanner.nextInt() - 1;

        if(choice < 0 || choice >= albums.size()) {
            System.out.println("There is no album of number " + (choice + 1) + " in your player store. Try again");
            return;
        }

        String albumToRemove = albums.get(choice).getAlbumName();
        String artistName = albums.get(choice).getArtist();
        albums.remove(choice);
        System.out.println("ALbum \"" + albumToRemove + " by " + artistName + " successfully removed from your player album store");
        showAllAlbumTitles();
    }

    private void addNewAlbumToPlayer() { // no album names duplicates allowed for a specific artist
        System.out.print("Enter artist name: ");
        String artistName = scanner.nextLine();

        System.out.print("Enter album name: ");
        String albumName = scanner.nextLine();

        if(isAlbumNameDuplicated(artistName, albumName)) {
            System.out.println(albumName + " of the artist " + artistName + " already exists in your player store. No duplicates allowed");
            return;
        }

        System.out.print("How many songs are there needed in this album? ");

        if(!scanner.hasNextInt()) {
            System.out.println("Invalid number of songs entered. Please try again to add the new album");
        }
        int numberOfSongs = scanner.nextInt();
        scanner.nextLine();

        Album newAlbum = Album.createNewAlbum(artistName, albumName, numberOfSongs);

        if(newAlbum == null) {
            return;
        } else {
            System.out.println("New album \"" + albumName + "\" has been added to your player store successfully. This is how it looks now:");
            newAlbum.showSongs();
        }



    }

    private boolean isAlbumNameDuplicated(String artistName, String albumName) {
        Album currentAlbum;

        for(int i = 0; i < albums.size(); i++) {
            currentAlbum = albums.get(i);

            if(currentAlbum.getArtist().equals(artistName)) {
                if(currentAlbum.getAlbumName().equals(albumName)) {
                    return true;
                }
            }
        }

        return false;
    }


    private void showAllAlbums() {
        if(albums.isEmpty()) {
            System.out.println("You currently have no albums stored in your player. Please add some first");
            return;
        }

        System.out.println("This is the current status of your album store:");

        for(int i = 0; i < albums.size(); i++) {
            albums.get(i).showSongs();
        }
    }

    private boolean showAllAlbumTitles() {
        if(albums.isEmpty()) {
            System.out.println("You currently have no albums stored in your player. Please add some first");
            return false;
        }

        System.out.println("You currently have below listed albums stored in your player:");

        for(int i = 0; i < albums.size(); i++) {
            System.out.println("\t\"" + (i + 1) + ": " + albums.get(i).getAlbumName() + "\" - " + albums.get(i).getArtist());
        }

        return true;
    }


    private void showSpecificAlbum() {
        if(!showAllAlbumTitles()) {
            return;
        }

        System.out.print("Content of which one would you like to see? Choose the album number: ");

        if(!scanner.hasNextInt()) {
            System.out.println("Invalid value entered (number is required). Please try again.");
            return;
        }

        int choice = scanner.nextInt() - 1;
        scanner.nextLine();

        if(choice < 0 || choice >= albums.size()) {
            System.out.println("There is no album of number " + choice + ". Please try again.");
            return;
        }

        Album chosenAlbum = albums.get(choice);
        System.out.println("This is the content of the chosen album " + chosenAlbum.getAlbumName() + " by " + chosenAlbum.getArtist() + ":");

        chosenAlbum.showSongs();
    }


    private void showSongsInPlaylist() {
        System.out.println("*********************************");
        System.out.println("PLAYLIST:");

        for(int i = 0; i < playList.size(); i++) {
            System.out.println("\t" + (i + 1) + ": " + playList.get(i).toString());
        }
    }

    private void addExistingAlbumToPlayer(int positionChoice, int albumChoice) {    // sub-method for addExistingAlbumToPlayer()
        Album chosenAlbum = albums.get(albumChoice);

        for(int i = chosenAlbum.getAlbumSize() - 1; i >= 0; i++) {  // adding songs in backward order so that they show up in the original order later in the playlist
            playList.add(positionChoice, chosenAlbum.getSong(i));
        }
    }

    private void addExistingAlbumToPlayer() {
        if(albums.isEmpty()) {
            System.out.println("No albums available in your player store. Please add some new ones");
            return;
        }

        System.out.println("Which album would like to fully add to your playlist? Choose one of below listed (enter album number only):");

        for(int i = 0; i < albums.size(); i++) {
            System.out.println("\t" + (i + 1) + ": \"" + albums.get(i).getAlbumName() + "\" - " + albums.get(i).getArtist());
        }

        System.out.print("\nEnter your choice: ");
        int albumChoice;
        
        if(scanner.hasNextInt()) {
            albumChoice = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Invalid album number entered. Please try again");
            return;
        }


        if(albumChoice < 1 || albumChoice > albums.size()) {
            System.out.println("You have entered a wrong album number. Please try again");
            return;
        }

        System.out.println("You chose album number " + albumChoice + " (" + albums.get(albumChoice - 1).getAlbumName());
        System.out.println("\nEnter the position in the playlist at which adding this album should be started." +
                            "\nTo add this album at the beginning of the playlist please enter number 1 or less." +
                            "\nTo add it at the very end of the playlist please enter number " + playList.size() + " or higher." +
                            "\nTo add it somewhere in between choose the number from the range <2, " + (playList.size() - 1) + ">");
        System.out.println("\nThis is the current content of your playlist:");
        showSongsInPlaylist();
        System.out.print("At which position should this album be added then? ");
        int positionChoice = scanner.nextInt() - 1;

        if(positionChoice < 0) {
            positionChoice = 0;
        } else if(positionChoice >= playList.size()) {
            positionChoice = playList.size() - 1;
        }

        addExistingAlbumToPlayer(positionChoice, albumChoice);

        System.out.println("\nThis is your playlist now:");
        showSongsInPlaylist();
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

        System.out.println("\nEnter the position in the playlist at which you would like this song to be added." +
                "\nTo add this song at the beginning of the playlist please enter number 1 or less." +
                "\nTo add it at the very end of the playlist please enter number " + playList.size() + " or higher." +
                "\nTo add it somewhere in between choose the number from the range <2, " + (playList.size() - 1) + ">");
        System.out.println("\nThis is the current content of your playlist:");
        showSongsInPlaylist();
        System.out.print("At which position should this album be added then? ");
        int positionChoice = scanner.nextInt() - 1;

        if(positionChoice < 0) {
            positionChoice = 0;
        } else if(positionChoice >= playList.size()) {
            positionChoice = playList.size() - 1;
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

    private void findSongInPlaylist() {
        if(playList.isEmpty()) {
            System.out.println("Your playlist is empty yet. Please add some songs to it first");
            return;
        }

        System.out.print("What song would you like to search for in the playlist?: ");
        String title = scanner.nextLine();
        ArrayList<Integer> foundPositions = findSongInPlaylist(title);

        if(foundPositions.isEmpty()) {
            System.out.println("There is no " + title + " in your current playlist");
            return;
        }

        System.out.print(title + " is available in your playlist at the positions ");

        for(int i = 0; i < foundPositions.size(); i++) {
            System.out.print((foundPositions.get(i) + 1));

            if(i < (foundPositions.size() - 1)) {   // to avoid putting colon at the end of the printed values
                System.out.print(", ");
            }
        }
    }

    private ArrayList<PairNumberSong> findSongInAlbums(String title) {
        if(albums.isEmpty()) {
            return null;
        }

        ArrayList<PairNumberSong> foundPairs = new ArrayList<>();
        int foundSongPosition;

        for(int i = 0; i < albums.size(); i++) {
            foundSongPosition = albums.get(i).findSong(title);

            if(foundSongPosition >= 0) {
                foundPairs.add(new PairNumberSong(i, foundSongPosition));
            }
        }

        return foundPairs;
    }

    private void findSongInAlbums() {
        if(albums.isEmpty()) {
            System.out.println("Currently you don't have any albums stored in your player. Please try adding some first");
            return;
        }

        System.out.print("What song would you like to search for in your currently stored albums? ");
        String title = scanner.nextLine();
        ArrayList<PairNumberSong> foundSongs = findSongInAlbums(title);

        if(foundSongs.isEmpty()) {
            System.out.println(title + " is non-existent in any of your stored albums");
            return;
        }

        System.out.println(title + " has been present in below listed locations");
        int albumNumber;
        int songPosition;

        for(int i = 0; i < foundSongs.size(); i++) {
            albumNumber = foundSongs.get(i).getAlbumNumber();
            songPosition = foundSongs.get(i).getSongNumberInAlbum();
            System.out.println("\tAlbum: " + albums.get(albumNumber).getAlbumName() + " / Track #: " + songPosition);
        }
    }

    /*private Album findAlbumByArtistName(String artistName) {
        if(albums.isEmpty()) {
            return null;
        }

        for(int i = 0; i < albums.size(); i++) {
            if(albums.get(i).getArtist().equals(artistName)) {
                return albums.get(i);
            }
        }

        return null;
    }*/








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
