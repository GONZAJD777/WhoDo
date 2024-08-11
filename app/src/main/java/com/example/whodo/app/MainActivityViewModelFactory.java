package com.example.whodo.app;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.whodo.app.domain.user.dao.FirebaseUserDAO;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final FirebaseUserDAO dao;

    public MainActivityViewModelFactory(FirebaseUserDAO dao) {
        this.dao = dao;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(dao);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }


}
