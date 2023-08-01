package org.armandosalazar.aseapplication.ui.people;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.UserService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PeopleViewModel extends ViewModel {
    private static final String TAG = PeopleViewModel.class.getSimpleName();
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();
    // private final PeopleRepository peopleRepository = new PeopleRepository();
    private final UserService userService = UserService.retrofit.create(UserService.class);

    public MutableLiveData<List<User>> getUsers() {
        Observable<List<User>> usersObservable = userService.getUsers();
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
