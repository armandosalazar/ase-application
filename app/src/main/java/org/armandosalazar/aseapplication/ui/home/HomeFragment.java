package org.armandosalazar.aseapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        viewModel = new HomeViewModel(getContext());
        postsAdapter = new PostsAdapter(getContext());

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