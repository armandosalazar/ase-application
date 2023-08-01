package org.armandosalazar.aseapplication;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

public abstract class DataStore {
    public static final Preferences.Key<? super String> TOKEN_KEY = PreferencesKeys.stringKey("token");
    public static final Preferences.Key<? super String> USER_KEY = PreferencesKeys.stringKey("user");

    private static RxDataStore<Preferences> instance;

    public static RxDataStore<Preferences> getInstance(Context context) {
        if (instance == null) {
            instance = new RxPreferenceDataStoreBuilder(context, "store").build();
        }
        return instance;
    }

}
