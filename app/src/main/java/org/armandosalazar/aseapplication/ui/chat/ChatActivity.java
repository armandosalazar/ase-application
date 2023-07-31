package org.armandosalazar.aseapplication.ui.chat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.armandosalazar.aseapplication.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        org.armandosalazar.aseapplication.databinding.ActivityChatBinding binding = ActivityChatBinding.inflate(getLayoutInflater());

        binding.backButton.setOnClickListener(v -> onBackPressed());

        setContentView(binding.getRoot());
    }
}