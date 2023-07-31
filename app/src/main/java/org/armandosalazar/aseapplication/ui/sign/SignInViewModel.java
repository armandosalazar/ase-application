package org.armandosalazar.aseapplication.ui.sign;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import org.armandosalazar.aseapplication.model.ErrorResponse;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.AuthService;
import org.armandosalazar.aseapplication.network.ErrorHandler;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class SignInViewModel extends ViewModel {

    private static final String TAG = "SignInViewModel";
    Context context;

    public SignInViewModel(Context context) {
        this.context = context;
    }

    public void login(String email, String password) {
        AuthService authService = AuthService.retrofit.create(AuthService.class);
        Disposable disposable = authService.login(new User(email, password)).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(successResponse -> {
            Log.d("SignInViewModel", "Login message: " + successResponse.getMessage());
        }, throwable -> {
            Log.e("SignInViewModel", "Error message: " + throwable.getMessage());
            Log.e("SignInViewModel", "Error cause: " + throwable.getCause());
            if (throwable instanceof HttpException) {
                HttpException exception = (HttpException) throwable;

                ErrorResponse errorResponse = ErrorHandler.parseError(exception.response());

                Log.e("SignInViewModel", "Error message: " + errorResponse.getError());
                Log.e("SignInViewModel", "Error code: " + errorResponse.getCode());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Error");
                builder.setMessage(errorResponse.getError());
                builder.setPositiveButton("OK", null);
                builder.create().show();
            }
        });
    }


}
