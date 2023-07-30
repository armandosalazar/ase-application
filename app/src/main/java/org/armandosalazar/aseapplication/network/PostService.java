package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public interface PostService {
    @GET("/api/posts")
    Call<List<Post>> getPosts();

    Retrofit retrofit = RetrofitClient.get();
}
