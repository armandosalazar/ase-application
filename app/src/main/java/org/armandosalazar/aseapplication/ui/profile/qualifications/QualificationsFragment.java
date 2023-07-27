package org.armandosalazar.aseapplication.ui.profile.qualifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.armandosalazar.aseapplication.R;

public class QualificationsFragment extends Fragment {

    public static QualificationsFragment newInstance() {
        return new QualificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qualifications, container, false);
    }
}