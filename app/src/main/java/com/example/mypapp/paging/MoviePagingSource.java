package com.example.mypapp.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.example.mypapp.api.ApiClient;
import com.example.mypapp.model.Movie;
import com.example.mypapp.model.MovieResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviePagingSource extends RxPagingSource<Integer,Movie> {
    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Movie> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {

            int page = loadParams.getKey() != null ? loadParams.getKey() : 1;
            return ApiClient.getApi().getMoviesByPage(page).subscribeOn(Schedulers.io())
                    .map(MovieResponse::getResults).map(movies -> toLoadResult(movies, page))
                    .onErrorReturn(LoadResult.Error::new);

    }
    private LoadResult<Integer,Movie>  toLoadResult(List<Movie> movies, int page)
    {
        return new LoadResult.Page(movies,page==1 ? null:page-1,page+1);
    }

}

