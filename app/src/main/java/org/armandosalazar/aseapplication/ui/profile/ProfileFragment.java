package org.armandosalazar.aseapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.tabs.TabLayout;

import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.databinding.FragmentProfileBinding;
import org.armandosalazar.aseapplication.ui.profile.qualifications.QualificationsFragment;

import kotlin.Unit;

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

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Log.i("PhotoPicker", "Result code: " + result.getResultCode());
            if (result.getResultCode() == getActivity().RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Log.i("PhotoPicker", "Selected URI: " + data.getData());
                    binding.imageProfile.setImageURI(data.getData());
                } else {
                    Log.i("PhotoPicker", "No media selected");
                }
            }
        });

        binding.buttonEditProfilePhoto.setOnClickListener(v -> {
            ImagePicker.with(ProfileFragment.this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .createIntent(intent -> {
                        launcher.launch(intent);
                        return Unit.INSTANCE;
                    });
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