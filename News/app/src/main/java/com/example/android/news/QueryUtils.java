package com.example.android.news;

import android.app.DownloadManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }

    public static List<News> fetchNewsData(String requestUrl) {

        URL url = createURL(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "HTTP request faulty", e);
        }
        List<News> news = extractFeatureFromJson(jsonResponse);

        return news;
    }

    private static URL createURL(String stringURL){
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG,"Malfunctioning url", e);
        }
        return url;
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(11000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode()==200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
            }
        } catch ( IOException e) {
            Log.e(LOG_TAG, "Problem with JSON results", e);
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String newsJSON) {

        if(TextUtils.isEmpty(newsJSON))
            return null;

        List<News> news = new ArrayList<>();


        try {

            JSONObject jsonResponse = new JSONObject(newsJSON);
            JSONObject serverResponse = jsonResponse.getJSONObject("response");
            JSONArray newsArray = serverResponse.getJSONArray("results");
            JSONArray tagsArray;
            String author = "";
            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentNews =newsArray.getJSONObject(i);
                tagsArray=currentNews.getJSONArray("tags");
                if(!tagsArray.isNull(0))
                    author = tagsArray.getJSONObject(0).getString("webTitle");
                else
                    author = "";

                news.add(new News(
                        currentNews.getString("webTitle"),
                        currentNews.getString("sectionName"),
                        author,
                        currentNews.getString("webPublicationDate").substring(0,10),
                        currentNews.getString("webUrl"))
                );
            }
        }catch (JSONException e) {
            Log.e(LOG_TAG, "Problem with JSON result", e);
        }
        return news;
    }
}
