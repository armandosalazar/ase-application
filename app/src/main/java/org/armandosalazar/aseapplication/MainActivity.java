package org.armandosalazar.aseapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.armandosalazar.aseapplication.databinding.ActivityMainBinding;
import org.armandosalazar.aseapplication.ui.home.HomeFragment;
import org.armandosalazar.aseapplication.ui.location.LocationFragment;
import org.armandosalazar.aseapplication.ui.notifications.NotificationsFragment;
import org.armandosalazar.aseapplication.ui.profile.ProfileFragment;

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
            if (item.getItemId() == R.id.navigation_notifications) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, NotificationsFragment.newInstance()).commit();
                return true;
            }
            if (item.getItemId() == R.id.navigation_profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ProfileFragment.newInstance()).commit();
                return true;
            }
            return false;
        });

        setContentView(binding.getRoot());
    }
}