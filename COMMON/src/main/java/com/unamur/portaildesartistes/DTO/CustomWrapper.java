package com.unamur.portaildesartistes.DTO;

import java.io.Serializable;

public class CustomWrapper<T> implements Serializable {
    private T obj;

    public T getObject() {
        return obj;
    }

    public void setObject(T obj) {
        this.obj = obj;
    }

}
