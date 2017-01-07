package com.example.tupac.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.tupac.popularmoviesapp.MovieDetailActivity.BASE_URL;
import static com.example.tupac.popularmoviesapp.MovieDetailActivity.SIZE;


/**
 * Created by tupac on 1/6/2017.
 */
public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ImageViewHolder> {

    private List<Movie> mMovieList;
    private Context mContext;

    public MovieRecyclerViewAdapter(Context context) {
        mContext = context;
        this.mMovieList = new ArrayList<>();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void setMovieList(List<Movie> movies) {
        if (movies == null) {
            return;
        }
        mMovieList.clear();
        mMovieList.addAll(movies);
        notifyDataSetChanged();
    }

    /**
     * Cache of the children views for a list item.
     */
    class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mMoviePosterView;


        public ImageViewHolder(View itemView) {
            super(itemView);
            mMoviePosterView = (ImageView) itemView.findViewById(R.id.moviePosterImageView);
        }

        public void bind(final int position) {
            String moviePosterPath = mMovieList.get(position).getPosterPath();
            ImageView moviePoster = (ImageView) mMoviePosterView.findViewById(R.id.moviePosterImageView);

            Picasso.with(mContext).load(BASE_URL + SIZE + moviePosterPath).into(moviePoster);

            mMoviePosterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: create movie detail activity
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovieList.get(position));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
