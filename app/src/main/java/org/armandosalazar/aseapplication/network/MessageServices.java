package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.Message;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageServices {
    // Get messages
    @GET("/api/messages/{receiverId}")
    Observable<List<Message>> getMessages(@Header("Authorization") String token, @Path("receiverId") int receiverId);

    @POST("/api/messages")
    Observable<Message> sendMessage(@Header("Authorization") String token, @Body Message message);

    Retrofit retrofit = RetrofitClient.getInstance();
}
