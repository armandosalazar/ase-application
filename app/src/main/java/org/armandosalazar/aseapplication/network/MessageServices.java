package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.Message;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MessageServices {
    @POST("/api/messages")
    Observable<Message> sendMessage(@Header("Authorization") String token, @Body Message message);

    Retrofit retrofit = RetrofitClient.getInstance();
}
