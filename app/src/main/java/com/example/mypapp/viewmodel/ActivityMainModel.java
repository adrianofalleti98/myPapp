package com.example.mypapp.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.example.mypapp.model.Movie;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;
import com.example.mypapp.paging.MoviePagingSource;

public class ActivityMainModel extends ViewModel {
    public Flowable<PagingData<Movie>> moviePagingDataFlowable;

    public ActivityMainModel() {
        init();
    }

    private void init()
    {
        MoviePagingSource moviePagingSource = new MoviePagingSource();
        Pager<Integer,Movie> pager = new Pager(new PagingConfig(20,20,false,20,20*499)
                ,()-> moviePagingSource);

        moviePagingDataFlowable = PagingRx.getFlowable(pager);
        CoroutineScope scope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(moviePagingDataFlowable,scope);
    }
}
