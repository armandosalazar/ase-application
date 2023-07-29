package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.Constants;
import org.armandosalazar.aseapplication.model.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface NotificationsService {
    @GET("/api/notifications")
    Call<List<Notification>> getNotifications();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
