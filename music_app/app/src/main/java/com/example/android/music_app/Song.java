package com.example.android.music_app;

/**
 * Stores informations about single song
 */
public class Song {
    private String title;
    private String author;
    private static int numberOfSongs=0;

    public Song(String title, String author)
    {   numberOfSongs++;
        this.title = title;
        this.author = author;
    }
    public String[] getInfo()
    {
        return new String[]{getTitle(), getAuthor()};
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }


}
