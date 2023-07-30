package org.armandosalazar.aseapplication.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitClient {

    public static Retrofit get() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static final OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .build();
    private static final String BASE_URL = "https://10.0.2.2:3000";
}
