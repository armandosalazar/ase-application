package org.armandosalazar.aseapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.PreferencesKeys;

import org.armandosalazar.aseapplication.databinding.ActivityMainBinding;
import org.armandosalazar.aseapplication.ui.chat.ChatFragment;
import org.armandosalazar.aseapplication.ui.home.HomeFragment;
import org.armandosalazar.aseapplication.ui.location.LocationFragment;
import org.armandosalazar.aseapplication.ui.notification.NotificationFragment;
import org.armandosalazar.aseapplication.ui.profile.ProfileFragment;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, HomeFragment.newInstance()).commit();

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, HomeFragment.newInstance()).commit();
                return true;
            }
            if (item.getItemId() == R.id.navigation_location) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, LocationFragment.newInstance()).commit();
                return true;
            }
            if (item.getItemId() == R.id.navigation_chat) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ChatFragment.newInstance()).commit();
                return true;
            }
            if (item.getItemId() == R.id.navigation_notifications) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, NotificationFragment.newInstance()).commit();
                return true;
            }
            if (item.getItemId() == R.id.navigation_profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ProfileFragment.newInstance()).commit();
                return true;
            }
            return false;
        });

        // Using the DataManager class
        DataManager.getInstance(this).getDataStore().updateDataAsync(dataStore -> {
            MutablePreferences mutablePreferences = dataStore.toMutablePreferences();
            mutablePreferences.set(PreferencesKeys.booleanKey("dark_mode"), true);
            return Single.just(mutablePreferences);
        });
        // read data
        Flowable<Boolean> darkMode = DataManager.getInstance(this).getDataStore().data().map(preferences -> preferences.get(PreferencesKeys.booleanKey("dark_mode")));
        Log.e("darkMode", "DARK MODE: " + darkMode.blockingFirst());

        setContentView(binding.getRoot());
    }
}