package org.armandosalazar.aseapplication.network;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfileService {
    @Multipart
    @POST("/profile/picture")
    Call<Void> uploadPicture(@Part MultipartBody.Part picture);

    Retrofit retrofit = RetrofitClient.getInstance();
}
