package org.armandosalazar.aseapplication.network;


import org.armandosalazar.aseapplication.model.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public interface CommentService {
    @GET("/api/comments")
    Call<List<Comment>> getComments();

    @GET("/api/comments/{id}")
    Call<Comment> getComment(int id);

    Retrofit retrofit = RetrofitClient.getInstance();
}
