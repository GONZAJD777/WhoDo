package com.example.whodo.aplication;

import com.example.whodo.domain.user.User;

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