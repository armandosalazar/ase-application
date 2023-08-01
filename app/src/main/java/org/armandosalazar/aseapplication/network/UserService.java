package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @GET("/api/users")
    Observable<List<User>> getUsers(@Header("Authorization") String token);

    @POST("/api/users")
    Observable<User> create(@Body User user);

    Retrofit retrofit = RetrofitClient.getInstance();

}
