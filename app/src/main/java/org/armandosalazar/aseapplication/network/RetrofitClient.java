package org.armandosalazar.aseapplication.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitClient {
    private static Retrofit retrofit;
    // private static final String BASE_URL = "http://10.0.2.2:3000";
    private static final String BASE_URL = "http://192.168.0.27:3000";
    private static final OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .build();

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static UserService getUserService() {
        return getInstance().create(UserService.class);
    }


}