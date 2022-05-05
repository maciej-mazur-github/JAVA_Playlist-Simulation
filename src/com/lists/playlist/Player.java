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
    private boolean goingForward = true;
    private Song lastPlayedSong;   // its field isDeleted will be used to verify whether the object at which this reference points was recently deleted or not

    public Player() {  // default constructor building initial album store in player
        String album1Name = "The Best Of Guano Apes";
        String artist1 = "Guano Apes";
        Album album1 = new Album(album1Name, artist1);

        album1.addSong(new Song("Break The Line", artist1, album1Name, "4:32"));
        album1.addSong(new Song("Open Your Eyes", artist1, album1Name,"3:50"));
        album1.addSong(new Song("Big In Japan", artist1, album1Name,"4:02"));
        album1.addSong(new Song("Rain", artist1, album1Name,"4:51"));
        album1.addSong(new Song("No Speech", artist1, album1Name,"3:56"));
        album1.addSong(new Song("Living In A Lie", artist1, album1Name,"3:10"));
        album1.addSong(new Song("Lords Of The Boards", artist1, album1Name,"5:10"));
        album1.addSong(new Song("Quietly", artist1, album1Name,"4:15"));
        album1.addSong(new Song("Wash It Down", artist1, album1Name,"3:45"));


        String album2Name = "Greatest Flix";
        String artist2 = "Queen";

        Album album2 = new Album(album2Name, artist2);
        album2.addSong(new Song("Killer Queen", artist2 ,album2Name, "5:40"));
        album2.addSong(new Song("Bohemian Rhapsody", artist2 ,album2Name,"5:25"));
        album2.addSong(new Song("You're My Best Friend", artist2 ,album2Name,"3:57"));
        album2.addSong(new Song("Somebody to Love", artist2 ,album2Name,"4:23"));
        album2.addSong(new Song("Tie Your Mother Down", artist2 ,album2Name,"4:12"));
        album2.addSong(new Song("We Are the Champions", artist2 ,album2Name,"4:34"));
        album2.addSong(new Song("We Will Rock You", artist2 ,album2Name,"3:35"));
        album2.addSong(new Song("We Will Rock You (Live)", artist2 ,album2Name,"4:50"));
        album2.addSong(new Song("Spread Your Wings", artist2 ,album2Name,"5:01"));
        album2.addSong(new Song("Bicycle Race", artist2 ,album2Name,"4:59"));

        albums.add(album1);
        albums.add(album2);
    }

    public void runPlayer() {
        System.out.println("The player has been started.\n\n");
        printActions();
        boolean quit = false;
        int choice;

        while (!quit) {
            System.out.print("\nWhat action do you choose now?: (enter 20 to print all available actions) ");

            if(!scanner.hasNextInt()) {
                System.out.println("Invalid value entered. Integer numbers allowed only. Try again");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

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
                    replayLastSong();
                    break;
                case 4:
                    addExistingAlbumToPlaylist();
                    break;
                case 5:
                    addNewAlbumToPlayerStore();
                    break;
                case 6:
                    addNewSongToPlaylist();
                    break;
                case 7:
                    addSongFromAlbumsToPlaylist();
                    break;
                case 8:
                    deleteLastPlayedSong();
                    break;
                case 9:
                    deleteSongFromPlaylistByName();
                    break;
                case 10:
                    deleteSongFromPlaylistByTrackNumber();
                    break;
                case 11:
                    deleteAlbumFromPlayer();
                    break;
                case 12:
                    showSongsInPlaylist();
                    break;
                case 13:
                    showAllAlbums();
                    break;
                case 14:
                    showAllAlbumTitles();
                    break;
                case 15:
                    showSpecificAlbum();
                    break;
                case 16:
                    findSongInPlaylist();
                    break;
                case 17:
                    findSongInAlbums();
                    break;
                case 18:
                    quit = true;
                    System.out.println("You have decided to quit the program. See you next time");
                    break;
                case 20:
                    printActions();
                    break;
                default:
                    System.out.println("Invalid choice. Please check out the list of available choice numbers and try again...");
            }
        }
    }

    private void addSongFromAlbumsToPlaylist() {
        System.out.println("Albums currently stored:");
        showAllAlbumTitles();
        System.out.print("\nEnter the album number: ");
        int albumNumber = albumNumberChoice();

        if(albumNumber < 0) {
            return;
        }

        Album chosenAlbum = albums.get(albumNumber);
        chosenAlbum.showSongs();
        System.out.print("Enter the song number: ");

        int songNumber = chosenAlbum.songNumberChoice();

        if(songNumber < 0) {
            return;
        }

        Song chosenSong = chosenAlbum.getSong(songNumber);
        int positionInPlaylist = establishSongPositionInPlaylist();

        if(positionInPlaylist < 0) {
            return;
        }

        playList.add(positionInPlaylist, chosenSong);
        System.out.println("\"" + chosenSong.getTitle() + "\" successfully added to the playlist at the position " + (positionInPlaylist + 1));

    }

    private void deleteLastPlayedSong() {
        if(lastPlayedSong == null) {
            System.out.println("No song has been played yet");
            return;
        }

        if(lastPlayedSong.deleted) {
            System.out.println(lastPlayedSong + " has already been deleted. You cannot delete it again");
            return;
        }

        System.out.println("\"" + lastPlayedSong.getTitle() + "\" has been successfully deleted from your playlist");
        lastPlayedSong.deleted = true;
        playList.remove(lastPlayedSong);
    }

    private ListIterator<Song> createIterator() {
        ListIterator<Song> iterator;

        if(currentPlaylistPosition == null) {
            iterator = playList.listIterator();
        } else {
            iterator = currentPlaylistPosition;
        }

        return iterator;
    }

    private ListIterator<Song> createIterator(int index) {
        ListIterator<Song> iterator = playList.listIterator(index);

        return iterator;
    }


    private int playlistTrackNumberChoice() {
        if(playList.isEmpty()) {
            scanner.nextLine();
            return 0;
        }

        if(!scanner.hasNextInt()) {
            System.out.println("Invalid value entered. Numbers allowed only. Try again");
            return - 1;
        }

        int choice = scanner.nextInt() - 1;
        scanner.nextLine();

        if(choice < 0 || choice >= playList.size()) {
            System.out.println("There is no song of number " + (choice + 1) + " in your playlist. Try again");
            return - 1;
        }

        return choice;
    }

    private int albumNumberChoice() {
        if(!scanner.hasNextInt()) {
            System.out.println("Invalid value entered. Numbers allowed only. Try again");
            scanner.nextLine();
            return - 1;
        }

        int choice = scanner.nextInt() - 1;
        scanner.nextLine();

        if(choice < 0 || choice >= albums.size()) {
            System.out.println("There is no album of number " + (choice + 1) + " in your player store. Try again");
            return - 1;
        }

        return choice;
    }


    private void chooseTrackToPlay() {
        if(playList.isEmpty()) {
            System.out.println("Your playlist is currently empty. Please try adding some songs to it first");
            return;
        }

        System.out.println("Your playlist looks like this now:");
        showSongsInPlaylist();
        System.out.print("Which song would you like to play now? (choose number only) ");

        int chosenTrackNumber = playlistTrackNumberChoice();

        if(chosenTrackNumber < 0) {
            return;
        }

        ListIterator<Song> iterator = createIterator(chosenTrackNumber);
        lastPlayedSong = iterator.next();

        System.out.println("Now playing " + lastPlayedSong);  // due to track number being earlier verified by playlistTrackNumberChoice() there will always be at least one next element to play
        goingForward = true;
        currentPlaylistPosition = iterator;
    }


    private void replayLastSong() {
        if(playList.isEmpty()) {
            System.out.println("Your playlist is currently empty. Please try adding some songs to it first");
            return;
        }

        if(lastPlayedSong == null) {
            System.out.println("No song has been played yet. You can only replay a song that was played at least once");
            return;
        } else {
            if(lastPlayedSong.deleted) {
                System.out.println("You can't replay \"" + lastPlayedSong.getTitle() + "\" as it has just been deleted from the playlist");
                return;
            }
        }

        System.out.println("Now playing " + lastPlayedSong);

    }

    private void playPreviousSong() {
        if (playList.isEmpty()) {
            System.out.println("Your playlist is currently empty. Please try adding some songs to it first");
            return;
        }

        ListIterator<Song> iterator = createIterator();

        if (goingForward) {
            if (iterator.hasPrevious()) {
                iterator.previous();  // in case change of direction is required first skip the song played recently
            }
        }

        if (iterator.hasPrevious()) {
            lastPlayedSong = iterator.previous();
            System.out.println("Now playing " + lastPlayedSong);
            goingForward = false;
            currentPlaylistPosition = iterator;  // saving iterator position to let it be used further by playPreviousSong(), playNextSong() or replayLastSong()
        } else {
            System.out.println("You have already reached the beginning of the playlist. You can start moving forward now or pick a specific track number");
            goingForward = true;
        }
    }


    private void playNextSong() {
        if(playList.isEmpty()) {
            System.out.println("Your playlist is currently empty. Please try adding some songs to it first");
            return;
        }

        ListIterator<Song> iterator = createIterator();

        if(!goingForward) { // goingForward being false implies having at least one next element
            if(iterator.hasNext()) {  // in case change of direction is required first skip the song played recently
                iterator.next();
            }
        }

        if(iterator.hasNext()) {
            lastPlayedSong = iterator.next();
            System.out.println("Now playing " + lastPlayedSong);
            goingForward = true;
            currentPlaylistPosition = iterator;
        } else {
            System.out.println("You have already reached the end of the playlist. You can start moving backwards now or pick a specific track number");
            goingForward = false;
        }


    }

    private void printActions() {
        System.out.println("Press:");
        System.out.println("0 - to choose track number to be played from the playlist");
        System.out.println("1 - to play next song in playlist");
        System.out.println("2 - to play previous song in playlist");
        System.out.println("3 - to replay the recently played song in playlist");
        System.out.println("**********************************************");
        System.out.println("4 - to add a specific album from player store to the playlist");
        System.out.println("5 - to create and add a new album to the player");
        System.out.println("6 - to add a song from outside of album store to the playlist");
        System.out.println("7 - to add a specific song from album store to the playlist");
        System.out.println("8 - to delete recently played song from playlist");
        System.out.println("9 - to delete a specific song from the playlist by name");
        System.out.println("10 - to delete a specific song from the playlist by track number");
        System.out.println("11 - to delete specific album from the player store");
        System.out.println("12 - to show all songs in the playlist");
        System.out.println("13 - to show all albums content in the player store");
        System.out.println("14 - to show album titles only in the player store");
        System.out.println("15 - to show the content of a specific album in player store");
        System.out.println("16 - to find a specific song in the playlist");
        System.out.println("17 - to search for a song through all albums");
        System.out.println("18 - to quit the program");
        System.out.println("20 - to print available actions");

    }

    private void deleteAlbumFromPlayer() {
        if(!showAllAlbumTitles()) {
            return;
        }

        System.out.print("\nWhich one of them would like to be deleted from the player store? (choose number only) ");

        int choice = albumNumberChoice();

        if(choice < 0) {
            return;
        }

        String albumToRemove = albums.get(choice).getAlbumName();
        String artistName = albums.get(choice).getArtist();
        albums.remove(choice);
        System.out.println("Album \"" + albumToRemove + " by " + artistName + " successfully removed from your player album store");
        showAllAlbumTitles();
    }

    private void addNewAlbumToPlayerStore() { // no album names duplicates allowed for a specific artist
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
            scanner.nextLine();
            return;
        }

        int numberOfSongs = scanner.nextInt();
        scanner.nextLine();

        Album newAlbum = Album.createNewAlbum(artistName, albumName, numberOfSongs);
        albums.add(newAlbum);

        System.out.println("New album \"" + albumName + "\" has been added to your player store successfully. This is how newly added album looks like now:");
        newAlbum.showSongs();



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
            System.out.println("\t" + (i + 1) + ": \"" + albums.get(i).getAlbumName() + "\" - " + albums.get(i).getArtist());
        }

        return true;
    }


    private void showSpecificAlbum() {
        if(!showAllAlbumTitles()) {
            return;
        }

        System.out.print("Content of which one would you like to see? Choose the album number: ");

        int choice = albumNumberChoice();

        if(choice < 0) {
            return;
        }

        Album chosenAlbum = albums.get(choice);
        System.out.println("This is the content of the chosen album:");

        chosenAlbum.showSongs();
    }


    private void showSongsInPlaylist() {
        if(playList.isEmpty()) {
            System.out.println("The playlist is empty now");
            return;
        }

        System.out.println("*********************************");
        System.out.println("PLAYLIST:");

        for(int i = 0; i < playList.size(); i++) {
            System.out.println("\t" + (i + 1) + ": " + playList.get(i).toString());
        }
    }

    private void addExistingAlbumToPlaylist(int positionChoice, int albumChoice) {    // sub-method for addExistingAlbumToPlayer()
        Album chosenAlbum = albums.get(albumChoice);

        for(int i = chosenAlbum.getAlbumSize() - 1; i >= 0; i--) {  // adding songs in backward order so that they show up in the original order later in the playlist
            playList.add(positionChoice, chosenAlbum.getSong(i));
        }
    }

    private void addExistingAlbumToPlaylist() {
        if(albums.isEmpty()) {
            System.out.println("No albums available in your player store. Please add some new ones");
            return;
        }

        System.out.println("Which album would like to fully add to your playlist? Choose one of below listed (enter album number only):");

        showAllAlbumTitles();

        System.out.print("\nEnter your choice: ");
        int albumChoice = albumNumberChoice();

        if(albumChoice < 0) {
            return;
        }

        System.out.println("You chose album number " + (albumChoice + 1) + " (\"" + albums.get(albumChoice).getAlbumName() + "\")");

        int positionChoice = establishSongPositionInPlaylist();

        addExistingAlbumToPlaylist(positionChoice, albumChoice);

        System.out.println("\"" + albums.get(albumChoice).getAlbumName() + "\" has been successfully added to the playlist at the position " + (positionChoice + 1));
        System.out.println("This is your playlist now:");
        showSongsInPlaylist();
    }

    private int establishSongPositionInPlaylist() {
        int positionChoice;

        if(!playList.isEmpty()) {
            System.out.println("At what position?");
            System.out.println("Entering the number outside of the of the existing playlist track numbers range will result in either prepending or appending the whole album to the playlist");
            System.out.println("\nThis is the current content of your playlist:");
            System.out.println();
            showSongsInPlaylist();
            System.out.println();
            System.out.print("What position do you choose then? ");

            if(!scanner.hasNextInt()) {
                System.out.println("Invalid value entered. Numbers only allowed. Try again");
                scanner.nextLine();
                return -1;
            }

            positionChoice = scanner.nextInt() - 1;
            scanner.nextLine();

            if(positionChoice < 0) {
                positionChoice = 0;
            } else if(positionChoice >= playList.size()) {
                positionChoice = playList.size();
            }
        } else {
            positionChoice = 0;
        }

        return positionChoice;
    }

    private void addNewSongToPlaylist() {   // each song is allowed to show up multiple times in the playlist
        System.out.print("Enter the title: ");
        String title = scanner.nextLine();
        System.out.print("Enter artist name: ");
        String artistName = scanner.nextLine();
        System.out.print("Enter album name: ");
        String albumName = scanner.nextLine();
        System.out.print("Enter duration: ");
        String duration = scanner.nextLine();

        int positionChoice = establishSongPositionInPlaylist();

        if(positionChoice < 0) {
            System.out.println("Invalid position value. Numbers allowed only. Try again");
            scanner.nextLine();
            return;
        }

        Song newSong = new Song(title, artistName, albumName, duration);

        playList.add(positionChoice, newSong);
        System.out.println("\"" + newSong.getTitle() + "\" has been successfully added to the playlist at the position " + (positionChoice + 1));
    }

    private void deleteSongFromPlaylistByName() {
        if(playList.isEmpty()) {
            System.out.println("No songs added to the playlist yet, nothing to delete then");
            return;
        }

        System.out.print("Enter the title you need to be removed: ");
        String title = scanner.nextLine();
        ArrayList<Integer> foundPositions = findSongInPlaylist(title);   // one song can be added multiple times to the playlist

        if(foundPositions == null) {
            System.out.println("The playlist is empty at the moment so " + title + " could not be deleted. Try adding some songs to the playlist first");
            return;
        }

        if(foundPositions.size() == 0) {
            System.out.println("\"" + title + "\" not found in your playlist");
            return;
        }
        int counter = 0;

        for(int i = 0; i < foundPositions.size(); i++) {
            playList.remove((int)foundPositions.get(i));
            counter++;
        }

        System.out.println("\"" + title + "\" has been successfully removed from the playlist. Number of encounters: " + counter);
    }

    private void deleteSongFromPlaylistByTrackNumber() {
        if(playList.isEmpty()) {
            System.out.println("No songs added to the playlist yet, nothing to delete then");
            return;
        }

        System.out.println("This is your current playlist:");
        showSongsInPlaylist();
        System.out.print("Which one of those songs would like to delete? (pick the number only) ");
        int choice = playlistTrackNumberChoice();

        if(choice < 0) {
            return;
        }

        System.out.println("\""+ playList.get(choice).getTitle() + "\" has been successfully deleted from your playlist");
        playList.remove(choice);
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

        System.out.print("What song would you like to search for (by title) in your currently stored albums? ");
        String title = scanner.nextLine();
        ArrayList<PairNumberSong> foundSongs = findSongInAlbums(title);

        if(foundSongs.isEmpty()) {
            System.out.println("\"" + title + "\" is non-existent in any of your stored albums");
            return;
        }

        System.out.println("\"" + title + "\" has been present in below listed locations");   // different albums may contain a song of the same title
        int albumNumber;
        int songPosition;

        for(int i = 0; i < foundSongs.size(); i++) {
            albumNumber = foundSongs.get(i).getAlbumNumber();
            songPosition = foundSongs.get(i).getSongNumberInAlbum();
            System.out.println("\tAlbum: " + albums.get(albumNumber).getAlbumName() + " / Track #: " + songPosition);

            if(askAboutAddingToPlaylist()) {
                int positionInPlaylist = establishSongPositionInPlaylist();

                if(positionInPlaylist < 0) {
                    return;
                }

                Song songAddedToPlaylist = albums.get(albumNumber).getSong(songPosition);
                playList.add(positionInPlaylist, songAddedToPlaylist);
                System.out.println("\t\t\"" + songAddedToPlaylist.getTitle() + "\" successfully added to the playlist at the position " + (positionInPlaylist + 1));
            }
        }
    }

    private boolean askAboutAddingToPlaylist() {
        System.out.print("\t\tDo you want to add this one to the playlist? (Yes or No) ");
        String answer;

        while(true) {
            answer = scanner.nextLine().toLowerCase();

            if(answer.equals("yes")) {
                return true;
            } else if (answer.equals("no")) {
                return false;
            } else {
                System.out.print("\t\tInvalid value. Try again (enter Yes or No): ");
            }
        }

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
