package com.example.whodo.aplication;

public interface Callback<T> {
    void onSuccess(T t);
    void onError(Exception e);
}
