package com.example.tupac.popularmoviesapp;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by tupac on 1/5/2017.
 */

public class NetworkUtils {

    final static String PARAM_API_KEY = "api_key";

    final static String myApiKey = "";

    final static String MOVIE_DB_URL = "http://api.themoviedb.org/3/discover/movie";

    final static String PARAM_SORT = "sort_by";

    /**
     * Builds the URL used to query themoviedb.
     *
     * @param sortByQuery The keyword that will be queried for.
     * @return The URL to use to query the server.
     */
    public static URL buildUrl(String sortByQuery){
        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendQueryParameter(PARAM_SORT, sortByQuery)
                .appendQueryParameter(PARAM_API_KEY, myApiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {

        //TODO: use OkHttp instead
        // http://square.github.io/okhttp/
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}


