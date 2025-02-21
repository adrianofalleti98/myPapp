package com.example.mypapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.example.mypapp.adapter.MovieLoadStateAdapter;
import com.example.mypapp.adapter.MyAdapter;
import com.example.mypapp.databinding.ActivityMainBinding;
import com.example.mypapp.utility.GridSpace;
import com.example.mypapp.utility.MovieComparator;
import com.example.mypapp.utility.Utils;
import com.example.mypapp.viewmodel.ActivityMainModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    ActivityMainModel vm;
    ActivityMainBinding binding;

    MyAdapter adapter;

    @Inject //viene inizializzato da dependency injection
    RequestManager rm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Utils.API_KEY == null || Utils.API_KEY.isEmpty())
        {
            Toast.makeText(this, "Error API KEY", Toast.LENGTH_SHORT).show();
        }
        adapter = new MyAdapter(new MovieComparator(),rm);
        vm = new ViewModelProvider(this).get(ActivityMainModel.class);
        initRcv();
        vm.moviePagingDataFlowable.subscribe
                (moviePagingData ->
                        adapter.submitData(getLifecycle(),moviePagingData));

    }

    private void initRcv()
    {
        GridLayoutManager grid = new GridLayoutManager(this,2);
        binding.recyclerView.setLayoutManager(grid);
        binding.recyclerView.addItemDecoration(new GridSpace(2,20,true));
        binding.recyclerView.setAdapter(adapter.withLoadStateFooter(new MovieLoadStateAdapter(view->adapter.retry())));
        grid.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == MyAdapter.Loading_item ? 1:2;
            }
        });
    }
}