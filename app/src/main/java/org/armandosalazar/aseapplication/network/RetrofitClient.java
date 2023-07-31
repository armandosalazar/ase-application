package org.armandosalazar.aseapplication.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitClient {

    public static Retrofit get() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static final OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .build();
    private static final String BASE_URL = "http://10.0.2.2:3000";
}