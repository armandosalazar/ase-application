package org.armandosalazar.aseapplication.ui.sign;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import androidx.appcompat.app.AppCompatActivity;

import org.armandosalazar.aseapplication.databinding.ActivitySignUpBinding;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    SignUpViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());

        SpannableString content = new SpannableString(binding.tvSignIn.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.tvSignIn.setText(content);

        viewModel = new SignUpViewModel(this);

        binding.tvSignIn.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnSignUp.setOnClickListener(v -> {
            // viewModel.signUp("Armando Salazar", "armando@email.com", "123");
            if (Objects.requireNonNull(binding.tilFullName.getEditText()).getText().toString().trim().isEmpty()) {
                binding.tilFullName.setError("Full name is required");
            }
            if (Objects.requireNonNull(binding.tilEmail.getEditText()).getText().toString().trim().isEmpty()) {
                binding.tilEmail.setError("Email is required");
            }
            if (Objects.requireNonNull(binding.tilPassword.getEditText()).getText().toString().trim().isEmpty()) {
                binding.tilPassword.setError("Password is required");
            }
            if (Objects.requireNonNull(binding.tilConfirmPassword.getEditText()).getText().toString().trim().isEmpty()) {
                binding.tilConfirmPassword.setError("Confirm password is required");
            }
            if (!Objects.requireNonNull(binding.tilPassword.getEditText()).getText().toString().trim().isEmpty() &&
                    !Objects.requireNonNull(binding.tilConfirmPassword.getEditText()).getText().toString().trim().isEmpty()) {
                if (!Objects.requireNonNull(binding.tilPassword.getEditText()).getText().toString().trim().equals(
                        Objects.requireNonNull(binding.tilConfirmPassword.getEditText()).getText().toString().trim())) {
                    binding.tilPassword.setError("Password and confirm password must be the same");
                    binding.tilConfirmPassword.setError("Password and confirm password must be the same");
                }
            }

            if (!Objects.requireNonNull(binding.tilFullName.getEditText()).getText().toString().trim().isEmpty() &&
                    !Objects.requireNonNull(binding.tilEmail.getEditText()).getText().toString().trim().isEmpty() &&
                    !Objects.requireNonNull(binding.tilPassword.getEditText()).getText().toString().trim().isEmpty() &&
                    !Objects.requireNonNull(binding.tilConfirmPassword.getEditText()).getText().toString().trim().isEmpty()) {
                viewModel.signUp(
                        Objects.requireNonNull(binding.tilFullName.getEditText()).getText().toString().trim(),
                        Objects.requireNonNull(binding.tilEmail.getEditText()).getText().toString().trim(),
                        Objects.requireNonNull(binding.tilPassword.getEditText()).getText().toString().trim()
                );
            }
        });


        setContentView(binding.getRoot());
    }
}