package org.armandosalazar.aseapplication.ui.sign;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.armandosalazar.aseapplication.databinding.ActivitySigninBinding;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    private ActivitySigninBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());

        SpannableString content = new SpannableString(binding.tvSignUp.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        binding.tvSignUp.setText(content);
        binding.tvSignUp.setOnClickListener(v -> {
            Log.d(TAG, "onCreate: Sign up clicked");
        });

        setContentView(binding.getRoot());
    }
}