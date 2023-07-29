package org.armandosalazar.aseapplication.ui.profile.qualifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.armandosalazar.aseapplication.adapter.CommentsAdapter;
import org.armandosalazar.aseapplication.databinding.FragmentQualificationsBinding;
import org.armandosalazar.aseapplication.model.Comment;
import org.armandosalazar.aseapplication.network.CommentsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QualificationsFragment extends Fragment {
    private FragmentQualificationsBinding binding;

    public static QualificationsFragment newInstance() {
        return new QualificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQualificationsBinding.inflate(inflater, container, false);
        binding.recyclerViewQualifications.setLayoutManager(new LinearLayoutManager(getContext()));

        CommentsService commentsService = CommentsService.retrofit.create(CommentsService.class);

        Call<List<Comment>> call = commentsService.getComments();
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                binding.recyclerViewQualifications.setAdapter(new CommentsAdapter(response.body()));
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });

        return binding.getRoot();
    }
}