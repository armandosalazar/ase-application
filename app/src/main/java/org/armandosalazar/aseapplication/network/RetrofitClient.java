package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitClient {

    public static Retrofit get() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static final OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .build();
}
