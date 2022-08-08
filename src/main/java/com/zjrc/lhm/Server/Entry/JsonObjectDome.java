package com.zjrc.lhm.Server.Entry;

public class JsonObjectDome {
    private String message;
    private Object o;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }

    public JsonObjectDome() {
    }

    public JsonObjectDome(String message, Object o) {
        this.message = message;
        this.o = o;
    }
}
