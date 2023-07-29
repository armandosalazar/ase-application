package org.armandosalazar.aseapplication.network;


import org.armandosalazar.aseapplication.Constants;
import org.armandosalazar.aseapplication.model.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface CommentsService {
    @GET("/api/comments")
    Call<List<Comment>> getComments();

    @GET("/api/comments/{id}")
    Call<Comment> getComment(int id);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
