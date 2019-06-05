package com.example.android.news;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter (Context context, List<News> news) {
        super(context,0,news);
    }

    public View getView(int position, View listItemView, ViewGroup parent) {
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_element, parent, false);
        }

        News currentNews = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title_text_view);
        titleView.setText(currentNews.getTitle());

        TextView sectionView = (TextView) listItemView.findViewById(R.id.section_text_view);
        String sectionName = currentNews.getSectionName();
        sectionView.setText(sectionName);
        switch (sectionName){
            case "Travel":
                sectionView.setTextColor(getContext().getResources().getColor(R.color.travelCategory));
                break;
            case "Football":
                sectionView.setTextColor(getContext().getResources().getColor(R.color.footballCategory));
                break;
            case "World news":
                sectionView.setTextColor(getContext().getResources().getColor(R.color.worldCategory));
                break;
                default :
                    Random random = new Random();
                    sectionView.setTextColor(Color.rgb(random.nextInt(140),random.nextInt(140),random.nextInt(140)));
        }

        TextView authorView = (TextView) listItemView.findViewById(R.id.author_text_view);
        authorView.setText(currentNews.getAuthor());

        TextView dateView = (TextView) listItemView.findViewById(R.id.date_text_view);
        dateView.setText(currentNews.getPublicationDate());

        return listItemView;
    }

}
