package org.armandosalazar.aseapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofiClient {
    public static Retrofit getInstance() {
        return new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
