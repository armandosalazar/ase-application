package org.armandosalazar.aseapplication.ui.chat;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.armandosalazar.aseapplication.adapter.MessagesAdapter;
import org.armandosalazar.aseapplication.databinding.ActivityChatBinding;
import org.armandosalazar.aseapplication.model.Message;
import org.armandosalazar.aseapplication.model.User;

import java.util.ArrayList;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChatBinding binding = ActivityChatBinding.inflate(getLayoutInflater());
        ChatViewModel viewModel = new ChatViewModel();

        User user = (User) getIntent().getSerializableExtra("user");

        binding.name.setText(Objects.requireNonNull(user).getFullName());
        binding.backButton.setOnClickListener(v -> {
            // viewModel.sendMessage("Hello World!");
            onBackPressed();
        });

        binding.recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewMessages.setAdapter(new MessagesAdapter(new ArrayList<>()));

        viewModel.getMessages(user.getId()).observe(this, messages -> {
            MessagesAdapter adapter = (MessagesAdapter) binding.recyclerViewMessages.getAdapter();
            Objects.requireNonNull(adapter).setMessages(messages);
            binding.recyclerViewMessages.scrollToPosition(messages.size() - 1);
            adapter.notifyDataSetChanged();
            for (Message message : messages) {
                Log.i("ChatActivity", "onCreate: " + message.getContent());
            }
        });

        setContentView(binding.getRoot());
    }
}