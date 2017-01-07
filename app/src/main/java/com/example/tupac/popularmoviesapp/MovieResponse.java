package com.example.tupac.popularmoviesapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tupac on 1/5/2017.
 */

public class MovieResponse implements Parcelable{

    @SerializedName("results")
    private ArrayList<Movie> movies;

    protected MovieResponse(Parcel in) {
        movies = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(movies);

    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public MovieResponse() {
        movies = new ArrayList<>();
    }

    public MovieResponse(ArrayList<Movie> movies) {
        this.movies = movies;
    }


}
