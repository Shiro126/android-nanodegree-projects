package com.example.android.music_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {
    /**
     * Class handles activity single playlist with its songs
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        String[] info =getIntent().getStringArrayExtra("info");

        TextView nameTextView = (TextView)findViewById(R.id.playlist_title);
        nameTextView.setText(info[0]);

        TextView numberOfSongsTextView = (TextView)findViewById(R.id.number_of_songs);
        numberOfSongsTextView.setText(info[1]);


        final ArrayList<Song> songList= new ArrayList<Song>();
        for(int i=2; i<info.length; i+=2)
        {
            songList.add(new Song(info[i],info[i+1]));


        }
        SongAdapter adapter = new SongAdapter(this, songList);



        ListView songListView = (ListView) findViewById(R.id.song_list_view);
        songListView.setAdapter(adapter);

        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent to song activity
                Intent openSongIntent = new Intent(PlaylistActivity.this, SongActivity.class);
                openSongIntent.putExtra("song",songList.get(position).getInfo());
                startActivity(openSongIntent);
            }
        });
    }
}
