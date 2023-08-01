package org.armandosalazar.aseapplication.ui.chat;

import android.util.Log;

import androidx.datastore.preferences.core.Preferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.DataStore;
import org.armandosalazar.aseapplication.model.Message;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.MessageServices;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatViewModel extends ViewModel {
    private static final String TAG = ChatViewModel.class.getSimpleName();
    private final MutableLiveData<List<Message>> messages = new MutableLiveData<>();
    // private final MessageRepository messageRepository = new MessageRepository();
    private final User user;
    private final String token;
    private final MessageServices messageServices = MessageServices.retrofit.create(MessageServices.class);

    public ChatViewModel() {
        Preferences preferences = DataStore.getInstance(null).data().blockingFirst();
        Log.d(TAG, "ChatViewModel: " + preferences.get(DataStore.USER_KEY));
        user = new Gson().fromJson((String) preferences.get(DataStore.USER_KEY), User.class);
        token = (String) preferences.get(DataStore.TOKEN_KEY);
    }

    void sendMessage(String content) {
        Observable<Message> observableMessage = messageServices.sendMessage(token, new Message(2, content));
        Disposable disposable = observableMessage
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        msg -> Log.d(TAG, "sendMessage: " + msg.getContent()),
                        throwable -> Log.e(TAG, "sendMessage: ", throwable)
                );

    }

    public LiveData<List<Message>> getMessages() {
        Observable<List<Message>> observableMessages = messageServices.getMessages(token, 2);
        Disposable disposable = observableMessages
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messages -> {
                            Log.d(TAG, "getMessages: " + messages.size());
                            this.messages.setValue(messages);
                        },
                        throwable -> Log.e(TAG, "getMessages: ", throwable)
                );

        return messages;
    }
}
