package org.armandosalazar.aseapplication.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.tabs.TabLayout;

import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.databinding.FragmentProfileBinding;
import org.armandosalazar.aseapplication.network.ProfileService;
import org.armandosalazar.aseapplication.ui.profile.qualifications.QualificationsFragment;

import java.io.File;
import java.util.Objects;

import kotlin.Unit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {


    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        viewModel = new ProfileViewModel(getContext());
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.tvFullName.setText(user.getFullName());
            binding.tvEmail.setText(user.getEmail());
        });

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    binding.imageProfile.setImageURI(data.getData());

                    ProfileService service = ProfileService.retrofit.create(ProfileService.class);

                    File file = new File(Objects.requireNonNull(Objects.requireNonNull(data.getData()).getPath()));

                    MultipartBody.Part part = new MultipartBody
                            .Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("picture", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                            .build()
                            .part(0);

                    Call<Void> call = service.uploadPicture(part);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                            Log.i("ProfileFragment", "Response code: " + response.code());
                        }

                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            Log.e("ProfileFragment", "ErrorResponse uploading picture", t);
                        }
                    });

                } else {
                    Log.i("PhotoPicker", "No media selected");
                }
            }
        });

        binding.buttonEditProfilePhoto.setOnClickListener(v -> {
            ImagePicker.with(ProfileFragment.this)
                    .crop(1, 1)
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
                if (Objects.equals(tab.getText(), "Qualifications")) {
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

        binding.btnMore.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), binding.btnMore);
            popupMenu.inflate(R.menu.profile_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_about) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("About")
                            .setMessage("Social Network by Armando Salazar\nVersion 1.0.0")
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                            .show();
                }
                if (item.getItemId() == R.id.action_logout) {
                    // viewModel.logout(getContext());
                }
                return true;
            });
            popupMenu.show();
        });

        return binding.getRoot();
    }
}