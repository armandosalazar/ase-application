package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public interface PostService {
    @GET("/api/posts")
    Observable<List<Post>> getPosts();

    Retrofit retrofit = RetrofitClient.getInstance();
}
