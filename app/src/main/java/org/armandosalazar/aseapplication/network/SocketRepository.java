package org.armandosalazar.aseapplication.network;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.Preferences;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.DataStore;
import org.armandosalazar.aseapplication.Notification;
import org.armandosalazar.aseapplication.model.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public abstract class SocketRepository {
    private static final String TAG = SocketRepository.class.getSimpleName();
    private static final String SOCKET_URL = Const.BASE_URL;
    private static Socket socket;

    public static Socket getInstance(Context context) {
        if (socket == null) {
            Preferences preferences = DataStore.getInstance(context).data().blockingFirst();
            User user = new Gson().fromJson((String) preferences.get(DataStore.USER_KEY), User.class);
            try {
                socket = IO.socket(SOCKET_URL);

                socket.on("server:[new-post]", args -> {
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        if (jsonObject.get("userId").equals(user.getId()))
                            Notification.show(context, "Success", "Your post was created successfully");
                        else {
                            JSONObject data = (JSONObject) jsonObject.get("user");
                            String username = (String) data.get("fullName");
                            String content = (String) jsonObject.get("content");
                            Notification.show(context, "New post by " + username, content);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                });

                socket.on("server:[new-message]", args -> {
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        if (jsonObject.get("receiverId").equals(user.getId())) {
                            JSONObject data = (JSONObject) jsonObject.get("sender");
                            String sender = (String) data.get("fullName");
                            Notification.show(context, "New message", "You have a new message from " + sender);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                });

                socket.connect();
            } catch (URISyntaxException e) {
                Log.e(TAG, "getInstance: ", e);
            }
        }
        return socket;
    }
}
