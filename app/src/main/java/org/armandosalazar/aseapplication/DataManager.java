package org.armandosalazar.aseapplication;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

public class DataManager {
    static DataManager instance;
    private final RxDataStore<Preferences> dataStore;

    private DataManager(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, "settings").build();
    }

    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    public RxDataStore<Preferences> getDataStore() {
        return dataStore;
    }
}
