package org.armandosalazar.aseapplication.ui.home;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.datastore.preferences.core.Preferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.DataStore;
import org.armandosalazar.aseapplication.model.Post;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";
    private final MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    private final PostRepository postRepository = PostRepository.retrofit.create(PostRepository.class);
    private final List<Disposable> disposables = new ArrayList<>();
    private final User user;
    private final String token;
    private final Context context;

    public HomeViewModel(Context context) {
        this.context = context;
        Preferences preferences = DataStore.getInstance(context).data().blockingFirst();
        user = new Gson().fromJson((String) preferences.get(DataStore.USER_KEY), User.class);
        token = (String) preferences.get(DataStore.TOKEN_KEY);
    }

    private void fetchData() {
        disposables.add(postRepository.getPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(posts::postValue, throwable -> {
                    Log.e(TAG, "onError: ", throwable);
                    new android.app.AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage(throwable.getMessage())
                            .setPositiveButton("OK", (dialog, id) -> {
                            }).show();
                }));
    }


    public void createPost(String content) {
        disposables.add(postRepository.createPost("", new Post(user.getId(), content))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(post -> {
                    new AlertDialog.Builder(context)
                            .setTitle("Success")
                            .setMessage("Post created successfully")
                            .setPositiveButton("OK", (dialog, id) -> {
                            }).show();
                    fetchData();
                }, throwable -> {
                    Log.e(TAG, "onError: ", throwable);
                }));


    }

    public LiveData<List<Post>> getPosts() {
        // Important: fetch data only once
        fetchData();
        return posts;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
    }
}
