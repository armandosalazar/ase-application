package org.armandosalazar.aseapplication.ui.profile;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.Preferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.DataStore;
import org.armandosalazar.aseapplication.model.User;

public class ProfileViewModel extends ViewModel {
    private static final String TAG = ProfileViewModel.class.getSimpleName();
    private final String token;
    private final MutableLiveData<User> user = new MutableLiveData<>();

    public ProfileViewModel(Context context) {
        Preferences preferences = DataStore.getInstance(context)
                .data()
                .blockingFirst();
        user.setValue(new Gson().fromJson(preferences.get(DataStore.USER_KEY).toString(), User.class));
        token = preferences.get(DataStore.TOKEN_KEY).toString();
    }

    public LiveData<User> getUser() {
        return user;
    }
}