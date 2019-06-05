package com.example.android.music_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //Filling up playlists
        final ArrayList<Playlist> playlists = new ArrayList<Playlist>();
        ArrayList<Song> tempSongs = new ArrayList<Song>();
        tempSongs.add(new Song("Song","Author"));
        tempSongs.add(new Song("Song","Author"));
        tempSongs.add(new Song("Song","Author"));
        tempSongs.add(new Song("Song","Author"));
        Drawable img = getResources().getDrawable(R.drawable.ic_launcher_foreground);
        Drawable img2 = getResources().getDrawable(R.drawable.album_cover);
        Drawable img3 = getResources().getDrawable(R.drawable.ic_launcher_foreground);
        ArrayList<Song> tempSongs2 = new ArrayList<Song>();
        tempSongs2.add(new Song("Other Song","Me"));
        tempSongs2.add(new Song("Believers","Inagine Dragoms"));
        tempSongs2.add(new Song("Loosing My Rebelion","R.E.N."));
        tempSongs2.add(new Song("Swift Shop","Maclomore"));
        tempSongs2.add(new Song("R U Nein","Arctishen Monkeys"));
        tempSongs2.add(new Song("Tester of puppets","Ironica"));
        tempSongs2.add(new Song("Turn up for what?","I don't remember"));
        tempSongs2.add(new Song("Someboy once told me","I know, I know. It's not thr title"));
        playlists.add(new Playlist("Sunny Day",tempSongs,img));
        playlists.add(new Playlist("RandomSongs",tempSongs2,img2));
        playlists.add(new Playlist("Sunny Day 2",tempSongs2,img3));
        playlists.add(new Playlist("Sunny Day",tempSongs,img));
        playlists.add(new Playlist("Sunny Day",tempSongs,img));
        PlaylistAdapter adapter= new PlaylistAdapter(this,playlists);
        GridView gridView = findViewById(R.id.playlist_table);
        gridView.setAdapter(adapter);
        /**
         * event handler for selecting playlist
         */
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Create intent with info from playlists
                Intent openPlaylistIntent = new Intent(MainActivity.this, PlaylistActivity.class);
                Log.v("check","working");
                openPlaylistIntent.putExtra("info", playlists.get(position).getInfo());
                startActivity(openPlaylistIntent);
            }
        });

    }
}
