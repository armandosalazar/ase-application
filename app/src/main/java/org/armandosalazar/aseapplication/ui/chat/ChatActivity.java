package org.armandosalazar.aseapplication.ui.chat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.armandosalazar.aseapplication.databinding.ActivityChatBinding;
import org.armandosalazar.aseapplication.model.Person;

import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChatBinding binding = ActivityChatBinding.inflate(getLayoutInflater());

        Person person = (Person) getIntent().getSerializableExtra("person");

        binding.name.setText(Objects.requireNonNull(person).getName());
        binding.backButton.setOnClickListener(v -> onBackPressed());

        setContentView(binding.getRoot());
    }
}