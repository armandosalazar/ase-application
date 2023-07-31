package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.ErrorResponse;

import java.lang.annotation.Annotation;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public abstract class ErrorHandler {
    public static ErrorResponse parseError(Response<?> response) {
        Converter<ResponseBody, ErrorResponse> converter = RetrofitClient.get().responseBodyConverter(ErrorResponse.class, new Annotation[0]);
        ErrorResponse errorResponse = null;
        try {
            errorResponse = converter.convert(Objects.requireNonNull(response.errorBody()));
        } catch (Exception e) {
            new ErrorResponse();
        }

        return errorResponse;
    }
}
