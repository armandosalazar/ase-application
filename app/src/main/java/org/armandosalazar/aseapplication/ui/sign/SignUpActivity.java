package org.armandosalazar.aseapplication.ui.sign;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import androidx.appcompat.app.AppCompatActivity;

import org.armandosalazar.aseapplication.databinding.ActivitySignUpBinding;

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
            viewModel.signUp("Armando Salazar", "armando@email.com", "123");
        });


        setContentView(binding.getRoot());
    }
}