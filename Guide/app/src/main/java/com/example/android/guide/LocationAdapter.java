package com.example.android.guide;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LocationAdapter extends ArrayAdapter {
    private int colorResource;

    public LocationAdapter(Context context, ArrayList<Location> locations, int color)
    {
        super(context,0,locations);
        colorResource = color;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Location currentLocation = (Location)getItem(position);


        TextView nameTextView = (TextView)listItemView.findViewById(R.id.location_name);
        nameTextView.setText(currentLocation.getNameStringId());
        int color = ContextCompat.getColor(getContext(),colorResource);
        nameTextView.setBackgroundColor(color);

        TextView localizationTextView = (TextView)listItemView.findViewById(R.id.location_localization);
        localizationTextView.setText(currentLocation.getLocalizationStringId());
        localizationTextView.setBackgroundColor(color);

        TextView descriptionTextView = (TextView)listItemView.findViewById(R.id.location_description);
        descriptionTextView.setText(currentLocation.getDescriptionStringId());

        ImageView imageView = (ImageView)listItemView.findViewById(R.id.location_picture);
        imageView.setImageResource(currentLocation.getImgResourceId());

        return listItemView;
    }
}
