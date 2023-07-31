package org.armandosalazar.aseapplication.ui.sign;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import org.armandosalazar.aseapplication.model.ErrorResponse;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.ErrorHandler;
import org.armandosalazar.aseapplication.network.UserService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class SignUpViewModel extends ViewModel {
    private static final String TAG = "SignUpViewModel";
    private final UserService userService;
    Context context;

    public SignUpViewModel(Context context) {
        userService = UserService.retrofit.create(UserService.class);
        this.context = context;
    }

    public void signUp(String fullName, String email, String password) {

        Disposable disposable = userService.create(new User(fullName, email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> {
                            Log.d(TAG, "signUp: " + user);
                            // show user data
                            Log.i(TAG, "signUp: " + user.getId());
                            Log.i(TAG, "signUp: " + user.getFullName());
                            Log.i(TAG, "signUp: " + user.getEmail());
                            Log.i(TAG, "signUp: " + user.getPassword());
                            // CreatedAt and UpdatedAt
                            Log.i(TAG, "signUp: " + user.getCreatedAt());
                            Log.i(TAG, "signUp: " + user.getUpdatedAt());

                        },
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException exception = (HttpException) throwable;
                                ErrorResponse errorResponse = ErrorHandler.parseError(exception.response());

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Error");
                                builder.setMessage(errorResponse.getMessage());
                                builder.setPositiveButton("OK", null);
                                builder.create().show();
                            }
                        }
                );
    }
}
