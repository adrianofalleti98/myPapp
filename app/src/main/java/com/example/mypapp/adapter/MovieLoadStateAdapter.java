package com.example.mypapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypapp.R;
import com.example.mypapp.databinding.LoadStateItemBinding;

public class MovieLoadStateAdapter extends LoadStateAdapter<MovieLoadStateAdapter.LoadStateViewHolder> {
    public MovieLoadStateAdapter(View.OnClickListener listener) {
        this.listener = listener;
    }

    //ok
    private View.OnClickListener listener;


    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder loadStateViewHolder, @NonNull LoadState loadState) {
        loadStateViewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull LoadState loadState) {
        return new LoadStateViewHolder(viewGroup,listener);
    }

    public static class LoadStateViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        private TextView tError;

        private Button b;

        public LoadStateViewHolder(@NonNull ViewGroup parent,@NonNull View.OnClickListener retry) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.load_state_item,parent,false));
            LoadStateItemBinding binding = LoadStateItemBinding.bind(this.itemView);
            progressBar = binding.progressBar;
            tError = binding.errorMsg;
            b = binding.retryButton;
            b.setOnClickListener(retry);
        }
        public void bind(LoadState loadState)
        {
            if (loadState instanceof LoadState.Error)
            {
                LoadState.Error errore = (LoadState.Error) loadState;
                tError.setText(errore.getError().getLocalizedMessage());
            }
            progressBar.setVisibility(loadState instanceof LoadState.Loading ? View.VISIBLE: View.GONE);
            b.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
            tError.setText(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
        }
    }
}
