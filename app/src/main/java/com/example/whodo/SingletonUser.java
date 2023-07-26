package com.example.whodo;

import com.example.whodo.BusinessClasses.User;

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