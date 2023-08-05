package org.armandosalazar.aseapplication.adapter;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.databinding.ItemPostBinding;
import org.armandosalazar.aseapplication.model.Post;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {
    private final List<Post> posts = new ArrayList<>();


    @NonNull
    @Override
    public PostsAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LinearLayout.inflate(parent.getContext(), R.layout.item_post, null);

        return new PostViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        ItemPostBinding binding = ItemPostBinding.bind(holder.itemView);
        Post post = posts.get(position);

        if (Objects.nonNull(post.getUser())) {
            binding.textUsername.setText(post.getUser().getFullName());
            binding.textContent.setText(post.getContent());

            binding.textDate.setText(getFormattedDate(post.getCreatedAt()));
        }


        binding.addComment.setOnClickListener(v -> {
        });

        binding.addLike.setOnCheckedChangeListener((buttonView, isChecked) -> {
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getFormattedDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

        Instant instant = Instant.from(formatter.parse(date));
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

        Locale locale = new Locale("es", "MX");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("E d 'de' MMM HH:mm", locale);

        return zonedDateTime.format(outputFormatter);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setItems(List<Post> posts) {
        this.posts.clear();
        this.posts.addAll(posts);
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
