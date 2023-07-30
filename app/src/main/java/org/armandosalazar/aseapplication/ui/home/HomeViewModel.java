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
                            posts.setValue(Collections.emptyList());

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Error");
                            builder.setMessage("Error fetching data");
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
