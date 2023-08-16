package org.armandosalazar.aseapplication.ui.chat;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.Preferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.DataStore;
import org.armandosalazar.aseapplication.model.Message;
import org.armandosalazar.aseapplication.model.User;
import org.armandosalazar.aseapplication.network.MessageRepository;
import org.armandosalazar.aseapplication.network.SocketRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.socket.client.Socket;

public class ChatViewModel extends ViewModel {
    private static final String TAG = ChatViewModel.class.getSimpleName();
    private final MutableLiveData<List<Message>> messages = new MutableLiveData<>();
    private final User user;
    private final String token;
    private final MessageRepository messageRepository = MessageRepository.retrofit.create(MessageRepository.class);

    public ChatViewModel(Context context) {
        final Socket socket = SocketRepository.getInstance(context);

        Preferences preferences = DataStore.getInstance(null).data().blockingFirst();
        Log.d(TAG, "ChatViewModel: " + preferences.get(DataStore.USER_KEY));
        user = new Gson().fromJson((String) preferences.get(DataStore.USER_KEY), User.class);
        token = (String) preferences.get(DataStore.TOKEN_KEY);

        socket.on("new-message", args -> {
            Message message = new Gson().fromJson(args[0].toString(), Message.class);
            if (message.getSenderId() == user.getId() || message.getReceiverId() == user.getId()) {
                Log.d(TAG, "on: " + message.getContent());
                List<Message> messages = this.messages.getValue();
                if (messages != null) {
                    messages.add(message);
                }
                this.messages.postValue(messages);
            }
        });

        socket.connect();
    }

    void sendMessage(int receiverId, String content) {
        Observable<Message> observableMessage = messageRepository.sendMessage(token, new Message(receiverId, content));
        Disposable disposableMessage = observableMessage
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        msg -> Log.d(TAG, "sendMessage: " + msg.getContent()),
                        throwable -> Log.e(TAG, "sendMessage: ", throwable)
                );

    }

    public LiveData<List<Message>> getMessages(int receiverId) {
        Observable<List<Message>> observableMessages = messageRepository.getMessages(token, receiverId);
        Disposable disposableMessages = observableMessages
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messages -> {
                            Log.d(TAG, "getMessages: " + messages.size());
                            this.messages.setValue(messages);
                        },
                        throwable -> {
                            Log.e(TAG, "getMessages: ");
                            Log.e(TAG, "Error Type: " + throwable.getClass().getSimpleName());
                        }
                );

        return messages;
    }
}
