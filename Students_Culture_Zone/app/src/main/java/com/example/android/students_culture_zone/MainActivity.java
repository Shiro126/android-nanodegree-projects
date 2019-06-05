package com.example.android.students_culture_zone;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter adapter;

    /**Array with addresses of pictures in web*/
    private String[] images =
            {       "http://wroclaw.carpediem.cd/data/afisha/o/83/5e/835ebd3191.jpg",
                    "https://www.pitupitu.pl/files/PSHcXmv16t8nMPIbgLU5-QeyRzA/coverBig",
                    "http://farm4.staticflickr.com/3833/10979897985_3c049f1878_b.jpg"


            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Creating and assigning ViewPagerAdapter item*/
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(MainActivity.this,images);
        viewPager.setAdapter(adapter);
    }
}
