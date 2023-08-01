package org.armandosalazar.aseapplication.ui.people;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.Preferences;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.armandosalazar.aseapplication.DataStore;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.UserService;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PeopleViewModel extends ViewModel {
    private static final String TAG = PeopleViewModel.class.getSimpleName();
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();
    // private final PeopleRepository peopleRepository = new PeopleRepository();
    private final UserService userService = UserService.retrofit.create(UserService.class);
    private final String token;


    public PeopleViewModel(Context context) {
        Preferences preferences = DataStore.getInstance(context)
                .data()
                .blockingFirst();
        token = Objects.requireNonNull(preferences.get(DataStore.TOKEN_KEY)).toString();
        // getUsers();
    }

    public MutableLiveData<List<User>> getUsers() {
        // send header with token
        Observable<List<User>> usersObservable = userService
                .getUsers(token);
        Disposable usersDisposable = usersObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        this.users::setValue,
                        error -> {
                            Log.e(TAG, "ErrorResponse: " + error.getMessage());
                        }
                );
        return users;
    }

}
