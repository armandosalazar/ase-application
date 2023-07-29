package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.Constants;
import org.armandosalazar.aseapplication.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface PostsService {
    @GET("/api/posts")
    Call<List<Post>> getPosts();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
