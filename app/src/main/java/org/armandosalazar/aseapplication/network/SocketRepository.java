package org.armandosalazar.aseapplication.network;

import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public abstract class SocketRepository {
    private static final String TAG = SocketRepository.class.getSimpleName();
    private static final String SOCKET_URL = Const.BASE_URL;

    private static Socket socket;


    public static Socket getInstance() {
        if (socket == null) {
            try {
                socket = IO.socket(SOCKET_URL);
            } catch (URISyntaxException e) {
                Log.e(TAG, "getInstance: ", e);
            }
        }
        return socket;
    }
}
