package com.example.android.students_culture_zone;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Created by szymo on 28.02.2018.
 * class that handles sliding images inside ViewPager
 */

public class ViewPagerAdapter extends PagerAdapter{
    Activity activity;
    String[] images;
    LayoutInflater inflater;

    /**
     * Constructor
     * @param activity - current activity
     * @param images - String array of web addresses of images to show
     */

    public ViewPagerAdapter(Activity activity, String[] images) {
        this.activity = activity;
        this.images = images;
    }

    /**
     *
     * @return number of images to display
     */
    @Override
    public int getCount() {
        return images.length;
    }

    /**
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /**
         * Extracting information about images from XML res file.
         */
        inflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_res,container,false);
        ImageView image;
        image = itemView.findViewById(R.id.picture);
        /**
         * Detecting and setting height and width witch image needs to be.
         */
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        int height = dis.heightPixels;
        int width = dis.widthPixels;
        image.setMinimumHeight(height);
        image.setMinimumWidth(width);

        /**
         * Loading images from web
         */

        try {
            Picasso.with(activity.getApplicationContext())
                    .load(images[position])
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(image);
        }
        catch (Exception ex){

        }

        container.addView(itemView);
        return itemView;
    }

    /**
     * Destructor
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

}
