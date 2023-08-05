package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.FavoritePost;
import org.armandosalazar.aseapplication.model.Post;
import org.armandosalazar.aseapplication.model.SuccessResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostRepository {
    @GET("/api/posts")
    Observable<List<Post>> getPosts(@Header("Authorization") String token);

    @POST("/api/posts")
    Observable<Post> createPost(@Header("Authorization") String token, @Body Post post);

    @POST("/api/posts/favorite")
    Observable<SuccessResponse> favoritePost(@Header("Authorization") String token, @Body FavoritePost favoritePost);

    @DELETE("/api/posts/favorite")
    Observable<SuccessResponse> unfavoritePost(@Header("Authorization") String token, @Query("postId") int postId);

    Retrofit retrofit = RetrofitClient.getInstance();
}
