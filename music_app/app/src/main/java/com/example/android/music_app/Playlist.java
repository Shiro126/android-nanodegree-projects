package com.example.android.music_app;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Playlist  {
    private Drawable coverImage;
    private String name;
    private int numberOfSongs;
    private ArrayList<Song> songList = new ArrayList<Song>();

    public Playlist(String nam)
    {
        name=nam;
        numberOfSongs=0;
    }
    public Playlist(String nam, ArrayList<Song> songsToAdd, Drawable img)
    {
        coverImage = img;
        songList = songsToAdd;
        numberOfSongs=songsToAdd.size();
        name=nam;

    }
    public String [] getInfo()
    {
        int fields = 2*numberOfSongs+2;
        String[] info = new String[fields];
        info[0]=getName();
        info[1]=Integer.toString(getNumberOfSongs());
        for (int i=2;i<fields; i+=2) {
            info[i]=songList.get((i-2)/2).getTitle();
            info[i+1]=songList.get((i-2)/2).getAuthor();
            Log.v("check",info[i]+info[i+1]);
        }
        return info;
    }

    public String getName() {
        return name;
    }

    public void addSong(Song song)
    {
        songList.add(song);
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }

    public Drawable getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Drawable coverImage) {
        this.coverImage = coverImage;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }
}
