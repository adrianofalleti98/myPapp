package com.example.mypapp.api;

import static com.example.mypapp.utility.Utils.API_KEY;
import static com.example.mypapp.utility.Utils.BASE_URL;

import com.bumptech.glide.RequestBuilder;
import com.example.mypapp.model.MovieResponse;

import io.reactivex.rxjava3.core.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiClient {
    public interface ApiInterface{
        @GET("movie/popular")
        public Single<MovieResponse> getMoviesByPage(@Query("page") int page);

    }

    static ApiInterface api;

    public static ApiInterface getApi() {
        if (api == null) // lo creo da 0, altrimenti lo ritorno
        {
            //implementazione diversa rispetto a quella vista in MovieApp
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(chain -> {
               Request original = chain.request();
                HttpUrl originalUrl = original.url();
                HttpUrl url = originalUrl.newBuilder().addQueryParameter("api_key",API_KEY).build();
                Request.Builder requestB =  original.newBuilder().url(url);
                Request request = requestB.build();
                return chain.proceed(request);

            });
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
                    build();

            api = retrofit.create(ApiInterface.class);

        }
        return api;
    }
}
