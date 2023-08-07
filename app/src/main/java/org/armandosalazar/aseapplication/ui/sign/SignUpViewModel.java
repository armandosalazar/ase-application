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
                            new AlertDialog.Builder(context)
                                    .setTitle("Success")
                                    .setMessage("User created successfully\nNow you can sign in")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        dialog.dismiss();
                                    })
                                    .show();
                        },
                        error -> {
                            new AlertDialog.Builder(context)
                                    .setTitle("Error")
                                    .setMessage(error.getMessage())
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        dialog.dismiss();
                                    })
                                    .show();
                        }
                );
    }
}
