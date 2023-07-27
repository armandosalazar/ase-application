package org.armandosalazar.aseapplication.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.armandosalazar.aseapplication.adapter.PostsAdapter;
import org.armandosalazar.aseapplication.databinding.FragmentHomeBinding;
import org.armandosalazar.aseapplication.model.Post;
import org.armandosalazar.aseapplication.network.PostService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        PostService postService = PostService.retrofit.create(PostService.class);

        Call<List<Post>> call = postService.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.i("PostService", "Successfully retrieved posts");
                binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewPosts.setAdapter(new PostsAdapter(response.body()));
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("PostService", "Failed to retrieve posts");
                Log.e("PostService", t.getMessage());
            }
        });

        return binding.getRoot();
    }
}