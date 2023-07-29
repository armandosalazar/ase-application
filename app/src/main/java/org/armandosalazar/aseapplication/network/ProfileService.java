package org.armandosalazar.aseapplication.network;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfileService {
    @POST("/profile/picture")
    Call<Void> uploadFile(@Part MultipartBody body);

    Retrofit retrofit = RetrofitClient.get();
}
