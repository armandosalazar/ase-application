package org.armandosalazar.aseapplication.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.model.Post;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {
    private final List<Post> posts;

    public PostsAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostsAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_post, null);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.textUsername.setText(post.getUsername());
        holder.textContent.setText(post.getContent());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView textUsername;
        private final TextView textContent;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.text_username);
            textContent = itemView.findViewById(R.id.text_content);
        }
    }
}
