package com.example.android.music_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter to fill ListView with Song class items
 */
public class SongAdapter extends ArrayAdapter<Song> {
    public SongAdapter(Context context, ArrayList<Song> songs) {
        super(context,0, songs);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);

        }

        Song currentWord = getItem(position);

        TextView songNameTextView = (TextView) listItemView.findViewById(R.id.song_title);

        songNameTextView.setText(currentWord.getTitle());

        TextView songAuthorTextView = (TextView) listItemView.findViewById(R.id.song_author);

        songAuthorTextView.setText(currentWord.getAuthor());
        return listItemView;
    }

}
