package com.example.tupac.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends AppCompatActivity {

    public final static String BASE_URL = "http://image.tmdb.org/t/p/";
    public final static String SIZE = "w185";

    public static final String EXTRA_MOVIE = "movie";

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intentThatStartedActivity = getIntent();
        if (intentThatStartedActivity.hasExtra(EXTRA_MOVIE)){
            movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            setToolbar();

            TextView releaseDate = (TextView) findViewById(R.id.release_date);
            releaseDate.setText(movie.getReleaseDate());

            TextView averageVote = (TextView) findViewById(R.id.average_vote);
            String avgVote = String.valueOf(movie.getVoteAverage()) + getString(R.string.out_of_ten);
            averageVote.setText(avgVote);

            TextView moviePlot = (TextView) findViewById(R.id.movie_plot);
            moviePlot.setText(movie.getOverview());
        }



        //TODO: add trailer and user reviews
        //TODO: get them using movie id
        //TODO: favorite button
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(movie.getTitle());


        String movieBackDropPath = movie.getBackDropPath();
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load(BASE_URL + SIZE + movieBackDropPath).fit().into(imageView);
    }
}
