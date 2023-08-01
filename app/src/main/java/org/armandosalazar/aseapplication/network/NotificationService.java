package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public interface NotificationService {
    @GET("/api/notifications")
    Call<List<Notification>> getNotifications();

    Retrofit retrofit = RetrofitClient.getInstance();
}
