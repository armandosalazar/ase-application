package org.armandosalazar.aseapplication.ui.location;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.databinding.FragmentLocationBinding;

import java.util.Optional;

public class LocationFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;
    FragmentLocationBinding binding;

    public static LocationFragment newInstance() {
        return new LocationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentLocationBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Optional<SupportMapFragment> mapFragment = Optional.ofNullable((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));

        mapFragment.ifPresent(fragment -> {
            fragment.getMapAsync(this);
            Log.d("LocationFragment", "Map ready");
        });
        return binding.getRoot();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng location = new LatLng(40.4165000, -3.7025600);
        this.googleMap.addMarker(new com.google.android.gms.maps.model.MarkerOptions().position(location).title("Madrid"));
        this.googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(location, 10));
    }
}