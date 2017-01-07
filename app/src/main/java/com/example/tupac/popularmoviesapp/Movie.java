package com.example.tupac.popularmoviesapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tupac on 1/5/2017.
 */

public class Movie implements Parcelable{

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("release_date")
    String releaseDate;

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("backdrop_path")
    String backDropPath;

    @SerializedName("vote_average")
    double voteAverage;

    @SerializedName("overview")
    String overview;

    private Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        backDropPath = in.readString();
        voteAverage = in.readDouble();
        overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(posterPath);
        parcel.writeString(backDropPath);
        parcel.writeDouble(voteAverage);
        parcel.writeString(overview);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }
}
