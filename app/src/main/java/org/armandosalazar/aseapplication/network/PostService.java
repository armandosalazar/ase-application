package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PostService {
    @GET("/api/posts")
    Observable<List<Post>> getPosts();

    @POST("/api/posts")
    Observable<Post> createPost(@Header("Authorization") String token, @Body Post post);

    Retrofit retrofit = RetrofitClient.getInstance();
}
