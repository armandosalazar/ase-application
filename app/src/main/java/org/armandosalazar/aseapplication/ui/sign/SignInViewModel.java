package org.armandosalazar.aseapplication.ui.sign;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.DataManager;
import org.armandosalazar.aseapplication.model.ErrorResponse;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.AuthService;
import org.armandosalazar.aseapplication.network.ErrorHandler;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
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
                                Log.d(TAG, "Authorization: " + token);
                                // Save token in datastore
                                Disposable disposable1 = DataManager.getInstance(context)
                                        .getDataStore()
                                        .updateDataAsync(dataStore -> {
                                            User user = response.body();
                                            // convert user to json
                                            Gson gson = new Gson();
                                            String json = gson.toJson(user);
                                            // save json in datastore
                                            MutablePreferences mutablePreferences = dataStore.toMutablePreferences();
                                            mutablePreferences.set(DataManager.TOKEN_KEY, token);
                                            mutablePreferences.set(DataManager.USER, json);
                                            return Single.just(mutablePreferences);
                                        }).subscribe(
                                                preferences -> {
                                                    Log.d(TAG, "Token & user saved");
                                                    Log.d(TAG, "Preferences: " + preferences.toString());
                                                },
                                                throwable -> Log.e(TAG, "Error saving token: " + throwable.getMessage(), throwable)
                                        );

                                // Read all data from datastore
                                Disposable disposable2 = DataManager.getInstance(context)
                                        .getDataStore()
                                        .data()
                                        .subscribe(
                                                preferences -> {
                                                    Log.d(TAG, "Preferences: " + preferences.toString());
                                                },
                                                throwable -> Log.e(TAG, "Error reading preferences: " + throwable.getMessage(), throwable)
                                        );


                            } else {
                                ErrorResponse errorResponse = ErrorHandler.parseError(Objects.requireNonNull(response.errorBody()).string());
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder
                                        .setTitle("Error")
                                        .setMessage(errorResponse.getMessage())
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton("OK", (dialog, id) -> {
                                            // User clicked OK button
                                        })
                                        .show();
                            }
                        },
                        throwable -> Log.e(TAG, "Error: " + throwable.getMessage(), throwable)
                );
    }


}
