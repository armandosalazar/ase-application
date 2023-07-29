package org.armandosalazar.aseapplication.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.databinding.FragmentProfileBinding;
import org.armandosalazar.aseapplication.ui.profile.qualifications.QualificationsFragment;

public class ProfileFragment extends Fragment {


    private ProfileViewModel viewModel;
    private FragmentProfileBinding binding;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                Log.i("PhotoPicker", "Selected URI: " + uri);
                binding.imageProfile.setImageURI(uri);
            } else {
                Log.i("PhotoPicker", "No media selected");
            }
        });

        binding.buttonEditProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder().build());

            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(ProfileFragment.class.getSimpleName(), "Tab selected: " + tab.getText());
                if (tab.getText().equals("Qualifications")) {
                    getParentFragmentManager().beginTransaction().replace(R.id.profile_fragment_container, QualificationsFragment.newInstance()).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return binding.getRoot();
    }
}