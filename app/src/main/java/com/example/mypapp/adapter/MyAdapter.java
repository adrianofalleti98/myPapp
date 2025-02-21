package com.example.mypapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.mypapp.databinding.SingleMovieItemBinding;
import com.example.mypapp.model.Movie;

public class MyAdapter extends PagingDataAdapter<Movie,MyAdapter.MovieViewHolder> {

    public static final int Loading_item = 0;
    public static final int movie_item = 1;

    private RequestManager glide;
    public MyAdapter(@NonNull DiffUtil.ItemCallback<Movie> diffCallback, RequestManager manager) {
        super(diffCallback);
        glide = manager;
    }

    @NonNull
    @Override
    public MyAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(SingleMovieItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MovieViewHolder holder, int position) {
        Movie m = getItem(position);
        if (m!= null)
        {
            glide.load("https://image.tmdb.org/t/p/w500"+m.getPosterPath()).into(holder.binding.imageViewMovie);
            holder.binding.textViewRating.setText(String.valueOf(m.getVoteAverage()));
        }
    }

    public int getItemViewType(int position){
        return position == getItemCount() ? movie_item:Loading_item;
    }
    public class MovieViewHolder extends RecyclerView.ViewHolder{

        SingleMovieItemBinding binding;
        public MovieViewHolder(@NonNull SingleMovieItemBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
