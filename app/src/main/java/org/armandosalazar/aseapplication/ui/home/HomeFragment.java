package org.armandosalazar.aseapplication.ui.home;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.armandosalazar.aseapplication.adapter.PostsAdapter;
import org.armandosalazar.aseapplication.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private PostsAdapter postsAdapter;
    private SensorManager sensorManager;
    private Sensor sensor;
    private MediaPlayer mediaPlayer;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        viewModel = new HomeViewModel(getContext());
        postsAdapter = new PostsAdapter(getContext());
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mediaPlayer = MediaPlayer.create(getContext(), org.armandosalazar.aseapplication.R.raw.near);

        if (sensor == null) {
            binding.textProximity.setText("Proximity sensor not available");
        }

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.values[0] < sensor.getMaximumRange()) {
                    binding.textProximity.setText("Near");
                    mediaPlayer.start();
                } else {
                    binding.textProximity.setText("Far");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewPosts.setAdapter(postsAdapter);

        viewModel
                .getPosts()
                .observe(getViewLifecycleOwner(), posts -> postsAdapter.setItems(posts));

        binding.buttonCreatePost.setOnClickListener(view -> {
            if (binding.textInputLayoutPost.getEditText().getText().toString().isEmpty()) {
                binding.textInputLayoutPost.setError("Post cannot be empty");
                return;
            }
            viewModel.createPost(binding.textInputLayoutPost.getEditText().getText().toString());
            binding.textInputLayoutPost.getEditText().setText("");
        });

        return binding.getRoot();
    }
}