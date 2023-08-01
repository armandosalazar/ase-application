package org.armandosalazar.aseapplication.ui.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.armandosalazar.aseapplication.adapter.PeopleAdapter;
import org.armandosalazar.aseapplication.databinding.FragmentPeopleBinding;
import org.armandosalazar.aseapplication.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PeopleFragment extends Fragment {
    private FragmentPeopleBinding binding;
    private PeopleViewModel viewModel;
    public static PeopleFragment newInstance() {
        return new PeopleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentPeopleBinding.inflate(getLayoutInflater());
        viewModel = new PeopleViewModel(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding.recyclerViewPeople.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewPeople.setAdapter(new PeopleAdapter(new ArrayList<>()));

        MutableLiveData<List<User>> observer = viewModel.getUsers();

        observer.observe(getViewLifecycleOwner(), users -> {
            PeopleAdapter adapter = (PeopleAdapter) binding.recyclerViewPeople.getAdapter();
            Objects.requireNonNull(adapter).setUsers(users);
            adapter.notifyDataSetChanged();
        });

        return binding.getRoot();
    }
}