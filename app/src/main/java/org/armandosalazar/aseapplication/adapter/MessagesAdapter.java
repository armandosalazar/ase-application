package org.armandosalazar.aseapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.datastore.preferences.core.Preferences;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.DataStore;
import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.databinding.ItemMessageReceivedBinding;
import org.armandosalazar.aseapplication.databinding.ItemMessageSentBinding;
import org.armandosalazar.aseapplication.model.Message;
import org.armandosalazar.aseapplication.model.User;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    private static final String TAG = MessagesAdapter.class.getSimpleName();
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private final List<Message> messages;
    private final User user;
    private Context context;

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSenderId() == user.getId()) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    public MessagesAdapter(List<Message> messages) {
        Preferences preferences = DataStore.getInstance(context)
                .data()
                .blockingFirst();
        user = new Gson().fromJson((String) preferences.get(DataStore.USER_KEY), User.class);
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            View view = inflater.inflate(R.layout.item_message_sent, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            View view = inflater.inflate(R.layout.item_message_received, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (getItemViewType(position) == VIEW_TYPE_MESSAGE_SENT) {
            ItemMessageSentBinding binding = ItemMessageSentBinding.bind(holder.itemView);
            binding.textViewMessageSent.setText(message.getContent());
        } else if (getItemViewType(position) == VIEW_TYPE_MESSAGE_RECEIVED) {
            ItemMessageReceivedBinding binding = ItemMessageReceivedBinding.bind(holder.itemView);
            binding.textViewMessageReceived.setText(message.getContent());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
