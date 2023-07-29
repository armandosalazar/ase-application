package org.armandosalazar.aseapplication.adapter;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.databinding.ItemPostBinding;
import org.armandosalazar.aseapplication.model.Comment;
import org.armandosalazar.aseapplication.model.Post;
import org.armandosalazar.aseapplication.network.CommentsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {
    private final List<Post> posts;
    private ItemPostBinding binding;

    public PostsAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostsAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LinearLayout.inflate(parent.getContext(), R.layout.item_post, null);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        binding = ItemPostBinding.bind(holder.itemView);
        Post post = posts.get(position);

        binding.textUsername.setText(post.getUsername());
        binding.textContent.setText(post.getContent());
        binding.addComment.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetComments = new BottomSheetDialog(v.getContext());
            bottomSheetComments.setContentView(R.layout.bottom_sheet_comments);

            FrameLayout bottomSheet = bottomSheetComments.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);


            RecyclerView recyclerView = bottomSheetComments.findViewById(R.id.recycler_view_comments);
            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

            LinearLayout linearLayout = bottomSheetComments.findViewById(R.id.linear_layout_comment);
            linearLayout.setGravity(Gravity.BOTTOM);

            CommentsService commentsService = CommentsService.retrofit.create(CommentsService.class);

            Call<List<Comment>> call = commentsService.getComments();
            call.enqueue(new Callback<List<Comment>>() {
                @Override
                public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                    Log.i("Comments", response.body().toString());
                    CommentsAdapter commentsAdapter = new CommentsAdapter(response.body());
                    recyclerView.setAdapter(commentsAdapter);
                }

                @Override
                public void onFailure(Call<List<Comment>> call, Throwable t) {

                }
            });

            bottomSheetComments.show();
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
