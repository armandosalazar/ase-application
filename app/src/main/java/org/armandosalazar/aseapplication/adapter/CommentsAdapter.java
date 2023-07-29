package org.armandosalazar.aseapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.databinding.ItemCommentBinding;
import org.armandosalazar.aseapplication.model.Comment;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    private final List<Comment> comments;
    private ItemCommentBinding binding;

    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        binding = ItemCommentBinding.bind(holder.itemView);
        Comment comment = comments.get(position);
        binding.commentUsername.setText(comment.getUsername());
        binding.commentContent.setText(comment.getContent());
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
