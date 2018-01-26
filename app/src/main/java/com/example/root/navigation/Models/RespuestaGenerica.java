package com.example.root.navigation.Models;

/**
 * Created by root on 25/01/18.
 */

public class RespuestaGenerica {
    String message;

    public RespuestaGenerica(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
