package org.armandosalazar.aseapplication.ui.home;

import static io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.armandosalazar.aseapplication.model.Post;
import org.armandosalazar.aseapplication.network.PostService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";
    private final MutableLiveData<List<Post>> posts;
    private final Disposable disposable;

    public HomeViewModel(Context context) {

        posts = new MutableLiveData<>();
        PostService postService = PostService.retrofit.create(PostService.class);

        Observable<List<Post>> observable = postService.getPosts();
        disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(
                        posts::setValue,
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
                            builder.setTitle("Error");
                            builder.setMessage(message);
                            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                            builder.create().show();
                        },
                        () -> Log.d(TAG, "onCreate: completed")
                );


    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }
}
