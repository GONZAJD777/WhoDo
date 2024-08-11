package com.example.whodo.app;

public interface Callback<T> {
    void onSuccess(T t);
    void onError(Exception e);
}
