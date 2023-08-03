package org.armandosalazar.aseapplication.ui.home;

import static io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.Preferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.DataStore;
import org.armandosalazar.aseapplication.Notification;
import org.armandosalazar.aseapplication.model.Post;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.PostService;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.socket.client.IO;
import io.socket.client.Socket;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";
    private final MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    private final Disposable disposable;
    private Socket socket;
    private final User user;
    private String token;

    {
        try {
            socket = IO.socket("http://10.0.2.2:3000");
        } catch (Exception e) {
            Log.e(TAG, "instance initializer: ", e);
        }
    }

    public HomeViewModel(Context context) {
        Preferences preferences = DataStore.getInstance(context).data().blockingFirst();

        user = new Gson().fromJson((String) preferences.get(DataStore.USER_KEY), User.class);

        PostService postService = PostService.retrofit.create(PostService.class);

        Observable<List<Post>> observable = postService.getPosts();
        disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(response -> {
                            posts.setValue(response);
                            Log.d(TAG, "onCreate: " + new Gson().toJson(response));

                        },
                        throwable -> {
                            // get throwable message
                            String message = throwable.getMessage();
                            Log.e(TAG, "onCreate: " + message);
                            // get http status code
                            Log.e(TAG, "onCreate: " + Objects.requireNonNull(throwable.getCause()));

                            // verify if exception is a connection exception
                            if (throwable.getCause() instanceof java.net.SocketTimeoutException) {
                                message = "Connection timeout";
                            }
                            // verify if connection exception is a timeout exception
                            if (throwable.getCause() instanceof java.net.UnknownHostException) {
                                message = "Unknown host";
                            }
                            // verify if connection exception is a connection refused exception
                            if (throwable.getCause() instanceof java.net.ConnectException) {
                                message = "Connection refused";
                            }

                            posts.setValue(Collections.emptyList());

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("ErrorResponse");
                            builder.setMessage(message);
                            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                            builder.create().show();
                        },
                        () -> Log.d(TAG, "onCreate: completed")
                );

        socket.on("server:message", args -> {
            JSONObject data = (JSONObject) args[0];
            Log.e(TAG, "onCreate: " + data.toString());
            try {
                if (data.get("idSender").equals(1)) {
                    Log.e(TAG, "onCreate: " + data.get("message"));
                    Notification.show(context, "New message", data.get("message").toString());
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
        socket.emit("client:message", new JSONObject() {
            {
                try {
                    put("idSender", 1);
                    put("idReceiver", 2);
                    put("message", "Hello from Android");
                } catch (JSONException ignored) {
                }
            }
        });

        socket.connect();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public void addPost(String content) {
        PostService postService = PostService.retrofit.create(PostService.class);

        Observable<Post> postObservable = postService.createPost(token, new Post(user.getId(), content));
        Disposable postDisposable = postObservable
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(post -> {
                    Log.d(TAG, "addPost: " + post.getUserId());
                    List<Post> postList = posts.getValue();
                    postList.add(post);
                    posts.setValue(postList);
                }, throwable -> {
                    Log.e(TAG, "addPost: ", throwable);
                });
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }
}
