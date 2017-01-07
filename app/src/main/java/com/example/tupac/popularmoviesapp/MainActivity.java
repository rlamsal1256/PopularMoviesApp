package com.example.tupac.popularmoviesapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private ArrayList<Movie> movieList;

    String sortByString;
    boolean sortByChanged = false;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.parcelable_movies), movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
//        ab.setDisplayHomeAsUpEnabled(true);

        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(movieRecyclerViewAdapter);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        //TODO: put this search query on click listener somewhere
        //TODO: change default to previos maybe

        if (savedInstanceState == null || !savedInstanceState.containsKey(getString(R.string.parcelable_movies))){
            movieList = new ArrayList<>();
            sortByString = getString(R.string.sort_by_most_popular);
            makeMovieSearchQuery(sortByString);
        } else{
            Log.d("inside ", "saved instance state");
            movieList = savedInstanceState.getParcelableArrayList(getString(R.string.parcelable_movies));
            sortByString = getString(R.string.sort_by_most_popular);
            movieRecyclerViewAdapter.setMovieList(movieList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            /*
             * When you click the reset main item, we want to start all over
             * and display the pretty gradient again. There are a few similar
             * ways of doing this, with this one being the simplest of those
             * ways. (in our humble opinion)
             */
            case R.id.action_sort:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Sort Movies By: ")
                        .setSingleChoiceItems(R.array.sortBy, -1,
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i){
                                            case 0:
                                                if (!sortByString.equals(getString(R.string.sort_by_most_popular))){
                                                    sortByChanged = true;
                                                    sortByString = getString(R.string.sort_by_most_popular);
                                                }
                                                break;

                                            case 1:
                                                if (!sortByString.equals(getString(R.string.sort_by_most_rated))){
                                                    sortByChanged = true;
                                                    sortByString = getString(R.string.sort_by_most_rated);
                                                }
                                                break;
                                        }

                                    }
                                })
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                sortByChanged = false;
                                makeMovieSearchQuery(sortByString);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                sortByChanged = false;
                                dialog.dismiss();
                            }
                }).show();


                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method retrieves the sort type from drop down main, constructs the
     * URL (using {@link NetworkUtils}) for the list of movies you'd like to find on that order,
     * and finally fires off an AsyncTask to perform the GET request using
     * our {@link DownloadMoviesTask}
     */
    private void makeMovieSearchQuery(String sortByQuery) {
        URL movieDbSearchUrl = NetworkUtils.buildUrl(sortByQuery);
        new DownloadMoviesTask().execute(movieDbSearchUrl);
    }



    /**
     * This method will make the View for the JSON data visible and
     * hide the error message.
     */
    private void showJsonDataView() {
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the JSON
     * View.
     */
    private void showErrorMessage() {
        // First, hide the currently visible data
        mRecyclerView.setVisibility(View.INVISIBLE);
        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class DownloadMoviesTask extends AsyncTask<URL, Void, MovieResponse> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected MovieResponse doInBackground(URL... urls) {

            //TODO: handle no internet connection
            // http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out

            URL searchUrl = urls[0];
            String movieSearchResults = null;
            MovieResponse movieResponse = new MovieResponse();

            try {
                movieSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

                Gson gson = new GsonBuilder().create();
                movieResponse = gson.fromJson(movieSearchResults, MovieResponse.class);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return movieResponse;
        }

        @Override
        protected void onPostExecute(MovieResponse movieResponse) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (!movieResponse.getMovies().isEmpty()) {
                showJsonDataView();

                movieList = movieResponse.getMovies();
                movieRecyclerViewAdapter.setMovieList(movieList);
            } else {
                showErrorMessage();
            }
        }
    }

}
