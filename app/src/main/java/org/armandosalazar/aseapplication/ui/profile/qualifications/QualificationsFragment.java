package org.armandosalazar.aseapplication.ui.profile.qualifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.armandosalazar.aseapplication.databinding.FragmentQualificationsBinding;

public class QualificationsFragment extends Fragment {
    private FragmentQualificationsBinding binding;

    public static QualificationsFragment newInstance() {
        return new QualificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentQualificationsBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return binding.getRoot();
    }
}