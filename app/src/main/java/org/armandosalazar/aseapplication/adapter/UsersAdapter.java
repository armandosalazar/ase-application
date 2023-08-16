package org.armandosalazar.aseapplication.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.databinding.ItemPersonBinding;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.Const;
import org.armandosalazar.aseapplication.ui.chat.ChatActivity;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private final List<User> users;
    private ItemPersonBinding binding;

    public UsersAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        binding = ItemPersonBinding.bind(holder.itemView);
        final User user = users.get(position);
        binding.name.setText(user.getFullName());
        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            intent.putExtra("user", user);
            v.getContext().startActivity(intent);
        });
        if (user.getProfilePicture() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(Const.BASE_URL + "/uploads/" + user.getProfilePicture())
                    .into(binding.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
