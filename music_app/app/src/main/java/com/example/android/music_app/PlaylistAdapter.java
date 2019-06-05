package com.example.android.music_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter to show playlists in a grid view
 */

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(Context context, ArrayList<Playlist> playlists) {
        super  (context, 0, playlists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        if(gridItemView == null){
            gridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_item,parent, false);
        }
        Playlist currentPlaylist = getItem(position);
        TextView nameTextView = (TextView) gridItemView.findViewById(R.id.album_name);

        nameTextView.setText(currentPlaylist.getName());

        ImageView albumCoverImage = (ImageView) gridItemView.findViewById(R.id.album_cover);

        albumCoverImage.setImageDrawable(currentPlaylist.getCoverImage());
        return gridItemView;

    }

}
