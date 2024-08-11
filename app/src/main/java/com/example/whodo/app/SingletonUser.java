package com.example.whodo.app;

import com.example.whodo.app.domain.user.User;

public final class SingletonUser extends User {
    private static SingletonUser instance;

    private SingletonUser(){}

    public static synchronized SingletonUser getInstance() {
        if (instance == null) {
            instance = new SingletonUser();
        }
        return instance;
    }

}