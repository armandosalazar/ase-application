package org.armandosalazar.aseapplication.adapter;

import android.text.SpannableString;
import android.text.SpannedString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
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
        // fix: https://stackoverflow.com/questions/30691150/match-parent-width-does-not-work-in-recyclerview
        final View view = LayoutInflater.from(parent.getContext()).inflate(org.armandosalazar.aseapplication.R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.NotificationViewHolder holder, int position) {
        binding = ItemNotificationBinding.bind(holder.itemView);
        final Notification notification = notifications.get(position);

        SpannableString content = new SpannableString(notification.getTitle() + " " + notification.getDescription());
        content.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, notification.getTitle().length(), SpannedString.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), notification.getTitle().length(), content.length(), SpannedString.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.notificationContent.setText(content);
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
