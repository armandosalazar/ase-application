package org.armandosalazar.aseapplication.ui.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.databinding.FragmentMapBinding;

import java.util.Optional;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;
    FragmentMapBinding binding;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentMapBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Optional<SupportMapFragment> mapFragment = Optional
                .ofNullable((SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map));

        mapFragment.ifPresent(fragment -> {
            fragment.getMapAsync(this);
            Log.d("MapFragment", "Map ready");
        });
        return binding.getRoot();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == 0) {
            Log.e("MapFragment", "Permission granted");
            googleMap.setMyLocationEnabled(true);
            // get location
        } else {
            Log.e("MapFragment", "Permission not granted");
        }
    }

}