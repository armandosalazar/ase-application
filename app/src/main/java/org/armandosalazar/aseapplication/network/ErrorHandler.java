package org.armandosalazar.aseapplication.network;

import com.google.gson.Gson;

import org.armandosalazar.aseapplication.model.ErrorResponse;

public abstract class ErrorHandler {
    public static ErrorResponse parseError(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ErrorResponse.class);
    }
}
