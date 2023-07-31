package org.armandosalazar.aseapplication.ui.sign;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.model.ErrorResponse;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.AuthService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Headers;

public class SignInViewModel extends ViewModel {

    private static final String TAG = "SignInViewModel";
    Context context;

    public SignInViewModel(Context context) {
        this.context = context;
    }

    public void login(String email, String password) {
        AuthService authService = AuthService.retrofit.create(AuthService.class);

        Disposable disposable = authService.login(new User(email, password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            if (response.isSuccessful()) {
                                Headers headers = response.headers();
                                String token = headers.get("Authorization");
                                Log.d(TAG, "Success: " + token);
                            } else {
                                // TODO: !Important - Handle error
                                /**
                                 * response.errorBody() is only readable once. If you try to read it again, you will get an IllegalStateException.
                                 */
                                Gson gson = new Gson();
                                ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);

                                Log.e(TAG, "Error: " + errorResponse.getMessage());
                                Log.e(TAG, "Code: " + errorResponse.getCode());
                            }
                        },
                        throwable -> Log.e(TAG, "Error: " + throwable.getMessage(), throwable)
                );
    }


}
