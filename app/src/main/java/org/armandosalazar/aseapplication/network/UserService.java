package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.User;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    // Send User as Body
    @POST("/api/users")
    Observable<User> create(@Body User user);

    Retrofit retrofit = RetrofitClient.get();
}
