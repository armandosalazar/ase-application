package org.armandosalazar.aseapplication.ui.people;

import static io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.armandosalazar.aseapplication.adapter.PeopleAdapter;
import org.armandosalazar.aseapplication.databinding.FragmentPeopleBinding;
import org.armandosalazar.aseapplication.model.Person;
import org.armandosalazar.aseapplication.network.PeopleService;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PeopleFragment extends Fragment {
    private FragmentPeopleBinding binding;
    public static PeopleFragment newInstance() {
        return new PeopleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentPeopleBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding.recyclerViewPeople.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewPeople.setAdapter(new PeopleAdapter(Collections.emptyList()));

        PeopleService peopleService = PeopleService.retrofit.create(PeopleService.class);

        Observable<List<Person>> peopleObservable = peopleService.getPeople();

        Disposable peopleDisposable = peopleObservable
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(
                        people -> {
                            PeopleAdapter adapter = new PeopleAdapter(people);
                            binding.recyclerViewPeople.setAdapter(adapter);
                        },
                        error -> {
                            System.out.println("Error: " + error.getMessage());
                        }
                );

        return binding.getRoot();
    }
}