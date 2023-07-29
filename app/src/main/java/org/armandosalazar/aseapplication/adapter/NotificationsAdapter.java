package org.armandosalazar.aseapplication.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.armandosalazar.aseapplication.databinding.ItemNotificationBinding;
import org.armandosalazar.aseapplication.model.Notification;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {
    private final List<Notification> notifications;
    private ItemNotificationBinding binding;

    public NotificationsAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationsAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = View.inflate(parent.getContext(), org.armandosalazar.aseapplication.R.layout.item_notification, null);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.NotificationViewHolder holder, int position) {
        binding = ItemNotificationBinding.bind(holder.itemView);
        final Notification notification = notifications.get(position);
        binding.notificationTitle.setText(notification.getTitle());
        binding.notificationDescription.setText(notification.getDescription());
        binding.notificationDate.setText(notification.getDate());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
