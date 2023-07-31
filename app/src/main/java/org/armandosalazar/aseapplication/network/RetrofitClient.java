package org.armandosalazar.aseapplication.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.model.ErrorResponse;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
            .addInterceptor(new ErrorInterceptor())
            .build();
    private static final String BASE_URL = "http://10.0.2.2:3000";
}

class ErrorInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.isSuccessful()) {
            Gson gson = new Gson();
            ErrorResponse errorResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(), ErrorResponse.class);
            Log.e("ErrorInterceptor", "ErrorInterceptor: " + errorResponse.getMessage());
            Log.e("ErrorInterceptor", "ErrorInterceptor: " + errorResponse.getCode());

            ResponseBody responseBody = ResponseBody.create(response.body().contentType(), gson.toJson(errorResponse));

            response = response.newBuilder().body(responseBody).build();
        }
        return response;
    }
}

class NetworkConnectionInterceptor implements Interceptor {


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}