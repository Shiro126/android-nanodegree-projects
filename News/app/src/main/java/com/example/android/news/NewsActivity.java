package com.example.android.news;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class NewsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = NewsActivity.class.getName();

    private static final String URL_BASE =
            "https://content.guardianapis.com/search?q=poland";
    private static final String API_KEY = "d2a28e79-441e-4c37-8710-035a8a1dc7c8";
    private static final int LOADER_ID = 0;

    private NewsAdapter adapter;

    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ListView newsListView = (ListView) findViewById(R.id.news_list);

        emptyStateTextView = (TextView) findViewById(R.id.no_news_text);
        newsListView.setEmptyView(emptyStateTextView);

        adapter = new NewsAdapter(this, new ArrayList<News> ());

        newsListView.setAdapter(adapter);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        preferences.registerOnSharedPreferenceChangeListener(this);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = adapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.getWebURL());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW,newsUri);

                startActivity(websiteIntent);
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID,null,this);
        } else {
            View progressViev = findViewById(R.id.progress);
            progressViev.setVisibility(View.GONE);

            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }
    private void updateUI(List<News> data){


    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String topics = sharedPreferences.getString(getString(R.string.settings_subject_key),
                getString(R.string.settings_subject_def_value));
        String date = sharedPreferences.getString(getString(R.string.settings_date_key),
                getString(R.string.settings_date_def_value));
        Uri baseUri = Uri.parse(URL_BASE);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        if(!topics.equals(getString(R.string.settings_subject_def_value)))
            uriBuilder.appendQueryParameter("section", topics);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
        Date mDate = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(mDate);
        String dateQueryValue;
        if (date.equals(getString(R.string.settings_last_month_value)))
        {
            calendar.add(Calendar.MONTH,-1);
            dateQueryValue= format.format(calendar.getTime());
            uriBuilder.appendQueryParameter("from-date", dateQueryValue);
        } else if (date.equals(getString(R.string.settings_last_three_months_value)))
        {
            calendar.add(Calendar.MONTH,-3);
            dateQueryValue= format.format(calendar.getTime());
            uriBuilder.appendQueryParameter("from-date", dateQueryValue);
        } else if (date.equals(getString(R.string.settings_last_year_value)))
        {
            calendar.add(Calendar.YEAR,-1);
            dateQueryValue= format.format(calendar.getTime());
            uriBuilder.appendQueryParameter("from-date", dateQueryValue);
        }


        uriBuilder.appendQueryParameter("show-tags","contributor");
        uriBuilder.appendQueryParameter("api-key", API_KEY);
        Log.v("URL",uriBuilder.toString());
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {


        findViewById(R.id.progress).setVisibility(View.GONE);
        emptyStateTextView.setText(R.string.no_news);

        if(data != null && !data.isEmpty()) {
            adapter.addAll(data);
            adapter.notifyDataSetChanged();
            updateUI(data);

        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.settings_subject_key)) || key.equals(getString(R.string.settings_date_key))) {
            adapter.clear();

            emptyStateTextView.setVisibility(View.GONE);

            View loadingIndicator = findViewById(R.id.progress);
            loadingIndicator.setVisibility(View.VISIBLE);

            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings_action) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
