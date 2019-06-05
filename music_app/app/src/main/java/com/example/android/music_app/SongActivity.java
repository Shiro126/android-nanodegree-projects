package com.example.android.music_app;

import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SongActivity extends AppCompatActivity {
    /**
     * Single song activity
     */

    boolean playing=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        String []info = getIntent().getExtras().getStringArray("song");

        TextView songTitle= (TextView) findViewById(R.id.activity_song_title);

        TextView songAuthor=(TextView) findViewById(R.id.activity_song_author);

        if (info != null) {
            songTitle.setText(info[0]);
            songAuthor.setText(info[1]);

        }
        else
        {
            songTitle.setText("Failed to load song");
        }
        ImageView btnSkipNext = (ImageView) findViewById(R.id.skip_next_btn);
        ImageView btnPlay = (ImageView) findViewById(R.id.play_button);
        ImageView btnSkipPrev = (ImageView) findViewById(R.id.skip_prev_btn);
        ImageView btnOptions = (ImageView) findViewById(R.id.options_vert);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(!playing)
                {
                    playing=true;
                    ((ImageView)v).setImageDrawable(getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
                }
                else {

                    playing = false;
                    ((ImageView)v).setImageDrawable(getDrawable(R.drawable.ic_play_circle_outline_white_24dp));
                }
            }
        });
        /**
         * Event handlers for clicking song navigation icons
         */
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSkipPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSkipNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
