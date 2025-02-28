package com.example.whodo.app.domain.user.dao;

import androidx.lifecycle.LiveData;

import com.example.whodo.app.Callback;

import java.util.List;

public interface UserDao<T> {

    LiveData<T> findUser(T t);
    void findCustomer(T t,Callback<T> callback);
    void create(T t, Callback<T> callback);
    void update(T t);
    void findProviders(T t, Callback<List<T>> callback);
    void findLanguages(Callback<List<String>> callback);
    void findServices(Callback<List<String>> callback);

}
