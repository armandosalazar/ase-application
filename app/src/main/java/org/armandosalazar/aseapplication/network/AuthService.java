package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.User;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/api/auth")
    Observable<Response<User>> login(@Body User user);

    Retrofit retrofit = RetrofitClient.getInstance();
}
