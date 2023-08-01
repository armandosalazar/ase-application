package org.armandosalazar.aseapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.armandosalazar.aseapplication.adapter.PostsAdapter;
import org.armandosalazar.aseapplication.databinding.FragmentHomeBinding;

import java.util.Collections;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        viewModel = new HomeViewModel(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewPosts.setAdapter(new PostsAdapter(Collections.emptyList()));
        viewModel.getPosts().observe(getViewLifecycleOwner(), posts -> binding.recyclerViewPosts.setAdapter(new PostsAdapter(posts)));

        binding.btnNewPost.setOnClickListener(v -> {
            viewModel.addPost("New post from Android");
        });

        return binding.getRoot();
    }
}