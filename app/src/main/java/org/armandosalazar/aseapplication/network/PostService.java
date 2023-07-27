package org.armandosalazar.aseapplication.network;

import static org.armandosalazar.aseapplication.Constants.BASE_URL;

import org.armandosalazar.aseapplication.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface PostService {
    @GET("/api/posts")
    Call<List<Post>> getPosts();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
