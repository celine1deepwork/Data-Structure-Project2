package com.company;

public class Song {
    private String songName;
    private String artist;
    private int ID;
    private String genre;
    private int year;

    Song(String songName,String artist, int ID, String genre, int year)
    {
        this.songName = songName;
        this.artist = artist;
        this.ID = ID;
        this.genre = genre;
        this.year = year;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtist() {
        return artist;
    }

    public int getID() {
        return ID;
    }
}
