package org.armandosalazar.aseapplication.ui.sign;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.DataStore;
import org.armandosalazar.aseapplication.MainActivity;
import org.armandosalazar.aseapplication.model.ErrorResponse;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.AuthService;
import org.armandosalazar.aseapplication.network.ErrorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Headers;

public class SignInViewModel extends ViewModel {

    private static final String TAG = "SignInViewModel";
    Context context;
    private final List<Disposable> disposables = new ArrayList<>();


    public SignInViewModel(Context context) {
        this.context = context;
    }

    public void login(String email, String password) {
        AuthService authService = AuthService.retrofit.create(AuthService.class);

        Disposable disposableAuthService = authService.login(new User(email, password)).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(response -> {
            if (response.isSuccessful()) {
                Headers headers = response.headers();
                String token = headers.get("Authorization");
                // Save token in datastore
                RxDataStore<Preferences> store = DataStore.getInstance(context);
                Disposable disposable = store.updateDataAsync(dataStore -> {
                    User user = response.body();
                    // convert user to json
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    Log.d(TAG, "User: " + json);
                    // save json in datastore
                    MutablePreferences mutablePreferences = dataStore.toMutablePreferences();
                    mutablePreferences.set(DataStore.TOKEN_KEY, token);
                    mutablePreferences.set(DataStore.USER_KEY, json);
                    return Single.just(mutablePreferences);
                }).subscribe(preferences -> {
                    Log.d(TAG, "Token & user saved");
                    Log.i(TAG, "Preferences: " + preferences.toString());
                    context.startActivity(new Intent(context, MainActivity.class));
                }, throwable -> Log.e(TAG, "Error saving token: " + throwable.getMessage(), throwable));

                // Read all data from datastore
                Disposable disposable2 = DataStore.getInstance(context).data().subscribe(preferences -> {
                    Log.i(TAG, "Preferences: " + preferences.toString());
                }, throwable -> Log.e(TAG, "Error reading preferences: " + throwable.getMessage(), throwable));


            } else {
                ErrorResponse errorResponse = ErrorHandler.parseError(Objects.requireNonNull(response.errorBody()).string());

                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage(errorResponse.getMessage())
                        .setPositiveButton("OK", (dialog, id) -> {
                        }).show();
            }
        }, throwable -> {
            Log.e(TAG, "Error: " + throwable.getMessage());
            Log.e(TAG, "Type: " + throwable.getClass().getName());

            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage(throwable.getMessage())
                    .setPositiveButton("OK", (dialog, id) -> {
                    })
                    .show();

        });

        disposables.add(disposableAuthService);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!disposables.isEmpty()) {
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
        }
    }


}
