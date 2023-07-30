package org.armandosalazar.aseapplication.model;

public class Error {
    private String message;
    private String code;

    public Error() {
    }

    public Error(String message, String code) {
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
