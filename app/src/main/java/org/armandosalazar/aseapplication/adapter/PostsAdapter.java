package org.armandosalazar.aseapplication.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.datastore.preferences.core.Preferences;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.armandosalazar.aseapplication.DataStore;
import org.armandosalazar.aseapplication.R;
import org.armandosalazar.aseapplication.databinding.ItemPostBinding;
import org.armandosalazar.aseapplication.model.FavoritePost;
import org.armandosalazar.aseapplication.model.Post;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.Const;
import org.armandosalazar.aseapplication.network.PostRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {
    private final String TAG = "PostsAdapter";
    private final List<Post> posts = new ArrayList<>();
    private final String token;
    private final User user;
    private final PostRepository postRepository = PostRepository.retrofit.create(PostRepository.class);

    public PostsAdapter(Context context) {
        Preferences preferences = DataStore.getInstance(context).data().blockingFirst();
        user = new Gson().fromJson((String) preferences.get(DataStore.USER_KEY), User.class);
        token = (String) preferences.get(DataStore.TOKEN_KEY);
    }


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

        Log.d(TAG, "onBindViewHolder: " + post.getUser().getFullName());
        String username = post.getUser().getFullName();
        Log.d(TAG, "onBindViewHolder: " + username);
        binding.textUsername.setText(username);
        binding.textContent.setText(post.getContent());
        binding.textDate.setText(getFormattedDate(post.getCreatedAt()));
        if (post.getUser().getProfilePicture() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(Const.BASE_URL + "/uploads/" + post.getUser().getProfilePicture())
                    .into(binding.imageProfile);
        }
        if (post.isFavorite()) {
            binding.isFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            binding.isFavorite.setImageResource(R.drawable.ic_favorite_border);
        }

        binding.isFavorite.setOnClickListener(view -> {
            if (post.isFavorite()) {
                Disposable disposable = postRepository.unfavoritePost(token, post.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            Log.d(TAG, "onBindViewHolder: " + response.getMessage());
                            post.setFavorite(false);
                            binding.isFavorite.setImageResource(R.drawable.ic_favorite_border);
                        }, error -> {
                            binding.isFavorite.setImageResource(R.drawable.ic_favorite);
                        });
            } else {
                Disposable disposable = postRepository.favoritePost(token, new FavoritePost(post.getId()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            Log.d(TAG, "onBindViewHolder: " + response.getMessage());
                            post.setFavorite(true);
                            binding.isFavorite.setImageResource(R.drawable.ic_favorite);
                        }, error -> {
                            binding.isFavorite.setImageResource(R.drawable.ic_favorite_border);
                        });
            }
        });

        binding.addComment.setOnClickListener(v -> {
        });

//        binding.isFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                Disposable disposable = postRepository.favoritePost(token, new FavoritePost(post.getId()))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(response -> {
//                            Log.d(TAG, "onBindViewHolder: " + response.getMessage());
//                            post.setFavorite(true);
//                        }, error -> {
//                            binding.isFavorite.setChecked(false);
//                        });
//            } else {
//                Disposable disposable = postRepository.unfavoritePost(token, post.getId())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(response -> {
//                            Log.d(TAG, "onBindViewHolder: " + response.getMessage());
//                            post.setFavorite(false);
//                        }, error -> {
//                            binding.isFavorite.setChecked(true);
//                        });
//            }
//        });
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
