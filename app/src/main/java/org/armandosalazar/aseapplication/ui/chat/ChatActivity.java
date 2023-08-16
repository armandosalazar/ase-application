package org.armandosalazar.aseapplication.ui.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        ChatViewModel viewModel = new ChatViewModel(this);

        User user = (User) getIntent().getSerializableExtra("user");

        binding.textViewName.setText(user.getFullName());
        binding.textViewEmail.setText(user.getEmail());
        binding.buttonBack.setOnClickListener(v -> finish());

        binding.buttonSend.setOnClickListener(v -> {
            String message = binding.message.getText().toString();
            if (!message.isEmpty()) {
                viewModel.sendMessage(user.getId(), message);
                binding.message.setText("");
            }
        });
        binding.buttonSend.setEnabled(false);
        binding.message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (binding.message.getText().toString().isEmpty()) {
                    binding.buttonSend.setEnabled(false);
                } else {
                    binding.buttonSend.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

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