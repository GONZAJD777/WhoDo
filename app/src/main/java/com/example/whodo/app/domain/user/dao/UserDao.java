package com.example.whodo.app.domain.user.dao;

import com.example.whodo.app.Callback;
import com.example.whodo.app.ViewModelInterface;
import com.example.whodo.app.domain.user.User;

import java.util.List;

public interface UserDao<T> {

    void findUser(User pUser, Callback<User> callback);
    void findProviders(User pUser, Double distance, Callback<List<User>> callback);
    //void findProviders(ViewModelInterface pViewModel, T t, Double distance);
    void findCustomer(T t, Callback<T> callback);
    void create(T t, Callback<T> callback);
    void update(T t,Callback<T> callback);
    void closeConnection();
    void updateFcmToken(User pUser);
}
