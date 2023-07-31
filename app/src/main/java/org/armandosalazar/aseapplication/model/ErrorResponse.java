package org.armandosalazar.aseapplication.model;

public class ErrorResponse {
    private String error;
    private String code;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, String code) {
        this.error = error;
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public String getCode() {
        return code;
    }
}
