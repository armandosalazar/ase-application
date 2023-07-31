package org.armandosalazar.aseapplication.model;

public class SuccessResponse {
    private String message;
    private String code;

    public SuccessResponse() {
    }

    public SuccessResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
